package com.application.banque;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.application.banque.Database.DatabaseHelper;
import com.application.banque.models.User;

public class SignUpActivity extends AppCompatActivity {

    private Spinner typeOfAccountSpinner;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        typeOfAccountSpinner = findViewById(R.id.typeOfAccountSpinner);
        databaseHelper = new DatabaseHelper(this);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.account_types,
                android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeOfAccountSpinner.setAdapter(adapter);


        typeOfAccountSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedAccountType = parentView.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        EditText nameInput = findViewById(R.id.nameInput);
        EditText lastNameInput = findViewById(R.id.lastNameInput);
        EditText addressInput = findViewById(R.id.addressInput);
        EditText phoneNumberInput = findViewById(R.id.phoneNumberInput);
        Spinner typeOfAccountSpinner = findViewById(R.id.typeOfAccountSpinner);
        EditText passwordInput = findViewById(R.id.passwordInput);

        String name = nameInput.getText().toString();
        String lastName = lastNameInput.getText().toString();
        String address = addressInput.getText().toString();
        String phoneNumber = phoneNumberInput.getText().toString();
        String typeOfAccount = typeOfAccountSpinner.getSelectedItem().toString();
        String password = passwordInput.getText().toString();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(address)
                || TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(typeOfAccount)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User(0, name, lastName, address, phoneNumber, typeOfAccount, password, "Pending", 1000);

        long newRowId = databaseHelper.addUser(user);

        if (newRowId != -1) {
            Toast.makeText(this, "Registration Successful. Awaiting approval.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }
}
