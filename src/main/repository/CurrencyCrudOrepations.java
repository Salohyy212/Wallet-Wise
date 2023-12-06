package main.repository;

import main.Main;
import main.entity.Currency;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CurrencyCrudOrepations implements CrudOperations <Currency>{
    private final Main db = Main.getInstance();
    private final Connection connection = db.getConnection();

    @Override
    public List<Currency> findAll() {
        List<Currency> currencyList = new ArrayList<>();
        String sql = "SELECT * FROM \"currency\"";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Currency currency = new Currency(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("code")
                );
                currencyList.add(currency);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return currencyList;
    }

    @Override
    public List<Currency> saveAll(List<Currency> toSave) {
        String insertQuery = "INSERT INTO \"currency\" (name, code) VALUES (?, ?)";
        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            for (Currency currency : toSave) {
                insertStatement.setString(1, currency.getName());
                insertStatement.setString(2, currency.getCode());
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
    public Currency save(Currency toSave) {
        String insertQuery = "INSERT INTO \"currency\" (name, code) VALUES (?, ?)";
        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            insertStatement.setString(1, toSave.getName());
            insertStatement.setString(2, toSave.getCode());
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

