/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 *
 * @author vietdinh
 */
public class TCPOperation {
    
    private boolean gotError;
    private String errorMessage;
    public final ClientTCPSocket tcpSocket;
    private PrintStream out;
    private BufferedReader in;
    
    public TCPOperation(String ipAddress)
    {
        gotError = false;
        errorMessage = "";
        tcpSocket = new ClientTCPSocket(9090, ipAddress);
        try {
            out = new PrintStream(tcpSocket.getSocket().getOutputStream(),true);
            in = new BufferedReader(new InputStreamReader(tcpSocket.getSocket().getInputStream()));
        } catch (IOException ex) {
            gotError = true;
            errorMessage = ex.toString();
        }
    }
    
    public void sendData(int value)
    {
        out.write(value);
    }
    
    public String getData()
    {
        String temp = "0 0 0 0 0 0 0";
        try {
            if(in.ready())
            {
                temp = in.readLine();
            }
        } catch (IOException ex) {
            gotError = true;
            errorMessage = ex.toString();
        }
        return temp;
    }
    
    
    public boolean isError()
    {
        return gotError;
    }
    
    public String errorMess()
    {
        return errorMessage;
    }
}
