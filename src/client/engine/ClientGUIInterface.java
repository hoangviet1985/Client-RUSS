/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.engine;

//import java.net.ServerSocket;

//client side

import client.camera.Client;
import java.awt.Color;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author vietdinh
 */
public class ClientGUIInterface extends javax.swing.JFrame{

    /**
     * Creates new form SocketJFrame
     */
    
    private TCPOperation tcpOperation;
    static Timer timer;
    //private Gauce gauce;
    boolean gotError = false;
    String errorMessage = "";
    
    private int AcX;
    private int AcY;
    private int AcZ;
    private int temp;
    private int GyX;
    private int GyY;
    private int GyZ;
    
    private final GauceFront gauceF;
    private final GauceSide gauceS;
    
    public ClientGUIInterface() 
    {
        
        
        initComponents();
        
        //add listener to slider
        powerBar.addChangeListener(new JSliderActionListener());
        //setup the slider
        powerBar.setMaximum(100);
        powerBar.setMinimum(0);
        powerBar.setMajorTickSpacing(25);
        powerBar.setMinorTickSpacing(5);
        powerBar.setValue(0);
        powerBar.setPaintLabels(true);
        powerBar.setPaintTicks(true);
        powerBar.setEnabled(false);
        
        connectBtt.addActionListener(new connectBttListener());
        connectBtt.setEnabled(true);
      
        disconnectBtt.addActionListener(new disconnectBttListener());
        disconnectBtt.setEnabled(false);
        
        reverseBtt.addActionListener(new reverseBttListener());
        reverseBtt.setEnabled(false);
        
        forwardBtt.addActionListener(new forwardBttListener());
        forwardBtt.setEnabled(false);
        
        cameraBtt.addActionListener(new cameraBttListener());
        cameraBtt.setVisible(true);
        
        rightBtt.addActionListener(new rightBttListener());
        rightBtt.setEnabled(false);
        
        leftBtt.addActionListener(new leftBttListener());
        leftBtt.setEnabled(false);
        
        serverIPaddr.addActionListener(new serverIPaddrListener());
        serverIPaddr.setEnabled(true);
        
        timer = new Timer(500, new TimerListener());
        timer.setInitialDelay(0);
        timer.setCoalesce(true);
        
        AcX = 0;
        AcY = 0;
        AcZ = 0;
        temp = 0;
        GyX = 0;
        GyY = 0;
        GyZ = 0;
    
        gauceF = new GauceFront();
        getContentPane().add(gauceF, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, 100, 100 ));
        
