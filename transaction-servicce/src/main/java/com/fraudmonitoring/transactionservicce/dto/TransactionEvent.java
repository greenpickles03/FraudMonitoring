package com.fraudmonitoring.transactionservicce.dto;

import java.math.BigDecimal;

public record TransactionEvent(String transactionReference, String type, String sourceAccount,
                               String destinationAccount, BigDecimal amount, String status ) {
}
