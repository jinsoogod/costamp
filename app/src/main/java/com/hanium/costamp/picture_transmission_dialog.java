package com.hanium.costamp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

// 사이니지에서 전송한 사진을 받을때 뜰 팝업창
// 최종 수정자 : 이은영, 최종 수정 날짜 : 20160712 15:30
public class picture_transmission_dialog extends AppCompatActivity {


    //전송받은 이미지
    ImageView picture = (ImageView)findViewById(R.id.imageView_Transmission_SignagePicture);

    //사진전송 수락버튼과 거절버튼
    Button btn_accept = (Button)findViewById(R.id.btn_dialog_accept);
    Button btn_reject = (Button)findViewById(R.id.btn_dialog_reject);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_transmission_dialog);

        //이부분은 전송받은 사진 이미지뷰에 띄움


        //사진전송 수락
        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //사진 전송 수락하면 갤러리에 저장, 내 여행루트에 등록된 곳이라면 여행기 코멘트창으로 넘어가야됨

            }
        });

        //사진전송 거절
        btn_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //거절하면 토스트메세지 띄우고 거절
                Toast.makeText(picture_transmission_dialog.this,"사진 안받아",Toast.LENGTH_LONG).show();
            }
        });
    }
}