package com.wallet.service;

import com.wallet.model.Account;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TransactionServiceIT {

  @Test
  @Disabled
  void testPerformTransactionCredit() throws IllegalAccessException {
    Account account = new Account();
    account.setAccountId(1L);
    account.setBalance(100.0);

    TransactionService transactionService = new TransactionService();

    Account updatedAccount = transactionService.performTransaction(account, "CREDIT", 50.0);

    assertEquals(150.0, updatedAccount.getBalance());
  }

  @Test
  @Disabled
  void testPerformTransactionDebit() throws IllegalAccessException {
    Account account = new Account();
    account.setAccountId(2L);
    account.setBalance(100.0);

    TransactionService transactionService = new TransactionService();

    Account updatedAccount = transactionService.performTransaction(account, "DEBIT", 30.0);

    assertEquals(70.0, updatedAccount.getBalance());
  }

  @Test
  void testPerformTransactionInsufficientFunds() {
    Account account = new Account();
    account.setAccountId(3L);
    account.setBalance(50.0);

    TransactionService transactionService = new TransactionService();

    assertThrows(IllegalAccessException.class,
        () -> transactionService.performTransaction(account, "DEBIT", 70.0));
  }
}
