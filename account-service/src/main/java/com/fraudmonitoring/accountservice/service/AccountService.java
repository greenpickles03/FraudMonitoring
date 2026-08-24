package com.fraudmonitoring.accountservice.service;

import com.fraudmonitoring.accountservice.dto.AccountResponse;
import com.fraudmonitoring.accountservice.dto.CreateAccountRequest;
import com.fraudmonitoring.accountservice.dto.DepositRequest;
import com.fraudmonitoring.accountservice.dto.WithdrawRequest;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

public interface AccountService {

    AccountResponse createAccount(CreateAccountRequest request);
    AccountResponse getAccount(int id) throws AccountNotFoundException;
    AccountResponse getByAccountNumber(String accountNumber) throws AccountNotFoundException;
    List<AccountResponse> getCustomerAccounts(int customerId);
    AccountResponse deposit(int id, DepositRequest request) throws AccountNotFoundException;
    AccountResponse withdraw(int id, WithdrawRequest request) throws AccountNotFoundException;
    void blockAccount(int id) throws AccountNotFoundException;
    void closeAccount(int id) throws AccountNotFoundException;


}
