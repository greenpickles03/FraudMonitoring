package com.fraudmonitoring.accountservice.dto;

import com.fraudmonitoring.accountservice.entity.AccountType;
import jakarta.validation.constraints.NotNull;

public class CreateAccountRequest {

    @NotNull(message = "Customer ID is required")
    private int customerId;
    @NotNull(message = "Account type is required")
    private AccountType accountType;

    public CreateAccountRequest() {
    }

    public CreateAccountRequest(int customerId, AccountType accountType) {
        this.customerId = customerId;
        this.accountType = accountType;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
}
