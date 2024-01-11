package com.wallet.model;

import com.wallet.annotation.Table;
import com.wallet.model.type.TransactionType;
import java.sql.Timestamp;
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
  private int categoryId;
}
