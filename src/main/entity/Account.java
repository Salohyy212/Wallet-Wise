package main.entity;

import java.time.LocalDateTime;
import java.util.List;

public class Account {
    private List<Transaction> transactions;
    private int id;
    private String name;
    private Double balance;
    private int currencyId;
    private LocalDateTime lastUpdate;
    private String type;

    public Account(int id, String name, Double balance, int currencyId, LocalDateTime lastUpdate, String type) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.currencyId = currencyId;
        this.lastUpdate = lastUpdate;
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

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                ", currencyId=" + currencyId +
                ", lastUpdate=" + lastUpdate +
                ", type='" + type + '\'' +
                '}';
    }
}
