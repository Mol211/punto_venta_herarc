package org.mol211.ventaceramica.model;

import java.time.LocalDateTime;

public class ProductWithCategoryDTO {
  private Long id;
  private String code;
  private String name;
  private String description;
  private Double price;
  private Integer stock;
  private Long categoryId;
  private String categoryName;
  private LocalDateTime createdAt;

  public ProductWithCategoryDTO() {
  }

  public ProductWithCategoryDTO(Long id, String code, String name, String description, Double price, Integer stock, Long categoryId, String categoryName, LocalDateTime createdAt) {
    this.id = id;
    this.code = code;
    this.name = name;
    this.description = description;
    this.price = price;
    this.stock = stock;
    this.categoryId = categoryId;
    this.categoryName = categoryName;
    this.createdAt = createdAt;
  }
}
