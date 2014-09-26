package com.springapp.mvc.web;

import com.springapp.mvc.dto.Car;
import com.springapp.mvc.dto.Item;
import com.springapp.mvc.dto.JOOQQueryExecuteException;
import com.springapp.mvc.dto.QueryBuildException;
import com.springapp.mvc.dto.QueryNotFoundException;
import com.springapp.mvc.service.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/car")
public class CarController {
    @Autowired
    private CarService carService;

    public static final Logger logger = LoggerFactory.getLogger(CarController.class);

    @RequestMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<String> printSQLQuery(@RequestBody Item item) throws QueryBuildException {
        String queryString = carService.getQueryString(item);
        if (queryString == null) {
            throw new QueryBuildException("Incorrect JSON content");
        } else {
            return new ResponseEntity<>(queryString, HttpStatus.ACCEPTED);
        }
    }

    @RequestMapping(value = "/filtered", method = RequestMethod.POST)
    public @ResponseBody List<Car> showInfoFilteredCars(@RequestBody Item item) throws JOOQQueryExecuteException {
        List<Car> cars = carService.getFilteredCars(item);
        if (cars == null) {
            throw new JOOQQueryExecuteException("Can't to execute query");
        } else {
            return cars;
        }
    }

    @RequestMapping(value = "/JDBCFiltered", method = RequestMethod.POST)
    public @ResponseBody List<Car> filterWithJDBCExecution(@RequestBody Item item) throws JOOQQueryExecuteException {
        List<Car> cars = carService.getJDBCFilteredCars(carService.getQueryString(item));
        if (cars == null) {
            throw new JOOQQueryExecuteException("Can't execute jOOQ query by JDBCTemplate");
        } else {
            return cars;
        }
    }

    @RequestMapping(value = "/concatQueries", method = RequestMethod.POST)
    public @ResponseBody String buildFullQuery(@RequestBody Item item) throws Exception {
        String fullCarQuery = carService.getFullCarQuery(carService.getQueryString(item));
        if (fullCarQuery == null) {
            throw new QueryNotFoundException();
        } else {
            return fullCarQuery;
        }
    }

    @RequestMapping(value = "/filteredWithFullQuery", method = RequestMethod.POST)
    public @ResponseBody List<Car> filteredWithFullQuery(@RequestBody Item item) throws JOOQQueryExecuteException {
        List<Car> cars = carService.getJDBCFilteredCars(carService.getFullCarQuery(carService.getQueryString(item)));
        if (cars == null) {
            throw new JOOQQueryExecuteException("Can't execute combine query by JDBCTemplate");
        } else {
            return cars;
        }
    }

    @ExceptionHandler(QueryNotFoundException.class)
    public @ResponseBody ResponseEntity<String> handleQueryNotFoundException(HttpServletRequest request, Exception e) {
        logger.error("Query not found", e);
        return new ResponseEntity<String>("Query not found.", HttpStatus.NOT_FOUND);
    }
}
