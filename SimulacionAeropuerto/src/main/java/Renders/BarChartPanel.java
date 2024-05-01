package Renders;
import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * Clase que representa gráficos de barras
 * @author Isabel Barquilla y Sandra Familiar
 */

public class BarChartPanel extends JPanel {
    private final Map<String, Integer> valores;
    private final int valorMaximo;

    /**
     * Constructor
     * @param valores: Conjunto de valores que quiero representar 
     */
    public BarChartPanel(Map<String, Integer> valores) {
        this.valores = valores;
        this.valorMaximo = valores.values().stream().max(Integer::compareTo).orElse(0);
    }

    /**
     * Método que dibuja los componentes del gráfico
     * @param g: Gráfico que quiero dibujar
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        dibujarGraficoDeBarras(g);
    }

    /**
     * Método que dibuja el gráfico de barras
     * @param g: Gráfico que quiero dibujar
     */
    private void dibujarGraficoDeBarras(Graphics g) {
        int ancho = getWidth();
        int alto = getHeight();
        int margen = 27;
        int margenDerecho = 85; // Margen derecho adicional
        int margenEtiquetas = 0;
        int numeroBarras = valores.size();
        int anchoBarra = (ancho - (2 * margen) - margenEtiquetas - 2*margenDerecho) / numeroBarras;
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
}
