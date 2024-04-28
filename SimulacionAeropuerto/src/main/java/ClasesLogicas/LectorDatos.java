/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClasesLogicas;

import Interfaz.MenuAdministrador;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author isaba
 */
public class LectorDatos implements Runnable{
    private static DataInputStream entrada;
    private static DataOutputStream salida; 
    private static MenuAdministrador menu;
    
    public LectorDatos(DataInputStream e, DataOutputStream s, MenuAdministrador m){
        this.entrada = e;
        this.salida = s;
        this.menu = m;
    }
    @Override
    public void run() {
        while(true){
            try{
                String mensaje = entrada.readUTF();
                String[] datos = mensaje.split(";");
                menu.modPasajerosM(datos[0]);
                menu.modPasajerosB(datos[1]);
                menu.modHangarM(datos[2]);
                menu.modHangarB(datos[3]);
                menu.modTallerM(datos[4]);
                menu.modTallerB(datos[5]);
                menu.modEstacionamientoM(datos[6]);
                menu.modEstacionamientoB(datos[7]);
                menu.modRodajeM(datos[8]);
                menu.modRodajeB(datos[9]);
                menu.modAerovMB(datos[10]);
                menu.modAerovBM(datos[11]);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(menu, "El servidor no esta activo, se procederá a cerrar el cliente... ");
                System.exit(0);
            }
        }
    }
}
