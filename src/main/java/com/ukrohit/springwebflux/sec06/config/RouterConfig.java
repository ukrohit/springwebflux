package com.ukrohit.springwebflux.sec06.config;

import com.ukrohit.springwebflux.sec06.exception.CustomerNotFoundException;
import com.ukrohit.springwebflux.sec06.exception.InvalidInutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {

    @Autowired
    CustmerRequestHandler custmerRequestHandler;

    @Autowired
    ApplicationExceptionHandler excveptionHandler;


    @Bean
    public RouterFunction<ServerResponse> customerRoutes() {
        return RouterFunctions.route()
                .GET("/customer", this.custmerRequestHandler::allCustomers)
                .GET("/customer/{id}", this.custmerRequestHandler::getCustomer)
                .POST("/customer", this.custmerRequestHandler::saveCustomer)
                .PUT("/customer/{id}", this.custmerRequestHandler::updateCustomer)
                .onError(CustomerNotFoundException.class,(ex,req)->this.excveptionHandler.handleException(ex,req))
                .onError(InvalidInutException.class,(ex, req)->this.excveptionHandler.handleException(ex,req))

                .build();
    }
}
