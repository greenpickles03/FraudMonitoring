package com.fraudmonitoring.transactionservicce.config;

import com.fraudmonitoring.transactionservicce.client.AccountClient;
import com.fraudmonitoring.transactionservicce.dto.AccountResponse;
import com.fraudmonitoring.transactionservicce.dto.AmountRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountServiceClient {

    private final AccountClient accountClient;

    public AccountServiceClient(AccountClient accountClient) {
        this.accountClient = accountClient;
    }

    @Retry( name = "accountService")
    @CircuitBreaker(name = "accountService",fallbackMethod = "accountFallback")
    public AccountResponse getAccount(String accountNumber) {
        return accountClient.getAccount(accountNumber);
    }

    @Retry(name = "accountService")
    @CircuitBreaker(name = "accountService", fallbackMethod = "accountFallback")
    public AccountResponse deposit(String accountNumber, AmountRequest request) {
        return accountClient.deposit(accountNumber, request);
    }

    @Retry(name = "accountService")
    @CircuitBreaker(name = "accountService", fallbackMethod = "accountFallback")
    public AccountResponse withdraw(String accountNumber, AmountRequest request) {
        return accountClient.withdraw(accountNumber, request);
    }

    private AccountResponse accountFallback(String accountNumber, Throwable throwable) {
        throw  new RuntimeException("Account Service is currently unavailable");
    }
}
