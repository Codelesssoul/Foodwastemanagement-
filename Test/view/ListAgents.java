package com.example.foodwastagemanagement.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import com.example.foodwastagemanagement.R;
import com.example.foodwastagemanagement.dao.DAO;
import com.example.foodwastagemanagement.form.Agent;
import com.example.foodwastagemanagement.util.Constants;
import com.example.foodwastagemanagement.util.Session;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListAgents extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    ArrayList<String> al=new ArrayList<String>();
    Button post;
    String agent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_agents);
        post=(Button) findViewById(R.id.listagentsubmit);

        Spinner spin = (Spinner) findViewById(R.id.spinner2);
        spin.setOnItemSelectedListener(this);

        final Session s = new Session(getApplicationContext());

        DAO d=new DAO();
        d.getDBReference(Constants.AGENT_DB).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshotNode: dataSnapshot.getChildren()) {

                    Agent agent=(Agent) snapshotNode.getValue(Agent.class);

                    if(agent!=null && agent.getOrphanage().equals(s.getusename()))
                    {
                        al.add(agent.getUsername());
                    }
                }

                //Creating the ArrayAdapter instance having the country list
                ArrayAdapter aa = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,al.toArray(new String[al.size()]));
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //Setting the ArrayAdapter data on the Spinner
                spin.setAdapter(aa);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),OrphanageSendMessage.class);
                i.putExtra("agentid",agent);
                startActivity(i);
            }
        });
    }

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
       agent=al.get(position);
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}
