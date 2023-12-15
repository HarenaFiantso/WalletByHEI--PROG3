package com.wallet.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionToDb {
  /* TODO: Put credentials to environment variable */

  private static Connection connection;

  public static Connection getConnection() {
    try {
      if (connection == null || connection.isClosed()) {
        String DB_URL = System.getenv("DB_URL");
        String DB_USERNAME = System.getenv("DB_USERNAME");
        String DB_PASSWORD = System.getenv("DB_PASSWORD");

        try {
          connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (SQLException e) {
          throw new RuntimeException(STR."Cannot connect to database : \{e.getMessage()}");
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(STR."Failed to get connection : \{e.getMessage()}");
    }
    return connection;
  }
}
