package com.example.foodwastagemanagement.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodwastagemanagement.R;
import com.example.foodwastagemanagement.dao.DAO;
import com.example.foodwastagemanagement.form.User;
import com.example.foodwastagemanagement.util.Constants;
import com.example.foodwastagemanagement.util.Session;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private Session session;
    EditText e1,e2;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new Session(getApplicationContext());

        setContentView(R.layout.activity_login);

        e1=(EditText)findViewById(R.id.loginPhone);
        e2=(EditText)findViewById(R.id.loginPass);
        b1=(Button)findViewById(R.id.loginConfirm);

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
                    d.getDBReference(Constants.USER_DB).child(username).addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            User user = (User) dataSnapshot.getValue(User.class);

                            if (user == null) {
                                Toast.makeText(getApplicationContext(), "Invalid UserName ", Toast.LENGTH_SHORT).show();
                            } else if (user != null && user.getPassword().equals(password)) {

                                session.setusename(user.getUsername());
                                session.setRole(user.getType());

                                Intent i = null;

                                if (user.getType().equals("donar")){
                                    i = new Intent(getApplicationContext(),DonarHome.class);
                                }else if (user.getType().equals("user")){
                                    i = new Intent(getApplicationContext(), UserHome.class);
                                }else if (user.getType().equals("orphanage")){
                                    i = new Intent(getApplicationContext(),OrphanageHome.class);
                                }

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
