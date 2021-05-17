package com.kill.consumer.service;

import com.kill.api.model.Profile;
import com.kill.api.model.Stock;

import java.util.List;

public interface SearchSercice {

    List<Stock> searchStock(String q);

    List<Profile> selectProfile(String q);
}
