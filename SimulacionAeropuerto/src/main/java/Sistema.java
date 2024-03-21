/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author isaba
 */
public class Sistema {
    public static void main(String[] x){
        Aeropuerto aeroM = new Aeropuerto();
        Aeropuerto aeroB = new Aeropuerto();
        Thread gAviones = new Thread(new GeneradorAviones(aeroM, aeroB));
        Thread gBuses = new Thread(new GeneradorAutobus(aeroM, aeroB));
        gBuses.start();
        gAviones.start();
    }
}
