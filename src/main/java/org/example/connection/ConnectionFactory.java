package org.example.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class methods for connecting and closing to the database
 */

public class ConnectionFactory {

  private static final String DB_URL = "jdbc:postgresql://localhost/project_database";
  private static final String DB_USER = "postgres";
  private static final String DB_PASSWORD = "hello";
  private static final String POSTGRESQL_DRIVER = "org.postgresql.Driver";
  private static Connection connection = null;

  public static Connection getConnection() {
    try {
      Class.forName(POSTGRESQL_DRIVER);
      connection = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
    } catch (SQLException e) {
      System.out.println("Problem with making a database connection: " + e.getMessage());
    } catch (ClassNotFoundException e) {
      System.out.println("Problem with driver registration:" + e.getMessage());
    }
    return connection;
  }

  public static void closeConnection(Connection connection) {
    if (connection != null) {
      try {
        connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }

    }
  }

}
