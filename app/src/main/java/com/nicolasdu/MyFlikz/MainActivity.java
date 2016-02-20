package com.nicolasdu.MyFlikz;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.nicolasdu.MyFlikz.common.adapter.ShowsPagerAdapter;
import com.nicolasdu.MyFlikz.fragment.ShowsListFragment;
import com.nicolasdu.MyFlikz.model.Show;


public class MainActivity extends AppCompatActivity implements ShowsListFragment.ShowListCallback{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("In Theaters"));
        tabLayout.addTab(tabLayout.newTab().setText("Coming Soon"));
        tabLayout.addTab(tabLayout.newTab().setText("Top"));
        tabLayout.setTabGravity(tabLayout.GRAVITY_FILL);

        final ViewPager pager = (ViewPager) findViewById(R.id.shows_pager);
        final ShowsPagerAdapter showsPagerAdapter = new ShowsPagerAdapter(getSupportFragmentManager(),
                tabLayout.getTabCount());
        pager.setOffscreenPageLimit(showsPagerAdapter.getCount());
        System.out.println("TAB COUNT : "+tabLayout.getTabCount());
        pager.setAdapter(showsPagerAdapter);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    @Override
    public void onItemSelected(Show show) {
        Intent intent = new Intent(this, ShowDetailsActivity.class);
        intent.putExtra("show",show);
        startActivity(intent);
    }
}
