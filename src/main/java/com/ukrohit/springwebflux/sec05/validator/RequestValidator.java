package com.ukrohit.springwebflux.sec05.validator;

import com.ukrohit.springwebflux.sec05.dto.CustomerDto;
import com.ukrohit.springwebflux.sec05.exception.ApplicationException;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class RequestValidator {

    public static UnaryOperator<Mono<CustomerDto>> validate() {
        return mono -> mono.filter(hasName())
                .switchIfEmpty(ApplicationException.missingName())
                .filter(hasValidEmail())
                .switchIfEmpty(ApplicationException.missingValidEmail());
    }

    private static Predicate<CustomerDto> hasName() {
        return dto -> Objects.nonNull(dto.getName());
    }

    private static Predicate<CustomerDto> hasValidEmail() {
        return dto -> Objects.nonNull(dto.getEmail()) && dto.getEmail().contains("@");
    }

}
