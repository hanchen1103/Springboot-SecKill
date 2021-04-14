package com.kill.provider.util;

import com.kill.api.model.Stock;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class StockDeseralizer implements Deserializer<Stock> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public Stock deserialize(String s, byte[] bytes) {
        return (Stock) SerializeUtil.deserialize(bytes, Stock.class);
    }

    @Override
    public Stock deserialize(String topic, Headers headers, byte[] data) {
        return null;
    }

    @Override
    public void close() {

    }
}
