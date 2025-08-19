package com.ukrohit.springwebflux.sec06.service.impl;

import com.ukrohit.springwebflux.sec06.dto.CustomerDto;
import com.ukrohit.springwebflux.sec06.entity.Customer;
import com.ukrohit.springwebflux.sec06.mapper.CustomerMapper;
import com.ukrohit.springwebflux.sec06.repository.CustomerRepository;
import com.ukrohit.springwebflux.sec06.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
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
               // .doOnNext(c->c.id()==null? new RuntimeException("Id Not found "):c)
                .map(getCustomerToDto());
    }

    @Override
   public  Flux<CustomerDto> findAll(Integer pageNumber, Integer pageSize){
        return customerRepository.findBy(PageRequest.of(pageNumber-1,pageSize))
                .map(getCustomerToDto());
    }

    @Override
    public Flux<CustomerDto> getAllCustomer() {
        return customerRepository.findAll()
                .map(getCustomerToDto());
    }

    @Override
    public Mono<Void> deleteById(Integer customerId) {
        return customerRepository.deleteById(customerId);
    }

    @Override
    public Mono<Boolean> deleteCustomerById(Integer customerId) {
        return customerRepository.deleteByCustomerId(customerId);
    }

    @Override
    public Mono<List<CustomerDto>> findAllAsList(Integer pageNumber, Integer pageSize) {
        return customerRepository.findBy(PageRequest.of(pageNumber-1,pageSize))
                .map(getCustomerToDto())
                .collectList();
    }

    @Override
    public Mono<Page<CustomerDto>> findAllWithPage(Integer pageNumber, Integer pageSize) {

        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return customerRepository.findBy(pageRequest)
                .map(getCustomerToDto())
                .collectList()
                .zipWith(customerRepository.count())
                .map(t->new PageImpl<>(t.getT1(),pageRequest,t.getT2()));
    }
}
