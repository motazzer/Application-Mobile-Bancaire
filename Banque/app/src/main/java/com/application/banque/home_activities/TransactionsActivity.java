package com.application.banque.home_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.application.banque.Database.DatabaseHelper;
import com.application.banque.R;
import com.application.banque.models.Transaction;
import com.application.banque.models.User;

public class TransactionsActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private EditText recipientPhoneNumberEditText;
    private EditText amountEditText;
    private Button convertButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        databaseHelper = new DatabaseHelper(this);

        recipientPhoneNumberEditText = findViewById(R.id.recipientPhoneNumberEditText);
        amountEditText = findViewById(R.id.amountEditText);
        convertButton = findViewById(R.id.convertButton);

        convertButton.setOnClickListener(v -> convertAmount());
    }

    private void convertAmount() {
        try {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String senderUsername = sharedPreferences.getString("username", "");

        String recipientPhoneNumber = recipientPhoneNumberEditText.getText().toString();
        String amountStr = amountEditText.getText().toString();

        if (!recipientPhoneNumber.isEmpty() && !amountStr.isEmpty()) {
            double amount = Double.parseDouble(amountStr);
            if (databaseHelper.userExists(recipientPhoneNumber)) {
                try {
                    double senderBalance = databaseHelper.getUserBalance(senderUsername);

                    if (senderBalance >= amount) {

                        double newSenderBalance = senderBalance - amount;
                        databaseHelper.updateUserBalance(senderUsername, newSenderBalance);

                        User recipientUser = databaseHelper.getuserByPhoneNumber(recipientPhoneNumber);
                        double recipientBalance = recipientUser.getBalance(); // Assuming User class has a getBalance method
                        double newRecipientBalance = recipientBalance + amount;
                        databaseHelper.updateUserBalanceByPhoneNumber(recipientPhoneNumber, newRecipientBalance);

                        databaseHelper.addTransaction(new Transaction(recipientPhoneNumber, amount, "credit"));
                        databaseHelper.addTransaction(new Transaction(senderUsername, amount, "debit"));
                        Toast.makeText(this, "Transaction successful", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        // Insufficient balance, show appropriate message
                        Toast.makeText(this, "Insufficient balance. Please check your account balance.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    // Log the exception for debugging
                    Log.e("TransactionError", "Error during transaction", e);

                    // Show a generic error message to the user
                    Toast.makeText(this, "An error occurred. Please try again later.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Recipient does not exist", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please enter recipient phone number and amount", Toast.LENGTH_SHORT).show();
        }

    } catch (Exception e) {
        e.printStackTrace();
        Toast.makeText(this, "An error occurred. Please try again.", Toast.LENGTH_SHORT).show();
    }
}
}