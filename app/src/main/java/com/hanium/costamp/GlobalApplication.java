package com.hanium.costamp;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.kakao.auth.KakaoSDK;

// (카카오) 캐시를 앱 수준에서 관리하기 위한 어플리케이션 객체이다.
// 최종 수정자 : 유재혁, 최종 수정 날짜 : 20160805 19:00
public class GlobalApplication extends Application
{
    private static GlobalApplication mInstance;
    private static volatile Activity currentActivity = null;

    public static Activity getCurrentActivity()
    {
        Log.d("TAG", "++ currentActivity : " + (currentActivity != null ? currentActivity.getClass().getSimpleName() : ""));
        return currentActivity;
    }

    public static void setCurrentActivity(Activity currentActivity)
    {
        GlobalApplication.currentActivity = currentActivity;
    }

    public static GlobalApplication getGlobalApplicationContext()
    {
        if(mInstance == null)
        {
            throw new IllegalStateException("this application does not inherit GlobalApplication");
        }
        return mInstance;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        mInstance = this;
        KakaoSDK.init(new KakaoSDKAdapter());
    }
}
