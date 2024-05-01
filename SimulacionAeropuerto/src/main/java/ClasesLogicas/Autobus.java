package ClasesLogicas;
import java.util.Random;
/**
 * Clase que representa a los autobuses del simulador
 * @author Isabel Barquilla y Sandra Familiar
 */

public class Autobus implements Runnable{
    RegistroLog logger = RegistroLog.getInstance();
    private final Random random = new Random();
    
    //Atributos 
    private final String id;
    private int ocupacion;
    private final Aeropuerto aero;
    
    /**
     * Constructor de la clase Autobús
     * @param cod: Codigo usado para la creación del ID del bús, correspondiente al número de bus creado
     * @param aeropuerto: Aeropuerto al que estará asociado el autobús
     */
    public Autobus(String cod, Aeropuerto aeropuerto){
        this.id = "B-" + cod;
        this.ocupacion = 0;
        this.aero = aeropuerto;
        logger.registrarEvento("Bus " + id + " es creado. ");
    }
    
    /**
     * Ciclo de vida del bús
     */
    @Override
    public void run() {
        while(true){
            try{
                aero.salidaBus(id);
                Thread.sleep(2000+(int)(Math.random()*3000));
                ocupacion = random.nextInt(51);
                logger.registrarEvento("Bus " + id + " ha salido de " + aero.getNombre() + " ciudad con "+ ocupacion + " pasajeros. ");
                Thread.sleep(5000+(int)(Math.random()*5000));
                aero.llegadaPasajeros(ocupacion);
                aero.llegadaBus(id);
                logger.registrarEvento("Bus " + id + " ha dejado " + ocupacion + " pasajeros en el aeropuerto de " + aero.getNombre() + ". ");
                Thread.sleep(2000+(int)(Math.random()*3000));
                ocupacion = aero.salidaPasajeros(random.nextInt(51));
                logger.registrarEvento("Bus " + id + " ha salido del aeropuerto de " + aero.getNombre() + " con " + ocupacion + " pasajeros. ");
                Thread.sleep(5000+(int)(Math.random()*5000));
                logger.registrarEvento("Bus " + id + " ha llegado a la ciudad de " + aero.getNombre() + " y ha dejado a " + ocupacion + " pasajeros. ");
            }catch(InterruptedException e){
                break;
            }
        }
    }
}
