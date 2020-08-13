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

import static com.google.firebase.auth.FirebaseAuth.getInstance;

public class signupActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText name, email, password, cpassword;

    Button signup;
    TextView signin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Intent intent = getIntent();
        intent.getStringExtra("email");

        mAuth = FirebaseAuth.getInstance();
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        cpassword = findViewById(R.id.cpassword);
        signup = findViewById(R.id.signup);
        signin = findViewById(R.id.signin);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signupActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String nameInput = name.getText().toString();
                String emailInput = email.getText().toString();
                String passwordInput = password.getText().toString();
                String cpasswordInput = cpassword.getText().toString();

                if (nameInput.equals("")) {

                    Toast.makeText(signupActivity.this, "Please enter your name", Toast.LENGTH_LONG).show();
                } else if (emailInput.equals("")) {
                    Toast.makeText(signupActivity.this, "Please enter your email id", Toast.LENGTH_LONG).show();
                } else if (passwordInput.equals("")) {
                    Toast.makeText(signupActivity.this, "Please enter your password", Toast.LENGTH_LONG).show();
                } else if (cpasswordInput.equals("")) {
                    Toast.makeText(signupActivity.this, "Please enter your confirm password", Toast.LENGTH_LONG).show();
                } else if (!passwordInput.equals(cpasswordInput)) {

                    Toast.makeText(signupActivity.this, "Your passwords are wrong", Toast.LENGTH_LONG).show();
                } else if (passwordInput.length() < 7) {
                    Toast.makeText(signupActivity.this, "Your password is not strong!", Toast.LENGTH_LONG).show();
                } else {
                    signup(emailInput, passwordInput);
                }


            }


        });


    }

    void signup(String emailInput, String passwordInput) {

        mAuth.createUserWithEmailAndPassword(emailInput, passwordInput).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(signupActivity.this, "Your account is successfully created", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(signupActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            }

        });


    }
}
