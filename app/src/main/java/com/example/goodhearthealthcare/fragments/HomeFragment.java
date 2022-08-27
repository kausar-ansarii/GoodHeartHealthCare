package com.example.goodhearthealthcare.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.goodhearthealthcare.R;
import com.example.goodhearthealthcare.services.BroadcastService;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class HomeFragment extends Fragment {

    ImageView calculateBmiImg, viewDoctorImg, viewLabsImg;

    //VARIABLES FOR APPOINTMENT
    ImageView viewAppliedAptImg, viewConfirmedAptImg, viewRejectedAptImg;

    TextView medicineOneTimer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        medicineOneTimer = view.findViewById(R.id.medicineOneTimer);

        //INITIALIZE THE TIME
        long duration = TimeUnit.HOURS.toMillis(1);

        initializeTimeCounter(duration);

        viewAppliedAptImg = view.findViewById(R.id.viewAppliedAptImg);
        viewAppliedAptImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewAppliedAppointment viewAppliedAppointment = new ViewAppliedAppointment();
                replaceFragment(viewAppliedAppointment,"fragmentB");
            }
        });

        viewConfirmedAptImg = view.findViewById(R.id.viewConfirmedAptImg);
        viewConfirmedAptImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewConfirmedAppointment confirmedAppointment = new ViewConfirmedAppointment();
                replaceFragment(confirmedAppointment,"fragmentB");
            }
        });

        viewRejectedAptImg = view.findViewById(R.id.viewRejectedAptImg);
        viewRejectedAptImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewAppointmentRejected rejected = new ViewAppointmentRejected();
                replaceFragment(rejected,"fragmentB");
            }
        });

        viewDoctorImg = view.findViewById(R.id.viewDoctorImg);
        viewDoctorImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewDoctorsList viewDoctorsList = new ViewDoctorsList();
                replaceFragment(viewDoctorsList,"fragmentB");
            }
        });

        viewLabsImg = view.findViewById(R.id.viewLabsImg);
        viewLabsImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewLabsList viewLabsList = new ViewLabsList();
                replaceFragment(viewLabsList,"fragmentB");
            }
        });

        calculateBmiImg = view.findViewById(R.id.calculateBmiImg);
        calculateBmiImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalculateBmi calculateBmi = new CalculateBmi();
                //getParentFragmentManager().beginTransaction().replace(R.id.container, factsPage).commit();
                replaceFragment(calculateBmi,"fragmentB");
            }
        });

        return view;
    }

    private void initializeTimeCounter(long duration) {
        new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long l) {
                String sDur = String.format(Locale.ENGLISH,"%d : %02d : %02d"
                        , TimeUnit.MILLISECONDS.toHours(l)
                        , TimeUnit.MILLISECONDS.toMinutes(l)
                        , TimeUnit.MILLISECONDS.toSeconds(l) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l)));

                medicineOneTimer.setText(sDur);
            }

            @Override
            public void onFinish() {
                // THIS IS THE EXAMPLE OF RECURRENCE RELATION OR RECURRENCE FUNCTION which means
                // Function is calling itself again and again and it will work infinity times or when the user dies
                initializeTimeCounter(duration);
            }
        }.start();
    }

    public void replaceFragment(Fragment fragment, String tag) {
        //Get current fragment placed in container
        Fragment currentFragment = getParentFragmentManager().findFragmentById(R.id.container);
        //Prevent adding same fragment on top
        if (currentFragment.getClass() == fragment.getClass()) {
            return;
        }
        //If fragment is already on stack, we can pop back stack to prevent stack infinite growth
        if (getParentFragmentManager().findFragmentByTag(tag) != null) {
            getParentFragmentManager().popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        //Otherwise, just replace fragment
        getParentFragmentManager().beginTransaction().addToBackStack(tag).replace(R.id.container, fragment, tag).commit();
    }

}