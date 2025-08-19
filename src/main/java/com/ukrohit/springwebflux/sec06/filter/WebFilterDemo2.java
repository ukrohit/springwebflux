package com.ukrohit.springwebflux.sec06.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Service
@Order(3)
public class WebFilterDemo2 implements WebFilter
{
    public static final Logger logger= LoggerFactory.getLogger(WebFilterDemo2.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        logger.info("received 2");
        return  chain.filter(exchange);
    }
}
