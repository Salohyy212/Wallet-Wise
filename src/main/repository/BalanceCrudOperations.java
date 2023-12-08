package main.repository;


import main.Main;
import main.entity.Account;
import main.entity.Balance;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BalanceCrudOperations  implements CrudOperations <Balance>{
    private final Main db = Main.getInstance();
    private Connection connection = db.getConnection();
    @Override
    public List<Balance> findAll() {
        List<Balance> balanceList = new ArrayList<>();
        String sql = "SELECT * FROM \"balance\"";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Balance balance = new Balance(
                        resultSet.getInt("id"),
                        resultSet.getInt("account_id"),
                        resultSet.getDouble("balance"),
                        resultSet.getTimestamp("last_update").toLocalDateTime()

                );
                balanceList.add(balance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return balanceList;
    }

    @Override
    public List<Balance> saveAll(List<Balance> toSave) {
        String insertQuery = "INSERT INTO \"balance\" (account_id, balance, last_update) VALUES (?, ?, ?)";
        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            for (Balance balance : toSave) {
                insertStatement.setInt(1, balance.getAccountId());
                insertStatement.setDouble(2, balance.getBalance());
                insertStatement.setTimestamp(3, Timestamp.valueOf(balance.getLastUpdate()));
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
    public Balance save(Balance toSave) {
        String insertQuery = "INSERT INTO balance (account_id, balance, last_update) VALUES (?, ?, ?)";
        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            insertStatement.setInt(1, toSave.getAccountId());
            insertStatement.setDouble(2, toSave.getBalance());
            insertStatement.setTimestamp(3, Timestamp.valueOf(toSave.getLastUpdate()));
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


