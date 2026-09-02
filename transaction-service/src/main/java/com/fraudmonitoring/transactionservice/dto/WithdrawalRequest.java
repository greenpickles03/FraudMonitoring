package com.fraudmonitoring.transactionservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record WithdrawalRequest(@NotBlank String accountNumber,
                                @NotBlank @DecimalMin(value = "0.01") BigDecimal amount, String description) {
}
