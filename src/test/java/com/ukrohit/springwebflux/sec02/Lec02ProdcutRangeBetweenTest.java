package com.ukrohit.springwebflux.sec02;

import com.ukrohit.springwebflux.sec02.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import reactor.test.StepVerifier;

public class Lec02ProdcutRangeBetweenTest extends  AbstractTest{

    public static final Logger logger= LoggerFactory.getLogger(Lec02ProdcutRangeBetweenTest.class);

    // Create the product class entity and find the product between two price range\

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void productRangeTest() {
        this.productRepository.findByPriceBetween(200, 300)
                .doOnNext(product -> logger.info("product received : {}",product))
                .as(products -> StepVerifier.create(products))
                .expectNextCount(3)
                .expectComplete()
                .verify();
    }

    @Test
    public void productPageableTest() {
        this.productRepository.findBy(PageRequest.of(0,3).withSort(Sort.by("price").ascending()))
                .doOnNext(product -> logger.info("product received : {}",product))
                .as(products -> StepVerifier.create(products))
                .assertNext(p-> Assertions.assertThat(p.price()==200))
                .assertNext(p-> Assertions.assertThat(p.price()==250))
                .assertNext(p-> Assertions.assertThat(p.price()==300))
                .expectComplete()
                .verify();
    }
}
