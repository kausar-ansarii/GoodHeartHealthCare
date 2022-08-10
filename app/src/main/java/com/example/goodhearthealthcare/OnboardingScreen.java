package com.example.goodhearthealthcare;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;

import com.ramotion.paperonboarding.PaperOnboardingFragment;
import com.ramotion.paperonboarding.PaperOnboardingPage;

import java.util.ArrayList;

public class OnboardingScreen extends AppCompatActivity {
     private  FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_screen);

        fragmentManager = getSupportFragmentManager();
        final PaperOnboardingFragment paperOnboardingFragment = PaperOnboardingFragment.newInstance(getDataforOnboarding());
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.onboardingFrameLayout, paperOnboardingFragment);
        fragmentTransaction.commit();

        //  New Line
    }

    private ArrayList<PaperOnboardingPage> getDataforOnboarding() {

        PaperOnboardingPage nursingcarepage = new PaperOnboardingPage("24 Hours Nursing Care", "Book a Home Visit Now! Get Nursing Care services to help patients recover quickly at home.", Color.parseColor("#ffffff"),R.drawable.nursing, R.drawable.nurse);
        PaperOnboardingPage dentistrypage = new PaperOnboardingPage("Dentistry Services", "Looking to find a Dentist who can provide Dental Care Regularly? You are at the right place. Contact us today!",Color.parseColor("#22eaaa"),R.drawable.dentist, R.drawable.teeth);
        PaperOnboardingPage diabetescarepage = new PaperOnboardingPage("Home Diabetes Care", "Are you looking for a Skilled and Professional nurse for a Diabetic patient? We are here to provide proper care in the comfort of their Home. ", Color.parseColor("#ee5a5a"),R.drawable.diabetes, R.drawable.glucometer);
        PaperOnboardingPage labtestpage = new PaperOnboardingPage("Lab Test At Home", "Book Lab Test and General Health checkup at the tip of your finger and take the steps towards a healthy life. ", Color.parseColor("#ee5a5a"),R.drawable.labtest, R.drawable.blood_test);


        ArrayList<PaperOnboardingPage> elements = new ArrayList<>();

        elements.add(nursingcarepage);
        elements.add(dentistrypage);
        elements.add(diabetescarepage);
        elements.add(labtestpage);
        return elements;
    }
}