package com.wallet.service;

import com.wallet.model.Account;
import com.wallet.model.Transaction;
import com.wallet.model.TransferHistory;
import com.wallet.model.type.TransactionType;
import com.wallet.repository.implementations.CurrencyCrudOperations;
import com.wallet.repository.implementations.CurrencyValueCrudOperations;
import com.wallet.repository.implementations.TransactionCrudOperations;
import com.wallet.repository.implementations.TransferHistoryCrudOperations;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TransferService {
  private final TransactionCrudOperations transactionCrudOperations = new TransactionCrudOperations();
  private final TransferHistoryCrudOperations transferHistoryCrudOperations =
      new TransferHistoryCrudOperations();

  /* TODO: Create a function to transfer money between two accounts (fourth question a) */
  public void transferMoneyFirstCase(Account debitAccount, Account creditAccount, Double amount) throws IllegalAccessException {
    if (debitAccount.getAccountId().equals(creditAccount.getAccountId())) {
      throw new IllegalAccessException("An account couldn't done transfer for himself");
    }

    Transaction debitTransaction = createTransaction(debitAccount, amount, TransactionType.DEBIT);
    Transaction creditTransaction =
        createTransaction(creditAccount, amount, TransactionType.CREDIT);

    /* Save transactions in the database */
    assert debitTransaction != null;
    assert creditTransaction != null;
    transactionCrudOperations.save(debitTransaction);
    transactionCrudOperations.save(creditTransaction);

    TransferHistory transferHistory = new TransferHistory();
    transferHistory.setTransferDate(Timestamp.valueOf(LocalDateTime.now()));
    transferHistory.setDebitTransactionId(Math.toIntExact(debitTransaction.getTransactionId()));
    transferHistory.setCreditTransactionId(Math.toIntExact(creditTransaction.getTransactionId()));

    /* Save transferHistory in the database */
    transferHistoryCrudOperations.save(transferHistory);
  }

  private Transaction createTransaction(Account account, Double amount, TransactionType transactionType) {
    Transaction transaction = new Transaction();
    transaction.setTransactionDate(Timestamp.valueOf(LocalDateTime.now()));
    transaction.setTransactionType(transactionType);
    transaction.setAmount(amount);
    transaction.setAccountId(Math.toIntExact(account.getAccountId()));

    return transactionCrudOperations.save(transaction);
  }
}
