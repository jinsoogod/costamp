package com.hanium.costamp;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import static com.hanium.costamp.picture_transmission_dialog.*;

/**
 * Created by YEP on 2016-08-04.
 */
//최종 작업일자 160820 00:30
//최종 작업자 : 으녕으녕

public class ImageAdapter extends BaseAdapter {
    static String[] mThumblds = new String[100];
    Context context;
//RelativeLayout rel;



    public ImageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return mThumblds.length;
    }

    @Override
    public Object getItem(int position) {
        return mThumblds[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageView imageView;


        if (convertView == null) {

            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(500, 500));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setPadding(2, 2, 2, 2);
        } else {
            imageView = (ImageView) convertView;
        }


        String url = (String) getItem(position);
/*        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.loader)
                .fit()
                .centerCrop().into(imageView);
*/
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.loader)
                .into(imageView);

        if(imageView == null){

        }

        imageView.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                View dialogView = (View) View.inflate(context, R.layout.fragment4_dialog, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                ImageView image10 = (ImageView) dialogView.findViewById(R.id.image10);
                //image10.setImageResource(mThumblds[position]);
                dlg.setView(dialogView);
                dlg.setNegativeButton("닫기", null);
                dlg.show();
            }
        });
        return imageView;
    }

}
