package com.example.ash.landonus;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.ash.landonus.Adapters.Adapter;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton fbutton;
    private String[] tabsTitles = {"Home", "Chart", "Setting"};
    private Realm realm;
    private int tabIcons [] = {R.drawable.ic_home_white_24dp, R.drawable.ic_show_chart_white_24dp, R.drawable.ic_settings_white_24dp};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing views

        Realm.init(getApplicationContext());

        realm = Realm.getDefaultInstance();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        fbutton = (FloatingActionButton) findViewById(R.id.fb);

        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);
        tabLayout.setSelectedTabIndicatorHeight(9);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.white));

        viewPager.setAdapter(new Adapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);



        //initializing methods

        settingUpTitle();
        setupTabIcons();
        fabClickHandler();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.removeAllChangeListeners();

    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);

        tabLayout.getTabAt(0).getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(1).getIcon().setColorFilter(Color.parseColor("#90CAF9"), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(2).getIcon().setColorFilter(Color.parseColor("#90CAF9"), PorterDuff.Mode.SRC_IN);

       // toolbar.setTitle(tabsTitles[0]);


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
                int pos = tab.getPosition();




            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.parseColor("#90CAF9"), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void settingUpTitle(){


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {
                toolbar.setTitle(tabsTitles[position]);
                switch (position){
                    case 0:
                        fbutton.show();
                        break;
                    case 1:
                        fbutton.hide();
                        break;
                    case 2:
                        fbutton.hide();
                        break;
                    default:
                        fbutton.hide();
                }



            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void fabClickHandler(){

        fbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddPoperty.class);
                //intent.putExtra("key", "value");
                startActivity(intent);
            }
        });

    }



}
