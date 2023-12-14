package main;

import main.repository.*;

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

        AccountCrudOrepations accountCrudOperations = new AccountCrudOrepations();
        TransactionCrudOperations transactionCrudOperations = new TransactionCrudOperations();
        BalanceCrudOperations balanceCrudOperations = new BalanceCrudOperations();
        LocalDateTime currentDate = LocalDateTime.now();



        // test of getBalanceHistory
        /*Account account2 = new Account(36, "Julie current account", 9000.0, 8, currentDate, "Cash");
        Transaction debitTransaction = new Transaction(account2.getId(), "Make up purchase", 5000.0, currentDate, account2.getId(), "debit");
        account2 = account2.performTransaction(debitTransaction);
        System.out.println("Account after the debit transaction : " + account2);
        accountCrudOperations.save(account2);
        transactionCrudOperations.save(debitTransaction);
        LocalDateTime startDate = currentDate.minusDays(1);
        LocalDateTime endDate = currentDate.plusDays(4);
        List<Double> balanceHistory = account2.getBalanceHistory(startDate, endDate);
        System.out.println("Balance History between " + startDate + " and " + endDate + ": " + balanceHistory);
        for (Double balance : balanceHistory) {
            Balance balanceObject = new Balance(4, account2.getId(), balance, currentDate);
            balanceCrudOperations.save(balanceObject);
        }
        List<Balance> balances = balanceCrudOperations.findAll();
        System.out.println("List of balanceHistory: " + balances);*/



        // test of perfomTransaction
       /* Account newaccount = new Account(34, "Jenny current account", 3000.0, 8, currentDate, "Bank");
        Transaction creditTransaction = new Transaction(newaccount.getId(), "Parie", 5000.0, currentDate, newaccount.getId(), "credit");
        newaccount = newaccount.performTransaction(creditTransaction);
        System.out.println("Account after the credit transaction : " + newaccount);
        accountCrudOperations.save(newaccount);

        Account account2 = new Account(35, "Nelly savings account", 15000.0, 8, currentDate, "Cash");
        Transaction debitTransaction = new Transaction(account2.getId(), "Snack", 3000.0, currentDate, account2.getId(), "debit");
        account2 = account2.performTransaction(debitTransaction);
        System.out.println("Account after the debit transaction : " + account2);
        accountCrudOperations.save(account2);

        List<Transaction> transactions = transactionCrudOperations.findAll();
        System.out.println("List of transactions after the transaction: " + transactions);
        transactionCrudOperations.save(debitTransaction);
        transactionCrudOperations.save(creditTransaction);*/

        // test of crudOperations:
        /*AccountCrudOrepations accountCrudOperations1 = new AccountCrudOrepations();
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

        TransactionCrudOperations transactionCrudOperations1 = new TransactionCrudOperations();
        List<Transaction> allTransactions = transactionCrudOperations.findAll();
        System.out.println("All Transactions:");
        allTransactions.forEach(System.out::println);
        Transaction newTransaction= new Transaction(13,"Purchase", 10000.00, LocalDateTime.now(),11, "debit");
        Transaction savedTransaction = transactionCrudOperations.save(newTransaction);
        System.out.println("New subscriber added: " + savedTransaction);*/

       /* private static void testGetBalanceAtDateTime(Account account, String dateTimeString ) throws ParseException {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
            Date targetDateTime = sdf.parse(dateTimeString);

            double balance = account.getBalanceAtDateTime(LocalDateTime.ofInstant(targetDateTime.toInstant(), account.getLastUpdate().getOffset()));

            System.out.println("Balance: " + dateTimeString + ": " + balance);

       */
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }









