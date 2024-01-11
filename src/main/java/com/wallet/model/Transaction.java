package com.wallet.model;

import java.sql.Timestamp;

import com.wallet.annotation.Table;
import com.wallet.model.types.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Table(name = "transaction")
public class Transaction {
  private Long transactionId;
  private Double amount;
  private String label;
  private Timestamp transactionDate;
  private TransactionType transactionType;
  private int accountId;
}
