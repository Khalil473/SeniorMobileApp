package com.example.senior_project_mobile_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class Fragment_Settings_Screen extends Fragment {

  MainActivity myActivity;

  public Fragment_Settings_Screen(MainActivity m) {
    myActivity = m;
  }

  View v,mainScreenView;
  TextView TV_deviceName;
    String oldSpeedUnit,oldWeightUnit,oldTemperatureUnit;
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
    v = inflater.inflate(R.layout.settings_black, container, false);
      ImageView goToMainScreen;
      TextView saveChangesHeight,saveChangesMaxLift,saveChangesDeviceName;
      EditText myHeight,maxLift,deviceName;
      TV_deviceName=v.findViewById(R.id.TextView_Device_Name_in_Settings_Screen_Black);
      deviceName=v.findViewById(R.id.EditText_Device_Name_in_settings_screen);
      TV_deviceName.setText(myActivity.DeviceName);
      deviceName.setText(myActivity.DeviceName);
      myHeight=v.findViewById(R.id.EditText_My_Height_in_settings_screen);
      maxLift=v.findViewById(R.id.EditText_Max_Lift_in_settings_screen);
    goToMainScreen = v.findViewById(R.id.GoBack_to_main_screen_from_black_settings_screen_id);
      goToMainScreen.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            myActivity.replaceFragment(new Fragment_Main_Screen(myActivity));
          }
        });

      saveChangesHeight=v.findViewById(R.id.saveChangesOnHeightFromSettingsScreen);
      saveChangesHeight.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              String temp= myHeight.getText().toString();
              int height = Integer.parseInt(temp);
              MySharedPreference.saveHeight(myActivity,height);
              // myActivity.shoe.onWriteFinished();
          }
      });


      saveChangesMaxLift=v.findViewById(R.id.saveChangesOnMaxLiftWeightFromSettingsScreen);
      saveChangesMaxLift.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              String temp = maxLift.getText().toString();
              int maxlift=Integer.parseInt(temp);
              MySharedPreference.saveMaxLiftWeight(myActivity,maxlift);
              // myActivity.shoe.onWriteFinished();
          }
      });
      saveChangesDeviceName=v.findViewById(R.id.saveChangesOnDeviceNameFromSettingsScreen);
      saveChangesDeviceName.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              String name=deviceName.getText().toString();
              myActivity.DeviceName=name;
              MySharedPreference.saveDeviceName(myActivity,name);
              TV_deviceName.setText(myActivity.DeviceName);
              Toast.makeText(myActivity,myActivity.DeviceName,Toast.LENGTH_SHORT).show();
              //myActivity.shoe.onWriteFinished();
          }
      });

      Spinner SpeedUnitSpinner=v.findViewById(R.id.SpeedUnitSpinner);
      ArrayAdapter SpeedUnitSpinnerAdapter = ArrayAdapter.createFromResource(myActivity,R.array.speed_units,R.layout.spinner_color);
      SpeedUnitSpinnerAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
      SpeedUnitSpinner.setAdapter(SpeedUnitSpinnerAdapter);
      SpeedUnitSpinner.setSelection(SpeedUnitSpinnerAdapter.getPosition(myActivity.SpeedUnit));
      oldSpeedUnit = SpeedUnitSpinner.getSelectedItem().toString();

      SpeedUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              if(!SpeedUnitSpinner.getSelectedItem().toString().equals(oldSpeedUnit))
              {
                  oldSpeedUnit=SpeedUnitSpinner.getSelectedItem().toString();
                  String speedSelectedItem=SpeedUnitSpinner.getSelectedItem().toString();
                  MySharedPreference.saveSpeedUnit(myActivity,speedSelectedItem);
                  myActivity.SpeedUnit=oldSpeedUnit;
                  Toast.makeText(myActivity,speedSelectedItem,Toast.LENGTH_SHORT).show();
              }
          }
          @Override
          public void onNothingSelected(AdapterView<?> parent) {
          }
      });

      Spinner WeightUnitSpinner = v.findViewById(R.id.WeightUnitSpinner);
      ArrayAdapter WeightUnitSpinnerAdapter=ArrayAdapter.createFromResource(myActivity,R.array.weight_units,R.layout.spinner_color);
      WeightUnitSpinnerAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
      WeightUnitSpinner.setAdapter(WeightUnitSpinnerAdapter);
      WeightUnitSpinner.setSelection(WeightUnitSpinnerAdapter.getPosition(myActivity.WeightUnit));
      oldWeightUnit=WeightUnitSpinner.getSelectedItem().toString();

      WeightUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              if(!WeightUnitSpinner.getSelectedItem().toString().equals(oldWeightUnit))
              {
                  oldWeightUnit =WeightUnitSpinner.getSelectedItem().toString();
                  String WeightSelectedItem=WeightUnitSpinner.getSelectedItem().toString();
                  MySharedPreference.saveWeightUnit(myActivity,WeightSelectedItem);
                  myActivity.WeightUnit=oldWeightUnit;
                 Toast.makeText(myActivity,WeightSelectedItem,Toast.LENGTH_SHORT).show();
              }
          }
          @Override
          public void onNothingSelected(AdapterView<?> parent) {

          }
      });



      Spinner TemperatureUnitSpinner =v.findViewById(R.id.TempretureUnitSpinner);
      ArrayAdapter TemperatureUnitSpinnerAdapter=ArrayAdapter.createFromResource(myActivity,R.array.Temerature_units,R.layout.spinner_color);
      TemperatureUnitSpinnerAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
      TemperatureUnitSpinner.setAdapter(TemperatureUnitSpinnerAdapter);
      TemperatureUnitSpinner.setSelection(TemperatureUnitSpinnerAdapter.getPosition(myActivity.TemperatureUnit));
      oldTemperatureUnit=TemperatureUnitSpinner.getSelectedItem().toString();
      TemperatureUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              if(!TemperatureUnitSpinner.getSelectedItem().toString().equals(oldTemperatureUnit))
              {
                  oldTemperatureUnit=TemperatureUnitSpinner.getSelectedItem().toString();
                  String TemperatureSelectedItem=TemperatureUnitSpinner.getSelectedItem().toString();
                  MySharedPreference.saveTemperatureUnit(myActivity,TemperatureSelectedItem);
                  myActivity.TemperatureUnit=oldTemperatureUnit;
                  Toast.makeText(myActivity,TemperatureSelectedItem,Toast.LENGTH_SHORT).show();
              }
          }

          @Override
          public void onNothingSelected(AdapterView<?> parent) {

          }

      });




    TextView disconnect_btn = v.findViewById(R.id.disconnect_button_on_setting_screen_black);
    disconnect_btn.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            myActivity.shoe.disconnectDevice();
            myActivity.replaceFragment(new Fragment_Bluetooth_Not_Connected_Screen(myActivity));
          }
        });
    return v;
  }
}
