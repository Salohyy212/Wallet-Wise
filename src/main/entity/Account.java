package main.entity;

public class Account {
    private int id;
    private String name;
    private Double balance;
    private int currencyId;

    public Account(int id, String name, Double balance, int currencyId) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.currencyId = currencyId;
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

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                ", currencyId=" + currencyId +
                '}';
    }
}
