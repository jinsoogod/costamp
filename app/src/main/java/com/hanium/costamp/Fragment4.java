package com.hanium.costamp;

/**
 * Created by YEP on 2016-07-16.
 */
//최종 작업일자 160809 10:08
//최종 작업자 : 으녕으녕

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;

import java.util.ArrayList;


//메인화면에서 PHOTO탭 Fragment
public class Fragment4 extends Fragment {

    Spinner mSpinner2;
    Spinner mSpinner3;
    GridView mGridview;
    View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment4, container, false);

        mSpinner2 = (Spinner) view.findViewById(R.id.spinner2);
        mSpinner3 = (Spinner) view.findViewById(R.id.spinner3);
        initSpinner2();
        initSpinner3();


        // 그리드 어댑터 세팅: 이미지 처리를 위한 이미지 어댑터를 그리드뷰에 전달
        mGridview = (GridView) view.findViewById(R.id.gridView);
        ImageAdapter imageAdapter = new ImageAdapter(this.getActivity());
        mGridview.setAdapter(imageAdapter);

        return view;
    }



    //지역별로 사진을 볼 수 있도록 하는 Spinner2를 초기화하는 메소드
    public void initSpinner2() {
        String[] strRegion = {"모든지역", "지역1", "지역2", "지역3"};
        ArrayList<String> mS1 = new ArrayList<String>();
        for (int i = 0; i < 4; i++) {
            mS1.add(strRegion[i]);
        }
        // 어댑터 객체를 생성하고 ArrayList 를 지정
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_spinner_item, mS1);
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        mSpinner2 = (Spinner) view.findViewById(R.id.spinner2);
        mSpinner2.setAdapter(adapter);
    }


    //최신순 or 인기순 등으로 사진을 볼 수 있도록 하는 Spinner3을 초기화하는 메소드
    public void initSpinner3() {
        String[] strcate = {"최신순", "인기순", "????",};
        ArrayList<String> mS2 = new ArrayList<String>();
        for (int i = 0; i < 3; i++) {
            mS2.add(strcate[i]);
        }
        // 어댑터 객체를 생성하고 ArrayList 를 지정
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_spinner_item, mS2);
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        mSpinner3 = (Spinner) view.findViewById(R.id.spinner3);
        mSpinner3.setAdapter(adapter);
    }



}