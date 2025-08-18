package com.ukrohit.springwebflux.sec05.exception;

import reactor.core.publisher.Mono;

public class ApplicationException {

    public static <T> Mono<T> customerNotFound(Integer id) {
        return Mono.error(new CustomerNotFoundException(id));
    }

    public static <T> Mono<T> missingName() {
        return Mono.error(new InvalidInutException("Name is required"));
    }

    public static <T> Mono<T> missingValidEmail() {
        return Mono.error(new InvalidInutException("Valid Email is required"));
    }
}
