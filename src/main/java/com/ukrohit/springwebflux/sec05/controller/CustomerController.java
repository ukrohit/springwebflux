package com.ukrohit.springwebflux.sec05.controller;

import com.ukrohit.springwebflux.sec05.dto.CustomerDto;
import com.ukrohit.springwebflux.sec05.exception.ApplicationException;
import com.ukrohit.springwebflux.sec05.filter.Category;
import com.ukrohit.springwebflux.sec05.service.CustomerService;
import com.ukrohit.springwebflux.sec05.validator.RequestValidator;
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

    public static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    CustomerService customerService;

    @PostMapping
    public Mono<CustomerDto> createCustomer(

            @RequestBody Mono<CustomerDto> customerDto) {


        return customerDto.transform(RequestValidator.validate())
                .as(req -> customerService.createCustomer(req))
                .doOnNext(c -> logger.info("CustomerReceived: {]", c));

    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<CustomerDto>> updateCustomer(@PathVariable Integer id, @RequestBody Mono<CustomerDto> dto) {

        logger.info("Put method call ");
        return dto.transform(RequestValidator.validate())
                .as(validDto -> customerService.update(id, validDto))
                .switchIfEmpty(ApplicationException.customerNotFound(id))
                .map(customerDto -> ResponseEntity.ok(customerDto))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping()
    public Flux<CustomerDto> getCustomer() {
        return customerService.getAllCustomer();
    }


    @GetMapping("{id}")
    public Mono<ResponseEntity<CustomerDto>> getCustomerById(
            @RequestAttribute("category") Category category,
            @PathVariable Integer id) {
        logger.info("Get method category : {} ", category);

        return customerService.getCustomer(id)
                .switchIfEmpty(ApplicationException.customerNotFound(id))
                .map(customerDto -> ResponseEntity.ok(customerDto))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("paginated")
    public Mono<List<CustomerDto>> getCustomerByList(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        return customerService.findAllAsList(pageNumber, pageSize);
    }

    @GetMapping("paginated/page")
    public Mono<Page<CustomerDto>> getCustomerByPage(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        return customerService.findAllWithPage(pageNumber, pageSize);
    }

    @DeleteMapping("{id}")
    public Mono<Void> deleteCustomer(@PathVariable Integer id) {
        return customerService.deleteCustomerById(id)
                .filter(b -> b)
                .switchIfEmpty(ApplicationException.customerNotFound(id))
                .then();

    }
}
