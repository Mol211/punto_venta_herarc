package org.mol211.ventaceramica.persistence;

import org.mol211.ventaceramica.model.Customer;
import org.mol211.ventaceramica.model.Sale;
import org.mol211.ventaceramica.util.DatabaseConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mol211.ventaceramica.mappers.SaleMapper.mapResultSetToSale;

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
    private final String SQL_UPDATE = "UPDATE sales SET sale_date = ?, total = ?, customer_id = ?, payment_method = ? WHERE id = ?";
    private final String SQL_DELETE = "DELETE FROM sales WHERE id = ?";
    //Especific
    private final String SQL_FIND_BY_CUSTOMER = "SELECT id, sale_date, total, customer_id, payment_method, created_at FROM sales WHERE costumer_id = ORDER BY sale_date DESC";
    private final String SQL_FIND_BY_DATE = "SELECT id, sale_date, total, customer_id, payment_method, created_at FROM sales WHERE DATE(sale_date) = ? ORDER BY sale_date DESC";
    private final String SQL_FIND_BY_DATERANGE = "SELECT id, sale_date, total, customer_id, payment_method, created_at FROM sales WHERE sale_date BETWEEN ? AND ? ORDER BY sale_date DESC";
    private final String SQL_GET_TOTAL_BY_DATE = "SELECT SUM(total) FROM sales WHERE DATE(sale_date) = ?";
    private final String SQL_GET_TOTAL_BY_DATERANGE = "SELECT SUM(total) FROM sales WHERE sale_date BETWEEN ? AND ?";
    private final String SQL_COUNT_SALES_BY_DATE = "SELECT COUNT(*) FROM sales WHERE DATE(sale_date) = ?";
    private final String SQL_FIND_RECENT = "SELECT id, sale_date, total, customer_id, payment_method, created_at FROM sales ORDER BY sale_date DESC LIMIT ?";

    private final Logger logger = LoggerFactory.getLogger(SaleDAO.class);
    DatabaseConnection db = DatabaseConnection.getInstance();

    public Sale save(Sale s) {
        try (Connection con = db.getConnection();
             PreparedStatement statement = con.prepareStatement(SQL_SAVE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setDate(1, Date.valueOf(s.getSale_date()));
            statement.setDouble(2, s.getTotalPrice());
            statement.setLong(3, s.getCustomer_id());
            statement.setString(4, s.getPaymentMethod().name());
            statement.executeUpdate();
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    s.setId(rs.getLong(1));
                }
            }
            logger.info("The Save is saved correctly with ID: " + s.getId());
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return s;
    }

    public boolean update(Sale s) {
        try (Connection conn = db.getConnection();
             PreparedStatement statement = conn.prepareStatement(SQL_UPDATE)) {
            statement.setDate(1, Date.valueOf(s.getSale_date()));
            statement.setDouble(2, s.getTotalPrice());
            statement.setLong(3, s.getCustomer_id());
            statement.setString(4, s.getPaymentMethod().name());
            statement.setLong(5,s.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public List<Sale> findAll() {
        try (Connection con = db.getConnection();
             PreparedStatement statement = con.prepareStatement(SQL_FIND_ALL);
             ResultSet resultSet = statement.executeQuery()) {
            List<Sale> sales = new ArrayList<>();
            while (resultSet.next()) {
                sales.add(mapResultSetToSale(resultSet));
            }
            logger.info("Sales returned ok");
            return sales;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public Sale findById(Long id) {
        try (Connection con = db.getConnection();
             PreparedStatement statement = con.prepareStatement(SQL_FIND_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                Sale s = null;
                if (resultSet.next()) {
                    s = mapResultSetToSale(resultSet);
                }
                return s;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public void delete(Sale s) {
        try (Connection con = db.getConnection();
             PreparedStatement statement = con.prepareStatement(SQL_DELETE)) {
            statement.setLong(1, s.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public List<Sale> findByCostumer(Customer c) {
        try (Connection connection = db.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_CUSTOMER)) {
            statement.setLong(1, c.getId());
            List<Sale> sales = new ArrayList<>();
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    sales.add(mapResultSetToSale(resultSet));
                }
            }
            return sales;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public List<Sale> findByDate(LocalDate dateTime) {
        try (Connection connection = db.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_DATE)) {
            statement.setDate(1, Date.valueOf(dateTime));
            List<Sale> sales = new ArrayList<>();
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    sales.add(mapResultSetToSale(resultSet));
                }
            }
            return sales;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public List<Sale> findByDateRange(LocalDate dateStart, LocalDate dateEnd) {
        try (Connection connection = db.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_DATERANGE)) {
            statement.setDate(1, Date.valueOf(dateStart));
            statement.setDate(1, Date.valueOf(dateEnd));
            List<Sale> sales = new ArrayList<>();
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    sales.add(mapResultSetToSale(resultSet));
                }
            }
            return sales;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public double getTotalByDate(LocalDate dateStart) {
        try (Connection connection = db.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_GET_TOTAL_BY_DATE)) {
            statement.setDate(1, Date.valueOf(dateStart));
            double total = 0;
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    total = resultSet.getDouble(1);
                }
            }
            return total;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public double getTotalByDateRange(LocalDate dateStart, LocalDate dateEnd) {
        try (Connection connection = db.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_GET_TOTAL_BY_DATERANGE)) {
            statement.setDate(1, Date.valueOf(dateStart));
            statement.setDate(2, Date.valueOf(dateEnd));

            double total = 0;
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    total = resultSet.getDouble(1);
                }
            }
            return total;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public int countSalesByDate(LocalDate date) {

        try (Connection connection = db.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_COUNT_SALES_BY_DATE)) {
            int numberOfSales = 0;
            statement.setDate(1, Date.valueOf(date));

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    numberOfSales = resultSet.getInt(1);
                }
            }
            return numberOfSales;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public List<Sale> findLastSalesByNumber(int number) {
        try (Connection connection = db.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_RECENT)) {
            int numberOfSales = 0;
            statement.setInt(1, number);
            List<Sale> sales = new ArrayList<>();
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    sales.add(mapResultSetToSale(resultSet));
                }
            }
            return sales;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

}
