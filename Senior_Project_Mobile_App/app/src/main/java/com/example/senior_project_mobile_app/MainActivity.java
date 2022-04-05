package com.example.senior_project_mobile_app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    public static String currentScreen="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        replaceFragment(new Fragment_Blutooth_Not_Connected_Screen(this));
    }

    public void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(fragment instanceof Fragment_Login_Screen || fragment instanceof Fragment_Login_Screen_Light)
            fragmentTransaction.setCustomAnimations(R.anim.anim_to_down,R.anim.fade_out,
                    R.anim.anim_to_right,R.anim.fade_out );
        if(fragment instanceof Fragment_Main_Screen)
            fragmentTransaction.setCustomAnimations(R.anim.anim_to_left,R.anim.fade_out,
                    R.anim.anim_to_left,R.anim.fade_out);

        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.addToBackStack("back to connect with bluetooth screen");
        fragmentTransaction.commit();
    }
}