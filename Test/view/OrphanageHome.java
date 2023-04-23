package com.example.foodwastagemanagement.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodwastagemanagement.MainActivity;
import com.example.foodwastagemanagement.R;
import com.example.foodwastagemanagement.util.Session;

public class OrphanageHome extends AppCompatActivity {

    Button orphanagelogout;
    Button orphanagesendmessage;
    Button orphanageviewmessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orphanage_home);

        orphanagelogout=(Button) findViewById(R.id.orphanagelogout);
        orphanagesendmessage= (Button) findViewById(R.id.orphanagesendmessagebutton);
        orphanageviewmessage = (Button) findViewById(R.id.orphanageviewmessagebutton);

        final Session s = new Session(getApplicationContext());

        orphanagesendmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),ListAgents.class);
                startActivity(i);
            }
        });

        orphanageviewmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),ViewMessages.class);
                startActivity(i);
            }
        });

        orphanagelogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                s.loggingOut();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }
}
