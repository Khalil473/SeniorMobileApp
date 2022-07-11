package com.example.senior_project_mobile_app;

import android.os.Bundle;
import android.util.DisplayMetrics;
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
  TextView deviceName;
  ImageView pointer;

  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
    v = inflater.inflate(R.layout.bmi_blacked, container, false);
    deviceName = v.findViewById(R.id.TextView_Device_Name_in_BMI_Screen_Black);
    deviceName.setText(myActivity.DeviceName);
    pointer = v.findViewById(R.id.pointer);
    pointer.setX(getXPos());
    ImageView imageView = v.findViewById(R.id.GoBack_to_main_screen_from_black_BMI_screen_id);
    imageView.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            myActivity.replaceFragment(new Fragment_Main_Screen(myActivity));
          }
        });
    return v;
  }
  /*

  OldRange = (OldMax - OldMin)
  NewRange = (NewMax - NewMin)
  NewValue = (((OldValue - OldMin) * NewRange) / OldRange) + NewMin
   */
  int getScreenWidth() {
    DisplayMetrics displayMetrics = new DisplayMetrics();
    myActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    return displayMetrics.widthPixels / 2;
  }

  float getXPos() {
    double BMI = myActivity.weight / (myActivity.height * myActivity.height / 10000.0);
    int oldRange = 30;
    int newRange = getScreenWidth() * 2;
    return (float) (((BMI - 10) * newRange) / oldRange) + (-1 * getScreenWidth());
  }
}
