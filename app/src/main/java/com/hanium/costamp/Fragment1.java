package com.hanium.costamp;

/**
 * Created by YEP on 2016-07-16.
 */

import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;


//메인화면에서 COSTAMP탭 Fragment
public class Fragment1 extends Fragment {

    ViewPager pager;
    View view;

    Spinner mSpinner11;
    Spinner mSpinner12;

    ListView listView;
    ArrayList<ListViewData> course_info_list;
    ListViewAdapter courseAdapter;
    ListViewData test1, test2, test3, test4;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment1, container, false);
        mSpinner11 = (Spinner) view.findViewById(R.id.spinner11);
        mSpinner12 = (Spinner) view.findViewById(R.id.spinner12);
        pager = (ViewPager) view.findViewById(R.id.adpager);

        initSpinner11();
        initSpinner12();

        listView = (ListView) view.findViewById(R.id.listView);

        test1 = new ListViewData("A여행지", "가나라 정보2", "정보3", false, BitmapFactory.decodeResource(getResources(), R.drawable.testimage1));
        test2 = new ListViewData("B여행지", "아앙정보2", "정보3", false, BitmapFactory.decodeResource(getResources(), R.drawable.testimage2));
        test3 = new ListViewData("C여행지", "아아으 정보2", "정보3", false, BitmapFactory.decodeResource(getResources(), R.drawable.testimage1));
        test4 = new ListViewData("D여행지", "123정보2", " 정보3", false, BitmapFactory.decodeResource(getResources(), R.drawable.testimage2));

        course_info_list = new ArrayList<ListViewData>();
        course_info_list.add(test1);
        course_info_list.add(test2);
        course_info_list.add(test3);
        course_info_list.add(test4);
        course_info_list.add(test1);
        course_info_list.add(test2);
        course_info_list.add(test3);
        course_info_list.add(test4);


        courseAdapter = new ListViewAdapter(getActivity(), R.layout.fragment1_listview, course_info_list);
        listView.setAdapter(courseAdapter);

        return view;
    }


    //지역별로 사진을 볼 수 있도록 하는 Spinner2를 초기화하는 메소드
    public void initSpinner11() {
        String[] strRegion = {"모든지역", "지역1", "지역2", "지역3"};
        ArrayList<String> mS4 = new ArrayList<String>();
        for (int i = 0; i < 4; i++) {
            mS4.add(strRegion[i]);
        }
        // 어댑터 객체를 생성하고 ArrayList 를 지정
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_spinner_item, mS4);
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        mSpinner11 = (Spinner) view.findViewById(R.id.spinner11);
        mSpinner11.setAdapter(adapter);
    }


    //최신순 or 인기순 등으로 사진을 볼 수 있도록 하는 Spinner5을 초기화하는 메소드
    public void initSpinner12() {
        String[] strcate = {"최신순", "인기순", "????",};
        ArrayList<String> mS5 = new ArrayList<String>();
        for (int i = 0; i < 3; i++) {
            mS5.add(strcate[i]);
        }
        // 어댑터 객체를 생성하고 ArrayList 를 지정
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_spinner_item, mS5);
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        mSpinner12 = (Spinner) view.findViewById(R.id.spinner12);
        mSpinner12.setAdapter(adapter);
    }


}





