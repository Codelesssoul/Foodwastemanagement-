package com.example.foodwastagemanagement.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodwastagemanagement.R;
import com.example.foodwastagemanagement.dao.DAO;
import com.example.foodwastagemanagement.form.AppMessage;
import com.example.foodwastagemanagement.util.Constants;
import com.example.foodwastagemanagement.util.Session;

public class OrphanageSendMessage extends AppCompatActivity {

    EditText text;
    Button post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_orphanage_send_message);
        final Session s = new Session(getApplicationContext());

        text = (EditText) findViewById(R.id.orphanagesendmessagetext);
        post=(Button) findViewById(R.id.orphanagemessagesubmit);

        Intent i = getIntent();
        savedInstanceState = i.getExtras();

        final String agentid = savedInstanceState.getString("agentid");

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(agentid!=null) {

                    String message = text.getText().toString();

                    if (message == null) {
                        Toast.makeText(getApplicationContext(), "Please Enter Message", Toast.LENGTH_SHORT).show();
                    } else {

                        AppMessage msg = new AppMessage();

                        msg.setMid(new DAO().getUnicKey(Constants.MESSAGE_DB));
                        msg.setSender(s.getusename());
                        msg.setReceiver(agentid);
                        msg.setTextmessage(message);

                        new DAO().addObject(Constants.MESSAGE_DB, msg, msg.getMid());

                        Intent i = new Intent(getApplicationContext(), OrphanageHome.class);
                        startActivity(i);
                    }
                }
            }
        });
    }
}
