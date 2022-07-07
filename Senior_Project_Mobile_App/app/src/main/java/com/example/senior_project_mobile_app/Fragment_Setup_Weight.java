package com.example.senior_project_mobile_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class Fragment_Setup_Weight extends Fragment {
  MainActivity myActivity;

  public Fragment_Setup_Weight(MainActivity m) {

    myActivity = m;
  }

  View v;

  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
    v = inflater.inflate(R.layout.setup_weight, container, false);
    TextView textView = v.findViewById(R.id.confirm_black_id);
    textView.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            myActivity.shoe.sendData("tare");
            myActivity.shoe.setOnWriteFinishedListener(new OnWriteFinishedListener() {
              @Override
              public void writeFinished() {
                myActivity.shoe.setOnWriteFinishedListener(new OnWriteFinishedListener() {
                  @Override
                  public void writeFinished() {

                  }
                });
                myActivity.replaceFragment(new Fragment_Main_Screen(myActivity));
              }
            });

          }
        });
    return v;
  }
}
