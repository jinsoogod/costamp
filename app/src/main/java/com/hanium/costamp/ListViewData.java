package com.hanium.costamp;

import android.graphics.Bitmap;

/**
 * Created by YEP on 2016-07-26.
 */
public class ListViewData {

    public Bitmap image;
    public String info1;
    public String info2;
    public String info3;
    public boolean like;

    ListViewData(String info1, String info2, String info3, Boolean like, Bitmap image) {
        this.image = image;
        this.info1 = info1;
        this.info2 = info2;
        this.info3 = info3;
        this.like = like;
    }
}


