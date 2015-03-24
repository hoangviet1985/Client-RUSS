/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.camera;

/*
 * @author vietdinh
 */
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class Client extends JFrame{

  //GUI
  //----
  
  JButton playButton = new JButton("Play");
  JButton stopButton = new JButton("Stop");
 
  JPanel mainPanel = new JPanel();
  JPanel buttonPanel = new JPanel();
  JLabel iconLabel = new JLabel();
  ImageIcon icon;

  DatagramPacket receivePacket; //UDP packet received from the server
  DatagramSocket socket; //socket to be used to send and receive UDP packets
  
  byte[] receiveData; //buffer used to store data received from the server 
 
  Socket tcpSocket; //socket used to send/receive RTSP messages
  PrintStream tcpOutput;
  BufferedReader tcpInput;
  Timer timer;
  
  //--------------------------
  //Constructor
  //--------------------------
  public Client(String serverIP) {
        super("Camera");
        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //Buttons
        buttonPanel.setLayout(new GridLayout(1,0));
        buttonPanel.add(playButton);
        buttonPanel.add(stopButton);
        playButton.addActionListener(new playButtonListener());
        stopButton.addActionListener(new stopButtonListener());
        //Image display label
        iconLabel.setIcon(null);
        //frame layout
        mainPanel.setLayout(null);
        mainPanel.add(iconLabel);
        mainPanel.add(buttonPanel);
        iconLabel.setBounds(0,0,380,280);
        buttonPanel.setBounds(0,280,380,50);
        add(mainPanel, BorderLayout.CENTER);
        setSize(new Dimension(390,370));
        setVisible(true);

        timer = new Timer(5, new timerListener());
        timer.setInitialDelay(0);
        timer.setCoalesce(true);

        try {
            socket = new DatagramSocket(5000);//need to setup router to allow udp traffic
        } catch (SocketException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        tcpSocket = new Socket();
        SocketAddress serverAddress = new InetSocketAddress(serverIP, 5001);
        try {
            tcpSocket.connect(serverAddress);
            socket.setReceiveBufferSize(300000);//set size for receive buffer
            tcpOutput = new PrintStream(tcpSocket.getOutputStream(),true);
            tcpInput = new BufferedReader(new InputStreamReader(tcpSocket.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
          
  }  //End of Client Constructor


  //------------------------------------
  //handle for timer
  //------------------------------------
 class timerListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if(tcpInput.ready())
            {
                //get size of the incoming packet
                int size = Integer.parseInt(tcpInput.readLine());
                //report the size to the console
                System.out.println("image size: "+size+" bytes");
                //create an array of bytes to hold the data
                byte[] totalPayload = new byte[size];
                socket.setSoTimeout(100);//udp socket will block the app for 0.1 second for receiving a udp packet
                //This pointer is used to chop the packet of data if it is greater than 30000 bytes
                int bytePointer = 0;
                while(totalPayload.length - bytePointer >= 30000)
                {
                    //create a frame with size of 30000 bytes
                    byte[] receiveData = new byte[30000];
                    //create UDP receving packet with fixed size of the frame above
                    receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    //move the data into the packet
                    socket.receive(receivePacket);
                    //get the content of the packet
                    byte[] payload = receivePacket.getData();
                    //add this content to the packet, with will hold all the data of a picture at the end
                    System.arraycopy(payload, 0, totalPayload, bytePointer, payload.length);
                    //move the pointer ahead 30000 bytes
                    bytePointer += 30000;
                }
                //when the incoming packet is smaller than 30000 bytes or the rest of the packet
                //from the position of the pointer is smaller than 30000 bytes
                //then resize the holding frame to whatever size of data left from the incoming packet
                byte[] receiveData = new byte[totalPayload.length-bytePointer];
                receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);
                byte[] payload = receivePacket.getData(); 
                //now payload is full with data of the sending image
                System.arraycopy(payload, 0, totalPayload, bytePointer, payload.length);

                //get an Image object from the payload bitstream
                Toolkit toolkit = Toolkit.getDefaultToolkit();
                Image image = toolkit.createImage(totalPayload, 0, totalPayload.length);

                //display the image as an ImageIcon object
                icon = new ImageIcon(image);
                iconLabel.setIcon(icon);
                iconLabel.repaint();

                //tell the server that the client is ready for a new frame
                tcpOutput.println("1");
            }
            //when there is no incoming image
            System.out.println("not ready!");
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            //when getting error, tell the server to send a new frame
            tcpOutput.println("1");
        }
    }
 }

  
  //Handler for Play button
  //-----------------------
  class playButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e){
        tcpOutput.println("1");
        timer.start();
        playButton.setEnabled(false);
    }
  }

  //Handler for Pause button
  //-----------------------
  class stopButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e){
        tcpOutput.println("0");
        timer.stop();
        try {
            tcpInput.close();
            tcpSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        tcpOutput.close();
        socket.close();
        stopButton.setEnabled(false);
    }
  }
}