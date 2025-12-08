package org.mol211.ventaceramica.persistence;

import org.mol211.ventaceramica.model.*;
import org.mol211.ventaceramica.util.DatabaseConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.mol211.ventaceramica.mappers.SaleDetailMapper.*;

public class SaleDetailDAO {
  //Metodos CRUD
  //FindAll
  //FindById
  //Save
  //Update
  //Delete

  //metodos espec
  //findBySale(saleid) -> list saledetails
  //findByProducto(productoId) -> list saledetails
  // getTotalQuantitySold(productId) -> int
  //getTopSellingProductos(int limit) -> List Product
  //General
  private final String SQL_FIND_ALL = "SELECT id, sale_id, product_id, quantity, unit_price, subtotal FROM sale_details ORDER BY id";
  private final String SQL_FIND_BY_ID = "SELECT id, sale_id, product_id, quantity, unit_price, subtotal FROM sale_details WHERE id = ?";
  private final String SQL_SAVE = "INSERT INTO sale_details (sale_id, product_id, quantity, unit_price, subtotal) VALUES (?,?,?,?,?) RETURNING id";
  private final String SQL_UPDATE = "UPDATE sale_details SET sale_id = ?, product_id = ?, quantity = ?, unit_price = ?, subtotal = ? WHERE id = ?";
  private final String SQL_DELETE = "DELETE FROM sale_details WHERE id = ?";
  //Especific
  private final String SQL_FIND_BY_SALE = "SELECT id, sale_id, product_id, quantity, unit_price, subtotal FROM sale_details WHERE sale_id = ? ORDER BY id";
  private final String SQL_FIND_BY_SALE_WITH_PRODUCT = "SELECT d.id, d.sale_id, d.product_id, d.quantity, d.unit_price, d.subtotal, " +
          "p.name AS product_name, p.code AS product_code " +
          "FROM sale_details d " +
          "INNER JOIN products p ON d.product_id = p.id " +
          "WHERE d.sale_id = ?";
  private final String SQL_FIND_DETAILS_BY_PRODUCT = "SELECT id, sale_id, product_id, quantity, unit_price, subtotal " +
          "FROM sale_details WHERE product_id = ? ORDER BY id DESC";
  private final String SQL_GET_TOTAL_QUANTITY_SOLD = "SELECT SUM(quantity) FROM sale_details WHERE product_id = ?";
  private final String SQL_GET_TOP_SELLING_PRODUCTS = "SELECT d.product_id, p.name AS product_name, " +
          "SUM(d.quantity) AS total_sold, SUM(d.subtotal) AS total_revenue " +
          "FROM sale_details d " +
          "INNER JOIN products p ON d.product_id = p.id " +
          " GROUP BY d.product_id, p.name ORDER BY total_sold DESC LIMIT ?";
  public void setStatement(PreparedStatement statement, SaleDetails s) throws SQLException{
    statement.setLong(1,s.getSale_id());
    statement.setLong(2, s.getProduct_id());
    statement.setInt(3,s.getQuantity());
    statement.setDouble(4,s.getUnit_price());
    statement.setDouble(5,s.getSubtotal());
  }

  private final Logger logger = LoggerFactory.getLogger(SaleDetailDAO.class);
  DatabaseConnection db = DatabaseConnection.getInstance();

