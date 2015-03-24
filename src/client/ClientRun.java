/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

//import java.net.Socket;
//import java.net.ServerSocket;

import client.engine.ClientGUIInterface;
import javax.swing.JFrame;
import javax.swing.*;


/**
 *
 * @author vietdinh
 */
public class ClientRun {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ClientGUIInterface myFrame = new ClientGUIInterface();
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setSize(670, 530);
        myFrame.setVisible(true);
        myFrame.setResizable(false);
       
    }
    
}
