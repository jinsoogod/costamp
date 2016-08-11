package com.hanium.costamp;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class CourseActivity extends AppCompatActivity {
    Button mBtn_makingCourse;
    Button btn_MapView;
    ListView listView;

    //ListViewData= 여행지 data
    ArrayList<ListViewData> travel_info_list;
    ListViewAdapter travelAdapter;
    ListViewData test1, test2, test3, test4;


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
        test1 = new ListViewData("카멜리아힐", "여행지 정보2", "여행지 정보3", true, BitmapFactory.decodeResource(getResources(), R.drawable.testimage1));
        test2 = new ListViewData("섭지코지", "여행지 정보2", "여행지 정보3", true, BitmapFactory.decodeResource(getResources(), R.drawable.testimage1));
        test3 = new ListViewData("우도", "여행지 정보2", "여행지 정보3", true, BitmapFactory.decodeResource(getResources(), R.drawable.testimage1));
        test4 = new ListViewData("성산일출봉", "여행지 정보2", "여행지 정보3", true, BitmapFactory.decodeResource(getResources(), R.drawable.testimage1));

        travel_info_list = new ArrayList<ListViewData>();

        travel_info_list.add(test1);
        travel_info_list.add(test2);
        travel_info_list.add(test3);
        travel_info_list.add(test4);


        travelAdapter = new ListViewAdapter(this, R.layout.fragment1_listview, travel_info_list);
        listView.setAdapter(travelAdapter);


    }


}
