package main.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Transaction {
    private int id;
    private Double amount;
    private LocalDate date;
    private  int accountID;

    public Transaction(int id, Double amount, LocalDate date, int accountID) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.accountID = accountID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", amount=" + amount +
                ", date=" + date +
                ", accountID=" + accountID +
                '}';
    }
}
