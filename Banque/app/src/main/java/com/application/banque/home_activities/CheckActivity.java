package com.application.banque.home_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.application.banque.CheckRequestsActivity;
import com.application.banque.Database.DatabaseHelper;
import com.application.banque.HomeActivity;
import com.application.banque.MainActivity;
import com.application.banque.R;

public class CheckActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private EditText amountEditText;
    private Button demandeCheckButton;
    private static final int REQUEST_CHECK_STATUS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        databaseHelper = new DatabaseHelper(this);

        amountEditText = findViewById(R.id.amountEditText);
        demandeCheckButton = findViewById(R.id.demandeCheckButton);

        demandeCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestCheck();
            }
        });
    }

    private void requestCheck() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        String amountStr = amountEditText.getText().toString();

        if (!amountStr.isEmpty()) {
            double amount = Double.parseDouble(amountStr);
            long newRowId = databaseHelper.addCheckRequest(username, amount, "Pending");
            if (newRowId != -1) {
                Toast.makeText(this, "Check request submitted", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CheckActivity.this, CheckRequestsActivity.class);
                startActivityForResult(intent, REQUEST_CHECK_STATUS);
                finish();
            } else {
                Toast.makeText(this, "Failed to submit check request", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please enter the amount", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CHECK_STATUS) {
            if (resultCode == RESULT_OK) {
                String status = data.getStringExtra("status");
                if ("Approved".equals(status)) {
                    Toast.makeText(this, "Check request approved by the administrator", Toast.LENGTH_SHORT).show();
                } else if ("Dismissed".equals(status)) {
                    Toast.makeText(this, "Check request dismissed by the administrator", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }}