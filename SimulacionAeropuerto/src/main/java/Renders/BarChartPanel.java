/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Renders;

/**
 *
 * @author isaba
 */

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class BarChartPanel extends JPanel {
    private Map<String, Integer> valores;
    private int valorMaximo;

    public BarChartPanel(Map<String, Integer> valores) {
        this.valores = valores;
        this.valorMaximo = valores.values().stream().max(Integer::compareTo).orElse(0);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        dibujarGraficoDeBarras(g);
    }

    private void dibujarGraficoDeBarras(Graphics g) {
        int ancho = getWidth();
        int alto = getHeight();
        int margen = 25;
        int margenDerecho = 75; // Margen derecho adicional
        int margenEtiquetas = 50;
        int numeroBarras = valores.size();
        int anchoBarra = (ancho - (2 * margen) - margenEtiquetas - margenDerecho) / numeroBarras;
        int alturaMaxBarra = alto - 2 * margen;

        int posX = margen + margenEtiquetas;
        for (Map.Entry<String, Integer> entrada : valores.entrySet()) {
            int alturaBarra = (int) (((double) entrada.getValue() / valorMaximo) * alturaMaxBarra);
            int posY = alto - margen - alturaBarra;

            g.setColor(Color.darkGray);
            g.fillRect(posX, posY, anchoBarra, alturaBarra);

            g.setColor(Color.BLACK);
            String etiqueta = entrada.getKey();
            g.drawString(etiqueta, posX + (anchoBarra / 2) - g.getFontMetrics().stringWidth(etiqueta) / 2, alto - margen + 20);

            String valor = entrada.getValue().toString();
            g.drawString(valor, posX + (anchoBarra / 2) - g.getFontMetrics().stringWidth(valor) / 2, posY - 5);

            posX += anchoBarra + margen;
        }
    }

//    private Color obtenerColorBarra() {
//        return new Color((int)(Math.random() * 0x1000000));
//    }
}
