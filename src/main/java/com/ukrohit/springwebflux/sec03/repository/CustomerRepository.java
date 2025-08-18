package com.ukrohit.springwebflux.sec03.repository;

import com.ukrohit.springwebflux.sec03.entity.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerRepository extends ReactiveCrudRepository<Customer, Integer> {

    Mono<Customer> findByName(String name);

    Flux<Customer> findByEmailEndingWith(String email);

    @Modifying
    @Query("delete from customer where id=:customerId")
    Mono<Boolean> deleteByCustomerId(Integer customerId);

    Flux<Customer> findBy(Pageable pageable);

}
