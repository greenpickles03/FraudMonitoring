package com.fraudmonitoring.transactionservice.service;

import com.fraudmonitoring.transactionservice.dto.TransactionResponse;
import com.fraudmonitoring.transactionservice.dto.TransferRequest;
import com.fraudmonitoring.transactionservice.entity.BankTransaction;

import java.util.List;

public interface TransactionService {

    TransactionResponse transfer(TransferRequest request, String idempotencyKey) throws Exception;
    List<TransactionResponse> getHistory(String accountNumber);

}
