package com.example.pansit.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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


    TextView calBurnTxt,waterGetTxt,calGetToday,calMaxToday,calBurnMax,waterMaxTxt,calSlash,calUnit,graphtext;
    ImageView btn_arrowback,btn_arrownext;

    //Button waterButton;
    LinearLayout foodButton,activityButton,reportButton,assistanceButton;
    LinearLayout calBox,calburnBox;
    ListView foodDailyListView;
    ArrayList<String> arr_list;

    //check show graph
    int graphnow = 1;


    boolean isOpen;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.home, container, false);
        final Context context = new ContextThemeWrapper(getActivity(), R.style.Theme_AppCompat_Light);//set theme for fragment
        //isOpen = false;

        final DataKeeper data  = ((NewHome) getActivity()).getData();
        ArrayList<Food> breakfastFood = data.getBreakfastFood();
        ArrayList<Food> lunchFood = data.getLunchFood();
        ArrayList<Food> eveningFood = data.getEveningFood();
        ArrayList<Food> nightFood = data.getNightFood();

        ProgressBar progressBarlvl;
        progressBarlvl = (ProgressBar) view.findViewById(R.id.lvlprogess);
        progressBarlvl.setMax(100);
        progressBarlvl.setProgress(10);





        // setgraph



        graphtext = (TextView)view.findViewById(R.id.txtgraph_home);
        btn_arrowback = (ImageView) view.findViewById(R.id.backgraph_home);
        btn_arrownext = (ImageView) view.findViewById(R.id.nextgraph_home);

        graphtext.setText("Calorie today");

        graphswitch( data, view);

        btn_arrowback.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 graphnow--;
                                                 graphswitch( data, view);
                                             }
                                         }

        );

        btn_arrownext.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 graphnow++;
                                                 graphswitch( data, view);}}

        );



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




        foodButton = (LinearLayout)view.findViewById(R.id.foodButton);
        activityButton = (LinearLayout)view.findViewById(R.id.activityButton);
        reportButton = (LinearLayout)view.findViewById(R.id.reportButton);
        assistanceButton = (LinearLayout)view.findViewById(R.id.assistantButton);




        /*
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
        */
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

    public void graphswitch(DataKeeper data ,View view)
    {
        final PieGraph pg = (PieGraph)view.findViewById(R.id.graph);
        pg.removeSlices();

        ListView detail_listview = (ListView)view.findViewById(R.id.listView_detail_home);
        detail_listview.setAdapter(null);

        ArrayList<String> detailname = new ArrayList<String>();
        ArrayList<String> valname = new ArrayList<String>();
        ArrayList<String> colorname = new ArrayList<String>();
        customdetail_home_Adapter adapter;


        int calperday = data.getCaloriesPerDay();
        int caleat = data.getCaloriesConsumed();
        int calburn = data.getCaloriesBurned();


        String textcolor1 = "#ff262626";
        String textcolor2 = "#fffd8409";

        if( graphnow == 1)
        {
            // graph1

            graphtext.setText("Calorie today");

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

            PieSlice slice = new PieSlice();
            slice.setColor(Color.parseColor(textcolor1));
            slice.setValue(calperday - caleat);
            pg.addSlice(slice);


            slice = new PieSlice();
            slice.setColor(Color.parseColor(textcolor2));
            slice.setValue(caleat);
            pg.addSlice(slice);



            pg.setInnerCircleRatio(200);
            pg.setPadding(5);


            // list view
            detailname.add("Calorie Today : ");
            valname.add(data.getCaloriesConsumed() + " cal");
            colorname.add(textcolor2);


            detailname.add("Calorie Per Day : ");
            valname.add(data.getCaloriesPerDay()+" cal");
            colorname.add(textcolor1);

            adapter = new customdetail_home_Adapter(getActivity(), detailname,colorname,valname);
            detail_listview.setAdapter(adapter);
        }
        else if(graphnow == 2)
        {

            // graph2
            graphtext.setText("Calorie's Burn today");

            textcolor1 = "#ff262626";
            textcolor2 = "#ff16b405";


            PieGraph pg2 = pg;
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


            // list view
            detailname.add("Calorie Burn : ");
            valname.add(data.getCaloriesBurned() + " cal");
            colorname.add(textcolor2);


            detailname.add("Calorie Today : ");
            valname.add(data.getCaloriesConsumed() + " cal");
            colorname.add(textcolor1);

            adapter = new customdetail_home_Adapter(getActivity(), detailname,colorname,valname);
            detail_listview.setAdapter(adapter);

        }
        else if(graphnow == 3 )
        {
            graphtext.setText("Nutrorail today");

            PieGraph pg3 = pg;

            int fatval=1,choresval=1,sodiumval=1,carboval=1,proteinval=1;
            for(int i=0;i < data.getTodayFood().size();i++)
            {

                fatval += data.getTodayFood().get(i).getFat();
                choresval += data.getTodayFood().get(i).getCalories();
                sodiumval += data.getTodayFood().get(i).getSodium();
                carboval += data.getTodayFood().get(i).getCarbohydrate();
                proteinval += data.getTodayFood().get(i).getProtein();


            }


            // 1 fat
            PieSlice slice = new PieSlice();
            slice.setColor(Color.parseColor("#fffd8409"));
            slice.setValue(fatval);
            pg3.addSlice(slice);

            // 2 Cholesterol
            slice = new PieSlice();
            slice.setColor(Color.parseColor("#FFD10AA6"));
            slice.setValue(choresval);
            pg3.addSlice(slice);


            // 3 Sodium
            slice = new PieSlice();
            slice.setColor(Color.parseColor("#FF34CB14"));
            slice.setValue(sodiumval);
            pg3.addSlice(slice);

            // 4 Carbohydrate
            slice = new PieSlice();
            slice.setColor(Color.parseColor("#ff2f95be"));
            slice.setValue(carboval);
            pg3.addSlice(slice);

            // 5 Protein
            slice = new PieSlice();
            slice.setColor(Color.parseColor("#FFFDEB37"));
            slice.setValue(proteinval);
            pg3.addSlice(slice);

            pg3.setInnerCircleRatio(200);
            pg3.setPadding(5);


            // list view
            detailname.add("Fat Today : ");
            valname.add(fatval-1 + " mmg");
            colorname.add("#fffd8409");


            detailname.add("Cholesterol Today : ");
            valname.add(choresval-1 + " mmg");
            colorname.add("#FFD10AA6");

            detailname.add("Sodium Today : ");
            valname.add(sodiumval-1 + " mmg");
            colorname.add("#FF34CB14");

            detailname.add("Carbohydrate Today : ");
            valname.add(carboval-1 + " mmg");
            colorname.add("#ff2f95be");

            detailname.add("Protein Today : ");
            valname.add(proteinval-1 + " mmg");
            colorname.add("#FFFDEB37");

            adapter = new customdetail_home_Adapter(getActivity(), detailname,colorname,valname);
            detail_listview.setAdapter(adapter);

        }
        else if(graphnow <= 0)
        {
            graphnow = 3;
            graphswitch(data,view);

        }
        else
        {

            graphnow = 1;
            graphswitch(data,view);
        }

    }
}