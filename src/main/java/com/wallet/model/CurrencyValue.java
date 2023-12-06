package com.wallet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CurrencyValue {
  private Long currencyValueId;
  private LocalDateTime currencyValueDate;
  private Double exchangeRate;
  private int sourceCurrencyId;
  private int destinationCurrencyId;
}