  public SaleDetails save(SaleDetails s){
    try(Connection connection = db.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_SAVE, Statement.RETURN_GENERATED_KEYS)){
        setStatement(statement,s);
        statement.executeUpdate();
        try(ResultSet resultSet = statement.getGeneratedKeys()){
          if(resultSet.next()){
            s.setId(resultSet.getLong(1));
          }
        }
    }catch(SQLException e){
      logger.error(e.getMessage());
      throw new RuntimeException(e);
    }
    return s;
  }
  public boolean update(SaleDetails s){
    try(Connection connection = db.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)){
      setStatement(statement,s);
      statement.setLong(6, s.getId());
      return statement.executeUpdate() > 0;
    }catch(SQLException e){
      logger.error(e.getMessage());
      throw new RuntimeException(e);
    }
  }
  public void delete(SaleDetails s){
    try(Connection connection = db.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_DELETE)){
        statement.setLong(1,s.getId());
        statement.executeUpdate();
    }catch(SQLException e){
      logger.error(e.getMessage());
      throw new RuntimeException(e);
    }
  }
  public List<SaleDetails> findAll(){
    try(Connection connection = db.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL);
        ResultSet resultSet = statement.executeQuery()){
        List<SaleDetails> saleDetails = new ArrayList<>();
        while(resultSet.next()){
          saleDetails.add(mapResultSetToSaleDetail(resultSet));
        }
        return saleDetails;
    }catch(SQLException e){
      logger.error(e.getMessage());
      throw new RuntimeException(e);
    }
  }
  public SaleDetails findById(Long id){
    try(Connection connection = db.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID)){
        statement.setLong(1, id);
        try(ResultSet resultSet = statement.executeQuery()) {
          SaleDetails sd = null;
          if(resultSet.next()){
            sd = mapResultSetToSaleDetail(resultSet);
          }
          return sd;
        }
    }catch(SQLException e){
      logger.error(e.getMessage());
      throw new RuntimeException(e);
    }
  }




  //"SELECT id, sale_id, product_id, quantity, unit_price, subtotal FROM sale_details WHERE sale_id = ? ORDER BY id"
  public List<SaleDetails> findBySale(Sale s){
    try(Connection connection = db.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_SALE)){
        statement.setLong(1,s.getId());
        try(ResultSet resultSet = statement.executeQuery()){
          List<SaleDetails> saleDetails = new ArrayList<>();
          while(resultSet.next()){
            saleDetails.add(mapResultSetToSaleDetail(resultSet));
          }
          return saleDetails;
        }
    }catch(SQLException e){
      logger.error(e.getMessage());
      throw new RuntimeException(e);
    }
  }

  public List<SaleDetailsWithProduct> findBySaleWithProduct(Sale s){
    try(Connection connection = db.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_SALE_WITH_PRODUCT)){
      statement.setLong(1,s.getId());
      try(ResultSet resultSet = statement.executeQuery()){
        List<SaleDetailsWithProduct> saleDetails = new ArrayList<>();
        while(resultSet.next()){
          saleDetails.add(mapResultSetToSaleDetailWithProduct(resultSet));
        }
        return saleDetails;
      }
    }catch(SQLException e){
      logger.error(e.getMessage());
      throw new RuntimeException(e);
    }
  }

  public List<SaleDetails> findByProduct(Product p){
    try(Connection connection = db.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_FIND_DETAILS_BY_PRODUCT)){
      statement.setLong(1,p.getId());
      try(ResultSet resultSet = statement.executeQuery()){
        List<SaleDetails> saleDetails = new ArrayList<>();
        while(resultSet.next()){
          saleDetails.add(mapResultSetToSaleDetail(resultSet));
        }
        return saleDetails;
      }
    }catch(SQLException e){
      logger.error(e.getMessage());
      throw new RuntimeException(e);
    }
  }

  //  "SELECT d.product_id, p.name AS product_name, " +
//          "SUM(d.quantity) AS total_sold, SUM(d.subtotal) AS total_revenue " +
//          "FROM sale_details d " +
//          "INNER JOIN products p ON d.product_id = p.id " +
//          " GROUP BY d.product_id, p.name ORDER BY total_sold DESC LIMIT ?";
  public List<ProductWithDetails> getMostSoldProducts(int limit){
    try(Connection connection = db.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_GET_TOP_SELLING_PRODUCTS)){
      statement.setInt(1, limit);
      try(ResultSet resultSet = statement.executeQuery()){
        List<ProductWithDetails> products = new ArrayList<>();
        while(resultSet.next()){
          products.add(mapResultSetToProductWithDetails(resultSet));
        }
        return products;
      }
    }catch(SQLException e){
      logger.error(e.getMessage());
      throw new RuntimeException(e);
    }
  }

  public double getSoldByProduct(Product p){
    try(Connection connection = db.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_GET_TOTAL_QUANTITY_SOLD)){
      statement.setLong(1, p.getId());
      try(ResultSet resultSet = statement.executeQuery()){
        double total = 0;
        if(resultSet.next()){
          total = resultSet.getDouble(1);
        }
        return total;
      }
    }catch(SQLException e){
      logger.error(e.getMessage());
      throw new RuntimeException(e);
    }
  }

}
