package com.example.foodwastagemanagement.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.foodwastagemanagement.R;
import com.example.foodwastagemanagement.dao.DAO;
import com.example.foodwastagemanagement.form.Agent;
import com.example.foodwastagemanagement.form.User;
import com.example.foodwastagemanagement.util.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
public class AgentRegistration extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    EditText e1,e2,e3,e4,e5,e6,e7;
    Button b1;
    String orphanage;
    ArrayList<String> al=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_agent_registration);

        e1=(EditText)findViewById(R.id.registerPhone1);
        e2=(EditText)findViewById(R.id.registerPassword1);
        e3=(EditText)findViewById(R.id.registerConPass1);
        e4=(EditText)findViewById(R.id.registerEmail1);
        e5=(EditText)findViewById(R.id.registerMobile1);
        e6=(EditText)findViewById(R.id.registerName1);
        e7=(EditText)findViewById(R.id.registerAddress1);

        Spinner spin = (Spinner) findViewById(R.id.spinner1);
        spin.setOnItemSelectedListener(this);

        DAO d=new DAO();
        d.getDBReference(Constants.USER_DB).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshotNode: dataSnapshot.getChildren()) {

                    User user=(User)snapshotNode.getValue(User.class);

                    if(user!=null && user.getType().equals("orphanage"))
                    {
                      al.add(user.getUsername());
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

        b1=(Button)findViewById(R.id.registerButton1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username=e1.getText().toString();
                String password=e2.getText().toString();
                String conformPassword=e3.getText().toString();
                String email=e4.getText().toString();
                String mobile=e5.getText().toString();
                String name=e6.getText().toString();
                String address=e7.getText().toString();

                if(username==null|| password==null|| conformPassword==null|| email==null|| mobile==null|| name==null|| address==null)
                {
                    Toast.makeText(getApplicationContext(),"Please Enter Valid Data",Toast.LENGTH_SHORT).show();
                }
                else if(mobile.length()<10|| mobile.length()>12)
                {
                    Toast.makeText(getApplicationContext(),"Invalid Mobile",Toast.LENGTH_SHORT).show();
                }
                else if(!password.equals(conformPassword))
                {
                    Toast.makeText(getApplicationContext(),"Password Mismatch",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Agent agent=new Agent();

                    agent.setUsername(username);
                    agent.setPassword(password);
                    agent.setEmail(email);
                    agent.setMobile(mobile);
                    agent.setName(name);
                    agent.setAddress(address);
                    agent.setOrphanage(orphanage);

                    DAO dao=new DAO();

                    try
                    {
                        dao.addObject(Constants.AGENT_DB,agent,agent.getUsername());

                        Toast.makeText(getApplicationContext(),"Register Success",Toast.LENGTH_SHORT).show();

                        Intent i=new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(i);
                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(getApplicationContext(),"Register Error",Toast.LENGTH_SHORT).show();
                        Log.v("User Registration Ex", ex.toString());
                        ex.printStackTrace();
                    }

                }
            }
        });
    }

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        orphanage=al.get(position);
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}
