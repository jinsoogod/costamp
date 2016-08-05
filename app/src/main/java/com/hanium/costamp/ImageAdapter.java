package com.hanium.costamp;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by YEP on 2016-08-04.
 */

public class ImageAdapter extends BaseAdapter {
    Context context;
//RelativeLayout rel;
    private Integer[] mThumblds = {
            R.drawable.testimage1, R.drawable.testimage2, R.drawable.testimage1,
    R.drawable.testimage2, R.drawable.testimage1, R.drawable.testimage2, R.drawable.testimage1,
    R.drawable.testimage2, R.drawable.testimage1, R.drawable.testimage2, R.drawable.testimage1,
    R.drawable.testimage2
};


    public ImageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return mThumblds.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        ImageView imageView;


        if (convertView == null) {

            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(250, 250));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setPadding(2, 2, 2, 2);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumblds[position]);


        imageView.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                View dialogView = (View) View.inflate(context, R.layout.fragment4_dialog, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                ImageView image10 = (ImageView) dialogView.findViewById(R.id.image10);
                image10.setImageResource(mThumblds[pos]);
                dlg.setView(dialogView);
                dlg.setNegativeButton("닫기", null);
                dlg.show();
            }
        });


        return imageView;
    }
}
