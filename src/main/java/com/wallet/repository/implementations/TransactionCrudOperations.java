package com.wallet.repository.implementations;

import com.wallet.database.ConnectionToDb;
import com.wallet.model.Account;
import com.wallet.model.Transaction;
import com.wallet.repository.CrudOperations;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.StringTemplate.STR;

public class TransactionCrudOperations implements CrudOperations<Transaction> {
  private static final String TRANSACTION_ID_COLUMN = "transaction_id";
  private static final String TRANSACTION_DATE_COLUMN = "transaction_date";
  private static final String TRANSACTION_TYPE_COLUMN = "transaction_type";
  private static final String AMOUNT_COLUMN = "amount";
  private static final String LABEL_COLUMN = "label";
  private static final String ACCOUNT_ID_COLUMN = "account_id";

  private static final String SELECT_BY_ID_QUERY =
      "SELECT * FROM transaction WHERE transaction_id = ?";
  private static final String SELECT_ALL_QUERY = "SELECT * FROM transaction";
  private static final String INSERT_QUERY =
      "INSERT INTO transaction (transaction_date, transaction_type, amount, label, account_id"
          + ") VALUES (?, CAST(? AS transaction_type), ?, ?, ?) RETURNING *";
  private static final String UPDATE_QUERY =
      "UPDATE transaction SET transaction_date = ?, transaction_type = CAST(? AS account_type),"
          + " amount = ?, label = ?, account_id = ? WHERE transaction_id = ?"
          + " RETURNING *";
  private static final String DELETE_QUERY = "DELETE FROM transaction WHERE transaction_id = ?";

  private static final String SELECT_TRANSFERS_BETWEEN_ACCOUNTS =
      "SELECT * FROM transaction WHERE account_id = ? OR account_id = ?";

  @Override
  public Transaction findById(Long toFind) {
    Transaction transaction = null;
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    try {
      connection = ConnectionToDb.getConnection();

      statement = connection.prepareStatement(SELECT_BY_ID_QUERY);
      statement.setLong(1, toFind);

      resultSet = statement.executeQuery();

      if (resultSet.next()) {
        transaction = new Transaction();
        transaction.setTransactionId(resultSet.getLong(TRANSACTION_ID_COLUMN));
      }
    } catch (SQLException e) {
      throw new RuntimeException(STR."Failed to retrieve transaction : \{e.getMessage()}");
    } finally {
      closeResources(connection, statement, resultSet);
    }
    return transaction;
  }

  @Override
  public List<Transaction> findAll() {
    List<Transaction> transactions = new ArrayList<>();
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    try {
      connection = ConnectionToDb.getConnection();

      statement = connection.prepareStatement(SELECT_ALL_QUERY);
      resultSet = statement.executeQuery();

      while (resultSet.next()) {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(resultSet.getLong(TRANSACTION_ID_COLUMN));
        transaction.setTransactionDate(resultSet.getTimestamp(TRANSACTION_DATE_COLUMN));
        transaction.setTransactionType(resultSet.getString(TRANSACTION_TYPE_COLUMN));
        transaction.setAmount(resultSet.getDouble(AMOUNT_COLUMN));
        transaction.setLabel(resultSet.getString(LABEL_COLUMN));
        transaction.setAccountId(resultSet.getInt(ACCOUNT_ID_COLUMN));

        transactions.add(transaction);
      }
    } catch (SQLException e) {
      throw new RuntimeException(STR."Failed to retrieve transactions : \{e.getMessage()}");
    } finally {
      closeResources(connection, statement, resultSet);
    }
    return transactions;
  }

  @Override
  public List<Transaction> saveAll(List<Transaction> toSave) {
    List<Transaction> savedTransactions = new ArrayList<>();

    for (Transaction transaction : toSave) {
      Transaction savedTransaction = this.save(transaction);
      savedTransactions.add(savedTransaction);
    }

    return savedTransactions;
  }

  @Override
  public Transaction save(Transaction toSave) {
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    String QUERY;
    boolean isNewTransaction = toSave.getTransactionId() == null;

    try {
      connection = ConnectionToDb.getConnection();
      if (isNewTransaction) {
        QUERY = INSERT_QUERY;
        statement = connection.prepareStatement(QUERY, Statement.RETURN_GENERATED_KEYS);
        statement.setTimestamp(1, toSave.getTransactionDate());
        statement.setString(2, String.valueOf(toSave.getTransactionType()));
        statement.setDouble(3, toSave.getAmount());
        statement.setString(4, toSave.getLabel());
        statement.setInt(5, toSave.getAccountId());
      } else {
        QUERY = UPDATE_QUERY;
        statement = connection.prepareStatement(QUERY);
        statement.setTimestamp(1, toSave.getTransactionDate());
        statement.setString(2, String.valueOf(toSave.getTransactionType()));
        statement.setDouble(3, toSave.getAmount());
        statement.setInt(4, toSave.getAccountId());
        statement.setString(5, toSave.getLabel());
        statement.setLong(6, toSave.getTransactionId());
      }

      boolean isResultSet = statement.execute();

      if (isResultSet) {
        resultSet = statement.getResultSet();

        if (resultSet.next()) {
          Transaction savedTransaction = new Transaction();
          savedTransaction.setTransactionDate(resultSet.getTimestamp(TRANSACTION_DATE_COLUMN));
          savedTransaction.setTransactionType(resultSet.getString(TRANSACTION_TYPE_COLUMN));
          savedTransaction.setAmount(resultSet.getDouble(AMOUNT_COLUMN));
          savedTransaction.setLabel(resultSet.getString(LABEL_COLUMN));
          savedTransaction.setAccountId(resultSet.getInt(ACCOUNT_ID_COLUMN));

          return savedTransaction;
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(STR."Failed to save transaction : \{e.getMessage()}");
    } finally {
      closeResources(connection, statement, resultSet);
    }
    return null;
  }

  @Override
  public void delete(Transaction toDelete) {
    Connection connection = null;
    PreparedStatement statement = null;

    try {
      connection = ConnectionToDb.getConnection();
      statement = connection.prepareStatement(DELETE_QUERY);
      statement.setLong(1, toDelete.getTransactionId());

    } catch (SQLException e) {
      throw new RuntimeException(STR."Failed to delete transaction :\{e.getMessage()}");
    } finally {
      closeResources(connection, statement, null);
    }
  }

  @Override
  public void closeResources(
      Connection connection, PreparedStatement statement, ResultSet resultSet) {
    try {
      if (resultSet != null) {
        resultSet.close();
      }
      if (statement != null) {
        statement.close();
      }
      if (connection != null) {
        connection.close();
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public List<Transaction> findTransfersBetweenAccounts(Account euroAccount, Account ariaryAccount) {
    List<Transaction> transactions = new ArrayList<>();
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    try {
      Long euroAccountId = euroAccount.getAccountId();
      Long ariaryAccountId = ariaryAccount.getAccountId();

      if (euroAccountId != null && ariaryAccountId != null) {
        connection = ConnectionToDb.getConnection();
        statement = connection.prepareStatement(SELECT_TRANSFERS_BETWEEN_ACCOUNTS);
        statement.setInt(1, Math.toIntExact(euroAccount.getAccountId()));
        statement.setInt(2, Math.toIntExact(ariaryAccount.getAccountId()));

        resultSet = statement.executeQuery();

        while (resultSet.next()) {
          Transaction transaction = new Transaction();
          transaction.setTransactionId(resultSet.getLong(TRANSACTION_ID_COLUMN));
          transaction.setTransactionDate(Timestamp.valueOf(resultSet.getTimestamp(TRANSACTION_DATE_COLUMN).toLocalDateTime()));
          transaction.setTransactionType(resultSet.getString(TRANSACTION_TYPE_COLUMN));
          transaction.setAmount(resultSet.getDouble(AMOUNT_COLUMN));
          transaction.setLabel(resultSet.getString(LABEL_COLUMN));
          transaction.setAccountId(resultSet.getInt(ACCOUNT_ID_COLUMN));

          transactions.add(transaction);
        }
      } else {
        throw new IllegalArgumentException("The account ID should not be null");
      }
    } catch (SQLException e) {
      throw new RuntimeException(STR."Failed the find transfer between account : \{e.getMessage()}");
    } finally {
      closeResources(connection, statement, resultSet);
    }

    return transactions;
  }
}
