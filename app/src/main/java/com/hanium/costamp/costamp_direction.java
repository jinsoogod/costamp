package com.hanium.costamp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Step;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

//최종 작업일자 160820 00:30
//최종 작업자 : 으녕으녕
public class costamp_direction extends Activity implements OnMapReadyCallback {


    static final LatLng PKNU = new LatLng(35.1338149, 129.1015348);
    //direction api를 사용하기위한 인터넷 server Key
    //String serverKey = "AIzaSyDFWdlR5DG1VYXSaMwG62ilxxxxxxxxx";
    //String serverKey ="AIzaSyC5atU8_OZIE9Bkf5q0g6VKCXOhrOQ1HPw";
    String serverKey = "AIzaSyBkrnK1sUgkaAIurp5xuebZ-HuKsJff3nc";
    int i = 0;
    LatLng origin;
    LatLng destination;

    private GoogleMap googleMap;

    @Override
    public void onMapReady(final GoogleMap map) {

        //구글맵 객체 선언
        googleMap = map;
        //어디를 띄어줄 것인가?
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Course2Activity.test1.latLng, 15));

        //줌 해주는거
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

        //시작점
        origin = Course2Activity.test1.latLng;
        //도착점
        destination = Course2Activity.test2.latLng;

        //길찾기 요청
        requestDirection();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_costamp_direction);


        //MapFragment에 OnMapReadySync를 맞춰줌
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.direction_map);
        mapFragment.getMapAsync(this);

    }


    //길찾기요청
    public void requestDirection() {
        Snackbar.make(getWindow().getDecorView().getRootView(), "Direction Requesting...", Snackbar.LENGTH_SHORT).show();

        //디렉 션 라이브러리 를 그대로사용
        GoogleDirection.withServerKey(serverKey)
                .from(origin)
                .to(destination)
                .transportMode(TransportMode.TRANSIT)       //대중교통모드
                .execute(new DirectionCallback() {
                    //길찾기 성공했을때
                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
                        Snackbar.make(getWindow().getDecorView().getRootView(), "Success with status : " + direction.getStatus(), Snackbar.LENGTH_LONG).show();
                        if (direction.isOK()) {
                            ArrayList<LatLng> sectionPositionList = direction.getRouteList().get(0).getLegList().get(0).getSectionPoint();
                            for (LatLng position : sectionPositionList) {
                                //마커찍어줌
                                googleMap.addMarker(new MarkerOptions().position(position));
                            }
                            //경로 그려줌 대중교통은 빨간색 도보는 파란색
                            List<Step> stepList = direction.getRouteList().get(0).getLegList().get(0).getStepList();
                            ArrayList<PolylineOptions> polylineOptionList = DirectionConverter.createTransitPolyline(getApplicationContext(), stepList, 5, Color.RED, 3, Color.BLUE);
                            for (PolylineOptions polylineOption : polylineOptionList) {
                                googleMap.addPolyline(polylineOption);
                            }

                            getWindow().getDecorView().getRootView().setVisibility(View.GONE);
                        }
                    }

                    //길찾기 실패했을때
                    @Override
                    public void onDirectionFailure(Throwable t) {
                        Snackbar.make(getWindow().getDecorView().getRootView(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                });
    }

}
