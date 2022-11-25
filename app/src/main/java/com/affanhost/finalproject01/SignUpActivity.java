package com.affanhost.finalproject01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText edtTxtNameSignup, edtTxtUsernameSignUp, edtTextEmailAddress, edtTextPasswordSignUp;
    private Button btnSignUp;
    private ProgressBar progressBarSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();



        TextView loginActivity = findViewById(R.id.txtViewLoginFromSignUp);
        loginActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });

        TextView homeActivity = findViewById(R.id.txtViewHomeFromSignup);
        homeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        mAuth = FirebaseAuth.getInstance();

        edtTxtNameSignup = findViewById(R.id.edtTxtNameSignUp);
        edtTxtUsernameSignUp = findViewById(R.id.edtTxtUsernameSignUp);
        edtTextEmailAddress = findViewById(R.id.edtTextEmailAddress);
        edtTextPasswordSignUp = findViewById(R.id.edtTextPasswordSignUp );

        progressBarSignUp = findViewById(R.id.progressBarSignup);

        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterUser();
            }
        });
    }

    private void RegisterUser() {
        String fullname = edtTxtNameSignup.getText().toString().trim();
        String username = edtTxtUsernameSignUp.getText().toString().trim();
        String email = edtTextEmailAddress.getText().toString().trim();
        String password = edtTextPasswordSignUp.getText().toString().trim();

        if(fullname.isEmpty()){
            edtTxtNameSignup.setError("Your Name is Required!");
            edtTxtNameSignup.requestFocus();
            return;
        }

        if(username.isEmpty()){
            edtTxtUsernameSignUp.setError("Username is Required!");
            edtTxtUsernameSignUp.requestFocus();
            return;
        }

        if(email.isEmpty()){
            edtTextEmailAddress.setError("Email Address is Required!");
            edtTextEmailAddress.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtTextEmailAddress.setError("Please provide valid email address!");
            edtTextEmailAddress.requestFocus();
            return;
        }

        if(password.isEmpty()){
            edtTextPasswordSignUp.setError("Password is Required!");
            edtTextPasswordSignUp.requestFocus();
            return;
        }

        if(password.length() < 6){
            edtTextPasswordSignUp.setError("Password should be more than 6 characters!");
            edtTextPasswordSignUp.requestFocus();
            return;
        }

        progressBarSignUp.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            User user = new User(fullname, username, email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(SignUpActivity.this, "User has been Registered Successfully!", Toast.LENGTH_LONG).show();
                                                progressBarSignUp.setVisibility(View.GONE);

                                                // Redirects to Login Activity
                                            }else{
                                                Toast.makeText(SignUpActivity.this, "Failed to Register! Try Again!",Toast.LENGTH_LONG).show();
                                                progressBarSignUp.setVisibility(View.GONE);
                                            }
                                        }
                                    });

                        }else{
                            Toast.makeText(SignUpActivity.this, "Failed to Register! Try Again!",Toast.LENGTH_LONG).show();
                            progressBarSignUp.setVisibility(View.GONE);
                        }
                    }
                });


    }
}