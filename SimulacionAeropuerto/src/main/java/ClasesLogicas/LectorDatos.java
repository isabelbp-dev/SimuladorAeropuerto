package ClasesLogicas;

import Interfaz.MenuAdministrador;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Lector de datos usado por el cliente para actualizar los datos proporcionados por el servidor
 * @author Isabel Barquilla Poyato
 */
public class LectorDatos implements Runnable{
    private static DataInputStream entrada;
    private static DataOutputStream salida; 
    private static MenuAdministrador menu;
    
    /**
     * Constructor del lector de datos
     * @param e: DataInputStream usado para la lectura de los datos proporcionados por el servidor
     * @param m: Menú del cliente
     */
    public LectorDatos(DataInputStream e, MenuAdministrador m){
        this.entrada = e;
        this.menu = m;
    }
    
    /**
    * Ciclo de vida del lector
    */
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
