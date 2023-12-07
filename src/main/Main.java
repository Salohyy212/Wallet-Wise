package main;

import main.entity.Account;
import main.entity.Transaction;
import main.repository.AccountCrudOrepations;
import main.repository.PostgresqlConf;
import main.repository.TransactionCrudOperations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
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
        TransactionCrudOperations transactionCrudOperations = new TransactionCrudOperations();
        LocalDateTime currentDate = LocalDateTime.now();

        Account account3 = new Account(22, "Kelly current account", 1000.0, 9, currentDate, "Bank");
        Transaction debitTransaction = new Transaction(account3.getId(), "Carte", 1000.0, currentDate, account.getId(), "dedit");
        account3 = account3.performTransaction(debitTransaction);
        System.out.println("Account after the credit transaction : " + account3);
        accountCrudOperations.save(account3);
        transactionCrudOperations.save(debitTransaction);

        LocalDateTime startDate = currentDate.minusDays(1);
        LocalDateTime endDate = currentDate.plusDays(3);
        List<Double> balanceHistory = account3.getBalanceHistory(startDate, endDate);
        System.out.println("Balance History between " + startDate + " and " + endDate + ": " + balanceHistory);

        // test of perfomTransaction
        /*Account account = new Account(15, "Harry current account", 200000.0, 8, currentDate, "Mobile Money");
        Transaction creditTransaction = new Transaction(account.getId(), "Birthday", 10000.0, currentDate, account.getId(), "credit");
        account = account.performTransaction(creditTransaction);
        System.out.println("Account after the credit transaction : " + account);
        accountCrudOperations.save(account);

        Account account2 = new Account(15, "Julie savings account", 5000.0, 9, currentDate, "Bank");
        Transaction debitTransaction = new Transaction(account.getId(), "Gift", 2000.0, currentDate, account.getId(), "debit");
        account2 = account2.performTransaction(debitTransaction);
        System.out.println("Account after the credit transaction : " + account);
        accountCrudOperations.save(account);

        List<Transaction> transactions = transactionCrudOperations.findAll();
        System.out.println("List of transactions after the transaction: " + transactions);
        transactionCrudOperations.save(debitTransaction);
        transactionCrudOperations.save(creditTransaction);*/

        // test of crudOperations:
        /*AccountCrudOrepations accountCrudOperations = new AccountCrudOrepations();
        List<Account> allAccounts = accountCrudOperations.findAll();
        System.out.println("All Accounts:");
        allAccounts.forEach(System.out::println);
        Account newAccount = new Account(13,  "John current account", 100000.00, 8, LocalDateTime.now(),"Bank");
        Account savedAccount = accountCrudOperations.save(newAccount);
        System.out.println("New subscriber added: " + savedAccount);

        CurrencyCrudOrepations currencyCrudOperations = new CurrencyCrudOrepations();
        List<Currency> allCurrencies = currencyCrudOperations.findAll();
        System.out.println("All Currencies:");
        allCurrencies.forEach(System.out::println);

        TransactionCrudOperations transactionCrudOperations = new TransactionCrudOperations();
        List<Transaction> allTransactions = transactionCrudOperations.findAll();
        System.out.println("All Transactions:");
        allTransactions.forEach(System.out::println);
        Transaction newTransaction= new Transaction(13,"Purchase", 10000.00, LocalDateTime.now(),11, "debit");
        Transaction savedTransaction = transactionCrudOperations.save(newTransaction);
        System.out.println("New subscriber added: " + savedTransaction);*/


        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}







