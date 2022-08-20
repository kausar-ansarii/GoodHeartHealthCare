package com.example.goodhearthealthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginReceptionist extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_receptionist);
        TextView LoginPatient = (TextView) findViewById(R.id.PatientLoginText);
        Button receptionistLogin_button = (Button) findViewById(R.id.receptionistLogin_button);
        receptionistLogin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginvalidation();
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

    private void loginvalidation() {
        EditText UserID = (EditText) findViewById(R.id.ReceptionistUserID);
        EditText Password = (EditText) findViewById(R.id.ReceptionistPassword);
        String useridstring = UserID.getText().toString().trim();
        String passwordstring = Password.getText().toString().trim();

        if (useridstring.isEmpty() || passwordstring.isEmpty()){
            Log.d("Error","Password and Username cannot be empty");
        }
        else {
            if(useridstring.equals("GoodHealth") && passwordstring.equals("GoodPassword") ){
               Intent intent =new Intent( LoginReceptionist.this,ReceptionistDashboard.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
            else {
                Log.d("Error Credentials","Credentials are incorrect");
            }

        }
    }
}