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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;


//메인화면에서 RANKING탭 Fragment
public class Fragment3 extends Fragment {

    Spinner mSpinner4;
    Spinner mSpinner5;

    ListView listView2;
    ArrayList<ListViewData2> ranking_info_list;
    ListViewAdapter2 rankingAdapter;
    ListViewData2 testa ,testb, testc, testd, teste;



    View view;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment3, container, false);

        mSpinner4 = (Spinner) view.findViewById(R.id.spinner4);
        mSpinner5 = (Spinner) view.findViewById(R.id.spinner5);

        initSpinner4();
        initSpinner5();

        listView2 = (ListView) view.findViewById(R.id.listView2);

        testa = new ListViewData2("Skymemorize777", "제주도 여행", "#제주도 #엑티비티", BitmapFactory.decodeResource(getResources(), R.drawable.ranking_profile_1));
        testb = new ListViewData2("pinkgonju","제주도 여행", "#제주도 #힐링", BitmapFactory.decodeResource(getResources(), R.drawable.ranking_profile_2));
        testc = new ListViewData2("jinsugod12", "제주도 여행", "#편안한 #안전한", BitmapFactory.decodeResource(getResources(), R.drawable.ranking_profile_3));
        testd = new ListViewData2("godjujin", "제주도 여행", "#먹방 #제주도올레길", BitmapFactory.decodeResource(getResources(), R.drawable.ranking_profile_4));
        teste = new ListViewData2("younget02", "제주도 여행", "#제주도 #헬", BitmapFactory.decodeResource(getResources(), R.drawable.ranking_profile_5));

        ranking_info_list = new ArrayList<ListViewData2>();


        ranking_info_list.add(testa);
        ranking_info_list.add(testb);
        ranking_info_list.add(testc);
        ranking_info_list.add(testd);
        ranking_info_list.add(teste);



        rankingAdapter = new ListViewAdapter2(getActivity(), R.layout.fragment3_listview, ranking_info_list);
        listView2.setAdapter(rankingAdapter);
        return view;
    }



    //지역별로 사진을 볼 수 있도록 하는 Spinner2를 초기화하는 메소드
    public void initSpinner4() {
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

        mSpinner4 = (Spinner) view.findViewById(R.id.spinner4);
        mSpinner4.setAdapter(adapter);
    }


    //최신순 or 인기순 등으로 사진을 볼 수 있도록 하는 Spinner5을 초기화하는 메소드
    public void initSpinner5() {
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

        mSpinner5 = (Spinner) view.findViewById(R.id.spinner5);
        mSpinner5.setAdapter(adapter);
    }


}