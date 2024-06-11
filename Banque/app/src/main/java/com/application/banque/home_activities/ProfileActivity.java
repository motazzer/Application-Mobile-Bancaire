package com.application.banque.home_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.application.banque.Database.DatabaseHelper;
import com.application.banque.EditProfileActivity;
import com.application.banque.R;
import com.application.banque.models.User;

public class ProfileActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private SharedPreferences sharedPreferences;
    private TextView nameTextView, lastNameTextView, addressTextView, phoneNumberTextView, accountTypeTextView, balanceTextView;
    private Button editProfileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        databaseHelper = new DatabaseHelper(this);
        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);

        nameTextView = findViewById(R.id.nameTextView);
        lastNameTextView = findViewById(R.id.lastNameTextView);
        addressTextView = findViewById(R.id.addressTextView);
        phoneNumberTextView = findViewById(R.id.phoneNumberTextView);
        accountTypeTextView = findViewById(R.id.accountTypeTextView);
        balanceTextView = findViewById(R.id.balanceTextView);
        editProfileButton = findViewById(R.id.editProfileButton);

        displayUserProfile();

        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startEditProfileActivity();
            }
        });
    }

    private void displayUserProfile() {
        String username = sharedPreferences.getString("username", "");

        User user = databaseHelper.getUserByName(username);
        double balance = databaseHelper.getUserBalance(username);

        nameTextView.setText("Name: " + user.getName());
        lastNameTextView.setText("Last Name: " + user.getLastName());
        addressTextView.setText("Address: " + user.getAddress());
        phoneNumberTextView.setText("Phone Number: " + user.getPhoneNumber());
        accountTypeTextView.setText("Account Type: " + user.getTypeOfAccount());
        balanceTextView.setText("Balance: $" + balance);
    }

    private void startEditProfileActivity() {
        Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
        startActivity(intent);
    }
}
