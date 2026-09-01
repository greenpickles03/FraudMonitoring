package com.fraudmonitoring.transactionservicce.dto;

import com.fraudmonitoring.transactionservicce.entity.TransactionStatus;
import com.fraudmonitoring.transactionservicce.entity.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse(int id, String transactionReference, TransactionType type,
                                  String sourceAccount, String destinationAccount, BigDecimal amount,
                                  TransactionStatus status, String description, LocalDateTime createdAt) {
}
