package com.example.senior_project_mobile_app;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class Fragment_Humadity_Screen extends Fragment {
    MainActivity myActivity;
    public Fragment_Humadity_Screen(MainActivity m){
        myActivity=m;
    }
    View v;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState)
    {
        v= inflater.inflate(R.layout.humidity_history_black,container,false);

        ImageView imageView = v.findViewById(R.id.GoBack_to_main_screen_from_black_humadity_history_screen_id);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myActivity.replaceFragment(new Fragment_Main_Screen(myActivity));
            }
        });
        double x,y;
        GraphView graph = v.findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();

        for (int i=0;i<100;i++){
            x=i;
            y=Math.sin(i);
            series.appendData(new DataPoint(x,y),false,100);
            //color of series
            series.setColor(Color.argb(255,237,125,49));
            // series.setDataPointsRadius(200);
            series.setDrawDataPoints(true);
            series.setDataPointsRadius(10);

        }


        graph.addSeries(series);
        //color of background color
        graph.setBackgroundColor(Color.argb(255,2,0,3));
        //color of Horizontal  numbers
        graph.getGridLabelRenderer().setHorizontalLabelsColor(Color.argb(255,86,86,86));
        //color of Vertical numbers
        graph.getGridLabelRenderer().setVerticalLabelsColor(Color.argb(255,86,86,86));
        //graph.getGridLabelRenderer().setVerticalLabelsVisible(true);
        //color of Horizontal and Vertical lines
        graph.getGridLabelRenderer().setGridColor(Color.argb(255,209,208,209));

        graph.getViewport().setScalable(true);  // activate horizontal zooming and scrolling
        graph.getViewport().setScrollable(true);  // activate horizontal scrolling
        graph.getViewport().setScalableY(true);  // activate horizontal and vertical zooming and scrolling
        graph.getViewport().setScrollableY(true);  // activate vertical scrolling
        return v;

    }
}
