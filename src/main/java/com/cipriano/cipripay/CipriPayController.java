package com.cipriano.cipripay;

import com.cipriano.cipripay.request.PaymentRequest;
import com.cipriano.cipripay.response.PaymentResponse;
import com.cipriano.cipripay.service.PaymentService;
import com.cipriano.cipripay.utils.POSEntryModes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cipripay")
public class CipriPayController {

    private static final Logger LOGGER = LogManager.getLogger(CipripayApplication.class);


    @Autowired
    private PaymentService paymentService;

    @PostMapping("/authorization")
    public String doPayment(@RequestBody PaymentRequest paymentRequest) {
        LOGGER.info("Received payment authorization request");
        PaymentResponse paymentResponse = new PaymentResponse();
        try {
            this.paymentService.pay(paymentRequest);

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            paymentResponse.setMessage("Denied Transaction");
            paymentResponse.setCode("400");
        }

        return "Ok";
    }
}
