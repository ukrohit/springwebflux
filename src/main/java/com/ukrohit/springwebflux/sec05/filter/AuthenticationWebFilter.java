package com.ukrohit.springwebflux.sec05.filter;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Order(1)
public class AuthenticationWebFilter implements WebFilter {

    public static final Map<String,Category> TOKEN_CATEGORY_MAP=Map.of(
            "secret123",Category.PRIME,
            "secret678",Category.STANDARD
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

       String token = exchange.getRequest().getHeaders().getFirst("auth-token");

       if(Objects.nonNull(token) &&  TOKEN_CATEGORY_MAP.keySet().contains(token))
       {
           exchange.getAttributes().put("cartegory",TOKEN_CATEGORY_MAP.get(token));
           return chain.filter(exchange);
       }

        return Mono.fromRunnable(()->
               exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)
                );
    }
}
