package com.ukrohit.springwebflux.sec02.service.impl;

import com.ukrohit.springwebflux.sec02.dto.CustomerDto;
import com.ukrohit.springwebflux.sec02.entity.Customer;
import com.ukrohit.springwebflux.sec02.mapper.CustomerMapper;
import com.ukrohit.springwebflux.sec02.repository.CustomerRepository;
import com.ukrohit.springwebflux.sec02.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
public class CustomerServiceImpl implements CustomerService {

    public static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    private static Function<Customer, CustomerDto> getCustomerToDto() {
        return CustomerMapper::customerToDto;
    }

    @Override
    public Mono<CustomerDto> createCustomer(Mono<CustomerDto> customerMono) {
        return customerMono
                .map(CustomerMapper::dtoToCustomer)
                .flatMap(customerRepository::save)
                .map(getCustomerToDto());
    }

    @Override
    public Mono<CustomerDto> update(Integer customerId, Mono<CustomerDto> customerMono) {
        return customerRepository.findById(customerId)
                .flatMap(c ->
                        customerMono
                                .map(cMono -> {
                                    return new Customer(customerId, cMono.getName(), cMono.getEmail());
                                })
                                .flatMap(customerRepository::save)
                ).map(getCustomerToDto());

    }

    @Override
    public Mono<CustomerDto> getCustomer(Integer customerId) {
        return customerRepository.findById(customerId)
                .map(getCustomerToDto());
    }

    @Override
    public Flux<CustomerDto> getAllCustomer() {
        return customerRepository.findAll()
                .map(getCustomerToDto());
    }
}
