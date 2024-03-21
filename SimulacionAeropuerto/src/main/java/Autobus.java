/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author isaba
 */

import java.util.LinkedList;
import java.util.Random;

public class Autobus implements Runnable{
    Random random = new Random();
    
    private String id;
    private int ocupacion;
    private int estaAeropuerto;
    private Aeropuerto aero;
    
    public Autobus(String cod, Aeropuerto aeropuerto){
        this.id = "B-" + cod;
        this.ocupacion = 0;
        this.estaAeropuerto = 0; 
        this.aero = aeropuerto;
        System.out.println("Se ha creado el bus:" + id);
    }
    public void run() {
        while(true){
            try{
                System.out.println("Subir pasajeros");
                Thread.sleep(2000+(int)(Math.random()*3000));
                ocupacion = random.nextInt(51);
                System.out.println("La ocupacion del bus es: " + String.valueOf(ocupacion));
                Thread.sleep(5000+(int)(Math.random()*5000));
                System.out.println("Ha llegado al aeropuerto.");
                aero.llegadaPasajeros(ocupacion);
                Thread.sleep(2000+(int)(Math.random()*3000));
                ocupacion = aero.salidaPasajeros(random.nextInt(51));
                System.out.println("Salen de aeropuerto. ");
                Thread.sleep(5000+(int)(Math.random()*5000));
                System.out.println("Llega ciudad. ");
            }catch(InterruptedException e){
                System.out.println("Ha sido interrumpido.");
                break;
            }
        }
    }
}
