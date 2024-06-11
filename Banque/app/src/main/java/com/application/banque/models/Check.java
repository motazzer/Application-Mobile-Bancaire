package com.application.banque.models;

public class Check {

    private long id;
    private String username;
    private double amount;
    private String status; // Approved, Dismissed, Pending, etc.

    public Check(long id, String username, double amount, String status) {
        this.id = id;
        this.username = username;
        this.amount = amount;
        this.status = status;
    }
    @Override
    public String toString() {
        return "Username: " + username + ", Amount: " + amount + ", Status: " + status;
    }
    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public double getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }
}

