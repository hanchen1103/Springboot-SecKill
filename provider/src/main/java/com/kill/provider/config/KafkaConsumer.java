package com.kill.provider.config;

import com.alibaba.fastjson.JSON;
import com.kill.api.model.Message;
import com.kill.api.model.Stock;
import com.kill.api.service.MessageService;
import com.kill.api.service.OrderService;
import com.kill.provider.service.impl.MessageServiceImpl;
import com.kill.provider.service.impl.StockOrderServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class KafkaConsumer {

    @Autowired
    StockOrderServiceImpl stockOrderService;

    @Autowired
    MessageServiceImpl messageService;

    @KafkaListener(topics = KafkaProducer.TOPIC_TEST, groupId = KafkaProducer.TOPIC_GROUP1)
    public void topic_test(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {

        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            Object msg = message.get();
            log.info("topic_test 消费了： Topic:" + topic + ",Message:" + msg);
            ack.acknowledge();
        }
    }

    @KafkaListener(topics = KafkaProducer.TOPIC_TEST, groupId = KafkaProducer.TOPIC_GROUP2)
    public void topic_test1(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {

        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            Object msg = message.get();
            log.info("topic_test1 消费了： Topic:" + topic + ",Message:" + msg);
            ack.acknowledge();
        }
    }

    @KafkaListener(topics = KafkaProducer.TOPIC_ORDER, groupId = KafkaProducer.TOPIC_GROUP1)
    public void topic_order(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) throws Exception {

        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            String msg = String.valueOf(message.get());
            log.info("topic_order 消费了： Topic:" + topic + ",Message:" + msg);
            Stock stock = JSON.parseObject(msg, Stock.class);
            //stockOrderService.saleStockByOptimistic(stock.getSale(), stock);
            stockOrderService.createOrderUseRedis(stock.getSale(), stock.getId(), stock.getUserId(),stock.getPrice());
            ack.acknowledge();
        }
    }

    @KafkaListener(topics = KafkaProducer.TOPIC_Collect, groupId = KafkaProducer.TOPIC_GROUP2)
    public void topic_collect(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) throws Exception {

        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            String msg = String.valueOf(message.get());
            log.info("topic_order 消费了： Topic:" + topic + ",Message:" + msg);
            Message mes = JSON.parseObject(msg, Message.class);
            messageService.addMessage(mes);
            ack.acknowledge();
        }
    }

    @KafkaListener(topics = KafkaProducer.TOPIC_LIKE, groupId = KafkaProducer.TOPIC_GROUP2)
    public void topic_Like(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) throws Exception {

        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            String msg = String.valueOf(message.get());
            log.info("topic_order 消费了： Topic:" + topic + ",Message:" + msg);
            Message mes = JSON.parseObject(msg, Message.class);
            messageService.addMessage(mes);
            ack.acknowledge();
        }
    }

    @KafkaListener(topics = KafkaProducer.TOPIC_RESOURCE, groupId = KafkaProducer.TOPIC_GROUP2)
    public void topic_Resource(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) throws Exception {

        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            String msg = String.valueOf(message.get());
            log.info("topic_order 消费了： Topic:" + topic + ",Message:" + msg);
            Message mes = JSON.parseObject(msg, Message.class);
            messageService.addMessage(mes);
            ack.acknowledge();
        }
    }
}