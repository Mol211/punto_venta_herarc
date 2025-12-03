package org.mol211.ventaceramica.persistence;

public class SaleDAO {

  //Metodos CRUD
  //FindAll
  //FindById
  //Save
  //Update
  //Delete

  //metodos espec
  //findByCustomer -> list
  //findByDate(LocalDate date) -> list sales
  //findByDateRange(from, to) -> list sales
  //getTotalByDate(date) -> double
  //getTotalByDateRange(from, to) -> double
  //find recent(int limit) -> list sales
  //General
  private final String SQL_FIND_ALL = "SELECT id, sale_date, total, " +
          "customer_id, payment_method, created_at FROM sales ORDER BY sale_date DESC";
  private final String SQL_FIND_BY_ID = "SELECT id, sale_date, total, customer_id, payment_method, created_at FROM sales WHERE id = ?";
  private final String SQL_SAVE = "INSERT INTO sales (sale_date, total, customer_id, payment_method) VALUES (?,?,?,?) RETOURNING id";
  private final String SQL_UPDATE = "UPDATE sales SET sale_date = ?, total = ?, customer_id = ?, payment_method = ?";
  private final String SQL_DELETE = "DELETE FROM sales WHERE id = ?";
  //Especific
  private final String SQL_FIND_BY_CUSTOMER = "SELECT id, sale_date, total, customer_id, payment_method, created_at FROM sales WHERE costumer_id = ORDER BY sale_date DESC";
  private final String SQL_FIND_BY_DATE = "SELECT id, sale_date, total, customer_id, payment_method, created_at FROM sales WHERE DATE(sale_date) = ? ORDER BY sale_date DESC";
  private final String SQL_FIND_BY_DATERANGE = "SELECT id, sale_date, total, customer_id, payment_method, created_at FROM sales WHERE sale_date BETWEEN ? AND ? ORDER BY sale_date DESC";
  private final String SQL_GET_TOTAL_BY_DATE = "SELECT SUM(total) FROM sales WHERE DATE(sale_date) = ?";
  private final String SQL_GET_TOTAL_BY_DATERANGE = "SELECT SUM(total) FROM sales WHERE sale_date BETWEEN ? AND ?";
  private final String SQL_COUNT_SALES_BY_DATE = "SELECT COUNT(*) FROM sales WHERE DATE(sale_date) = ?";
  private final String SQL_FIND_RECENT = "SELECT id, sale_date, total, customer_id, payment_method, created_at FROM sales ORDER BY sale_date DESC LIMIT ?";


}
