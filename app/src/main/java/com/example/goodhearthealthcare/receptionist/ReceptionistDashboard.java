package com.example.goodhearthealthcare.receptionist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.goodhearthealthcare.R;

public class ReceptionistDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receptionist_dashboard);
    }

    public void addLabs(View view) {
        Intent intent = new Intent(this, AddLabsActivity.class);
        startActivity(intent);
    }

    public void viewLabs(View view) {
        Intent intent = new Intent(this, ViewLabsActivity.class);
        startActivity(intent);
    }

    public void addHospitalStaff(View view) {
        Intent intent = new Intent(this, AddStaffActivity.class);
        startActivity(intent);
    }

    public void viewHospitalStaff(View view) {
        Intent intent = new Intent(this, ViewStaffsActivity.class);
        startActivity(intent);
    }
}