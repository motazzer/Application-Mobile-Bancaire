package com.application.banque;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.application.banque.home_activities.AccountsActivity;
import com.application.banque.home_activities.CheckActivity;
import com.application.banque.home_activities.ConverterActivity;
import com.application.banque.home_activities.ProfileActivity;
import com.application.banque.home_activities.TransactionsActivity;
import com.application.banque.home_activities.CurrencyExchangeActivity;
import com.application.banque.models.User;

public class HomeActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button transactionsButton = findViewById(R.id.transactionsButton);
        transactionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, TransactionsActivity.class);
                startActivity(intent);
            }
        });

        Button profileButton = findViewById(R.id.profileButton);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startProfileActivity();
            }
        });

        Button checkButton = findViewById(R.id.checkButton);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CheckActivity.class);
                startActivity(intent);
            }
        });

        Button accountsButton = findViewById(R.id.accountsButton);
        accountsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAccountsActivity();
            }
        });

        Button converterButton = findViewById(R.id.converterButton);
        converterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ConverterActivity.class);
                startActivity(intent);
            }
        });

        Button currencyExchangeButton = findViewById(R.id.currencyExchangeButton);
        currencyExchangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CurrencyExchangeActivity.class);
                startActivity(intent);
            }
        });

        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);

        if (!isLoggedIn()) {
            startLoginActivity();
        }

        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
    }
    private void startProfileActivity() {
        Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    private void logoutUser() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("username");
        editor.apply();

        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean isLoggedIn() {
        return sharedPreferences.contains("username");
    }

    private void startLoginActivity() {
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void startAccountsActivity() {
        Intent intent = new Intent(HomeActivity.this, AccountsActivity.class);
        startActivity(intent);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
