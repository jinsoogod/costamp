package com.hanium.costamp;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

//최종 작업일자 160820 00:30
//최종 작업자 : 으녕으녕
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
        test1 = new ListViewData("한라산", "제주시 아라동", "'한국에서 가장 높은산'", true,R.drawable.hanrasan);
        test2 = new ListViewData("우도", "제주시 우도면 연평리", "'한국의 사이판'", true, R.drawable.woodo);
        test3 = new ListViewData("협재 해수욕장", "제주시 한림읍 협재리", "'에메랄드빛 바다'", true, R.drawable.hyeopjae);
        test4 = new ListViewData("올레길", "서귀포시 남원읍 남원리", "'한국에서 가장 높은산'", true, R.drawable.olle);
        test5 = new ListViewData("관음사", " 제주시 아라일동", "'제주의 대표적 사찰'", true, R.drawable.gwanum);
        test6 = new ListViewData("바다바다", "제주시 노형동", "'가장 핫한 음식점'", true, R.drawable.badabada);

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
