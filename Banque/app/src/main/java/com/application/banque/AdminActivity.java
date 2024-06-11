package com.application.banque;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.application.banque.Database.DatabaseHelper;


public class AdminActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private ListView usersListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isAdminLoggedIn()) {
            Intent intent = new Intent(AdminActivity.this, AdminLoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        setContentView(R.layout.activity_admin);

        Button logoutButton = findViewById(R.id.adminLogoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutAdmin();
            }
        });

        Button userSignupButton = findViewById(R.id.userSignupButton);
        userSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToUserSignupApproval();
            }
        });

        Button checkRequestsButton = findViewById(R.id.checkRequestsButton);
        checkRequestsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToCheckRequests();
            }
        });

        Button getAllUsersButton = findViewById(R.id.getAllUsersButton);
        getAllUsersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewAllUsers();
            }
        });
    }

    private void viewAllUsers() {
        Intent intent = new Intent(AdminActivity.this, GetAllUsersActivity.class);
        startActivity(intent);
    }
    private boolean isAdminLoggedIn() {
        SharedPreferences preferences = getSharedPreferences("admin_prefs", Context.MODE_PRIVATE);
        return preferences.getBoolean("admin_logged_in", false);
    }

    private void navigateToUserSignupApproval() {
        Intent intent = new Intent(AdminActivity.this, UserSignupApprovalActivity.class);
        startActivity(intent);
    }

    private void navigateToCheckRequests() {
        Intent intent = new Intent(AdminActivity.this, CheckRequestsActivity.class);
        startActivity(intent);
    }
    private void logoutAdmin() {
        SharedPreferences preferences = getSharedPreferences("admin_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("admin_logged_in", false);
        editor.apply();

        Intent intent = new Intent(AdminActivity.this, AdminLoginActivity.class);
        startActivity(intent);
        finish();
    }

}

