package com.springapp.mvc.service;

import com.springapp.mvc.dto.Item;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-context.xml")
public class CarServiceTest {
    @Autowired
    private CarService carService;

    @Autowired
    private ItemBuilder itemBuilder;

    @Test
    public void testGetFullCarQuery() throws Exception {
        String queryEnd = "select color from car where id=3";
        String expectedString = "SELECT car.id, car.manufactor, car.model, car.color, " +
                "car.door_quantity, car.create_date FROM Car " +
                "LEFT JOIN driver ON driver.car_id = car.id " +
                "LEFT JOIN company ON company.id = driver.company_id" + " where id=3";
        Assert.assertEquals(expectedString, carService.getFullCarQuery(queryEnd));
    }

    @Test(expected = NullPointerException.class)
    public void testGetFullCarQueryWithNull() throws Exception {
        String expectedString = "SELECT car.id, car.manufactor, car.model, car.color, " +
                "car.door_quantity, car.create_date FROM Car " +
                "LEFT JOIN driver ON driver.car_id = car.id " +
                "LEFT JOIN company ON company.id = driver.company_id" + " where id=3";
        Assert.assertEquals(expectedString, carService.getFullCarQuery(null));
    }

    @Test
    public void testVerifyQueryString() throws Exception {
        String testString = "Maybe you know where is my mind?";
        String expectedString = "where is my mind?";
        Assert.assertEquals(expectedString, carService.verifyQueryString(testString));
    }

    @Test(expected = StringIndexOutOfBoundsException.class)
    public void testVerifyQueryStringWithoutWhere() throws Exception {
        String testString = "Hello world!";
        Assert.assertEquals(null, carService.verifyQueryString(testString));
    }

    @Test(expected = NullPointerException.class)
    public void testVerifyQueryStringWithNull() throws Exception {
        String testString = "Hello world!";
        Assert.assertEquals(testString, carService.verifyQueryString(null));
    }

    @Test
    public void testGetQueryString() throws Exception {
        Item nestedGroup = itemBuilder.build(Arrays.asList(ItemBuilder.CAR_KUGA, ItemBuilder.CAR_TOYOTA), "OR");
        Item testedItem = itemBuilder.build(Arrays.asList(ItemBuilder.CAR_RED, nestedGroup), "AND");

        String expectedString = "select * from car where (car.color = 'red' and (car.model = 'Kuga' or car" +
                ".manufactor = 'Toyota'))";
        Assert.assertEquals(expectedString, carService.getQueryString(testedItem));
    }

    @Test
    public void testGetQueryStringWithIncorrectOperator() throws Exception {
        Item nestedGroup = itemBuilder.build(Arrays.asList(ItemBuilder.CAR_KUGA, ItemBuilder.CAR_TOYOTA), "COR");
        Item testedItem = itemBuilder.build(Arrays.asList(ItemBuilder.CAR_RED, nestedGroup), "AND");

        String expectedString = "select * from car where (car.color = 'red' and car.model = 'Kuga' and car" +
                ".manufactor = 'Toyota')";
        Assert.assertEquals(expectedString, carService.getQueryString(testedItem));
    }
}
