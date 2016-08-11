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

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import static com.hanium.costamp.picture_transmission_dialog.*;

/**
 * Created by YEP on 2016-08-04.
 */
//최종 작업일자 160809 10:11
    //최종 작업자 : 으녕

public class ImageAdapter extends BaseAdapter {
    static String[] mThumblds = new String[100];


    Thread setImage = new Thread(new Runnable(){
        @Override
        public void run() {
            int i=0;
            String[] items=new String[100];
            while(i<100){
                items[i] ="http://1.255.57.236/picture/upload"+Integer.toString(i)+".png";
                i++;
            }
        }
    });

    public void setSetImage() {
        setImage.start();
    }

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
            imageView.setLayoutParams(new GridView.LayoutParams(250, 250));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setPadding(2, 2, 2, 2);
        } else {
            imageView = (ImageView) convertView;
        }


        String url = (String) getItem(position);
        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.loader)
                .fit()
                .centerCrop().into(imageView);

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
