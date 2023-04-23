package com.example.foodwastagemanagement.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.foodwastagemanagement.R;
import com.example.foodwastagemanagement.dao.DAO;
import com.example.foodwastagemanagement.form.Food;
import com.example.foodwastagemanagement.util.Constants;
import com.example.foodwastagemanagement.util.MapUtil;
import com.example.foodwastagemanagement.util.Session;

public class ListFood extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_food);

        listView=(ListView) findViewById(R.id.FoodList);
        final Session s=new Session(getApplicationContext());

        final DAO dao=new DAO();

        dao.setDataToAdapterList(listView, Food.class, Constants.FOOD_DB,"employee");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String item = listView.getItemAtPosition(position).toString();
                item= MapUtil.stringToMap(s.getViewMap()).get(item);

                Intent intent=new Intent(getApplicationContext(),ViewFood.class);
                intent.putExtra("foodid",item);
                startActivity(intent);
            }
        });
    }
}