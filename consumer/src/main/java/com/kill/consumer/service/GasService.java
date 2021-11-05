package com.kill.consumer.service;

import java.util.List;

public interface GasService {

    List<Gas> selectByMonth(int userId);

    List<Gas> seleccByYear(int userId);

    List<Gas> selectBySeason(int userId);

    Integer addGas(Gas g);

    Gas selectById(int id);

}
