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
        //testGetBalanceAtDateTime();
        //testTransferMoney();

        // test of getBalanceHistory
        /*Account account2 = new Account(36, "Julie current account", 9000.0, 8, currentDate, "Cash");
        Transaction debitTransaction = new Transaction(account2.getId(), "Make up purchase", 5000.0, currentDate, account2.getId(), "debit",6);
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
        Transaction creditTransaction = new Transaction(newaccount.getId(), "Parie", 5000.0, currentDate, newaccount.getId(), "credit",13);
        newaccount = newaccount.performTransaction(creditTransaction);
        System.out.println("Account after the credit transaction : " + newaccount);
        accountCrudOperations.save(newaccount);

        Account account2 = new Account(35, "Nelly savings account", 15000.0, 8, currentDate, "Cash");
        Transaction debitTransaction = new Transaction(account2.getId(), "Snack", 3000.0, currentDate, account2.getId(), "debit",1);
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
        Transaction newTransaction= new Transaction(13,"Purchase", 10000.00, LocalDateTime.now(),11, "debit",11);
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
/*  public static void testGetBalanceAtDateTime() {
        Transaction salaryTransaction = new Transaction(1, "Salaire", 100000, "Crédit", LocalDateTime.parse("2023-12-01T00:15:00"));
        Transaction christmasGiftTransaction = new Transaction(2, "Cadeau de Noël", 50000, "Débit", LocalDateTime.parse("2023-12-02T14:00:00"));
        Transaction shoeTransaction = new Transaction(3, "Nouvelle chaussure", 20000, "Débit", LocalDateTime.parse("2023-12-06T16:00:00"));
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(salaryTransaction);
        transactions.add(christmasGiftTransaction);
        transactions.add(shoeTransaction);
        TransactionManager transactionManager = new TransactionManager();
        transactionManager.setTransactions(transactions);
        LocalDateTime targetDateTime = LocalDateTime.parse("2023-12-05T12:00:00");
        double result = transactionManager.getBalanceAtDateTime(targetDateTime);
        double expectedBalance = 100000 - 50000 - 20000;  // Solde attendu après les trois transactions
        if (result == expectedBalance) {
            System.out.println("Test passed!");
        }
        public static void main(String[] args) {
        testTransferMoney();
    }

    public static void testTransferMoney() {
        // Créer deux comptes pour le test avec différentes devises
        Account sourceAccount = new Account(1, "Source Account", 1000.0, "Euro", LocalDateTime.now());
        Account targetAccount = new Account(2, "Target Account", 500.0, "Ariary", LocalDateTime.now());

        // Ajouter des taux de change pour la conversion Euro vers Ariary
        CurrencyExchange.setExchangeRate("Euro", "Ariary", 4300.0);

        double transferAmount = 300.0;
        transferMoney(sourceAccount, targetAccount, transferAmount, LocalDateTime.now());

        double expectedSourceBalance = 1000.0 - transferAmount;
        double expectedTargetBalance = 500.0 + (transferAmount * 4300.0);
        assert sourceAccount.getBalance() == expectedSourceBalance : "Test failed! Expected source balance: " + expectedSourceBalance + ", Actual: " + sourceAccount.getBalance();
        assert targetAccount.getBalance() == expectedTargetBalance : "Test failed! Expected target balance: " + expectedTargetBalance + ", Actual: " + targetAccount.getBalance();

        System.out.println("All tests passed!");
    }

       */
}









