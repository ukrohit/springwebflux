package com.ukrohit.springwebflux.sec02.mapper;

import com.ukrohit.springwebflux.sec02.dto.CustomerDto;
import com.ukrohit.springwebflux.sec02.entity.Customer;

public class CustomerMapper {

    public static Customer dtoToCustomer(CustomerDto customerDto) {
        return new Customer(customerDto.getId(), customerDto.getName(), customerDto.getEmail());
    }

    public static CustomerDto customerToDto(Customer customer) {
        return new CustomerDto(customer.id(), customer.name(), customer.email());
    }

}
