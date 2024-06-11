package com.application.banque;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.application.banque.Database.DatabaseHelper;
import com.application.banque.models.User;
import com.application.banque.services.UserListAdapter;

import java.util.List;

public class UserSignupApprovalActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private ListView usersListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup_approval);

        databaseHelper = new DatabaseHelper(this);
        usersListView = findViewById(R.id.usersListView);
        if (usersListView != null) {
            displayPendingUsers();
            setListItemClickListener();
        }

    }


    private void displayPendingUsers() {
        List<User> pendingUsers = databaseHelper.getPendingUsers();
        UserListAdapter adapter = new UserListAdapter(this, R.layout.list_item_user, pendingUsers);
        usersListView.setAdapter(adapter);
    }

    private void setListItemClickListener() {
        usersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showApprovalDialog((User) parent.getItemAtPosition(position));
            }
        });
    }

    private void showApprovalDialog(final User user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("User Approval")
                .setMessage("Do you want to approve or reject the user?")
                .setPositiveButton("Approve", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        approveUser(user);
                    }
                })
                .setNegativeButton("Reject", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        rejectUser(user);
                    }
                })
                .show();
    }

    private void approveUser(User user) {
        user.setBalance(1000.0);
        databaseHelper.updateUserStatus(user.getId(), "Approved");
        databaseHelper.updateUserBalance(user.getName(), user.getBalance());
        displayPendingUsers();
    }

    private void rejectUser(User user) {
        databaseHelper.deleteUser(user.getId());
        displayPendingUsers();
    }


}