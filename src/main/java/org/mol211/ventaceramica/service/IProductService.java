package org.mol211.ventaceramica.service;

import org.mol211.ventaceramica.model.Product;
import org.mol211.ventaceramica.model.ProductWithCategoryDTO;

import java.util.List;


public interface IProductService {

  List<ProductWithCategoryDTO> findAll();
  Product findById(Long id);
  Product save(Product product);
  boolean update(Product product);
  void delete(Long id);

  Product findByCode(String code);
  List<Product> findByName(String name);
  List<Product> findByCategory(Long categoryId);
  boolean updateStock(Long id, int newStock);
  List<Product> findLowStock(int stock);
  List<Product> search(String query);

  List<ProductWithCategoryDTO> findProductAndCategory(Long categoryId);
}