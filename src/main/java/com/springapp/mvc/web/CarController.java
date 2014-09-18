package com.springapp.mvc.web;

import com.springapp.mvc.dto.Car;
import com.springapp.mvc.dto.Item;
import com.springapp.mvc.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/car")
public class CarController {
    @Autowired
    private CarService carService;

    @RequestMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<String> printSQLQuery(@RequestBody Item item) {
        return new ResponseEntity<String>(carService.getQueryString(item), HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/filtered", method = RequestMethod.POST)
    public @ResponseBody List<Car> showInfoFilteredCars(@RequestBody Item item) {
        return carService.getFilteredCars(item);
    }
}
