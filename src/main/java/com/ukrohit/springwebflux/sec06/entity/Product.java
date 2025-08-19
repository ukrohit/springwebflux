package com.ukrohit.springwebflux.sec06.entity;

import org.springframework.data.annotation.Id;

public record Product(
        @Id
        int id,
        String description,
        int price) {
}
