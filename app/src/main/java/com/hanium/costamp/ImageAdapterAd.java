package com.hanium.costamp;

/**
 * Created by YEP on 2016-08-03.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

public class ImageAdapterAd extends PagerAdapter {

    Context mContext;
    LayoutInflater inflater;
    ImageView img;
    ArrayList<Bitmap> bitmaps;

    //context랑 화면에보여줄 bitmap파일들 받고 초기화
    public ImageAdapterAd(Context context, ArrayList<Bitmap> items) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        bitmaps = items;
    }

    //PagerAdapter가 가지고 잇는 View의 개수를 리턴
    //보통 보여줘야하는 이미지 배열 데이터의 길이를 리턴
    @Override
    public int getCount() {
        return bitmaps.size(); //이미지 개수 리턴
    }

    //ViewPager가 현재 보여질 Item(View객체)를 생성할 필요가 있는 때 자동으로 호출
    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view=null;

        view= inflater.inflate(R.layout.viewpager, null);
        img= (ImageView)view.findViewById(R.id.img_viewpager);

        img.setImageBitmap(bitmaps.get(position));
        //ViewPager에 만들어 낸 View 추가
        container.addView(view);

        //Image가 세팅된 View를 리턴
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub


        container.removeView((View)object);

    }

    //instantiateItem() 메소드에서 리턴된 Ojbect가 View가  맞는지 확인하는 메소드
    @Override
    public boolean isViewFromObject(View v, Object obj) {
        // TODO Auto-generated method stub
        return v==obj;
    }

}