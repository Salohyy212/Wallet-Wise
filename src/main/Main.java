package main;

import main.entity.Account;
import main.entity.Currency;
import main.entity.Transaction;
import main.repository.AccountCrudOrepations;
import main.repository.CurrencyCrudOrepations;
import main.repository.TransactionCrudOperations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

        AccountCrudOrepations accountCrudOperations = new AccountCrudOrepations();
        List<Account> allAccounts = accountCrudOperations.findAll();
        System.out.println("All Accounts:");
        allAccounts.forEach(System.out::println);
        Account newAccount = new Account(4, "Elise account",100000.00, 1);
        Account savedAccount = accountCrudOperations.save(newAccount);
        System.out.println("New subscriber added: " + savedAccount);

        CurrencyCrudOrepations currencyCrudOperations = new CurrencyCrudOrepations();
        List<Currency> allCurrencies = currencyCrudOperations.findAll();
        System.out.println("All Currencies:");
        allCurrencies.forEach(System.out::println);

        TransactionCrudOperations transactionCrudOperations = new TransactionCrudOperations();
        List<Transaction> allTransactions = transactionCrudOperations.findAll();
        System.out.println("All Currencies:");
        allCurrencies.forEach(System.out::println);
        Transaction newTransaction= new Transaction(3,"Grocery shopping", 500.00, LocalDateTime.now(),1, "debit");
        Transaction savedTransaction = transactionCrudOperations.save(newTransaction);
        System.out.println("New subscriber added: " + savedTransaction);



        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


