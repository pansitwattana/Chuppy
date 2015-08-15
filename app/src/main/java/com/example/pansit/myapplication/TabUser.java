package com.example.pansit.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.echo.holographlibrary.PieGraph;
import com.echo.holographlibrary.PieSlice;

import java.util.ArrayList;


/**
 * Created by Pansit on 6/2/2015.
 */
public class TabUser extends Fragment {


    TextView calBurnTxt,waterGetTxt,calGetToday,calMaxToday,calBurnMax,waterMaxTxt,calSlash,calUnit;
    //Button waterButton;
    LinearLayout foodButton,activityButton,reportButton,assistanceButton;
    LinearLayout calBox,calburnBox;
    ListView foodDailyListView;
    ArrayList<String> arr_list;


    boolean isOpen;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);
        final Context context = new ContextThemeWrapper(getActivity(), R.style.Theme_AppCompat_Light);//set theme for fragment
        //isOpen = false;

        DataKeeper data  = ((NewHome) getActivity()).getData();
        ArrayList<Food> breakfastFood = data.getBreakfastFood();
        ArrayList<Food> lunchFood = data.getLunchFood();
        ArrayList<Food> eveningFood = data.getEveningFood();
        ArrayList<Food> nightFood = data.getNightFood();

        ProgressBar progressBarlvl;
        progressBarlvl = (ProgressBar) view.findViewById(R.id.lvlprogess);
        progressBarlvl.setMax(100);
        progressBarlvl.setProgress(10);


        int calperday = data.getCaloriesPerDay();
        int caleat = data.getCaloriesConsumed();
        int calburn = data.getCaloriesBurned();

        String textcolor1 = "#ff262626";
        String textcolor2 = "#fffd8409";

        // graph1
        PieGraph pg = (PieGraph)view.findViewById(R.id.graph);
        pg.setDuration(2000);//default if unspecified is 300 ms



        if(calperday >= caleat)
        {
            caleat = (caleat *100)/calperday;
            calperday = 100;
        }
        else
        {
         int calmore = caleat%calperday;
        caleat =  (calmore *100)/calperday;
            calperday = 100;

            textcolor1 = "#fffd8409";
            textcolor2 = "#FFB01F1B";
        }

        //showToast(calperday + "  " + caleat);
        PieSlice slice = new PieSlice();
        slice.setColor(Color.parseColor(textcolor1));
        slice.setValue(calperday - caleat);
        pg.addSlice(slice);


        slice = new PieSlice();
        slice.setColor(Color.parseColor(textcolor2));
        slice.setValue(caleat);
      //  slice.setGoalValue(caleat);
        pg.addSlice(slice);
       // pg.animateToGoalValues();
       // for (PieSlice s : pg.getSlices())
           // s.setGoalValue(data.getCaloriesConsumed());


        pg.setInnerCircleRatio(200);
        pg.setPadding(5);

        // graph2

         textcolor1 = "#ff262626";
         textcolor2 = "#ff16b405";

        PieGraph pg2 = (PieGraph)view.findViewById(R.id.graph2);
       // pg2.setDuration(2000);//default if unspecified is 300 ms


        caleat = data.getCaloriesConsumed();
        if(caleat >= calburn)
        {
            if(caleat != 0) {
                calburn = (calburn * 100) / caleat;
            }
            caleat = 100;
        }
        else
        {
            int calmore = calburn - caleat;
            calburn =  (calmore *100)/caleat;
            caleat = 100;

            textcolor1 = "#ff16b405";
            textcolor2 = "#ff438424";
        }




        PieSlice slice2 = new PieSlice();
        slice2.setColor(Color.parseColor(textcolor1));
        slice2.setValue(caleat - calburn);
        slice2.setTitle("CAL ALL");
      //  slice2.setGoalValue(data.getCaloriesPerDay() - data.getCaloriesConsumed());
        pg2.addSlice(slice2);


        slice2 = new PieSlice();
        slice2.setColor(Color.parseColor(textcolor2));
        slice2.setValue(calburn);
       // slice2.setGoalValue(calburn);
        pg2.addSlice(slice2);
        // pg2.animateToGoalValues();



        pg2.setInnerCircleRatio(200);
        pg2.setPadding(5);




        arr_list = new ArrayList<String>();
        arr_list.add("*MONRING*");
        if(!breakfastFood.isEmpty()) {
            for (int i = 0; i < breakfastFood.size(); i++) {
                arr_list.add("-" + breakfastFood.get(i).getName());
            }
        }

        arr_list.add("*NOON*");
        if(!lunchFood.isEmpty()) {
            for (int i = 0; i < lunchFood.size(); i++) {
                arr_list.add("- " + lunchFood.get(i).getName()+" ( "+lunchFood.get(i).getCalories()+" Cal. ) ");
            }
        }
        arr_list.add("*EVENING & NIGHT*");
        if(!eveningFood.isEmpty()) {
            for (int i = 0; i < eveningFood.size(); i++) {
                arr_list.add("- " + eveningFood.get(i).getName()+" ( "+eveningFood.get(i).getCalories()+" Cal. ) ");
            }
        }
        if(!nightFood.isEmpty()){
            for(int i = 0;i<nightFood.size();i++){
                arr_list.add("- " + nightFood.get(i).getName()+" ( "+nightFood.get(i).getCalories()+" Cal. ) ");
            }
        }

        foodDailyListView = (ListView) view.findViewById(R.id.foodDailyListView);
        calBox = (LinearLayout) view.findViewById(R.id.calBox);
        calburnBox = (LinearLayout) view.findViewById(R.id.calburnbox);
        calGetToday = (TextView) view.findViewById(R.id.calGetToday);
        calMaxToday = (TextView) view.findViewById(R.id.calMaxToday);
        calSlash = (TextView) view.findViewById(R.id.calSlash1);
        calUnit = (TextView) view.findViewById(R.id.calUnit);

        calBurnTxt = (TextView) view.findViewById(R.id.calBurnTxt);
        calBurnMax = (TextView) view.findViewById(R.id.calBurnMax);
        waterGetTxt = (TextView) view.findViewById(R.id.waterGetTxt);
        waterMaxTxt = (TextView) view.findViewById(R.id.waterMaxTxt);


        foodButton = (LinearLayout)view.findViewById(R.id.foodButton);
        activityButton = (LinearLayout)view.findViewById(R.id.activityButton);
        reportButton = (LinearLayout)view.findViewById(R.id.reportButton);
        assistanceButton = (LinearLayout)view.findViewById(R.id.assistantButton);


        // waterButton = (Button) view.findViewById(R.id.waterButton);
        calMaxToday.setText("" + data.getCaloriesPerDay());
        calGetToday.setText("" + data.getCaloriesConsumed());
        calBurnTxt.setText("" + data.getCaloriesBurned());
        calBurnMax.setText("" + data.getCaloriesConsumed());
        waterGetTxt.setText("" + data.getWaterConsumed());
        waterMaxTxt.setText("" + data.getWaterPerDay());

        foodDailyListView.setAdapter(new ArrayAdapter(getActivity(), R.layout.simple_list_item_1, arr_list));

        if(data.getCaloriesConsumed() < (data.getCaloriesPerDay() * 0.5) ){
            //< 50 ������ < 80 ������ͧ < 100 ��ᴧ
            calMaxToday.setTextColor(Color.parseColor("#a4c639"));
            calGetToday.setTextColor(Color.parseColor("#a4c639"));
            calSlash.setTextColor(Color.parseColor("#a4c639"));
            calUnit.setTextColor(Color.parseColor("#a4c639"));

        }
        else if(data.getCaloriesConsumed() < (data.getCaloriesPerDay() * 0.8)){
            calMaxToday.setTextColor(Color.parseColor("#ffff00"));
            calGetToday.setTextColor(Color.parseColor("#ffff00"));
            calSlash.setTextColor(Color.parseColor("#ffff00"));
            calUnit.setTextColor(Color.parseColor("#ffff00"));
        }
        else {
            calMaxToday.setTextColor(Color.parseColor("#ff0000"));
            calGetToday.setTextColor(Color.parseColor("#ff0000"));
            calSlash.setTextColor(Color.parseColor("#ff0000"));
            calUnit.setTextColor(Color.parseColor("#ff0000"));
        }

        calBox.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {



                      final Dialog dialog = new Dialog(context);
                      dialog.setTitle("Food History");
                      dialog.setContentView(R.layout.foodhistory_dialog);

                      ListView listView = (ListView) dialog.findViewById(R.id.foodhistory_listview);
                      ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, arr_list);
                      listView.setAdapter(adapter);

                      Button button = (Button) dialog.findViewById(R.id.foodhistoryclose);
                      button.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View v) {

                              dialog.dismiss();

                          }});

                      dialog.show();

                  }

              }

        );
        foodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NewHome)getActivity()).changeFragment(1);

            }
        });

        activityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NewHome)getActivity()).changeFragment(3);
            }
        });

        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NewHome)getActivity()).changeFragment(5);
            }
        });

        assistanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NewHome)getActivity()).changeFragment(6);
            }
        });
        /*waterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment newFragment = new TabWater();
                FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
                ft.replace(R.id.fragmentContainer, newFragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();
            }
        });*/

        calburnBox.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {


                                          final Dialog dialog = new Dialog(context);
                                          dialog.setTitle("Acitivity History");
                                          dialog.setContentView(R.layout.foodhistory_dialog);


                                          Button button = (Button) dialog.findViewById(R.id.foodhistoryclose);
                                          button.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {

                                                  dialog.dismiss();
                                              }});

                                          dialog.show();

                                      }

                                  }

        );

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        ((NewHome) activity).onSectionAttached(1);
    }

    public void showToast(CharSequence text){
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }
}