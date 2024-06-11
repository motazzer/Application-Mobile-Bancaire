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

import com.application.banque.Database.DatabaseHelper;
import com.application.banque.home_activities.ProfileActivity;
import com.application.banque.models.User;

public class EditProfileActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private SharedPreferences sharedPreferences;
    private EditText nameEditText, lastNameEditText, addressEditText, phoneNumberEditText;
    private Button saveChangesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        databaseHelper = new DatabaseHelper(this);
        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);

        nameEditText = findViewById(R.id.nameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        addressEditText = findViewById(R.id.addressEditText);
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        saveChangesButton = findViewById(R.id.saveChangesButton);

        displayUserProfile();

        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges();
            }
        });
    }

    private void displayUserProfile() {

        String username = sharedPreferences.getString("username", "");

        User user = databaseHelper.getUserByName(username);

        nameEditText.setText(user.getName());
        lastNameEditText.setText(user.getLastName());
        addressEditText.setText(user.getAddress());
        phoneNumberEditText.setText(user.getPhoneNumber());
    }

    private void saveChanges() {
        String username = sharedPreferences.getString("username", "");

        String newName = nameEditText.getText().toString();
        String newLastName = lastNameEditText.getText().toString();
        String newAddress = addressEditText.getText().toString();
        String newPhoneNumber = phoneNumberEditText.getText().toString();

        databaseHelper.updateUserProfile(username, newName, newLastName, newAddress, newPhoneNumber);

        Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }
}
