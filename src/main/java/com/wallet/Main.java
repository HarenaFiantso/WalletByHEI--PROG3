package com.wallet;

import com.wallet.database.ConnectionToDb;

public class Main {
  public static void main(String[] args) {
    System.out.println("Hello world!");
    ConnectionToDb.getConnection();
  }
}
