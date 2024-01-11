package com.wallet.model;

import com.wallet.annotation.Column;
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
  @Column(name = "transaction_id")
  private Long transactionId;

  @Column(name = "amount")
  private Double amount;

  @Column(name = "label")
  private String label;

  @Column(name = "transaction_date")
  private Timestamp transactionDate;

  @Column(name = "transaction_type")
  private TransactionType transactionType;

  @Column(name = "account_id")
  private int accountId;

  @Column(name = "category_id")
  private int categoryId;
}
