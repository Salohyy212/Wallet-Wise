package main.entity;

import java.time.LocalDateTime;

public class Balance {
    private int id;
    private int accountId;
    private double balance;
    private LocalDateTime lastUpdate;

    public Balance(int id, int accountId, double balance, LocalDateTime lastUpdate) {
        this.id = id;
        this.accountId = accountId;
        this.balance = balance;
        this.lastUpdate = lastUpdate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public String toString() {
        return "BalanceRecord{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", balance=" + balance +
                ", lastUpdate=" + lastUpdate +
                '}';
    }
}
