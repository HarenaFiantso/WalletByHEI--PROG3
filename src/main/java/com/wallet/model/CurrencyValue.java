package com.wallet.model;

import com.wallet.annotation.Table;
import java.time.LocalDateTime;
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
