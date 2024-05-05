package ClasesLogicas;

/**
 * Clase encargada de generar los autobuses del sistema
 * @author Isabel Barquilla y Sandra Familiar
 */

public class GeneradorAutobus implements Runnable{
    RegistroLog logger = RegistroLog.getInstance();
    
    //Atributos
    private final Aeropuerto aeropuerto1;
    private final Aeropuerto aeropuerto2;

    /**
     * Constructor del generador de autobuses
     * @param aero1: Aeropuerto de Madrid
     * @param aero2: Aeropuerto de Barcelona
     */
    public GeneradorAutobus(Aeropuerto aero1, Aeropuerto aero2){
        this.aeropuerto1 = aero1;
        this.aeropuerto2 = aero2;
    }
    
    /**
     * Ciclo de vida del generador de autobuses
     */
    @Override
    public void run() {
        for(int i = 0; i < 4000; i++){
            try{
                if(i%2 == 0){
                    Thread bus = new Thread(new Autobus(String.format("%04d", i), aeropuerto1));
                    bus.start();
                }else{
                    Thread bus = new Thread(new Autobus(String.format("%04d", i), aeropuerto2));
                    bus.start();
                }
                Thread.sleep(500+(int)(Math.random()*500));
            }catch(InterruptedException e){}
        }
    }   
}
