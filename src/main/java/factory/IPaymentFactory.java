package factory;

import com.cipriano.cipripay.request.PaymentRequest;
import org.jpos.iso.ISOException;

import java.io.IOException;

public interface IPaymentFactory {

    byte[] createPayment(PaymentRequest payment) throws ISOException;
}
