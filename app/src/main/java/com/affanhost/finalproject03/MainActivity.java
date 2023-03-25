package com.affanhost.finalproject03;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        TextView loginText = findViewById(R.id.txtViewLogin);

        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        TextView signUpText = findViewById(R.id.txtViewSignUp);

        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });
    }

    public void onBtnClick(View view) {

        TextView welcomeMessage = findViewById(R.id.txtViewWelcome);

        EditText name = findViewById(R.id.edtTxtName);

        welcomeMessage.setText("WELCOME! " + name.getText());
    }
}