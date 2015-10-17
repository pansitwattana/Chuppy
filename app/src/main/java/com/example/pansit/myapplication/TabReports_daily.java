package com.example.pansit.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.echo.holographlibrary.Bar;
import com.echo.holographlibrary.BarGraph;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * Created by Pansit on 6/2/2015.
 */
public class TabReports_daily extends Fragment {

    final int AmountBar = 30;
    final int BarColor = Color.parseColor("#fffb8209");



    DataKeeper data;
    BarGraph barGraph;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_report_daily, container, false);
        barGraph = (BarGraph)view.findViewById(R.id.barGraph2);
        data =  ((NewHome)getActivity()).getData();

        Spinner dropdown = (Spinner) view.findViewById(R.id.spinnerReport);
        String[] items = new String[]{"Nutrorail today"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, items);
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {

                    ArrayList<Bar> points = new ArrayList<Bar>();

                        Bar bar = new Bar();
                        bar.setColor(BarColor);
                        bar.setName("01");
                        bar.setGoalValue(10);
                        points.add(bar);

                         bar.setName("02");
                         bar.setGoalValue(10);
                         points.add(bar);

                        barGraph.setBars(points);

                        barGraph.setValueStringPrecision(0);
                        barGraph.setDuration(0);//default if unspecified is 300 ms
                        barGraph.setInterpolator(new AccelerateDecelerateInterpolator());//Only use over/undershoot  when not inserting/deleting
                        barGraph.animateToGoalValues();
                        barGraph.setValueStringPrecision(0); //1 decimal place. 0 by default for integers.

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




         //1 decimal place. 0 by default for integers.



        return view;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        ((NewHome) activity).onSectionAttached(6);
    }
}
