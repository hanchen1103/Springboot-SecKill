package com.kill.provider.util;

import com.kill.api.model.Stock;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

import java.io.Serializable;
import java.util.Map;

public class StockSeralizer implements Serializer<Stock> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String s, Stock stock) {
        return SerializeUtil.serialize(stock);
    }

    @Override
    public byte[] serialize(String topic, Headers headers, Stock data) {
        return new byte[0];
    }

    @Override
    public void close() {

    }
}
