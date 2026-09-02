package com.fraudmonitoring.transactionservice.controller;

import com.fraudmonitoring.transactionservice.dto.TransactionResponse;
import com.fraudmonitoring.transactionservice.dto.TransferRequest;
import com.fraudmonitoring.transactionservice.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> transfer(
            @RequestHeader("Idempotency-Key") String idempotency,@Valid @RequestBody TransferRequest request) throws Exception {
        System.out.println("transfer method called");
        System.out.println("idempotencyKey:"+idempotency);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(transactionService.transfer(request, idempotency));
    }

    @GetMapping("/account/{accountNumber}")
    public List<TransactionResponse> history(
            @PathVariable String accountNumber) throws Exception {
        return transactionService.getHistory(accountNumber);
    }

}
