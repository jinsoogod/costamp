package com.hanium.costamp;

import android.app.Activity;
import android.content.Context;

import com.hanium.costamp.module.GlobalApplication;
import com.kakao.auth.ApprovalType;
import com.kakao.auth.AuthType;
import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.ISessionConfig;
import com.kakao.auth.KakaoAdapter;

// 카카오톡 API 사용을 위한 어댑터
// 최종 수정자 : 유재혁, 최종 수정 날짜 : 20160805 19:00
public class KakaoSDKAdapter extends KakaoAdapter
{
    @Override
    public ISessionConfig getSessionConfig()
    {
        return new ISessionConfig()
        {
            @Override
            public AuthType[] getAuthTypes()
            {
                return new AuthType[]{AuthType.KAKAO_LOGIN_ALL};
            }

            @Override
            public boolean isUsingWebviewTimer()
            {
                return false;
            }

            @Override
            public ApprovalType getApprovalType()
            {
                return ApprovalType.INDIVIDUAL;
            }

            @Override
            public boolean isSaveFormData()
            {
                return true;
            }
        };
    }

    @Override
    public IApplicationConfig getApplicationConfig()
    {
        return new IApplicationConfig()
        {
            @Override
            public Activity getTopActivity()
            {
                return GlobalApplication.getCurrentActivity();
            }

            @Override
            public Context getApplicationContext()
            {
                return GlobalApplication.getGlobalApplicationContext();
            }
        };
    }
}
