package com.cipriano.cipripay.integration;

import com.cipriano.cipripay.CipripayApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import java.io.IOException;
import java.net.Socket;

public class ServerConnection {
    private static final Logger LOGGER = LogManager.getLogger(ServerConnection.class);

    private static ServerConnection instance;
    private Socket socket;
    private static final int PORT = 8081;
    private static final String IP_ADDRESS =  "localhost";

    private ServerConnection() {
        // Private constructor to prevent external instantiation
    }

    public static ServerConnection getInstance() {
        if (instance == null) {
            synchronized (ServerConnection.class) {
                if (instance == null) {
                    instance = new ServerConnection();
                }
            }
        }
        return instance;
    }

    public Socket getSocket() throws IOException {
        if (socket == null || socket.isClosed()) {
            synchronized (this) {
                if (socket == null || socket.isClosed()) {
                    socket = new Socket(IP_ADDRESS, PORT);
                }
            }
        }
        return socket;
    }

    public void closeSocket() throws IOException {
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }
}







