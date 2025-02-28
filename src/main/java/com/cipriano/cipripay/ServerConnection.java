package com.cipriano.cipripay;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerConnection {

    public Socket connectToServer(byte[] isoMsg, String host, int port) {
        System.out.println("Connecting to " + host + ":" + port);
        Socket socket = null;
        try {
            socket = new Socket(host, port);
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(isoMsg);
            outputStream.flush();
            System.out.println("Connected to " + host + ":" + port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return socket;

    }
}
