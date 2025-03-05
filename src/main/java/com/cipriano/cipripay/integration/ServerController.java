package com.cipriano.cipripay.integration;

import org.apache.catalina.Server;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.Socket;

@Service
public class ServerController{

    ServerController() {
    }

    public byte[] doPaymentTransaction(byte[] paymentTransaction) throws IOException {
        Socket clientSocket = ServerConnection.getInstance().getSocket();
        byte[] responseBytes = null;
        while(true) {
            if(clientSocket.isConnected()) {
                OutputStream os = clientSocket.getOutputStream();
                os.write(paymentTransaction);
                os.flush();

//                responseBytes = IOUtils.toByteArray(clientSocket.getInputStream());

                break;
            }
            clientSocket.close();
        }


        return responseBytes;
    }
}
