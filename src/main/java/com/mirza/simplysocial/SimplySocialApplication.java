package com.mirza.simplysocial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class SimplySocialApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimplySocialApplication.class, args);
    }

}
