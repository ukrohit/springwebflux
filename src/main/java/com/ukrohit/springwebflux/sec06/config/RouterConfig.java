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
                .path("customer", this::customerRoutes2) // This is for nested router function
                /* .GET("/customer", this.custmerRequestHandler::allCustomers)
                 .GET("/customer/paginated", this.custmerRequestHandler::getCustomer)
                 .GET("/customer/{id}", this.custmerRequestHandler::getCustomer)
                */.POST("/customer", this.custmerRequestHandler::saveCustomer)
                .PUT("/customer/{id}", this.custmerRequestHandler::updateCustomer)
                .onError(CustomerNotFoundException.class, (ex, req) -> this.excveptionHandler.handleException(ex, req))
                .onError(InvalidInutException.class, (ex, req) -> this.excveptionHandler.handleException(ex, req))
                /*.filter(((request, next) -> {
                    //return next.handle(request); order 1
                    return ServerResponse.badRequest().build();
                }))
                .filter(((request, next) -> {
                            //return next.handle(request);order 2
                            return ServerResponse.badRequest().build();
                        })
                )*/


                .build();
    }

    // @Bean
  /*  public RouterFunction<ServerResponse> customerRoutes2() {
        return RouterFunctions.route()
                .GET("/customer", this.custmerRequestHandler::allCustomers)
                .GET("/customer/paginated", this.custmerRequestHandler::getCustomer)
                .GET("/customer/{id}", this.custmerRequestHandler::getCustomer)
                .build();
    }*/

    public RouterFunction<ServerResponse> customerRoutes2() {
        return RouterFunctions.route()
                .GET("/paginated", this.custmerRequestHandler::getCustomer)
                .GET("/{id}", this.custmerRequestHandler::getCustomer)
                .GET("", this.custmerRequestHandler::allCustomers)
                .build();
    }
}
