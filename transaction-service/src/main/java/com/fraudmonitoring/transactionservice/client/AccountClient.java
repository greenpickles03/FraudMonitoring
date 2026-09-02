package com.fraudmonitoring.transactionservice.client;

import com.fraudmonitoring.transactionservice.dto.AccountResponse;
import com.fraudmonitoring.transactionservice.dto.AmountRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "account-service")
public interface AccountClient {

    @GetMapping("/api/v1/accounts/number/{accountNumber}")
    AccountResponse getAccount(@PathVariable String accountNumber);
    @PostMapping("/api/v1/accounts/{id}/deposit")
    AccountResponse deposit(@PathVariable int id, @RequestBody AmountRequest request);

    @PostMapping("/api/v1/accounts/{id}/withdraw")
    AccountResponse withdraw(@PathVariable int id, @RequestBody AmountRequest request);

}
