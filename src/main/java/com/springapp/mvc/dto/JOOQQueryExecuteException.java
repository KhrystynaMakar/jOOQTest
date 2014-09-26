package com.springapp.mvc.dto;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Can't connect to DB")
public class JOOQQueryExecuteException extends Exception {
    private static final long serialVersionUID = -3L;

    public JOOQQueryExecuteException() {
    }

    public JOOQQueryExecuteException(String message) {
        super(message);
    }
}
