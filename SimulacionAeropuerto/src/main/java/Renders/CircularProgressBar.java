package Renders;
import javax.swing.*;
import java.awt.*;

/**
 * Clase que dibuja un gráfico circular
 * @author Isabel Barquilla y Sandra Familiar
 */

public class CircularProgressBar extends JPanel {
    private int progress; // Progreso de la barra (0-100)
    private Color progressColor; // Color de la barra de progreso

    /**
     * Constructor de la clase
     */
    public CircularProgressBar() {
        this.progress = 0;
        this.progressColor = Color.black;
        setPreferredSize(new Dimension(20, 20)); // Tamaño aproximado de 20x20
    }

    /**
     * Método para modificar el progreso o ocupación que representa el gráfico
     * @param progress: Porcentaje que quiero representar
     */
    public void setProgress(int progress) {
        if (progress >= 0 && progress <= 100) {
            this.progress = progress;
            repaint(); // Vuelve a dibujar la barra de progreso
        } else {
            throw new IllegalArgumentException("El progreso debe estar entre 0 y 100");
        }
    }

    /**
     * Método que dibuja el gráfico
     * @param g: Gráfico que quiero dibujar
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();

        // Calcula el ángulo de la barra de progreso
        int angle = (int) (360 * ((double) progress / 100));

        // Dibuja el arco de progreso
        g2d.setColor(progressColor);
        int thickness = 3; // Grosor de la barra de progreso
        int padding = thickness / 2;
        g2d.setStroke(new BasicStroke(thickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.drawArc(padding, padding, getWidth() - thickness, getHeight() - thickness, 90, -angle);

        g2d.dispose();
    }

    /**
     * Método que cambiar el color del gráfico
     * @param color: Color que quiero aplicar
     */
    public void setProgressColor(Color color) {
        this.progressColor = color;
        repaint(); // Vuelve a dibujar la barra de progreso con el nuevo color
    }
}


