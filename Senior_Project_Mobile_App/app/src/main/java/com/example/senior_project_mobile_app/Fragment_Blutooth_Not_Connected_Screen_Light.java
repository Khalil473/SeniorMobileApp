package com.example.senior_project_mobile_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class Fragment_Blutooth_Not_Connected_Screen_Light extends Fragment {
  MainActivity myActivity;

  public Fragment_Blutooth_Not_Connected_Screen_Light(MainActivity m) {
    myActivity = m;
  }

  View v;

  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
    v = inflater.inflate(R.layout.bluetooth_not_connected_light, container, false);
    TextView tv = v.findViewById(R.id.TextView_bluetooth_not_connected_light_id);
    tv.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            myActivity.replaceFragment(new Fragment_Login_Screen_Light(myActivity));
          }
        });
    return v;
  }
}
