package com.kill.provider.config;

import com.kill.api.model.Stock;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.brokerList}")
    private String brokerList;

    @Value("${spring.kafka.swicth}")
    private boolean check;

    @Bean
    public KafkaProducer<String,Stock> build(){

        if (!check){
            return null ;
        }

        //初始化生产者
        Map<String, Object> props = new HashMap<>(16);
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("metadata.broker.list", brokerList);
        props.put("bootstrap.servers", brokerList);
        props.put("key.serializer", StringSerializer.class);
        props.put("value.serializer", JsonSerializer.class);
        KafkaProducer<String, Stock> producer = new KafkaProducer(props);
        return producer ;
    }

}
