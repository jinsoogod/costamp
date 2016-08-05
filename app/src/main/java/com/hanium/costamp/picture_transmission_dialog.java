package com.hanium.costamp;

import android.*;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;

// 사이니지에서 전송한 사진을 받을때 뜰 팝업창
// 최종 수정자 : 이은영, 최종 수정 날짜 : 20160801 15:30
public class picture_transmission_dialog extends Activity {

    private final int MY_PERMISSION_REQUEST_STORAGE = 100;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //앱이름 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_picture_transmission_dialog);
        //바깥쪽터치해도 dialog종료안됨

        //이미지뷰
        ImageViewThread imageviewthread = new ImageViewThread();
        imageviewthread.start();

        //파일저장전 퍼미션 체크

        //퍼미션체크
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED){

            if(shouldShowRequestPermissionRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                Toast.makeText(this,"dlqcnffur",Toast.LENGTH_SHORT).show();
            }

            requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_PERMISSION_REQUEST_STORAGE);
        }
        else{
        }



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_STORAGE:
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //return super.onTouchEvent(event);
        return false;
    }

    // 수락 버튼
    public void clk_accept(View v){

        RequestThread thread = new RequestThread();
        thread.start();
        Toast.makeText(this,"사진전송이 완료 되었습니다.",Toast.LENGTH_LONG).show();
        setResult(1002);
        finish();

    }

    //거절 버튼
    public void clk_reject(View v){

        Toast.makeText(this,"사진전송이 거절 되었습니다.",Toast.LENGTH_LONG).show();
        RejectThread thread = new RejectThread();
        thread.start();
        setResult(1002);
        finish();
    }

    class RequestThread extends Thread{

        DataInputStream dis;
        FileOutputStream fos;
        BufferedOutputStream bos;

        public void run(){

            try {

                //소켓 아이피 & 포트
                Socket socket = new Socket("192.168.0.14",5549);


                //사이니지에 accept 반환
                ObjectOutputStream outstream = new ObjectOutputStream(socket.getOutputStream());
                outstream.writeUTF("accept");
                outstream.flush();

                dis = new DataInputStream(socket.getInputStream());

                //파일 이름 불러오기
                String fName = dis.readUTF();

                //파일 이름
                File f = new File("upload.png");
                fos = new FileOutputStream(Environment.getExternalStorageDirectory().getAbsolutePath()+"/upload.png");
                bos = new BufferedOutputStream(fos);
                Log.d("picture transmission","ok");

                //바이트 데이터를 전송받으면서 기록
                int len;
                int size = 4096;
                byte[] data = new byte[size];

                while((len = dis.read(data)) > 0){
                    bos.write(data,0,len);
                }

                bos.flush();
                //Toast.makeText(getApplicationContext(),"파일 수신완료",Toast.LENGTH_SHORT).show();
                Log.d("picture transmission","ok2");

                outstream.close();
                fos.close();
                bos.close();
                dis.close();
                socket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    class RejectThread extends Thread{
        @Override
        public void run() {

            //소켓 아이피 & 포트
            Socket socket = null;
            try {
                socket = new Socket("192.168.0.14",5549);
                //사이니지에 reject 반환
                ObjectOutputStream outstream = new ObjectOutputStream(socket.getOutputStream());
                outstream.writeUTF("reject");
                outstream.flush();

                outstream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    class ImageViewThread extends Thread{
        Handler handler = new Handler();
        @Override
        public void run() {

            final ImageView pictureView = (ImageView)findViewById(R.id.imageView_Transmission_SignagePicture);

            URL url = null;
            try {
                url = new URL("http://1.255.57.236/upload.png");
                InputStream inputstream = url.openStream();
                final Bitmap bm = BitmapFactory.decodeStream(inputstream);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        pictureView.setImageBitmap(bm);
                    }
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        }
}