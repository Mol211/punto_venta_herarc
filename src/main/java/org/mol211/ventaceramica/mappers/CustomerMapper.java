package org.mol211.ventaceramica.mappers;

import org.mol211.ventaceramica.model.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerMapper {
  public static Customer rsToCustomer(ResultSet set) throws SQLException {
    return new Customer(
            set.getLong("id"),
            set.getString("name"),
            set.getString("mail"),
            set.getString("phone"),
            set.getTimestamp("created_at").toLocalDateTime()
    );
  }
}
