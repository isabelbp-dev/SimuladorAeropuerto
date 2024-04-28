package Interfaz;
import ClasesLogicas.Aeropuerto;
import ClasesLogicas.Avion;
import ClasesLogicas.GeneradorAutobus;
import ClasesLogicas.GeneradorAviones;
import ClasesLogicas.RegistroLog;
import ClasesLogicas.Servidor;
import Renders.CircularProgressBar;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;

/**
 *
 * @author Isabel Barquilla
 */

public class InterfazSimulador extends javax.swing.JFrame {
    private final RegistroLog logger = RegistroLog.getInstance();
    private int maxPasajerosM = 0;
    private int maxPasajerosB = 0; 
    
    //Atributos
    private final Lock modBus1 = new ReentrantLock();
    private final CircularProgressBar[] graficosM;
    private final CircularProgressBar[] graficosB;
    private final JTextField[] puertasM;
    private final JTextField[] puertasB;
    private final JTextField[] pistasM;
    private final JTextField[] pistasB;
    private final Aeropuerto aeroMadrid;
    private final Aeropuerto aeroBarcelona;
    private final HashSet<String> aeroviaMB = new HashSet<>();
    private final HashSet<String> aeroviaBM = new HashSet<>();
    private boolean pausado = false; 

    //Atributos para la comunicación y sincronicación de hilos
    private final Lock lAeroviaMB = new ReentrantLock();
    private final Lock lAeroviaBM = new ReentrantLock();
    private final Lock lPausar = new ReentrantLock();
    Condition simuladorPausado = lPausar.newCondition();
    
    //Servidor
    private final Thread server; 
    
    //Constructor
    public InterfazSimulador() {
        initComponents();
        
        bEstadisticas.setVisible(false);
        CircularProgressBar p1 = new CircularProgressBar();
        CircularProgressBar p2 = new CircularProgressBar();
        CircularProgressBar p3 = new CircularProgressBar();
        CircularProgressBar p4 = new CircularProgressBar();
        CircularProgressBar p5 = new CircularProgressBar();
        CircularProgressBar p7 = new CircularProgressBar();
        CircularProgressBar p8 = new CircularProgressBar();
        CircularProgressBar p9 = new CircularProgressBar();
        CircularProgressBar p10 = new CircularProgressBar();
        CircularProgressBar p11 = new CircularProgressBar();
        ocupacionP1.add(p1);
        ocupacionP2.add(p2);
        ocupacionP3.add(p3);
        ocupacionP4.add(p4);
        ocupacionP5.add(p5);
        ocupacionP7.add(p7);
        ocupacionP8.add(p8);
        ocupacionP9.add(p9);
        ocupacionP10.add(p10);
        ocupacionP11.add(p11);
        this.puertasM = new JTextField[]{puerta1M, puerta2M, puerta3M, puerta4M, puerta5M, puerta6M};
        this.graficosM =  new CircularProgressBar[]{p1, p2, p3, p4, p5, null};
        this.graficosB =  new CircularProgressBar[]{p7, p8, p9, p10, p11, null};
        this.puertasB = new JTextField[]{puerta1B, puerta2B, puerta3B, puerta4B, puerta5B, puerta6B};
        this.pistasM = new JTextField[]{pista1M, pista2M, pista3M, pista4M};
        this.pistasB = new JTextField[]{pista1B, pista2B, pista3B, pista4B};
        this.setLocationRelativeTo(null);
        Aeropuerto aeroM = new Aeropuerto(this, "Madrid");
        Aeropuerto aeroB = new Aeropuerto(this, "Barcelona");
        aeroMadrid = aeroM;
        aeroBarcelona = aeroB;
        Thread gAviones = new Thread(new GeneradorAviones(aeroM, aeroB));
        Thread gBuses = new Thread(new GeneradorAutobus(aeroM, aeroB, this));
        gBuses.start();
        gAviones.start();
        this.server = new Thread( new Servidor(this));
        server.start();
    }

