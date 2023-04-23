package com.example.foodwastagemanagement.view;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodwastagemanagement.R;
import com.example.foodwastagemanagement.dao.DAO;
import com.example.foodwastagemanagement.form.AppMessage;
import com.example.foodwastagemanagement.util.Constants;
import com.example.foodwastagemanagement.util.Session;

public class ViewMessages extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_messages);

        listView=(ListView) findViewById(R.id.MessageList);
        final Session s=new Session(getApplicationContext());

        new DAO().setDataToAdapterList(listView, AppMessage.class, Constants.MESSAGE_DB,s.getusename());
    }
}
