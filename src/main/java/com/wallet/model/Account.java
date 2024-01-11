package com.wallet.model;

import com.wallet.annotation.Column;
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
  @Column(name = "account_id")
  private Long accountId;

  @Column(name = "account_name")
  private String accountName;

  @Column(name = "account_type")
  private AccountType accountType;

  @Column(name = "currency_id")
  private int currencyId;

  @Column private Double balance;

  @Column private LocalDateTime lastTransactionDate;

  @Column private List<Transaction> transactionList;
}
