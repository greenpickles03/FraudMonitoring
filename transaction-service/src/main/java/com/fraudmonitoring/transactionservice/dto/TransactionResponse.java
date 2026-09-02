package com.fraudmonitoring.transactionservice.dto;

import com.fraudmonitoring.transactionservice.entity.TransactionStatus;
import com.fraudmonitoring.transactionservice.entity.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse(int id, String transactionReference, TransactionType type,
                                  String sourceAccount, String destinationAccount, BigDecimal amount,
                                  TransactionStatus status, String description, LocalDateTime createdAt) {
}
