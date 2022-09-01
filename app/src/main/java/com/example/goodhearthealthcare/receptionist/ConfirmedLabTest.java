package com.example.goodhearthealthcare.receptionist;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goodhearthealthcare.R;
import com.example.goodhearthealthcare.modal.LabTest;
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
        //userID = mAuth.getCurrentUser().getUid();

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
                    Query query = FirebaseDatabase.getInstance().getReference().child("LabTestConfirmed").limitToLast(50);
                    FirebaseRecyclerOptions<LabTest> options = new FirebaseRecyclerOptions.Builder<LabTest>().setQuery(query, LabTest.class).build();
                    FirebaseRecyclerAdapter<LabTest, viewConfirmedLabsTestViewHolder> adapter = new FirebaseRecyclerAdapter<LabTest, viewConfirmedLabsTestViewHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull viewConfirmedLabsTestViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull final LabTest model) {
                            //final String PostKey = getRef(position).getKey();
                            holder.setName(model.getPatientName());
                            holder.setPhone(model.getPatientPhone());
                            holder.setAddress(model.getPatientAddress());
                            holder.setDate(model.getTestDate());
                            holder.setTime(model.getTestTime());
                            holder.setTestName(model.getTestName());
                            holder.setTestStatus(model.getTestStatus());
                            loadingBar.dismiss();

                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    CharSequence options[] = new CharSequence[]
                                            {
                                                    "Test Booked",
                                                    "In Progress",
                                                    "Reports Done"
                                            };
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmedLabTest.this);
                                    builder.setTitle("What is the status of the Test?");
                                    builder.setItems(options, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int i) {
                                            if (i == 0) {
                                                //String uid = getRef(i).getKey();
                                                //AddToDatabase(uid);
                                                //RemoveOrderThroughId(uid);
                                                Toast.makeText(ConfirmedLabTest.this, "Test Booked", Toast.LENGTH_SHORT).show();
                                            } else if (i == 1) {
                                                //String uid = getRef(i).getKey();
                                                //AddToDatabase(uid);
                                                //RemoveOrderThroughId(uid);
                                                Toast.makeText(ConfirmedLabTest.this, "In Progress", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(ConfirmedLabTest.this, "Reports Done", Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                        }
                                    });
                                    builder.show();
                                }
                            });
                        }

                        @NonNull
                        @Override
                        public viewConfirmedLabsTestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_lab_test_confirm, parent, false);
                            return new viewConfirmedLabsTestViewHolder(view);
                        }
                    };
                    viewConfirmedLabsTest.setAdapter(adapter);
                    adapter.startListening();
                } else {
                    viewConfirmedLabsTest.setVisibility(View.GONE);
                    noConfirmLabTestTxt.setVisibility(View.VISIBLE);
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public static class viewConfirmedLabsTestViewHolder extends RecyclerView.ViewHolder {
        public viewConfirmedLabsTestViewHolder(@NonNull View itemView) {
            super(itemView);
            //mView = itemView;
        }

        public void setName(String fname) {
            TextView firstname = (TextView) itemView.findViewById(R.id.patientName);
            firstname.setText("Name: " + fname);
        }

        public void setPhone(String phone) {
            TextView phoneText = (TextView) itemView.findViewById(R.id.patientPhoneApt);
            phoneText.setText("Phone: " + phone);
        }

        public void setAddress(String address) {
            TextView setAddress = (TextView) itemView.findViewById(R.id.patientAddress);
            setAddress.setText("Address: " + address);
        }

        public void setDate(String date) {
            TextView dateTxt = (TextView) itemView.findViewById(R.id.appointmentDate);
            dateTxt.setText("Date: " + date);
        }

        public void setTime(String time) {
            TextView timeTxt = (TextView) itemView.findViewById(R.id.appointmentTime);
            timeTxt.setText("Time: " + time);
        }

        public void setTestName(String tName) {
            TextView tNameTxt = (TextView) itemView.findViewById(R.id.labTestName);
            tNameTxt.setText("Test Name: " + tName);
        }

        public void setTestStatus(String status) {
            TextView tStatus = itemView.findViewById(R.id.labTestStatus);
            tStatus.setText(status);
        }
        /*public void setImagee(Context ctx, String image)
        {
            CircleImageView donorimage = (CircleImageView) mView.findViewById(R.id.donor_profile_image);
            Picasso.with(ctx).load(image).into(donorimage);
        }*/
    }
}