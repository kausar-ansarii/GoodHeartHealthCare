package com.example.goodhearthealthcare.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.goodhearthealthcare.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    FirebaseAuth mAuth;
    String currUserId;
    DatabaseReference profileRef;
    TextView userPhone, userAltPhone, userEmail, userAddress, userDOB, userMaritalStatus, userAge, userGender, userId;
    ProgressDialog loadingBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mAuth = FirebaseAuth.getInstance();
        currUserId = mAuth.getCurrentUser().getUid();
        profileRef = FirebaseDatabase.getInstance().getReference().child("Patients").child(currUserId);

        loadingBar = new ProgressDialog(getActivity());

        userPhone = view.findViewById(R.id.userPhone);
        userAltPhone = view.findViewById(R.id.userAltPhone);
        userEmail = view.findViewById(R.id.userEmail);
        userAddress = view.findViewById(R.id.userAddress);
        userDOB = view.findViewById(R.id.userDOB);
        userMaritalStatus = view.findViewById(R.id.userMaritalStatus);
        userAge = view.findViewById(R.id.userAge);
        userGender = view.findViewById(R.id.userGender);
        userId = view.findViewById(R.id.userId);

        loadingBar.setTitle("please wait...");
        loadingBar.setMessage("fetching your details have patience");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
        profileRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String phone = dataSnapshot.child("Phone").getValue().toString();
                String altPhone = dataSnapshot.child("AltPhone").getValue().toString();
                String email = dataSnapshot.child("Email").getValue().toString();
                String add = dataSnapshot.child("Address").getValue().toString();
                String dob = dataSnapshot.child("DOB").getValue().toString();
                String mStatus = dataSnapshot.child("MaritalStatus").getValue().toString();
                String age = dataSnapshot.child("Age").getValue().toString();
                String gender = dataSnapshot.child("Gender").getValue().toString();
                String fname = dataSnapshot.child("fName").getValue().toString();
                String lname = dataSnapshot.child("lName").getValue().toString();
                String fnameStr = fname.substring(0,3).toUpperCase();
                String lnameStr = lname.substring(0,3).toUpperCase();
                String phoneStr = phone.substring(0,4).toUpperCase();
                String uniqueId = fnameStr+lnameStr+phoneStr;

                userPhone.setText(phone);
                userAltPhone.setText(altPhone);
                userEmail.setText(email);
                userAddress.setText(add);
                userDOB.setText(dob);
                userMaritalStatus.setText(mStatus);
                userAge.setText(age);
                userGender.setText(gender);
                userId.setText("ID - "+uniqueId);
                loadingBar.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
}