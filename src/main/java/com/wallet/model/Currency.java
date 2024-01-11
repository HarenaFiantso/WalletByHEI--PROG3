package com.wallet.model;

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
  private Long currencyId;
  private CurrencyNameType currencyName;
  private CurrencyCodeType currencyCode;
}
