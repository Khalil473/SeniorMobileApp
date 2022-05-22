package com.example.senior_project_mobile_app;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class Fragment_Speed_History extends Fragment {
  MainActivity myActivity;
  ProgressBar loading_bar;

  public Fragment_Speed_History(MainActivity m) {

    myActivity = m;
  }

  View v;

  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
    v = inflater.inflate(R.layout.max_speed_history_black, container, false);
    loading_bar=v.findViewById(R.id.loading_bar_in_speed_history_screen);

    ImageView imageView = v.findViewById(R.id.GoBack_to_main_screen_from_black_max_speed_history_screen_id);
    imageView.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            myActivity.replaceFragment(new Fragment_Main_Screen(myActivity));
          }
        });

    TextView daily_max_speed= v.findViewById(R.id.daily_max_speed_history_button);
    daily_max_speed.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        myActivity.shoe.startHistoryReading("ds");
        loading_bar.setVisibility(View.VISIBLE);
        myActivity.shoe.setOnHistoryReadFinished(
                new OnHistoryReadFinishedListener() {
                  @Override
                  public void historyReadFinished() {

                  }
                });

      }
    });



    TextView weakly_max_speed=v.findViewById(R.id.weakly_max_speed_history_button);
    weakly_max_speed.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        myActivity.shoe.startHistoryReading("ws");
        loading_bar.setVisibility(View.VISIBLE);
        myActivity.shoe.setOnHistoryReadFinished(
                new OnHistoryReadFinishedListener() {
                  @Override
                  public void historyReadFinished() {

                  }
                });

      }
    });


    TextView monthly_max_speed=v.findViewById(R.id.monthly_max_speed_history_button);
    monthly_max_speed.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        myActivity.shoe.startHistoryReading("ms");
        loading_bar.setVisibility(View.VISIBLE);
        myActivity.shoe.setOnHistoryReadFinished(
                new OnHistoryReadFinishedListener() {
                  @Override
                  public void historyReadFinished() {

                  }
                });
      }
    });


    TextView yearly_max_speed=v.findViewById(R.id.yearly_max_speed_history_button);
    yearly_max_speed.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        myActivity.shoe.startHistoryReading("ys");
        loading_bar.setVisibility(View.VISIBLE);
        myActivity.shoe.setOnHistoryReadFinished(
                new OnHistoryReadFinishedListener() {
                  @Override
                  public void historyReadFinished() {

                  }
                });

      }
    });



    double x, y;
    GraphView graph = v.findViewById(R.id.graph);
    LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();

    for (int i = 0; i < 100; i++) {
      x = i;
      y = Math.sin(i);
      series.appendData(new DataPoint(x, y), false, 100);
      // color of series
      series.setColor(Color.argb(255, 237, 125, 49));
      // series.setDataPointsRadius(200);
      series.setDrawDataPoints(true);
      series.setDataPointsRadius(10);
    }

    graph.addSeries(series);
    // color of background color
    graph.setBackgroundColor(Color.argb(255, 2, 0, 3));
    // color of Horizontal  numbers
    graph.getGridLabelRenderer().setHorizontalLabelsColor(Color.argb(255, 86, 86, 86));
    // color of Vertical numbers
    graph.getGridLabelRenderer().setVerticalLabelsColor(Color.argb(255, 86, 86, 86));
    // graph.getGridLabelRenderer().setVerticalLabelsVisible(true);
    // color of Horizontal and Vertical lines
    graph.getGridLabelRenderer().setGridColor(Color.argb(255, 209, 208, 209));

    graph.getViewport().setScalable(true); // activate horizontal zooming and scrolling
    graph.getViewport().setScrollable(true); // activate horizontal scrolling
    graph
        .getViewport()
        .setScalableY(true); // activate horizontal and vertical zooming and scrolling
    graph.getViewport().setScrollableY(true); // activate vertical scrolling
    return v;
  }
}
