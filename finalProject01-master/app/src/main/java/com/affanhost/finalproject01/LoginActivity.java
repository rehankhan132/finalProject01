package com.affanhost.finalproject03;

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
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText edtTxtEmailLogin, edtTextPasswordLogin;
    private TextView tvForgotPassword;
    private Button btnLogin;
    private ProgressBar progressBarLogin;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();



        TextView signUpActivity = findViewById(R.id.txtViewSignUpFromLogin);
        signUpActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(i);
            }
        });

        TextView homeActivity = findViewById(R.id.txtViewHomeFromLogin);
        homeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(i);
            }
        });

        mAuth = FirebaseAuth.getInstance();

        tvForgotPassword = findViewById(R.id.tvForgotPassword);

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,ForgotPassword.class));
            }
        });

        edtTxtEmailLogin = findViewById(R.id.edtTxtEmailLogin);
        edtTextPasswordLogin = findViewById(R.id.edtTextPasswordLogin );

        progressBarLogin = findViewById(R.id.progressBarLogin);

        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });





    }

    private void userLogin() {
        String email = edtTxtEmailLogin.getText().toString().trim();
        String password = edtTextPasswordLogin.getText().toString().trim();

        if(email.isEmpty()){
            edtTxtEmailLogin.setError("Email is Required!");
            edtTxtEmailLogin.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtTxtEmailLogin.setError("Provide valid Email!");
            edtTxtEmailLogin.requestFocus();
            return;
        }

        if(password.isEmpty()){
            edtTextPasswordLogin.setError("Password is Required!");
            edtTextPasswordLogin.requestFocus();
            return;
        }

        if(password.length() < 6){
            edtTextPasswordLogin.setError("Password must be greater than and equal to 6 characters!");
            edtTextPasswordLogin.requestFocus();
            return;
        }

        progressBarLogin.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if(user.isEmailVerified()) {
                        // Redirects to Profile Activity
                        startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
                    }else{
                        user.sendEmailVerification();
                        Toast.makeText(LoginActivity.this,"Verify your email please.", Toast.LENGTH_LONG).show();
                        progressBarLogin.setVisibility(View.GONE);
                    }

                }else{
                    Toast.makeText(LoginActivity.this,"Login Failed! Try Again!", Toast.LENGTH_LONG).show();
                    progressBarLogin.setVisibility(View.GONE);
                }
            }
        });
    }
}