package com.hanium.costamp;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;

//최종 작업일자 160820 00:30
//최종 작업자 : 으녕으녕
public class Course2Activity extends AppCompatActivity {

    private final int MY_PERMISSION_REQUEST_ACCESS_LOCATION = 100;
    static ArrayList<costamp_listData> costamp_list;
    costamp_listAdapter costampAdapter;
    ListView listView;
    static costamp_listData test1, test2, test3, test4, test5, test6;
    Button btn_CourseView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course2);

        Snackbar.make(getWindow().getDecorView().getRootView(),"코스가 제작되었습니다.",Snackbar.LENGTH_LONG).show();
        listView = (ListView) findViewById(R.id.lv_costamp);


        btn_CourseView = (Button)findViewById(R.id.btn_CourseView);
        // 좋아요 된 애들만 출력해주기
        test1 = new costamp_listData("바다바다", "제주시 노형동", "'가장 핫한 음식점'",R.drawable.badabada,new LatLng(33.4815972,126.4706638));
        test2 = new costamp_listData("협재 해수욕장", "제주시 한림읍 협재리", "'에메랄드빛 바다'", R.drawable.hyeopjae,new LatLng(33.3941308,126.2222184));
        test3 = new costamp_listData("관음사", " 제주시 아라일동", "'제주의 대표적 사찰'",R.drawable.gwanum,new LatLng(33.4302786,126.5300466));
        test4 = new costamp_listData("한라산", "제주시 아라동", "'한국에서 가장 높은산'",R.drawable.hanrasan,new LatLng(33.3616711,126.5269779));
        test5 = new costamp_listData("올레길", "서귀포시 남원읍 남원리", "'한국에서 가장 높은산'",R.drawable.olle,new LatLng(33.2781089,126.7157667));
        test6 = new costamp_listData("우도", "제주시 우도면 연평리", "'한국의 사이판'", R.drawable.woodo,new LatLng(33.4990766,126.9384237));

         /*
        한라산/@33.3616711,126.5269779
        우도/@33.4990766,126.9384237
        올레길/@33.2781089,126.7157667
        제주도 바다바다/@33.4815972,126.4706638
        관음사/@33.4302786,126.5300466
        협재 해수욕장 33.3941308,126.2222184
         */

        costamp_list = new ArrayList<costamp_listData>();
        costamp_list.add(test1);
        costamp_list.add(test2);
        costamp_list.add(test3);
        costamp_list.add(test4);
        costamp_list.add(test5);
        costamp_list.add(test6);


        costampAdapter = new costamp_listAdapter(this, R.layout.costamp_listview,costamp_list);
        listView.setAdapter(costampAdapter);

        btn_CourseView.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                //마시멜로이상 퍼미션체크
                if(ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                        checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

                    if(shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_COARSE_LOCATION)){
                        Snackbar.make(btn_CourseView,"위치확인",Snackbar.LENGTH_SHORT).show();
                    }

                    requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION},MY_PERMISSION_REQUEST_ACCESS_LOCATION);
                }
                else{
                }


                Intent intent = new Intent(getApplicationContext(),costamp_mapView.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_ACCESS_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {


                    // permission was granted, yay! do the
                    // calendar task you need to do.

                } else {

                    Log.d("permissioncheck", "Permission always deny");

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                break;
        }
    }
}
