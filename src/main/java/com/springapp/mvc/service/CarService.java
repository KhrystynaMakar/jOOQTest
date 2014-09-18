package com.springapp.mvc.service;

import com.springapp.mvc.dto.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService {
    @Autowired
    private QueryBuildService queryBuildService;

    public String getQueryString(Item item) {
        //QueryBuildService queryBuildService = new QueryBuildService();
        return queryBuildService.getQueryString(item);
    }
}
