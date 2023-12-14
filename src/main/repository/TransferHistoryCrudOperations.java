package main.repository;

import main.Main;
import main.entity.TransferHistory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransferHistoryCrudOperations implements CrudOperations<TransferHistory> {
    private final Main db = Main.getInstance();
    private final Connection connection = db.getConnection();

    @Override
    public List<TransferHistory> findAll() {
        List<TransferHistory> transferHistoryList = new ArrayList<>();
        String sql = "SELECT * FROM \"transfer_history\"";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                TransferHistory transferHistory = new TransferHistory(
                        resultSet.getInt("idTransfer"),
                        resultSet.getInt("debitTransactionId"),
                        resultSet.getInt("creditTransactionId"),
                        resultSet.getString("transferDate")
                );
                transferHistoryList.add(transferHistory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transferHistoryList;
    }

    @Override
    public List<TransferHistory> saveAll(List<TransferHistory> toSave) {
        String insertQuery = "INSERT INTO \"transfer_history\" (debitTransactionId, creditTransactionId, transferDate) VALUES (?, ?, ?)";
        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            for (TransferHistory transferHistory : toSave) {
                insertStatement.setInt(1, transferHistory.getDebitTransactionId());
                insertStatement.setInt(2, transferHistory.getCreditTransactionId());
                insertStatement.setString(3, transferHistory.getTransferDate());
                insertStatement.addBatch();
            }
            int[] rowsAffected = insertStatement.executeBatch();
            ResultSet generatedKeys = insertStatement.getGeneratedKeys();
            int i = 0;
            while (generatedKeys.next()) {
                toSave.get(i).setIdTransfer(generatedKeys.getInt(1));
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toSave;
    }

    @Override
    public TransferHistory save(TransferHistory toSave) {
        String insertQuery = "INSERT INTO \"transfer_history\" (debitTransactionId, creditTransactionId, transferDate) VALUES (?, ?, ?)";
        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            insertStatement.setInt(1, toSave.getDebitTransactionId());
            insertStatement.setInt(2, toSave.getCreditTransactionId());
            insertStatement.setString(3, toSave.getTransferDate());
            int rowsAffected = insertStatement.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = insertStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    toSave.setIdTransfer(generatedKeys.getInt(1));
                    return toSave;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
