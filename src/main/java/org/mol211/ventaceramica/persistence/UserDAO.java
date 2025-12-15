package org.mol211.ventaceramica.persistence;

import org.mol211.ventaceramica.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
  public boolean login(String username, String password){
    try(Connection con = DatabaseConnection.getInstance().getConnection();
        PreparedStatement statement = con.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?")) {
      statement.setString(1, username);
      statement.setString(2, password);
      try(ResultSet resultSet = statement.executeQuery()){
        return resultSet.next();
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
