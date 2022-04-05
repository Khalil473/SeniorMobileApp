package com.example.senior_project_mobile_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

public class Fragment_Bluetooth_Not_Connected_Screen extends Fragment {
  MainActivity myActivity;

  public Fragment_Bluetooth_Not_Connected_Screen(MainActivity m) {
    myActivity = m;
  }

  View v;

  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
    v = inflater.inflate(R.layout.bluetooth_not_connected_black, container, false);
    TextView tv = v.findViewById(R.id.TextView_bluetooth_not_connected_black_id);
    tv.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            // myActivity.replaceFragment(new Fragment_Login_Screen(myActivity));
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
                } catch (InterruptedException e) {
                  Toast.makeText(myActivity, "Failed to start scanning", Toast.LENGTH_SHORT).show();
                }
                break;
            }
          }
        });
    return v;
  }
}
