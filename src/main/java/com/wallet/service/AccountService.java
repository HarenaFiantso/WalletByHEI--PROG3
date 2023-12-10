package com.wallet.service;

import com.wallet.model.Account;
import com.wallet.model.CurrencyValue;
import com.wallet.model.Transaction;
import com.wallet.model.type.TransactionType;
import com.wallet.repository.implementations.CurrencyValueCrudOperations;
import com.wallet.repository.implementations.TransactionCrudOperations;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AccountService {

  /* TODO: Create a function that allows you to obtain the balance of an account at a given date and time
   *   - Should write test for this method */
  public Double getBalanceAtDateTime(Account account, LocalDateTime dateTime) {
    List<Transaction> transactions = account.getTransactionList();

    Double balance = 0.0;

    for (Transaction transaction : transactions) {
      Instant transactionInstant = transaction.getTransactionDate().toInstant();

      if (transactionInstant.isBefore(dateTime.atZone(ZoneId.systemDefault()).toInstant())
          || transactionInstant.equals(dateTime.atZone(ZoneId.systemDefault()).toInstant())) {

        if (Objects.equals(transaction.getTransactionType(), "CREDIT")) {
          balance += transaction.getAmount();
        } else if (Objects.equals(transaction.getTransactionType(), "DEBIT")) {
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

  /* TODO: Create a function that allows to obtain balance history of an account in interval of date and time
   *   - Should create a test for this method */
  public Map<LocalDateTime, Double> getBalanceHistoryInInterval(
      Account account, LocalDateTime startDate, LocalDateTime endDate) {
    Map<LocalDateTime, Double> balanceHistory = new HashMap<>();
    LocalDateTime currentDateTime = startDate;

    while (!currentDateTime.isAfter(endDate)) {
      Double balanceAtDateTime = getBalanceAtDateTime(account, currentDateTime);
      balanceHistory.put(currentDateTime, balanceAtDateTime);

      currentDateTime = currentDateTime.plusSeconds(1);
    }

    return balanceHistory;
  }

  private final TransactionCrudOperations transactionCrudOperations = new TransactionCrudOperations();
  private final CurrencyValueCrudOperations currencyValueCrudOperations = new CurrencyValueCrudOperations();

  /* TODO: Create a function to transfer money between two accounts (fourth question b | first part)
   *   - Should write a test for this method */
  public Double calculateAriaryBalance(Account euroAccount, Account ariaryAccount, LocalDateTime dateTime) {
    List<Transaction> euroToAriaryTransfers = transactionCrudOperations.findTransfersBetweenAccounts(euroAccount, ariaryAccount);

    double ariaryBalance = 0.0;

    for (Transaction transfer : euroToAriaryTransfers) {
      Double euroAmount = transfer.getAmount();

      CurrencyValue currencyValue =
          currencyValueCrudOperations.findCurrencyValueForDate(transfer.getTransactionDate());

      Double exchangeRate = currencyValue.getExchangeRate();
      double ariaryEquivalent = euroAmount * exchangeRate;

      ariaryBalance += ariaryEquivalent;
    }

    return ariaryBalance;
  }
}
