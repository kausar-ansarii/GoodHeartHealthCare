package com.example.goodhearthealthcare.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.goodhearthealthcare.R;
import com.example.goodhearthealthcare.modal.Staff;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ViewDoctorsList extends Fragment {

    RecyclerView viewDoctorsListForAppointment;
    DatabaseReference doctorsList;
    ProgressDialog loadingBar;
    String patientIDStr, currUserId;
    FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_doctors_list, container, false);

        mAuth = FirebaseAuth.getInstance();

        loadingBar = new ProgressDialog(getActivity());

        viewDoctorsListForAppointment = view.findViewById(R.id.viewDoctorsListForAppointment);
        viewDoctorsListForAppointment.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        viewDoctorsListForAppointment.setLayoutManager(linearLayoutManager);
        doctorsList = FirebaseDatabase.getInstance().getReference();

        currUserId = mAuth.getCurrentUser().getUid();

        startListen();

        return view;
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

        Query query = doctorsList.child("Staffs").child("Doctor").limitToLast(50);
        FirebaseRecyclerOptions<Staff> options = new FirebaseRecyclerOptions.Builder<Staff>().setQuery(query, Staff.class).build();
        FirebaseRecyclerAdapter<Staff, viewDoctorsListViewHolder> adapter = new FirebaseRecyclerAdapter<Staff, viewDoctorsListViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull viewDoctorsListViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull final Staff model) {
                //final String PostKey = getRef(position).getKey();
                holder.setName(model.getStaffName());
                holder.setSpecial(model.getDoctorSpecial());
                holder.setHosName(model.getHospitalName());
                holder.setHospAddress(model.getHospitalAddress());
                //holder.setTeacherName(model.getTeacher());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String hospID = model.getHospitalID();
                        /*Intent intent = new Intent(ViewDoctorsList.this, PatientDoctorsProfile.class);
                        intent.putExtra("hospID",hospID);
                        intent.putExtra("doctorID",model.getStaffPhone());
                        startActivity(intent);*/
                    }
                });
                loadingBar.dismiss();
            }

            @NonNull
            @Override
            public viewDoctorsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_doctors_view, parent, false);
                return new viewDoctorsListViewHolder(view);
            }
        };
        viewDoctorsListForAppointment.setAdapter(adapter);
        adapter.startListening();
    }

    public static class viewDoctorsListViewHolder extends RecyclerView.ViewHolder {
        Button acceptBtn, rejectBtn;
        public viewDoctorsListViewHolder(@NonNull View itemView) {
            super(itemView);
            //mView = itemView;
            /*acceptBtn = itemView.findViewById(R.id.studRqstAcceptBtn);
            rejectBtn = itemView.findViewById(R.id.studRqstRejectBtn);*/
        }

        public void setName(String fname) {
            TextView firstname = (TextView) itemView.findViewById(R.id.doctorName);
            firstname.setText(fname);
        }

        public void setSpecial(String specialist) {
            TextView special = (TextView) itemView.findViewById(R.id.doctorSpecialist);
            special.setText(specialist);
        }

        public void setHosName(String hospName) {
            TextView stdID = (TextView) itemView.findViewById(R.id.doctorHospitalName);
            stdID.setText(hospName);
        }

        public void setHospAddress(String hospAddress) {
            TextView stdID = (TextView) itemView.findViewById(R.id.doctorHospitalAddress);
            stdID.setText(hospAddress);
        }

        /*public void setImagee(Context ctx, String image)
        {
            CircleImageView donorimage = (CircleImageView) mView.findViewById(R.id.donor_profile_image);
            Picasso.with(ctx).load(image).into(donorimage);
        }*/
    }
}