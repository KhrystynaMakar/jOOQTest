package com.springapp.mvc.service;

import com.springapp.mvc.dto.Car;
import com.springapp.mvc.dto.Item;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-context.xml")
public class CarServiceTestIT {
    @Autowired
    private CarService carService;

    @Autowired
    private ItemBuilder itemBuilder;

    @Test
    public void testGetJDBCFilteredCars() throws Exception {
        String query = "select * from car where car.color = 'azure'";
        List<Car> cars = carService.getJDBCFilteredCars(query);
        Assert.assertEquals("azure", cars.get(0).getColor());
    }

    @Test
    public void testGetFilteredCars() {
        Item item = itemBuilder.build(Arrays.asList(ItemBuilder.CAR_RED), "OR");
        List<Car> cars = carService.getFilteredCars(item);
        Assert.assertEquals("red", cars.get(0).getColor());
    }
}
