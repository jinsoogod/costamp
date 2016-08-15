package com.hanium.costamp.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.view.KeyEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hanium.costamp.LoginAddInfoActivity;
import com.hanium.costamp.Main1Activity;
import com.hanium.costamp.R;
import com.hanium.costamp.config.ServerConnect;
import com.hanium.costamp.module.AppController;
import com.hanium.costamp.module.EmailSQLiteHandler;
import com.hanium.costamp.module.EmailSessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// E-mail로 회원 가입 화면
// 최종 수정자 : 유재혁, 최종 수정 날짜 : 20160813 14:30

public class EmailJoinActivity extends Activity
{
    // 로그캣 체크를 위한 태그값 설정
    private static final String TAG = EmailJoinActivity.class.getSimpleName();

    // 추가정보 입력 양식 관련 뷰 선언
    private EditText et_EnterEmail;
    private EditText et_EnterPassWord;
    private EditText et_EnterName;
    private Button btn_LoginClose;
    private RelativeLayout EmailJoinLayout;

    // 회원 DB 송수신 관련
    private Button btn_LoginSubmit;
    private ProgressDialog loadingDialog;
    private EmailSessionManager emailSessionManager;
    private EmailSQLiteHandler emailSQLiteHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.email_join);

        // 글꼴 변경을 위한 Typeface 객체 선언
        Typeface tf = Typeface.createFromAsset(getAssets(), "font.ttf.mp3"); // mp3 파일로 바꾸면 용량 문제가 해결됨.

        // 추가정보 입력 양식 관련 뷰 선언 및 저장
        et_EnterEmail = (EditText) findViewById(R.id.et_EnterEmail); // 이메일 입력
        et_EnterEmail.setTypeface(tf);
        et_EnterPassWord = (EditText) findViewById(R.id.et_EnterPassWord); // 패스워드입력
        et_EnterPassWord.setTypeface(tf);
        et_EnterName = (EditText) findViewById(R.id.et_EnterName); // 이름 입력
        et_EnterName.setTypeface(tf);
        btn_LoginClose = (Button)findViewById(R.id.btn_LoginClose); // 닫기 버튼
        EmailJoinLayout = (RelativeLayout)findViewById(R.id.EmailJoinLayout); // 레이아웃 처리

        // 포커스 움직임
        et_EnterEmail.setNextFocusDownId(R.id.et_EnterPassWord);
        et_EnterPassWord.setNextFocusDownId(R.id.et_EnterName);

        // 진행상황 표시 부분
        btn_LoginSubmit = (Button)findViewById(R.id.btn_LoginSubmit);
        loadingDialog = new ProgressDialog(this); // 자기참조
        loadingDialog.setCancelable(false);

        // 세션 매니저 선언
        emailSessionManager = new EmailSessionManager(getApplicationContext());

        // SQLite Database Handler 선언
        emailSQLiteHandler = new EmailSQLiteHandler(getApplicationContext());

        // 유저가 이미 로그인 했는지 안되었는지 파악
        if (emailSessionManager.isLoggedIn())
        {
            // 유저가 이미 로그인 한 상황이면 메인 액티비티로 이동함.
            Intent intent = new Intent(EmailJoinActivity.this, Main1Activity.class);
            startActivity(intent);
            finish();
        }

        // 키패드 밖 레이아웃 클릭하면 키보드 감추기
        EmailJoinLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(EmailJoinLayout.getWindowToken(), 0);
            }
        });

        // 확인 버튼을 누르면 정보 저장 후 이동함.
        btn_LoginSubmit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // 문자열 형태로 변환 및 저장
                String UserEmailID = et_EnterEmail.getText().toString();
                String UserPassword = et_EnterPassWord.getText().toString();
                String UserName = et_EnterName.getText().toString();

                // 입력된 값 식별 후 자료 넘겨줌.
                if ((UserEmailID.isEmpty() == false) && (UserEmailID.contains("@")) && (UserEmailID.contains(".")))
                {
                    registerEmailUser(UserEmailID, UserPassword, UserName);
                }

                // 공백이 있는 경우 에러 메시지 출력
                else
                {
                    Toast.makeText(getApplicationContext(), "빠진 항목이 있으니 확인해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 닫기 버튼을 누르면 이동
        btn_LoginClose.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent JoinToLogin = new Intent(EmailJoinActivity.this, LoginActivity.class);
                startActivity(JoinToLogin);
                finish();
            }
        });
    }

    // 파라미터를 받아 URL 등록 방식으로 MySQL Database에 회원정보를 뿌려주는 함수
    private void registerEmailUser(final String UserEmailID, final String UserPassword, final String UserName)
    {
        String tag_string_req = "req_register"; // 응답 취소를 위한 태그 선언

        // 로딩 다이얼로그 설정
        loadingDialog.setMessage("사용자 등록 중 입니다....");
        showDialog();

        // JSON 방식을 통하여 어드민 서버 상으로 내용 전달 부분
        // StringRequest : URL을 지정해주고 Raw String으로 된 응답을 받는다.
        // Raw String : String.xml을 통해서 정의된 String
        StringRequest strEmailReq = new StringRequest(Request.Method.POST, ServerConnect.URL_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // 등록 완료 로그값 출력
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                // JSON 형태의 객체를 생성하여 자료 저장
                try {
                    JSONObject EmailJObj = new JSONObject(response);
                    boolean error = EmailJObj.getBoolean("error");

                    // 만약 에러가 아닐 경우
                    if (!error) {
                        // 유저 정보를 정상적으로 MYSQL에 저장함
                        // SQLite를 통해서 유저 정보를 함께 저장함
                        JSONObject EmailJoinUser = EmailJObj.getJSONObject("user");

                        String UserEmailID = EmailJoinUser.getString("id");
                        String UserPassword = EmailJObj.getString("uid");
                        String UserName = EmailJObj.getString("name");

                        // 유저 테이블 안에 열 삽입
                        emailSQLiteHandler.addEmailJoinUser(null, UserEmailID, UserPassword, UserName, null, null);

                        // 알림 메시지 출력
                        Toast.makeText(getApplicationContext(), "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();

                        // 메인 액티비티로 이동
                        Intent JoinToMain = new Intent(EmailJoinActivity.this, Main1Activity.class);
                        startActivity(JoinToMain);
                        finish();
                    }

                    // 회원가입 과정에서 에러가 났을 경우
                    else {
                        String EmailErrorMsg = EmailJObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(), EmailErrorMsg, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() // 에러 시에 에러 응답을 콜백해주는 인터페이스
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                // 등록 관련 에러 메시지 출력
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                // Posting params to register url
                Map<String, String> EmailUserParams = new HashMap<String, String>();
                EmailUserParams.put("id", UserEmailID);
                EmailUserParams.put("uid", UserPassword);
                EmailUserParams.put("name", UserName);

                return EmailUserParams;
            }
        };

        // ReQuest Queue에 Request 추가
        AppController.getInstance().addToRequestQueue(strEmailReq, tag_string_req);
    }

    // 다이얼로그를 띄워줌.
    private void showDialog()
    {
        if (!loadingDialog.isShowing())
            loadingDialog.show();
    }

    // 다이얼로그를 감춤.
    private void hideDialog()
    {
        if (loadingDialog.isShowing())
            loadingDialog.dismiss();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        switch (keyCode)
        {
            case KeyEvent.KEYCODE_BACK:
                Intent JoinToLogin = new Intent(EmailJoinActivity.this, LoginActivity.class);
                startActivity(JoinToLogin);
                finish();
                break;
            default:
                break;
        }
        return true;
    }
}