package com.cipriano.cipripay.service;

import com.cipriano.cipripay.CipripayApplication;
import com.cipriano.cipripay.factory.CreditCardPaymentFactory;
import com.cipriano.cipripay.factory.IPaymentFactory;
import com.cipriano.cipripay.integration.ServerController;
import com.cipriano.cipripay.request.PaymentRequest;
import com.cipriano.cipripay.response.PaymentResponse;
import com.cipriano.cipripay.service.interfaces.IPaymentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpos.iso.ISOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PaymentService implements IPaymentService {

    private static final Logger LOGGER = LogManager.getLogger(CipripayApplication.class);

    @Autowired
    ServerController serverController;

    IPaymentFactory creditCardPaymentFactory;


    PaymentService() {
        this.creditCardPaymentFactory = new CreditCardPaymentFactory();
    }

    @Override
    public PaymentResponse pay(PaymentRequest paymentRequest) throws ISOException, IOException {
        byte[] paymentTransaction = creditCardPaymentFactory.createPayment(paymentRequest);
        byte[] transactionResponse = serverController.doPaymentTransaction(paymentTransaction);
        return creditCardPaymentFactory.convertToPaymentResponse(transactionResponse);

    }



}
