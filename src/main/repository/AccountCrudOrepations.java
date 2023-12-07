package main.repository;

import main.Main;
import main.entity.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountCrudOrepations implements CrudOperations <Account> {
    private final Main db = Main.getInstance();
    private Connection connection = db.getConnection();

    @Override
    public List<Account> findAll() {
        List<Account> accountList = new ArrayList<>();
        String sql = "SELECT * FROM \"account\"";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Account account = new Account(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("balance"),
                        resultSet.getInt("currency_id"),
                        resultSet.getTimestamp("last_update").toLocalDateTime(),
                        resultSet.getString("type")
                );
                accountList.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accountList;
    }

    @Override
    public List<Account> saveAll(List<Account> toSave) {
        String insertQuery = "INSERT INTO \"account\" (name, balance, currency_id, last_update, type) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            for (Account account : toSave) {
                insertStatement.setString(1, account.getName());
                insertStatement.setDouble(2, account.getBalance());
                insertStatement.setInt(3, account.getCurrencyId());
                insertStatement.setTimestamp(4, Timestamp.valueOf(account.getLastUpdate()));
                insertStatement.setString(5, account.getType());
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
    public Account save(Account toSave) {
        String insertQuery = "INSERT INTO \"account\" (name, balance, currency_id, last_update, type) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            insertStatement.setString(1, toSave.getName());
            insertStatement.setDouble(2, toSave.getBalance());
            insertStatement.setInt(3, toSave.getCurrencyId());
            insertStatement.setTimestamp(4, Timestamp.valueOf(toSave.getLastUpdate()));
            insertStatement.setString(5, toSave.getType());

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