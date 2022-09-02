package com.example.goodhearthealthcare.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.goodhearthealthcare.MainActivity;
import com.example.goodhearthealthcare.R;
import com.example.goodhearthealthcare.adapter.Medicine;
import com.example.goodhearthealthcare.modal.AddMedicine;
import com.example.goodhearthealthcare.modal.MedicineReminder;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Locale;

public class HomeFragment extends Fragment {

    ImageView calculateBmiImg, viewDoctorImg, viewLabsImg, viewAppliedLabImg;
    RelativeLayout addMedicineBtn, submitMedicineBtn;
    LinearLayout layout_list;
    MaterialCardView submitMedicineListCard;
    //VARIABLES FOR APPOINTMENT
    ImageView viewAppliedAptImg, viewConfirmedAptImg, viewRejectedAptImg;
    TextView medicineOneTimer;
    RecyclerView viewMedicineForReminder;
    ArrayList<AddMedicine> medList = new ArrayList<>();
    ArrayList<MedicineReminder> medicineReminder = new ArrayList<>();
    AlertDialog dialog;

    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mStartTimeInMillis;
    private long mTimeLeftInMillis;
    private long mEndTime;

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
        /*submitMedicineBtn = view.findViewById(R.id.submitMedicineBtn);
        submitMedicineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkIfValidAndRead()){}
            }
        });*/

        viewAppliedLabImg = view.findViewById(R.id.viewAppliedLabImg);
        viewAppliedLabImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppliedLabTest appliedLabTest = new AppliedLabTest();
                replaceFragment(appliedLabTest,"fragmentB");
            }
        });

        buildDialog();
        loadData();

        addMedicineBtn = view.findViewById(R.id.addMedicineBtn);
        addMedicineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //addView();
                //submitMedicineListCard.setVisibility(View.VISIBLE);
                /*AddMedicineReminder medicineReminder = new AddMedicineReminder();
                replaceFragment(medicineReminder,"fragmentB");*/
                dialog.show();
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

    private void buildDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.medicine_add_row,null);
        final TextInputLayout medName = view.findViewById(R.id.medicineNameLay);
        final TextInputLayout medTime = view.findViewById(R.id.medicineTimeLay);
        builder.setView(view);
        builder.setTitle("Enter details:").setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                addCard(medName.getEditText().getText().toString(),medTime.getEditText().getText().toString());
                medName.getEditText().setText("");
                medTime.getEditText().setText("");
            }
        });
        dialog = builder.create();
    }

    private void addCard(String medName, String medTime) {
        View view = getLayoutInflater().inflate(R.layout.view_med_card,null);
        TextView medNameTxt = view.findViewById(R.id.medicineName);
        TextView medTimeTxt = view.findViewById(R.id.medicineTime);
        medNameTxt.setText(medName);
        medTimeTxt.setText(medTime);
        Long hours = Long.valueOf(medTimeTxt.getText().toString());
        layout_list.addView(view);
        startTimerWithParam(Long.valueOf(medTime));
        updateCountDownTextWithParam(medTimeTxt,hours);
        SharedPreferences medData = getActivity().getSharedPreferences("DATA", MODE_PRIVATE);
        SharedPreferences.Editor editor = medData.edit();
        Gson gson = new Gson();
        medicineReminder.add(new MedicineReminder(medName, medTime));
        String json = gson.toJson(medicineReminder);
        editor.putString("med_rem_data",json);
        editor.apply();
        //loadData();
    }

    private void loadData() {
        SharedPreferences medData = getActivity().getSharedPreferences("DATA", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = medData.getString("med_rem_data",null);
        Type type = new TypeToken<ArrayList<MedicineReminder>>(){

        }.getType();
        medicineReminder = gson.fromJson(json,type);

        if (medicineReminder == null){
            medicineReminder = new ArrayList<>();
            medicineReminder.clear();
        } else {
            for (int i = 0; i < medicineReminder.size(); i++){
                View view = getLayoutInflater().inflate(R.layout.view_med_card,null);
                TextView medName = view.findViewById(R.id.medicineName);
                TextView medTime = view.findViewById(R.id.medicineTime);
                medName.setText(medicineReminder.get(i).getName());
                medTime.setText(medicineReminder.get(i).getTime());
                layout_list.addView(view);
            }
        }
    }

    /*private boolean checkIfValidAndRead() {
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
    }*/

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

    private void startTimerWithParam(Long time) {
        mEndTime = System.currentTimeMillis() + time;
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
            }
        }.start();
        mTimerRunning = true;
    }

    private void startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
            }
        }.start();
        mTimerRunning = true;
    }

    private void updateCountDownText() {
        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, seconds);
        //View view = getLayoutInflater().inflate(R.layout.card,null);
        //EditText timeET = view.findViewById(R.id.medicineTime);
        //mTextViewCountDown.setText(timeLeftFormatted);
        //timeET.setText(timeLeftFormatted);
    }

    private void updateCountDownTextWithParam(TextView medTimeTxt, Long hoursParent) {
        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%d:%02d:%02d", hoursParent, minutes, seconds);
        //View view = getLayoutInflater().inflate(R.layout.card,null);
        //EditText timeET = view.findViewById(R.id.medicineTime);
        //mTextViewCountDown.setText(timeLeftFormatted);
        medTimeTxt.setText(timeLeftFormatted);
    }

    @Override
    public void onStop() {
        super.onStop();
        SharedPreferences prefs = getActivity().getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong("startTimeInMillis", mStartTimeInMillis);
        editor.putLong("millisLeft", mTimeLeftInMillis);
        editor.putBoolean("timerRunning", mTimerRunning);
        editor.putLong("endTime", mEndTime);
        editor.apply();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences prefs = getActivity().getSharedPreferences("prefs", MODE_PRIVATE);
        mTimeLeftInMillis = prefs.getLong("startTimeInMillis", 600000);
        mTimeLeftInMillis = prefs.getLong("millisLeft", mStartTimeInMillis);
        mTimerRunning = prefs.getBoolean("timerRunning", false);
        updateCountDownText();
        if (mTimerRunning) {
            mEndTime = prefs.getLong("endTime", 0);
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();
            if (mTimeLeftInMillis < 0) {
                mTimeLeftInMillis = 0;
                mTimerRunning = false;
                updateCountDownText();
            } else {
                startTimer();
            }
        }
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