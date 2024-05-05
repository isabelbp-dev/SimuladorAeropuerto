package ClasesLogicas;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase empleada para el registro log del programa
 * @author Isabel Barquilla y Sandra Familiar
 */
public class RegistroLog {
    private static RegistroLog instance;
    private BufferedWriter writer;

    /**
     * Constructor de la clase
     */
    private RegistroLog() {
        try {
            writer = new BufferedWriter(new FileWriter("evolucionAeropuerto.txt"));
        } catch (IOException e) {}
    }

    //Métodos propios de la clase
    /**
     * Método para obtener la instancia única de la clase
     * @return RegistroLog: Devuelve la instancia de la clase para su posterior uso
     */
    public static synchronized RegistroLog getInstance() {
        if (instance == null) {
            instance = new RegistroLog();
        }
        return instance;
    }
    /**
     * Método para el registro de eventos en el log
     * @param evento: String con el evento a registrar
     */
    public synchronized void registrarEvento(String evento) {
        LocalDateTime fecha = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String rFecha = fecha.format(formatter);
        String registro = rFecha + " - " + evento;
        try {
            writer.write(registro);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {}
    }
    /**
     * Método para cerrar el buffer de la clase
     */
    public synchronized void close() {
        try {
            writer.close();
        } catch (IOException e) {}
    }
}
