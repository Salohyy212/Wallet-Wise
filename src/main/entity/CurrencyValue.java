package main.entity;

import java.time.LocalDate;

public class CurrencyValue {
    private int id;
    private String id_currency_source;
    private String id_currency_destination;
    private double amount;
    private LocalDate effective_date;

    public CurrencyValue(int id, String id_currency_source, String id_currency_destination, double amount, LocalDate effective_date) {
        this.id = id;
        this.id_currency_source = id_currency_source;
        this.id_currency_destination = id_currency_destination;
        this.amount = amount;
        this.effective_date = effective_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getId_currency_source() {
        return id_currency_source;
    }

    public void setId_currency_source(String id_currency_source) {
        this.id_currency_source = id_currency_source;
    }

    public String getId_currency_destination() {
        return id_currency_destination;
    }

    public void setId_currency_destination(String id_currency_destination) {
        this.id_currency_destination = id_currency_destination;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getEffective_date() {
        return effective_date;
    }

    public void setEffective_date(LocalDate effective_date) {
        this.effective_date = effective_date;
    }

    @Override
    public String toString() {
        return "CurrencyValue{" +
                "id=" + id +
                ", id_currency_source='" + id_currency_source + '\'' +
                ", id_currency_destination='" + id_currency_destination + '\'' +
                ", amount=" + amount +
                ", effective_date=" + effective_date +
                '}';
    }
}
