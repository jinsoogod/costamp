package com.hanium.costamp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.Serializable;
import java.util.ArrayList;

//최종 작업일자 160820 00:30
//최종 작업자 : 으녕으녕
public class costamp_mapView extends Activity implements OnMapReadyCallback {

    costamp_listAdapter costampAdapter;
    ListView listView;
    Button btn_courseDirectionRequest;

    private GoogleMap googleMap;
    private PolylineOptions polylineOptions;
    private ArrayList<LatLng> arrayPoints;

    String serverKey = "AIzaSyBkrnK1sUgkaAIurp5xuebZ-HuKsJff3nc";

    @Override
    public void onMapReady(final GoogleMap map) {

        //경로 마커를 찍어준다
        googleMap = map;

        Marker mk1 = googleMap.addMarker(new MarkerOptions().position(Course2Activity.test1.latLng).title("바다바다").icon(BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(((BitmapDrawable)getResources().getDrawable(R.drawable.marker1)).getBitmap(), 117, 170 , false))));
        Marker mk2 = googleMap.addMarker(new MarkerOptions().position(Course2Activity.test2.latLng).title("협재 해수욕장").icon(BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(((BitmapDrawable)getResources().getDrawable(R.drawable.marker2)).getBitmap(), 117, 170 , false))));
        Marker mk3 = googleMap.addMarker(new MarkerOptions().position(Course2Activity.test3.latLng).title("관음사").icon(BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(((BitmapDrawable)getResources().getDrawable(R.drawable.marker3)).getBitmap(), 117, 170, false))));
        Marker mk4 = googleMap.addMarker(new MarkerOptions().position(Course2Activity.test4.latLng).title("한라산").icon(BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(((BitmapDrawable)getResources().getDrawable(R.drawable.marker4)).getBitmap(), 117, 170 , false))));
        Marker mk5 = googleMap.addMarker(new MarkerOptions().position(Course2Activity.test5.latLng).title("올레길").icon(BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(((BitmapDrawable)getResources().getDrawable(R.drawable.marker5)).getBitmap(), 117, 170 , false))));
        Marker mk6 = googleMap.addMarker(new MarkerOptions().position(Course2Activity.test6.latLng).title("우도").icon(BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(((BitmapDrawable)getResources().getDrawable(R.drawable.marker6)).getBitmap(), 117, 170 , false))));


        //경로 라인을 그려줌 add 된순서대로 나온다.
        polylineOptions = new PolylineOptions();
        polylineOptions.color(Color.rgb(16,15,56));
        polylineOptions.width(15);
        arrayPoints.add(Course2Activity.test1.latLng);
        arrayPoints.add(Course2Activity.test2.latLng);
        arrayPoints.add(Course2Activity.test3.latLng);
        arrayPoints.add(Course2Activity.test4.latLng);
        arrayPoints.add(Course2Activity.test5.latLng);
        arrayPoints.add(Course2Activity.test6.latLng);
        polylineOptions.addAll(arrayPoints);
        map.addPolyline(polylineOptions);

        // 맵셋팅
       googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom( Course2Activity.test1.latLng, 15));

        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_costamp_map_view);

        listView = (ListView) findViewById(R.id.lv_course);
        costampAdapter = new costamp_listAdapter(this, R.layout.costamp_listview,Course2Activity.costamp_list);
        listView.setAdapter(costampAdapter);

        arrayPoints = new ArrayList<LatLng>();

        //onMapReady와 MapFragment Sync
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.course_map);
        mapFragment.getMapAsync(this);


        //길찾기 요청해줌
        btn_courseDirectionRequest = (Button)findViewById(R.id.btn_costamp_RequestDirection);
        btn_courseDirectionRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),costamp_direction.class);
                startActivity(intent);

            }
        });
            }

}

        //카메라 움직임
