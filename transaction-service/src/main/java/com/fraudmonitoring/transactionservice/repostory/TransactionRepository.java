package com.fraudmonitoring.transactionservice.repostory;

import com.fraudmonitoring.transactionservice.dto.TransactionResponse;
import com.fraudmonitoring.transactionservice.entity.BankTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<BankTransaction,Integer> {

    Optional<BankTransaction> findByTransactionReference(String transactionReference);
    Optional<BankTransaction> findByIdempotencyKey(String idempotencyKey);
    List<BankTransaction> findBySourceAccountOrDestinationAccountOrderByCreatedAtDesc(String sourceAccount, String destinationAccount);
}
