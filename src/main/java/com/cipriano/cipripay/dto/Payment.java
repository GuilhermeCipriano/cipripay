package com.cipriano.cipripay.dto;

import lombok.Data;

import java.time.LocalTime;
import java.util.Date;

@Data
public class Payment {

    private String payment_id;
    private Float value;
    private String authorization_code;
    private Date transaction_date;
    private LocalTime transaction_hour;

}
