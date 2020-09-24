package com.zyxm;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BotToolApplicationTests {

    @Test
    void contextLoads() {
        // 左移n位，即乘以2的n次方
        int i = 11;
        i = i << 2;
        System.out.println("i = " + i);

        // 右移n位，即除以2的n次方
        int i2 = 20;
        i2 = i2 >> 2;
        System.out.println("i2 = " + i2);
    }

}
