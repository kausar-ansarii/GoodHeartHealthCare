package com.example.goodhearthealthcare;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class SignupForm extends AppCompatActivity {

    TextInputEditText patientFirstName, patientLastName, patientDOB, patientAge, patientGender,
            patientMarital, patientAddress, patientCity, patientDistrict, patientPin, patientPhone,
            patientAltPhone, patientEmail, patientPassword;
    Spinner marital_spinner;
    Button submitButton;
    TextView selectDOBPatient, setPatientAge;
    DatePickerDialog.OnDateSetListener mDateSetListenerBirth, mDateSetListenerCurr;
    FirebaseAuth mAuth;
    ProgressDialog loadingBar;
    DatabaseReference patientsRef;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_form);

        mAuth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this);

        patientsRef = FirebaseDatabase.getInstance().getReference().child("Patients");

        //HOOKS FOR THE VARIABLES(for eg. TextFields, Buttons, Views, etc)
        //--This is for TextInputLayout
        patientFirstName = findViewById(R.id.patientFirstName);
        patientLastName = findViewById(R.id.patientLastName);
        patientDOB = findViewById(R.id.patientDOB);
        patientAge = findViewById(R.id.patientAge);
        patientAddress = findViewById(R.id.patientAddress);
        patientCity = findViewById(R.id.patientCity);
        patientDistrict = findViewById(R.id.patientDistrict);
        patientPin = findViewById(R.id.patientPin);
        patientPhone = findViewById(R.id.patientPhone);
        patientAltPhone = findViewById(R.id.patientAltPhone);
        patientEmail = findViewById(R.id.patientEmail);
        patientPassword = findViewById(R.id.patientPassword);
        patientGender = findViewById(R.id.patientGender);
        patientMarital = findViewById(R.id.patientMarital);
        marital_spinner = findViewById(R.id.marital_spinner);

        //This is for TextView
        selectDOBPatient = findViewById(R.id.selectDOBPatient);
        setPatientAge = findViewById(R.id.setPatientAge);

        selectDOBPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String date = simpleDateFormat.format(Calendar.getInstance().getTime());
                //patientDOB.setText(date);

                DatePickerDialog dialog = new DatePickerDialog(
                        SignupForm.this,
                        android.R.style.Animation_Dialog,
                        mDateSetListenerBirth,
                        year, month, day);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.show();
            }
        });

        mDateSetListenerBirth = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                //Log.d(TAG,"onDateSet: " + month + "/" + dayOfMonth + "/" + year);
                final String proDate = dayOfMonth + "/" + month + "/" + year;
                //Toast.makeText(SignupForm.this, proDate, Toast.LENGTH_SHORT).show();
                patientDOB.setText(proDate);
            }
        };

        setPatientAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String date = simpleDateFormat.format(Calendar.getInstance().getTime());
                //patientDOB.setText(date);

                DatePickerDialog dialog = new DatePickerDialog(
                        SignupForm.this,
                        android.R.style.Animation_Dialog,
                        mDateSetListenerCurr,
                        year, month, day);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.show();
            }
        });

        mDateSetListenerCurr = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                //Log.d(TAG,"onDateSet: " + month + "/" + dayOfMonth + "/" + year);
                final String currDate = dayOfMonth + "/" + month + "/" + year;
                //Toast.makeText(SignupForm.this, proDate, Toast.LENGTH_SHORT).show();
                //patientDOB.setText(proDate);
                String sDate = patientDOB.getText().toString();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date date1 = simpleDateFormat.parse(sDate);
                    Date date2 = simpleDateFormat.parse(currDate);

                    long startDate = date1.getTime();
                    long currentDate = date2.getTime();
                    if (startDate <= currentDate) {
                        Period period = new Period(startDate, currentDate, PeriodType.years());
                        int years = period.getYears();
                        patientAge.setText(years + " years");
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        };

        submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fName = patientFirstName.getText().toString();
                String lName = patientLastName.getText().toString();
                String dob = patientDOB.getText().toString();
                String age = patientAge.getText().toString();
                String gender = patientGender.getText().toString();
                String marital = patientMarital.getText().toString();
                String address = patientAddress.getText().toString();
                String city = patientCity.getText().toString();
                String district = patientDistrict.getText().toString();
                String pincode = patientPin.getText().toString();
                String phone = patientPhone.getText().toString();
                String altPhone = patientAltPhone.getText().toString();
                String email = patientEmail.getText().toString();
                String password = patientPassword.getText().toString();

                if (fName.isEmpty() || lName.isEmpty() || dob.isEmpty() || age.isEmpty() || gender.isEmpty() || marital.isEmpty()
                        || address.isEmpty() || city.isEmpty() || district.isEmpty() || pincode.isEmpty() || phone.isEmpty() || altPhone.isEmpty() ||
                        email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignupForm.this, "Field's are empty", Toast.LENGTH_SHORT).show();
                } else {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                    loadingBar.setTitle("please wait...");
                    loadingBar.setMessage("account creation is in progress don't press back button");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();
                    userID = mAuth.getCurrentUser().getUid();
                    HashMap patientMap = new HashMap();
                    patientMap.put("fName", fName);
                    patientMap.put("lName", lName);
                    patientMap.put("DOB", dob);
                    patientMap.put("Age", age);
                    patientMap.put("Gender", gender);
                    patientMap.put("MaritalStatus", marital);
                    patientMap.put("Address", address);
                    patientMap.put("City", city);
                    patientMap.put("District", district);
                    patientMap.put("Pincode", pincode);
                    patientMap.put("Phone", phone);
                    patientMap.put("AltPhone", altPhone);
                    patientMap.put("Email", email);
                    patientMap.put("Password", password);
                    patientMap.put("image", "default");
                    patientMap.put("userID", userID);
                    patientsRef.child(userID).updateChildren(patientMap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()) {
                                AuthCredential credentialAuth = EmailAuthProvider.getCredential(email, password);
                                mAuth.getCurrentUser().linkWithCredential(credentialAuth);
                                Intent intent = new Intent(getApplicationContext(), AddDiseasesActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            } else {
                                String msg = task.getException().getMessage();
                                Toast.makeText(SignupForm.this, msg, Toast.LENGTH_SHORT).show();
                            }
                            loadingBar.dismiss();
                        }
                    });
                    /*loadingBar.setTitle("please wait...");
                    loadingBar.setMessage("creation of account and saving of data is in progress. Don't click back button");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                userId = mAuth.getCurrentUser().getUid();
                                HashMap patientMap = new HashMap();
                                patientMap.put("fName", fName);
                                patientMap.put("lName", lName);
                                patientMap.put("DOB", dob);
                                patientMap.put("Age", age);
                                patientMap.put("Gender", gender);
                                patientMap.put("MaritalStatus", marital);
                                patientMap.put("Address", address);
                                patientMap.put("City", city);
                                patientMap.put("District", district);
                                patientMap.put("Pincode", pincode);
                                patientMap.put("Phone", phone);
                                patientMap.put("AltPhone", altPhone);
                                patientMap.put("Email", email);
                                patientMap.put("Password", password);
                                patientMap.put("image", "default");
                                patientMap.put("userID", userId);
                                patientsRef.child(userId).updateChildren(patientMap).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        if (task.isSuccessful()) {
                                            Intent intent = new Intent(getApplicationContext(), phoneNumber.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            intent.putExtra("phone",phone);
                                            intent.putExtra("email",email);
                                            intent.putExtra("password",password);
                                            startActivity(intent);
                                            finish();

                                        } else {
                                            String msg = task.getException().getMessage();
                                            Toast.makeText(SignupForm.this, msg, Toast.LENGTH_SHORT).show();
                                        }
                                        loadingBar.dismiss();
                                    }
                                });
                            } else {
                                String msg = task.getException().getMessage();
                                Toast.makeText(SignupForm.this, "Error: " + msg, Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });*/
                }
            }
        });

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
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }
}