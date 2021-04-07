package com.kill.consumer.kafka;

import com.kill.consumer.service.StockOrderService;
import com.kill.consumer.util.SpringBeanFactory;
import com.google.gson.Gson;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Properties;

public class ConsumerTask implements Runnable {
    private static Logger LOGGER = LoggerFactory.getLogger(ConsumerTask.class);


    /**
     * 每个线程维护KafkaConsumer实例
     */
    private final KafkaConsumer<String, String> consumer;

    private Gson gson ;

    private StockOrderService orderService;

    public ConsumerTask(String brokerList, String groupId, String topic) {
        this.gson = SpringBeanFactory.getBean(Gson.class) ;
        this.orderService = SpringBeanFactory.getBean(StockOrderService.class) ;

        Properties props = new Properties();
        props.put("bootstrap.servers", brokerList);
        props.put("group.id", groupId);
        //自动提交位移
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");




        this.consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(topic));
    }


    @Override
    public void run() {
//        boolean flag = true;
//        while (flag) {
//            // 使用200ms作为获取超时时间
//            ConsumerRecords<String, String> records = consumer.poll(200);
//
//            for (ConsumerRecord<String, String> record : records) {
//                // 简单地打印消息
//                LOGGER.info("==="+record.value() + " consumed " + record.partition() +
//                        " message with offset: " + record.offset());
//
//                dealMessage(record.value()) ;
//            }
//        }


    }

    /**
     * @param value
     */
//    private void dealMessage(String value) {
//        try {
//
//            Stock stock = gson.fromJson(value, Stock.class);
//            LOGGER.info("consumer stock={}", JSON.toJSONString(stock));
//
//            //创建订单
//            orderService.createOrderUseRedis(stock.getId());
//
//        }catch (RejectedExecutionException e){
//            LOGGER.error("rejected message = " + value);
//        }catch (Exception e){
//            LOGGER.error("unknown exception",e);
//        }
//
//    }


}