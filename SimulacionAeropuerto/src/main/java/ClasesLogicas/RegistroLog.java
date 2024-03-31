/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClasesLogicas;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author isaba
 */
public class RegistroLog {
    private static RegistroLog instance;
    private BufferedWriter writer;

    private RegistroLog() {
        try {
            writer = new BufferedWriter(new FileWriter("evolucionAeropuerto.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized RegistroLog getInstance() {
        if (instance == null) {
            instance = new RegistroLog();
        }
        return instance;
    }

    public synchronized void registrarEvento(String evento) {
        LocalDateTime fecha = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String rFecha = fecha.format(formatter);
        String registro = rFecha + " - " + evento;
        try {
            writer.write(registro);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public synchronized void close() {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
