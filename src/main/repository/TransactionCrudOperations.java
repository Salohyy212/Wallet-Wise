package main.repository;

import main.Main;
import main.entity.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionCrudOperations implements CrudOperations<Transaction> {
    private final Main db = Main.getInstance();
    private final Connection connection = db.getConnection();

    @Override
    public List<Transaction> findAll() {
        List<Transaction> transactionList = new ArrayList<>();
        String sql = "SELECT * FROM \"transaction\"";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Transaction transaction = new Transaction(
                        resultSet.getInt("id"),
                        resultSet.getString("label"),
                        resultSet.getDouble("amount"),
                        resultSet.getTimestamp("date_time").toLocalDateTime(),
                        resultSet.getInt("account_id"),
                        resultSet.getString("type"),
                        resultSet.getInt("category_id")
                );
                transactionList.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionList;
    }

    @Override
    public List<Transaction> saveAll(List<Transaction> toSave) {
        String insertQuery = "INSERT INTO \"transaction\" (label, amount, date_time, account_id, type) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            for (Transaction transaction : toSave) {
                insertStatement.setString(1, transaction.getLabel());
                insertStatement.setDouble(2, transaction.getAmount());
                insertStatement.setTimestamp(3, Timestamp.valueOf(transaction.getDateTime()));
                insertStatement.setInt(4, transaction.getAccountId());
                insertStatement.setString(5, String.valueOf(transaction.getType()));
                insertStatement.setInt(6, transaction.getCategoryId());
                insertStatement.addBatch();
            }
            int[] rowsAffected = insertStatement.executeBatch();
            ResultSet generatedKeys = insertStatement.getGeneratedKeys();
            int i = 0;
            while (generatedKeys.next()) {
                toSave.get(i).setId(generatedKeys.getInt(1));
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toSave;
    }

    @Override
    public Transaction save(Transaction toSave) {
        String insertQuery = "INSERT INTO \"transaction\" (label, amount, date_time, account_id, type) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            insertStatement.setString(1, toSave.getLabel());
            insertStatement.setDouble(2, toSave.getAmount());
            insertStatement.setTimestamp(3, Timestamp.valueOf(toSave.getDateTime()));
            insertStatement.setInt(4, toSave.getAccountId());
            insertStatement.setString(5, String.valueOf(toSave.getType()));
            insertStatement.setInt(6, toSave.getCategoryId());
            int rowsAffected = insertStatement.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = insertStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    toSave.setId(generatedKeys.getInt(1));
                    return toSave;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
