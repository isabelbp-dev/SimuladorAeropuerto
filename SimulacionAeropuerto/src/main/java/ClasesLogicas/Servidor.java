/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClasesLogicas;

import Interfaz.InterfazSimulador;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author isaba
 */
import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor implements Runnable{
    private InterfazSimulador simulador; 
    private ServerSocket servidor; 
    private Socket conexion;
    private DataOutputStream salida;
    private DataInputStream entrada; 

    public Servidor(InterfazSimulador s){
        this.simulador = s; 
    }
    
    @Override
    public void run() {
    try {
        servidor = new ServerSocket(5000);
        System.out.println("Servidor iniciado y escuchando en el puerto 5000");

        while (true) {
            Socket conexion = null;
            DataInputStream entrada = null;
            DataOutputStream salida = null;

            try {
                conexion = servidor.accept(); // Aceptar conexiones entrantes
                entrada = new DataInputStream(conexion.getInputStream());
                salida = new DataOutputStream(conexion.getOutputStream());
                String datos;
                while (true) {  // Cambiado para asegurarse de que el bucle sólo termina por una excepción
                    datos = simulador.getPasajerosM() + ";" + simulador.getPasajerosB() +";"
                            + simulador.getHangarM().size() + ";" + simulador.getHangarB().size() + ";" 
                            + simulador.getTallerM().size() + ";" + simulador.getTallerB().size() + ";"
                            + simulador.getEstacionamientoM().size() + ";" + simulador.getEstacionamientoB().size() + ";"
                            + simulador.getRodajeM().size() + ";" + simulador.getRodajeB().size() + ";"
                            + simulador.getAeroviaMB() + " ;" + simulador.getAeroviaBM() +" ";
                    salida.writeUTF(datos);
                    Thread.sleep(30);
                    if(entrada.available()>0){
                        String mensaje = entrada.readUTF();
                        int pista = Integer.parseInt(mensaje.split(";")[1]);
                        if("Cerrar".equals(mensaje.split(";")[0])){
                            simulador.cerrarPista(pista);
                        }else{
                            simulador.abrirPista(pista);
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Cliente desconectado o error en la conexión: " + e.getMessage());
            } catch (InterruptedException ex) {
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, "Error en el hilo del servidor", ex);
                break; // Salir del bucle si el servidor necesita detenerse completamente
            } finally {
                if (conexion != null) {
                    try { conexion.close(); } catch (IOException e) { /* Ignorar */ }
                }
                if (entrada != null) {
                    try { entrada.close(); } catch (IOException e) { /* Ignorar */ }
                }
                if (salida != null) {
                    try { salida.close(); } catch (IOException e) { /* Ignorar */ }
                }
            }
        }
    } catch (IOException e) {
        System.out.println("Error al inicializar el servidor: " + e.getMessage());
    } finally {
        try {
            if (servidor != null) servidor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
}

