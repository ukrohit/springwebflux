package com.ukrohit.springwebflux.sec03.service;

import com.ukrohit.springwebflux.sec03.dto.CustomerDto;
import com.ukrohit.springwebflux.sec03.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CustomerService {

    Mono<CustomerDto> createCustomer(Mono<CustomerDto> customerMono);
    Mono<CustomerDto> update(Integer customerId, Mono<CustomerDto> customerMono);
    Mono<CustomerDto> getCustomer(Integer customerId);
    Flux<CustomerDto> getAllCustomer();
    Mono<Void> deleteById(Integer customerId);
    Flux<CustomerDto> findAll(Integer pageNumber,Integer pageSize );
    Mono<Boolean> deleteCustomerById(Integer customerId);
    Mono<List<CustomerDto>> findAllAsList(Integer pageNumber, Integer pageSize );

    Mono<Page<CustomerDto>> findAllWithPage(Integer pageNumber, Integer pageSize);
}
