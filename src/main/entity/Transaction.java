package main.entity;



import java.time.LocalDateTime;

public class Transaction {
    private int id;
    private String label;
    private Double amount;
    private LocalDateTime dateTime;
    private int accountId;
    private String type;

    public Transaction(int id, String label, Double amount, LocalDateTime dateTime, int accountId, String type) {
        this.id = id;
        this.label = label;
        this.amount = amount;
        this.dateTime = dateTime;
        this.accountId = accountId;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountID(int accountID) {
        this.accountId = accountId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", amount=" + amount +
                ", dateTime=" + dateTime +
                ", accountId=" + accountId +
                ", type='" + type + '\'' +
                '}';
    }
}
