package com.example.senior_project_mobile_app;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
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

public class Fragment_Carried_Weight_History extends Fragment {
  MainActivity myActivity;
  ProgressBar loading_bar;

    private void update_graph_data(String type){
        loading_bar.setVisibility(View.VISIBLE);
        myActivity.shoe.startHistoryReading(type+"c");
        graph.removeAllSeries();
        myActivity.shoe.setOnHistoryReadFinished(
                () -> {
                    LineGraphSeries<DataPoint> series1 = new LineGraphSeries<DataPoint>();
                    for (int i = 0; i < myActivity.shoe.historyData.size(); i++) {
                        series1.appendData(new DataPoint(i, myActivity.shoe.historyData.get(i)), false, 100);
                        // color of series
                        series1.setColor(Color.argb(255, 237, 125, 49));
                        // series.setDataPointsRadius(200);
                        series1.setDrawDataPoints(true);
                        series1.setDataPointsRadius(10);

                    }
                    graph.addSeries(series1);
                    loading_bar.setVisibility(View.INVISIBLE);

                });
    }

  public Fragment_Carried_Weight_History(MainActivity m) {
    myActivity = m;
  }

  View v;
    GraphView graph;
    TextView daily_carried_weight,weakly_carried_weight,monthly_carried_weight,yearly_carried_weight;
    TextView []buttons;
    void changeButtonsColors(Integer index)
    {
        for (int i=0;i<4;i++)
        {
            if(i==index)
                buttons[i].setBackgroundResource(R.drawable.purple_background_for_buttons_in_blacked_history_screens);
            else
                buttons[i].setBackgroundResource(R.drawable.gray_background_for_buttons_in_blacked_history_screens);
        }


    }

  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
      v = inflater.inflate(R.layout.carried_wieght_history_black, container, false);
      graph = v.findViewById(R.id.graph);
        buttons=new TextView[4];
      loading_bar=v.findViewById(R.id.loading_bar_in_carried_weight_history_screen);
      buttons[0]= v.findViewById(R.id.daily_carried_weight_history_button);
      buttons[1]=v.findViewById(R.id.weakly_carried_weight_history_button);

      buttons[2]=v.findViewById(R.id.monthly_carried_weight_history_button);

      buttons[3]=v.findViewById(R.id.yearly_carried_weight_history_button);
      TextView max_carried_weight=v.findViewById(R.id.max_carried_weight_button_in_carried_weight_screen);
      TextView avg_carried_weight=v.findViewById(R.id.avg_carried_weight_button_in_carried_weight_screen);



    ImageView imageView = v.findViewById(R.id.GoBack_to_main_screen_from_black_carried_weight_screen_id);
    imageView.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            myActivity.replaceFragment(new Fragment_Main_Screen(myActivity));
          }
        });


    buttons[0].setOnClickListener(new View.OnClickListener() {// daily
      @Override
      public void onClick(View v) {
         changeButtonsColors(0);
         update_graph_data("d");
      }
    });




    buttons[1].setOnClickListener(new View.OnClickListener() {//weakly
      @Override
      public void onClick(View v) {
          changeButtonsColors(1);
          update_graph_data("w");
      }
    });



    buttons[2].setOnClickListener(new View.OnClickListener() {//monthly
      @Override
      public void onClick(View v) {
          changeButtonsColors(2);
          update_graph_data("m");
      }
    });



    buttons[3].setOnClickListener(new View.OnClickListener() {//yearly
      @Override
      public void onClick(View v) {
          changeButtonsColors(3);
          update_graph_data("y");
      }
    });

      max_carried_weight.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              max_carried_weight.setBackgroundResource(R.drawable.purple_background_for_buttons_in_blacked_history_screens);
              avg_carried_weight.setBackgroundResource(R.drawable.gray_background_for_buttons_in_blacked_history_screens);
          }
      });

      avg_carried_weight.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              max_carried_weight.setBackgroundResource(R.drawable.gray_background_for_buttons_in_blacked_history_screens);
              avg_carried_weight.setBackgroundResource(R.drawable.purple_background_for_buttons_in_blacked_history_screens);
          }
      });

    //graph.addSeries(series1);
    // color of background color
    graph.setBackgroundColor(Color.argb(255, 2, 0, 3));
    // color of Horizontal  numbers
    graph.getGridLabelRenderer().setHorizontalLabelsColor(Color.argb(255, 86, 86, 86));
    // color of Vertical numbers
    graph.getGridLabelRenderer().setVerticalLabelsColor(Color.argb(255, 86, 86, 86));
    // graph.getGridLabelRenderer().setVerticalLabelsVisible(true);
    // color of Horizontal and Vertical lines
    graph.getGridLabelRenderer().setGridColor(Color.argb(255, 209, 208, 209));

    /*graph.getViewport().setScalable(true); // activate horizontal zooming and scrolling
    graph.getViewport().setScrollable(true); // activate horizontal scrolling
    graph
        .getViewport()
        .setScalableY(true); // activate horizontal and vertical zooming and scrolling
    graph.getViewport().setScrollableY(true); // activate vertical scrolling*/
    return v;
  }
}
