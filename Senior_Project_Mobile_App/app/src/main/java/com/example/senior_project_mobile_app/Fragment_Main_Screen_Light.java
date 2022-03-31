package com.example.senior_project_mobile_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;

import androidx.fragment.app.Fragment;

public class Fragment_Main_Screen_Light extends Fragment {
    MainActivity myActivity;

    public Fragment_Main_Screen_Light(MainActivity m) {
        myActivity = m;
    }

    View v;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        v = inflater.inflate(R.layout.main_screen_light, container, false);
        ImageView imageView = v.findViewById(R.id.settings_image_light_id);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myActivity.replaceFragment(new Fragment_Settings_Screen_Light(myActivity));
            }
        });
        Switch aSwitch = v.findViewById(R.id.switch_in_main_screen_light);
        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myActivity.replaceFragment(new Fragment_Main_Screen(myActivity));
            }
        });
        LinearLayout linearLayout_weight = v.findViewById(R.id.linear_layout_weight_history_id_light);
        linearLayout_weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myActivity.replaceFragment(new Fragment_Weight_History_Screen_Light(myActivity));
            }
        });
        LinearLayout linearLayout_Tempreture_light = v.findViewById(R.id.linear_layout_light_Tempreture_history_id);
        linearLayout_Tempreture_light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myActivity.replaceFragment(new Fragment_Tempreture_History_screen_Light(myActivity));
            }
        });
        LinearLayout linearLayout_Humadity_light = v.findViewById(R.id.linear_layout_light_Humadity_history_id);
        linearLayout_Humadity_light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myActivity.replaceFragment(new Fragment_Humadity_Screen_Light(myActivity));
            }
        });
        LinearLayout linearLayout_Speed_light = v.findViewById(R.id.linear_layout_light_Speed_history_id);
        linearLayout_Speed_light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myActivity.replaceFragment(new Fragment_Speed_History_Light(myActivity));
            }
        });
        LinearLayout linearLayout_Carried_Weight_light = v.findViewById(R.id.linear_layout_light_Carried_Weight_history_id);
        linearLayout_Carried_Weight_light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myActivity.replaceFragment(new Fragment_Carried_Weight_History_Light(myActivity));
            }
        });
        LinearLayout linearLayout_Location = v.findViewById(R.id.linear_layout_light_Location_id);
        linearLayout_Location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        LinearLayout linearLayout_BMI = v.findViewById(R.id.linear_layout_light_BMI_id);
        linearLayout_BMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myActivity.replaceFragment(new Fragment_BMI_Screen_Light(myActivity));
            }
        });


        return v;
    }
}
