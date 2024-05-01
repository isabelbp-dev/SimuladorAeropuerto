package ClasesLogicas;
import Renders.CircularProgressBar;
import java.util.Random; 
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que representa a los aviones del simulador
 * @author Isabel Barquilla y Sandra Familiar
 */

public class Avion implements Runnable{
    RegistroLog logger = RegistroLog.getInstance();
    
    //Atributos
    private final Random random = new Random();
    private final String id;
    private final int capacidad;
    private int ocupacion;
    private Aeropuerto aero;
    private int numVuelos;
    
    /**
     * Constructor de la clase avión
     * @param cod: Número del avión
     * @param aeropuerto: Aeropuerto al que pertenece el avión
     */
    public Avion(String cod, Aeropuerto aeropuerto){
        aero = aeropuerto;
        this.capacidad = (int) ((Math.random()*200)+100);
        this.ocupacion = 0;
        char letra1 = (char) ('A' + random.nextInt(26));
        char letra2 = (char) ('A' + random.nextInt(26));
        this.id = Character.toString(letra1) + Character.toString(letra2)+ "-"+cod;
        this.numVuelos = 0;
        logger.registrarEvento("Avion " + id + " es creado. ");
    }
    
    //Métodos getter y setter
    /**
     * Método que devuelve el identificador del avión
     * @return String: Devuelve el Id del avión
     */
    public String getId(){
        return id;
    }
    /**
     * Método que devuelve la ocupación actual del avión
     * @return int: Número de pasajeros que hay en el avión
     */
    public int getOcupacion(){
        return ocupacion;
    }
    /**
     * Método que devuelve la capacidad máxima del avión
     * @return int: Número de pasajeros máximo del avión
     */
    public int getCapacidad(){
        return capacidad;
    }
    /**
     * Método que modifica el aeropuerto al que esta asociado el avión
     * @param a: Aeropuerto al que queremos asociar el avión
     */
    public void setAeropuerto(Aeropuerto a){
        this.aero = a;
    }
    
    //Métodos propios del avión
    /**
     * Método que simula la salida del avión, desde que llega al área de estacionamiento hasta que sale de la pista de despegue
     * @throws java.lang.InterruptedException
     */
    public void salidaAvion() throws InterruptedException{
        aero.llegadaEstacionamiento(id);//Llegamos estacionamiento
        logger.registrarEvento("Avion " + id + " ha llegado al area de estacionamiento. ");
        Object[] resultado = aero.solPuertaEmbarque(id);//Solicitamos puerta embarque
        int puerta = (int) resultado[0];
        logger.registrarEvento("Avion " + id + " accede a la puerta de embarque " + (puerta+1) + " para esperar pasajeros. ");
        CircularProgressBar c = (CircularProgressBar) resultado[1];
        c.setProgress(Math.max(((ocupacion/capacidad)*100),5));
        aero.salidaEstacionamiento(id);//salimos estacionamiento, vamos a puerta
        this.ocupacion = aero.salidaPasajeros(capacidad);//Esperamos a que se suban los pasajeros
        c.setProgress(Math.max(((ocupacion/capacidad)*100),5));
        Thread.sleep(1000+(int)(Math.random()*2000));
        int i = 0;
        while((i <2) && (ocupacion < capacidad)){
            Thread.sleep(1000+(int)(Math.random()*4000));
            this.ocupacion += aero.salidaPasajeros(capacidad - ocupacion);
            c.setProgress(Math.max((int)(ocupacion * 100.0) / capacidad, 5));
            Thread.sleep(1000+(int)(Math.random()*2000));
            i++;
        }
        aero.liberarPuerta(puerta);//Salimos de la puerta de embarque
        c.setProgress(0);
        aero.llegadaRodaje(id);//Llegamos a rodaje hasta tener pista
        logger.registrarEvento("Avion " + id +" (" + ocupacion +"/" + capacidad + " pasajeros) accede al área de rodaje en espera de una pista de despegue. ");
        Thread.sleep(1000+(int)(Math.random()*4000));//Realizamos comprobaciones
        aero.solPistaDespegue(id, ocupacion);
    }
    /**
     * Método que simula la llegada del avión al aeropuerto, desde la solicitud de la pista de aterrizaje hasta el desembarque de los pasajeros
     * @throws java.lang.InterruptedException
     */
    public void llegadaAvion() throws InterruptedException{
        aero.solPistaAterrizaje(this);//Solicitan pista de aterrizaje
        aero.llegadaRodaje(id);//Después de aterrizar, van al área de rodaje y esperan a una puerta de desembarque
        logger.registrarEvento("Avion " + id + " llega al área de rodaje en espera de una puerta para desembarcar. ");
        int puerta = aero.solPuertaDesembarque(id);//van a la puerta de desembarque
        Thread.sleep(3000+(int)(Math.random()*2000));//tiempo que tardan en ir a la puerta
        aero.salidaRodaje(id);//salen del área de rodaje
        logger.registrarEvento("Avión " + id + " accede a puerta de embarque " + puerta + " para desembarcar a " + ocupacion + " pasajeros. ");
        Thread.sleep(1000+(int)(Math.random()*4000));//bajan a los pasajeros del avion
        aero.llegadaPasajeros(ocupacion);//llegan pasajeros a aeropuerto
        this.ocupacion = 0;//se vacia avion 
        aero.liberarPuerta(puerta);//salimos de la puerta de desembarque
        aero.llegadaEstacionamiento(id);//llegamos a la zona de estacionamiento
        logger.registrarEvento("Avión " + id + " llega al área de estacionamiento tras desembarcar a los pasajeros. ");
        Thread.sleep(1000+(int)(Math.random()*4000));//realizamos comprobaciones 
    }
    /**
     * Ciclo de vida del avión
     */
    @Override
    public void run() {
        while(true){
            try {
                salidaAvion();
                aero.volar(this);
                numVuelos += 1;
                llegadaAvion();
                if(numVuelos == 15){
                    aero.revisionProfunda(id);
                    numVuelos = 0;
                }else{
                    aero.revisionRapida(id);
                }
                boolean irHangar = random.nextBoolean();
                if(irHangar){
                    aero.llegadaHangar(id);
                    logger.registrarEvento("Avión " + id + " llega al hangar para reposar. ");
                    Thread.sleep(15000+(int)(Math.random()*15000));
                    aero.salidaHangar(id);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Avion.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
}
