package com.hanium.costamp.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.view.KeyEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.hanium.costamp.LoginAddInfoActivity;
import com.hanium.costamp.Main1Activity;
import com.hanium.costamp.R;
import com.hanium.costamp.module.EmailSQLiteHandler;
import com.hanium.costamp.module.EmailSessionManager;

import java.util.ArrayList;

// E-mail로 회원 가입 화면
// 최종 수정자 : 유재혁, 최종 수정 날짜 : 20160813 14:30

public class EmailJoinActivity extends Activity
{
    // 로그캣 체크를 위한 태그값 설정
    private static final String TAG = EmailJoinActivity.class.getSimpleName();

    // 추가정보 입력 양식 관련 뷰 선언
    private EditText et_EnterEmail;
    private EditText et_EnterPassWord;
    private EditText et_EnterRePassWord;
    private EditText et_EnterName;

    // 회원 DB 송수신 관련
    private ProgressDialog loadingDialog;
    private EmailSessionManager emailSessionManager;
    private EmailSQLiteHandler emailSQLiteHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.email_join);

        // 추가정보 입력 양식 관련 뷰 선언 및 저장
        et_EnterEmail = (EditText) findViewById(R.id.et_EnterEmail); // 이메일 입력
        et_EnterPassWord = (EditText) findViewById(R.id.et_EnterPassWord); // 패스워드입력
        et_EnterRePassWord = (EditText) findViewById(R.id.et_EnterRePassWord); // 패스워드 재입력
        et_EnterName = (EditText) findViewById(R.id.et_EnterName); // 이름 입력

        // 진행상황 표시 부분
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
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        switch (keyCode)
        {
            case KeyEvent.KEYCODE_BACK:
                Intent JoinToLogin = new Intent(EmailJoinActivity.this, LoginActivity.class);
                startActivity(JoinToLogin);
                break;
            default:
                break;
        }
        return true;
    }

    /*
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.submit_button:
                // String str = mEditEmail.getText().toString();
                // String strpw = mEditPassword.getText().toString();
                String strpw2 = mEditRePassword.getText().toString();
                RadioButton strrd = (RadioButton) findViewById(mRadioGender.getCheckedRadioButtonId());
                String strrd2 = strrd.getText().toString();
                String strItem = (String) mSpinner1.getSelectedItem();
                strItem = strItem.substring(0, 2);


                if ((str.isEmpty() == false) && (str.contains("@")) && (str.contains("."))) {
                    if (strpw.isEmpty() == false) {
                        if (strpw.equals(strpw2)) {
                            //##########Toast메시지로!! Key값 호츨?##########
                            Toast.makeText(getApplication(), str + " / " + strpw + " / " + strpw2 + " / " + strrd2 + " / " + strItem + " / ", Toast.LENGTH_LONG).show();
                            //submit_button -> Main1Activity화면으로 back
                            Intent intent = new Intent(getApplicationContext(), Main1Activity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplication(), "Pw does not match", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplication(), "Please enter Pw", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplication(), "Please enter a valid E-mail", Toast.LENGTH_LONG).show();
                }

                break;


            // cancel_button -> LoginActivity화면으로 back
            case R.id.cancel_button:
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.Relaitve1:
                // 키패드 밖 레이아웃 클릭하면 키패드 감추기
                RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.Relaitve1);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(relativeLayout.getWindowToken(), 0);
        }
    }*/
}
