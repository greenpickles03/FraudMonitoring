package com.fraudmonitoring.customer_service.repository;

import com.fraudmonitoring.customer_service.entity.Customer;
import com.fraudmonitoring.customer_service.entity.CustomerStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    Optional<Customer> findByCustomerNumber(String customerNumber);
    Optional<Customer> findByEmail(String email);
    boolean existsByCustomerNumber(String customerNumber);
    boolean existsByEmail(String email);
    List<Customer> findByStatus(CustomerStatus status);

}
