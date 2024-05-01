package Interfaz;
import Renders.BarChartPanel;
import java.util.LinkedHashMap;

/**
 * Interfaz para mostrar las estadísticas actuales del programa una vez pausado
 * @author Isabel Barquilla y Sandra Familiar
 */
public final class EstadisticasActuales extends javax.swing.JFrame {
    private final InterfazSimulador simulador;
    
    /**
     * Constructor de la interfaz
     * @param s: Simulador del programa 
     */
    public EstadisticasActuales(InterfazSimulador s) {
        initComponents();
        this.simulador = s;
        LinkedHashMap datos = s.datosOcupacion();
        groupEstadisticas.add(bNumP);
        groupEstadisticas.add(bDistA);
        pintar(datos);
        setLocationRelativeTo(null);
        bNumP.setSelected(true);
    }
    
    /**
     * Pintar los gráficos en la interfaz
     * @param datos: Datos a representar en formato gráfico de barras
     */
    public void pintar(LinkedHashMap datos){
        BarChartPanel barChartPanel = new BarChartPanel(datos);
        barChartPanel.setPreferredSize(pGrafico.getSize());
        pGrafico.add(barChartPanel);
        pack();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        groupEstadisticas = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        bNumP = new javax.swing.JRadioButton();
        bDistA = new javax.swing.JRadioButton();
        pGrafico = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        bNumP.setText("Número de pasajeros");
        bNumP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bNumPActionPerformed(evt);
            }
        });

        bDistA.setText("Distribución de aviones");
        bDistA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDistAActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bNumP, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bDistA, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bNumP)
                    .addComponent(bDistA))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        pGrafico.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pGrafico.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(pGrafico, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 8, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addComponent(pGrafico, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 530, 410));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Método que pinta los datos de la distribución de los aviones 
     * @param evt: Evento que detecta la pulsación del botón que solicita la representación de los datos 
     */
    private void bDistAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDistAActionPerformed
       LinkedHashMap datos = simulador.datosDistribucion();
       pintar(datos);
    }//GEN-LAST:event_bDistAActionPerformed
    /**
     * Método que pinta los datos del número de pasajeros de cada aeropuerto
     * @param evt: Evento que detecta la pulsación del botón que solicita la representación de los datos 
     */
    private void bNumPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bNumPActionPerformed
       LinkedHashMap datos = simulador.datosOcupacion();
       pintar(datos);
    }//GEN-LAST:event_bNumPActionPerformed

    /**
     * @param args the command line arguments
     */
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton bDistA;
    private javax.swing.JRadioButton bNumP;
    private javax.swing.ButtonGroup groupEstadisticas;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel pGrafico;
    // End of variables declaration//GEN-END:variables
}
