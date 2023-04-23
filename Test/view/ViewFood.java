package com.example.foodwastagemanagement.view;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodwastagemanagement.R;
import com.example.foodwastagemanagement.dao.DAO;
import com.example.foodwastagemanagement.form.Agent;
import com.example.foodwastagemanagement.form.Food;
import com.example.foodwastagemanagement.form.User;
import com.example.foodwastagemanagement.util.Constants;
import com.example.foodwastagemanagement.util.Session;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.List;
import java.util.Locale;

public class ViewFood extends AppCompatActivity {

    Button menuDeleteFood;
    Button viewFoodBack;
    Button updatefood;
    ImageView imageView;

    TextView t1,t2,t3,t4,t5,t6,t7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_food);

        menuDeleteFood=(Button) findViewById(R.id.menuDeleteFood);
        viewFoodBack=(Button) findViewById(R.id.viewFoodBack);
        updatefood=(Button) findViewById(R.id.updateFood);

        t1=(TextView) findViewById(R.id.foodviewname);
        t2=(TextView)findViewById(R.id.foodviewpreparedtime);
        t3=(TextView)findViewById(R.id.foodviewmembercount);
        t4=(TextView)findViewById(R.id.foodviewstatus);
        t5=(TextView)findViewById(R.id.foodviewpostedby);
        t6=(TextView)findViewById(R.id.foodviewlocation);
        t7=(TextView)findViewById(R.id.foodviewmobile);

        imageView = (ImageView) findViewById(R.id.foodviewimage);

        final Session s = new Session(getApplicationContext());

        Intent i = getIntent();
        savedInstanceState = i.getExtras();
        final String foodid = savedInstanceState.getString("foodid");

        DAO d=new DAO();
        d.getDBReference(Constants.FOOD_DB).child(foodid).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Food food=dataSnapshot.getValue(Food.class);

                if(!food.getPostedby().equals(s.getusename()))
                {
                    menuDeleteFood.setEnabled(false);
                    updatefood.setEnabled(false);
                }

                if(food!=null)
                {
                    String[] foodLocation=food.getLocation().split(",");

                    Geocoder geocoder;
                    List<Address> addresses;
                    geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                    String foodAddress="";

                    try {

                        addresses = geocoder.getFromLocation(new Double(foodLocation[0]),new Double(foodLocation[1]), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                        String city = addresses.get(0).getLocality();
                        String state = addresses.get(0).getAdminArea();
                        String country = addresses.get(0).getCountryName();
                        String postalCode = addresses.get(0).getPostalCode();
                        String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL

                        if(address!=null)
                        {
                            foodAddress=foodAddress+address+"\n";
                        }

                        if(city!=null)
                        {
                            foodAddress=foodAddress+city+"\n";
                        }

                        if(state!=null)
                        {
                            foodAddress=foodAddress+state+"\n";
                        }

                        if(country!=null)
                        {
                            foodAddress=foodAddress+country+"\n";
                        }

                        if(postalCode!=null)
                        {
                            foodAddress=foodAddress+postalCode+"\n";
                        }

                        if(knownName!=null)
                        {
                            foodAddress=foodAddress+knownName+"\n";
                        }
                    }
                    catch(Exception e)
                    {
                        Log.v("voidmain ","in on succes ");
                    }

                    Log.v("voidmain Address ",foodAddress);

                    t1.setText("Name :"+food.getName());
                    t2.setText("Prepared Time :"+food.getPreparedtime());
                    t3.setText("Description :"+food.getMembercount());
                    t4.setText("Status :"+food.getStatus());
                    t5.setText("Posted By :"+food.getPostedby());
                    t6.setText("Location :"+foodAddress);
                    t7.setText("Mobile:"+food.getMobile());

                    StorageReference ref = DAO.getStorageReference().child("images/" + food.getImage());
                    long ONE_MEGABYTE = 1024 * 1024 *5;
                    ref.getBytes(ONE_MEGABYTE)
                            .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                @Override
                                public void onSuccess(byte[] bytes) {

                                    Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                                    if(bm!=null)
                                    {
                                        imageView.setImageBitmap(bm);
                                    }
                                    else
                                    {
                                        Log.v("voidmain ","bm null");
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                            Log.v("voidmain ","image reading failure");
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        menuDeleteFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final DAO dao=new DAO();
                dao.deleteObject(Constants.FOOD_DB,foodid);

                Intent i= new Intent(getApplicationContext(),DonarHome.class);
                startActivity(i);
            }
        });

        updatefood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),UpdateFood.class);
                intent.putExtra("foodid",foodid);
                startActivity(intent);
            }
        });

        viewFoodBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=null;

                String role=s.getRole();

                if(role.equals("donar"))
                {
                    i= new Intent(getApplicationContext(),DonarHome.class);
                }else if(role.equals("user"))
                {
                    i= new Intent(getApplicationContext(),UserHome.class);
                }else if(role.equals("agent"))
                {
                    i= new Intent(getApplicationContext(),AgentHome.class);
                }
                else if(role.equals("orphanage"))
                {
                    i= new Intent(getApplicationContext(),OrphanageHome.class);
                }
                startActivity(i);
            }
        });
    }
}