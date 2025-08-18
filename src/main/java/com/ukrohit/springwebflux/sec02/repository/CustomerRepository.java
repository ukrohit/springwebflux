package com.ukrohit.springwebflux.sec02.repository;

import com.ukrohit.springwebflux.sec02.entity.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerRepository extends ReactiveCrudRepository<Customer, Integer> {

    Mono<Customer> findByName(String name);

    Flux<Customer> findByEmailEndingWith(String email);
}
