package com.cipriano.cipripay.response;

import lombok.Data;

import java.time.LocalTime;
import java.util.Date;

@Data
public class PaymentResponse {
    private String payment_id;
    private Float value;
    private String authorization_code;
    private Date transaction_date;
    private LocalTime transaction_hour;
}
