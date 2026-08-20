package com.fraudmonitoring.customer_service.controller;

import com.fraudmonitoring.customer_service.dto.CreateCustomerRequest;
import com.fraudmonitoring.customer_service.dto.CustomerResponse;
import com.fraudmonitoring.customer_service.dto.UpdateCustomerRequest;
import com.fraudmonitoring.customer_service.entity.Customer;
import com.fraudmonitoring.customer_service.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;


    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CreateCustomerRequest request){
        return  ResponseEntity
                .status(HttpStatus.CREATED)
                .body(customerService.createCustomer(request));
    }

    @GetMapping("/{id}")
    public CustomerResponse getCustomer(@PathVariable int id){
        return customerService.getCustomerById(id);
    }

    @GetMapping("/number/{customerNumber}")
    public CustomerResponse getByCustomerNumber(@PathVariable String customerNumber){
        return customerService.getByCustomerNumber(customerNumber);
    }

    @GetMapping("/active")
    public List<CustomerResponse> getActiveCustomers(){
        return customerService.getActiveCustomers();
    }

    @PutMapping("/{id}")
    public CustomerResponse updateCustomer(@PathVariable int id, @Valid @RequestBody UpdateCustomerRequest request){
        return customerService.updateCustomer(id, request);
    }

    @PatchMapping("/{id}/block")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void blockCustomer(@PathVariable int id){
        customerService.blockCustomer(id);
    }
}
