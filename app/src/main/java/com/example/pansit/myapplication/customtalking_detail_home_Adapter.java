package com.example.pansit.myapplication;

/**
 * Created by Pansit on 7/21/2015.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class customtalking_detail_home_Adapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private ArrayList<String> textdetail;
    private ArrayList<String> textname;
    private ArrayList<String> textpic;


    public customtalking_detail_home_Adapter(Activity activity, ArrayList<String> textdetail , ArrayList<String> textname , ArrayList<String> textpic) {
        this.activity = activity;
        this.textdetail = textdetail;
        this.textname = textname;
        this.textpic = textpic;
    }

    @Override
    public int getCount() {
        return textdetail.size();
    }

    @Override
    public Object getItem(int location) {
        return textdetail.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.detailtalking_of_listview_assit, null);


        TextView listview_textname = (TextView) convertView.findViewById(R.id.name_talking_assit);
        TextView listview_textdetail = (TextView) convertView.findViewById(R.id.detail_talking_assit);
        //ImageView listview_textpic = (ImageView) convertView.findViewById(R.id.pic_talking_assit);


        final String name =  textname.get(position);
        final String detail =  textdetail.get(position);

        listview_textname.setText(name);
        listview_textdetail.setText(detail);

        return convertView;
    }

}
