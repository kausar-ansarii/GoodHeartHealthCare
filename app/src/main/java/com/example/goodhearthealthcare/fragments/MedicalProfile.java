package com.example.goodhearthealthcare.fragments;

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

public class MedicalProfile extends Fragment {

    TextView patientMediAge, patientMediDiabetes, patientMediThyroid, patientMediAllergies;
    FirebaseAuth mAuth;
    DatabaseReference profileRef;
    String userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_medical_profile, container, false);

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        profileRef = FirebaseDatabase.getInstance().getReference().child("Patients").child(userId);

        patientMediAge = view.findViewById(R.id.patientMediAge);
        patientMediDiabetes = view.findViewById(R.id.patientMediDiabetes);
        patientMediThyroid = view.findViewById(R.id.patientMediThyroid);
        patientMediAllergies = view.findViewById(R.id.patientMediAllergies);

        profileRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String age = snapshot.child("Age").getValue().toString();
                patientMediAge.setText(age);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
}