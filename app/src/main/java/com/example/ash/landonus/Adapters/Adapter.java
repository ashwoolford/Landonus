package com.example.ash.landonus.Adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.ash.landonus.FirstFragment;
import com.example.ash.landonus.SecondFragment;
import com.example.ash.landonus.SettingFragment;

/**
 * Created by ash on 5/17/2017.
 */

public class Adapter extends FragmentPagerAdapter {

    private String tabLevels[] = {"Home", "Dock Feed", "Dock List"};

    public Adapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new FirstFragment();
            case 1:
                return new SecondFragment();
            case 2:
                return new SettingFragment();
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return tabLevels.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "";
    }
}
