package com.cipriano.cipripay.factory;

import com.cipriano.cipripay.CipripayApplication;
import com.cipriano.cipripay.request.PaymentRequest;
import com.cipriano.cipripay.response.PaymentResponse;
import com.cipriano.cipripay.utils.ISO8583Fields;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.GenericPackager;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;


@Service
public class CreditCardPaymentFactory implements IPaymentFactory {
    public static final String PAYMENT_MODE_ONE_INSTALLMENT = "003000";
    public static final String PAYMENT_MODE_MANY_INSTALLMENTS = "003001";
    public static final String ONE_INSTALLMENT = "01";
    public static final String ISO_1987 = "ISO1987";
    public static final String MTI_CODE = "0200";
    public static final String DATE_FORMAT = "MMddHHmmss";
    public static final String HOUR_FORMAT = "HHmmss";
    public static final String MONTH_DAY = "MMdd";

    @Override
    public byte[] createPayment(PaymentRequest paymentRequest) throws ISOException {
        InputStream inputStream = CipripayApplication.class.getResourceAsStream("/fields.xml");
        GenericPackager genericPackager = new GenericPackager(inputStream);

        ISOMsg isoMsg = new ISOMsg();
        isoMsg.setPackager(genericPackager);

        isoMsg.setHeader(ISO_1987.getBytes());
        isoMsg.setMTI(MTI_CODE);


        //CreditCard number
        isoMsg.set(ISO8583Fields.PRIMARY_ACCOUNT_NUMBER, paymentRequest.getCard_number());

        //Installments quantity at first glance
        isoMsg.set(ISO8583Fields.EXTENDED_PAYMENT_CODE, ONE_INSTALLMENT);
        isoMsg.set(ISO8583Fields.PROCESSING_CODE, PAYMENT_MODE_ONE_INSTALLMENT);

        if(!paymentRequest.getInstallments().toString().equals(ONE_INSTALLMENT)) {
            isoMsg.set(ISO8583Fields.PROCESSING_CODE, PAYMENT_MODE_MANY_INSTALLMENTS);
            isoMsg.set(ISO8583Fields.EXTENDED_PAYMENT_CODE, paymentRequest.getInstallments().toString());
        }

        isoMsg.set(ISO8583Fields.TRANSACTION_AMOUNT, String.valueOf(paymentRequest.getValue()));
        isoMsg.set(ISO8583Fields.TRANSMISSION_DATE_AND_TIME, LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT)));
        isoMsg.set(ISO8583Fields.SYSTEM_TRACE_AUDIT_NUMBER, this.generateUID());
        isoMsg.set(ISO8583Fields.LOCAL_TRANSACTION_TIME, LocalDateTime.now().format(DateTimeFormatter.ofPattern(HOUR_FORMAT)));
        isoMsg.set(ISO8583Fields.LOCAL_TRANSACTION_DATE, LocalDateTime.now().format(DateTimeFormatter.ofPattern(MONTH_DAY)));
        isoMsg.set(ISO8583Fields.EXPIRATION_DATE, paymentRequest.getExp_year() + paymentRequest.getExp_month());
        isoMsg.set(ISO8583Fields.PAN_EXTENDED, "0");

        //Todo: value below must be a variable, but I am just gonna stick with this value
        isoMsg.set(ISO8583Fields.POINT_OF_SERVICE_ENTRY_MODE, "210");
        //Todo: value below must be a variable

        isoMsg.set(ISO8583Fields.CARD_ACCEPTOR_IDENTIFICATION_CODE, "123456");
        isoMsg.set(ISO8583Fields.ADDITIONAL_DATA_PRIVATE, paymentRequest.getCvv());
        isoMsg.dump(System.out, "");
        return isoMsg.pack();
    }

    @Override
    public PaymentResponse convertToPaymentResponse(byte[] transactionResponse) throws ISOException {
        ISOMsg isoMsg = new ISOMsg();
        InputStream inputStream = CipripayApplication.class.getResourceAsStream("/fields.xml");
        GenericPackager genericPackager = new GenericPackager(inputStream);
        isoMsg.setPackager(genericPackager);
        isoMsg.unpack(transactionResponse);

        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setPayment_id("123");
        paymentResponse.setValue(Float.valueOf(isoMsg.getString(ISO8583Fields.TRANSACTION_AMOUNT)));
        paymentResponse.setAuthorization_code("0200");
        paymentResponse.setTransaction_hour(LocalTime.parse(isoMsg.getString(ISO8583Fields.TRANSMISSION_DATE_AND_TIME)));
        paymentResponse.setTransaction_date(new Date());

        return paymentResponse;
    }

    private String generateUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().substring(0, 6);
    }
}
