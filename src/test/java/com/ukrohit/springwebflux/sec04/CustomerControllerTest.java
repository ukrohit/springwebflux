package com.ukrohit.springwebflux.sec04;

import com.ukrohit.springwebflux.sec03.entity.Customer;
import com.ukrohit.springwebflux.sec04.dto.CustomerDto;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@AutoConfigureWebTestClient
@SpringBootTest(properties = "sec=sec04")
public class CustomerControllerTest {

    public static final Logger logger = LoggerFactory.getLogger(CustomerControllerTest.class);

    @Autowired
    private WebTestClient client;

    @Test
    public void testAllCustomer() {
        this.client.get()
                .uri("/customer")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Customer.class)
                .value(list -> logger.info("List : {}", list.size()))
                .hasSize(10);
    }

    @Test
    public void testAllCustomerJsonPath() {
        this.client.get()
                .uri("/customer/paginated?pageNumber=2&pageSize=3")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(res -> logger.info("List : {}", String.valueOf(res.getResponseBodyContent())))
                .jsonPath("$.length()").isEqualTo(3)
                .jsonPath("$[0].id").isEqualTo(4)
                .jsonPath("[1].id").isEqualTo(5);
    }

    @Test
    public void testCustomerFound() {
        this.client.get()
                .uri("/customer/1")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(res -> logger.info("Response : {}", String.valueOf(res.getResponseBodyContent())))
                .jsonPath("$.name").isEqualTo("sam")
                .jsonPath("$.email").isEqualTo("sam@gmail.com");

    }

    @Test
    public void testCustomerNotFound() {
        this.client.get()
                .uri("/customer/11")
                .exchange()
                .expectStatus().is4xxClientError()
                .expectHeader().contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .expectBody()
                .jsonPath("$.detail").isEqualTo("Customer [id=11] is not found");
    }

    @Test
    public void validInputPost() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName("test");
        customerDto.setEmail("test@test.com");
        this.client.post()
                .uri("/customer")
                .bodyValue(customerDto)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.name").isEqualTo("test")
                .jsonPath("$.email").isEqualTo("test@test.com");
    }

    @Test
    public void invalidNameInputPost() {
        CustomerDto customerDto = new CustomerDto();
        this.client.post()
                .uri("/customer")
                .bodyValue(customerDto)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectHeader().contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .expectBody()
                .jsonPath("$.detail").isEqualTo("Name is required ");
    }

    @Test
    public void invalidEmailInputPost() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName("test");
        this.client.post()
                .uri("/customer")
                .bodyValue(customerDto)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectHeader().contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .expectBody()
                .jsonPath("$.detail").isEqualTo("Valid Email is required");
    }

}
