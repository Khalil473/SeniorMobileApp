package com.example.senior_project_mobile_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class Fragment_BMI_Screen extends Fragment {
    MainActivity myActivity;

    public Fragment_BMI_Screen(MainActivity m) {
        myActivity = m;
    }

    View v;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        v = inflater.inflate(R.layout.bmi_blacked, container, false);

        ImageView imageView = v.findViewById(R.id.GoBack_to_main_screen_from_black_BMI_screen_id);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myActivity.replaceFragment(new Fragment_Main_Screen(myActivity));
            }
        });
        return v;
    }
}
