package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    private final String url;
    private final String username;
    private final String password;
    private static Main instance;
    private Connection connection;

    private Main(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public static Main getInstance() {
        if (instance == null) {
            instance = new Main(
                    PostgresqlConf.URL,
                    PostgresqlConf.USERNAME,
                    PostgresqlConf.PASSWORD
            );
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(url, username, password);
            }
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("\n" +
                    "Error connecting to database.", e);
        }
    }

    public static void main(String[] args) {
        Main mainInstance = Main.getInstance();
        Connection conn = mainInstance.getConnection();


        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


