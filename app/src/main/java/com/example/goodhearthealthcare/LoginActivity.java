package com.example.goodhearthealthcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    TextView signUpEditText, LoginReceptionist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signUpEditText = findViewById(R.id.signupText);
        LoginReceptionist = findViewById(R.id.ReceptionistLoginText);
        LoginReceptionist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ReceptionistScreen = new Intent(LoginActivity.this, LoginReceptionist.class);
                startActivity(ReceptionistScreen);
            }
        });
        signUpEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent otpScreen = new Intent(LoginActivity.this, phoneNumber.class);
                startActivity(otpScreen);

            }
        });

    }
}

