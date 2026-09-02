package com.fraudmonitoring.accountservice.controller;

import com.fraudmonitoring.accountservice.dto.AccountResponse;
import com.fraudmonitoring.accountservice.dto.CreateAccountRequest;
import com.fraudmonitoring.accountservice.dto.DepositRequest;
import com.fraudmonitoring.accountservice.dto.WithdrawRequest;
import com.fraudmonitoring.accountservice.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody CreateAccountRequest request){
        System.out.println("request: " + request.getCustomerId());
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccount(request));
    }

    @GetMapping("/{id}")
    public AccountResponse getAccount(@PathVariable int id) throws AccountNotFoundException {
        return accountService.getAccount(id);
    }

    @GetMapping("/number/{accountNumber}")
    public AccountResponse getByAccountNumber(@PathVariable String accountNumber) throws AccountNotFoundException {
        System.out.println("accountNumber: " + accountNumber);
        return accountService.getByAccountNumber(accountNumber);
    }

    @GetMapping("/customer/{customerId}")
    public List<AccountResponse> getCustomerAccounts(@PathVariable int customerId) {

        return accountService.getCustomerAccounts(customerId);
    }

    @PostMapping("/{id}/deposit")
    public AccountResponse deposit(@PathVariable int id,@Valid @RequestBody DepositRequest request) throws AccountNotFoundException {
        return accountService.deposit(id, request);
    }


    @PostMapping("/{id}/withdraw")
    public AccountResponse withdraw(@PathVariable int id, @Valid @RequestBody WithdrawRequest request) throws AccountNotFoundException {
        return accountService.withdraw( id,request);
    }


    @PatchMapping("/{id}/block")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void blockAccount( @PathVariable int id) throws AccountNotFoundException {
        accountService.blockAccount(id);
    }

    @PatchMapping("/{id}/close")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void closeAccount( @PathVariable int id) throws AccountNotFoundException {
        accountService.closeAccount(id);
    }
}
