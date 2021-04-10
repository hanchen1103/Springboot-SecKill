package com.kill.consumer;

import com.kill.consumer.controller.LoginController;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@SpringBootTest
@RunWith(SpringRunner.class)
class ConsumerApplicationTests {

    @Autowired
    LoginController userService;

    Map<String, String> map = new HashMap<>();

    @Test
    void contextLoads() {
        for(int i = 0; i < 100; i ++) {
            map.put("username", UUID.randomUUID().toString().substring(5));
            map.put("password", UUID.randomUUID().toString().substring(8));
        }
    }

}
