package org.mol211.ventaceramica.mappers;

import org.mol211.ventaceramica.model.ProductWithDetails;
import org.mol211.ventaceramica.model.SaleDetails;
import org.mol211.ventaceramica.model.SaleDetailsWithProduct;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SaleDetailMapper {
    public static ProductWithDetails mapResultSetToProductWithDetails(ResultSet rs) throws SQLException {
        return new ProductWithDetails(
                rs.getLong(1),
                rs.getString(2),
                rs.getDouble(3),
                rs.getDouble(4)
        );
    }
    public static SaleDetails mapResultSetToSaleDetail(ResultSet rs) throws SQLException{
        return new SaleDetails(
                rs.getLong(1),
                rs.getLong(2),
                rs.getLong(3),
                rs.getInt(4),
                rs.getDouble(5),
                rs.getDouble(6)
        );
    }
    public static SaleDetailsWithProduct mapResultSetToSaleDetailWithProduct(ResultSet rs) throws SQLException{
        return new SaleDetailsWithProduct(
                rs.getLong(1),
                rs.getLong(2),
                rs.getLong(3),
                rs.getInt(4),
                rs.getDouble(5),
                rs.getDouble(6),
                rs.getString(7),
                rs.getString(8)

        );
    }
}
