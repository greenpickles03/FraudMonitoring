package com.fraudmonitoring.accountservice.service.impl;

import com.fraudmonitoring.accountservice.dto.AccountResponse;
import com.fraudmonitoring.accountservice.dto.CreateAccountRequest;
import com.fraudmonitoring.accountservice.dto.DepositRequest;
import com.fraudmonitoring.accountservice.dto.WithdrawRequest;
import com.fraudmonitoring.accountservice.entity.Account;
import com.fraudmonitoring.accountservice.entity.AccountStatus;
import com.fraudmonitoring.accountservice.exception.InsufficientBalanceException;
import com.fraudmonitoring.accountservice.repository.AccountRepository;
import com.fraudmonitoring.accountservice.service.AccountService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class AccountServiceImp implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImp(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    @Override
    public AccountResponse createAccount(CreateAccountRequest request) {

        Account account = new Account();
        account.setAccountNumber(generateAccountNumber());
        account.setAccountType(request.getAccountType());
        account.setBalance(BigDecimal.ONE);
        account.setStatus(AccountStatus.ACTIVE);

        return mapToResponse(accountRepository.save(account));
    }

    @Transactional
    @Override
    public AccountResponse getAccount(int id) throws AccountNotFoundException {
        return mapToResponse(findAccount(id));
    }

    @Transactional
    @Override
    public AccountResponse getByAccountNumber(String accountNumber) throws AccountNotFoundException {
        return mapToResponse(accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found: " + accountNumber)));
    }

    @Transactional
    @Override
    public List<AccountResponse> getCustomerAccounts(int customerId) {
        return accountRepository.findByCustomerId(customerId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional
    @Override
    public AccountResponse deposit(int id, DepositRequest request) throws AccountNotFoundException {
        Account account = findAccount(id);
        validateAccountIsActive(account);
        BigDecimal amount = request.getAmount();
        account.setBalance(account.getBalance().add(amount));
        return mapToResponse(accountRepository.save(account));
    }

    @Transactional
    @Override
    public AccountResponse withdraw(int id, WithdrawRequest request) throws AccountNotFoundException {

        Account account = findAccount(id);
        validateAccountIsActive(account);
        BigDecimal amount = request.getAmount();
        if(account.getBalance().compareTo(amount) <= 0){
            throw new InsufficientBalanceException("Insufficient balance");
        }
        account.setBalance(account.getBalance().subtract(amount));
        return mapToResponse(accountRepository.save(account));
    }

    @Transactional
    @Override
    public void blockAccount(int id) throws AccountNotFoundException {
        Account account = findAccount(id);
        account.setStatus(AccountStatus.BLOCKED);
        accountRepository.save(account);
    }

    @Transactional
    @Override
    public void closeAccount(int id) throws AccountNotFoundException {
        Account account = findAccount(id);
        if(account.getBalance().compareTo(BigDecimal.ZERO) != 0){
            throw new IllegalStateException("Account balance must be zero closing");

        }
        account.setStatus(AccountStatus.CLOSED);
        accountRepository.save(account);
    }

    private Account findAccount(int id) throws AccountNotFoundException {

        return accountRepository
                .findById(id)
                .orElseThrow(()
                        -> new AccountNotFoundException("Account not found: " + id));
    }

    private void validateAccountIsActive(Account account) {
        if(account.getStatus() != AccountStatus.ACTIVE) {
            throw new IllegalStateException("Account is not active");
        }
    }

    private String generateAccountNumber(){
        return "10" +  UUID.randomUUID().toString()
                .replace("-", "")
                .substring(0, 10)
                .toUpperCase();
    }

    private AccountResponse mapToResponse(Account account) {
        AccountResponse accountResponse = new AccountResponse();
        return new AccountResponse(
                account.getId(),
                account.getAccountNumber(),
                account.getCustomerId(),
                account.getAccountType(),
                account.getBalance(),
                account.getStatus(),
                account.getCreatedAt(),
                account.getUpdatedAt()
        );
    }
}
