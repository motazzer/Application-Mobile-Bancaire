package com.application.banque.services;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.application.banque.R;
import com.application.banque.models.User;

import java.util.List;

public class UserListAdapter extends ArrayAdapter<User> {

    private Context context;
    private int resource;

    public UserListAdapter(Context context, int resource, List<User> users) {
        super(context, resource, users);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, parent, false);
        }

        User user = getItem(position);

        if (user != null) {
            TextView textViewName = convertView.findViewById(R.id.textViewName);
            TextView textViewDetails = convertView.findViewById(R.id.textViewDetails);
            TextView textViewBalance = convertView.findViewById(R.id.textViewBalance);

            textViewName.setText(user.getName());
            textViewDetails.setText(String.format("%s | %s | %s", user.getLastName(), user.getAddress(), user.getPhoneNumber()));
            textViewBalance.setText("Balance: $" + user.getBalance());  // Add this line
        }

        return convertView;
    }
}
