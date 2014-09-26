package com.springapp.mvc.dto;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Query can't be build.")
public class QueryBuildException extends Exception {
    private static final long serialVersionUID = -3L;

    public QueryBuildException() {
    }

    public QueryBuildException(String message) {
        super(message);
    }
}
