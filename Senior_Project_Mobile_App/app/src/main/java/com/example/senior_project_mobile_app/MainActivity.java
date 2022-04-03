package com.example.senior_project_mobile_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static String currentScreen = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        replaceFragment(new Fragment_Blutooth_Not_Connected_Screen(this));
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void onBluetoothDisconnected() {

    }

    public void onBluetoothConnected() {

    }

    public void onDataReceived(String data) {
        if(data.startsWith("h")){

        }
        else if(data.startsWith("w")){

        }
    }

    public void onBluetoothSearchFinished(ArrayList<String> bluetoothDevicesNames) {

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