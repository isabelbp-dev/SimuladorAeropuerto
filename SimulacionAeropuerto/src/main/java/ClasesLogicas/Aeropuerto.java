package ClasesLogicas;
import Interfaz.InterfazSimulador;
import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author isaba
 */

public class Aeropuerto {

    //Atributos
    private String nombre;
    private int ocupacion;
    private InterfazSimulador simulador;
    ArrayList<String> puertasEmbarque = new ArrayList<>(Collections.nCopies(6,null));
    ArrayList<String> pistas = new ArrayList<>(Collections.nCopies(4, null));
    HashSet<String> hangar = new HashSet<>();
    HashSet<String> rodaje = new HashSet<>();
    HashSet<String> estacionamiento = new HashSet<>();
    
    //Atributos para la comunicación y sincronización de...
    //de buses
    private final Lock llegadaB = new ReentrantLock();
    private final Lock salidaB = new ReentrantLock();
    
    //de puertas de embarque
    private final Lock puertas = new ReentrantLock(true);
    Condition puertaEmbarque = puertas.newCondition();
    Condition puertaDesembarque = puertas.newCondition();
    
    //de pistas de aterrizaje/despegue
    private final Semaphore semPistas = new Semaphore(4, true);
    private final Lock lPistas = new ReentrantLock();
    
    //de hangar, rodaje y estacionamiento
    private final Lock lHangar = new ReentrantLock();
    private final Lock lRodaje = new ReentrantLock();
    private final Lock lEstacionamiento = new ReentrantLock();
    
    //Constructor
    public Aeropuerto(InterfazSimulador s, String n){
        ocupacion = 0;
        simulador = s;
        nombre = n;
    }
    
    //Métodos getter y setter
    public String getNombre(){
        return nombre;
    }
    
    //Llegada y salida de pasajeros
    public void actualizarNumPasajeros(int num){
        if(nombre == "Madrid"){
            simulador.modPasajerosM(num);
        }else{
            simulador.modPasajerosB(num);
    }}
    public synchronized void llegadaPasajeros(int num){
        ocupacion += num;
        actualizarNumPasajeros(num);
    }
    public synchronized int salidaPasajeros(int num){
        if(ocupacion >= num){
            ocupacion -= num;
        }else{
            num = ocupacion;
            ocupacion = 0;
        }
        actualizarNumPasajeros(num);
        return num;}
    
    //Llegada y salida de buses
    public void llegadaBus(String id){
        llegadaB.lock();
        if(nombre == "Madrid"){
           simulador.modAeroM(id);
        }else{
            simulador.modAeroB(id);
        }
        llegadaB.unlock();
    }
    public void salidaBus(String id){
        salidaB.lock();
        if(nombre == "Madrid"){
           simulador.modBusCiudadM(id);
        }else{
            simulador.modBusCiudadB(id);
        }
        salidaB.unlock();
    }
    
    //Llegada y salida de aviones al hangar
    public void llegadaHangar(String id){
        lHangar.lock();
        hangar.add(id);
        actualizarHangar();
        lHangar.unlock();
    }
    public void salidaHangar(String id){
        lHangar.lock();
        hangar.remove(id);
        actualizarHangar();
        lHangar.unlock();
    }
    public void actualizarHangar(){
        if(nombre == "Madrid"){
            simulador.modHangarM(hangar);
        }else{
            simulador.modHangarB(hangar);
        }
    }
    
