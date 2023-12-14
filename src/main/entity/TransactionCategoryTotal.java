package main.entity;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

    public class TransactionCategoryTotal {
        private double categoryTotal;

        public double getCategoryTotal() {
            return categoryTotal;
        }

        public void setCategoryTotal(double categoryTotal) {
            this.categoryTotal = categoryTotal;
        }

        public static List<TransactionCategoryTotal> getTransactionCategoryTotal(
                int account_id,
                Timestamp start_datetime,
                Timestamp end_datetime,
                String category_name,
                Connection connection
        ) throws SQLException {
            List<TransactionCategoryTotal> result = new ArrayList<>();

            String query = "SELECT * FROM sum_by_category(?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, account_id);
                preparedStatement.setTimestamp(2, start_datetime);
                preparedStatement.setTimestamp(3, end_datetime);
                preparedStatement.setString(4, category_name);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        TransactionCategoryTotal total = new TransactionCategoryTotal();
                        total.setCategoryTotal(resultSet.getDouble("category_total"));
                        result.add(total);
                    }
                }
            }

            return result;
        }
    }


