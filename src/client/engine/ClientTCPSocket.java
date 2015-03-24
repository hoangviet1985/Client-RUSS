/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.engine;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 *
 * @author vietdinh
 */
public class ClientTCPSocket {
    private Socket s;
    private boolean gotError;
    private String errorMessage;
    
    public ClientTCPSocket(int port, String ipAddress)
    {
        gotError = false;
        errorMessage = "";
        try
        {
            s = new Socket();
            SocketAddress serverAddress = new InetSocketAddress(ipAddress, port);
            s.connect(serverAddress, 5000);
        }
        catch(IOException ioe)
        {
            gotError = true;
            errorMessage = ioe.toString();
        }
    }
    
    public Socket getSocket()
    {
        return s;
    }
}
