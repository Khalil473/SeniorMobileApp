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

public class Fragment_Tempreture_History_screen extends Fragment {

  MainActivity myActivity;
  ProgressBar loading_bar;

  public Fragment_Tempreture_History_screen(MainActivity m) {
    myActivity = m;
  }

  View v;
    GraphView graph ;
    LineGraphSeries<DataPoint> series ;

  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
    v = inflater.inflate(R.layout.temperature_history_black, container, false);
    loading_bar=v.findViewById(R.id.loading_bar_in_temperature_history_screen);
      TextView daily_tempreture= v.findViewById(R.id.daily_temperature_history_button);
      TextView weakly_tempreture=v.findViewById(R.id.weakly_temperature_history_button);
      TextView monthly_tempreture=v.findViewById(R.id.monthly_temperature_history_button);
      TextView yearly_tempreture=v.findViewById(R.id.yearly_temperature_history_button);

    ImageView imageView = v.findViewById(R.id.GoBack_to_main_screen_from_black_tempreture_history_screen_id);
    imageView.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            myActivity.replaceFragment(new Fragment_Main_Screen(myActivity));
          }
        });


    daily_tempreture.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

          daily_tempreture.setBackgroundResource(R.drawable.purple_background_for_buttons_in_blacked_history_screens);
          weakly_tempreture.setBackgroundResource(R.drawable.gray_background_for_buttons_in_blacked_history_screens);
          monthly_tempreture.setBackgroundResource(R.drawable.gray_background_for_buttons_in_blacked_history_screens);
          yearly_tempreture.setBackgroundResource(R.drawable.gray_background_for_buttons_in_blacked_history_screens);

        myActivity.shoe.startHistoryReading("dt");
        loading_bar.setVisibility(View.VISIBLE);
        myActivity.shoe.setOnHistoryReadFinished(
                new OnHistoryReadFinishedListener() {
                  @Override
                  public void historyReadFinished() {

                  }
                });

      }
    });




    weakly_tempreture.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

          daily_tempreture.setBackgroundResource(R.drawable.gray_background_for_buttons_in_blacked_history_screens);
          weakly_tempreture.setBackgroundResource(R.drawable.purple_background_for_buttons_in_blacked_history_screens);
          monthly_tempreture.setBackgroundResource(R.drawable.gray_background_for_buttons_in_blacked_history_screens);
          yearly_tempreture.setBackgroundResource(R.drawable.gray_background_for_buttons_in_blacked_history_screens);

        myActivity.shoe.startHistoryReading("wt");
        myActivity.shoe.setOnHistoryReadFinished(
                new OnHistoryReadFinishedListener() {
                  @Override
                  public void historyReadFinished() {
                      graph.removeAllSeries();
                      LineGraphSeries<DataPoint> series1 = new LineGraphSeries<DataPoint>();
                      for (int i = 0; i < myActivity.shoe.historyData.size(); i++) {
                          series1.appendData(new DataPoint(i, myActivity.shoe.historyData.get(i)), false, 100);
                          // color of series
                          series1.setColor(Color.argb(255, 237, 125, 49));
                          // series.setDataPointsRadius(200);
                          series1.setDrawDataPoints(true);
                          series1.setDataPointsRadius(10);

                      }
                      graph.addSeries(series);
                      loading_bar.setVisibility(View.INVISIBLE);

                  }
                });

      }
    });



    monthly_tempreture.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

          daily_tempreture.setBackgroundResource(R.drawable.gray_background_for_buttons_in_blacked_history_screens);
          weakly_tempreture.setBackgroundResource(R.drawable.gray_background_for_buttons_in_blacked_history_screens);
          monthly_tempreture.setBackgroundResource(R.drawable.purple_background_for_buttons_in_blacked_history_screens);
          yearly_tempreture.setBackgroundResource(R.drawable.gray_background_for_buttons_in_blacked_history_screens);
        myActivity.shoe.startHistoryReading("mt");
        myActivity.shoe.setOnHistoryReadFinished(
                new OnHistoryReadFinishedListener() {
                  @Override
                  public void historyReadFinished() {
                      graph.removeAllSeries();
                      LineGraphSeries<DataPoint> series1 = new LineGraphSeries<DataPoint>();
                      for (int i = 0; i < myActivity.shoe.historyData.size(); i++) {
                          series1.appendData(new DataPoint(i, myActivity.shoe.historyData.get(i)), false, 100);
                          // color of series
                          series1.setColor(Color.argb(255, 237, 125, 49));
                          // series.setDataPointsRadius(200);
                          series1.setDrawDataPoints(true);
                          series1.setDataPointsRadius(10);

                      }
                      graph.addSeries(series);
                      loading_bar.setVisibility(View.INVISIBLE);

                  }
                });

      }
    });



    yearly_tempreture.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          daily_tempreture.setBackgroundResource(R.drawable.gray_background_for_buttons_in_blacked_history_screens);
          weakly_tempreture.setBackgroundResource(R.drawable.gray_background_for_buttons_in_blacked_history_screens);
          monthly_tempreture.setBackgroundResource(R.drawable.gray_background_for_buttons_in_blacked_history_screens);
          yearly_tempreture.setBackgroundResource(R.drawable.purple_background_for_buttons_in_blacked_history_screens);
        myActivity.shoe.startHistoryReading("yt");

        myActivity.shoe.setOnHistoryReadFinished(
                new OnHistoryReadFinishedListener() {
                  @Override
                  public void historyReadFinished() {
                      graph.removeAllSeries();
                      LineGraphSeries<DataPoint> series1 = new LineGraphSeries<DataPoint>();
                      for (int i = 0; i < myActivity.shoe.historyData.size(); i++) {
                          series1.appendData(new DataPoint(i, myActivity.shoe.historyData.get(i)), false, 100);
                          // color of series
                          series1.setColor(Color.argb(255, 237, 125, 49));
                          // series.setDataPointsRadius(200);
                          series1.setDrawDataPoints(true);
                          series1.setDataPointsRadius(10);

                      }
                      graph.addSeries(series);
                      loading_bar.setVisibility(View.INVISIBLE);
                  }
                });
      }
    });

    graph = v.findViewById(R.id.graph);
    series = new LineGraphSeries<DataPoint>();

    for (int i = 0; i < myActivity.shoe.historyData.size(); i++) {
      series.appendData(new DataPoint(i, myActivity.shoe.historyData.get(i)), false, 100);
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
