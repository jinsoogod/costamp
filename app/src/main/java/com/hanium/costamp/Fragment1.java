package com.hanium.costamp;

/**
 * Created by YEP on 2016-07-16.
 */

import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


//메인화면에서 COSTAMP탭 Fragment
public class Fragment1 extends Fragment {

    ListView listView;
    ArrayList<ListViewData> course_info_list;
    ListViewAdapter courseAdapter;
    ListViewData test1, test2, test3, test4;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment1, container, false);

        listView = (ListView) view.findViewById(R.id.listView);

        test1 = new ListViewData("A코스", "코스 정보2", "코스 정보3", BitmapFactory.decodeResource(getResources(), R.drawable.testimage1));
        test2 = new ListViewData("B코스", "코스 정보2", "코스 정보3", BitmapFactory.decodeResource(getResources(), R.drawable.testimage2));
        test3 = new ListViewData("C코스", "코스 정보2", "코스 정보3", BitmapFactory.decodeResource(getResources(), R.drawable.testimage1));
        test4 = new ListViewData("D코스", "코스 정보2", "코스 정보3", BitmapFactory.decodeResource(getResources(), R.drawable.testimage2));

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


}



