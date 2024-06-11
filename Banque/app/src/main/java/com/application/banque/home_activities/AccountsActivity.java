package com.application.banque.home_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.application.banque.Database.DatabaseHelper;
import com.application.banque.R;
import com.application.banque.models.User;

import java.util.List;

public class AccountsActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private SharedPreferences sharedPreferences;
    private ListView accountsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);

        databaseHelper = new DatabaseHelper(this);
        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        accountsListView = findViewById(R.id.accountsListView);
        displayUserAccounts();
        displayTotalBalance();
    }

    private void displayUserAccounts() {
        String username = sharedPreferences.getString("username", "");
        User user = databaseHelper.getUserByName(username);
        List<String> accountsList = databaseHelper.getAllAccountsByPhoneNumber(user.getPhoneNumber());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, accountsList);
        accountsListView.setAdapter(adapter);
    }
    private void displayTotalBalance() {
        String username = sharedPreferences.getString("username", "");
        User user = databaseHelper.getUserByName(username);
        double totalBalance = databaseHelper.getSumOfBalancesByPhoneNumber(user.getPhoneNumber());
        TextView totalBalanceTextView = findViewById(R.id.totalBalanceTextView);
        if (totalBalanceTextView != null) {
            totalBalanceTextView.setText("Total Balance: $" + totalBalance);
        }
    }
}
