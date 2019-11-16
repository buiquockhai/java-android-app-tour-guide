package com.example.myapplication;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class BuiQuocKhai extends AppCompatActivity {

    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;
    String sessionId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bui_quoc_khai);

        //Tab view function
        sessionId= getIntent().getStringExtra("key");
        TabView();
        ImageButton btn_home = (ImageButton) findViewById(R.id.btn_home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BuiQuocKhai.this,TrangChu.class));
            }
        });


    }

    public void TabView(){
        tabLayout = (TabLayout)findViewById(R.id.tablayout_id);
        appBarLayout = (AppBarLayout)findViewById(R.id.appbarid);
        viewPager = (ViewPager)findViewById(R.id.viewpager_id);

        Bundle bundle= new Bundle();
        bundle.putString("key",sessionId);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),bundle);

        //Add to Fragment list
        adapter.AddFragment(new FragmentList(), "Thông tin");
        adapter.AddFragment(new FagmentAnalyst(),"Thống kê");
        adapter.AddFragment(new FragmentImage(), "Hình ảnh");
        //Set up Adapter
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
