package org.mol211.ventaceramica.mappers;

import org.mol211.ventaceramica.model.PaymentMethod;
import org.mol211.ventaceramica.model.Product;
import org.mol211.ventaceramica.model.Sale;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SaleMapper {
    public static Sale mapResultSetToSale(ResultSet rs) throws SQLException {
        String payment_mehtod = rs.getString("payment_method");
        Sale s = new Sale(
                rs.getLong("id"),
                rs.getDate("sale_date").toLocalDate(),
                rs.getDouble("total_price"),
                PaymentMethod.valueOf(payment_mehtod),
                rs.getLong("customer_id"),
                rs.getTimestamp("created_at").toLocalDateTime());
        return s;
    }
}
