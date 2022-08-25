package com.example.goodhearthealthcare.fragments;

import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.goodhearthealthcare.R;
import com.example.goodhearthealthcare.receptionist.AddStaffActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.TimeZone;

public class ViewLabProfile extends Fragment {

    String labId,labName, labEmail, labPhone, labAddress, labFrom, labTo, currUserID;
    TextView labNameTxt, labPhoneTxt, labEmailTxt, labAddressTxt, labFromTxt, labToTxt;
    TextView bookLabTxt, knowTheTestsCost, selectDateText, selectTimeText;
    LinearLayout labBookLayout;
    TextInputLayout patientNameLay, patientPhoneLay, patientAddressLay, bookingLabForLay;
    EditText selectAppointmentDate, selectAppointmentTime;
    FirebaseAuth mAuth;
    DatabaseReference patientRef;
    Spinner bookingLabForSpinner;

    int fromHour, fromMinute;
    long today;
    DateTimeFormatter dtf;
    LocalDateTime now;
    String saveCurrentDate, saveCurrentTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_lab_profile, container, false);

        mAuth = FirebaseAuth.getInstance();
        currUserID = mAuth.getCurrentUser().getUid();
        patientRef = FirebaseDatabase.getInstance().getReference().child("Patients").child(currUserID);

        Calendar callForDate = Calendar.getInstance();
        final SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yyyy");
        saveCurrentDate = currentDate.format(callForDate.getTime());

        Calendar callForTime = Calendar.getInstance();
        final SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss");
        saveCurrentTime = currentTime.format(callForTime.getTime());

        selectDateText = view.findViewById(R.id.selectDateText);
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.clear();

        today = MaterialDatePicker.todayInUtcMilliseconds();
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select Preferred Date");
        builder.setSelection(today);
        final MaterialDatePicker materialDatePicker = builder.build();
        selectDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getChildFragmentManager(), "DATE_PICKER");
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                selectAppointmentDate.setText(materialDatePicker.getHeaderText());
            }
        });

        selectTimeText = view.findViewById(R.id.selectTimeText);
        selectTimeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                fromHour = hourOfDay;
                                fromMinute = minute;
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(0, 0, 0, fromHour, fromMinute);
                                //selectFromTime.setText(DateFormat.format("hh:mm aa"));
                                android.text.format.DateFormat df = new android.text.format.DateFormat();
                                selectAppointmentTime.setText(df.format("hh:mm:ss aa", calendar));
                            }
                        }, 12, 0, false
                );
                timePickerDialog.updateTime(fromHour, fromMinute);
                timePickerDialog.show();
            }
        });

        patientNameLay = view.findViewById(R.id.patientNameLay);
        patientPhoneLay = view.findViewById(R.id.patientPhoneLay);
        patientAddressLay = view.findViewById(R.id.patientAddressLay);
        bookingLabForLay = view.findViewById(R.id.bookingLabForLay);

        bookingLabForSpinner = view.findViewById(R.id.bookingLabForSpinner);

        selectAppointmentDate = view.findViewById(R.id.selectAppointmentDate);
        selectAppointmentTime = view.findViewById(R.id.selectAppointmentTime);

        labBookLayout = view.findViewById(R.id.labBookLayout);

        patientRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String pt_fName = snapshot.child("fName").getValue().toString();
                    String pt_lName = snapshot.child("lName").getValue().toString();
                    String pt_phone = snapshot.child("Phone").getValue().toString();
                    String pt_address = snapshot.child("Address").getValue().toString();
                    String name = pt_fName + " " + pt_lName;
                    patientNameLay.getEditText().setText(name);
                    patientPhoneLay.getEditText().setText(pt_phone);
                    patientAddressLay.getEditText().setText(pt_address);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        //SPINNER VALUE
        ArrayAdapter<CharSequence> adapter4LabTest = ArrayAdapter.createFromResource(getActivity(), R.array.labTests, android.R.layout.simple_spinner_item);
        adapter4LabTest.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bookingLabForSpinner.setAdapter(adapter4LabTest);
        bookingLabForSpinner.setOnItemSelectedListener(new LabTestSpinner());

        labId = this.getArguments().getString("labID");
        labName = this.getArguments().getString("labName");
        labEmail = this.getArguments().getString("labEmail");
        labPhone = this.getArguments().getString("labPhone");
        labAddress = this.getArguments().getString("labAdd");
        labFrom = this.getArguments().getString("labFrom");
        labTo = this.getArguments().getString("labTo");

        labNameTxt = view.findViewById(R.id.labNameTxt);
        labPhoneTxt = view.findViewById(R.id.labPhoneTxt);
        labEmailTxt = view.findViewById(R.id.labEmailTxt);
        labAddressTxt = view.findViewById(R.id.labAddressTxt);
        labToTxt = view.findViewById(R.id.labToTxt);
        labFromTxt = view.findViewById(R.id.labFromTxt);

        labNameTxt.setText("Name: "+labName);
        labPhoneTxt.setText("Phone: "+labPhone);
        labEmailTxt.setText("E-mail: "+labEmail);
        labAddressTxt.setText("Address: "+labAddress);
        labToTxt.setText("To: "+labTo);
        labFromTxt.setText("From: "+labFrom);

        knowTheTestsCost = view.findViewById(R.id.knowTheTestsCost);
        knowTheTestsCost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPricesBottomSheet();
            }
        });

        bookLabTxt = view.findViewById(R.id.bookLabTxt);
        bookLabTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                labBookLayout.setVisibility(View.VISIBLE);
            }
        });
        return view;
    }

    private void openPricesBottomSheet() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_table);
        bottomSheetDialog.setCanceledOnTouchOutside(false);
        bottomSheetDialog.show();
    }

    private class LabTestSpinner implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
            String itemSpinner = adapterView.getItemAtPosition(position).toString();
            bookingLabForLay.getEditText().setText(itemSpinner);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }
}