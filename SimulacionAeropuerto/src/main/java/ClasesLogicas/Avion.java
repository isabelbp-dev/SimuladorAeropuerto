package ClasesLogicas;


import ClasesLogicas.Aeropuerto;
import static java.lang.Math.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author isaba
 */
import java.util.Random; 
import java.util.logging.Level;
import java.util.logging.Logger;

public class Avion implements Runnable{
    Random random = new Random();
    private String id;
    private int capacidad;
    private int ocupacion;
    private Aeropuerto aero;
    
    public Avion(String cod, Aeropuerto aeropuerto){
        aero = aeropuerto;
        this.capacidad = (int) ((Math.random()*200)+100);
        this.ocupacion = 0;
        char letra1 = (char) ('A' + random.nextInt(26));
        char letra2 = (char) ('A' + random.nextInt(26));
        this.id = Character.toString(letra1) + Character.toString(letra2)+ "-"+cod;
    }
    
    public String getId(){
        return id;
    }
    
    public void setAeropuerto(Aeropuerto a){
        this.aero = a;
    }
    public void run() {
        while(true){
            try {
                aero.llegadaHangar(id);
                int puerta = aero.solPuertaEmbarque(id);
                aero.salidaHangar(id);
                int ocupacion = aero.salidaPasajeros(capacidad);
                Thread.sleep(1000+(int)(Math.random()*2000));
                int i = 0;
                while((i <2) && (ocupacion < capacidad)){
                    Thread.sleep(1000+(int)(Math.random()*4000));
                    ocupacion += aero.salidaPasajeros(capacidad - ocupacion);
                    Thread.sleep(1000+(int)(Math.random()*2000));
                }
                aero.liberarPuerta(puerta);
                Thread.sleep(1000+(int)(Math.random()*4000));
                aero.solPistaDespegue(id);
                aero.volar(this);
                aero.solPistaAterrizaje(id);
                aero.llegadaRodaje(id);
                puerta = aero.solPuertaDesembarque(id);
                Thread.sleep(3000+(int)(Math.random()*2000));
                aero.salidaRodaje(id);
                Thread.sleep(1000+(int)(Math.random()*4000));
                aero.llegadaPasajeros(ocupacion);
                ocupacion = 0;
            } catch (InterruptedException ex) {
                Logger.getLogger(Avion.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
}
