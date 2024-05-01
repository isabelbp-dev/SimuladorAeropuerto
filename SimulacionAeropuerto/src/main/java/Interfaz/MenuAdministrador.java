package Interfaz;

import ClasesLogicas.LectorDatos;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;

/**
 * Interfaz del cliente del programa
 * @author Isabel Barquilla y Sandra Familiar
 */
public class MenuAdministrador extends javax.swing.JFrame {
    private static Socket cliente;
    private static DataInputStream entrada;
    private static DataOutputStream salida; 
    
    /**
     * Constructor de la clase
     */
    public MenuAdministrador() {
        initComponents();
        this.setLocationRelativeTo(null);
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        aeroM1 = new javax.swing.JPanel();
        txtPasajerosB = new javax.swing.JLabel();
        txtHangarB = new javax.swing.JLabel();
        txtTallerB = new javax.swing.JLabel();
        txtEstacionamientoB = new javax.swing.JLabel();
        txtRodajeB = new javax.swing.JLabel();
        inputPasajerosB = new javax.swing.JTextField();
        inputHangarB = new javax.swing.JTextField();
        inputTallerB = new javax.swing.JTextField();
        inputEstacionamientoB = new javax.swing.JTextField();
        inputRodajeB = new javax.swing.JTextField();
        txtP1B = new javax.swing.JLabel();
        txtP2B = new javax.swing.JLabel();
        txtP4B = new javax.swing.JLabel();
        txtP3B = new javax.swing.JLabel();
        bPista1B = new javax.swing.JToggleButton();
        bPista2B = new javax.swing.JToggleButton();
        bPista3B = new javax.swing.JToggleButton();
        bPista4B = new javax.swing.JToggleButton();
        aeroM = new javax.swing.JPanel();
        txtPasajerosM = new javax.swing.JLabel();
        txtHangarM = new javax.swing.JLabel();
        txtTallerM = new javax.swing.JLabel();
        txtEstacionamientoM = new javax.swing.JLabel();
        txtRodajeM = new javax.swing.JLabel();
        inputPasajerosM = new javax.swing.JTextField();
        inputHangarM = new javax.swing.JTextField();
        inputTallerM = new javax.swing.JTextField();
        inputEstacionamientoM = new javax.swing.JTextField();
        inputRodajeM = new javax.swing.JTextField();
        txtP1M = new javax.swing.JLabel();
        txtP2M = new javax.swing.JLabel();
        txtP4M = new javax.swing.JLabel();
        txtP3M = new javax.swing.JLabel();
        bPista1M = new javax.swing.JToggleButton();
        bPista2M = new javax.swing.JToggleButton();
        bPista3M = new javax.swing.JToggleButton();
        bPista4M = new javax.swing.JToggleButton();
        jPanel1 = new javax.swing.JPanel();
        txtAeroBM = new javax.swing.JLabel();
        txtAeroMB = new javax.swing.JLabel();
        inputAerovMB = new javax.swing.JTextField();
        inputAerovBM = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        aeroM1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Aeropuerto de Barcelona", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N

        txtPasajerosB.setText("Nº Pasajeros en aeropuerto: ");

        txtHangarB.setText("Nº Aviones en hangar: ");

        txtTallerB.setText("Nº Aviones en taller: ");

        txtEstacionamientoB.setText("Nº Aviones en área de estacionamiento: ");

        txtRodajeB.setText("Nº Aviones en área de rodaje:  ");

        inputPasajerosB.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        inputPasajerosB.setEnabled(false);

        inputHangarB.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        inputHangarB.setEnabled(false);

        inputTallerB.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        inputTallerB.setEnabled(false);

        inputEstacionamientoB.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        inputEstacionamientoB.setEnabled(false);

        inputRodajeB.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        inputRodajeB.setEnabled(false);

        txtP1B.setText("Pista 1: ");

        txtP2B.setText("Pista 2: ");

        txtP4B.setText("Pista 4: ");

        txtP3B.setText("Pista 3: ");

        bPista1B.setText("Cerrar");
        bPista1B.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPista1BActionPerformed(evt);
            }
        });

        bPista2B.setText("Cerrar");
        bPista2B.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPista2BActionPerformed(evt);
            }
        });

        bPista3B.setText("Cerrar");
        bPista3B.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPista3BActionPerformed(evt);
            }
        });

        bPista4B.setText("Cerrar");
        bPista4B.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPista4BActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout aeroM1Layout = new javax.swing.GroupLayout(aeroM1);
        aeroM1.setLayout(aeroM1Layout);
        aeroM1Layout.setHorizontalGroup(
            aeroM1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, aeroM1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(aeroM1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(aeroM1Layout.createSequentialGroup()
                        .addGroup(aeroM1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, aeroM1Layout.createSequentialGroup()
                                .addComponent(txtP1B, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bPista1B, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(bPista3B, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtP3B, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, aeroM1Layout.createSequentialGroup()
                                .addGroup(aeroM1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtPasajerosB, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtHangarB, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTallerB, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtRodajeB, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtEstacionamientoB, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                                .addGroup(aeroM1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(inputPasajerosB, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(inputRodajeB, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(inputEstacionamientoB, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(inputTallerB, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(inputHangarB, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(21, 21, 21))
                    .addGroup(aeroM1Layout.createSequentialGroup()
                        .addComponent(txtP2B, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bPista2B, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(bPista4B, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtP4B)
                        .addGap(36, 36, 36))))
        );
        aeroM1Layout.setVerticalGroup(
            aeroM1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(aeroM1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(aeroM1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPasajerosB)
                    .addComponent(inputPasajerosB, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(aeroM1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtHangarB)
                    .addComponent(inputHangarB, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(aeroM1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTallerB)
                    .addComponent(inputTallerB, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(aeroM1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEstacionamientoB)
                    .addComponent(inputEstacionamientoB, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(aeroM1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtRodajeB)
                    .addComponent(inputRodajeB, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(aeroM1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtP1B)
                    .addComponent(txtP3B)
                    .addComponent(bPista1B)
                    .addComponent(bPista3B))
                .addGap(18, 18, 18)
                .addGroup(aeroM1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtP4B)
                    .addComponent(txtP2B)
                    .addComponent(bPista4B)
                    .addComponent(bPista2B))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        aeroM.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Aeropuerto de Madrid", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N

        txtPasajerosM.setText("Nº Pasajeros en aeropuerto: ");

        txtHangarM.setText("Nº Aviones en hangar: ");

        txtTallerM.setText("Nº Aviones en taller: ");

        txtEstacionamientoM.setText("Nº Aviones en área de estacionamiento: ");

        txtRodajeM.setText("Nº Aviones en área de rodaje:  ");

        inputPasajerosM.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        inputPasajerosM.setEnabled(false);

        inputHangarM.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        inputHangarM.setEnabled(false);

        inputTallerM.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        inputTallerM.setEnabled(false);

        inputEstacionamientoM.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        inputEstacionamientoM.setEnabled(false);

        inputRodajeM.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        inputRodajeM.setEnabled(false);

        txtP1M.setText("Pista 1: ");

        txtP2M.setText("Pista 2: ");

        txtP4M.setText("Pista 4: ");

        txtP3M.setText("Pista 3: ");

        bPista1M.setText("Cerrar");
        bPista1M.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPista1MActionPerformed(evt);
            }
        });

        bPista2M.setText("Cerrar");
        bPista2M.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPista2MActionPerformed(evt);
            }
        });

        bPista3M.setText("Cerrar");
        bPista3M.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPista3MActionPerformed(evt);
            }
        });

        bPista4M.setText("Cerrar");
        bPista4M.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPista4MActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout aeroMLayout = new javax.swing.GroupLayout(aeroM);
        aeroM.setLayout(aeroMLayout);
        aeroMLayout.setHorizontalGroup(
            aeroMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, aeroMLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(aeroMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(aeroMLayout.createSequentialGroup()
                        .addGroup(aeroMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, aeroMLayout.createSequentialGroup()
                                .addComponent(txtP1M, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bPista1M, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(bPista3M, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtP3M, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, aeroMLayout.createSequentialGroup()
                                .addGroup(aeroMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtPasajerosM, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtHangarM, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTallerM, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtRodajeM, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtEstacionamientoM, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                                .addGroup(aeroMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(inputPasajerosM, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(inputRodajeM, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(inputEstacionamientoM, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(inputTallerM, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(inputHangarM, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(21, 21, 21))
                    .addGroup(aeroMLayout.createSequentialGroup()
                        .addComponent(txtP2M, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bPista2M, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(bPista4M, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtP4M)
                        .addGap(36, 36, 36))))
        );
        aeroMLayout.setVerticalGroup(
            aeroMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(aeroMLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(aeroMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPasajerosM)
                    .addComponent(inputPasajerosM, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(aeroMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtHangarM)
                    .addComponent(inputHangarM, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(aeroMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTallerM)
                    .addComponent(inputTallerM, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(aeroMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEstacionamientoM)
                    .addComponent(inputEstacionamientoM, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(aeroMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtRodajeM)
                    .addComponent(inputRodajeM, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(aeroMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtP1M)
                    .addComponent(txtP3M)
                    .addComponent(bPista1M)
                    .addComponent(bPista3M))
                .addGap(18, 18, 18)
                .addGroup(aeroMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtP4M)
                    .addComponent(txtP2M)
                    .addComponent(bPista4M)
                    .addComponent(bPista2M))
                .addGap(61, 61, 61))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Aerovías", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        txtAeroBM.setText("Aerovía Barcelona-Madrid:");

        txtAeroMB.setText("Aerovía Madrid-Barcelona:");

        inputAerovMB.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        inputAerovMB.setEnabled(false);

        inputAerovBM.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        inputAerovBM.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtAeroMB, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inputAerovMB, javax.swing.GroupLayout.PREFERRED_SIZE, 688, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtAeroBM, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inputAerovBM, javax.swing.GroupLayout.PREFERRED_SIZE, 688, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAeroMB)
                    .addComponent(inputAerovMB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAeroBM)
                    .addComponent(inputAerovBM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(aeroM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(aeroM1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(aeroM, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(aeroM1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Métodos para la apertura o cierre de pistas
    /**
     * Método para cerrar/abrir la pista 1 del aeropuerto de Madrid
     * @param evt: Método que detecta cuándo se pulsa el botón correspondiente de la interfaz
     */
    private void bPista1MActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPista1MActionPerformed
        try {
            modBotonPista(bPista1M,1);
        } catch (IOException ex) {
            Logger.getLogger(MenuAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bPista1MActionPerformed
    /**
     * Método para cerrar/abrir la pista 2 del aeropuerto de Madrid
     * @param evt: Método que detecta cuándo se pulsa el botón correspondiente de la interfaz
     */
    private void bPista2MActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPista2MActionPerformed
        try {
            modBotonPista(bPista2M,2);
        } catch (IOException ex) {
            Logger.getLogger(MenuAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bPista2MActionPerformed
    /**
     * Método para cerrar/abrir la pista 3 del aeropuerto de Madrid
     * @param evt: Método que detecta cuándo se pulsa el botón correspondiente de la interfaz
     */
    private void bPista3MActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPista3MActionPerformed
        try {
            modBotonPista(bPista3M,3);
        } catch (IOException ex) {
            Logger.getLogger(MenuAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bPista3MActionPerformed
    /**
     * Método para cerrar/abrir la pista 4 del aeropuerto de Madrid
     * @param evt: Método que detecta cuándo se pulsa el botón correspondiente de la interfaz
     */
    private void bPista4MActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPista4MActionPerformed
        try {
            modBotonPista(bPista4M,4);
        } catch (IOException ex) {
            Logger.getLogger(MenuAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bPista4MActionPerformed
    /**
     * Método para cerrar/abrir la pista 1 del aeropuerto de Barcelona
     * @param evt: Método que detecta cuándo se pulsa el botón correspondiente de la interfaz
     */
    private void bPista1BActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPista1BActionPerformed
        try {
            modBotonPista(bPista1B,5);
        } catch (IOException ex) {
            Logger.getLogger(MenuAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bPista1BActionPerformed
    /**
     * Método para cerrar/abrir la pista 3 del aeropuerto de Barcelona
     * @param evt: Método que detecta cuándo se pulsa el botón correspondiente de la interfaz
     */
    private void bPista3BActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPista3BActionPerformed
        try {
            modBotonPista(bPista3B,7);
        } catch (IOException ex) {
            Logger.getLogger(MenuAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bPista3BActionPerformed
    /**
     * Método para cerrar/abrir la pista 2 del aeropuerto de Barcelona
     * @param evt: Método que detecta cuándo se pulsa el botón correspondiente de la interfaz
     */
    private void bPista2BActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPista2BActionPerformed
        try {
            modBotonPista(bPista2B,6);
        } catch (IOException ex) {
            Logger.getLogger(MenuAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bPista2BActionPerformed
    /**
     * Método para cerrar/abrir la pista 4 del aeropuerto de Barcelona
     * @param evt: Método que detecta cuándo se pulsa el botón correspondiente de la interfaz
     */
    private void bPista4BActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPista4BActionPerformed
        try {
            modBotonPista(bPista4B,8);
        } catch (IOException ex) {
            Logger.getLogger(MenuAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bPista4BActionPerformed
    /**
     * Método para cerrar una pista
     * @param pista: Pista que queremos cerrar
     * @throws java.io.IOException
     */
    public void cerrarPista(int pista) throws IOException{
        salida.writeUTF("Cerrar;" + String.valueOf(pista));
    }
    /**
     * Método para abrir una pista
     * @param pista: Pista que queremos abrir
     * @throws java.io.IOException
     */
    public void abrirPista(int pista) throws IOException{
        salida.writeUTF("Abrir;" + String.valueOf(pista));
    }
    /**
     * Método para modificar el texto del botón de la pista que hemos abierto/cerrado
     * @param b: Botón que hemos pulsado
     * @param n: Pista a la que pertenece el botón
     * @throws java.io.IOException
     */
    public void modBotonPista(JToggleButton b, int n) throws IOException{
        if(b.isSelected()){
            b.setText("Abrir");
            cerrarPista(n);
        }else{
            b.setText("Cerrar");
            abrirPista(n);
        }
    }
    
    //Método para modificar los datos de los pasajeros
    /**
     * Método para modificar los pasajeros del aeropuerto de Madrid
     * @param n: Número de pasajeros actuales
     */
    public void modPasajerosM(String n){
        inputPasajerosM.setText(n);
    }
    /**
     * Método para modificar los pasajeros del aeropuerto de Barcelona
     * @param n: Número de pasajeros actuales
     */
    public void modPasajerosB(String n){
        inputPasajerosB.setText(n);
    }
    
    //Método para modificar los datos de los hangares
    /**
     * Método para modificar el número de aviones del hangar de Madrid
     * @param n: Número de aviones actuales
     */
    public void modHangarM(String n){
        inputHangarM.setText(n);
    }
    /**
     * Método para modificar el número de aviones del hangar de Barcelona
     * @param n: Número de aviones actuales
     */
    public void modHangarB(String n){
        inputHangarB.setText(n);
    }
    
    //Método para modificar los datos de los talleres
    /**
     * Método para modificar el número de avioens en el taller de Madrid
     * @param n: Número de aviones actuales
     */
    public void modTallerM(String n){
        inputTallerM.setText(n);
    }
    /**
     * Método para modificar el número de aviones en el taller de Barcelona
     * @param n: Número de aviones actuales
     */
    public void modTallerB(String n){
        inputTallerB.setText(n);
    }
    
    //Método para modificar los datos de los estacionamientos
    /**
     * Método para modificar el número de aviones que hay en el estacionamiento de Madrid
     * @param n: Número de aviones actuales
     */
    public void modEstacionamientoM(String n){
        inputEstacionamientoM.setText(n);
    }
    /**
     * Método para modificar el número de aviones que hay en el estacionamiento de Barcelona
     * @param n: Número de aviones actuales
     */
    public void modEstacionamientoB(String n){
        inputEstacionamientoB.setText(n);
    }
    
    //Método para modificar los datos de las áreas de rodaje
    /**
     * Método para modificar el número de aviones que hay en el área de rodaje de Madrid
     * @param n: Número de aviones actuales
     */
    public void modRodajeM(String n){
        inputRodajeM.setText(n);
    }
    /**
     * Método para modificar el número de aviones que hay en el área de rodaje de Barcelona
     * @param n: Número de aviones actuales
     */
    public void modRodajeB(String n){
        inputRodajeB.setText(n);
    }
    
    //Método para modificar los datos de las aerovías
    /**
     * Método para modificar los datos de la aerovía de Madrid a Barcelona
     * @param aero: Datos actuales de la aerovía
     */
    public void modAerovMB(String aero){
        inputAerovMB.setText(aero);
    }
    /**
     * Método para modificar los datos de la aerovía de Barcelona a Madrid
     * @param aero: Datos actuales de la aerovía
     */
    public void modAerovBM(String aero){
        inputAerovBM.setText(aero);
    }

    /**
     * Método main de la interfaz del cliente
     * @param args
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MenuAdministrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuAdministrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuAdministrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuAdministrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MenuAdministrador m = new MenuAdministrador();
                m.setVisible(true);
                try {
                    cliente = new Socket(InetAddress.getLocalHost(),5000);
                    entrada = new DataInputStream(cliente.getInputStream());
                    salida = new DataOutputStream(cliente.getOutputStream());
                    Thread c = new Thread(new LectorDatos(entrada,m));
                    c.start();
                }catch (IOException ex) {
                    JOptionPane.showMessageDialog(m, "El servidor no esta activo, se procederá a cerrar el cliente... ");
                    System.exit(0);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel aeroM;
    private javax.swing.JPanel aeroM1;
    private javax.swing.JToggleButton bPista1B;
    private javax.swing.JToggleButton bPista1M;
    private javax.swing.JToggleButton bPista2B;
    private javax.swing.JToggleButton bPista2M;
    private javax.swing.JToggleButton bPista3B;
    private javax.swing.JToggleButton bPista3M;
    private javax.swing.JToggleButton bPista4B;
    private javax.swing.JToggleButton bPista4M;
    private javax.swing.JTextField inputAerovBM;
    private javax.swing.JTextField inputAerovMB;
    private javax.swing.JTextField inputEstacionamientoB;
    private javax.swing.JTextField inputEstacionamientoM;
    private javax.swing.JTextField inputHangarB;
    private javax.swing.JTextField inputHangarM;
    private javax.swing.JTextField inputPasajerosB;
    private javax.swing.JTextField inputPasajerosM;
    private javax.swing.JTextField inputRodajeB;
    private javax.swing.JTextField inputRodajeM;
    private javax.swing.JTextField inputTallerB;
    private javax.swing.JTextField inputTallerM;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel txtAeroBM;
    private javax.swing.JLabel txtAeroMB;
    private javax.swing.JLabel txtEstacionamientoB;
    private javax.swing.JLabel txtEstacionamientoM;
    private javax.swing.JLabel txtHangarB;
    private javax.swing.JLabel txtHangarM;
    private javax.swing.JLabel txtP1B;
    private javax.swing.JLabel txtP1M;
    private javax.swing.JLabel txtP2B;
    private javax.swing.JLabel txtP2M;
    private javax.swing.JLabel txtP3B;
    private javax.swing.JLabel txtP3M;
    private javax.swing.JLabel txtP4B;
    private javax.swing.JLabel txtP4M;
    private javax.swing.JLabel txtPasajerosB;
    private javax.swing.JLabel txtPasajerosM;
    private javax.swing.JLabel txtRodajeB;
    private javax.swing.JLabel txtRodajeM;
    private javax.swing.JLabel txtTallerB;
    private javax.swing.JLabel txtTallerM;
    // End of variables declaration//GEN-END:variables
}
