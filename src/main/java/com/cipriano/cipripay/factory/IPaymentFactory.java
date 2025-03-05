package com.cipriano.cipripay.factory;

import com.cipriano.cipripay.request.PaymentRequest;
import com.cipriano.cipripay.response.PaymentResponse;
import org.jpos.iso.ISOException;

public interface IPaymentFactory {

    byte[] createPayment(PaymentRequest payment) throws ISOException;

    PaymentResponse convertToPaymentResponse(byte[] transactionResponse) throws ISOException;
}
