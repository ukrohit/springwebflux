package com.ukrohit.springwebflux.sec05.entity;

import org.springframework.data.annotation.Id;

import java.time.Instant;
import java.util.UUID;

public record CustomerOrder(
        @Id
        UUID orderId,
        Integer customerId,
        Integer productId,
        Integer amout,
        Instant orderDate
)  {
}
