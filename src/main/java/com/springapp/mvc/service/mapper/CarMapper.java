package com.springapp.mvc.service.mapper;

import com.springapp.mvc.dto.Car;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarMapper implements RowMapper<Car> {
    @Override
    public Car mapRow(ResultSet rs, int rowNum) throws SQLException {
        Car car = new Car();
        car.setId(rs.getLong("id"));
        car.setManufactor(rs.getString("manufactor"));
        car.setModel(rs.getString("model"));
        car.setColor(rs.getString("color"));
        car.setDoorQuantity(rs.getInt("door_quantity"));
        car.setCreateDate(rs.getDate("create_date"));
        return car;
    }
}
