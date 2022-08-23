package com.example.goodhearthealthcare.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.goodhearthealthcare.R;

public class HomeFragment extends Fragment {

    ImageView calculateBmiImg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

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