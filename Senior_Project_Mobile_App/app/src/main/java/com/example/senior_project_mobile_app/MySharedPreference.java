package com.example.senior_project_mobile_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class MySharedPreference {
    private static final String MY_PREF="com.example.senior_project_mobile_app";
    //private static final String Value;
    public static void saveHeight(Context context,int height)
    {
        SharedPreferences preferences = context.getSharedPreferences(MY_PREF,context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putInt("Height",height);
        editor.apply();
    }
    public static int RetrieveHeight(Context context)
    {
        SharedPreferences preferences = context.getSharedPreferences(MY_PREF,context.MODE_PRIVATE);
        int height =preferences.getInt("Height",0);
        return height;
    }
    public static void saveMaxLiftWeight(Context context,int maxLift)
    {
        SharedPreferences preferences = context.getSharedPreferences(MY_PREF,context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putInt("MaxLift",maxLift);
        editor.apply();
    }
    public static int RetrieveMaxLiftWeight(Context context)
    {
        SharedPreferences preferences = context.getSharedPreferences(MY_PREF,context.MODE_PRIVATE);
        int weight= preferences.getInt("MaxLift",0);
        return weight;
    }
    public static void saveDeviceName(Context context,String deviceName)
    {
        SharedPreferences preferences = context.getSharedPreferences(MY_PREF,context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("DeviceName",deviceName);
        editor.apply();
    }
    public static String RetrieveDeviceName(Context context)
    {
        SharedPreferences preferences = context.getSharedPreferences(MY_PREF,context.MODE_PRIVATE);
        String deviceName=preferences.getString("DeviceName","No Entered Name");
        return deviceName;
    }
    public static void saveSpeedUnit(Context context,String unit)
    {
        SharedPreferences preferences =context.getSharedPreferences(MY_PREF,context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("SpeedUnit",unit);
        editor.apply();
    }
    public static String RetrieveSpeedUnit(Context context)
    {
        SharedPreferences preferences = context.getSharedPreferences(MY_PREF,context.MODE_PRIVATE);
        String unti = preferences.getString("SpeedUnit","Km/h");
        return unti;
    }
    public static void saveWeightUnit(Context context,String unit)
    {
        SharedPreferences preferences= context.getSharedPreferences(MY_PREF, context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("WeightUnit",unit);
        editor.apply();
    }
    public static String RetrieveWeightUnit(Context context)
    {
        SharedPreferences preferences = context.getSharedPreferences(MY_PREF,context.MODE_PRIVATE);
        String unit = preferences.getString("WeightUnit","Kg");
        return unit;
    }
    public static void saveTemperatureUnit(Context context, String unit)
    {
        SharedPreferences preferences=context.getSharedPreferences(MY_PREF,context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("TemperatureUnit",unit);
        editor.apply();
    }
    public static String RetrieveTemperatureUnit(Context context)
    {
        SharedPreferences preferences = context.getSharedPreferences(MY_PREF,context.MODE_PRIVATE);
        String unit = preferences.getString("TemperatureUnit","°C");
        return unit;
    }

}
