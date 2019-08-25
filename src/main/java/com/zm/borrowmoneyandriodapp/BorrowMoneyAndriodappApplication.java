package com.zm.borrowmoneyandriodapp;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableSwagger2Doc
public class BorrowMoneyAndriodappApplication {

    public static void main(String[] args) {
        SpringApplication.run(BorrowMoneyAndriodappApplication.class, args);
    }

}
