/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author isaba
 */
public class GeneradorAviones implements Runnable{
    private Aeropuerto aero1;
    private Aeropuerto aero2;
    public GeneradorAviones(Aeropuerto aeropuerto1, Aeropuerto aeropuerto2){
        this.aero1 = aeropuerto1;
        this.aero2= aeropuerto2;
    }
    
    public void run() {
        for(int i = 0; i < 8000; i++){
            try{
                Thread avion = new Thread(new Avion(String.format("%04d", i), aero1));
                avion.start();
                Thread.sleep(1000+(int)(Math.random()*2000));
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
