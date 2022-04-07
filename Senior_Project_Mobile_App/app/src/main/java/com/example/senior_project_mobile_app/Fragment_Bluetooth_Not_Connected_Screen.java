package com.example.senior_project_mobile_app;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
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
    tv.setOnClickListener(v -> myActivity.replaceFragment(new Fragment_Login_Screen(myActivity)));
     pb = v.findViewById(R.id.loading_bar);
    tv.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            if (myActivity.shoe.getStatus() == Shoe.STATE_CONNECTED) {
              myActivity.shoe.startReading();
              if (myActivity.shoe.isFirstTime())
                myActivity.replaceFragment(new Fragment_Login_Screen(myActivity));
              else myActivity.replaceFragment(new Fragment_Setup_Screen(myActivity));
              myActivity.shoe.stopBluetoothScan();
            }
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
                } else {
                  Toast.makeText(myActivity, "Enabling bluetooth", Toast.LENGTH_SHORT).show();
                }
                break;
              case Shoe.STATE_INITIALIZED:
                pb.setVisibility(View.VISIBLE);
                try {
                  myActivity.shoe.searchNearbyDevices();
                } catch (InterruptedException e) {
                  Toast.makeText(myActivity, "Failed to start scanning", Toast.LENGTH_SHORT).show();
                }
                myActivity.shoe.setOnBluetoothSearchFinished(
                    new OnBluetoothSearchFinishedListener() {
                      @Override
                      public void bluetoothSearchFinishedListener(ArrayList<String> devices) {
                        TextView textView =v.findViewById(R.id.TextView_bluetooth_not_connected_black_id);
                        textView.setText("Refresh");
                        LinearLayout linearLayout = v.findViewById(R.id.Linearlayout_name_of_near_devices);
                        linearLayout.setBackgroundColor(Color.WHITE);

                        ListView listView = new ListView(myActivity);

                        if(devices.isEmpty())
                        {
                          linearLayout.removeAllViews();
                          TextView no_nearby_devices_message = new TextView(myActivity);
                          no_nearby_devices_message.setText("There are no Devises please refresh and search again");
                          no_nearby_devices_message.setTextSize(20);
                          no_nearby_devices_message.setTextColor(Color.WHITE);
                          linearLayout.setBackgroundColor(Color.BLACK);
                          linearLayout.addView(no_nearby_devices_message);
                          return;
                        }
                        ArrayAdapter arrayAdapter = new ArrayAdapter(myActivity,android.R.layout.simple_list_item_1,devices);
                        listView.setAdapter(arrayAdapter);
                        linearLayout.addView(listView);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                          @Override
                          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String item = listView.getAdapter().getItem(position).toString();
                            if (item.equals("our device"))
                            {
                              myActivity.replaceFragment(new Fragment_Login_Screen(myActivity));
                            }
                            else
                              myActivity.replaceFragment(new Fragment_Bluetooth_Not_Connected_Screen(myActivity));
                          }
                        });
                        myActivity.shoe.connectToDevice(devices.get(0));
                        pb.setVisibility(View.INVISIBLE);
                      }
                    });

                myActivity.shoe.setOnBluetoothConnectedListener(() -> {});

                myActivity.shoe.setOnBluetoothDisconnectedListener(
                    () -> myActivity.replaceFragment(Fragment_Bluetooth_Not_Connected_Screen.this));
                break;
            }
          }
        });
    return v;
  }
}
