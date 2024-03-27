package ClasesLogicas;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author isaba
 */

import ClasesLogicas.Aeropuerto;
import Interfaz.InterfazSimulador;
import java.util.LinkedList;
import java.util.Random;

public class Autobus implements Runnable{
    Random random = new Random();
    
    private String id;
    private int ocupacion;
    private Aeropuerto aero;
    private InterfazSimulador simulador;
    
    public Autobus(String cod, Aeropuerto aeropuerto, InterfazSimulador s){
        this.id = "B-" + cod;
        this.ocupacion = 0;
        this.aero = aeropuerto;
        this.simulador = s;
    }
    public void run() {
        while(true){
            try{
                aero.salidaBus(id);
                Thread.sleep(2000+(int)(Math.random()*3000));
                ocupacion = random.nextInt(51);
                Thread.sleep(5000+(int)(Math.random()*5000));
                aero.llegadaPasajeros(ocupacion);
                aero.llegadaBus(id);
                Thread.sleep(2000+(int)(Math.random()*3000));
                ocupacion = aero.salidaPasajeros(random.nextInt(51));
                Thread.sleep(5000+(int)(Math.random()*5000));
            }catch(InterruptedException e){
                break;
            }
        }
    }
}
