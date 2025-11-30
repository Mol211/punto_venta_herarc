package org.mol211.ventaceramica.persistence;

import org.mol211.ventaceramica.model.Product;
import org.mol211.ventaceramica.util.DatabaseConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
                productos.add(new Product(
                        rs.getLong("id"),
                        rs.getInt("code"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("stock"),
                        rs.getLong("category_id"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        for(Product product: productos){
            logger.info(product.getName());
        }
        return productos;
    }
}
