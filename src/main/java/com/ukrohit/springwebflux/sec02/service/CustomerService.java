package com.ukrohit.springwebflux.sec02.service;

import com.ukrohit.springwebflux.sec02.dto.CustomerDto;
import com.ukrohit.springwebflux.sec02.entity.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {

    Mono<CustomerDto> createCustomer(Mono<CustomerDto> customerMono);
    Mono<CustomerDto> update(Integer customerId,Mono<CustomerDto> customerMono);
    Mono<CustomerDto> getCustomer(Integer customerId);
    Flux<CustomerDto> getAllCustomer();

}
