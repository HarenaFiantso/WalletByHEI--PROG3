package com.wallet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.wallet.model.Account;
import com.wallet.model.Transaction;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountServiceIT {

  private AccountService accountService;

  @BeforeEach
  void setUp() {
    accountService = new AccountService();
  }

  @Test
  void testGetBalanceAtDateTime() {
    List<Transaction> transactions = new ArrayList<>();
    Transaction creditTransaction = new Transaction();
    creditTransaction.setTransactionDate(
        Timestamp.valueOf(LocalDateTime.parse("2023-01-01T12:00:00")));
    creditTransaction.setTransactionType("CREDIT");
    creditTransaction.setAmount(100.0);
    transactions.add(creditTransaction);

    Transaction debitTransaction = new Transaction();
    debitTransaction.setTransactionDate(
        Timestamp.valueOf(LocalDateTime.parse("2023-01-02T12:00:00")));
    debitTransaction.setTransactionType("DEBIT");
    debitTransaction.setAmount(50.0);
    transactions.add(debitTransaction);

    Account account = new Account();
    account.setTransactionList(transactions);

    double balance =
        accountService.getBalanceAtDateTime(account, LocalDateTime.parse("2023-01-03T12:00:00"));

    assertEquals(50.0, balance);
  }

  @Test
  void testGetCurrentBalance() {
    List<Transaction> transactions = new ArrayList<>();
    Transaction creditTransaction = new Transaction();
    creditTransaction.setTransactionDate(Timestamp.valueOf(LocalDateTime.now()));
    creditTransaction.setTransactionType("CREDIT");
    creditTransaction.setAmount(50.0);
    transactions.add(creditTransaction);

    Account account = new Account();
    account.setTransactionList(transactions);

    Double currentBalance = accountService.getCurrentBalance(account);

    assertEquals(50.0, currentBalance);
  }

  @Test
  void testGetBalanceHistoryInInterval() {
    List<Transaction> transactions = new ArrayList<>();
    Transaction creditTransaction = new Transaction();
    creditTransaction.setTransactionDate(
        Timestamp.valueOf(LocalDateTime.parse("2023-01-01T12:00:00")));
    creditTransaction.setTransactionType("CREDIT");
    creditTransaction.setAmount(100.0);
    transactions.add(creditTransaction);

    Transaction debitTransaction = new Transaction();
    debitTransaction.setTransactionDate(
        Timestamp.valueOf(LocalDateTime.parse("2023-01-02T12:00:00")));
    debitTransaction.setTransactionType("DEBIT");
    debitTransaction.setAmount(50.0);
    transactions.add(debitTransaction);

    Account account = new Account();
    account.setTransactionList(transactions);

    LocalDateTime startDate = LocalDateTime.parse("2023-01-01T00:00:00");
    LocalDateTime endDate = LocalDateTime.parse("2023-01-03T00:00:00");
    Map<LocalDateTime, Double> balanceHistory =
        accountService.getBalanceHistoryInInterval(account, startDate, endDate);

    assertEquals(3, balanceHistory.size());
    assertEquals(0.0, balanceHistory.get(startDate));
    /* assertEquals(100.0, balanceHistory.get(LocalDateTime.parse("2023-01-01T12:00:00")));
    assertEquals(50.0, balanceHistory.get(LocalDateTime.parse("2023-01-02T12:00:00"))); */
  }
}
