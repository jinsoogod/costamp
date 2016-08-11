package com.hanium.costamp;

import android.app.Activity;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
public class MapView extends Activity {


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

        //map.moveCamera(CameraUpdateFactory.newLatLngZoom( PKNU, 15));

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(PKNU,14));
    }
}
