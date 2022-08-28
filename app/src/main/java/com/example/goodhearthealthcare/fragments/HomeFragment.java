package com.example.goodhearthealthcare.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.goodhearthealthcare.R;
import com.example.goodhearthealthcare.adapter.Medicine;
import com.example.goodhearthealthcare.modal.AddMedicine;
import com.example.goodhearthealthcare.services.BroadcastService;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class HomeFragment extends Fragment {

    ImageView calculateBmiImg, viewDoctorImg, viewLabsImg;
    RelativeLayout addMedicineBtn, submitMedicineBtn;
    LinearLayout layout_list;
    MaterialCardView submitMedicineListCard;

    //VARIABLES FOR APPOINTMENT
    ImageView viewAppliedAptImg, viewConfirmedAptImg, viewRejectedAptImg;

    TextView medicineOneTimer;
    RecyclerView viewMedicineForReminder;

    ArrayList<AddMedicine> medList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        viewMedicineForReminder = view.findViewById(R.id.viewMedicineForReminder);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        viewMedicineForReminder.setLayoutManager(layoutManager);
        viewMedicineForReminder.setAdapter(new Medicine(medList));

        submitMedicineListCard = view.findViewById(R.id.submitMedicineListCard);

        layout_list = view.findViewById(R.id.layout_list);
        submitMedicineBtn = view.findViewById(R.id.submitMedicineBtn);
        submitMedicineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkIfValidAndRead()){}
            }
        });

        addMedicineBtn = view.findViewById(R.id.addMedicineBtn);
        addMedicineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addView();
                submitMedicineListCard.setVisibility(View.VISIBLE);
            }
        });

        //INITIALIZE THE TIME
        //long duration = TimeUnit.HOURS.toMillis(1);

        //initializeTimeCounter(duration);

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

    private boolean checkIfValidAndRead() {
        medList.clear();
        boolean result = true;

        for (int i = 0; i<layout_list.getChildCount(); i++){
            View medicineView = layout_list.getChildAt(i);
            TextInputLayout mName = medicineView.findViewById(R.id.medicineNameLay);
            TextInputLayout mTime = medicineView.findViewById(R.id.medicineTimeLay);

            String mNameStr = mName.getEditText().getText().toString();
            String mTimeStr = mTime.getEditText().getText().toString();

            AddMedicine addMedicine = new AddMedicine();

            if (!mNameStr.isEmpty() || !mTimeStr.isEmpty()){
                addMedicine.setMedName(mNameStr);
                addMedicine.setMedTime(mTimeStr);
            } else {
                result = false;
                break;
            }

            medList.add(addMedicine);
        }

        return result;
    }

    private void addView() {
        View medicineView = getLayoutInflater().inflate(R.layout.medicine_add_row,null,false);

        TextInputLayout mName = medicineView.findViewById(R.id.medicineNameLay);
        TextInputLayout mTime = medicineView.findViewById(R.id.medicineTimeLay);
        ImageView mLayClose = medicineView.findViewById(R.id.clearMedicineLay);
        mLayClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeMediView(medicineView);
            }
        });

        layout_list.addView(medicineView);
    }

    private void removeMediView(View v) {
        layout_list.removeView(v);
    }

    /*private void initializeTimeCounter(long duration) {
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
    }*/

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