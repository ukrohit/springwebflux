package com.ukrohit.springwebflux.sec02;

import com.ukrohit.springwebflux.sec02.repository.CustomerOrderRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

public class Lec03CustomOrderTest extends  AbstractTest{

    public static final Logger logger= LoggerFactory.getLogger(Lec03CustomOrderTest.class);

    @Autowired
    CustomerOrderRepository customerOrderRepository;

    @Test
    public void getProductOrderByCustomerTest()
    {
        this.customerOrderRepository.getProductOrderbyCustomerName("mike")
                .as(productFlux -> StepVerifier.create(productFlux))
                .expectNextCount(2)
                .expectComplete()
                .verify();
    }
}
