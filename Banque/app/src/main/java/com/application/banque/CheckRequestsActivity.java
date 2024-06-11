package com.application.banque;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.application.banque.Database.DatabaseHelper;
import com.application.banque.models.Check;
import com.application.banque.models.User;

import java.util.List;

public class CheckRequestsActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private ListView checkRequestsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_requests);
        databaseHelper = new DatabaseHelper(this);

        checkRequestsListView = findViewById(R.id.checkRequestsListView);
        if (checkRequestsListView != null) {
            displayCheckRequests();
        }

        Button viewCheckRequestsButton = findViewById(R.id.viewCheckRequestsButton);
        viewCheckRequestsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewCheckRequests();
            }
        });
    }

    private void displayCheckRequests() {
        List<Check> checkRequests = databaseHelper.getAllCheckRequests();

        ArrayAdapter<Check> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, checkRequests);
        checkRequestsListView.setAdapter(adapter);

        checkRequestsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showApprovalDialog((Check) parent.getItemAtPosition(position));
            }
        });
    }
    private void showApprovalDialog(final Check check) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Check Request");
        builder.setMessage("Do you want to approve or dismiss this check request?");
        builder.setPositiveButton("Approve", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateCheckStatus(check.getId(), "Approved");
            }
        });
        builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateCheckStatus(check.getId(), "Dismissed");
            }
        });
        builder.show();
    }
    private void updateCheckStatus(long checkId, String newStatus) {
        Check check = databaseHelper.getCheckById(checkId);

        if (check != null && "Pending".equals(check.getStatus()) && "Approved".equals(newStatus)) {
            // Retrieve user associated with the check
            User user = databaseHelper.getUserByName(check.getUsername());

            if (user != null) {
                // Update user's balance
                double currentBalance = databaseHelper.getUserBalance(user.getName());
                double newBalance = currentBalance + check.getAmount();
                databaseHelper.updateUserBalance(user.getName(), newBalance);

                // Update check status
                databaseHelper.updateCheckStatus(checkId, newStatus);

                // Display updated check requests
                displayCheckRequests();

                // Set result for handling in onActivityResult if needed
                Intent resultIntent = new Intent();
                resultIntent.putExtra("status", newStatus);
                setResult(RESULT_OK, resultIntent);
            }}}
    private void viewCheckRequests() {
        Intent intent = new Intent(CheckRequestsActivity.this, CheckRequestsListActivity.class);
        startActivity(intent);
    }
}