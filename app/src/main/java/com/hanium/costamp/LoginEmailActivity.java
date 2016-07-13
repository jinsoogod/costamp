package com.hanium.costamp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

// E-mail로 회원 가입 화면
// 최종 수정자 : 표영은, 최종 수정 날짜 : 20160712 01:20

public class LoginEmailActivity extends AppCompatActivity {
    EditText mEditEmail;
    EditText mEditPassword;
    EditText mEditRePassword;
    RadioGroup mRadioGender;
    Spinner mSpinner1;
    ArrayList<String> mArGeneral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email);

        //입력 정보를 각각 멤버변수에 저장
        mEditEmail = (EditText) findViewById(R.id.editText);
        mEditPassword = (EditText) findViewById(R.id.editText2);
        mEditRePassword = (EditText) findViewById(R.id.editText3);
        mRadioGender = (RadioGroup) findViewById(R.id.radioGroupGender);
        mSpinner1 = (Spinner) findViewById(R.id.spinnerAge);
        initSpinner();

    }


    public void initSpinner() {
        String[] strTextList = {"10대 이하", "20대", "30대", "40대",
                "50대", "60대 이상"};
        // ArrayList 배열 객체 생성하고 6개의 텍스트 데이터를 입력
        mArGeneral = new ArrayList<String>();
        for (int i = 0; i < 6; i++)
            mArGeneral.add(strTextList[i]);

        // 어댑터 객체를 생성하고 ArrayList 를 지정
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, mArGeneral);
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        // ListView 위젯의 핸들을 구해서 멤버변수에 저장
        mSpinner1 = (Spinner) findViewById(R.id.spinnerAge);
        // ListView 에 어댑터를 지정
        mSpinner1.setAdapter(adapter);


    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_button:
                String str = mEditEmail.getText().toString();
                String strpw = mEditPassword.getText().toString();
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
                        } else {
                            Toast.makeText(getApplication(), "Pw does not match", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplication(), "Please enter Pw", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplication(), "Please enter a valid E-mail", Toast.LENGTH_LONG).show();
                }

                //submit_button -> Main1Activity화면으로 back
                Intent intent = new Intent(getApplicationContext(), Main1Activity.class);
                startActivity(intent);
                break;


            // cancel_button -> LoginActivity화면으로 back
            case R.id.cancel_button:
                intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.Relaitve1:
                // 키패드 밖 레이아웃 클릭하면 키패드 감추기
                RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.Relaitve1);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(relativeLayout.getWindowToken(), 0);
        }

    }
}
