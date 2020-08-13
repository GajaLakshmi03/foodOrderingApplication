package com.codeonboard.foodorderingapplicationuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrderActivity extends AppCompatActivity {

    TextView foodDetails;
    EditText qty;
    Button order;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;

    FoodModel foodModel;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference("orders");

        foodDetails = findViewById(R.id.foodDetails);
        qty = findViewById(R.id.qty);
        order = findViewById(R.id.order);

        firebaseAuth = FirebaseAuth.getInstance();


        final long timeNow = System.currentTimeMillis();


        Intent intent = getIntent();
        foodModel = (FoodModel) intent.getSerializableExtra("foodModel");
        foodDetails.setText("\n\n"+foodModel.getFoodname()+"\n"+foodModel.getDescription()+"\nPrice: "+foodModel.getPrice());

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String qtyInput = qty.getText().toString();
                if(qtyInput.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please enter valid quantity",Toast.LENGTH_LONG).show();
                }
                else{
                    OrderModel orderModel = new OrderModel("Order placed",foodModel.getDescription(),foodModel.getFoodname(),
                            foodModel.getPrice(),qtyInput,String.valueOf(Integer.parseInt(qtyInput)*Integer.parseInt(foodModel.getPrice())),String.valueOf(timeNow));

                    myRef.child(firebaseAuth.getUid()).setValue(orderModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Order successfully placed!",Toast.LENGTH_LONG).show();
                                finish();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),task.getException().getLocalizedMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });



    }
}