package ClasesLogicas;


import ClasesLogicas.Autobus;
import ClasesLogicas.Aeropuerto;
import Interfaz.InterfazSimulador;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author isaba
 */
public class GeneradorAutobus implements Runnable{
    private Aeropuerto aeropuerto1;
    private Aeropuerto aeropuerto2;
    private InterfazSimulador simulador; 
    
    public GeneradorAutobus(Aeropuerto aero1, Aeropuerto aero2, InterfazSimulador s){
        this.aeropuerto1 = aero1;
        this.aeropuerto2 = aero2;
        this.simulador = s;
    }
    
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
