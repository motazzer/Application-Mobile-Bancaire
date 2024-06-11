package com.application.banque;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.application.banque.Database.DatabaseHelper;
import com.application.banque.models.User;
import com.application.banque.services.UserListAdapter;

import java.util.List;

public class GetAllUsersActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private ListView usersListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getallusers);
        databaseHelper = new DatabaseHelper(this);
        usersListView = findViewById(R.id.usersListView);
        displayAllUsers();
    }
    private void displayAllUsers() {
        List<User> allUsers = databaseHelper.getAllUsers();
        UserListAdapter adapter = new UserListAdapter(this, R.layout.list_item_user, allUsers);
        usersListView.setAdapter(adapter);
        usersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User selectedUser = allUsers.get(position);
                showDeleteConfirmationDialog(selectedUser);
            }
        });
    }
    private void showDeleteConfirmationDialog(final User user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete User");
        builder.setMessage("Are you sure you want to delete this user?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteUser(user);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

    private void deleteUser(User user) {
        databaseHelper.deleteUser(user.getId());
        displayAllUsers();
    }
}