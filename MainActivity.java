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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    EditText Username;
    EditText Password;
    Button login;
    TextView signup;
    FirebaseAuth mAuth;


    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if(firebaseUser!=null){
            Intent intent = new Intent(getApplicationContext(),search.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();


        Username = findViewById(R.id.Username);
        Password = findViewById(R.id.Password);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String UsernameInput = Username.getText().toString();
                String PasswordInput = Password.getText().toString();
                String Usernameregex="[a-zA-Z0-9,_-]+@[a-z]+\\.+[a-z]+";
                if (UsernameInput.equals("")) {
                    Toast.makeText(MainActivity.this, "Please enter your USERNAME", Toast.LENGTH_LONG).show();
                }
                else if(!UsernameInput.matches(Usernameregex)){
                    Toast.makeText(MainActivity.this, "Your username is improperly formed", Toast.LENGTH_LONG).show();

                }

                else if (PasswordInput.equals("")) {
                    Toast.makeText(MainActivity.this, "Please enter your PASSWORD", Toast.LENGTH_LONG).show();
                }
                else if (PasswordInput.length() < 7) {
                    Toast.makeText(MainActivity.this, "Please enter a valid password", Toast.LENGTH_LONG).show();
                }
                else {

                    mAuth.createUserWithEmailAndPassword(UsernameInput,PasswordInput).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Logged in Successfully!",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(),search.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(getApplicationContext(),task.getException().getLocalizedMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        } );
    signup.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent =new Intent (MainActivity.this,signupActivity.class);
            startActivity(intent);
            finish();
        }



    });

        }










    }



