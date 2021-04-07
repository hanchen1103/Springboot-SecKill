package com.kill.consumer;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class ConsumerApplicationTests {

    @Value("$redis.limit")
    private int limit;

    @Test
    void contextLoads() {
        System.out.println(limit);
    }

}
