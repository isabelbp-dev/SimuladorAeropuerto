package ClasesLogicas;
import Interfaz.InterfazSimulador;
import Renders.CircularProgressBar;
import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Clase que representa a los aeropueros del simulador.
 * @author Isabel Barquilla y Sandra Familiar
 */

public class Aeropuerto {
    RegistroLog logger = RegistroLog.getInstance();
    //Atributos
    private final String nombre;
    private int ocupacion;
    private final InterfazSimulador simulador;
    private final ArrayList<String> puertasEmbarque = new ArrayList<>(Collections.nCopies(6,null));
    private final ArrayList<String> pistas = new ArrayList<>(Collections.nCopies(4, null));
    private final HashSet<String> hangar = new HashSet<>();
    private final HashSet<String> rodaje = new HashSet<>();
    private final HashSet<String> estacionamiento = new HashSet<>();
    private final HashSet<String> taller = new HashSet<>();
    
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
    
    /**
     * Constructor de la clase aeropuerto
     * La ocupación se inicializa a 0
     * @param s Es el simulador de nuestro aeropuerto, donde deberán registrarse las operaciones realizadas
     * @param n Es el nombre de la ciudad a la que pertenece el aeropuerto, por ejemplo "Madrid"
     */
    public Aeropuerto(InterfazSimulador s, String n){
        ocupacion = 0;
        simulador = s;
        nombre = n;
    }
    
    //Métodos getter y setter
    /**
     * Método que devuelve el nombre del aeropuerto
     * @return nombre: String de la ciudad a la que pertenece el aeropuerto
     */
    public String getNombre(){
        return nombre;
    }
    /**
     * Método que devuelve los aviones del hangar en el momento actual
     * @return hangar: HashSet con todos los Id's de los aviones que ocupan el hangar en el momento actual 
     */
    public HashSet getHangar(){
        return hangar;
    }
    /**
     * Método que devuelve los aviones que hay en el taller en el momento actual
     * @return taller: HashSet que guarda todos los Id's de los aviones que están en el taller en el momento actual
     */
    public HashSet getTaller(){
        return taller;
    }
    /**
     * Método que devuelve los aviones que hay en el estacionamiento en el momento actual
     * @return estacionamiento: HashSet que guarda todos los Id's de los aviones que están actualemente en el aeropuerto
     */
    public HashSet getEstacionamiento(){
        return estacionamiento;
    }
    /**
     * Método que devuelve los aviones que hay en el área de rodaje en el momento actual
     * @return rodaje: HashSet que guarda todos los Id's de los aviones que están actualmente en el aeropuerto
     */
    public HashSet getRodaje(){
        return rodaje;
    }
    
    //Llegada y salida de pasajeros
    /**
     * Método para actualizar el número de pasajeros en el actual en el simulador, bien sea por la llegada o la salida de estos
     * @param num: Int que indica el número de pasajeros que hay actualmente en el aeropuerto
     * @throws java.lang.InterruptedException
     */
    public void actualizarNumPasajeros(int num) throws InterruptedException{
        if("Madrid".equals(nombre)){
            simulador.modPasajerosM(num);
        }else{
            simulador.modPasajerosB(num);
    }}
    /**
     * Método para aumentar el atributo del número de pasajeros actual en el aeropuerto debido a la llegada de pasajeros
     * @param num: Int que indica el número de pasajeros que acaban de llegar al aeropuerto
     * @throws InterruptedException 
     */
    public synchronized void llegadaPasajeros(int num) throws InterruptedException{
        simulador.pausar();
        ocupacion += num;
        actualizarNumPasajeros(ocupacion);
    }
    /**
     * Método que mengua el número de pasajeros que hay actualmente en el aeropuerto, donde tratará de sacar "num" pasajeros para un avión o bus
     * @param num: Número de pasajeros que trataremos de sacar del aeropuerto
     * @return int: Número de pasajeros que finalmente han salido del aeropuerto. Puede ser menor que num en caso de que en el aeropuerto no haya suficientes pasajeros
     * @throws java.lang.InterruptedException
     */
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
    /**
     * Método para indicar la llegada de un bus al aeropuerto, actualizando los datos correspondientes en el simulador
     * @param id: Id del bus que acaba de llegar al aeropuerto
     * @throws java.lang.InterruptedException
     */
    public void llegadaBus(String id) throws InterruptedException{
        llegadaB.lock();
        try {
            simulador.pausar();
            if("Madrid".equals(nombre)){
                simulador.modAeroM(id);
            }else{
                simulador.modAeroB(id);
            }
        } finally {
            llegadaB.unlock();
        }
    }
    /**
     * Método que indica que un bus va a salir del aeropuerto, camino a la ciudad.De igual forma, lo indica en el simulador
     * @param id: Id del bus que sale del aeropuerto
     * @throws java.lang.InterruptedException
     */
    public void salidaBus(String id) throws InterruptedException{
        salidaB.lock();
        try {
            simulador.pausar();
            if("Madrid".equals(nombre)){
                simulador.modBusCiudadM(id);
            }else{
                simulador.modBusCiudadB(id);
            }
        } finally {
            salidaB.unlock();
        }
    }
    
