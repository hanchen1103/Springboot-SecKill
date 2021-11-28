package com.kill.provider;

import com.kill.provider.config.KafkaProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProviderApplicationTests {

    @Autowired
    KafkaProducer kafkaProducer;



    @Test
    void contextLoads() {

    }

}
