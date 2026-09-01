package com.fraudmonitoring.transactionservicce.client;

import com.fraudmonitoring.transactionservicce.dto.AccountResponse;
import com.fraudmonitoring.transactionservicce.dto.AmountRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;

@FeignClient(name = "account-service")
public interface AccountClient {

    @GetMapping("/api/v1/accounts/number/{accountNumber}")
    AccountResponse getAccount(@PathVariable String accountNumber);
    @GetMapping("/api/v1/accounts/number/{accountNumber}/deposit")
    AccountResponse deposit(@PathVariable String accountNumber, @RequestBody AmountRequest request);
    @GetMapping("/api/v1/accounts/number/{accountNumber}/withdraw")
    AccountResponse withdraw(@PathVariable String accountNumber, @RequestBody AmountRequest request);

}
