package main;

import main.entity.Account;
import main.entity.Transaction;
import main.repository.PostgresqlConf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;

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

            // Initialisation d'une instance de compte
            Account account = new Account(12, "Emma current account", 10000.0, 8, LocalDateTime.now(), "Cash");

            // Affichage du solde initial
            System.out.println("Initial balance: " + account.getBalance());

            // Création d'une transaction de débit
            Transaction creditTransaction = new Transaction(11, "Purchase", 500.0, LocalDateTime.now(), account.getId(), "Credit");

            // Appel de la fonction performTransaction
            Account updatedAccount = account.performTransaction(creditTransaction);

            // Affichage du compte mis à jour
            if (updatedAccount != null) {
                System.out.println("Updated account : " + updatedAccount);
            } else {
                System.out.println("The transaction failed");
            }




        /*AccountCrudOrepations accountCrudOperations = new AccountCrudOrepations();
        List<Account> allAccounts = accountCrudOperations.findAll();
        System.out.println("All Accounts:");
        allAccounts.forEach(System.out::println);
        Account newAccount = new Account(11,  "Emma current account", 220000.00, 2, LocalDateTime.now(),"Mobile Money");
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
        Transaction newTransaction= new Transaction(9,"Grocery shopping", 5000.00, LocalDateTime.now(),9, "debit");
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


