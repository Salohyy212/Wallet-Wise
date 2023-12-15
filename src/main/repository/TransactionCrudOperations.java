package main.repository;

import main.Main;
import main.entity.Transaction;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;

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
    public BigDecimal getCategorySumByIdAccount(int accountId, LocalDateTime startDate, LocalDateTime endDate) {
        BigDecimal foodAndDrinksSum = BigDecimal.ZERO;
        BigDecimal salarySum = BigDecimal.ZERO;

        String sql = "SELECT t.amount, c.category_name " +
                "FROM \"transaction\" t " +
                "LEFT JOIN transactionCategory c ON t.category_id = c.id " +
                "WHERE t.account_id = ? AND t.date_time BETWEEN ? AND ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, accountId);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(startDate));
            preparedStatement.setTimestamp(3, Timestamp.valueOf(endDate));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    BigDecimal amount = resultSet.getBigDecimal("amount");
                    String categoryName = resultSet.getString("category_name");
                    if ("Food and Drinks".equals(categoryName)) {
                        foodAndDrinksSum = foodAndDrinksSum.add(amount);
                        System.out.println("Food and Drinks sum : " + foodAndDrinksSum);
                    } else if ("Salary".equals(categoryName)) {
                        salarySum = salarySum.add(amount);
                        System.out.println("Salary sum :" + salarySum);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return foodAndDrinksSum.add(salarySum);
    }
    public BigDecimal getEntriesAndExitsSumByIdAccount(int accountId, LocalDateTime startDate, LocalDateTime endDate) {
        BigDecimal totalEntries = BigDecimal.ZERO;
        BigDecimal totalExits = BigDecimal.ZERO;

        String sql = "SELECT t.amount, t.type " +
                "FROM \"transaction\" t " +
                "WHERE t.account_id = ? AND t.date_time BETWEEN ? AND ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, accountId);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(startDate));
            preparedStatement.setTimestamp(3, Timestamp.valueOf(endDate));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    BigDecimal amount = resultSet.getBigDecimal("amount");
                    String transactionType = resultSet.getString("type");
                    if ("credit".equals(transactionType)) {
                        totalEntries = totalEntries.add(amount);
                    } else if ("debit".equals(transactionType)) {
                        totalExits = totalExits.add(amount);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return totalEntries.add(totalExits);
    }

}
