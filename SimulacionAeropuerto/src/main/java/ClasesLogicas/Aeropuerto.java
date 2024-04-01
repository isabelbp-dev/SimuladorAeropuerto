package ClasesLogicas;
import Interfaz.InterfazSimulador;
import Renders.CircularProgressBar;
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
    RegistroLog logger = RegistroLog.getInstance();
    
    //Atributos
    private String nombre;
    private int ocupacion;
    private InterfazSimulador simulador;
    ArrayList<String> puertasEmbarque = new ArrayList<>(Collections.nCopies(6,null));
    ArrayList<String> pistas = new ArrayList<>(Collections.nCopies(4, null));
    HashSet<String> hangar = new HashSet<>();
    HashSet<String> rodaje = new HashSet<>();
    HashSet<String> estacionamiento = new HashSet<>();
    HashSet<String> taller = new HashSet<>();
    
    //Atributos para la comunicación y sincronización de...
    //de buses
    private final Lock llegadaB = new ReentrantLock();
    private final Lock salidaB = new ReentrantLock();
    
    //de puertas de embarque
    private final Lock puertas = new ReentrantLock(true);
    private final Condition puertaEmbarque = puertas.newCondition();
    private final Condition puertaDesembarque = puertas.newCondition();
    
    //de pistas de aterrizaje/despegue
    private final Semaphore semPistas = new Semaphore(4, true);
    private final Lock lPistas = new ReentrantLock();
    
    //de hangar, rodaje, estacionamiento y taller
    private final Lock lHangar = new ReentrantLock();
    private final Lock lRodaje = new ReentrantLock();
    private final Lock lEstacionamiento = new ReentrantLock();
    private final Lock puertaTaller = new ReentrantLock(true);
    private final Semaphore semTaller = new Semaphore(20, true);
    
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
    public HashSet getHangar(){
        return hangar;
    }
    public HashSet getTaller(){
        return taller;
    }
    public HashSet getEstacionamiento(){
        return estacionamiento;
    }
    public HashSet getRodaje(){
        return rodaje;
    }
    
    //Llegada y salida de pasajeros
    public void actualizarNumPasajeros(int num) throws InterruptedException{
        if(nombre == "Madrid"){
            simulador.modPasajerosM(num);
        }else{
            simulador.modPasajerosB(num);
    }}
    public synchronized void llegadaPasajeros(int num) throws InterruptedException{
        simulador.pausar();
        ocupacion += num;
        actualizarNumPasajeros(ocupacion);
    }
    public synchronized int salidaPasajeros(int num) throws InterruptedException{
        simulador.pausar();
        if(ocupacion >= num){
            ocupacion -= num;
        }else{
            num = ocupacion;
            ocupacion = 0;
        }
        actualizarNumPasajeros(ocupacion);
        return num;}
    
    //Llegada y salida de buses
    public void llegadaBus(String id) throws InterruptedException{
        llegadaB.lock();
        simulador.pausar();
        if(nombre == "Madrid"){
           simulador.modAeroM(id);
        }else{
            simulador.modAeroB(id);
        }
        llegadaB.unlock();
    }
    public void salidaBus(String id) throws InterruptedException{
        salidaB.lock();
        simulador.pausar();
        if(nombre == "Madrid"){
           simulador.modBusCiudadM(id);
        }else{
            simulador.modBusCiudadB(id);
        }
        salidaB.unlock();
    }
    
    //Llegada y salida de aviones al hangar
    public void llegadaHangar(String id) throws InterruptedException{
        lHangar.lock();
        simulador.pausar();
        hangar.add(id);
        actualizarHangar();
        lHangar.unlock();
    }
    public void salidaHangar(String id) throws InterruptedException{
        lHangar.lock();
        simulador.pausar();
        hangar.remove(id);
        actualizarHangar();
        lHangar.unlock();
    }
    public void actualizarHangar() throws InterruptedException{
        if(nombre == "Madrid"){
            simulador.modHangarM(hangar);
        }else{
            simulador.modHangarB(hangar);
        }
    }
    
    //Solicitudes, asignaciones y salidas de las puertas de embarque
    public Object[] solPuertaEmbarque(String id) throws InterruptedException{
        puertas.lock();
        simulador.pausar();
        int n = 0;
        CircularProgressBar c = new CircularProgressBar(); 
        try{
            n = puertasEmbarque.subList(0, 5).indexOf(null);
            while(n<0){
                puertaEmbarque.await();
                n = puertasEmbarque.subList(0, 5).indexOf(null);
            }
            puertasEmbarque.set(n, id);
            c = actualizarPuertas(n, " 🛫 - "+id);
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }finally{
            puertas.unlock();}
        return new Object[]{n, c};
    }
    public int solPuertaDesembarque(String id) throws InterruptedException{
        puertas.lock();
        simulador.pausar();
        int n = 0;
        try{
            n = puertasEmbarque.subList(1, 6).lastIndexOf(null);
            while(n<0){
                puertaEmbarque.await();
                n = puertasEmbarque.subList(1, 6).lastIndexOf(null);
            }
            n+=1;
            puertasEmbarque.set(n, id);
            actualizarPuertas(n, " 🛬 - "+id);
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }finally{
            puertas.unlock();}
        return n;
    }
    public void liberarPuerta(int n) throws InterruptedException{
        puertas.lock();
        simulador.pausar();
        if(n > 0){
            puertaDesembarque.signalAll();
        }if(n < 5){
            puertaEmbarque.signalAll();
        }
        puertasEmbarque.set(n, null);
        actualizarPuertas(n, "");
        puertas.unlock();
    }
    public CircularProgressBar actualizarPuertas(int puerta, String id) throws InterruptedException{
        CircularProgressBar c;
        if(nombre == "Madrid"){
            c = simulador.modPuertasM(puerta, id);
        }else{
            c = simulador.modPuertasB(puerta, id);
        }
        return c; 
    }
    
    //Operaciones relacionadas con las pistas de despegue y aterrizaje
    public void solPistaDespegue(String id, int pasajeros) throws InterruptedException{
        semPistas.acquire();
        simulador.pausar();
        int pista = pistas.indexOf(null);
        ocuparPista(pista, id);
        salidaRodaje(id);
        logger.registrarEvento("Avión " + id + " (" + pasajeros + " pasajeros) accede a pista " + pista + " para despegue. ");
        Thread.sleep(1000+(int)(Math.random()*2000));
        liberarPista(pista);
        semPistas.release();
    }
    public void solPistaAterrizaje(Avion a) throws InterruptedException{
        boolean encontrada;
        encontrada = semPistas.tryAcquire();
        while(!encontrada){
            Thread.sleep(1000+(int)(Math.random()*4000));
            encontrada = semPistas.tryAcquire();
        }
        simulador.pausar();
        int pista = pistas.indexOf(null);
        ocuparPista(pista, a.getId());
        logger.registrarEvento("Avión " + a.getId() + " (" + a.getOcupacion() + " pasajeros) accede a pista " + pista + " para aterrizar. ");
        if(nombre == "Madrid"){
            simulador.salirAerovBM(a);
        }else{
            simulador.salirAerovMB(a);
        }
        Thread.sleep(1000+(int)(Math.random()*4000));
        liberarPista(pista);
        semPistas.release();
    }
    public void ocuparPista(int pista, String id) throws InterruptedException{
        lPistas.lock();
        pistas.set(pista, id);
        actualizarPistas(pista, id);
        lPistas.unlock();
    }
    public void liberarPista(int pista) throws InterruptedException{
        lPistas.lock();
        simulador.pausar();
        pistas.set(pista, null);
        actualizarPistas(pista, null);
        lPistas.unlock();
    }
    public void actualizarPistas(int pista, String id) throws InterruptedException{
        if(nombre == "Madrid"){
            simulador.modPistasM(pista, id);
        }else{
            simulador.modPistasB(pista, id);
        }
    }
    
    //Vuelo de aviones o uso de aerovías
    public void volar(Avion a) throws InterruptedException{
        simulador.pausar();
        if(nombre == "Madrid"){
            simulador.usoAeroviaMB(a);
        }else{
            simulador.usoAeroviaBM(a);
        }
    }

    //Llegadas y salidas al área de rodaje
    public void llegadaRodaje(String id) throws InterruptedException{
        lRodaje.lock();
        simulador.pausar();
        rodaje.add(id);
        actualizarRodaje();
        lRodaje.unlock();
    }
    public void salidaRodaje(String id) throws InterruptedException{
        lRodaje.lock();
        simulador.pausar();
        rodaje.remove(id);
        actualizarRodaje();
        lRodaje.unlock();
    }
    public void actualizarRodaje() throws InterruptedException{
        if(nombre == "Madrid"){
            simulador.modRodajeM(rodaje);
        }else{
            simulador.modRodajeB(rodaje);
        }
    }
    
    //Llegadas y salidas al área de estacionamiento
    public void llegadaEstacionamiento(String id) throws InterruptedException{
        lEstacionamiento.lock();
        simulador.pausar();
        estacionamiento.add(id);
        actualizarEstacionamiento();
        lEstacionamiento.unlock();
    }
    public void salidaEstacionamiento(String id) throws InterruptedException{
        lEstacionamiento.lock();
        simulador.pausar();
        estacionamiento.remove(id);
        actualizarEstacionamiento();
        lEstacionamiento.unlock();
    }
    public void actualizarEstacionamiento() throws InterruptedException{
        if(nombre == "Madrid"){
            simulador.modEstacionamientoM(estacionamiento);
        }else{
            simulador.modEstacionamientoB(estacionamiento);
        }
    }

    //Gestión de las revisiones del taller
    public void revisionRapida(String id) throws InterruptedException{
        semTaller.acquire();
        puertaTaller.lock();
        simulador.pausar();
        Thread.sleep(1000);
        taller.add(id);
        actualizarTaller();
        logger.registrarEvento("Avión " + id + " accede al taller para una revisión rápida. ");
        puertaTaller.unlock();
        Thread.sleep(1000+(int)(Math.random()*4000));
        puertaTaller.lock();
        Thread.sleep(1000);
        taller.remove(id);
        actualizarTaller();
        puertaTaller.unlock();
        semTaller.release();
    }
    public void revisionProfunda(String id) throws InterruptedException{
        semTaller.acquire();
        simulador.pausar();
        puertaTaller.lock();
        Thread.sleep(1000);
        taller.add(id);
        actualizarTaller();
        logger.registrarEvento("Avión " + id + " accede al taller para una revisión profunda. ");
        puertaTaller.unlock();
        Thread.sleep(5000+(int)(Math.random()*5000));
        puertaTaller.lock();
        Thread.sleep(1000);
        taller.remove(id);
        actualizarTaller();
        puertaTaller.unlock();
        semTaller.release();
    }
    public void actualizarTaller() throws InterruptedException{
        if(nombre == "Madrid"){
            simulador.modTallerM(taller);
        }else{
            simulador.modTallerB(taller);
        }
    }
}
