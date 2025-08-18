package com.ukrohit.springwebflux.sec02.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "CUSTOMER")
public record Customer(
        @Id Integer id,
        String name,
        String email) {

    public Customer(Integer id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
