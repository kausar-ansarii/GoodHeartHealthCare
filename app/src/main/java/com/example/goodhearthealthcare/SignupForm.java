package com.example.goodhearthealthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SignupForm extends AppCompatActivity {

    TextInputEditText patientGender,patientMarital;
    Spinner marital_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_form);

        patientGender = findViewById(R.id.patientGender);
        patientMarital = findViewById(R.id.patientMarital);
        marital_spinner = findViewById(R.id.marital_spinner);

        //SPINNER(Dropdown --> In android we call it as Spinner) CODE FOR MARITAL STATUS
        ArrayAdapter<CharSequence> adapter4Marital = ArrayAdapter.createFromResource(this, R.array.marital_array, android.R.layout.simple_spinner_item);
        adapter4Marital.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        marital_spinner.setAdapter(adapter4Marital);
        marital_spinner.setOnItemSelectedListener(new MaritalSpinner());
    }

    public void onRadioButtonCLicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radioMale:
                if (checked)
                    patientGender.setText("Male");
                break;
            case R.id.radioFemale:
                if (checked)
                    patientGender.setText("Female");
                break;
        }
    }

    private class MaritalSpinner implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String itemSpinner = parent.getItemAtPosition(position).toString();
            patientMarital.setText(itemSpinner);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) { }
    }
}