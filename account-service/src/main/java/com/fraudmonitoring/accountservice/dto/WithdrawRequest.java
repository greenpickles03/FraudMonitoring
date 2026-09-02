package com.fraudmonitoring.accountservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class WithdrawRequest {

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
    private BigDecimal amount;

    public WithdrawRequest() {
    }

    public WithdrawRequest(BigDecimal amount) {
        this.amount = amount;
    }

    public @NotNull(message = "Amount is required") @DecimalMin(value = "0.01", message = "Amount must be greater than zero") BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(@NotNull(message = "Amount is required") @DecimalMin(value = "0.01", message = "Amount must be greater than zero") BigDecimal amount) {
        this.amount = amount;
    }
}
