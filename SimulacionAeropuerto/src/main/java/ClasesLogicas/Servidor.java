package ClasesLogicas;

import Interfaz.InterfazSimulador;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase servidor del programa, empleada para la programación distribuida del mismo
 * @author Isabel Barquilla 
 */
public class Servidor implements Runnable{
    private InterfazSimulador simulador; 
    private ServerSocket servidor; 
    private Socket conexion;
    private DataOutputStream salida;
    private DataInputStream entrada; 

    /**
     * Constructor del servidor
     * @param s: Clase simulador del programa
     */
    public Servidor(InterfazSimulador s){
        this.simulador = s;}
    
    /**
     * Ciclo de vida del servidor
     */
    @Override
    public void run() {
    try {
        servidor = new ServerSocket(5000);
        while (true) {
            Socket conexion = null;
            DataInputStream entrada = null;
            DataOutputStream salida = null;

            try {
                conexion = servidor.accept(); // Aceptar conexiones entrantes
                entrada = new DataInputStream(conexion.getInputStream());
                salida = new DataOutputStream(conexion.getOutputStream());
                String datos;
                while (true) {
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
                break;
            } finally {
                if (conexion != null) {try { conexion.close(); } catch (IOException e) {}}
                if (entrada != null) {try { entrada.close(); } catch (IOException e) {}}
                if (salida != null) {try { salida.close(); } catch (IOException e) {}}
            }}
    } catch (IOException e) {
        System.out.println("Error al inicializar el servidor: " + e.getMessage());
    } finally {
        try {
            if (servidor != null) servidor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
}}}

