package com.wallet.model;

import com.wallet.annotation.Column;
import com.wallet.annotation.Table;
import com.wallet.model.type.CurrencyCodeType;
import com.wallet.model.type.CurrencyNameType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Table(name = "currency")
public class Currency {
  @Column(name = "currency_id")
  private Long currencyId;

  @Column(name = "currency_name")
  private CurrencyNameType currencyName;

  @Column(name = "currency_code")
  private CurrencyCodeType currencyCode;
}
