package com.nicolasdu.MyFlikz.common.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.nicolasdu.MyFlikz.fragment.ShowsListFragment;
import com.nicolasdu.MyFlikz.showsFilter;

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
        Bundle bundle = new Bundle();
        ShowsListFragment showsListFragment = new ShowsListFragment();
        switch (pos) {
            case 0:
                bundle.putSerializable("filter", showsFilter.IN_THEATERS);
                showsListFragment.setArguments(bundle);
                return showsListFragment;
            case 1:
                bundle.putSerializable("filter", showsFilter.COMING_SOON);
                showsListFragment.setArguments(bundle);
                return showsListFragment;
            case 2:
                bundle.putSerializable("filter", showsFilter.TOP);
                showsListFragment.setArguments(bundle);
                return showsListFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return this.numTabs;
    }

}
