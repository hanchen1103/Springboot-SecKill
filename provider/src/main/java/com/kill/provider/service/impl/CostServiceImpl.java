package com.kill.provider.service.impl;

import com.alibaba.dubbo.common.utils.Assert;
import com.alibaba.dubbo.config.annotation.Service;
import com.kill.api.model.spiderProduct;
import com.kill.api.service.CostService;
import com.kill.provider.mapper.SpiderDAO;
import com.kill.provider.mapper.StockDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
@org.springframework.stereotype.Service
public class CostServiceImpl implements CostService {

    private static final Logger logger = LoggerFactory.getLogger(CostServiceImpl.class);

    @Autowired
    SpiderDAO spiderDAO;

    @Autowired
    RedisTemplate<String, String> redis;

    @Override
    public Integer add(int cost) {
        return cost;
    }

    @Override
    public List<spiderProduct> spider(String name) {
        try {
            if(!redis.opsForSet().isMember("spider", name)) {
                redis.opsForSet().add("spider", name);
                SpiderHelp(name);
            }
        } catch (IOException | InterruptedException e) {
            logger.error(e.getMessage());
        }

        return spiderDAO.selectByname(name);
    }

    @Override
    public int delete() {
        redis.delete("spider");
        return spiderDAO.deletetable();
    }

    public void SpiderHelp(String name) throws IOException, InterruptedException {
        String[] args1 = new String[]{"python", "provider/src/main/resources/spider/spider.py", name};
        System.out.println("spider start");
        Process pr = Runtime.getRuntime().exec(args1);
        BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream(), "gb2312"));
        String line;
        while((line = in.readLine()) != null) {
            System.out.println(line);
        }
        in.close();
        pr.waitFor();
        System.out.println("spider end");
    }
}
