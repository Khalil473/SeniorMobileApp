package com.example.senior_project_mobile_app;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
  public String currentScreen = "";
  public Shoe shoe;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    shoe = new Shoe(this);
    replaceFragment(new Fragment_Bluetooth_Not_Connected_Screen(this));
  }

  public void replaceFragment(Fragment fragment) {
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    fragmentTransaction.replace(R.id.frame_layout, fragment);
    fragmentTransaction.addToBackStack(null);
    fragmentTransaction.commit();
  }
}
/*
steps to start reading from the bluetooth device:
1-Define a Shoe object
2-shoe.initialize()
3-check status to see one of these status and take action accordingly:
    3.1-check if stat  us = device doesn't support bluetooth
    3.2-check if status = device needs certain perms if so request them
    3.3-check if status != initialized if so reInit
4-if device is initialized if so call the searchNearbyDevices
5-connect to a device using its name with connectToDevice(string) method
6-call the startReading method
7-you can enable or disable notify (to read the data each time its changed) *optional
 */
