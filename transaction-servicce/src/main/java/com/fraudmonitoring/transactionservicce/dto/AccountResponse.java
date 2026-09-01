package com.fraudmonitoring.transactionservicce.dto;

import java.math.BigDecimal;

public record AccountResponse(int id, String accountNumber, int customerId, String accountType,
                              BigDecimal balance, String status) {

}
