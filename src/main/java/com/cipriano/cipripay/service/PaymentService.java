package com.cipriano.cipripay.service;

import com.cipriano.cipripay.CipripayApplication;
import com.cipriano.cipripay.integration.ServerConnection;
import com.cipriano.cipripay.request.PaymentRequest;
import com.cipriano.cipripay.service.interfaces.IPaymentService;
import factory.CreditCardPaymentFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpos.iso.ISOException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.Socket;

@Service
public class PaymentService implements IPaymentService {

    private static final Logger LOGGER = LogManager.getLogger(CipripayApplication.class);

    ServerConnection socketIntegrationConnection;

    PaymentService() {
        this.socketIntegrationConnection = ServerConnection.getInstance();
    }

    @Override
    public void pay(PaymentRequest paymentRequest) throws ISOException, IOException {
        CreditCardPaymentFactory creditCardPaymentFactory = new CreditCardPaymentFactory();
        byte[] isoMsg = creditCardPaymentFactory.createPayment(paymentRequest);
        Socket hostConnection = this.socketIntegrationConnection.getSocket();
        hostConnection.setKeepAlive(true);
        String returnData = handleRequest(hostConnection, isoMsg);
    }

    public String handleRequest(Socket socketHostConnection, byte[] isoMsg) {
        try {
            if(socketHostConnection.isConnected()) {
                sendData(socketHostConnection, isoMsg);
                receiveDaata(socketHostConnection);
                socketHostConnection.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Ok";

    }

    private static void receiveDaata(Socket socketHostConnection) throws IOException {
        InputStream response = socketHostConnection.getInputStream();
//        BufferedReader reader = new BufferedReader(new InputStreamReader(response));
//        String str = reader.readLine();
//        System.out.println(str +  "IT FUCKING WORKED.");
    }

    private static void sendData(Socket socketHostConnection, byte[] isoMsg) throws IOException {
        OutputStream outputStream = socketHostConnection.getOutputStream();
        outputStream.write(isoMsg);
    }
}
