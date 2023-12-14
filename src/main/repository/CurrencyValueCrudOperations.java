package main.repository;

import main.Main;
import main.entity.CurrencyValue;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CurrencyValueCrudOperations implements CrudOperations<CurrencyValue> {
    private final Main db = Main.getInstance();
    private final Connection connection = db.getConnection();

    @Override
    public List<CurrencyValue> findAll() {
        List<CurrencyValue> currencyValueList = new ArrayList<>();
        String sql = "SELECT * FROM \"currency_value\"";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                CurrencyValue currencyValue = new CurrencyValue(
                        resultSet.getInt("id"),
                        resultSet.getString("id_currency_source"),
                        resultSet.getString("id_currency_destination"),
                        resultSet.getDouble("amount"),
                        resultSet.getDate("effective_date").toLocalDate()
                );
                currencyValueList.add(currencyValue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return currencyValueList;
    }

    @Override
    public List<CurrencyValue> saveAll(List<CurrencyValue> toSave) {
        String insertQuery = "INSERT INTO \"currency_value\" (id_currency_source, id_currency_destination, amount, effective_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            for (CurrencyValue currencyValue : toSave) {
                insertStatement.setString(1, currencyValue.getId_currency_source());
                insertStatement.setString(2, currencyValue.getId_currency_destination());
                insertStatement.setDouble(3, currencyValue.getAmount());
                insertStatement.setDate(4, Date.valueOf(currencyValue.getEffective_date()));
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
    public CurrencyValue save(CurrencyValue toSave) {
        String insertQuery = "INSERT INTO \"currency_value\" (id_currency_source, id_currency_destination, amount, effective_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            insertStatement.setString(1, toSave.getId_currency_source());
            insertStatement.setString(2, toSave.getId_currency_destination());
            insertStatement.setDouble(3, toSave.getAmount());
            insertStatement.setDate(4, Date.valueOf(toSave.getEffective_date()));
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
