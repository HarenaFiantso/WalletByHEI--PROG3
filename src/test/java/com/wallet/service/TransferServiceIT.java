package com.wallet.service;

import com.wallet.model.Account;
import com.wallet.model.Transaction;
import com.wallet.repository.implementations.TransactionCrudOperations;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferServiceIT {

  @Test
  @Disabled
  void testTransferMoneyFirstCase() {
    Account debitAccount = new Account(80L);
    Account creditAccount = new Account(81L);

    Double amountToTransfer = 50.0;

    List<Transaction> savedTransactions = new ArrayList<>();

    TransactionCrudOperations transactionCrudOperations = new TransactionCrudOperations() {
      @Override
      public Transaction save(Transaction transaction) {
        savedTransactions.add(transaction);
        return transaction;
      }
    };

    TransferService transferService = new TransferService();

    transferService.transactionCrudOperations = transactionCrudOperations;

    assertDoesNotThrow(() -> transferService.transferMoneyFirstCase(debitAccount, creditAccount, amountToTransfer));

    assertEquals(2, savedTransactions.size());
  }
}
