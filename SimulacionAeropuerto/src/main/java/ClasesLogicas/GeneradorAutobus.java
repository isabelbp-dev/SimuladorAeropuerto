package ClasesLogicas;
import Interfaz.InterfazSimulador;

/**
 * Clase encargada de generar los autobuses del sistema
 * @author Isabel Barquilla
 */

public class GeneradorAutobus implements Runnable{
    RegistroLog logger = RegistroLog.getInstance();
    
    //Atributos
    private Aeropuerto aeropuerto1;
    private Aeropuerto aeropuerto2;
    private InterfazSimulador simulador;

    /**
     * Constructor del generador de autobuses
     * @param aero1: Aeropuerto de Madrid
     * @param aero2: Aeropuerto de Barcelona
     * @param s: Instancia del simulador del programa
     */
    public GeneradorAutobus(Aeropuerto aero1, Aeropuerto aero2, InterfazSimulador s){
        this.aeropuerto1 = aero1;
        this.aeropuerto2 = aero2;
        this.simulador = s;
    }
    
    /**
     * Ciclo de vida del generador de autobuses
     */
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
                Thread.sleep(500+(int)(Math.random()*500));
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }   
}
