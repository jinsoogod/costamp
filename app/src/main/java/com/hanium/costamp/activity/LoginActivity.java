package com.hanium.costamp.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.hanium.costamp.LoginAddInfoActivity;
import com.hanium.costamp.LoginEmailActivity;
import com.hanium.costamp.Main1Activity;
import com.hanium.costamp.R;
import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;

import org.json.JSONException;
import org.json.JSONObject;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

// 카카오톡 로그인 추가
// 최종 수정자 : 유재혁, 최종 수정 날짜 : 20160805 19:30
// 서버 테스트용 버튼추가
// 최종 수정자 : 이은영, 최종 수정 날짜 : 20150715 02:20

public class LoginActivity extends Activity
{
    // 페이스북 로그인을 위한 변수
    private CallbackManager FaceBookCallBackManager = null;
    private AccessTokenTracker accessTokenTracker = null;
    private LoginButton btn_FBLoginOri; // 진짜 페이스북 로그인 API가 담긴 버튼
    private Button btn_FBLoginCustom; // 커스터마이징 페이스북 로그인 버튼

    // 카카오톡 로그인을 위한 변수
    private Button btn_KakaoLoginCustom; // 커스터마이징 카카오톡 로그인 버튼
    private SessionCallback KakaoCallBack;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 상단바 삭제
        FacebookSdk.sdkInitialize(getApplicationContext()); // 페이스북 SDK 초기화
        AppEventsLogger.activateApp(getApplication());
        setContentView(R.layout.login_activity);

        FaceBookCallBackManager = CallbackManager.Factory.create(); // 로그인 응답을 처리할 콜백 관리자

        // 원본 페이스북 버튼과 커스터마이징한 페이스북 버튼 선언
        btn_FBLoginOri = (LoginButton)findViewById(R.id.btn_FBLoginOri); // 진짜 페이스북 API가 있는 버튼
        btn_FBLoginCustom = (Button)findViewById(R.id.btn_FBLoginCustom); // 커스터마이징한 이미지가 있는 버튼

        // 원본 카카오톡 버튼과 커스터마이징한 카카오톡 버튼 선언
        btn_KakaoLoginCustom = (Button)findViewById(R.id.btn_KakaoLoginCustom); // 커스터마이징한 이미지가 있는 버튼
        btn_KakaoLoginCustom.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // 카카오 로그인 요청
                KakaoLogin();
            }
        });

        // 허가정보?
        List < String > permissionNeeds = Arrays.asList("user_photos", "email", "user_birthday", "public_profile", "AccessToken");
        btn_FBLoginOri.registerCallback(FaceBookCallBackManager, new FacebookCallback<LoginResult>()
        {
            // 성공했을때
            @Override
            public void onSuccess(LoginResult loginResult)
            {
                String accessToken = loginResult.getAccessToken().getToken();
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback()
                {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response)
                    {
                        try
                        {
                            String id = object.getString("id");

                            try
                            {
                                URL profile_pic = new URL("http://graph.facebook.com/" + id + "/picture?type=large");
                            }

                            catch (MalformedURLException e)
                            {
                                e.printStackTrace();
                            }
                            String name = object.getString("name");
                            String email = object.getString("email");
                            String gender = object.getString("gender");
                            String birthday = object.getString("birthday");
                        }

                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();

                // 액티비티 이동
                Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();

                // 웹 상으로 정보 저장
                Intent LoginToMain = new Intent(LoginActivity.this, LoginAddInfoActivity.class);
                startActivity(LoginToMain);
                finish();
            }

            @Override
            public void onCancel()
            {

            }

            @Override
            public void onError(FacebookException error)
            {

            }
        });
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }

    // 카카오 로그인 세션 제어
    private void KakaoLogin()
    {
        // 카카오 세션을 오픈한다.
        KakaoCallBack = new SessionCallback();
        com.kakao.auth.Session.getCurrentSession().addCallback(KakaoCallBack);
        com.kakao.auth.Session.getCurrentSession().checkAndImplicitOpen();
        com.kakao.auth.Session.getCurrentSession().open(AuthType.KAKAO_TALK_EXCLUDE_NATIVE_LOGIN, LoginActivity.this);
    }

    // 세션 콜백 매니저 선언
    private class SessionCallback implements ISessionCallback
    {
        @Override
        public void onSessionOpened()
        {
            Toast.makeText(getApplicationContext(), "세션 오픈(로그인 성공)", Toast.LENGTH_SHORT).show();

            // 사용자 정보를 가져옴, 회원가입 미가입시 자동 가입 시킴
            KakaoRequestMe();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception)
        {
            // 에러 메시지 출력
            if(exception != null)
            {
                Toast.makeText(getApplicationContext(), exception.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // 사용자의 상태를 알아보기 위하여 Me API 호출
    protected void KakaoRequestMe()
    {
        UserManagement.requestMe(new MeResponseCallback()
        {
            @Override
            public void onFailure(ErrorResult errorResult)
            {
                int ErrorCode = errorResult.getErrorCode();
                int ClientErrorCode = -777;

                if (ErrorCode == ClientErrorCode)
                {
                    Toast.makeText(getApplicationContext(), "카카오톡 서버의 네트워크가 불안정합니다. 잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    Toast.makeText(getApplicationContext(), "알 수 없는 오류로 인하여 카카오톡 로그인 실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult)
            {
                Toast.makeText(getApplicationContext(), " 오류로 카카오로그인 실패", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNotSignedUp()
            {
                // 자동 가입이 아닐 경우 동의창
                Toast.makeText(getApplicationContext(), "자동 가입이 아닙니다.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(UserProfile userProfile)
            {
                // 이 부분에서 액티비티 이동 걸면 될듯.
                Toast.makeText(getApplicationContext(), "로그인 완료", Toast.LENGTH_SHORT).show();
            }
        });
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
            case R.id.btn_NoLoginStart:
                Toast.makeText(getApplicationContext(),"로그인없이 둘러보기",Toast.LENGTH_LONG).show();
                intent = new Intent(getApplicationContext(),Main1Activity.class );
                startActivity(intent);
                break;
            case R.id.btn_FBLoginCustom:
                btn_FBLoginOri.performClick();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        FaceBookCallBackManager.onActivityResult(requestCode, resultCode, data);
    }

    // 뒤로 가기 버튼 누를 때 종료
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        switch (keyCode)
        {
            case KeyEvent.KEYCODE_BACK:
                android.app.AlertDialog.Builder QuitBuilder = new android.app.AlertDialog.Builder(this)
                        .setMessage("코스탬프(COSTAMP)를 종료하시겠습니까?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                android.os.Process.killProcess(android.os.Process.myPid());
                            }
                        })
                        .setNegativeButton("아니오", null);
                android.app.AlertDialog QuitDialog = QuitBuilder.create();
                QuitDialog.show();
                break;
        }
        return true;
    }
}