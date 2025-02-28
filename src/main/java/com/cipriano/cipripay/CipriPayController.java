package com.cipriano.cipripay;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cipripay")
public class CipriPayController {


    @PostMapping("/authorization")
    public String doPayment() {

        return "Ok";
    }
}
