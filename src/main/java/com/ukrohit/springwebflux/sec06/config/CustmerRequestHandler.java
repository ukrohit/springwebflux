package com.ukrohit.springwebflux.sec06.config;

import com.ukrohit.springwebflux.sec06.dto.CustomerDto;
import com.ukrohit.springwebflux.sec06.exception.ApplicationException;
import com.ukrohit.springwebflux.sec06.service.CustomerService;
import com.ukrohit.springwebflux.sec06.validator.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class CustmerRequestHandler {

    @Autowired
    CustomerService customerService;

    Mono<ServerResponse> allCustomers(ServerRequest request) {
        //request.pathVariable
        //request.queryParam
        // request.headers()

        return this.customerService.getAllCustomer()
                .as(flux -> ServerResponse.ok()
                        .body(flux, CustomerDto.class));
    }

    Mono<ServerResponse> getCustomer(ServerRequest request) {
        Integer id = Integer.parseInt(request.pathVariable("id"));
        return this.customerService.getCustomer(id)
                .switchIfEmpty(ApplicationException.customerNotFound(id))
                .flatMap(mono -> ServerResponse.ok()
                        .bodyValue(mono));
    }

    Mono<ServerResponse> paginatedCustomer(ServerRequest request) {
        Integer pageNumber = request.queryParam("pageNumber").map(x -> Integer.parseInt(x)).orElse(1);
        Integer pagesize = request.queryParam("pagesize").map(x -> Integer.parseInt(x)).orElse(3);

        return this.customerService.findAllAsList(pageNumber, pagesize)
                .flatMap(mono -> ServerResponse.ok()
                        .bodyValue(mono));
    }

    Mono<ServerResponse> saveCustomer(ServerRequest request) {
        return request.bodyToMono(CustomerDto.class)
                .transform(RequestValidator.validate())
                .as(validatedDto -> this.customerService.createCustomer(validatedDto))
                .flatMap(mono -> ServerResponse.ok()
                        .bodyValue(mono));
    }

    Mono<ServerResponse> updateCustomer(ServerRequest request) {

        Integer id = Integer.parseInt(request.pathVariable("id"));
        return request.bodyToMono(CustomerDto.class)
                .transform(RequestValidator.validate())
                .as(validatedDto -> this.customerService.update(id, validatedDto))
                .switchIfEmpty(ApplicationException.customerNotFound(id))
                .flatMap(mono -> ServerResponse.ok()
                        .bodyValue(mono));
    }

/*    Mono<ServerResponse> deleteCustomer(ServerRequest request) {

        Integer id = Integer.parseInt(request.pathVariable("id"));
        return  this.customerService.deleteCustomerById(id)
                .filter(b->b)
                .switchIfEmpty(ApplicationException.customerNotFound(id))

    }*/

}