    //Solicitudes, asignaciones y salidas de las puertas de embarque
    /**
     * Método que sirve para que un avión solicite una puerta de embarque
     * @param id: Id del avión que solicita la puerta de embarque
     * @return Object[]: Lista con dos campos, el primero el número de la puerta que ha sido asignada al avión, y el segúndo el gráfico en el que indicaremos la ocupación del avión
     * @throws java.lang.InterruptedException
     */
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
    /**
     * Método que sirve para solicitar la puerta de desembarque de un avión
     * @param id: Id del avión que solicita la puerta de desembarque
     * @return int: Número de la puerta de desembarque asignada al avión
     * @throws java.lang.InterruptedException
     */
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
    /**
     * Método que sirve para desocupar una puerta, ya sea de embarque o de desembarque
     * @param n: Número de la puerta que queremos desocupar
     * @throws java.lang.InterruptedException
     */
    public void liberarPuerta(int n) throws InterruptedException{
        puertas.lock();
        try {
            simulador.pausar();
            if(n > 0){
                puertaDesembarque.signalAll();
            }if(n < 5){
                puertaEmbarque.signalAll();
            }
            puertasEmbarque.set(n, null);
            actualizarPuertas(n, "");
        } finally {
            puertas.unlock();
        }
    }
    /**
     * Método que actualiza la información de las puertas en el simulador
     * @param puerta: Puerta que ha sido modificada, y que por tanto, requiere ser actualizada
     * @param id: Id del avión al que se ha asignado la puerta. En caso de que la modificación haga referencia a la liberación de la puerta, será null
     * @return Gráfico que utilizaremos para indicar la ocupación de un avión respecto a la capacidad de cada puerta
     * @throws java.lang.InterruptedException
     */
    public CircularProgressBar actualizarPuertas(int puerta, String id) throws InterruptedException{
        CircularProgressBar c;
        if("Madrid".equals(nombre)){
            c = simulador.modPuertasM(puerta, id);
        }else{
            c = simulador.modPuertasB(puerta, id);
        }
        return c; 
    }
    
    //Operaciones relacionadas con las pistas de despegue y aterrizaje
    //Gestiones internas de las pistas 
    /**
     * Método que sirve para solicitar una pista de despegue para un avión
     * @param id: Id del avión que solicita la puerta de despegue
     * @param pasajeros: Número de pasajeros que transporta el avión
     * @throws java.lang.InterruptedException
     */
    public void solPistaDespegue(String id, int pasajeros) throws InterruptedException{
        semPistas.acquire();
        simulador.pausar();
        lPistas.lock();
        int pista = pistas.indexOf(null);
        ocuparPista(pista, id);
        salidaRodaje(id);
        logger.registrarEvento("Avión " + id + " (" + pasajeros + " pasajeros) accede a pista " + pista + " para despegue. ");
        Thread.sleep(1000+(int)(Math.random()*2000));
        liberarPista(pista);
        //semPistas.release();
    }
    /**
     * Método que sirve para solicitar una pista de aterrizaje para un avión
     * @param a: Avión que solicita la pista de aterrizaje
     * @throws java.lang.InterruptedException
     */
    public void solPistaAterrizaje(Avion a) throws InterruptedException{
        boolean encontrada;
        lPistas.lock();
        encontrada = semPistas.tryAcquire();
        while(!encontrada){
            lPistas.unlock();
            Thread.sleep(1000+(int)(Math.random()*4000));
            lPistas.lock();
            encontrada = semPistas.tryAcquire();
        }
        simulador.pausar();
        int pista = pistas.indexOf(null);
        ocuparPista(pista, a.getId());
        logger.registrarEvento("Avión " + a.getId() + " (" + a.getOcupacion() + " pasajeros) accede a pista " + pista + " para aterrizar. ");
        if("Madrid".equals(nombre)){
            simulador.salirAerovBM(a);
        }else{
            simulador.salirAerovMB(a);
        }
        Thread.sleep(1000+(int)(Math.random()*4000));
        liberarPista(pista);
    }
    /**
     * Método que sirve para ocupar una pista una vez esta ha sido asignada
     * @param pista: Pista que ha sido asignada al avión
     * @param id: Id del avión que va a ocupar la pista
     * @throws java.lang.InterruptedException
     */
    public void ocuparPista(int pista, String id) throws InterruptedException{
        pistas.set(pista, id);
        actualizarPistas(pista, id);
        lPistas.unlock();
    }
    /**
     * Método que sirve para liberar una pista de aterrizaje o de despegue, dejándola libre para futuras operaciones
     * @param pista: Número de la pista que vamos a liberar
     * @throws java.lang.InterruptedException
     */
    public void liberarPista(int pista) throws InterruptedException{
        lPistas.lock();
        try {
            simulador.pausar();
            if(!"Cerrada".equals(pistas.get(pista))){
                pistas.set(pista, null);
                semPistas.release();
            }
            actualizarPistas(pista, null);
        } finally {
            lPistas.unlock();
        }
    }
    /**
     * Método que actualiza los datos de las pistas en el simulador
     * @param pista: Número de la pista que ha sido modificada y que por tanto hay que actualizar en el simulador
     * @param id: Id del avión que a ocupado la pista. En caso de que la pista quede libre, este valor será null 
     * @throws java.lang.InterruptedException 
     */
    public void actualizarPistas(int pista, String id) throws InterruptedException{
        if("Madrid".equals(nombre)){
            simulador.modPistasM(pista, id);
        }else{
            simulador.modPistasB(pista, id);
        }
    }

