package com.nicolasdu.MyFlikz.common.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.nicolasdu.MyFlikz.fragment.ComingSoonFragment;
import com.nicolasdu.MyFlikz.fragment.InTheatersFragment;
import com.nicolasdu.MyFlikz.fragment.TopFragment;

/**
 * Created by Nicolas on 2/17/2016.
 */
public class ShowsPagerAdapter extends FragmentPagerAdapter {

    private int numTabs;

    public ShowsPagerAdapter(FragmentManager fm, int numTabs) {
        super(fm);
        this.numTabs = numTabs;
    }

    @Override
    public Fragment getItem(int pos) {
        switch (pos) {
            case 0:
                return new InTheatersFragment();
            case 1:
                return new ComingSoonFragment();
            case 2:
                return new TopFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return this.numTabs;
    }

}
