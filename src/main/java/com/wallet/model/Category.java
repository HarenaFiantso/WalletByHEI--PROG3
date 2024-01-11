package com.wallet.model;

import com.wallet.annotation.Column;
import com.wallet.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Table(name = "category")
public class Category {
  @Column(name = "category_id")
  private Long categoryId;

  @Column(name = "category_name")
  private String categoryName;
}
