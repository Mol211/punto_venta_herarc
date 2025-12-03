package org.mol211.ventaceramica.persistence;

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
  private final String SQL_SAVE = "INSERT INTO sale_details (sale_id, product_id, quantity, unit_price, subtotal) VALUES (?,?,?,?,?)";
  private final String SQL_UPDATE = "UPDATE sale_details SET sale_id = ?, product_id = ?, quantityt = ?, unit_price = ?, subtotal = ?";
  private final String SQL_DELETE = "DELETE FROM sale_details WHERE id = ?";
  //Especific
  private final String SQL_FIND_BY_SALE = "SELECT id, sale_id, product_id, quantity, unit_price, subtotal FROM sale_details WHERE sale_id = ? ORDER BY id";
  private final String SQL_FIND_BY_SALE_WITH_PRODUCT = "SELECT d.id, d.sale_id, d.product_id, d.quantity, d.unit_price, d.subtotal, " +
          "p.name AS product_name, p.code AS product_code " +
          "FROM sale_details d " +
          "INNER JOIN proudcts p ON d.product_id = p.id " +
          "WHERE s.sale_id = ?";
  private final String SQL_FIND_DETAILS_BY_PRODUCT = "SELECT SELECT id, sale_id, product_id, quantity, unit_price, subtotal" +
          "FROM sale_details WHERE product_id = ? ORDER BY id DESC";
  private final String SQL_GET_TOTAL_QUANTITY_SOLD = "SELECT SUM(quantity) FROM sale_details WHERE product_id = ?";
  private final String SQL_GET_TOP_SELLING_PRODUCTS = "SELECT d.product_id, p.name AS product_name," +
          "SUM(d.quantity) AS total_sold, SUM(d.subtotal) AS total_revenue" +
          "FROM sale_details d" +
          "INNER JOIN products p ON d.product_id = p.id" +
          " GROUP BY d.product_id, p.name ORDER BY total_sold DESC LIMIT ?";

}
