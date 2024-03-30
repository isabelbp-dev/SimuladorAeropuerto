package ClasesLogicas;
import ClasesLogicas.Autobus;
import ClasesLogicas.Aeropuerto;
import Interfaz.InterfazSimulador;

/**
 *
 * @author isaba
 */

public class GeneradorAutobus implements Runnable{
    RegistroLog logger = RegistroLog.getInstance();
    
    //Atributos
    private Aeropuerto aeropuerto1;
    private Aeropuerto aeropuerto2;
    private InterfazSimulador simulador; 
    
    //Constructor
    public GeneradorAutobus(Aeropuerto aero1, Aeropuerto aero2, InterfazSimulador s){
        this.aeropuerto1 = aero1;
        this.aeropuerto2 = aero2;
        this.simulador = s;
    }
    
    //Ciclo de vida del generador
    public void run() {
        for(int i = 0; i < 4000; i++){
            try{
                if(i%2 == 0){
                    Thread bus = new Thread(new Autobus(String.format("%04d", i), aeropuerto1, simulador));
                    bus.start();
                }else{
                    Thread bus = new Thread(new Autobus(String.format("%04d", i), aeropuerto2, simulador));
                    bus.start();
                }
                logger.registrarEvento("Bus " + String.format("%04d", i) + " es creado. ");
                Thread.sleep(500+(int)(Math.random()*500));
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }   
}
