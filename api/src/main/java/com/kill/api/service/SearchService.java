package com.kill.api.service;

import com.kill.api.model.Profile;
import com.kill.api.model.Stock;

import java.util.List;

public interface SearchService {

    List<Stock> searchStock(String q);

    List<Profile> selectProfile(String q);

}
