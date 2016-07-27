package com.hanium.costamp;

//최종작업일자 20160728 02:05
//최종작업자 : 이은영

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Main1Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //Main Fragment탭에 대한 버튼을 받을 멤버변수
    FragmentManager manager;
    FragmentTransaction trans;
//    FragmentTransaction trans;

    Fragment1 f1;
    Fragment2 f2 = new Fragment2();
    Fragment3 f3 = new Fragment3();
    Fragment4 f4 = new Fragment4();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        //GCM서버에 등록하는 REGISTATIONINTENTSERVICE
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);

        Button btn_a = (Button) findViewById(R.id.btnCostamp);
        Button btn_b = (Button) findViewById(R.id.btnCourse);
        Button btn_c = (Button) findViewById(R.id.btnRanking);
        Button btn_d = (Button) findViewById(R.id.btnPhoto);

        //프래그먼트를 제어하기 위해서는 FragmentManager를 사용해야한다.
        manager = getFragmentManager();

        f1 = (Fragment1) manager.findFragmentById(R.id.frg1);

        btn_a.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                //fragment1호출
                trans = manager.beginTransaction();
                trans.addToBackStack(null);
                trans.replace(R.id.frg1, f1);
                trans.commit();
            }
        });
        btn_b.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                //fragment2호출
                trans = manager.beginTransaction();
                trans.addToBackStack(null);
                trans.replace(R.id.frg1, f2);
                trans.commit();

            }
        });
        btn_c.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                //fragment3호출
                trans = manager.beginTransaction();
                trans.addToBackStack(null);
                trans.replace(R.id.frg1, f3);
                trans.commit();
            }
        });
        btn_d.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                //fragment4호출
                trans = manager.beginTransaction();
                trans.addToBackStack(null);
                trans.replace(R.id.frg1, f4);
                trans.commit();
            }
        });


        //네비게이션 드로우 onCreate
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener()
//
//                               {
//                                   @Override
//                                   public void onClick(View view) {
//                                       Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                                               .setAction("Action", null).show();
//                                   }
//                               }
//
//        );

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    // 네비게이션 드로우
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_trip) {
            // Handle the camera action
        } else if (id == R.id.nav_course) {

        } else if (id == R.id.nav_revise) {

        } else if (id == R.id.nav_help) {

        } else if (id == R.id.nav_inquiry) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
