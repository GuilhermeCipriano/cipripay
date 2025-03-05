package com.cipriano.cipripay;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CipripayApplication {

    private static final Logger LOGGER = LogManager.getLogger(CipripayApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CipripayApplication.class, args);
    }

}
