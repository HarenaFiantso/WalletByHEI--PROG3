package com.wallet.model;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Account {
  private Long accountId;
  private String accountName;
  private String accountType;
  private List<Transaction> transactionList;
  private Double balance;
  private int currencyId;
  private LocalDateTime lastTransactionDate;

  public Account(Long accountId) {
    this.accountId = accountId;
  }
}
