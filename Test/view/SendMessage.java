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
import com.example.foodwastagemanagement.form.Agent;
import com.example.foodwastagemanagement.form.AppMessage;
import com.example.foodwastagemanagement.util.Constants;
import com.example.foodwastagemanagement.util.Session;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class SendMessage extends AppCompatActivity {

    EditText text;
    Button post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_send_message);
        final Session s = new Session(getApplicationContext());

        text = (EditText) findViewById(R.id.agentsendmessagetext);
        post=(Button) findViewById(R.id.agentmessagesubmit);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message = text.getText().toString();

                if (message == null) {
                    Toast.makeText(getApplicationContext(), "Please Enter Message", Toast.LENGTH_SHORT).show();
                } else {

                    DAO dao = new DAO();
                    dao.getDBReference(Constants.AGENT_DB).child(s.getusename()).addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Agent agent =dataSnapshot.getValue(Agent.class);

                            if(agent!=null)
                            {
                                AppMessage msg=new AppMessage();
                                msg.setMid(dao.getUnicKey(Constants.MESSAGE_DB));
                                msg.setSender(s.getusename());
                                msg.setReceiver(agent.getOrphanage());
                                msg.setTextmessage(message);

                                dao.addObject(Constants.MESSAGE_DB,msg,msg.getMid());

                                Intent i = new Intent(getApplicationContext(),AgentHome.class);
                                startActivity(i);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }
}