        gauceS = new GauceSide();
        getContentPane().add(gauceS, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 340, 200, 100 ));
    }
    
    public boolean processReceivingData(String str)
    {
        String[] data;
        data = str.split(" ");
        boolean check = true;
        if(data.length != 7)
        {
            check = false;
            gotError = true;
            errorMessage = "not enough...";
        }
        else
        {
            try
            {
                AcX = Integer.parseInt(data[0]);
                AcY = Integer.parseInt(data[1]);
                AcZ = Integer.parseInt(data[2]);
                temp = Integer.parseInt(data[3]);
                GyX = Integer.parseInt(data[4]);
                GyY = Integer.parseInt(data[5]);
                GyZ = Integer.parseInt(data[6]);
            }
            catch(NumberFormatException e)
            {
                gotError = true;
                errorMessage = e.toString();
                check = false;
            }
        }
        
        return check;   
    }

    class GauceSide extends JPanel
    {
        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
          
            g.setColor(Color.red);
            g.fillArc(0, 0, 200, 100, (int)Math.toDegrees(Math.asin(AcY/16384.0)), 180);
            g.setColor(Color.blue);
            g.fillArc(0, 0, 200, 100, (int)Math.toDegrees(Math.asin(AcY/16384.0))-180, 180);
          
        }
    }
    
    class GauceFront extends JPanel
    {
        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
          
            g.setColor(Color.red);
            g.fillArc(0, 0, 100, 100, (int)Math.toDegrees(Math.asin(AcX/16384.0)), 180);
            g.setColor(Color.blue);
            g.fillArc(0, 0, 100, 100, (int)Math.toDegrees(Math.asin(AcX/16384.0))-180, 180);
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        powerBar = new javax.swing.JSlider();
        powerLabel = new javax.swing.JLabel();
        serverIPlabel = new javax.swing.JLabel();
        serverIPaddr = new javax.swing.JTextField();
        connectBtt = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        disconnectBtt = new javax.swing.JButton();
        forwardBtt = new javax.swing.JButton();
        reverseBtt = new javax.swing.JButton();
        runModeLabel = new javax.swing.JLabel();
        rightBtt = new javax.swing.JButton();
        leftBtt = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        tiltingLabel = new javax.swing.JLabel();
        frontTiltLabel = new javax.swing.JLabel();
        sideTiltLabel = new javax.swing.JLabel();
        cameraBtt = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        powerLabel.setFont(new java.awt.Font("Lucida Grande", 1, 15)); // NOI18N
        powerLabel.setForeground(new java.awt.Color(255, 51, 0));
        powerLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        powerLabel.setLabelFor(powerBar);
        powerLabel.setText("Power");

        serverIPlabel.setFont(new java.awt.Font("Lucida Grande", 1, 15)); // NOI18N
        serverIPlabel.setForeground(new java.awt.Color(51, 51, 255));
        serverIPlabel.setLabelFor(serverIPaddr);
        serverIPlabel.setText("Server IP address");

        connectBtt.setText("Connect");

        disconnectBtt.setText("Disconnect");

        forwardBtt.setText("Forward");

        reverseBtt.setText("Reverse");

        runModeLabel.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        runModeLabel.setForeground(new java.awt.Color(0, 0, 255));
        runModeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        runModeLabel.setLabelFor(powerBar);

        rightBtt.setText("Right");

        leftBtt.setText("Left");

        tiltingLabel.setBackground(new java.awt.Color(255, 255, 255));
        tiltingLabel.setFont(new java.awt.Font("Lucida Grande", 1, 15)); // NOI18N
        tiltingLabel.setForeground(new java.awt.Color(0, 0, 255));
        tiltingLabel.setText("Tilting");

        frontTiltLabel.setForeground(new java.awt.Color(0, 0, 255));
        frontTiltLabel.setText("Front");

        sideTiltLabel.setForeground(new java.awt.Color(0, 0, 255));
        sideTiltLabel.setText("Side");

        cameraBtt.setText("Camera");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(serverIPlabel)
                .addGap(5, 5, 5)
                .addComponent(serverIPaddr, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100)
                .addComponent(cameraBtt))
            .addGroup(layout.createSequentialGroup()
                .addGap(240, 240, 240)
                .addComponent(connectBtt)
                .addGap(14, 14, 14)
                .addComponent(disconnectBtt, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 670, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(forwardBtt)
                .addGap(246, 246, 246)
                .addComponent(runModeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(leftBtt, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(rightBtt, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(powerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(powerBar, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(reverseBtt))
            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 670, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createSequentialGroup()
                .addGap(120, 120, 120)
                .addComponent(tiltingLabel))
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(frontTiltLabel)
                .addGap(137, 137, 137)
                .addComponent(sideTiltLabel))
            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(serverIPlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(serverIPaddr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cameraBtt))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(connectBtt)
                    .addComponent(disconnectBtt))
                .addGap(11, 11, 11)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(forwardBtt)
                    .addComponent(runModeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(leftBtt)
                    .addComponent(rightBtt)
                    .addComponent(powerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(powerBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(reverseBtt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(tiltingLabel)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(frontTiltLabel)
                    .addComponent(sideTiltLabel))
                .addGap(184, 184, 184)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cameraBtt;
    private javax.swing.JButton connectBtt;
    private javax.swing.JButton disconnectBtt;
    private javax.swing.JButton forwardBtt;
    private javax.swing.JLabel frontTiltLabel;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JButton leftBtt;
    private javax.swing.JSlider powerBar;
    private javax.swing.JLabel powerLabel;
    private javax.swing.JButton reverseBtt;
    private javax.swing.JButton rightBtt;
    private javax.swing.JLabel runModeLabel;
    private javax.swing.JTextField serverIPaddr;
    private javax.swing.JLabel serverIPlabel;
    private javax.swing.JLabel sideTiltLabel;
    private javax.swing.JLabel tiltingLabel;
    // End of variables declaration//GEN-END:variables

    class serverIPaddrListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            if(e.getSource()==serverIPaddr)
            {
                if(serverIPaddr.isEnabled())
                {
                    connectBtt.setEnabled(true);
                }
                else
                {
                    connectBtt.setEnabled(false);
                }
            }
        }
    }
    
    class connectBttListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(connectBtt.isEnabled() && !serverIPaddr.getText().equals(""))
            {
                tcpOperation = new TCPOperation(serverIPaddr.getText());
            
                if(!tcpOperation.isError())
                {
                    connectBtt.setEnabled(false);
                    disconnectBtt.setEnabled(true);
                    powerBar.setEnabled(true);
                    serverIPaddr.setEnabled(false);
                    reverseBtt.setEnabled(true);
                    forwardBtt.setEnabled(true);
                    cameraBtt.setEnabled(true);
                    rightBtt.setEnabled(true);
                    leftBtt.setEnabled(true);
                    runModeLabel.setText("Forward");
                    jLabel3.setText("");
                    timer.start();
                }
            printError(tcpOperation.isError(), tcpOperation.errorMess());
            }
        }
    }
    
    class disconnectBttListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(disconnectBtt.isEnabled())
            {
                tcpOperation.sendData(103);
            
                disconnectBtt.setEnabled(false);
                connectBtt.setEnabled(true);
                serverIPaddr.setEnabled(true);
                powerBar.setValue(0);
                powerBar.setEnabled(false);
                forwardBtt.setEnabled(false);
                reverseBtt.setEnabled(false);
                cameraBtt.setEnabled(false);
                rightBtt.setEnabled(false);
                leftBtt.setEnabled(false);
                timer.stop();
                printError(tcpOperation.isError(), tcpOperation.errorMess());
            }
        }
    }
    
    class forwardBttListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(forwardBtt.isEnabled())
            {
                tcpOperation.sendData(101);
            
                runModeLabel.setText("Forward");
                powerBar.setValue(0);
                printError(tcpOperation.isError(), tcpOperation.errorMess());
            }
        }
    }
    
    class reverseBttListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(reverseBtt.isEnabled())
            {
                tcpOperation.sendData(102);
            
                runModeLabel.setText("Reverse");
                powerBar.setValue(0);
                printError(tcpOperation.isError(), tcpOperation.errorMess());
            }
        }
    }
    
    class cameraBttListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(cameraBtt.isEnabled())
            {   //camera part will be done later
                Client myClientCam;
                myClientCam = new Client("192.168.1.8");// IP address of the RP, which holds the camera
                //cameraBtt.setEnabled(false);
            }
        }
    }
    
    class rightBttListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(rightBtt.isEnabled())
            {
                tcpOperation.sendData(104);
            
                runModeLabel.setText("Right");
                powerBar.setValue(0);
            }
        }
    }
    
    class leftBttListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(leftBtt.isEnabled())
            {
                tcpOperation.sendData(105);
            
                runModeLabel.setText("left");
                powerBar.setValue(0);
            }
        }
    }
    
    class TimerListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(tcpOperation.tcpSocket!=null)
            {
                //jLabel3.setText(tcpOperation.getData());
                if(processReceivingData(tcpOperation.getData()))
                {
                    //jLabel3.setText("Side: "+String.format("%.2f",Math.toDegrees(Math.asin(AcY/16384.0)))+" degree---Front: "+String.format("%.2f",Math.toDegrees(Math.asin(AcX/16384.0)))+" degree");
                    gauceF.repaint();
                    gauceS.repaint();
                    tcpOperation.sendData(106);
                }
            
            }
        }
    }
    
    class JSliderActionListener implements ChangeListener
    {
        @Override
        public void stateChanged(ChangeEvent e) {
            JSlider source = (JSlider)e.getSource();
            if(e.getSource()==powerBar && tcpOperation != null)
            {
                if(!source.getValueIsAdjusting()&& tcpOperation.tcpSocket!=null)
                {
                    tcpOperation.sendData(powerBar.getValue());
                    printError(tcpOperation.isError(), tcpOperation.errorMess());
                }
            }
        }
    }
    
    public void printError(boolean gotError, String errorMessage)
    {
        if(gotError)
            jLabel3.setText(errorMessage);
    }
}
