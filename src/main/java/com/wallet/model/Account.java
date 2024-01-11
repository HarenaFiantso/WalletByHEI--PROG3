package com.wallet.model;

import com.wallet.annotation.Table;
import com.wallet.model.type.AccountType;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Table(name = "account")
public class Account {
  private Long accountId;
  private String accountName;
  private AccountType accountType;
  private Double balance;
  private LocalDateTime lastTransactionDate;
  private List<Transaction> transactionList;
  private int currencyId;
}
