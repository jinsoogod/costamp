package com.hanium.costamp;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
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
        test1 = new ListViewData("한라산", "여행지 정보2", "여행지 정보3", true, BitmapFactory.decodeResource(getResources(), R.drawable.hanrasan));
        test2 = new ListViewData("우도", "여행지 정보2", "여행지 정보3", true, BitmapFactory.decodeResource(getResources(), R.drawable.woodo));
        test3 = new ListViewData("협재 해수욕장", "여행지 정보2", "여행지 정보3", true, BitmapFactory.decodeResource(getResources(), R.drawable.hyeopjae));
        test4 = new ListViewData("올레길", "여행지 정보2", "여행지 정보3", true, BitmapFactory.decodeResource(getResources(), R.drawable.olle));
        test5 = new ListViewData("관음사", "여행지 정보2", "여행지 정보3", true, BitmapFactory.decodeResource(getResources(), R.drawable.testimage1));
        test6 = new ListViewData("바다바다", "여행지 정보2", "여행지 정보3", true, BitmapFactory.decodeResource(getResources(), R.drawable.badabada));




        travel_info_list = new ArrayList<ListViewData>();
        travel_info_list.clear();
        travel_info_list.add(test1);
        travel_info_list.add(test2);
        travel_info_list.add(test3);
        travel_info_list.add(test4);
        travel_info_list.add(test5);
        travel_info_list.add(test6);

        travelAdapter = new ListViewAdapter(this, R.layout.fragment1_listview, travel_info_list);
        listView.setAdapter(travelAdapter);



    }


}
