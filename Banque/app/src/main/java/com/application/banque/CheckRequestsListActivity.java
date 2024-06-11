package com.application.banque;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.application.banque.Database.DatabaseHelper;
import com.application.banque.models.User;
import com.application.banque.services.UserListAdapter;

import java.util.List;

// CheckRequestsListActivity.java
public class CheckRequestsListActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private ListView usersListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_requests_list);

        databaseHelper = new DatabaseHelper(this);
        usersListView = findViewById(R.id.usersListView);

        displayUsersWithCheckRequests();
    }

    private void displayUsersWithCheckRequests() {
        List<User> usersWithCheckRequests = databaseHelper.getUsersWithCheckRequests();
        UserListAdapter adapter = new UserListAdapter(this, R.layout.list_item_user, usersWithCheckRequests);
        usersListView.setAdapter(adapter);
    }
}
