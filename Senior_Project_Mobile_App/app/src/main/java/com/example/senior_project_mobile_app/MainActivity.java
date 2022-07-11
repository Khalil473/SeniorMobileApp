package com.example.senior_project_mobile_app;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
  public String SpeedUnit = "";
  public String WeightUnit = "";
  public String TemperatureUnit = "";
  public String DeviceName = "";
  public Shoe shoe;
  public double weight;
  public int height;
  public static String speedFromSensor = "";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    shoe = new Shoe(this);
    SpeedUnit = MySharedPreference.RetrieveSpeedUnit(this);
    WeightUnit = MySharedPreference.RetrieveWeightUnit(this);
    TemperatureUnit = MySharedPreference.RetrieveTemperatureUnit(this);
    DeviceName = MySharedPreference.RetrieveDeviceName(this);
    speedFromSensor = MySharedPreference.RetrieveSpeedReceivedFromSensor(this);
    height = MySharedPreference.RetrieveHeight(this);
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

  public static float convertSpeedFrom_MS_to_KmH(float Speed_In_ms) // from meter/second to Km/hour
      {
    float speed_in_KMH;
    speed_in_KMH = Speed_In_ms * 18 / 5;
    return speed_in_KMH;
  }

  public static float convertSpeedFrom_MS_to_MileH(
      float Speed_In_ms) // from meter/second to mile/hour
      {
    float speed_in_MileH;
    speed_in_MileH = (float) (Speed_In_ms * 2.236936);
    return speed_in_MileH;
  }

  public static float convertSpeedFrom_MS_to_ftS(
      float Speed_In_ms) // from meter/second to feet/second
      {
    float speed_in_ftS;
    speed_in_ftS = (float) (Speed_In_ms * 3.28084);
    return speed_in_ftS;
  }

  public static float convertWeightFrom_G_To_Kg(float weight_In_G) {
    float weight_In_Kg;
    weight_In_Kg = weight_In_G / 1000;
    return weight_In_Kg;
  }

  public static float convertWeightFrom_G_To_Pound(float weight_In_G) {
    float weight_In_Pound;
    weight_In_Pound = (float) (0.00220462262185 * weight_In_G);
    return weight_In_Pound;
  }

  public static float convertTemperatureFrom_C_To_F(float temperature_In_C) {
    float temperature_In_F;
    temperature_In_F = (float) ((temperature_In_C * 1.8) + 32);
    return temperature_In_F;
  }
}
