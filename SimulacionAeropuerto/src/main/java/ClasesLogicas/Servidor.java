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
public class Servidor {
    private InterfazSimulador simulador; 
    private static ServerSocket servidor; 
    private static Socket conexion;
    private static DataOutputStream salida;
    private static DataInputStream entrada; 

    public Servidor(InterfazSimulador s){
        this.simulador = s; 
        try{
            while(true){
                servidor = new ServerSocket(5000);
                conexion = servidor.accept();
                entrada = new DataInputStream(conexion.getInputStream());
                salida = new DataOutputStream(conexion.getOutputStream());}
        }catch(IOException e){
            System.out.println("Ha fallado la inicialización del servidor. ");
        }
        
    }
}
