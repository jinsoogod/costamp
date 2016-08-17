package com.hanium.costamp;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class Map_Custom_Marker extends AppCompatActivity {
    ImageView iv_CustomMarker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map__custom__marker);

        iv_CustomMarker = (ImageView)findViewById(R.id.iv_CustomMarker);
        markerImage();
    }

    public void markerImage(){
        Glide.with(this)
                .load(R.drawable.marker1)
                .into(iv_CustomMarker);
    }
}
