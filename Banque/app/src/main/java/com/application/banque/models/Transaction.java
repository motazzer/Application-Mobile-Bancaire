package com.application.banque.models;

public class Transaction {
    private long id;
    private String username;
    private double amount;
    private String type; // "credit" or "debit"



    public Transaction(String username, double amount, String type) {
        this.username = username;
        this.amount = amount;
        this.type = type;
    }


    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", amount=" + amount +
                ", type='" + type + '\'' +
                '}';
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
