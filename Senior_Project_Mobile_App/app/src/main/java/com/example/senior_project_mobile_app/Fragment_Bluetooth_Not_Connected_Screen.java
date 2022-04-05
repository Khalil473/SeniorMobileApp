package com.example.senior_project_mobile_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;

public class Fragment_Bluetooth_Not_Connected_Screen extends Fragment {
  MainActivity myActivity;

  public Fragment_Bluetooth_Not_Connected_Screen(MainActivity m) {
    myActivity = m;
  }

  View v;
  ProgressBar pb;

  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
    v = inflater.inflate(R.layout.bluetooth_not_connected_black, container, false);
    TextView tv = v.findViewById(R.id.TextView_bluetooth_not_connected_black_id);
    pb = v.findViewById(R.id.loading_bar);
    tv.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            // myActivity.replaceFragment(new Fragment_Login_Screen(myActivity));
            if (myActivity.shoe.getStatus() == Shoe.STATE_SCANNING) {
              Toast.makeText(myActivity, "Please wait until the search Finish", Toast.LENGTH_SHORT)
                  .show();
              return;
            } // TODO: disable the search while the bluetooth is searching.
            myActivity.shoe.initializeDevice();
            switch (myActivity.shoe.getStatus()) {
              case Shoe.STATE_BLUETOOTH_NOT_SUPPORTED:
                Toast.makeText(
                        myActivity, "Your Device does not support Bluetooth", Toast.LENGTH_SHORT)
                    .show();
                break;
              case Shoe.STATE_PERMISSION_REQUIRED:
                myActivity.requestPermissions(myActivity.shoe.getRequiredPerms(), 1);
                break;
              case Shoe.STATE_BLUETOOTH_DISABLED:
                if (!myActivity.shoe.enableBluetooth()) {
                  Toast.makeText(
                          myActivity,
                          "Unable to enable bluetooth try to enable it manually",
                          Toast.LENGTH_SHORT)
                      .show();
                }
                break;
              case Shoe.STATE_INITIALIZED:
                try {
                  myActivity.shoe.searchNearbyDevices();
                  myActivity.shoe.setOnBluetoothSearchFinished(
                      new OnBluetoothSearchFinishedListener() {
                        @Override
                        public void bluetoothSearchFinishedListener(ArrayList<String> devices) {
                          Toast.makeText(
                                  myActivity, devices.size() + "" + devices, Toast.LENGTH_SHORT)
                              .show();
                          myActivity.shoe.connectToDevice(devices.get(0));
                          pb.setVisibility(View.INVISIBLE);
                        }
                      });
                  pb.setVisibility(View.VISIBLE);
                } catch (InterruptedException e) {
                  Toast.makeText(myActivity, "Failed to start scanning", Toast.LENGTH_SHORT).show();
                }
                myActivity.shoe.setOnBluetoothConnectedListener(
                    new OnBluetoothConnectedListener() {
                      @Override
                      public void bluetoothConnectedListener() {
                        myActivity.replaceFragment(new Fragment_Login_Screen(myActivity));
                      }
                    });
                myActivity.shoe.setOnBluetoothDisconnectedListener(
                    new OnBluetoothDisconnectedListener() {
                      @Override
                      public void bluetoothDisconnected() {
                        myActivity.replaceFragment(Fragment_Bluetooth_Not_Connected_Screen.this);
                      }
                    });
                break;
            }
          }
        });
    return v;
  }
}
