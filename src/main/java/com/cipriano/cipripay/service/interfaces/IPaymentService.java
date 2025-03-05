package com.cipriano.cipripay.service.interfaces;

import com.cipriano.cipripay.request.PaymentRequest;
import com.cipriano.cipripay.response.PaymentResponse;
import org.jpos.iso.ISOException;
import org.springframework.stereotype.Component;

import java.io.IOException;

public interface IPaymentService {

    PaymentResponse pay(PaymentRequest paymentRequest) throws ISOException, IOException;
}
