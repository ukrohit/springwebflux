package com.ukrohit.springwebflux.sec04.repository;

import com.ukrohit.springwebflux.sec04.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product,Integer> {

    Flux<Product> findByPriceBetween(int rangeFrom, int rangeTo);

    Flux<Product> findBy(Pageable pageable);

}
