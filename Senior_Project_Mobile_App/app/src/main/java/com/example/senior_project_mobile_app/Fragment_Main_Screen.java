package com.example.senior_project_mobile_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class Fragment_Main_Screen extends Fragment {
  MainActivity myActivity;

  public Fragment_Main_Screen(MainActivity m) {
    myActivity = m;
  }

  View v;
  TextView temp, weight, humadity, speed, carred_weight,deviceName;
  ProgressBar loading_bar;

  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
    v = inflater.inflate(R.layout.main_screen_black, container, false);
    myActivity.shoe.startDataNotify();
    temp = v.findViewById(R.id.temp_real_time);
    weight = v.findViewById(R.id.weight_real_time);
    humadity = v.findViewById(R.id.humadity_real_time);
    speed = v.findViewById(R.id.speed_real_time);
    carred_weight = v.findViewById(R.id.carred_weight_real_time);
    loading_bar = v.findViewById(R.id.loading_bar);
    deviceName=v.findViewById(R.id.DeviceNameInMainScreen);
    deviceName.setText(myActivity.DeviceName);
    String SpeedUnit=MySharedPreference.RetrieveSpeedUnit(myActivity);
    String WeightUnit =MySharedPreference.RetrieveWeightUnit(myActivity);
    String TemperatureUnit=MySharedPreference.RetrieveTemperatureUnit(myActivity);


    myActivity.shoe.setOnDataReceivedListener(
        (data) -> {
          if (data.startsWith("w")) {

            weight.setText(data.substring(1));
          } else if (data.startsWith("t")) {
            temp.setText(data.substring(1) + "");

          } else if (data.startsWith("h")) {
            humadity.setText(data.substring(1) + "");
          } else if (data.startsWith("s")) {
            speed.setText(data.substring(1));
          } else if (data.startsWith("cw")) {
            carred_weight.setText(data.substring(2));
          }
        });
    ImageView imageView = v.findViewById(R.id.settings_image_black_id);
    imageView.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            myActivity.shoe.sendData("disconnect");
            myActivity.replaceFragment(new Fragment_Settings_Screen(myActivity));
          }
        });

    Switch aSwitch = v.findViewById(R.id.switch_in_main_screen_black);
    aSwitch.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            myActivity.replaceFragment(new Fragment_Main_Screen_Light(myActivity));
          }
        });

    LinearLayout linearLayout_weight = v.findViewById(R.id.linear_layout_weight_history_id);
    linearLayout_weight.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            myActivity.shoe.sendData("disconnect");
            myActivity.replaceFragment(new Fragment_Weight_History_Screen(myActivity));
          }
        });

    LinearLayout linearLayout_Tempreture = v.findViewById(R.id.linear_layout_Tempreture_history_id);
    linearLayout_Tempreture.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            myActivity.shoe.sendData("disconnect");
            myActivity.replaceFragment(new Fragment_Tempreture_History_screen(myActivity));
          }
        });

    LinearLayout linearLayout_Humadity = v.findViewById(R.id.linear_layout_Humadity_history_id);
    linearLayout_Humadity.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            myActivity.shoe.sendData("disconnect");
            myActivity.replaceFragment(new Fragment_Humadity_Screen(myActivity));
          }
        });

    LinearLayout linearLayout_Speed = v.findViewById(R.id.linear_layout_Speed_history_id);
    linearLayout_Speed.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            myActivity.shoe.sendData("disconnect");
            myActivity.replaceFragment(new Fragment_Speed_History(myActivity));
          }
        });

    LinearLayout linearLayout_Carried_Weight =
        v.findViewById(R.id.linear_layout_Carried_Weight_history_id);
    linearLayout_Carried_Weight.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            myActivity.shoe.sendData("disconnect");
            myActivity.replaceFragment(new Fragment_Carried_Weight_History(myActivity));
          }
        });

    LinearLayout linearLayout_Location = v.findViewById(R.id.linear_layout_Location_id);
    linearLayout_Location.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            myActivity.shoe.sendData("disconnect");
          }
        });
    LinearLayout linearLayout_BMI = v.findViewById(R.id.linear_layout_BMI_id);
    linearLayout_BMI.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            myActivity.shoe.sendData("disconnect");
            myActivity.replaceFragment(new Fragment_BMI_Screen(myActivity));
          }
        });
    return v;
  }
}
