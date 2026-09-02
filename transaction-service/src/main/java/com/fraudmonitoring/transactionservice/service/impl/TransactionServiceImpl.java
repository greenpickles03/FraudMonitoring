package com.fraudmonitoring.transactionservice.service.impl;

import com.fraudmonitoring.transactionservice.config.AccountServiceClient;
import com.fraudmonitoring.transactionservice.dto.AmountRequest;
import com.fraudmonitoring.transactionservice.dto.TransactionEvent;
import com.fraudmonitoring.transactionservice.dto.TransactionResponse;
import com.fraudmonitoring.transactionservice.dto.TransferRequest;
import com.fraudmonitoring.transactionservice.entity.BankTransaction;
import com.fraudmonitoring.transactionservice.entity.TransactionStatus;
import com.fraudmonitoring.transactionservice.entity.TransactionType;
import com.fraudmonitoring.transactionservice.messaging.TransactionEventProducer;
import com.fraudmonitoring.transactionservice.repostory.TransactionRepository;
import com.fraudmonitoring.transactionservice.service.TransactionService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountServiceClient accountServiceClient;
    private final TransactionEventProducer eventProducer;

    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountServiceClient accountServiceClient, TransactionEventProducer eventProducer) {
        this.transactionRepository = transactionRepository;
        this.accountServiceClient = accountServiceClient;
        this.eventProducer = eventProducer;
    }

    @Transactional
    @Override
    public TransactionResponse transfer(TransferRequest request, String idempotencyKey) throws Exception {

        var existing = transactionRepository.findByIdempotencyKey(idempotencyKey);
        System.out.println(idempotencyKey + " exists in transaction repository : " + existing);
        // Check duplicate request

        if(existing.isPresent()){
            return map(existing.get());
        }

        // Validate accounts

        accountServiceClient.getAccount(request.sourceAccountNumber());
        accountServiceClient.getAccount(request.destinationAccountNumber());

        // Create Transaction

        BankTransaction transaction = new BankTransaction();
        transaction.setTransactionReference(generateReference());
        transaction.setIdempotencyKey(idempotencyKey);
        transaction.setType(TransactionType.TRANSFER);
        transaction.setSourceAccount(request.sourceAccountNumber());
        transaction.setDestinationAccount(request.destinationAccountNumber());
        transaction.setAmount(request.amount());
        transaction.setStatus(TransactionStatus.PENDING);
        transaction.setDescription(request.description());
        transaction = transactionRepository.save(transaction);
        System.out.println(transaction.getIdempotencyKey() + " transaction saved in transaction repository : " + transaction);
        try{
            // Debit source
            accountServiceClient.withdraw(request.sourceAccountNumber(),
                    new AmountRequest(request.amount()));

            // Credit destination
            accountServiceClient.deposit(request.destinationAccountNumber(),
                    new AmountRequest(request.amount()));
            // Complete transaction
            transaction.setStatus(TransactionStatus.COMPLETED);
            transaction = transactionRepository.save(transaction);
            System.out.println("transaction1 : " + request.description());
            // Publish Kafka event
            publishEvent(transaction);
            return map(transaction);
        }catch(Exception e){
            System.out.println("transaction: : " + request.description());
            transaction.setStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);
            throw e;
        }
    }

    @Transactional
    @Override
    public List<TransactionResponse> getHistory(String accountNumber) {
        return transactionRepository
                .findBySourceAccountOrDestinationAccountOrderByCreatedAtDesc(accountNumber,accountNumber)
                .stream()
                .map(this::map)
                .toList();
    }

    private void publishEvent(BankTransaction transaction) {

        eventProducer.publish(new TransactionEvent(
                transaction.getTransactionReference(),
                transaction.getType().name(),
                transaction.getSourceAccount(),
                transaction.getDestinationAccount(),
                transaction.getAmount(),
                transaction.getStatus().name()
        ));
    }

    private TransactionResponse map(BankTransaction transaction) {
        return new TransactionResponse(
                transaction.getId(),
                transaction.getTransactionReference(),
                transaction.getType(),
                transaction.getSourceAccount(),
                transaction.getDestinationAccount(),
                transaction.getAmount(),
                transaction.getStatus(),
                transaction.getDescription(),
                transaction.getCreatedAt()
        );
    }

    private String generateReference(){
        return "TXN-" +
                UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase();
    }

}
