package ClasesLogicas;
import ClasesLogicas.Aeropuerto;
import static java.lang.Math.*;
import java.util.Random; 
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author isaba
 */

public class Avion implements Runnable{
    //Atributos
    Random random = new Random();
    private String id;
    private int capacidad;
    private int ocupacion;
    private Aeropuerto aero;
    private int numVuelos;
    
    //Constructor
    public Avion(String cod, Aeropuerto aeropuerto){
        aero = aeropuerto;
        this.capacidad = (int) ((Math.random()*200)+100);
        this.ocupacion = 0;
        char letra1 = (char) ('A' + random.nextInt(26));
        char letra2 = (char) ('A' + random.nextInt(26));
        this.id = Character.toString(letra1) + Character.toString(letra2)+ "-"+cod;
        this.numVuelos = 0;
    }
    
    //Métodos getter y setter
    public String getId(){
        return id;
    }
    public void setAeropuerto(Aeropuerto a){
        this.aero = a;
    }
    
    //Salida del avion
    public void salidaAvion() throws InterruptedException{
        aero.llegadaEstacionamiento(id);//Llegamos estacionamiento
        int puerta = aero.solPuertaEmbarque(id);//Solicitamos puerta embarque
        aero.salidaEstacionamiento(id);//salimos estacionamiento, vamos a puerta
        int ocupacion = aero.salidaPasajeros(capacidad);//Esperamos a que se suban los pasajeros
        Thread.sleep(1000+(int)(Math.random()*2000));
        int i = 0;
        while((i <2) && (ocupacion < capacidad)){
            Thread.sleep(1000+(int)(Math.random()*4000));
            ocupacion += aero.salidaPasajeros(capacidad - ocupacion);
            Thread.sleep(1000+(int)(Math.random()*2000));
        }
        aero.liberarPuerta(puerta);//Salimos de la puerta de embarque
        aero.llegadaRodaje(id);//Llegamos a rodaje hasta tener pista
        Thread.sleep(1000+(int)(Math.random()*4000));//Realizamos comprobaciones
        aero.solPistaDespegue(id);
    }
    //Llegada del avion
    public void llegadaAvion() throws InterruptedException{
        aero.solPistaAterrizaje(id);//Solicitan pista de aterrizaje
        aero.llegadaRodaje(id);//Después de aterrizar, van al área de rodaje y esperan a una puerta de desembarque
        int puerta = aero.solPuertaDesembarque(id);//van a la puerta de desembarque
        Thread.sleep(3000+(int)(Math.random()*2000));//tiempo que tardan en ir a la puerta
        aero.salidaRodaje(id);//salen del área de rodaje
        Thread.sleep(1000+(int)(Math.random()*4000));//bajan a los pasajeros del avion
        aero.llegadaPasajeros(ocupacion);//llegan pasajeros a aeropuerto
        ocupacion = 0;//se vacia avion 
        aero.liberarPuerta(puerta);//salimos de la puerta de desembarque
        aero.llegadaEstacionamiento(id);//llegamos a la zona de estacionamiento
        Thread.sleep(1000+(int)(Math.random()*4000));//realizamos comprobaciones 
    }
    //Ciclo de vida del avión
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
                    Thread.sleep(15000+(int)(Math.random()*15000));
                    aero.salidaHangar(id);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Avion.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
}
