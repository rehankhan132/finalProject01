package com.affanhost.finalproject03;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private EditText edtTxtEmailForgotPassword;
    private Button btnForgotPassword;
    private ProgressBar progressBarForgotPassword;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        edtTxtEmailForgotPassword = findViewById(R.id.edtTxtEmailForgotPassword);
        btnForgotPassword = findViewById(R.id.btnForgotPassword);
        progressBarForgotPassword = findViewById(R.id.progressBarForgotPassword);

        auth = FirebaseAuth.getInstance();

        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ForgetPasword();
            }
        });






    }

    private void ForgetPasword() {

        String email = edtTxtEmailForgotPassword.getText().toString().trim();

        if(email.isEmpty()){
            edtTxtEmailForgotPassword.setError("Email is Required!");
            edtTxtEmailForgotPassword.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtTxtEmailForgotPassword.setError("Please provide a valid Email Address!");
            edtTxtEmailForgotPassword.requestFocus();
            return;
        }

        progressBarForgotPassword.setVisibility(View.VISIBLE);

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPassword.this,"Check your email please!",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(ForgotPassword.this,"Something went wrong",Toast.LENGTH_LONG).show();
                }
                progressBarForgotPassword.setVisibility(View.GONE);
            }
        });
    }
}