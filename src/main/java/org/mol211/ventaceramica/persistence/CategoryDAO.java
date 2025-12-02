package org.mol211.ventaceramica.persistence;

import org.mol211.ventaceramica.model.Category;
import org.mol211.ventaceramica.model.Product;
import org.mol211.ventaceramica.util.DatabaseConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mol211.ventaceramica.mappers.CategoryMapper.rsToCategory;

public class CategoryDAO {
    //Metodos CRUD
    //FindAll
    //FindById
    //Save
    //Update
    //Delete

    //Métodos especificos
    //findByName
    //countProductos(categoryId)
//    private Long id;
//    private String name;
//    private String description;
//    private LocalDateTime createdAt;
    private final Logger logger = LoggerFactory.getLogger(CategoryDAO.class);

    private final DatabaseConnection dbConnection = DatabaseConnection.getInstance();
    private final String SQL_COUNT = "SELECT COUNT";
    private final String SQL_FIND_BY_NAME = "SELECT id, name, description, created_at FROM categories WHERE name = ?";
    private final String SQL_FIND_ALL = "SELECT id, name, description, created_at FROM categories";
    private  final String SQL_FIND_BY_ID = "SELECT id, name, description, created_at FROM categories WHERE id = ?";
    private final String SQL_DELETE = "DELETE FROM categories WHERE id = ?";

    public Category save(Category c){
        //Consulta SQL
        String sql = "INSERT INTO categories (name, description, created_at) " +
                "VALUES(?,?,?)";
        //Abrimos conexión y preparamos consulta
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            //Añadimos parámetros
            ps.setString(1, c.getName());
            ps.setString(2, c.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(c.getCreatedAt()));

            //Ejecutamos inserción
            ps.executeUpdate();
            //Obtenemos el ID generado
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if(rs.next()) {
                    c.setId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            logger.error("Error al crear la categoria {}", c.getName());
        }
        return c;
    }
    public boolean update(Category c){
        String sql = "UPDATE categories SET name = ?, description = ? WHERE id = ?" ;

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1,c.getName());
            stmt.setString(2,c.getDescription());
            stmt.setLong(3,c.getId());

            int rows = stmt.executeUpdate();
            //Si se ha ejecutado la modificación se devuelve un número mayor que cero
            return rows > 0; //Devuelve true.

        } catch (SQLException e){
            logger.error("Error al modificar la categoria {}",c.getName(), e);
            return false;
        }
    }
    public List<Category> findAll(){

        List<Category> categories = new ArrayList<>();

        try(Connection con = dbConnection.getConnection();
        PreparedStatement st = con.prepareStatement(SQL_FIND_ALL);
        ResultSet rs = st.executeQuery()){
            while (rs.next()) {
                categories.add(rsToCategory(rs));
            }
        }catch (SQLException e){
            logger.error("Error al obtener lista de categorias",e);
        }
        return categories;
    }
    public Category findById(Long id) {
        Category c = null;
        try (Connection con = dbConnection.getConnection();
            PreparedStatement st = con.prepareStatement(SQL_FIND_ALL)){
            st.setLong(1,id);

            try(ResultSet rs = st.executeQuery()){
                if(rs.next()) {
                    c = rsToCategory(rs);
                }
            }

        }catch (SQLException e) {
            logger.error("Error al cargar las categorias",e);
        }
        return c;
    }
    public void delete(Long id) {
        try (Connection con = dbConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(SQL_DELETE)){
            ps.setLong(1, id);
            ps.executeUpdate();
        }catch (SQLException e) {
            logger.error("Error el eliminar la categoria con ID: {}", id, e);
        }
    }
    public List<Category> findByName(String name)

}
