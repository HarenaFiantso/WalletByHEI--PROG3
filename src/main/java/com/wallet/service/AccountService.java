package com.wallet.service;

import com.wallet.model.Account;
import com.wallet.model.Transaction;
import com.wallet.model.type.TransactionType;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountService {

  /* TODO: Create a function that allows you to obtain the balance of an account at a given date and time
   *   - Should write test for this method */
  public Double getBalanceAtDateTime(Account account, LocalDateTime dateTime) {
    List<Transaction> transactions = account.getTransactionList();

    Double balance = 0.0;

    for (Transaction transaction : transactions) {
      if (transaction.getTransactionDate().toInstant().isBefore(Instant.from(dateTime))
          || transaction.getTransactionDate().toLocalDateTime().isEqual(dateTime)) {

        if (transaction.getTransactionType() == TransactionType.CREDIT) {
          balance += transaction.getAmount();
        } else if (transaction.getTransactionType() == TransactionType.DEBIT) {
          balance -= transaction.getAmount();
        }
      }
    }

    return balance;
  }

  /* TODO: (Bonus) Create a function to obtain the balance of the current account
   *   - Should create a test for this method */
  public Double getCurrentBalance(Account account) {
    LocalDateTime currentDateTime = LocalDateTime.now();
    return getBalanceAtDateTime(account, currentDateTime);
  }
}
