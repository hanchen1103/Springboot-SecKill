package com.kill.api.service;

import com.kill.api.model.power;

import java.util.List;

public interface PowerService {

    List<power> selectByMonth(int userId);

    List<power> seleccByYear(int userId);

    List<power> selectBySeason(int userId);

    Integer addpower(power p);

    power selectById(int id);
}