    //Métodos get
    public String getPasajerosM(){
        return inputPasajerosMadrid.getText();
    }
    public String getPasajerosB(){
        return inputPasajerosBarcelona.getText();
    }    
    public HashSet getHangarM(){
        return aeroMadrid.getHangar();
    }
    public HashSet getHangarB(){
        return aeroBarcelona.getHangar();
    }
    public HashSet getTallerM(){
        return aeroMadrid.getTaller();
    }
    public HashSet getTallerB(){
        return aeroBarcelona.getTaller();
    }
    public HashSet getEstacionamientoM(){
        return aeroMadrid.getEstacionamiento();
    }
    public HashSet getEstacionamientoB(){
        return aeroBarcelona.getEstacionamiento();
    }
    public HashSet getRodajeM(){
        return aeroMadrid.getRodaje();
    }
    public HashSet getRodajeB(){
        return aeroBarcelona.getRodaje();
    }
    public String getAeroviaMB(){
        return inputAerovMB.getText();
    }
    public String getAeroviaBM(){
        return inputAerovBM.getText();
    }
    //Ciclo de vida
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InterfazSimulador().setVisible(true);
            }
    });}
        
    //Modificaciones número de pasajeros
    public void modPasajerosM(int n) throws InterruptedException{
        pausar();
        inputPasajerosMadrid.setText(String.valueOf(n));
        if(n>maxPasajerosM){
            maxPasajerosM = n;
        }
    }
    public void modPasajerosB(int n) throws InterruptedException{
        pausar();
        inputPasajerosBarcelona.setText(String.valueOf(n));
        if(n>maxPasajerosB){
            maxPasajerosB = n;
        }
    }
    
    //Modificaciones de llegadas y salidas de buses
    public void modBusCiudadM(String Id) throws InterruptedException{
        pausar();
        inputBusMadrid.setText(Id);
    }
    public void modBusCiudadB(String Id) throws InterruptedException{
        pausar();
        inputBusBarcelona.setText(Id);
    }
    public void modAeroM(String Id) throws InterruptedException{
        pausar();
        inputBusAeroM.setText(Id);
    }
    public void modAeroB(String Id) throws InterruptedException{
        pausar();
        inputBusAeroB.setText(Id);
    }

    //Modificaciones de hangares
    public void modHangarM(HashSet<String> hangar) throws InterruptedException{
        pausar();
        StringJoiner joiner = new StringJoiner(",");
        for(String avion: hangar){
            joiner.add(avion);
        }
        inputHangarM.setText(joiner.toString());
    }
    public void modHangarB(HashSet<String> hangar) throws InterruptedException{
        pausar();
        StringJoiner joiner = new StringJoiner(",");
        for(String avion: hangar){
            joiner.add(avion);
        }
        inputHangarB.setText(joiner.toString());
    }
    
    //Modificaciones de las puertas
    public CircularProgressBar modPuertasM(int puerta, String id) throws InterruptedException{  
        pausar();
        puertasM[puerta].setText(id);
        return graficosM[puerta];
    }
    public CircularProgressBar modPuertasB(int puerta, String id) throws InterruptedException{
        pausar();
        puertasB[puerta].setText(id);
        return graficosB[puerta];
    }
    
    //Modificaciones de las pistas
    public void modPistasM(int pista, String id) throws InterruptedException{
        pausar();
        pistasM[pista].setText(id);
    }
    public void modPistasB(int pista, String id) throws InterruptedException{
        pausar();
        pistasB[pista].setText(id);
    }
    
    //Actualizar aerovías
    public void modAeroviaMB() throws InterruptedException{
        pausar();
        lAeroviaMB.lock();
        StringJoiner joiner = new StringJoiner(",");
        for(String avion: aeroviaMB){
            joiner.add(avion);
        }
        inputAerovMB.setText(joiner.toString());
        lAeroviaMB.unlock();
    }
    public void modAeroviaBM() throws InterruptedException{
        pausar();
        lAeroviaBM.lock();
        StringJoiner joiner = new StringJoiner(",");
        for(String avion: aeroviaBM){
            joiner.add(avion);
        }
        inputAerovBM.setText(joiner.toString());
        lAeroviaBM.unlock();
    }
    
    //Uso de aerovías
    public void usoAeroviaMB(Avion a) throws InterruptedException{
        pausar();
        aeroviaMB.add(a.getId() + "("+a.getOcupacion()+"/"+a.getCapacidad()+")");
        modAeroviaMB();
        logger.registrarEvento("Avión " + a.getId() + " (" + a.getOcupacion() + " pasajeros) accede a la aerovía Madrid-Barcelona. ");
        Thread.sleep(15000+(int)(Math.random()*15000));
        a.setAeropuerto(aeroBarcelona);
    }
    public void usoAeroviaBM(Avion a)throws InterruptedException{
        pausar();
        aeroviaBM.add(a.getId() + "("+a.getOcupacion()+"/"+a.getCapacidad()+")");
        modAeroviaBM();
        logger.registrarEvento("Avión " + a.getId() + " (" + a.getOcupacion() + " pasajeros) accede a la aerovía Barcelona-Madrid. ");
        Thread.sleep(15000+(int)(Math.random()*15000));
        a.setAeropuerto(aeroMadrid);
    }
    public void salirAerovMB(Avion a) throws InterruptedException{
        pausar();
        aeroviaMB.remove(a.getId() + "("+a.getOcupacion()+"/"+a.getCapacidad()+")");
        modAeroviaMB();
    }
    public void salirAerovBM(Avion a) throws InterruptedException{
        pausar();
        aeroviaBM.remove(a.getId() + "("+a.getOcupacion()+"/"+a.getCapacidad()+")");
        modAeroviaBM();
    }
    
    //Actualizar rodaje
    public void modRodajeM(HashSet<String> rodaje) throws InterruptedException{
        pausar();
        StringJoiner joiner = new StringJoiner(",");
        for(String avion: rodaje){
            joiner.add(avion);
        }
        inputRodajeM.setText(joiner.toString());
    }
    public void modRodajeB(HashSet<String> rodaje) throws InterruptedException{
        StringJoiner joiner = new StringJoiner(",");
        for(String avion: rodaje){
            joiner.add(avion);
        }
        inputRodajeB.setText(joiner.toString());
    }

    //Actualizar estacionamiento
    public void modEstacionamientoM(HashSet<String> estacionamiento) throws InterruptedException{
        pausar();
        StringJoiner joiner = new StringJoiner(",");
        for(String avion: estacionamiento){
            joiner.add(avion);
        }
        inputEstacionamientoM.setText(joiner.toString());
    }
    public void modEstacionamientoB(HashSet<String> estacionamiento) throws InterruptedException{
        pausar();
        StringJoiner joiner = new StringJoiner(",");
        for(String avion: estacionamiento){
            joiner.add(avion);
        }
        inputEstacionamientoB.setText(joiner.toString());
    }
 
    //Actualizar talleres
    public void modTallerM(HashSet<String> taller) throws InterruptedException{
        pausar();
        StringJoiner joiner = new StringJoiner(",");
        for(String avion: taller){
            joiner.add(avion);
        }
        inputTallerM.setText(joiner.toString());
    }
    public void modTallerB(HashSet<String> taller) throws InterruptedException{
        pausar();
        StringJoiner joiner = new StringJoiner(",");
        for(String avion: taller){
            joiner.add(avion);
        }
        inputTallerB.setText(joiner.toString());
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        ocupacionP3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        inputBusMadrid = new javax.swing.JTextField();
        inputBusAeroM = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        inputPasajerosMadrid = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        inputRodajeM = new javax.swing.JTextField();
        inputHangarM = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        inputTallerM = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        inputEstacionamientoM = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        puerta5M = new javax.swing.JTextField();
        puerta2M = new javax.swing.JTextField();
        puerta3M = new javax.swing.JTextField();
        puerta1M = new javax.swing.JTextField();
        puerta6M = new javax.swing.JTextField();
        puerta4M = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        pista1M = new javax.swing.JTextField();
        pista3M = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        pista2M = new javax.swing.JTextField();
        pista4M = new javax.swing.JTextField();
        ocupacionP1 = new javax.swing.JPanel();
        ocupacionP2 = new javax.swing.JPanel();
        ocupacionP4 = new javax.swing.JPanel();
        ocupacionP5 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        inputAerovMB = new javax.swing.JTextField();
        inputAerovBM = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        inputBusBarcelona = new javax.swing.JTextField();
        inputBusAeroB = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        inputPasajerosBarcelona = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        inputRodajeB = new javax.swing.JTextField();
        inputHangarB = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        inputTallerB = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        inputEstacionamientoB = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        puerta5B = new javax.swing.JTextField();
        puerta2B = new javax.swing.JTextField();
        puerta3B = new javax.swing.JTextField();
        puerta1B = new javax.swing.JTextField();
        puerta6B = new javax.swing.JTextField();
        puerta4B = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        pista1B = new javax.swing.JTextField();
        pista3B = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        pista2B = new javax.swing.JTextField();
        pista4B = new javax.swing.JTextField();
        ocupacionP9 = new javax.swing.JPanel();
        ocupacionP7 = new javax.swing.JPanel();
        ocupacionP8 = new javax.swing.JPanel();
        ocupacionP11 = new javax.swing.JPanel();
        ocupacionP10 = new javax.swing.JPanel();
        bPausar = new javax.swing.JToggleButton();
        bEstadisticas = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "AEROPUERTO MADRID", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ocupacionP3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ocupacionP3.setPreferredSize(new java.awt.Dimension(30, 30));
        ocupacionP3.setLayout(new java.awt.BorderLayout());
        jPanel2.add(ocupacionP3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 20, 20));

        jLabel1.setText("Bus a aeropuerto:");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, -1, -1));

        jLabel2.setText("Bus a ciudad:");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 110, -1, -1));
        jPanel2.add(inputBusMadrid, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 130, -1));
        jPanel2.add(inputBusAeroM, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 110, 130, -1));

        jLabel3.setText("Nº de Pasajeros en el aeropuerto:");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 30, 191, 30));

        inputPasajerosMadrid.setText("0");
        jPanel2.add(inputPasajerosMadrid, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 30, 50, 30));

        jLabel4.setText("Rodaje:");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 250, -1, -1));

        jLabel5.setText("Hangar: ");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 160, -1, -1));
        jPanel2.add(inputRodajeM, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 250, 270, -1));
        jPanel2.add(inputHangarM, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 160, 270, -1));

        jLabel6.setText("Taller: ");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 190, -1, -1));
        jPanel2.add(inputTallerM, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 190, 270, -1));

        jLabel7.setText("Estacionamiento:");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, -1, -1));
        jPanel2.add(inputEstacionamientoM, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 220, 270, -1));

        jLabel8.setText("Puerta 2:");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 320, -1, -1));

        jLabel9.setText("Puerta 3:");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 350, -1, -1));

        jLabel10.setText("Pista 3:");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 400, -1, -1));

        jLabel11.setText("Puerta 5:");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 320, -1, -1));

        jLabel12.setText("Puerta 6:");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 350, -1, -1));

        jLabel13.setText("Pista 1:");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 400, -1, -1));
        jPanel2.add(puerta5M, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 320, 110, -1));
        jPanel2.add(puerta2M, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 320, 110, -1));
        jPanel2.add(puerta3M, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 350, 110, -1));
        jPanel2.add(puerta1M, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 290, 110, -1));
        jPanel2.add(puerta6M, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 350, 110, -1));
        jPanel2.add(puerta4M, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 290, 110, -1));

        jLabel14.setText("Puerta 1:");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 290, -1, -1));
        jPanel2.add(pista1M, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 400, 110, -1));
        jPanel2.add(pista3M, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 400, 110, -1));

        jLabel15.setText("Puerta 4:");
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 290, -1, -1));

        jLabel16.setText("Pista 4:");
        jPanel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 430, -1, -1));

        jLabel17.setText("Pista 2:");
        jPanel2.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 430, -1, -1));
        jPanel2.add(pista2M, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 430, 110, -1));
        jPanel2.add(pista4M, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 430, 110, -1));

        ocupacionP1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ocupacionP1.setPreferredSize(new java.awt.Dimension(30, 30));
        ocupacionP1.setLayout(new java.awt.BorderLayout());
        jPanel2.add(ocupacionP1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 20, 20));

        ocupacionP2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ocupacionP2.setPreferredSize(new java.awt.Dimension(30, 30));
        ocupacionP2.setLayout(new java.awt.BorderLayout());
        jPanel2.add(ocupacionP2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, 20, 20));

        ocupacionP4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ocupacionP4.setPreferredSize(new java.awt.Dimension(30, 30));
        ocupacionP4.setLayout(new java.awt.BorderLayout());
        jPanel2.add(ocupacionP4, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 290, 20, 20));

        ocupacionP5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ocupacionP5.setPreferredSize(new java.awt.Dimension(30, 30));
        ocupacionP5.setLayout(new java.awt.BorderLayout());
        jPanel2.add(ocupacionP5, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 320, 20, 20));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Aerovías", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        jLabel35.setText("Madrid - Barcelona: ");

        jLabel36.setText("Barcelona - Madrid: ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(inputAerovMB, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inputAerovBM, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(inputAerovMB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(inputAerovBM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(50, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "AEROPUERTO BARCELONA", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel18.setText("Bus a aeropuerto:");
        jPanel4.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, -1, -1));

        jLabel19.setText("Bus a ciudad:");
        jPanel4.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 110, -1, -1));
        jPanel4.add(inputBusBarcelona, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 130, -1));
        jPanel4.add(inputBusAeroB, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 110, 130, -1));

        jLabel20.setText("Nº de Pasajeros en el aeropuerto:");
        jPanel4.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 30, 191, 30));

        inputPasajerosBarcelona.setText("0");
        jPanel4.add(inputPasajerosBarcelona, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 30, 50, 30));

        jLabel21.setText("Rodaje:");
        jPanel4.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 250, -1, -1));

        jLabel22.setText("Hangar: ");
        jPanel4.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 160, -1, -1));
        jPanel4.add(inputRodajeB, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 250, 270, -1));
        jPanel4.add(inputHangarB, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 160, 270, -1));

        jLabel23.setText("Taller: ");
        jPanel4.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 190, -1, -1));
        jPanel4.add(inputTallerB, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 190, 270, -1));

        jLabel24.setText("Estacionamiento:");
        jPanel4.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, -1, -1));
        jPanel4.add(inputEstacionamientoB, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 220, 270, -1));

        jLabel25.setText("Puerta 2:");
        jPanel4.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 320, -1, -1));

        jLabel26.setText("Puerta 3:");
        jPanel4.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 350, -1, -1));

        jLabel27.setText("Pista 3:");
        jPanel4.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 400, -1, -1));

        jLabel28.setText("Puerta 5:");
        jPanel4.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 320, -1, -1));

        jLabel29.setText("Puerta 6:");
        jPanel4.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 350, -1, -1));

        jLabel30.setText("Pista 1:");
        jPanel4.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 400, -1, -1));
        jPanel4.add(puerta5B, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 320, 110, -1));
        jPanel4.add(puerta2B, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 320, 110, -1));
        jPanel4.add(puerta3B, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 350, 110, -1));
        jPanel4.add(puerta1B, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 290, 110, -1));
        jPanel4.add(puerta6B, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 350, 110, -1));
        jPanel4.add(puerta4B, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 290, 110, -1));

        jLabel31.setText("Puerta 1:");
        jPanel4.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 290, -1, -1));
        jPanel4.add(pista1B, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 400, 110, -1));
        jPanel4.add(pista3B, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 400, 110, -1));

        jLabel32.setText("Puerta 4:");
        jPanel4.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 290, -1, -1));

        jLabel33.setText("Pista 4:");
        jPanel4.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 430, -1, -1));

        jLabel34.setText("Pista 2:");
        jPanel4.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 430, -1, -1));
        jPanel4.add(pista2B, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 430, 110, -1));
        jPanel4.add(pista4B, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 430, 110, -1));

        ocupacionP9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ocupacionP9.setPreferredSize(new java.awt.Dimension(30, 30));
        ocupacionP9.setLayout(new java.awt.BorderLayout());
        jPanel4.add(ocupacionP9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 20, 20));

        ocupacionP7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ocupacionP7.setPreferredSize(new java.awt.Dimension(30, 30));
        ocupacionP7.setLayout(new java.awt.BorderLayout());
        jPanel4.add(ocupacionP7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 20, 20));

        ocupacionP8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ocupacionP8.setPreferredSize(new java.awt.Dimension(30, 30));
        ocupacionP8.setLayout(new java.awt.BorderLayout());
        jPanel4.add(ocupacionP8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, 20, 20));

        ocupacionP11.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ocupacionP11.setPreferredSize(new java.awt.Dimension(30, 30));
        ocupacionP11.setLayout(new java.awt.BorderLayout());
        jPanel4.add(ocupacionP11, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 320, 20, 20));

        ocupacionP10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ocupacionP10.setPreferredSize(new java.awt.Dimension(30, 30));
        ocupacionP10.setLayout(new java.awt.BorderLayout());
        jPanel4.add(ocupacionP10, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 290, 20, 20));

        bPausar.setText("Pausar");
        bPausar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPausarActionPerformed(evt);
            }
        });

        bEstadisticas.setText("👁️  Ver estadísticas");
        bEstadisticas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bEstadisticasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(bEstadisticas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bPausar, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(380, 380, 380))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bPausar)
                    .addComponent(bEstadisticas))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 463, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 463, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 0, 920, 700));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bPausarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPausarActionPerformed
        if(bPausar.isSelected()){
            bPausar.setText("Reanudar");
            pausado = true;
            bEstadisticas.setVisible(pausado);
        }else{
            bPausar.setText("Pausar");
            pausado = false; 
            bEstadisticas.setVisible(pausado);
            lPausar.lock();
            simuladorPausado.signalAll();
            lPausar.unlock();
        }
    }//GEN-LAST:event_bPausarActionPerformed

    public LinkedHashMap datosOcupacion(){
        LinkedHashMap<String, Integer> datos = new LinkedHashMap<>();
        datos.put("Actual Madrid", Integer.parseInt(inputPasajerosMadrid.getText()));
        datos.put("Máx. Madrid",maxPasajerosM);
        datos.put("Actual Barcelona", Integer.parseInt(inputPasajerosBarcelona.getText()));
        datos.put("Máx. Barcelona",maxPasajerosB);
        return datos;
    }
    public LinkedHashMap datosDistribucion(){
        LinkedHashMap<String, Integer> datos = new LinkedHashMap<>();
        datos.put("Hangar M.", (inputHangarM.getText().isEmpty() ? 0 : inputHangarM.getText().split(",").length));
        datos.put("Hangar B.", (inputHangarB.getText().isEmpty() ? 0 : inputHangarB.getText().split(",").length));
        datos.put("Taller M.", (inputTallerM.getText().isEmpty() ? 0 : inputTallerM.getText().split(",").length));
        datos.put("Taller B.", (inputTallerB.getText().isEmpty() ? 0 : inputTallerB.getText().split(",").length));
        datos.put("Est. M.", (inputEstacionamientoM.getText().isEmpty() ? 0 : inputEstacionamientoM.getText().split(",").length));
        datos.put("Est. B.", (inputEstacionamientoB.getText().isEmpty() ? 0 : inputEstacionamientoB.getText().split(",").length));
        datos.put("Rodaje M.", (inputRodajeM.getText().isEmpty() ? 0 : inputRodajeM.getText().split(",").length));
        datos.put("Rodaje B.", (inputRodajeB.getText().isEmpty() ? 0 : inputRodajeB.getText().split(",").length));
        return datos;
    }
    private void bEstadisticasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bEstadisticasActionPerformed
        EstadisticasActuales estadisticas = new EstadisticasActuales(this);
        estadisticas.setVisible(true);
    }//GEN-LAST:event_bEstadisticasActionPerformed
    public void pausar() throws InterruptedException{
        lPausar.lock();
        try{
            if(pausado){
                simuladorPausado.await();}
        }finally{
            lPausar.unlock();
        }
    }
    
    public void cerrarPista(int pista) throws InterruptedException{
        if(pista <= 3){
            aeroMadrid.cerrarPista(pista);
        }else{
            aeroBarcelona.cerrarPista(pista - 4);
        }
    }
    public void abrirPista(int pista){
        if(pista <= 3){
            aeroMadrid.abrirPista(pista);
        }else{
            aeroBarcelona.abrirPista(pista);
        }
    }
    public boolean consultarPistaM(int pista){
        return pistasM[pista].getText().isEmpty();
    }
    public boolean consultarPistaB(int pista){
        return pistasB[pista].getText().isEmpty();
    }
    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bEstadisticas;
    private javax.swing.JToggleButton bPausar;
    private javax.swing.JTextField inputAerovBM;
    private javax.swing.JTextField inputAerovMB;
    private javax.swing.JTextField inputBusAeroB;
    private javax.swing.JTextField inputBusAeroM;
    private javax.swing.JTextField inputBusBarcelona;
    private javax.swing.JTextField inputBusMadrid;
    private javax.swing.JTextField inputEstacionamientoB;
    private javax.swing.JTextField inputEstacionamientoM;
    private javax.swing.JTextField inputHangarB;
    private javax.swing.JTextField inputHangarM;
    private javax.swing.JTextField inputPasajerosBarcelona;
    private javax.swing.JTextField inputPasajerosMadrid;
    private javax.swing.JTextField inputRodajeB;
    private javax.swing.JTextField inputRodajeM;
    private javax.swing.JTextField inputTallerB;
    private javax.swing.JTextField inputTallerM;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel ocupacionP1;
    private javax.swing.JPanel ocupacionP10;
    private javax.swing.JPanel ocupacionP11;
    private javax.swing.JPanel ocupacionP2;
    private javax.swing.JPanel ocupacionP3;
    private javax.swing.JPanel ocupacionP4;
    private javax.swing.JPanel ocupacionP5;
    private javax.swing.JPanel ocupacionP7;
    private javax.swing.JPanel ocupacionP8;
    private javax.swing.JPanel ocupacionP9;
    private javax.swing.JTextField pista1B;
    private javax.swing.JTextField pista1M;
    private javax.swing.JTextField pista2B;
    private javax.swing.JTextField pista2M;
    private javax.swing.JTextField pista3B;
    private javax.swing.JTextField pista3M;
    private javax.swing.JTextField pista4B;
    private javax.swing.JTextField pista4M;
    private javax.swing.JTextField puerta1B;
    private javax.swing.JTextField puerta1M;
    private javax.swing.JTextField puerta2B;
    private javax.swing.JTextField puerta2M;
    private javax.swing.JTextField puerta3B;
    private javax.swing.JTextField puerta3M;
    private javax.swing.JTextField puerta4B;
    private javax.swing.JTextField puerta4M;
    private javax.swing.JTextField puerta5B;
    private javax.swing.JTextField puerta5M;
    private javax.swing.JTextField puerta6B;
    private javax.swing.JTextField puerta6M;
    // End of variables declaration//GEN-END:variables
}
