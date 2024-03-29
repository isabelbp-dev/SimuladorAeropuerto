package ClasesLogicas;
import ClasesLogicas.Avion;
import ClasesLogicas.Aeropuerto;

/**
 *
 * @author isaba
 */

public class GeneradorAviones implements Runnable{
    //Atributos
    private Aeropuerto aero1;
    private Aeropuerto aero2;
    
    //Constructor
    public GeneradorAviones(Aeropuerto aeropuerto1, Aeropuerto aeropuerto2){
        this.aero1 = aeropuerto1;
        this.aero2= aeropuerto2;
    }
    
    //Ciclo de vida del generador
    public void run() {
        for(int i = 0; i < 8000; i++){
            try{
                if(i%2 == 0){
                    Thread avion = new Thread(new Avion(String.format("%04d", i), aero1));
                    avion.start();
                }else{
                    Thread avion = new Thread(new Avion(String.format("%04d", i), aero2));
                    avion.start();
                }
                Thread.sleep(1000+(int)(Math.random()*2000));
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
