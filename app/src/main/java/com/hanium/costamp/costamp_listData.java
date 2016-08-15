package com.hanium.costamp;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by korea on 2016-08-14.
 */
public class costamp_listData implements Serializable {

    public int image;
    public String info1;
    public String info2;
    public String info3;
    public LatLng latLng;
    public int image2;

    costamp_listData(String info1, String info2, String info3, int image, LatLng latLng) {
        this.image = image;
        this.info1 = info1;
        this.info2 = info2;
        this.info3 = info3;
        this.latLng = latLng;
        this.image2 = R.drawable.dot;
    }
}
