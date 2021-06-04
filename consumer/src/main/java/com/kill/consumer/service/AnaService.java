package com.kill.consumer.service;


import java.util.List;
import java.util.Map;

public interface AnaService {

    List<Map<String, Object>> daily();

    List<Map<String, Object>> profileLocation();

    List<Map<String, Object>> HotSearch();

    List<Map<String, Object>> selectDateMoney();

    int getLike();

    int getNewProfile();

    int selectLoginNow();

    Object selectAmountDaily();

    Object selectAmountYesterday();

    Object selectAmountMonth();

    Object selectAmountSeason();

    Object selectAmountYear();

    int selectNowDays();

    int selectYestDays();

    int selectMonth();

    int selectLastMonth();

}
