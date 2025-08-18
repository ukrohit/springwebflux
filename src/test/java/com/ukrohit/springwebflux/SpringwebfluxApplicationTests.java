package com.ukrohit.springwebflux;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "sec=sec02",
//        "logging.level.org.springframework.r2dbc = DEBUG"
})
class SpringwebfluxApplicationTests {

    @Test
    void contextLoads() {
    }

}
