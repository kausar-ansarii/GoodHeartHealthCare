package com.example.goodhearthealthcare.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.goodhearthealthcare.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class ViewLabProfile extends Fragment {

    String labId,labName, labEmail, labPhone, labAddress, labFrom, labTo;
    TextView labNameTxt, labPhoneTxt, labEmailTxt, labAddressTxt, labFromTxt, labToTxt;
    TextView bookLabTxt, knowTheTestsCost;
    LinearLayout labBookLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_lab_profile, container, false);

        labBookLayout = view.findViewById(R.id.labBookLayout);

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
}