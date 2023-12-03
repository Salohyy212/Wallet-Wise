package main.repository;

import main.Main;
import main.entity.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionCrudOperations implements CrudOperations <Transaction>{
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
                        resultSet.getDouble("amount"),
                         resultSet.getDate("date").toLocalDate(),
                        resultSet.getInt("account_id")
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
        String insertQuery = "INSERT INTO \"transaction\" (amount, date, account_id) VALUES (?, ?, ?)";
        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            for (Transaction transaction : toSave) {
                insertStatement.setDouble(1, transaction.getAmount());
                insertStatement.setDate(2, Date.valueOf(transaction.getDate()));
                insertStatement.setInt(3, transaction.getAccountID());
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
        String insertQuery = "INSERT INTO \"transaction\" (amount, date, account_id) VALUES (?, ?, ?)";
        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            insertStatement.setDouble(1, toSave.getAmount());
            insertStatement.setDate(2, Date.valueOf(toSave.getDate()));
            insertStatement.setInt(3, toSave.getAccountID());

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
