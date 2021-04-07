package com.kill.consumer.config;

import com.kill.consumer.kafka.ConsumerGroup;
import com.google.gson.Gson;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.mapper.autoconfigure.MapperProperties;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
@EnableConfigurationProperties(MapperProperties.class)
public class ConsumerConfig {
    private static final Logger logger = LoggerFactory.getLogger(ConsumerConfig.class);

    @Value("${consumer.broker.list}")
    private String brokerList;

    @Value("${consumer.group.id}")
    private String groupId;

    @Value("${consumer.topic}")
    private String topic;

    @Value("${consumer.thread}")
    private int threadNum;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private List<SqlSessionFactory> sqlSessionFactoryList;

    @Autowired
    private MapperProperties mapperProperties;


    @Bean(value = "consumerGroup")
    public ConsumerGroup createCosumerGroup() {
        ConsumerGroup consumerGroup = new ConsumerGroup(threadNum, groupId, topic, brokerList);
        logger.info("ConsumerGroup 初始成功");
        return consumerGroup;
    }

    @Bean
    public Gson build() {
        return new Gson();
    }

    @PostConstruct
    public void addPageInterceptor() {
        MapperHelper mapperHelper = new MapperHelper();
        mapperHelper.setConfig(mapperProperties);
        if(mapperProperties.getMappers().size() > 0) {
            for(Class mapper : mapperProperties.getMappers()) {
                applicationContext.getBeansOfType(mapper);
                mapperHelper.registerMapper(mapper);
            }
        } else {
            applicationContext.getBeansOfType(Mapper.class);
            mapperHelper.registerMapper(Mapper.class);
        }
        for(SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
            mapperHelper.processConfiguration(sqlSessionFactory.getConfiguration());
        }
    }
}
