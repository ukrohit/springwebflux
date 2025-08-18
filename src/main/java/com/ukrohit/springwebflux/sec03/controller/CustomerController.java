package com.ukrohit.springwebflux.sec03.controller;

import com.ukrohit.springwebflux.sec03.dto.CustomerDto;
import com.ukrohit.springwebflux.sec03.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("customer")
public class CustomerController {

    public static final Logger logger= LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    CustomerService customerService;

    @PostMapping
    public Mono<CustomerDto> createCustomer(@RequestBody Mono<CustomerDto> customerDto)
    {

        logger.info("Post method call ");
        return  customerService.createCustomer(customerDto.doOnNext(c->logger.info("CustomerReceived: {]",c)));
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<CustomerDto>> updateCustomer(@PathVariable Integer id, @RequestBody Mono<CustomerDto> dto) {

        logger.info("Put method call ");
        return customerService.update(id, dto)
                .map(customerDto -> ResponseEntity.ok(customerDto))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @GetMapping()
    public Flux<CustomerDto> getCustomer() {
        return customerService.getAllCustomer();
    }


    @GetMapping("{id}")
    public Mono<ResponseEntity<CustomerDto>> getCustomerById(@PathVariable Integer id) {
        return customerService.getCustomer(id)
                .map(customerDto -> ResponseEntity.ok(customerDto))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("paginated")
    public Mono<List<CustomerDto>> getCustomerByList(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        return customerService.findAllAsList(pageNumber,pageSize);
    }

    @GetMapping("paginated/page")
    public Mono<Page<CustomerDto>> getCustomerByPage(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        return customerService.findAllWithPage(pageNumber,pageSize);
    }

    @DeleteMapping("{id}")
    public Mono<ResponseEntity<String>> deleteCustomer(@PathVariable Integer id) {
        return customerService.deleteCustomerById(id)
                .filter(b->b)
                .map(b->ResponseEntity.ok("Sucess"));

    }
}
