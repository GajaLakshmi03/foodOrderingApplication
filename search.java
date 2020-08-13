package com.codeonboard.foodorderingapplicationuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class search extends AppCompatActivity {

    TextView allfoods;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef,myRef2;

    FoodModel foodModel;
    ArrayList<FoodModel> foodModels = new ArrayList<FoodModel>();
    EditText search_food;

    String message = "";

    ImageView imageView4;

    TextView orderStatus;

    FirebaseAuth mAuth;
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        allfoods = findViewById(R.id.allfoods);
        search_food = findViewById(R.id.search_food);
        imageView4 = findViewById(R.id.imageView4);
        mAuth = FirebaseAuth.getInstance();

        orderStatus = findViewById(R.id.orderStatus);

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference("foods");
        myRef2 = firebaseDatabase.getReference("orders");

        myRef2.child(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    orderStatus.setText(dataSnapshot.child("orderno").getValue().toString()+"\n" +dataSnapshot.child("currentStatus").getValue().toString());
                }
                else{
                    orderStatus.setText("No Live Orders!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = 0;
                String searchText = search_food.getText().toString();
                if(searchText.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please enter a food name to search",Toast.LENGTH_LONG).show();
                }
                else {
                    for(FoodModel foodModel: foodModels){
                        if(foodModel.getFoodname().matches(searchText)){
                            flag = 1;
                            Intent intent = new Intent(getApplicationContext(),OrderActivity.class);
                            intent.putExtra("foodModel",foodModel);
                            startActivity(intent);
                        }

                    }
                    if(flag==0){
                        Toast.makeText(getApplicationContext(),"No such food found!",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    message = "";
                    for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                        FoodModel foodModel;
                        foodModel = dataSnapshot1.getValue(FoodModel.class);
                        foodModels.add(foodModel);

                        message += foodModel.getFoodname()+"\n"+foodModel.getDescription()+"\n\nPrice: "+foodModel.getPrice()+"\n\n\n";
                    }
                    allfoods.setText(message);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }


}
