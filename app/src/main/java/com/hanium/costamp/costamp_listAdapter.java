package com.hanium.costamp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by korea on 2016-08-14.
 */
public class costamp_listAdapter extends BaseAdapter{

    Button mBtn_remove;

    private Context mContext = null;
    private int layout = 0;
    private ArrayList<costamp_listData> data = null;
    private LayoutInflater inflater = null;

    public costamp_listAdapter(Context c, int i , ArrayList<costamp_listData> d){
        this.mContext = c;
        this.layout = i;
        this.data = d;
        this.inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }
    @Override
    public Object getItem(int position) {
        return data;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        notifyDataSetChanged();
//                final int pos = position;

        // TODO Auto-generated method stub
        if (convertView == null) {
            convertView = inflater.inflate(this.layout, parent, false);

        } else {
            ImageView course_image = (ImageView) convertView.findViewById(R.id.iv_course);
            ImageView dot_image = (ImageView)convertView.findViewById(R.id.Iv_dot);
            TextView course_info1 = (TextView) convertView.findViewById(R.id.tv_courseinfo1);
            TextView course_info2 = (TextView) convertView.findViewById(R.id.tv_courseinfo2);
            TextView course_info3 = (TextView) convertView.findViewById(R.id.tv_courseinfo3);


            Glide.with(mContext)
                    .load(data.get(position).image)
                    .into(course_image);

            Glide.with(mContext)
                    .load(data.get(position).image2)
                    .into(dot_image);
            course_info1.setText(data.get(position).info1);
            course_info2.setText(data.get(position).info2);
            course_info3.setText(data.get(position).info3);
//                    checked= (data.get(position).like);
        }


        mBtn_remove = (Button) convertView.findViewById(R.id.btn_remove);
        //
        mBtn_remove.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });

        return convertView;

    }


}