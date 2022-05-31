package com.example.senior_project_mobile_app;

import android.os.Bundle;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
  public static String currentScreen = "";
  public String SpeedUnit="";
  public String WeightUnit="";
  public String TemperatureUnit="";
  public String DeviceName="";
  public Shoe shoe;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    shoe = new Shoe(this);
    SpeedUnit=MySharedPreference.RetrieveSpeedUnit(this);
    WeightUnit =MySharedPreference.RetrieveWeightUnit(this);
    TemperatureUnit=MySharedPreference.RetrieveTemperatureUnit(this);
    DeviceName=MySharedPreference.RetrieveDeviceName(this);
    replaceFragment(new Fragment_Bluetooth_Not_Connected_Screen(this));
  }

  public void replaceFragment(Fragment fragment) {
    if (!(fragment instanceof Fragment_Login_Screen
        || fragment instanceof Fragment_Setup_Screen
        || fragment instanceof Fragment_Main_Screen
        || fragment instanceof Fragment_Bluetooth_Not_Connected_Screen)) {
      // shoe.stopDataNotify();
      shoe.setOnDataReceivedListener(
          new OnDataReceivedListener() {
            @Override
            public void dataReceivedListener(String data) {}
          });
    }
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    fragmentTransaction.setCustomAnimations(
        R.anim.anim_to_right, R.anim.fade_out, R.anim.anim_to_left, R.anim.fade_out);
    fragmentTransaction.replace(R.id.frame_layout, fragment);
    if (!(fragment instanceof Fragment_Login_Screen
        || fragment instanceof Fragment_Setup_Screen
        || fragment instanceof Fragment_Main_Screen
        || fragment instanceof Fragment_Bluetooth_Not_Connected_Screen))
      fragmentTransaction.addToBackStack("back to connect with bluetooth screen");
    fragmentManager.executePendingTransactions();
    fragmentTransaction.commit();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    shoe.sendData("disconnect");
    shoe.setOnWriteFinishedListener(
        new OnWriteFinishedListener() {
          @Override
          public void writeFinished() {
            shoe.disconnectDevice();
          }
        });
  }
}
