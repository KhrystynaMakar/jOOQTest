package com.springapp.mvc.dto;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Query Not Found")
public class QueryNotFoundException extends Exception{
    private static final long serialVersionUID = -3L;
}
