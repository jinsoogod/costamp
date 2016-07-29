package com.hanium.costamp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

// 로그인 & 회원 가입 화면
// 최종 수정자 : 표영은, 최종 수정 날짜 : 20160714 16:01
//서버 테스트용 버튼추가
// 최종 수정자 : 이은영, 최종 수정 날짜 : 20150715 02:20

public class LoginActivity extends Activity
{
    // 페이스북 로그인을 위한 변수
    private CallbackManager callbackManager = null;
    private AccessTokenTracker accessTokenTracker = null;
    private LoginButton loginButton = null;

    // 로그인 결과를 가져오는 callback 메소드
    private FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>()
    {
        // 로그인에 성공할 경우
        @Override
        public void onSuccess(LoginResult loginResult)
        {
            // 로그인을 할 때 사용자별 발급되는 토큰 - 서버로 넘기기 용이하기 위함.
            AccessToken accessToken = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile(); // 사용자 프로필 추출

            // 로그인 테스트 메시지 출력
            Toast.makeText(getApplicationContext(), loginResult.getAccessToken().getUserId(), Toast.LENGTH_SHORT).show();

            // 로그인 완료시 메인 화면으로 이동됨.
            Intent LoginToMain = new Intent(LoginActivity.this, Main1Activity.class);
            startActivity(LoginToMain);
            finish();
        }

        @Override
        public void onCancel()
        {
            Toast.makeText(getApplicationContext(), "User Sign in Canceled!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(FacebookException error)
        {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 상단바 삭제
        FacebookSdk.sdkInitialize(getApplicationContext()); // 페이스북 SDK 초기화
        setContentView(R.layout.login_activity);

        callbackManager = CallbackManager.Factory.create(); // 로그인 응답을 처리할 콜백 관리자
        accessTokenTracker = new AccessTokenTracker()
        {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken)
            {
                // 현재 앱 코드 출력
                Toast.makeText(getApplicationContext(), currentAccessToken + "", Toast.LENGTH_SHORT).show();
            }
        };
        accessTokenTracker.startTracking();

        // 페이스북 로그인 버튼 제어
        LoginButton loginButton = (LoginButton)findViewById(R.id.btn_FacebookLogin);
        loginButton.setReadPermissions("public_profile", "user_friends");
        loginButton.registerCallback(callbackManager, callback);

    }

    @Override
    protected void onStop()
    {
        super.onStop();
        accessTokenTracker.stopTracking();
    }

    //onClick 메소드 : 카톡, 페북, 이메일, 로그인없이 로그인
    public void onClick(View v)
    {
        switch (v.getId())
        {
            //E-mail로 가입하기 : LoginEmailActivity실행
            case R.id.btn_EmailLogin:
                Intent intent = new Intent(getApplicationContext(),LoginEmailActivity.class );
                startActivity(intent);
                break;
            case R.id.btn_login:
                intent = new Intent(getApplicationContext(),Main1Activity.class );
                startActivity(intent);
                break;
            case R.id.btn_NoLoginStart:
                Toast.makeText(getApplicationContext(),"로그인없이 둘러보기",Toast.LENGTH_LONG).show();
                intent = new Intent(getApplicationContext(),Main1Activity.class );
                startActivity(intent);
                break;

        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}