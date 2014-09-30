package com.springapp.mvc.service;

import com.springapp.mvc.dto.Car;
import com.springapp.mvc.dto.Item;
import com.springapp.mvc.service.mapper.CarMapper;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CarService {

    public static final int CAR_ID_COLUMN = 0;
    public static final int CAR_MANUFACTOR_COLUMN = 1;
    public static final int CAR_CREATE_DATE_COLUMN = 2;
    public static final int CAR_MODEL_COLUMN = 3;
    public static final int CAR_COLOR_COLUMN = 4;
    public static final int CAR_DOOR_QUANTITY_COLUMN = 5;

    public static final Logger logger = LoggerFactory.getLogger(CarService.class);

    public static final String FULL_CAR_QUERY = "SELECT car.id, car.manufactor, car.model, car.color, " +
            "car.door_quantity, car.create_date FROM Car " +
            "LEFT JOIN driver ON driver.car_id = car.id " +
            "LEFT JOIN company ON company.id = driver.company_id";

    @Autowired
    private QueryBuildService queryBuildService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String getQueryString(Item item) throws IllegalArgumentException {
        return queryBuildService.getQueryString(item);
    }

    public List<Car> getFilteredCars(Item item) {
        List<Car> cars = new ArrayList<Car>();
        Result<Record> records = queryBuildService.getQuery(item).fetch();
        for (Record record : records) {
            Car car = new Car();
            car.setId((Long)record.getValue(CAR_ID_COLUMN));
            car.setManufactor((String)record.getValue(CAR_MANUFACTOR_COLUMN));
            car.setCreateDate((Date) record.getValue(CAR_CREATE_DATE_COLUMN));
            car.setModel((String) record.getValue(CAR_MODEL_COLUMN));
            car.setColor((String)record.getValue(CAR_COLOR_COLUMN));
            car.setDoorQuantity((Integer)record.getValue(CAR_DOOR_QUANTITY_COLUMN));
            cars.add(car);
        }
        return cars;
    }

    public List<Car> getJDBCFilteredCars(String queryString) {
        return jdbcTemplate.query(queryString, new CarMapper());
    }

    public String getFullCarQuery(String jooqQuery) {
        return FULL_CAR_QUERY + " " + verifyQueryString(jooqQuery);
    }

    protected String verifyQueryString(String jooqQuery) {
        int whereIndex = jooqQuery.indexOf("where");
        return jooqQuery.substring(whereIndex);
    }
}
