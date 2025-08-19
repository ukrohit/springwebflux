package com.ukrohit.springwebflux.sec05;

import com.ukrohit.springwebflux.sec05.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;


@AutoConfigureWebTestClient
@SpringBootTest(properties = "sec=sec05")
public class CustomerServiceTest {

    @Autowired
    WebTestClient client;

    // just validate http respomse code?
    // unauthorised  no token
    // unauthorised invalida token
    // standard category  get - success
    // standard catefgory port put delete - forbiddon
    // prime category get - success
    //prime category post put delete - success

    @Test
    public void noTokenVslidation()
    {
        this.client.get()
                .uri("/customer")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.UNAUTHORIZED);

        validateGet("secret",HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void standardCategory()
    {
        validateGet("secret678",HttpStatus.OK);
        validatePost("secret678",HttpStatus.FORBIDDEN);
    }

    @Test
    public void primeCategory()
    {
        validateGet("secret123",HttpStatus.OK);
        validatePost("secret123",HttpStatus.FORBIDDEN);
    }

    private void validateGet(String token, HttpStatus expectedStatus)
    {
        this.client.get()
                .uri("/customer")
                .header("auth-token",token)
                .exchange()
                .expectStatus().isEqualTo(expectedStatus);
    }

    private void validatePost(String token, HttpStatus expectedStatus)
    {
        var customer=new Customer(null,"test","test@test.com");

        this.client.post()
                .uri("/customer")
                .header("auth-token",token)
                .bodyValue(customer)
                .exchange()
                .expectStatus().isEqualTo(expectedStatus);
    }
}
