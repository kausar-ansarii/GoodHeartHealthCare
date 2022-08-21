package com.example.goodhearthealthcare;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class LoginReceptionist extends AppCompatActivity {

    TextInputLayout UserID, Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_receptionist);

        UserID = findViewById(R.id.ReceptionistUserID);
        Password = findViewById(R.id.ReceptionistPassword);

        TextView LoginPatient = (TextView) findViewById(R.id.PatientLoginText);
        Button receptionistLogin_button = (Button) findViewById(R.id.receptionistLogin_button);
        receptionistLogin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginValidation();
            }
        });

        LoginPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent PatientLogin = new Intent(LoginReceptionist.this, LoginActivity.class);
                startActivity(PatientLogin);
            }
        });
    }

    private void loginValidation() {
        String useridstring = UserID.getEditText().getText().toString().trim();
        String passwordstring = Password.getEditText().getText().toString().trim();

        if (useridstring.isEmpty() || passwordstring.isEmpty()) {
            Toast.makeText(this, "Field's are empty", Toast.LENGTH_SHORT).show();
        } else {
            if (useridstring.equals("GoodHealth") && passwordstring.equals("GoodPassword")) {
                Intent intent = new Intent(LoginReceptionist.this, ReceptionistDashboard.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            } else {
                Log.d("Error Credentials", "Credentials are incorrect");
            }

        }
    }
}