package com.example.goodhearthealthcare.receptionist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.example.goodhearthealthcare.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;

public class AddStaffActivity extends AppCompatActivity {

    Button addStaffBtn;
    DatabaseReference hospitalRef, doctorsRef;
    ProgressDialog loadingBar;
    Spinner addStaffRole;
    String hospIDStr,hospNameStr,hospAddressStr;
    LinearLayout spinnerDoctorRoleLay;
    TextInputLayout staffRoleTextField,doctorSpecSpinnerLay,staffGenderLay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_staff);

        staffGenderLay = findViewById(R.id.staffGenderLay);
        staffRoleTextField = findViewById(R.id.staffRoleTextField);
        doctorSpecSpinnerLay = findViewById(R.id.doctorSpecSpinnerLay);

        spinnerDoctorRoleLay = findViewById(R.id.spinnerDoctorRoleLay);
        addStaffRole = findViewById(R.id.doctor_role_spinner);

        ArrayAdapter<CharSequence> adapter4Specialist = ArrayAdapter.createFromResource(this, R.array.specialist, android.R.layout.simple_spinner_item);
        adapter4Specialist.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addStaffRole.setAdapter(adapter4Specialist);
        addStaffRole.setOnItemSelectedListener(new SpecialistSpinner());
    }

    public void clearForm(View view) {}

    public void onRadioButtonCLicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.radioDoctor:
                if (checked) {
                    staffRoleTextField.getEditText().setText("Doctor");
                    spinnerDoctorRoleLay.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.radioCLerk:
                if (checked) {
                    staffRoleTextField.getEditText().setText("Clerk");
                    spinnerDoctorRoleLay.setVisibility(View.GONE);
                    doctorSpecSpinnerLay.getEditText().setText("NA");
                }
                break;
            case R.id.radioNurse:
                if (checked) {
                    staffRoleTextField.getEditText().setText("Nurse");
                    spinnerDoctorRoleLay.setVisibility(View.GONE);
                    doctorSpecSpinnerLay.getEditText().setText("NA");
                }
                break;
            case R.id.radioWardboy:
                if (checked) {
                    staffRoleTextField.getEditText().setText("Wardboy");
                    spinnerDoctorRoleLay.setVisibility(View.GONE);
                    doctorSpecSpinnerLay.getEditText().setText("NA");
                }
                break;
            case R.id.radioReceptionist:
                if (checked) {
                    staffRoleTextField.getEditText().setText("Receptionist");
                    spinnerDoctorRoleLay.setVisibility(View.GONE);
                    doctorSpecSpinnerLay.getEditText().setText("NA");
                }
                break;
            case R.id.radioWatchman:
                if (checked) {
                    staffRoleTextField.getEditText().setText("Watchman");
                    spinnerDoctorRoleLay.setVisibility(View.GONE);
                    doctorSpecSpinnerLay.getEditText().setText("NA");
                }
                break;
        }
    }

    public void onGenderRadioButtonCLicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radioMale:
                if (checked)
                    staffGenderLay.getEditText().setText("Male");
                break;
            case R.id.radioFemale:
                if (checked)
                    staffGenderLay.getEditText().setText("Female");
                break;
        }
    }

    public class SpecialistSpinner implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String itemSpinner = parent.getItemAtPosition(position).toString();
            doctorSpecSpinnerLay.getEditText().setText(itemSpinner);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}