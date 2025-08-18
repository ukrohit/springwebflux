package com.ukrohit.springwebflux.sec01;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("reactive")
public class ReactiveController {

    public static final Logger logger = LoggerFactory.getLogger(ReactiveController.class);

    public final WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:8080")
            .build();

    @GetMapping("products")
    public Flux<Product> getProducts() {
        Flux<Product> productFlux = this.webClient.get()
                .uri("/demo01/products")
                .retrieve()
                .bodyToFlux(Product.class)
                .doOnNext(p -> logger.info("received :{}", p));
        return productFlux;
    }

    @GetMapping(value = "products/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Product> getProductsStream() {
        Flux<Product> productFlux = this.webClient.get()
                .uri("/demo01/products")
                .retrieve()
                .bodyToFlux(Product.class)
                .doOnNext(p -> logger.info("received :{}", p));
        return productFlux;

    }

    @GetMapping(value = "products/stream/notorious", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Product> getProductsStreamnotorious() {
        Flux<Product> productFlux = this.webClient.get()
                .uri("/demo01/products/notorious")
                .retrieve()
                .bodyToFlux(Product.class)
                .onErrorComplete()
                .doOnNext(p -> logger.info("received :{}", p));
        return productFlux;

    }


}




