package com.example.goodhearthealthcare.receptionist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.goodhearthealthcare.R;
import com.example.goodhearthealthcare.fragments.ViewAppliedAppointment;
import com.example.goodhearthealthcare.modal.AppointmentRequest;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ConfirmedLabTest extends AppCompatActivity {

    String userID;
    RecyclerView viewConfirmedLabsTest;
    TextView noConfirmLabTestTxt;
    DatabaseReference labTestRef;
    ProgressDialog loadingBar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmed_lab_test);

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();

        loadingBar = new ProgressDialog(this);

        noConfirmLabTestTxt = findViewById(R.id.noConfirmLabTestTxt);
        viewConfirmedLabsTest = findViewById(R.id.viewConfirmedLabsTest);

        viewConfirmedLabsTest.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        viewConfirmedLabsTest.setLayoutManager(linearLayoutManager);

        labTestRef = FirebaseDatabase.getInstance().getReference().child("LabTestConfirmed");

        startListen();
    }

    @Override
    public void onStart() {
        super.onStart();
        loadingBar.setMessage("please wait");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
        startListen();
    }

    private void startListen() {
        labTestRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    //INCOMPLETE - THINGS YET TO IMPLEMENT

                    Query query = FirebaseDatabase.getInstance().getReference().child("LabTestConfirmed").limitToLast(50);
                    FirebaseRecyclerOptions<AppointmentRequest> options = new FirebaseRecyclerOptions.Builder<AppointmentRequest>().setQuery(query, AppointmentRequest.class).build();
                    FirebaseRecyclerAdapter<AppointmentRequest, ViewAppliedAppointment.viewAppointmentReqViewHolder> adapter = new FirebaseRecyclerAdapter<AppointmentRequest, ViewAppliedAppointment.viewAppointmentReqViewHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull ViewAppliedAppointment.viewAppointmentReqViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull final AppointmentRequest model) {
                            //final String PostKey = getRef(position).getKey();
                            holder.setName(model.getPatientName());
                            holder.setPhone(model.getPatientPhone());
                            holder.setAddress(model.getPatientAddress());
                            holder.setDate(model.getAppDate());
                            holder.setTime(model.getAppTime());
                            loadingBar.dismiss();
                        }
                        @NonNull
                        @Override
                        public ViewAppliedAppointment.viewAppointmentReqViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_appointment_req_patient, parent, false);
                            return new ViewAppliedAppointment.viewAppointmentReqViewHolder(view);
                        }
                    };
                    viewAppointmentRequest.setAdapter(adapter);
                    adapter.startListening();
                } else {
                    viewAppointmentRequest.setVisibility(View.GONE);
                    noAppointmentAppliedTxt.setVisibility(View.VISIBLE);
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}