package com.example.senior_project_mobile_app;

import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment_Blutooth_Not_Connected_Screen extends Fragment {
    MainActivity myActivity;
    public Fragment_Blutooth_Not_Connected_Screen(MainActivity m){
        myActivity=m;
    }
    View v;

    @Override


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState)
    {

        v= inflater.inflate(R.layout.bluetooth_not_connected_black,container,false);
        TextView tv=v.findViewById(R.id.TextView_bluetooth_not_connected_black_id);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myActivity.replaceFragment(new Fragment_Login_Screen(myActivity));
            }
        });
        return v;

    }
}
