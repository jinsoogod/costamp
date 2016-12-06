package com.hanium.costamp.module;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;

// 이메일 회원가입 정보 송, 수신을 위한 SQLite 선언부
// 최종 수정자 : 유재혁, 최종 수정 날짜 : 20160809 16:20
public class EmailSQLiteHandler extends SQLiteOpenHelper
{
    private static final String TAG = EmailSQLiteHandler.class.getSimpleName();

    // 모든 정적 변수 선언
    // 데이터베이스 버젼
    private static final int EMAIL_MEMBER_DATABASE_VERSION = 1;

    // 데이터베이스 이름 지정
    private static final String DATABASE_NAME = "email_join_member";

    // 회원 정보 테이블 이름 지정
    private static final String TABLE_EMAIL_USER = "USER_EMAIL";

    // 각 테이블별 컬럼명 지정
    private static final String EMAIL_USER_NUMBER = "serial_number"; // 사용자 고유번호
    private static final String EMAIL_USER_ID = "id"; // 이메일(아이디)
    private static final String EMAIL_USER_PASSWORD = "uid"; // 사용자 비밀번호(추정)
    private static final String EMAIL_JOIN_DATE = "created_at"; // 가입날짜
    private static final String EMAIL_USER_GENDER = "gender"; // 성별
    private static final String EMAIL_USER_AGE = "age"; // 나이대

    // 생성자 선언
    public EmailSQLiteHandler(Context context)
    {
        super(context, DATABASE_NAME, null, EMAIL_MEMBER_DATABASE_VERSION);
    }

    // 테이블 생성
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_EMAIL_LOGIN_TABLE = "CREATE TABLE" + TABLE_EMAIL_USER + "(" + EMAIL_USER_NUMBER + " INTEGER PRIMARY KEY, "
                + EMAIL_USER_ID + " TEXT, " + EMAIL_USER_PASSWORD + " TEXT, " + EMAIL_JOIN_DATE + " TEXT, " + EMAIL_USER_GENDER
                + " TEXT, " + EMAIL_USER_AGE + " TEXT" + ")"; // 쿼리문을 작성하여 String 형태로 저장
        db.execSQL(CREATE_EMAIL_LOGIN_TABLE); // SELECT 명령을 제외한 모든 SQL 문장을 실행한다.
        Log.d(TAG, "데이터베이스 생성 완료"); // 로그캣에 메시지 출력
    }

    // 데이터베이스 업그레이드
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMAIL_USER); // 만약 테이블 내에 존재할 경우 오래된 거 삭제
        onCreate(db); // 테이블을 다시 한번 생성
    }

    // 유저 상세 정보를 데이터베이스에 저장함
    public void addEmailJoinUser(String serial_number, String id, String uid, String created_at, String gender, String age)
    {
        SQLiteDatabase EmailUserDB = this.getWritableDatabase(); // 읽고 쓰기용 DB 열기, SQLiteDatabase 반환
        ContentValues EmailUserValues = new ContentValues(); // 코드-> 데이터베이스로 자료를 입력하기 위한 객체 선언

        // 데이터베이스 자료 입력
        EmailUserValues.put(EMAIL_USER_NUMBER, serial_number); // 사용자 고유번호
        EmailUserValues.put(EMAIL_USER_ID, id); // 이메일(아이디)
        EmailUserValues.put(EMAIL_USER_PASSWORD, uid); // 사용자 비밀번호(추정)
        EmailUserValues.put(EMAIL_JOIN_DATE, created_at); // 가입날짜
        EmailUserValues.put(EMAIL_USER_GENDER, gender); // 성별
        EmailUserValues.put(EMAIL_USER_AGE, age); // 나이

        // 항목 삽입
        long UserID = EmailUserDB.insert(TABLE_EMAIL_USER, null, EmailUserValues);
        EmailUserDB.close(); // 데이터베이스 연결 종료

        // 로그값 출력
        Log.d(TAG, "새로운 " + UserID + "유저가 삽입되었습니다.");
    }

    // 유저 정보를 데이터베이스에서 불러옴
    // HashMap 형태로 데이터베이스 키 값과 Value를 같이 통제한다.
    public HashMap<String, String> getEmailJoinUserDetails()
    {
        HashMap<String, String> EmailJoinUser = new HashMap<String, String>();
        String selectEmailUserQuery = "SELECT * FROM " + TABLE_EMAIL_USER; // 유저명으로 쿼리문 저장

        SQLiteDatabase EmailUserDB = this.getReadableDatabase(); // 읽기 전용으로 DB를 불러온다.
        Cursor EmailUserCursor = EmailUserDB.rawQuery(selectEmailUserQuery, null); // Cursor : 일련의 데이터에 순차적으로 엑세스할 때 검색 및 현재 위치를 포함하는 데이터 요소

        EmailUserCursor.moveToFirst(); // 첫번째 실 데이터로 이동한다.

        // 커서가 참조할 수 있는 해당 테이블의 행의 개수가 존재한다면
        if (EmailUserCursor.getCount() > 0)
        {
            EmailJoinUser.put("serial_number", EmailUserCursor.getString(1)); // DB 테이블에 실제 데이터를 가지고 와서 USER 정보에 뿌려준다.
            EmailJoinUser.put("id", EmailUserCursor.getString(2));
            EmailJoinUser.put("uid", EmailUserCursor.getString(3));
            EmailJoinUser.put("created_at", EmailUserCursor.getString(4));
            EmailJoinUser.put("gender", EmailUserCursor.getString(5));
            EmailJoinUser.put("age", EmailUserCursor.getString(6));
        }

        EmailUserCursor.close(); // 커서를 닫는다. 과도한 쿼리 남발로 인하여 액티비티 사용성 문제가 발생할 수가 있으므로 적절하게 닫아야 함.
        EmailUserDB.close(); // DB 닫음.

        // 로그값을 통한 확인
        Log.d(TAG, "다음 유저 정보를 가지고 옵니다 : " + EmailJoinUser.toString());

        return EmailJoinUser;
    }

    // 유저 정보 테이블 삭제
    public void deleteEmailJoinUsers()
    {
        SQLiteDatabase EmailUserDB = this.getWritableDatabase();

        // 모든 데이터 삭제
        EmailUserDB.delete(TABLE_EMAIL_USER, null, null);
        EmailUserDB.close(); // 닫아줌

        Log.d(TAG, "저장되어 있는 유저 데이터 전부 삭제 완료.");
    }
}