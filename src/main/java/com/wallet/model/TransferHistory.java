package com.wallet.model;

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
  private Long transferHistoryId;
  private Timestamp transferDate;
  private Double amount;
  private int debitTransactionId;
  private int creditTransactionId;
}
