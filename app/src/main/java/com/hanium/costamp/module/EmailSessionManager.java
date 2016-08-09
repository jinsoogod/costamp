package com.hanium.costamp.module;

import android.content.Context;
import android.content.SharedPreferences;

// 이메일로 로그인 했을 때, 로그인 세션 관리를 도와줌.
// 최종 수정자 : 유재혁, 최종 수정 날짜 : 20160810 00:00
public class EmailSessionManager
{
    private static String TAG = EmailSessionManager.class.getSimpleName(); // 로그캣 출력을 위한 태그

    SharedPreferences EmailPref; // 세션 상태 저장을 위한 SharedPreferences 선언
    SharedPreferences.Editor EmailPrefEditor;
    Context EmailContext; // 시스템이 관리하고 있는 정보에 접근하기 위한 Context 객체 선언
}
