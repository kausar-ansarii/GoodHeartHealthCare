package com.example.goodhearthealthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class OTPScreen extends AppCompatActivity {

    Button OTP_button;
    String userID, number, otpID, email, password, fName, lName, dob, age, address, city, district, pincode, gender, marital, altPhone;
    TextView otpText;
    FirebaseAuth mAuth;
    ProgressDialog dialog;
    TextInputLayout otpTextField;
    DatabaseReference patientsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpscreen);

        patientsRef = FirebaseDatabase.getInstance().getReference().child("Patients");

        dialog = new ProgressDialog(this);

        number = getIntent().getStringExtra("phone");
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");

        fName = getIntent().getStringExtra("fName");
        lName = getIntent().getStringExtra("lName");
        dob = getIntent().getStringExtra("dob");
        age = getIntent().getStringExtra("age");
        address = getIntent().getStringExtra("address");
        gender = getIntent().getStringExtra("gender");
        marital = getIntent().getStringExtra("marital");
        city = getIntent().getStringExtra("city");
        district = getIntent().getStringExtra("district");
        pincode = getIntent().getStringExtra("pincode");
        altPhone = getIntent().getStringExtra("AltPhone");
        mAuth = FirebaseAuth.getInstance();

        otpTextField = findViewById(R.id.otpTextField);

        otpText = findViewById(R.id.otpText);
        otpText.setText("OTP sent to +91-"+number);

        initiateOTP();

        OTP_button = findViewById(R.id.OTP_button);
        OTP_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otp = otpTextField.getEditText().getText().toString();
                if (otp.isEmpty()) {
                    Toast.makeText(OTPScreen.this, "OTP is empty", Toast.LENGTH_SHORT).show();
                } else {
                    PhoneAuthCredential authCredential = PhoneAuthProvider.getCredential(otpID, otp);
                    signInWithPhoneAuthCredential(authCredential);
                }
                /*Toast.makeText(getApplicationContext(), "OTP Verified ", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), SignupForm.class);
                intent.putExtra("phone",number);
                startActivity(intent);*/
            }
        });
    }

    private void initiateOTP() {
        dialog.setMessage("please wait");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91" + number)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(PhoneAuthCredential credential) {
                                signInWithPhoneAuthCredential(credential);
                                dialog.dismiss();
                            }

                            @Override
                            public void onVerificationFailed(FirebaseException e) {
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                                dialog.dismiss();
                                otpID = verificationId;
                                //mResendToken = token;
                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    AuthCredential credentialAuth = EmailAuthProvider.getCredential(email, password);
                    mAuth.getCurrentUser().linkWithCredential(credentialAuth);
                    /*FirebaseUser user = task.getResult().getUser();
                    String userID = user.getUid();

                    HashMap userMap = new HashMap();
                    userMap.put("Name", name);
                    userMap.put("Email", email);
                    userMap.put("Password", password);
                    userMap.put("Phone", phone);
                    userMap.put("uid", userID);
                    userMap.put("About", "Hello There, I'm fact lover. I'm using this app to grow my knowledge.");
                    db.collection("Users").document(userID).set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(VerifyPhoneActivity.this, "OTP Verified ", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), VerifyEmailActivity.class);
                                intent.putExtra("email",email);
                                startActivity(intent);
                            } else {
                                String msg = task.getException().getMessage();
                                Toast.makeText(VerifyPhoneActivity.this, msg, Toast.LENGTH_LONG).show();
                            }
                        }
                    });*/

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
                    patientMap.put("Phone", number);
                    patientMap.put("AltPhone", altPhone);
                    patientMap.put("Email", email);
                    patientMap.put("Password", password);
                    patientMap.put("image", "default");
                    patientMap.put("userID", userID);
                    patientsRef.child(userID).updateChildren(patientMap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "OTP Verified ", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();

                            } else {
                                String msg = task.getException().getMessage();
                                Toast.makeText(OTPScreen.this, msg, Toast.LENGTH_SHORT).show();
                            }
                            dialog.dismiss();
                        }
                    });

                    /*dialog.setTitle("please wait...");
                    dialog.setMessage("creation of account and saving of data is in progress. don't click back button");
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                            } else {
                                String msg = task.getException().getMessage();
                                Toast.makeText(OTPScreen.this, "Error: " + msg, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }
                    });*/
                    // Update UI
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}