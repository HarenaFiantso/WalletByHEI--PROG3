package com.wallet.model;

import com.wallet.annotation.Column;
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
  @Column(name = "currency_value_id")
  private Long currencyValueId;

  @Column(name = "currency_value_date")
  private LocalDateTime currencyValueDate;

  @Column(name = "exchange_rate")
  private Double exchangeRate;

  @Column(name = "destination_currency_id")
  private int destinationCurrencyId;

  @Column(name = "source_currency_id")
  private int sourceCurrencyId;
}
