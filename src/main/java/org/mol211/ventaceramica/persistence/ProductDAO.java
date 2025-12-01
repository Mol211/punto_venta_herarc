package org.mol211.ventaceramica.persistence;

import org.mol211.ventaceramica.model.Product;
import org.mol211.ventaceramica.util.DatabaseConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.mol211.ventaceramica.mappers.ProductMapper.resultSetToProducto;

public class ProductDAO {
    //Metodos CRUD
    //FindAll
    //FindById
    //Save
    //Update
    //Delete
    //Metodos Espec
    //FindByCode
    //FindByName -> Return lista con mismo nombre
    //FindByCategory -> Return lista con misma categoria
    //FindLowStock -> Devuelve los que tienen poco stock
    //UpdateStock
    //search(String query) -> Devuelve lista de productos
    private static final Logger logger = LoggerFactory.getLogger(ProductDAO.class);
    //CRUD METHODS ESTANDAR
    public List<Product> findAll() {
        List<Product> productos = new ArrayList<>();
        String sql = "SELECT * FROM products ORDER BY id";

        //Obtener una instancia única
        DatabaseConnection dbConnection = DatabaseConnection.getInstance();

        //Obtener una conexión (crear nueva)
        try (Connection conn = dbConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                productos.add(resultSetToProducto(rs));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return productos;
    }
    public Product findById(Long id){
        String sql = "SELECT id, code, name, description, price, stock, category_id, created_at FROM products WHERE id=?";
        Product p = null;
        DatabaseConnection dbConnection = DatabaseConnection.getInstance();

        try (Connection conn = dbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setLong(1,id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    p = resultSetToProducto(rs);
                }
            }
            } catch (SQLException e){
            logger.error("Error al obtener el producto por ID {}",id);
        }
        return p;
    }
    public boolean update(Product p){
        String sql = "UPDATE products SET code = ?, name = ?, description = ?, " +
                "price = ?, stock = ?, category_id= ? WHERE id = ?" ;

        DatabaseConnection dbConnection = DatabaseConnection.getInstance();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1,p.getCode());
            stmt.setString(2,p.getName());
            stmt.setString(3,p.getDescription());
            stmt.setDouble(4,p.getPrice());
            stmt.setInt(5,p.getStock());
            stmt.setLong(6,p.getCategoryId());
            stmt.setLong(7,p.getId());

            int rows = stmt.executeUpdate();
            //Si se ha ejecutado la modificación se devuelve un número mayor que cero
            return rows > 0; //Devuelve true.

        } catch (SQLException e){
            logger.error("Error al modificar el producto {}",p.getCode());
            return false;
        }
    }
    public Product save(Product p){
    String sql = "INSERT INTO products(code, name, description, price, stock, category_id, created_at) " +
            "VALUES(?,?,?,?,?,?,?)";
    DatabaseConnection dbConnection = DatabaseConnection.getInstance();

    try (Connection conn = dbConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
        stmt.setString(1,p.getCode());
        stmt.setString(2,p.getName());
        stmt.setString(3,p.getDescription());
        stmt.setDouble(4,p.getPrice());
        stmt.setInt(5,p.getStock());
        stmt.setLong(6,p.getCategoryId());
        stmt.setTimestamp(7, Timestamp.valueOf(p.getCreatedAt()));

        stmt.executeUpdate();
        try (ResultSet rs = stmt.getGeneratedKeys()) {
            if(rs.next()) {
                p.setId(rs.getLong(1));
            }
        }
    } catch (SQLException e){
        logger.error("Error al guardar el producto {}",p.getCode(), e);
    }
    return p;
}
    public void delete(Long id) {
        String sql = "DELETE FROM products WHERE id = ?";
        DatabaseConnection dbConnection = DatabaseConnection.getInstance();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setLong(1, id);
            stmt.executeUpdate();

        }catch (SQLException e) {
            logger.error("Error al eliminar el producto por ID {}",id);
        }

    }

    //ESPECIFIC METHODS
    public Product findByCode(String code){
            String sql = "SELECT id, code, name, description, price, stock, category_id, created_at " +
                    "FROM products " +
                    "WHERE code = ?";
            Product p = null;
            DatabaseConnection dbConnection = DatabaseConnection.getInstance();

            try (Connection conn = dbConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setString(1,code);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        p = resultSetToProducto(rs);
                    }
                }
            }catch (SQLException e) {
                logger.error("Error al obtener el producto con code {}",code);
            }
            return p;
    }
    public List<Product> findByName(String name) {
        String sql = "SELECT id, code, name, description, price, stock, category_id, created_at " +
                "FROM products " +
                "WHERE name ILIKE ? " +
                "ORDER BY name";
        List<Product> products = new ArrayList<>();
        DatabaseConnection dbConnection = DatabaseConnection.getInstance();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1,"%"+name+"%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    products.add(resultSetToProducto(rs));
                }
            }
        }catch (SQLException e) {
            logger.error("Error al obtener el producto con name {}",name);
        }
        return products;
    }
    public List<Product> findByCategory(Long categoryID){
        String sql = "SELECT id, code, name, description, price, stock, category_id, created_at " +
                "FROM products " +
                "WHERE category_id = ? " +
                "ORDER BY name";
        List<Product> products = new ArrayList<>();
        DatabaseConnection dbConnection = DatabaseConnection.getInstance();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setLong(1,categoryID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    products.add(resultSetToProducto(rs));
                }
            }
        }catch (SQLException e) {
            logger.error("Error al obtener el producto con categoria {}",categoryID);
        }
        return products;
    }
    public boolean updateStock(Long productId, int newStock){
        String sql = "UPDATE products SET stock = ? WHERE id = ?" ;

        DatabaseConnection dbConnection = DatabaseConnection.getInstance();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1,newStock);
            stmt.setLong(2,productId);

            int rows = stmt.executeUpdate();
            //Si se ha ejecutado la modificación se devuelve un número mayor que cero
            return rows > 0; //Devuelve true.

        } catch (SQLException e){
            logger.error("Error al modificar el producto {}",productId);
            return false;
        }
    }

}
