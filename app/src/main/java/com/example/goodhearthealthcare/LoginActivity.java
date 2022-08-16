package com.example.goodhearthealthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView signUpEditText = (TextView) findViewById(R.id.signupText);
        signUpEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent otpScreen = new Intent(LoginActivity.this, phoneNumber.class);
                startActivity(otpScreen);

            }
        });

    }
}

