package com.example.senior_project_mobile_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

  View v;

  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
    v = inflater.inflate(R.layout.settings_black, container, false);
      ImageView goToMainScreen;
      TextView saveChangesHeight,saveChangesMaxLift,saveChangesDeviceName;

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
              // myActivity.shoe.onWriteFinished();
          }
      });


      saveChangesMaxLift=v.findViewById(R.id.saveChangesOnMaxLiftWeightFromSettingsScreen);
      saveChangesMaxLift.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              // myActivity.shoe.onWriteFinished();
          }
      });
      saveChangesDeviceName=v.findViewById(R.id.saveChangesOnDeviceNameFromSettingsScreen);
      saveChangesDeviceName.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              //myActivity.shoe.onWriteFinished();
          }
      });

      Spinner SpeedUnitSpinner=v.findViewById(R.id.SpeedUnitSpinner);
      ArrayAdapter SpeedUnitSpinnerAdapter = ArrayAdapter.createFromResource(myActivity,R.array.speed_units,R.layout.spinner_color);
      SpeedUnitSpinnerAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
      SpeedUnitSpinner.setAdapter(SpeedUnitSpinnerAdapter);
      final String[] OldSpeedUnit = {SpeedUnitSpinner.getSelectedItem().toString()};
      SpeedUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              if(!SpeedUnitSpinner.getSelectedItem().toString().equals(OldSpeedUnit[0]))
              {
                  OldSpeedUnit[0] =SpeedUnitSpinner.getSelectedItem().toString();
                  String speedSelectedItem=SpeedUnitSpinner.getSelectedItem().toString();
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
      final String[] OldWeightUnit = {WeightUnitSpinner.getSelectedItem().toString()};
      WeightUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              if(!WeightUnitSpinner.getSelectedItem().toString().equals(OldWeightUnit[0]))
              {
                  OldWeightUnit[0] =WeightUnitSpinner.getSelectedItem().toString();
                  String WeightSelectedItem=WeightUnitSpinner.getSelectedItem().toString();
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
      final String[] OldTemperatureUnit = {TemperatureUnitSpinner.getSelectedItem().toString()};
      TemperatureUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              if(!TemperatureUnitSpinner.getSelectedItem().toString().equals(OldTemperatureUnit[0]))
              {
                  OldTemperatureUnit[0] =TemperatureUnitSpinner.getSelectedItem().toString();
                  String WeightSelectedItem=TemperatureUnitSpinner.getSelectedItem().toString();
                  Toast.makeText(myActivity,WeightSelectedItem,Toast.LENGTH_SHORT).show();
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
