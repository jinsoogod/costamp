package com.hanium.costamp;
/**
 * Created by YEP on 2016-07-16.
 */

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


//메인화면에서 COURSE탭 Fragment
public class Fragment2 extends Fragment {
    Button mBtn_jeju;
    ImageView mIv_map;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment2, null);
        mBtn_jeju = (Button) view.findViewById(R.id.btn_jeju);
        mIv_map = (ImageView)view.findViewById(R.id.iv_map) ;

        int resourceId = R.drawable.map;

        Glide.with(this)
                .load(resourceId)
                .into(mIv_map);


        mBtn_jeju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CourseActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }



}