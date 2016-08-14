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
 * Created by YEP on 2016-07-26.
 */
public class ListViewAdapter extends BaseAdapter {
    Button mBtn_like;

    private Context mContext = null;
    private int layout = 0;
    private ArrayList<ListViewData> data = null;
    private LayoutInflater inflater = null;

    public ListViewAdapter(Context c, int l, ArrayList<ListViewData> d) {
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


        notifyDataSetChanged();
//                final int pos = position;

        // TODO Auto-generated method stub
        if (convertView == null) {
            convertView = inflater.inflate(this.layout, parent, false);

        } else {
            ImageView course_image = (ImageView) convertView.findViewById(R.id.iv_course);
            TextView course_info1 = (TextView) convertView.findViewById(R.id.tv_courseinfo1);
            TextView course_info2 = (TextView) convertView.findViewById(R.id.tv_courseinfo2);
            TextView course_info3 = (TextView) convertView.findViewById(R.id.tv_courseinfo3);


            Glide.with(mContext)
                    .load(data.get(position).image)
                    .into(course_image);
            course_info1.setText(data.get(position).info1);
            course_info2.setText(data.get(position).info2);
            course_info3.setText(data.get(position).info3);
//                    checked= (data.get(position).like);
        }


        mBtn_like = (Button) convertView.findViewById(R.id.btn_like);
        //
        data.get(position).like = true;
        mBtn_like.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if (data.get(position).like) {
                    String str = data.get(position).info1 + "를 좋아요 하였습니다.";
                    Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
                    //
                    data.get(position).like = false;

                } else if (data.get(position).like == false) {
                    String str = data.get(position).info1 + "를 좋아요 취소하였습니다.";
                    Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
                    //
                    data.get(position).like = true;
                }
            }
        });

        return convertView;

    }


}