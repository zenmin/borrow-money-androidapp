package com.zm.borrowmoneyandriodapp;

import com.zm.borrowmoneyandriodapp.util.IdWorker;
import com.zm.borrowmoneyandriodapp.util.StaticUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BorrowMoneyAndriodappApplicationTests {

    @Test
    public void contextLoads() {
        System.out.println(StaticUtil.md5Hex("123456"));
    }

}
