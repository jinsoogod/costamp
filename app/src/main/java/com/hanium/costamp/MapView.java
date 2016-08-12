package com.hanium.costamp;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Step;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class MapView extends Activity {

    private Button btn_RequestDirection;
    private final int MY_PERMISSION_REQUEST_ACCESS_LOCATION = 100;


    static final LatLng PKNU = new LatLng(35.1338149,129.1015348);
    private GoogleMap map;
    //String serverKey = "AIzaSyDFWdlR5DG1VYXSaMwG62ilxxxxxxxxx";
    String serverKey ="AIzaSyC5atU8_OZIE9Bkf5q0g6VKCXOhrOQ1HPw";
    LatLng origin = new LatLng(35.1338149,129.1015348); //pknu
    LatLng destination = new LatLng(35.1531863,129.1099112); //광안리해수욕장



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);

        btn_RequestDirection = (Button)findViewById(R.id.btn_RequestDirection);

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

        btn_RequestDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestDirection();
            }
        });

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

    public void requestDirection() {
        Snackbar.make(btn_RequestDirection, "Direction Requesting...", Snackbar.LENGTH_SHORT).show();
        GoogleDirection.withServerKey(serverKey)
                .from(origin)
                .to(destination)
                .transportMode(TransportMode.TRANSIT)
                .execute(new DirectionCallback(){
                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
                        Snackbar.make(btn_RequestDirection, "Success with status : " + direction.getStatus(), Snackbar.LENGTH_SHORT).show();
                        if (direction.isOK()) {
                            ArrayList<LatLng> sectionPositionList = direction.getRouteList().get(0).getLegList().get(0).getSectionPoint();
                            for (LatLng position : sectionPositionList) {
                                map.addMarker(new MarkerOptions().position(position));
                            }

                            List<Step> stepList = direction.getRouteList().get(0).getLegList().get(0).getStepList();
                            ArrayList<PolylineOptions> polylineOptionList = DirectionConverter.createTransitPolyline(getApplicationContext(), stepList, 5, Color.RED, 3, Color.BLUE);
                            for (PolylineOptions polylineOption : polylineOptionList) {
                                map.addPolyline(polylineOption);
                            }

                            btn_RequestDirection.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onDirectionFailure(Throwable t) {
                        Snackbar.make(btn_RequestDirection, t.getMessage(), Snackbar.LENGTH_SHORT).show();
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


