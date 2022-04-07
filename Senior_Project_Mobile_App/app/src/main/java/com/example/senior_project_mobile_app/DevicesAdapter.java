package com.example.senior_project_mobile_app;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class DevicesAdapter extends ArrayAdapter<String> {

  private final Activity context;
  private final ArrayList<String> device;

  public DevicesAdapter(Activity context, ArrayList<String> device) {
    super(context, R.layout.devices_list, device);
    this.context = context;
    this.device = device;
  }

  public View getView(int position, View view, ViewGroup parent) {
    LayoutInflater inflater = context.getLayoutInflater();
    View rowView = inflater.inflate(R.layout.devices_list, null, true);

    TextView deviceName = (TextView) rowView.findViewById(R.id.device);

    deviceName.setText(device.get(position));

    return rowView;
  }
  ;
}
