package com.application.banque;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminLoginActivity extends AppCompatActivity {

    private EditText usernameInput;
    private EditText passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        usernameInput = findViewById(R.id.adminUsernameInput);
        passwordInput = findViewById(R.id.adminPasswordInput);

        Button loginButton = findViewById(R.id.adminLoginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginAdmin();
            }
        });
    }

    private void loginAdmin() {
        String adminUsername = usernameInput.getText().toString();
        String adminPassword = passwordInput.getText().toString();


        if (adminUsername.equals("motazzer") && adminPassword.equals("7080")) {
            SharedPreferences preferences = getSharedPreferences("admin_prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("admin_logged_in", true);
            editor.apply();

            Intent intent = new Intent(AdminLoginActivity.this, AdminActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Invalid admin credentials", Toast.LENGTH_SHORT).show();
        }
    }
}
