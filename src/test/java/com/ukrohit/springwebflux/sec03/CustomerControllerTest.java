package com.ukrohit.springwebflux.sec03;

import com.ukrohit.springwebflux.sec03.entity.Customer;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient
@SpringBootTest(properties = "sec=sec03")
public class CustomerControllerTest {

    public static final Logger logger= LoggerFactory.getLogger(CustomerControllerTest.class);

    @Autowired
    private WebTestClient client;

    @Test
    public void testAllCustomer()
    {
        this.client.get()
                .uri("/customer")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Customer.class)
                .value(list-> logger.info("List : {}",list.size()))
                .hasSize(10);
    }

}
