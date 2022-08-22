package com.example.goodhearthealthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;

public class AddDiseasesActivity extends AppCompatActivity {

    LinearLayout linearDiabetic, linearPreviousMedication;
    Button diseaseProceedBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diseases);

        linearDiabetic = findViewById(R.id.linearDiabetic);
        linearPreviousMedication = findViewById(R.id.linearPreviousMedication);
        diseaseProceedBtn = findViewById(R.id.diseaseProceedBtn);
        diseaseProceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

    }

    public void onRadioButtonDiabeticCLicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.diabeticYes:
                if (checked)
                    linearDiabetic.setVisibility(View.VISIBLE);
                break;
            case R.id.diabeticNo:
                if (checked)
                    linearDiabetic.setVisibility(View.GONE);
                break;
        }
    }

    public void onRadioButtonPreMediCLicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.previousMedicationYes:
                if (checked)
                    linearPreviousMedication.setVisibility(View.VISIBLE);
                break;
            case R.id.previousMedicationNo:
                if (checked)
                    linearPreviousMedication.setVisibility(View.GONE);
                break;
        }
    }

    public void onRadioButtonAllergiesClicked(View view) {
    }
}