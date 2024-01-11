package com.wallet.model;

import java.time.LocalDateTime;

import com.wallet.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Table(name = "currency_value")
public class CurrencyValue {
  private Long currencyValueId;
  private LocalDateTime currencyValueDate;
  private Double exchangeRate;
  private int destinationCurrencyId;
  private int sourceCurrencyId;
}
