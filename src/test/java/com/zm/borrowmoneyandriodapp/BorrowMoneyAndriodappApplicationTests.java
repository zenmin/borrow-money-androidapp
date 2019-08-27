package com.zm.borrowmoneyandriodapp;

import com.google.common.collect.ImmutableMap;
import com.zm.borrowmoneyandriodapp.util.HttpClientUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class BorrowMoneyAndriodappApplicationTests {

    @Test
    public void contextLoads() throws IOException {
//        appkey=xxxx&phone=132****8362&zone=86&code=xxxx;
        Map<String, Object> map = ImmutableMap.of("appkey", "2c302339f63c4", "phone", "15228766049", "zone", "86", "code", "123456");
        String s = HttpClientUtil.sendPost("http://webapi.sms.mob.com/sms/verify", map);
        System.out.println(s);
    }

}
