package com.hanium.costamp;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hanium.costamp.module.MyLocation;

public class MapView extends Activity {

    private final int MY_PERMISSION_REQUEST_ACCESS_LOCATION = 100;


    static final LatLng PKNU = new LatLng(35.1338149,129.1015348);
    private GoogleMap map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);


        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                .getMap();
        Marker pknu = map.addMarker(new MarkerOptions().position(PKNU)
                .title("PKNU"));


        //초기 위치
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(PKNU, 15));

        //위치 찾기 퍼미션 체크
        //퍼미션체크 마시멜로 이상만 체크
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED){

            if(shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_COARSE_LOCATION)){
                Toast.makeText(this,"위치확인",Toast.LENGTH_SHORT).show();
            }

            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION},MY_PERMISSION_REQUEST_ACCESS_LOCATION);
        }
        else{
        }

        //현재 위치로 가는 버튼 표시
        map.setMyLocationEnabled(true);

        MyLocation.LocationResult locationResult = new MyLocation.LocationResult() {
            @Override
            public void gotLocation(Location location) {
                String msg = "lon : " + location.getLongitude() + " - lat : " + location.getLatitude();
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                drawMarker(location);
            }
        };

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(PKNU, 14));

    }
    private void drawMarker(Location location){

        LatLng currentPosition = new LatLng(location.getLatitude(),location.getLongitude());

        //currentPosition 위치로 카메라 중심을 옮기고 화면 줌을 조정함
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition,17));
        map.animateCamera(CameraUpdateFactory.zoomTo(14),2000,null);

        //마커 추가함
        map.addMarker(new MarkerOptions().position(currentPosition).snippet("Lat : "+location.getLatitude() + "Lng : " + location.getLongitude())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .title("현재위치"));
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