    //Solicitudes de apertura o cierre de pistas por parte del cliente
    /**
     * Método que cierra una pista de despegue/aterrizaje
     * @param pista: Número de la pista a cerrar
     * @throws java.lang.InterruptedException
     */
    public void cerrarPista(int pista) throws InterruptedException{
        lPistas.lock();
        try {
            if(pistas.get(pista-1)== null){
                semPistas.acquire();
            }
            pistas.set(pista-1, "Cerrada");
            logger.registrarEvento("La pista número " + pista + " de " + nombre + " ha sido cerrada. ");
        } finally {
            lPistas.unlock();
        }
    }
    /**
     * Método que abre una pista de despegue/aterrizaje
     * @param pista: Número de la pista a abrir
     */
    public void abrirPista(int pista){
        lPistas.lock();
        try {
            pistas.set(pista-1, null);
            if("Madrid".equals(nombre)){
                if(simulador.consultarPistaM(pista-1)){
                    semPistas.release();
                }
            }else{
                if(simulador.consultarPistaB(pista-1)){
                    semPistas.release();}}
            logger.registrarEvento("La pista número " + pista + " de " + nombre + " ha sido abierta. ");
        } finally {
            lPistas.unlock();
        }
    }
    
    //Llegada y salida de aviones al hangar
    /**
     * Método que sirve para indicar la llegada de un nuevo avión al hangar
     * @param id: Id del avión que llega al hangar
     * @throws java.lang.InterruptedException
     */
    public void llegadaHangar(String id) throws InterruptedException{
        lHangar.lock();
        try {
            simulador.pausar();
            hangar.add(id);
            actualizarHangar();
        } finally {
            lHangar.unlock();
        }
    }
    /**
     * Método que sirve para indicar que un avión ha salido del hangar
     * @param id: Id del avión que sale del hangar
     * @throws java.lang.InterruptedException
     */
    public void salidaHangar(String id) throws InterruptedException{
        lHangar.lock();
        try {
            simulador.pausar();
            hangar.remove(id);
            actualizarHangar();
        } finally {
            lHangar.unlock();
        }
    }
    /**
     * Método que sirve para actualizar los datos del hangar actual en el simulador
     * @throws java.lang.InterruptedException
     */
    public void actualizarHangar() throws InterruptedException{
        if("Madrid".equals(nombre)){
            simulador.modHangarM(hangar);
        }else{
            simulador.modHangarB(hangar);
        }
    }
    
    //Llegadas y salidas al área de rodaje
    /**
     * Método que sirve para indicar que un avión ha llegado al área de rodaje
     * @param id: Id del avión que acaba de llegar al área de rodaje
     * @throws java.lang.InterruptedException
     */
    public void llegadaRodaje(String id) throws InterruptedException{
        lRodaje.lock();
        try {
            simulador.pausar();
            rodaje.add(id);
            actualizarRodaje();
        } finally {
            lRodaje.unlock();
        }
    }
    /**
     * Método para indicar que un avión ha salido del área de rodaje
     * @param id: Id del avión que va a salir del área de rodaje
     * @throws java.lang.InterruptedException
     */
    public void salidaRodaje(String id) throws InterruptedException{
        lRodaje.lock();
        try {
            simulador.pausar();
            rodaje.remove(id);
            actualizarRodaje();
        } finally {
            lRodaje.unlock();
        }
    }
    /**
     * Método que sirve para actualizar los datos del área de rodaje en el simulador
     * @throws java.lang.InterruptedException
     */
    public void actualizarRodaje() throws InterruptedException{
        if("Madrid".equals(nombre)){
            simulador.modRodajeM(rodaje);
        }else{
            simulador.modRodajeB(rodaje);
        }
    }
    
