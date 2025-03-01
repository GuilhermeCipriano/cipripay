package com.cipriano.cipripay.service.interfaces;

import com.cipriano.cipripay.request.PaymentRequest;
import org.jpos.iso.ISOException;

import java.io.IOException;

public interface IPaymentService {

    public void pay(PaymentRequest paymentRequest) throws ISOException, IOException;
}
