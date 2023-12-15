package main.entity;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionCategory {
    private int id;
    private String name;
    private String type;

    public TransactionCategory(int id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "TransactionCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
    public static CategoryTotal getCategoryTotal(
            int accountId,
            Timestamp startDatetime,
            Timestamp endDatetime,
            String categoryName,
            Connection connection
    ) throws SQLException {
        CategoryTotal result = new CategoryTotal();

        String sql = "SELECT * FROM sum_by_category(?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, accountId);
            statement.setTimestamp(2, startDatetime);
            statement.setTimestamp(3, endDatetime);
            statement.setString(4, categoryName);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    result.setCategoryTotal(resultSet.getDouble("category_total"));
                }
            }
        }

        return result;
    }
}
