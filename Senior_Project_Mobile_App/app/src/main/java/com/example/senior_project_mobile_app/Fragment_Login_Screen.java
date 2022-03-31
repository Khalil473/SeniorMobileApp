package com.example.senior_project_mobile_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class Fragment_Login_Screen extends Fragment {
    MainActivity myActivity;

    public Fragment_Login_Screen(MainActivity m) {

        myActivity = m;
    }

    View v;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        v = inflater.inflate(R.layout.login_black, container, false);
        TextView textView = v.findViewById(R.id.login_black_id);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myActivity.replaceFragment(new Fragment_Main_Screen(myActivity));
            }
        });
        return v;
    }
}
