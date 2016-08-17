package com.hanium.costamp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Window;

// 앱을 실행시켰을 때의 처음 인트로 화면
// 최종 수정자 : 유재혁, 최종 수정 날짜 : 20160711 15:00
public class SplashActivity extends Activity
{
    boolean isInternetWiMax = false; // 4G망 상태 값 저장
    boolean isInternetWiFi = false; // WiFi망 상태 값 저장
    boolean isInternetMobile = false; // 3G망 상태 값 저장

    private final int MY_PERMISSION_REQUEST_ACCESS_LOCATION = 100;
//    ImageView mIv_loading;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 상단바 삭제
        setContentView(com.hanium.costamp.R.layout.activity_splash);
//mIv_loading=(ImageView)findViewById(R.id.iv_loading)
//        int resourceId = R.drawable.map;
//        Glide.with(this)
//                .load(resourceId)
//                .into(mIv_loading);

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE); // 연결 상태를 확인하기 위한 ConnectivityManager 객체 선언

        // 연결 상태망 확인
        // 3G, 4G, Wifi 등 정상적인 연결이 확인되었을 때
        if(connectivityManager.getActiveNetworkInfo() != null)
        {
            NetworkInfo activeNetWork = connectivityManager.getActiveNetworkInfo(); // 현재 접속된 네트워크의 정보를 가지고 온다.

            // 4G로 연결되었을 때 처리되는 구간
            if(activeNetWork.getType() == ConnectivityManager.TYPE_WIMAX)
            {
                isInternetWiMax = true; // 4G 상태값 변화

                // 3초 후 로그인 화면으로 전환
                android.os.Handler handler = new android.os.Handler()
                {
                    @Override
                    public void handleMessage(Message msg)
                    {
                        Intent SplashToLogin = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(SplashToLogin);
                        finish();
                    }
                };
                handler.sendEmptyMessageDelayed(0, 3000);
            }

            // WiFi로 연결 될 경우에 처리하는 구간
            if(activeNetWork.getType() == ConnectivityManager.TYPE_WIFI)
            {
                isInternetWiFi = true; // WiFi 상태값 변화

                // 3초 후 로그인 화면으로 전환
                android.os.Handler handler = new android.os.Handler()
                {
                    @Override
                    public void handleMessage(Message msg)
                    {
                        Intent SplashToLogin = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(SplashToLogin);
                        finish();
                    }
                };
                handler.sendEmptyMessageDelayed(0, 3000);
            }

            // 3G로 연결 될 경우에 처리하는 구간
            if(activeNetWork.getType() == ConnectivityManager.TYPE_MOBILE)
            {
                isInternetMobile = true; // 3G 상태값 변화

                // 3초 후 인트로 화면으로 진행
                android.os.Handler handler = new android.os.Handler()
                {
                    @Override
                    public void handleMessage(Message msg)
                    {
                        Intent SplashToLogin = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(SplashToLogin);
                        finish();
                    }
                };
                handler.sendEmptyMessageDelayed(0, 3000);
            }
        }

        // 연결이 되지 않았을 때
        else
        {
            // 다이얼로그 메시지를 띄워서 네트워크 연결 되도록 함!
            AlertDialog.Builder MsgBuilder = new AlertDialog.Builder(this); // AlertDialog 사용을 위한 Builder 생성

            // AlertDialog 속성 설정
            MsgBuilder.setTitle("네트워크 연결 오류!") // 제목 설정
                    .setMessage("네트워크가 연결되지 않았습니다. 연결된 네트워크를 확인해주세요!") // 메시지 설정
                    .setCancelable(true) // 뒤로 가기 버튼 클릭 시 취소 가능하도록 함.
                    .setPositiveButton("확인", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            android.os.Process.killProcess(android.os.Process.myPid()); // 액티비티 죽임
                        }
                    });

            AlertDialog MsgDialog = MsgBuilder.create(); // 알림창 객체 생성
            MsgDialog.show(); // 알림창 띄우기
        }



    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_ACCESS_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {


                    // permission was granted, yay! do the
                    // calendar task you need to do.

                } else {

                    Log.d("permissioncheck", "Permission always deny");

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                break;
        }
    }
}
