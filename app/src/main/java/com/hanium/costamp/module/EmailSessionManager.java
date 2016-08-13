package com.hanium.costamp.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

// 이메일로 로그인 했을 때, 로그인 세션 관리를 도와줌.
// 최종 수정자 : 유재혁, 최종 수정 날짜 : 20160813 13:51
public class EmailSessionManager
{
    private static String TAG = EmailSessionManager.class.getSimpleName(); // 로그캣 출력을 위한 태그

    SharedPreferences EmailPref; // 세션 상태 저장을 위한 SharedPreferences 선언
    SharedPreferences.Editor EmailPrefEditor;
    Context EmailContext; // 시스템이 관리하고 있는 정보에 접근하기 위한 Context 객체 선언

    int PRIVATE_MODE = 0; // SharedPreferences 모드 초기화

    // SharedPreferences 이름들
    private static final String PREF_NAME = "EmailSessionLogin";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";

    // 생성자 설정
    public EmailSessionManager(Context context)
    {
        this.EmailContext = context;
        EmailPref = EmailContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        EmailPrefEditor = EmailPref.edit();
    }

    // 로그인 세팅 메소드
    public void setLogin(boolean isLoggedIn)
    {
        EmailPrefEditor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        EmailPrefEditor.commit(); // 커밋 체인지
        Log.d(TAG, "User login session modified!");
    }

    // 로그인 확인 여부 체크하는 메소드
    public boolean isLoggedIn()
    {
        return EmailPref.getBoolean(KEY_IS_LOGGED_IN, false);
    }
}