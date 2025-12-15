package org.mol211.ventaceramica.mappers;

import org.mol211.ventaceramica.model.Product;
import org.mol211.ventaceramica.model.ProductWithCategoryDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductMapper {
  public static Product resultSetToProducto(ResultSet rs) throws SQLException {
    Product p = new Product(
            rs.getLong("id"),
            rs.getString("code"),
            rs.getString("name"),
            rs.getString("description"),
            rs.getDouble("price"),
            rs.getInt("stock"),
            rs.getLong("category_id"),
            rs.getString("image"));
    return p;
  }
  public static ProductWithCategoryDTO resultSetToProdAndCat(ResultSet rs) throws SQLException {
    ProductWithCategoryDTO p = new ProductWithCategoryDTO(
            rs.getLong("id"),
            rs.getString("code"),
            rs.getString("name"),
            rs.getString("description"),
            rs.getDouble("price"),
            rs.getInt("stock"),
            rs.getLong("category_id"),
            rs.getString("category"),
            rs.getString("image"));
    return p;
  }
  public static Product mapDTOtoProduct(ProductWithCategoryDTO p){
    return new Product(
            p.getId(),
            p.getCode(),
            p.getName(),
            p.getDescription(),
            p.getPrice(),
            p.getStock(),
            p.getCategoryId(),
            p.getImage()
    );
  }
}