    //Llegadas y salidas al área de estacionamiento
    /**
     * Método que sirve para indicar que un avión ha llegado al área de estacionamiento
     * @param id: Id del avión que acaba de llegar al área de estacionamiento
     * @throws java.lang.InterruptedException
     */
    public void llegadaEstacionamiento(String id) throws InterruptedException{
        lEstacionamiento.lock();
        try {
            simulador.pausar();
            estacionamiento.add(id);
            actualizarEstacionamiento();
        } finally {
            lEstacionamiento.unlock();
        }
    }
    /**
     * Método que sirve para indicar que un avión va a salir del área de estacionamiento
     * @param id: Id del avión que va a salir del área de estacionamiento
     * @throws java.lang.InterruptedException
     */
    public void salidaEstacionamiento(String id) throws InterruptedException{
        lEstacionamiento.lock();
        try {
            simulador.pausar();
            estacionamiento.remove(id);
            actualizarEstacionamiento();
        } finally {
            lEstacionamiento.unlock();
        }
    }
    /**
     * Método que sirve para actualizar los datos del área de estacionamiento en el simulador
     * @throws java.lang.InterruptedException
     */
    public void actualizarEstacionamiento() throws InterruptedException{
        if("Madrid".equals(nombre)){
            simulador.modEstacionamientoM(estacionamiento);
        }else{
            simulador.modEstacionamientoB(estacionamiento);
        }
    }

    //Gestión de las revisiones del taller
    /**
     * Método que sirve para indicar que un avión esta realizando una revisión rápida en el taller
     * @param id: Id del avión que acaba de llegar al taller para realizar la revisión rápida
     * @throws java.lang.InterruptedException
     */
    public void revisionRapida(String id) throws InterruptedException{
        semTaller.acquire();
        puertaTaller.lock();
        try {
            try {
                simulador.pausar();
                Thread.sleep(1000);
                taller.add(id);
                actualizarTaller();
                logger.registrarEvento("Avión " + id + " accede al taller para una revisión rápida. ");
            } finally {
                puertaTaller.unlock();
            }
            Thread.sleep(1000+(int)(Math.random()*4000));
            puertaTaller.lock();
            Thread.sleep(1000);
            taller.remove(id);
            actualizarTaller();
        } finally {
            puertaTaller.unlock();
        }
        semTaller.release();
    }
    /**
     * Método que sirve para indicar que un avión esta realizando una revisión profunda en el taller
     * @param id: Id del avión que acaba de llegar al taller para realizar una revisión profunda
     * @throws java.lang.InterruptedException
     */
    public void revisionProfunda(String id) throws InterruptedException{
        semTaller.acquire();
        simulador.pausar();
        puertaTaller.lock();
        try {
            try {
                Thread.sleep(1000);
                taller.add(id);
                actualizarTaller();
                logger.registrarEvento("Avión " + id + " accede al taller para una revisión profunda. ");
            } finally {
                puertaTaller.unlock();
            }
            Thread.sleep(5000+(int)(Math.random()*5000));
            puertaTaller.lock();
            Thread.sleep(1000);
            taller.remove(id);
            actualizarTaller();
        } finally {
            puertaTaller.unlock();
        }
        semTaller.release();
    }
    /**
     * Método que sirve para actualizar los datos de los aviones que hay actualmente en el taller en el simulador
     * @throws java.lang.InterruptedException
     */
    public void actualizarTaller() throws InterruptedException{
        if("Madrid".equals(nombre)){
            simulador.modTallerM(taller);
        }else{
            simulador.modTallerB(taller);
        }
    }
    
    //Vuelo de aviones o uso de aerovías
    /**
     * Método usado para indicar que un avión esta usando una aerovía para volar hacia el otro aeropuerto
     * @param a: Avión que esta volando
     * @throws java.lang.InterruptedException
     */
    public void volar(Avion a) throws InterruptedException{
        simulador.pausar();
        if("Madrid".equals(nombre)){
            simulador.usoAeroviaMB(a);
        }else{
            simulador.usoAeroviaBM(a);
        }
    }
}
