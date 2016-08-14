package com.hanium.costamp;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ListView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CourseActivity extends AppCompatActivity {
    Button mBtn_makingCourse;
    Button btn_MapView;
    ListView listView;

    //ListViewData= 여행지 data
    ArrayList<ListViewData> travel_info_list;
    ListViewAdapter travelAdapter;
    ListViewData test1, test2, test3, test4, test5, test6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        listView = (ListView) findViewById(R.id.listView);
        mBtn_makingCourse = (Button) findViewById(R.id.btn_makingCourse);

        //맵뷰 테스트용 버튼
        btn_MapView = (Button)findViewById(R.id.btn_mapview);

        mBtn_makingCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(),Course2Activity.class);
                startActivity(intent);
            }
        });

        btn_MapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(),MapView.class);
                startActivity(intent);
            }
        });


        // 좋아요 된 애들만 출력해주기
        test1 = new ListViewData("한라산", "여행지 정보2", "여행지 정보3", true,getDrawableFromResource(this,R.drawable.hanrasan));
        test2 = new ListViewData("우도", "여행지 정보2", "여행지 정보3", true,getDrawableFromResource(this,R.drawable.woodo));
        test3 = new ListViewData("협재 해수욕장", "여행지 정보2", "여행지 정보3", true,getDrawableFromResource(this,R.drawable.hyeopjae));
        test4 = new ListViewData("올레길", "여행지 정보2", "여행지 정보3", true, getDrawableFromResource(this,R.drawable.olle));
        test5 = new ListViewData("관음사", "여행지 정보2", "여행지 정보3", true, getDrawableFromResource(this,R.drawable.hanrasan));
        test6 = new ListViewData("바다바다", "여행지 정보2", "여행지 정보3", true, getDrawableFromResource(this,R.drawable.badabada));

         /*
        한라산/@33.3616711,126.5269779
        우도/@33.4990766,126.9384237
        올레길/@33.2781089,126.7157667
        제주도 바다바다/@33.4815972,126.4706638
        관음사/@33.4302786,126.5300466
        협재 해수욕장 33.3941308,126.2222184
         */

        travel_info_list = new ArrayList<ListViewData>();
        travel_info_list.add(test1);
        travel_info_list.add(test2);
        travel_info_list.add(test3);
        travel_info_list.add(test4);
        travel_info_list.add(test5);
        travel_info_list.add(test6);

        travelAdapter = new ListViewAdapter(this, R.layout.fragment1_listview, travel_info_list);
        listView.setAdapter(travelAdapter);



    }
    public static Drawable getDrawableFromResource(Context context, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getDrawable(id);
        } else {
            return context.getResources().getDrawable(id);
        }
    }

}
