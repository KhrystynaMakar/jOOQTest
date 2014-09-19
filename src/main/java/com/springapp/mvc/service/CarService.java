package com.springapp.mvc.service;

import com.springapp.mvc.dto.Car;
import com.springapp.mvc.dto.Item;
import com.springapp.mvc.service.mapper.CarMapper;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.jooqtest.model.tables.Car.CAR;

@Service
public class CarService {
    @Autowired
    private QueryBuildService queryBuildService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String getQueryString(Item item) {
        return queryBuildService.getQueryString(item);
    }

    public List<Car> getFilteredCars(Item item) {
        List<Car> cars = new ArrayList<Car>();
        Result<Record> records = queryBuildService.getQuery(item).fetch();
        for (Record record : records) {
            Car car = new Car();
            car.setId(record.getValue(CAR.ID));
            car.setManufactor(record.getValue(CAR.MANUFACTOR));
            car.setModel(record.getValue(CAR.MODEL));
            car.setCreateDate(record.getValue(CAR.CREATE_DATE));
            car.setColor(record.getValue(CAR.COLOR));
            car.setDoorQuantity(record.getValue(CAR.DOOR_QUANTITY));
            cars.add(car);
        }
        return cars;
    }

    public List<Car> getJDBCFilteredCars(String queryString) {
        return jdbcTemplate.query(queryString, new CarMapper());
    }
}
