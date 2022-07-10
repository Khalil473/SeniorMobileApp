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
  public static TextView TV_temp, TV_weight, TV_humadity, TV_speed, TV_carried_weight,deviceName,TV_speedUnit,TV_weightUnit,TV_temperatureUnit,TV_carriedWeightUnit;
  ProgressBar loading_bar;

  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
    v = inflater.inflate(R.layout.main_screen_black, container, false);
    myActivity.shoe.startDataNotify();
      TV_temp = v.findViewById(R.id.temp_real_time);
      TV_weight = v.findViewById(R.id.weight_real_time);
      TV_humadity = v.findViewById(R.id.humadity_real_time);
      TV_speed = v.findViewById(R.id.speed_real_time);
      TV_carried_weight = v.findViewById(R.id.carred_weight_real_time);
    loading_bar = v.findViewById(R.id.loading_bar);
    deviceName=v.findViewById(R.id.DeviceNameInMainScreen);
      TV_speedUnit=v.findViewById(R.id.speed_unit_real_time);
      TV_weightUnit=v.findViewById(R.id.weight_unit_real_time);
      TV_temperatureUnit=v.findViewById(R.id.temp_unit_real_time);
      TV_carriedWeightUnit=v.findViewById(R.id.carred_weight_unit_real_time);
    deviceName.setText(myActivity.DeviceName);
      TV_speedUnit.setText(myActivity.SpeedUnit);
      TV_weightUnit.setText(myActivity.WeightUnit);
      TV_carriedWeightUnit.setText(myActivity.WeightUnit);
      TV_temperatureUnit.setText(myActivity.TemperatureUnit);

    myActivity.shoe.setOnDataReceivedListener(
        (data) -> {
          if (data.startsWith("w")) {
              float weight=0,receivedWeight=Float.parseFloat(data.substring(1));
              if(myActivity.WeightUnit.equals("Kg"))
              {
                  weight=MainActivity.convertWeightFrom_G_To_Kg(receivedWeight);
              }
              else if (myActivity.WeightUnit.equals("lb"))
              {
                  weight=MainActivity.convertWeightFrom_G_To_Pound(receivedWeight);
              }
              TV_weight.setText(weight+"");
          }
          else if (data.startsWith("t")) {

              float temperature=0,receivedTemperature=Float.parseFloat(data.substring(1));
              if(myActivity.TemperatureUnit.equals("°C"))
              {
                  temperature=receivedTemperature;
              }
              else
              {
                  temperature=MainActivity.convertTemperatureFrom_C_To_F(receivedTemperature);
              }
              TV_temp.setText(temperature+"");

          }
          else if (data.startsWith("h")) {

              TV_humadity.setText(data.substring(1) +"");
          }
          else if (data.startsWith("s")) {
              float speed=0,receivedSpeed=Float.parseFloat(data.substring(1));

              if(myActivity.SpeedUnit.equals("Km/h"))
              {
                  speed=MainActivity.convertSpeedFrom_MS_to_KmH(receivedSpeed);
              }
              else if(myActivity.SpeedUnit.equals("m/s"))
              {
                  speed=receivedSpeed;
              }
              else if(myActivity.SpeedUnit.equals("Mile/h"))
              {
                  speed=MainActivity.convertSpeedFrom_MS_to_MileH(receivedSpeed);
              }
              else
              {
                  speed=MainActivity.convertSpeedFrom_MS_to_ftS(receivedSpeed);
              }
                  TV_speed.setText(speed+"");
          }

          else if (data.startsWith("c")) {
              float weight=0,receivedWeight=Float.parseFloat(data.substring(1));
              if(myActivity.WeightUnit.equals("Kg"))
              {
                  weight=MainActivity.convertWeightFrom_G_To_Kg(receivedWeight);
              }
              else if (myActivity.WeightUnit.equals("lb"))
              {
                  weight=MainActivity.convertWeightFrom_G_To_Pound(receivedWeight);
              }
              TV_carried_weight.setText(weight+"");
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
