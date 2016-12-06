package com.hanium.costamp;

/**
 * Created by YEP on 2016-07-16.
 */
//최종 작업일자 160820 00:30
//최종 작업자 : 으녕으녕

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;


//메인화면에서 PHOTO탭 Fragment
public class Fragment4 extends Fragment {

    Spinner mSpinner2;
    Spinner mSpinner3;
    GridView mGridview;
    View view;
    static int fileLength;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment4, container, false);

        mSpinner2 = (Spinner) view.findViewById(R.id.spinner2);
        mSpinner3 = (Spinner) view.findViewById(R.id.spinner3);
        initSpinner2();
        initSpinner3();
        ReadLengthThread readLength = new ReadLengthThread();
        readLength.start();

        // 그리드 어댑터 세팅: 이미지 처리를 위한 이미지 어댑터를 그리드뷰에 전달
        mGridview = (GridView) view.findViewById(R.id.gv_signageImage);
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


    class ReadLengthThread extends Thread{
        @Override
        public void run() {

            //소켓 아이피 & 포트
            Socket socket = null;
            try {
                socket = new Socket("1.255.57.236",8878);
                //사이니지에 reject 반환
                ObjectOutputStream outstream = new ObjectOutputStream(socket.getOutputStream());
                outstream.writeUTF("request");
                outstream.flush();
                ObjectInputStream instream = new ObjectInputStream(socket.getInputStream());
                //파일 갯수 불러오기
                fileLength = instream.readInt();
                outstream.close();
                instream.close();
                socket.close();
                //mThumbIds를 fileLength에따라 동적할당
                ImageAdapter.mThumblds = new String[fileLength];
                for(int i=0 ; i<fileLength; i++){
                    //이미지 url 다넣어줌
                    ImageAdapter.mThumblds[i] = "http://1.255.57.236/picture/upload"+i+".png";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}