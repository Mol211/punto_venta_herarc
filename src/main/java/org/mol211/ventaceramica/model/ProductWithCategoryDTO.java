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
  private String category;
  private LocalDateTime createdAt;
  private String image;

  public ProductWithCategoryDTO() {
  }

  public ProductWithCategoryDTO(Long id, String code, String name, String description, Double price, Integer stock, Long categoryId, String categoryName, String image) {
    this.id = id;
    this.code = code;
    this.name = name;
    this.description = description;
    this.price = price;
    this.stock = stock;
    this.categoryId = categoryId;
    this.category = categoryName;
    this.image = image;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public Integer getStock() {
    return stock;
  }

  public void setStock(Integer stock) {
    this.stock = stock;
  }

  public Long getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Long categoryId) {
    this.categoryId = categoryId;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }
}
