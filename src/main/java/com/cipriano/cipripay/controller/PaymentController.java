package com.cipriano.cipripay.controller;

import com.cipriano.cipripay.CipripayApplication;
import com.cipriano.cipripay.request.PaymentRequest;
import com.cipriano.cipripay.response.PaymentResponse;
import com.cipriano.cipripay.service.interfaces.IPaymentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cipripay")
public class PaymentController {

    private static final Logger LOGGER = LogManager.getLogger(CipripayApplication.class);


    @Autowired
    private IPaymentService paymentService;

    @PostMapping("/authorization")
    public ResponseEntity doPayment(@RequestBody PaymentRequest paymentRequest) {
        LOGGER.info("Method: doPayment - Start");
        PaymentResponse paymentResponse;
        try {
            paymentResponse = this.paymentService.pay(paymentRequest);

        } catch (Exception e) {
            LOGGER.error("Method: doPayment - ERROR");
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>("It was not possible to complete transaction. Try again later. ".concat(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        LOGGER.info("Method: doPayment - End");

        return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
    }
}
