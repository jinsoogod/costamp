package com.hanium.costamp;

import android.app.Activity;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class costamp_direction extends Activity implements OnMapReadyCallback {


    static final LatLng PKNU = new LatLng(35.1338149, 129.1015348);
    //direction api를 사용하기위한 인터넷 server Key
    //String serverKey = "AIzaSyDFWdlR5DG1VYXSaMwG62ilxxxxxxxxx";
    //String serverKey ="AIzaSyC5atU8_OZIE9Bkf5q0g6VKCXOhrOQ1HPw";
    String serverKey = "AIzaSyBkrnK1sUgkaAIurp5xuebZ-HuKsJff3nc";
    int i =0;
    LatLng origin;
    LatLng destination;

    private GoogleMap googleMap;

    @Override
    public void onMapReady(final GoogleMap map) {

        googleMap = map;
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Course2Activity.test1.latLng, 15));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

        origin = Course2Activity.test1.latLng;
        destination = Course2Activity.test2.latLng;

        requestDirection();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_costamp_direction);


        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.direction_map);
        mapFragment.getMapAsync(this);

    }



    //길찾기요청
    public void requestDirection() {
        Snackbar.make(getWindow().getDecorView().getRootView(), "Direction Requesting...", Snackbar.LENGTH_SHORT).show();
        GoogleDirection.withServerKey(serverKey)
                .from(origin)
                .to(destination)
                .transportMode(TransportMode.TRANSIT)
                .execute(new DirectionCallback(){
                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
                        Snackbar.make(getWindow().getDecorView().getRootView(), "Success with status : " + direction.getStatus(), Snackbar.LENGTH_LONG).show();
                        if (direction.isOK()) {
                            ArrayList<LatLng> sectionPositionList = direction.getRouteList().get(0).getLegList().get(0).getSectionPoint();
                            for (LatLng position : sectionPositionList) {
                                googleMap.addMarker(new MarkerOptions().position(position));
                            }

                            List<Step> stepList = direction.getRouteList().get(0).getLegList().get(0).getStepList();
                            ArrayList<PolylineOptions> polylineOptionList = DirectionConverter.createTransitPolyline(getApplicationContext(), stepList, 5, Color.RED, 3, Color.BLUE);
                            for (PolylineOptions polylineOption : polylineOptionList) {
                                googleMap.addPolyline(polylineOption);
                            }

                            getWindow().getDecorView().getRootView().setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onDirectionFailure(Throwable t) {
                        Snackbar.make(getWindow().getDecorView().getRootView(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                });
    }

}
