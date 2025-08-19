package com.ukrohit.springwebflux.sec06.config;

import com.ukrohit.springwebflux.sec06.exception.CustomerNotFoundException;
import com.ukrohit.springwebflux.sec06.exception.InvalidInutException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Service
public class ApplicationExceptionHandler {


    public Mono<ServerResponse> handleException(CustomerNotFoundException ex, ServerRequest request) {
        var statusCode = HttpStatus.NOT_FOUND;
        var problem = ProblemDetail.forStatusAndDetail(statusCode, ex.getMessage());
        problem.setType(URI.create("http://localhost:9090/problem/ciustomernotfounds"));
        problem.setTitle("Customer not found");
        problem.setInstance(URI.create(request.path()));
        return ServerResponse.status(statusCode).bodyValue(problem);

    }


    public Mono<ServerResponse> handleException(InvalidInutException ex, ServerRequest request) {
        var statusCode = HttpStatus.BAD_REQUEST;
        var problem = ProblemDetail.forStatusAndDetail(statusCode, ex.getMessage());
        problem.setType(URI.create("http://localhost:9090/problem/ciustomernotfounds"));
        problem.setTitle("InvalidInput not found");
        problem.setInstance(URI.create(request.path()));
        return ServerResponse.status(statusCode).bodyValue(problem);

    }
}

