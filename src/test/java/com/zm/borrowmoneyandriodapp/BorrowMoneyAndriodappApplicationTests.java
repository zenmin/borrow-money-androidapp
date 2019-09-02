package com.zm.borrowmoneyandriodapp;

import com.google.common.collect.ImmutableMap;
import com.zm.borrowmoneyandriodapp.util.SmsUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Random;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class BorrowMoneyAndriodappApplicationTests {

    @Autowired
    SmsUtil smsUtil;


    @Test
    public void contextLoads() throws IOException {
//        smsUtil.sendSms("15228766049",ImmutableMap.of("code",123456));
    }

}
