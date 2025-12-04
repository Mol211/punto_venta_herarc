package org.mol211.ventaceramica.persistence;

import org.mol211.ventaceramica.model.Customer;
import org.mol211.ventaceramica.util.DatabaseConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static org.mol211.ventaceramica.mappers.CustomerMapper.rsToCustomer;

public class CustomerDAO {
    //Metodos CRUD
    //FindAll
    //FindById
    //Save
    //Update
    //Delete
    //Metodos espec
    //findByEmail
    //findByName -> Lista con mismo name
    //emailExists(mail) -> boolean
    private final String SQL_FIND_ALL = "SELECT id, name, mail, " +
            "phone, created_at FROM customers ORDER BY name";
    private final String SQL_FIND_BY_ID = "SELECT id, name, mail, phone, created_at FROM customers WHERE id = ?";
    private final String SQL_FIND_BY_NAME ="SELECT id, name, mail, phone, created_at FROM customers WHERE name ILIKE ? ORDER BY name";
    private final String SQL_FIND_BY_MAIL = "SELECT id, name, mail, phone, created_at FROM customers WHERE mail = ?";
    private final String SQL_SAVE = "INSERT INTO customers (name, mail, phone) VALUES (?,?,?) RETURNING id";
    private final String SQL_UPDATE = "UPDATE customers SET name = ?, mail = ?, phone = ? WHERE id = ?";
    private final String SQL_DELETE = "DELETE FROM customers WHERE id = ?";
    //Validar si un email existe -> Devuelve 1 true, 0 false.
    private final String SQL_EMAIL_EXISTS = "SELECT COUNT(*) FROM customers WHERE mail = ?";

    private final DatabaseConnection dbConnection = DatabaseConnection.getInstance();


    private final Logger logger = LoggerFactory.getLogger(CustomerDAO.class);


    public Customer save(Customer c) {
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SAVE, RETURN_GENERATED_KEYS)){

            //Añadimos parámetros
            ps.setString(1,c.getName());
            ps.setString(2, c.getMail());
            ps.setString(3, c.getPhone());

            //Ejecutamos inserción
            ps.executeUpdate();
            //Obtenemos ID generado
            try(ResultSet rs = ps.getGeneratedKeys()){
                if(rs.next()) {
                    c.setId(rs.getLong(1));
                }
            }
        }catch (SQLException e){
            logger.error(e.getMessage());
        }
        return c;
    }
    public boolean update(Customer c) {
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE)){
            //Añadimos parámetros
            ps.setString(1,c.getName());
            ps.setString(2, c.getMail());
            ps.setString(3, c.getPhone());
            ps.setLong(4, c.getId());
            //Ejecutamos el save y si se ha ejecutado la modificación devuelve un núm mayor que cero
            int rows = ps.executeUpdate();
            return rows > 0;

        }catch (SQLException e){
            logger.error(e.getMessage());
            return false;
        }

    }
    public List<Customer> findByName (String name) {
        List<Customer> customers = new ArrayList<>();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_NAME)){
            ps.setString(1, "%"+name+"%");
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()) {
                    customers.add(rsToCustomer(rs));
                }
            }

        }catch (SQLException e){
            logger.error(e.getMessage());
        }
        return customers;
    }
    public Customer findByMail(String mail) {
        Customer c = null;
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_MAIL)){
            ps.setString(1, mail);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()) {
                   c = rsToCustomer(rs);
                }
            }
        }catch (SQLException e){
            logger.error(e.getMessage());
        }
        return c;
    }
    public Customer findById(Long id) {
        Customer c = null;
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_ID)){
            ps.setLong(1,id);
            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    c = rsToCustomer(rs);
                }
            }
        }catch (SQLException e){
            logger.error(e.getMessage());
        }
        return c;
    }
    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {
            while(rs.next()) {
                customers.add(rsToCustomer(rs));
            }
        }catch (SQLException e){
            logger.error(e.getMessage());
        }
        return customers;
    }
    public void delete(Customer c){
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_DELETE)){
            ps.setLong(1, c.getId());
            ps.executeUpdate();
        }catch (SQLException e){
            logger.error(e.getMessage());
        }
    }
    public boolean emailExists(String mail){
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_EMAIL_EXISTS)){
            ps.setString(1, mail);
            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        }catch (SQLException e){
            logger.error(e.getMessage());
        }
        return false;
    }



}
