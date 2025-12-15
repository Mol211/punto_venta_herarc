package org.mol211.ventaceramica.service;

import org.mol211.ventaceramica.model.Product;
import org.mol211.ventaceramica.model.ProductWithCategoryDTO;
import org.mol211.ventaceramica.persistence.ProductDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ProductServiceImpl implements   IProductService{

  Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

  private final ProductDAO pDAO = new ProductDAO();

  public ProductServiceImpl(ProductDAO pDAO) {
  }

  @Override
  public List<ProductWithCategoryDTO> findAll() {
    List<ProductWithCategoryDTO> products = pDAO.findAll();
    logger.info("Number of products found: "+ products.size());
    return products;
  }

  @Override
  public Product findById(Long id) {
    Product p = pDAO.findById(id);
    logger.info("Product: "+ p.getName()+", code: "+p.getCode()+" found");
    return p;
  }

  @Override
  public Product save(Product product) {
    Product p = pDAO.save(product);
    logger.info("Product ID: "+p.getId()+" found");
    return p;
  }

  @Override
  public boolean update(Product product) {
    boolean found =  pDAO.update(product);
    String mensaje = found ? "Product modified": "Error at modify product";
    logger.info(mensaje);
    return found;
  }

  @Override
  public void delete(Long id) {
    pDAO.delete(id);
    logger.info("Product deleted OK");
  }

  @Override
  public Product findByCode(String code) {
    Product p = pDAO.findByCode(code);
    logger.info("Product "+p.getName()+" with ID: "+p.getId()+" found");
    return p;
  }

  @Override
  public List<Product> findByName(String name) {
    List<Product> products =  pDAO.findByName(name);
    logger.info("Number of products found: "+ products.size());
    return products;
  }

  @Override
  public List<Product> findByCategory(Long categoryId) {
    List<Product>products = pDAO.findByCategory(categoryId);
    logger.info("Number of products found: "+ products.size());
    return products;
  }

  @Override
  public boolean updateStock(Long id, int newStock) {
    boolean found =  pDAO.updateStock(id, newStock);
    String mensaje = found ? "Stock modified": "Error at modify stock";
    logger.info(mensaje);
    return found;
  }

  @Override
  public List<Product> findLowStock(int stock) {
    List<Product> products = pDAO.findLowStock(stock);
    logger.info("Number of products found: "+ products.size());
    return products;
  }

  @Override
  public List<Product> search(String query) {
    List<Product> products = pDAO.searchByQuery(query);
    logger.info("Number of products found: "+ products.size());
    return products;
  }

  @Override
  public List<ProductWithCategoryDTO> findProductAndCategory(Long categoryId) {
    List<ProductWithCategoryDTO> products = pDAO.findProductAndCategoryBycategoryId(categoryId);
    logger.info("Number of products found: "+ products.size());
    return products;
  }
}
