package com.cipriano.cipripay.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalTime;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class PaymentResponse extends BaseResponse {

    private String payment_id;
    private Float value;
    private String authorization_code;
    private Date transaction_date;
    private LocalTime transaction_hour;


}