    //Solicitudes, asignaciones y salidas de las puertas de embarque
    public int solPuertaEmbarque(String id){
        puertas.lock();
        int n = 0;
        try{
            n = puertasEmbarque.subList(0, 5).indexOf(null);
            while(n<0){
                puertaEmbarque.await();
                n = puertasEmbarque.subList(0, 5).indexOf(null);
            }
            puertasEmbarque.set(n, id);
            actualizarPuertas(n, id);
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }finally{
            puertas.unlock();}
        return n;
    }
    public int solPuertaDesembarque(String id){
        puertas.lock();
        int n = 0;
        try{
            n = puertasEmbarque.subList(1, 6).lastIndexOf(null);
            while(n<0){
                puertaEmbarque.await();
                n = puertasEmbarque.subList(1, 6).lastIndexOf(null);
            }
            n+=1;
            System.out.println(n);
            puertasEmbarque.set(n, id);
            actualizarPuertas(n, id);
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }finally{
            puertas.unlock();}
        return n;
    }
    public void liberarPuerta(int n){
        puertas.lock();
        if(n > 0){
            puertaDesembarque.signalAll();
        }if(n < 5){
            puertaEmbarque.signalAll();
        }
        puertasEmbarque.set(n, null);
        actualizarPuertas(n, "");
        puertas.unlock();
    }
    public void actualizarPuertas(int puerta, String id){
        if(nombre == "Madrid"){
            simulador.modPuertasM(puerta, id);
        }else{
            simulador.modPuertasB(puerta, id);
        }
    }
    
    //Operaciones relacionadas con las pistas de despegue y aterrizaje
    public void solPistaDespegue(String id) throws InterruptedException{
        semPistas.acquire();
        int pista = pistas.indexOf(null);
        ocuparPista(pista, id);
        salidaRodaje(id);
        Thread.sleep(1000+(int)(Math.random()*2000));
        liberarPista(pista);
        semPistas.release();
    }
    public void solPistaAterrizaje(String id) throws InterruptedException{
        boolean encontrada;
        encontrada = semPistas.tryAcquire();
        while(!encontrada){
            Thread.sleep(1000+(int)(Math.random()*4000));
            encontrada = semPistas.tryAcquire();
        }
        int pista = pistas.indexOf(null);
        ocuparPista(pista, id);
        if(nombre == "Madrid"){
            simulador.salirAerovBM(id);
        }else{
            simulador.salirAerovMB(id);
        }
        Thread.sleep(1000+(int)(Math.random()*4000));
        liberarPista(pista);
        semPistas.release();
    }
    public void ocuparPista(int pista, String id){
        lPistas.lock();
        pistas.set(pista, id);
        actualizarPistas(pista, id);
        lPistas.unlock();
    }
    public void liberarPista(int pista){
        lPistas.lock();
        pistas.set(pista, null);
        actualizarPistas(pista, null);
        lPistas.unlock();
    }
    public void actualizarPistas(int pista, String id){
        if(nombre == "Madrid"){
            simulador.modPistasM(pista, id);
        }else{
            simulador.modPistasB(pista, id);
        }
    }
    
    //Vuelo de aviones o uso de aerovías
    public void volar(Avion a) throws InterruptedException{
        if(nombre == "Madrid"){
            simulador.usoAeroviaMB(a);
        }else{
            simulador.usoAeroviaBM(a);
        }
    }

    //Llegadas y salidas al área de rodaje
    public void llegadaRodaje(String id){
        lRodaje.lock();
        rodaje.add(id);
        actualizarRodaje();
        lRodaje.unlock();
    }
    public void salidaRodaje(String id){
        lRodaje.lock();
        rodaje.remove(id);
        actualizarRodaje();
        lRodaje.unlock();
    }
    public void actualizarRodaje(){
        if(nombre == "Madrid"){
            simulador.modRodajeM(rodaje);
        }else{
            simulador.modRodajeB(rodaje);
        }
    }
    
    //Llegadas y salidas al área de estacionamiento
    public void llegadaEstacionamiento(String id){
        lEstacionamiento.lock();
        estacionamiento.add(id);
        actualizarEstacionamiento();
        lEstacionamiento.unlock();
    }
    public void salidaEstacionamiento(String id){
        lEstacionamiento.lock();
        estacionamiento.remove(id);
        actualizarEstacionamiento();
        lEstacionamiento.unlock();
    }
    public void actualizarEstacionamiento(){
        if(nombre == "Madrid"){
            simulador.modEstacionamientoM(estacionamiento);
        }else{
            simulador.modEstacionamientoB(estacionamiento);
        }
    }
}
