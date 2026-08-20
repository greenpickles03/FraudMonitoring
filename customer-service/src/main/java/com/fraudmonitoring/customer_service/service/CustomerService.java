package com.fraudmonitoring.customer_service.service;

import com.fraudmonitoring.customer_service.dto.CreateCustomerRequest;
import com.fraudmonitoring.customer_service.dto.CustomerResponse;
import com.fraudmonitoring.customer_service.dto.UpdateCustomerRequest;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CustomerService {


    CustomerResponse createCustomer(CreateCustomerRequest request);
    CustomerResponse getCustomerById(int id);
    CustomerResponse getByCustomerNumber(String customerNumber);
    List<CustomerResponse> getActiveCustomers();
    CustomerResponse updateCustomer(int id, UpdateCustomerRequest request);
    void blockCustomer(int id);

}
