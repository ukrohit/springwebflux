package com.ukrohit.springwebflux.sec02;

import com.ukrohit.springwebflux.sec02.entity.Customer;
import com.ukrohit.springwebflux.sec02.repository.CustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

public class Lec01CustomerRepositoryTest extends AbstractTest {

    public static final Logger logger = LoggerFactory.getLogger(Lec01CustomerRepositoryTest.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void findAllCustomer() {
        this.customerRepository.findAll()
                .doOnNext(c -> logger.info("Customer : {}", c))
                .as(customer -> StepVerifier.create(customer))
                .expectNextCount(10)
                .expectComplete()
                .verify();
    }

    @Test
    public void findbyId() {
        this.customerRepository.findById(2)
                .doOnNext(c -> logger.info("Customer : {}", c))
                .as(customer -> StepVerifier.create(customer))
                .assertNext(c -> Assertions.assertThat(c.name().equals("mike")))
                .expectComplete()
                .verify();
    }

    @Test
    public void findbyName() {
        this.customerRepository.findByName("mike")
                .doOnNext(c -> logger.info("Customer : {}", c))
                .as(customer -> StepVerifier.create(customer))
                .assertNext(c -> Assertions.assertThat(c.email().equals("mike@gmail.com")))
                .expectComplete()
                .verify();
    }

    @Test
    public void findbyEmail() {
        this.customerRepository.findByEmailEndingWith("ke@gmail.com")
                .doOnNext(c -> logger.info("Customer : {}", c))
                .as(customer -> StepVerifier.create(customer))
                .assertNext(c -> Assertions.assertThat(c.name().equals("mike")))
                .assertNext(c -> Assertions.assertThat(c.name().equals("jake")))
                .expectComplete()
                .verify();
    }

    @Test
    public void insertAndDelete() {
        // insert
        Customer customer = new Customer(null, "demo", "demo@gmail.com");
        this.customerRepository.save(customer)
                .as(customerMono -> StepVerifier.create(customerMono))
                .assertNext(c -> Assertions.assertThat(c.id()).isNotNull())
                .expectComplete()
                .verify();

        // get
        this.customerRepository.findAll()
                .as(c -> StepVerifier.create(c))
                .expectNextCount(11)
                .expectComplete()
                .verify();

        // delete
        this.customerRepository.deleteById(11)
                .then(this.customerRepository.count())
                .as(c -> StepVerifier.create(c))
                .expectNext(10l)
                .expectComplete()
                .verify();


    }

    @Test
    public void updateCustomer()
    {
        this.customerRepository.findByName("ethan")
                .doOnNext(customer -> new Customer(customer.id(),"manoj",customer.email()))
                .flatMap(updatedCustomer->this.customerRepository.save(updatedCustomer))
                .as(cus->StepVerifier.create(cus))
                .assertNext(c->Assertions.assertThat(c.name().equals("manoj")))
                .expectComplete()
                .verify();
    }
}


