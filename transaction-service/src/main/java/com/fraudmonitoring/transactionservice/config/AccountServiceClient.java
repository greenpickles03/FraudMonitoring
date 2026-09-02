package com.fraudmonitoring.transactionservice.config;

import com.fraudmonitoring.transactionservice.client.AccountClient;
import com.fraudmonitoring.transactionservice.dto.AccountResponse;
import com.fraudmonitoring.transactionservice.dto.AmountRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        return accountClient.deposit(getAccount(accountNumber).id(), request);
    }

    @Retry(name = "accountService")
    @CircuitBreaker(name = "accountService", fallbackMethod = "accountFallback")
    public AccountResponse withdraw(String accountNumber, AmountRequest request) {
        return accountClient.withdraw(getAccount(accountNumber).id(), request);
    }

    private AccountResponse accountFallback(String accountNumber, Throwable throwable) {
        throw accountServiceUnavailable(throwable);
    }

    private AccountResponse accountFallback(
            String accountNumber, AmountRequest request, Throwable throwable) {
        throw accountServiceUnavailable(throwable);
    }

    private ResponseStatusException accountServiceUnavailable(Throwable throwable) {
        return new ResponseStatusException(
                HttpStatus.SERVICE_UNAVAILABLE,
                "Account service is currently unavailable",
                throwable
        );
    }

}
