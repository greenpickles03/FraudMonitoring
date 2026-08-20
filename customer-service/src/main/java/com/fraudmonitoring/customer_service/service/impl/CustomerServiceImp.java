package com.fraudmonitoring.customer_service.service.impl;

import com.fraudmonitoring.customer_service.dto.CreateCustomerRequest;
import com.fraudmonitoring.customer_service.dto.CustomerResponse;
import com.fraudmonitoring.customer_service.dto.UpdateCustomerRequest;
import com.fraudmonitoring.customer_service.entity.Customer;
import com.fraudmonitoring.customer_service.entity.CustomerStatus;
import com.fraudmonitoring.customer_service.exception.CustomerNotFoundException;
import com.fraudmonitoring.customer_service.repository.CustomerRepository;
import com.fraudmonitoring.customer_service.service.CustomerService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImp  implements CustomerService{

    private final CustomerRepository customerRepository;

    public CustomerServiceImp(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    @Override
    public CustomerResponse createCustomer(CreateCustomerRequest request) {

        if(customerRepository.existsByEmail(request.getEmail())){
            throw new IllegalArgumentException("Email already exists");
        }

        Customer customer = new Customer();
        customer.setCustomerNumber(generateCustomerNumber());
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setMobileNumber(request.getMobileNumber());
        customer.setDateOfBirth(request.getDateOfBirth());

        return mapToResponse(customerRepository.save(customer));
    }

    @Transactional
    @Override
    public CustomerResponse getCustomerById(int id) {
        return mapToResponse(customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found" + id)));
    }

    @Transactional
    @Override
    public CustomerResponse getByCustomerNumber(String customerNumber) {
        return mapToResponse(customerRepository.findByCustomerNumber(customerNumber)
        .orElseThrow(() -> new CustomerNotFoundException("Customer not found" + customerNumber)));
    }

    @Transactional
    @Override
    public List<CustomerResponse> getActiveCustomers() {
        return customerRepository
                .findByStatus(CustomerStatus.ACTIVE)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional
    @Override
    public CustomerResponse updateCustomer(int id, UpdateCustomerRequest request) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found" + id));

        if(request.getFirstName() != null){
            customer.setFirstName(request.getFirstName());
        }

        if(request.getLastName() != null){
            customer.setLastName(request.getLastName());
        }

        if(request.getEmail() != null){
            customer.setEmail(request.getEmail());
        }

        if(request.getMobileNumber() != null){
            customer.setMobileNumber(request.getMobileNumber());
        }

        if(request.getDateOfBirth() != null){
            customer.setDateOfBirth(request.getDateOfBirth());
        }


        return mapToResponse(customerRepository.save(customer));
    }

    @Transactional
    @Override
    public void blockCustomer(int id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found" + id));
        customer.setStatus(CustomerStatus.BLOCKED);
        customerRepository.save(customer);
    }

    private String generateCustomerNumber(){
        long nextId = customerRepository.count() + 1;
        return String.format("CUST-%06d", nextId);
    }

    private CustomerResponse mapToResponse(Customer customer){
        return new CustomerResponse(
                customer.getId(),
                customer.getCustomerNumber(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getMobileNumber(),
                customer.getDateOfBirth(),
                customer.getKycStatus(),
                customer.getStatus(),
                customer.getCreatedAt(),
                customer.getUpdatedAt());
    }
}
