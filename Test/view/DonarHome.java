package com.example.foodwastagemanagement.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodwastagemanagement.MainActivity;
import com.example.foodwastagemanagement.R;
import com.example.foodwastagemanagement.util.Session;

public class DonarHome extends AppCompatActivity {

    Button addfood;
    Button donarLogout;
    Button viewfood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donar_home);

        addfood=(Button) findViewById(R.id.donaraddfood);
        viewfood=(Button) findViewById(R.id.donarviewfood);
        donarLogout=(Button) findViewById(R.id.donarlogout);

        final Session s = new Session(getApplicationContext());

        addfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),AddFood.class);
                startActivity(i);
            }
        });

        viewfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),ListFood.class);
                startActivity(i);
            }
        });

        donarLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s.loggingOut();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }
}