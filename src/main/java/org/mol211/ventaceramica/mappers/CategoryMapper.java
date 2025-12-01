package org.mol211.ventaceramica.mappers;

import org.mol211.ventaceramica.model.Category;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryMapper {
    public static Category rsToCategory(ResultSet rs) throws SQLException{
        Category c = new Category(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getTimestamp("created_at").toLocalDateTime());
        return c;
    }
}
