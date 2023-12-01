package com.wallet.model;

import com.wallet.model.type.TransactionType;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Transaction {
  private Long transactionId;
  private Timestamp transactionDate;
  private TransactionType transactionType;
  private Double amount;
  private String label;
  private int accountId;
}
