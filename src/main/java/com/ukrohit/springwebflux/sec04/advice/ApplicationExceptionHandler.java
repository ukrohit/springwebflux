package com.ukrohit.springwebflux.sec04.advice;

import com.ukrohit.springwebflux.sec04.exception.CustomerNotFoundException;
import com.ukrohit.springwebflux.sec04.exception.InvalidInutException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ProblemDetail handleException(CustomerNotFoundException ex) {

        var problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setType(URI.create("http://localhost:9090/problem/ciustomernotfounds"));
        problem.setTitle("Customer not found");

        return problem;

    }

    @ExceptionHandler(InvalidInutException.class)
    public ProblemDetail handleException(InvalidInutException ex) {

        var problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problem.setType(URI.create("http://localhost:9090/problem/ciustomernotfounds"));
        problem.setTitle("InvalidInput not found");
        return problem;

    }
}

