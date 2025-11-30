package org.mol211.ventaceramica.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection { private static final Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);
   //Variable estática que guardará la única instancia de la clase
    //Con ella implementaremos patrón Singleton (solo una instancia en toda la app)
   private static DatabaseConnection instance;

   private static final String DB_URL = "jdbc:postgresql://localhost:5432/ventaceramica_db";
   private static final String DB_USER = "postgres";
   private static final String DB_PASSWORD = "admin";

   //Constructor privado para evitar que alguien pueda crearla desde otra clase
    private DatabaseConnection() {
    }
    //"Constructor" púlico para crear una instancia
    public static DatabaseConnection getInstance() {
        //Comporobamos si ya existe la instancia.
        //Primera vez que entra instancia = null, entra en el if
        //Siguientes veces ya existe, no permite crearla
        if (instance == null) {
            //Bloqueamos el acceso para evitar problemas con multithread
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }
    //Se crea y devuelve una conexión a la BD
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
    //Metodo para probar la conexión.
    public boolean testConnection() {
        try (Connection con = getConnection()) {
            //Si la conexión es nula o está cerrada salta excecpión
            return con != null && !con.isClosed();
        } catch (SQLException e) {
            logger.error("Error al probar la conexión: {}", e.getMessage());
            return false;
        }
    }
}
