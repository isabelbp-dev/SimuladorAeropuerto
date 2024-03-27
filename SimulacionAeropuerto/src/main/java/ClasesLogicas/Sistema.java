package ClasesLogicas;


import ClasesLogicas.GeneradorAviones;
import ClasesLogicas.GeneradorAutobus;
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
public class Sistema {
    public static void main(String[] x){
        InterfazSimulador simulador = new InterfazSimulador();
        simulador.setVisible(true);
        Aeropuerto aeroM = new Aeropuerto(simulador, "Madrid");
        Aeropuerto aeroB = new Aeropuerto(simulador, "Barcelona");
        Thread gAviones = new Thread(new GeneradorAviones(aeroM, aeroB));
        Thread gBuses = new Thread(new GeneradorAutobus(aeroM, aeroB, simulador));
        gBuses.start();
        gAviones.start();
    }
}
