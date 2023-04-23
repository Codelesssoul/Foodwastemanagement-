package com.example.foodwastagemanagement.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodwastagemanagement.form.Agent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import com.example.foodwastagemanagement.R;
import com.example.foodwastagemanagement.dao.DAO;
import com.example.foodwastagemanagement.util.Constants;
import com.example.foodwastagemanagement.util.Session;

public class AgentLogin extends AppCompatActivity {

    private Session session;
    EditText e1,e2;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new Session(getApplicationContext());

        setContentView(R.layout.activity_agent_login);

        e1=(EditText)findViewById(R.id.loginPhone1);
        e2=(EditText)findViewById(R.id.loginPass1);
        b1=(Button)findViewById(R.id.loginConfirm1);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String username=e1.getText().toString();
                final String password=e2.getText().toString();

                if(username==null|| password==null || username.length()<=0|| password.length()<=0)
                {
                    Toast.makeText(getApplicationContext(),"Please Enter UserName and Password",Toast.LENGTH_SHORT).show();
                }
                else {

                    DAO d = new DAO();
                    d.getDBReference(Constants.AGENT_DB).child(username).addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Agent agent= (Agent) dataSnapshot.getValue(Agent.class);

                            if (agent == null) {
                                Toast.makeText(getApplicationContext(), "Invalid UserName ", Toast.LENGTH_SHORT).show();
                            } else if (agent != null && agent.getPassword().equals(password)) {

                                session.setusename(agent.getUsername());
                                session.setRole("agent");

                                Intent i= new Intent(getApplicationContext(),AgentHome.class);
                                startActivity(i);

                            } else {
                                Toast.makeText(getApplicationContext(), "In valid Password", Toast.LENGTH_SHORT).show();
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
