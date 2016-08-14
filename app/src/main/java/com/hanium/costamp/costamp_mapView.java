package com.hanium.costamp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.JsonDeserializer;

import java.util.ArrayList;

public class costamp_mapView extends Activity implements OnMapReadyCallback {

    ArrayList<costamp_listData> costamp_list;
    costamp_listAdapter costampAdapter;
    ListView listView;
    costamp_listData test1, test2, test3, test4, test5, test6;

    private GoogleMap googleMap;
    private PolylineOptions polylineOptions;
    private ArrayList<LatLng> arrayPoints;

    String serverKey = "AIzaSyBkrnK1sUgkaAIurp5xuebZ-HuKsJff3nc";

    @Override
    public void onMapReady(final GoogleMap map) {
        googleMap = map;

        Marker badabada = googleMap.addMarker(new MarkerOptions().position(test1.latLng).title("바다바다"));
        Marker mk2 = googleMap.addMarker(new MarkerOptions().position(test2.latLng).title("협재 해수욕장"));
        Marker mk3 = googleMap.addMarker(new MarkerOptions().position(test3.latLng).title("관음사"));
        Marker mk4 = googleMap.addMarker(new MarkerOptions().position(test4.latLng).title("한라산"));
        Marker mk5 = googleMap.addMarker(new MarkerOptions().position(test5.latLng).title("올레길"));
        Marker mk6 = googleMap.addMarker(new MarkerOptions().position(test6.latLng).title("우도"));

        // 맵셋팅
        polylineOptions = new PolylineOptions();
        polylineOptions.color(Color.RED);
        polylineOptions.width(15);
        arrayPoints.add(test1.latLng);
        arrayPoints.add(test2.latLng);
        arrayPoints.add(test3.latLng);
        arrayPoints.add(test4.latLng);
        arrayPoints.add(test5.latLng);
        arrayPoints.add(test6.latLng);
        polylineOptions.addAll(arrayPoints);
        map.addPolyline(polylineOptions);

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom( test1.latLng, 15));

        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_costamp_map_view);

        listView = (ListView) findViewById(R.id.lv_course);
        test1 = new costamp_listData("바다바다", "제주시 노형동", "'가장 핫한 음식점'",R.drawable.badabada,new LatLng(33.4815972,126.4706638));
        test2 = new costamp_listData("협재 해수욕장", "제주시 한림읍 협재리", "'에메랄드빛 바다'", R.drawable.hyeopjae,new LatLng(33.3941308,126.2222184));
        test3 = new costamp_listData("관음사", " 제주시 아라일동", "'제주의 대표적 사찰'",R.drawable.gwanum,new LatLng(33.4302786,126.5300466));
        test4 = new costamp_listData("한라산", "제주시 아라동", "'한국에서 가장 높은산'",R.drawable.hanrasan,new LatLng(33.3616711,126.5269779));
        test5 = new costamp_listData("올레길", "서귀포시 남원읍 남원리", "'한국에서 가장 높은산'",R.drawable.olle,new LatLng(33.2781089,126.7157667));
        test6 = new costamp_listData("우도", "제주시 우도면 연평리", "'한국의 사이판'", R.drawable.woodo,new LatLng(33.4990766,126.9384237));

        costamp_list = new ArrayList<costamp_listData>();
        costamp_list.add(test1);
        costamp_list.add(test2);
        costamp_list.add(test3);
        costamp_list.add(test4);
        costamp_list.add(test5);
        costamp_list.add(test6);

        costampAdapter = new costamp_listAdapter(this, R.layout.costamp_listview,costamp_list);
        listView.setAdapter(costampAdapter);

        arrayPoints = new ArrayList<LatLng>();

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.course_map);
        mapFragment.getMapAsync(this);

            }

}

        //카메라 움직임
