package com.springapp.mvc.web;

import com.springapp.mvc.dto.ExceptionDescription;
import com.springapp.mvc.dto.JOOQQueryExecuteException;
import com.springapp.mvc.dto.QueryBuildException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandlerController {

    public static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ExceptionDescription> handleNullPointerException(HttpServletRequest request,
                                                                           NullPointerException e) {
        logger.error("NullPointerException handled. " + e.getMessage());
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ExceptionDescription exceptionDescription = buildExceptionDescription(httpStatus,
                request.getRequestURL().toString(), e);
        return new ResponseEntity <ExceptionDescription>(exceptionDescription, httpStatus);
    }

    @ExceptionHandler(QueryBuildException.class)
    public ResponseEntity<ExceptionDescription> handleQueryBuildException(HttpServletRequest request,
                                                                      QueryBuildException e) {
        logger.error("QueryBuildException handled. " + e.getMessage());
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ExceptionDescription exceptionDescription = buildExceptionDescription(httpStatus,
                request.getRequestURL().toString(), e);
        return new ResponseEntity<ExceptionDescription>(exceptionDescription, httpStatus);
    }

    @ExceptionHandler(JOOQQueryExecuteException.class)
    public ResponseEntity<ExceptionDescription> handleJOOQQueryExecuteException(HttpServletRequest request,
                                                                          JOOQQueryExecuteException e) {
        logger.error("JOOQQueryExecuteException handled. " + e.getMessage());
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ExceptionDescription exceptionDescription = buildExceptionDescription(httpStatus,
                request.getRequestURL().toString(), e);
        return new ResponseEntity<ExceptionDescription>(exceptionDescription, httpStatus);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionDescription> handleIllegalArgumentException (HttpServletRequest request,
                                                                                IllegalArgumentException e) {
        logger.error("IllegalArgumentException handled. " + e.getMessage());
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ExceptionDescription exceptionDescription = buildExceptionDescription(httpStatus,
                request.getRequestURL().toString(), e);
        return new ResponseEntity<ExceptionDescription>(exceptionDescription, httpStatus);
    }

    private ExceptionDescription buildExceptionDescription(HttpStatus httpStatus, String url, Exception e) {
        ExceptionDescription exceptionDescription = new ExceptionDescription();
        exceptionDescription.setUrl(url);
        exceptionDescription.setHttpStatus(httpStatus);
        exceptionDescription.setMessage(e.getMessage());
        return exceptionDescription;
    }
}
