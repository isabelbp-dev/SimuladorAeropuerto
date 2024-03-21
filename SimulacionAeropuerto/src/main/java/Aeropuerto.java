/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author isaba
 */
public class Aeropuerto {
    private int ocupacion;
    public Aeropuerto(){
        ocupacion = 0;
    }
    
    public synchronized void llegadaPasajeros(int num){
        ocupacion += num;
        System.out.println("Hay: " + String.valueOf(ocupacion) + " pasajeros");
    }
    
    public synchronized int salidaPasajeros(int num){
        if(ocupacion >= num){
            ocupacion -= num;
        }else{
            num = ocupacion;
            ocupacion = 0;
        }
        System.out.println("Hay: " + String.valueOf(ocupacion) + " pasajeros");
        return num;
    }
}
