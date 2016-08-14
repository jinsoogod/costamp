package com.hanium.costamp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by YEP on 2016-07-26.
 */
public class ListViewAdapter2 extends BaseAdapter {
    private Context mContext = null;
    private int layout = 0;
    private ArrayList<ListViewData2> data = null;
    private LayoutInflater inflater = null;

    public ListViewAdapter2(Context c, int l, ArrayList<ListViewData2> d) {
        this.mContext = c;
        this.layout = l;
        this.data = d;
        this.inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        notifyDataSetChanged();
        
        if (convertView == null) {
            convertView = inflater.inflate(this.layout, parent, false);
        } else {
            ImageView user_image = (ImageView) convertView.findViewById(R.id.iv_userimage);
            TextView ranking_info1= (TextView) convertView.findViewById(R.id.tv_rankinginfo1);
            TextView ranking_info2= (TextView) convertView.findViewById(R.id.tv_rankinginfo2);
            TextView ranking_info3= (TextView) convertView.findViewById(R.id.tv_rankinginfo3);


            user_image.setImageDrawable(data.get(position).image);
            ranking_info1.setText(data.get(position).info1);
            ranking_info2.setText(data.get(position).info2);
            ranking_info3.setText(data.get(position).info3);

        }


        return convertView;
    }
}