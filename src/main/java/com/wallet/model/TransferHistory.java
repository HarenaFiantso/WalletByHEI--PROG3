package com.wallet.model;

import com.wallet.annotation.Column;
import com.wallet.annotation.Table;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Table(name = "transfer_history")
public class TransferHistory {
  @Column(name = "transfer_history_id")
  private Long transferHistoryId;

  @Column(name = "transfer_date")
  private Timestamp transferDate;

  @Column(name = "debit_transaction_id")
  private int debitTransactionId;

  @Column(name = "credit_transaction_id")
  private int creditTransactionId;

  @Column private Double amount;
}
