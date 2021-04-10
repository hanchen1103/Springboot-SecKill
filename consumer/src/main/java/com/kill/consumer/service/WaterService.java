package com.kill.consumer.service;

import com.kill.api.model.water;

import java.util.List;

public interface WaterService {

    List<water> selectByMonth(int userId);

    List<water> seleccByYear(int userId);

    List<water> selectBySeason(int userId);

    Integer addWater(water w);

    water selectById(int id);
}
