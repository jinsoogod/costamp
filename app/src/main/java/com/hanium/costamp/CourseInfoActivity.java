package com.hanium.costamp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by YEP on 2016-08-05.
 */
public class CourseInfoActivity extends AppCompatActivity {
    TextView tv_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_info);
        Intent intent = getIntent();

        String info1 = intent.getExtras().getString("info1");
        String info2 = intent.getExtras().getString("info2");
        String info3 = intent.getExtras().getString("info3");
        String like = intent.getExtras().getString("like");
        tv_test = (TextView) findViewById(R.id.textView7);

        tv_test.setText("intent1"+info1+"intent2"+info2+"intent3"+info3);
    }

}