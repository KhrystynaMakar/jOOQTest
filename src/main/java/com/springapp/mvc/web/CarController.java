package com.springapp.mvc.web;

import com.springapp.mvc.dto.Item;
import com.springapp.mvc.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/car")
public class CarController {
    @Autowired
    private CarService carService;

    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public @ResponseBody String printWelcome(@RequestBody Item item) {
        return carService.findByColor(item);
    }
}
