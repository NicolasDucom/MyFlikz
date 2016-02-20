package com.nicolasdu.MyFlikz;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.nicolasdu.MyFlikz.common.adapter.DrawerAdapter;
import com.nicolasdu.MyFlikz.common.adapter.ShowsPagerAdapter;
import com.nicolasdu.MyFlikz.fragment.ShowsListFragment;
import com.nicolasdu.MyFlikz.model.Show;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;


public class MainActivity extends AppCompatActivity implements ShowsListFragment.ShowListCallback {

    String TITLES[] = {"Profile","Favorites","Watch Later"};
    int ICONS[] = {R.drawable.icon_profile,R.drawable.icon_favorites,R.drawable.icon_later};

    String NAME = "John Doe";
    String EMAIL = "John@doe.com";
    int PROFILE = R.drawable.profile;
    private Toolbar toolbar;
    RecyclerView drawerRecyclerView;
    RecyclerView.Adapter drawerRecyclerViewAdapter;
    RecyclerView.LayoutManager drawerRecyclerViewLayoutManager;
    DrawerLayout drawerLayout;

    ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("In Theaters"));
        tabLayout.addTab(tabLayout.newTab().setText("Coming Soon"));
        tabLayout.addTab(tabLayout.newTab().setText("Top"));
        tabLayout.setTabGravity(tabLayout.GRAVITY_FILL);

        drawerRecyclerView = (RecyclerView) findViewById(R.id.drawer_recyclerView);
        drawerRecyclerView.setHasFixedSize(true);
        drawerRecyclerViewAdapter = new DrawerAdapter(TITLES,ICONS,NAME,EMAIL,PROFILE);

        drawerRecyclerView.setAdapter(drawerRecyclerViewAdapter);
        drawerRecyclerViewLayoutManager = new LinearLayoutManager(this);
        drawerRecyclerView.setLayoutManager(drawerRecyclerViewLayoutManager);
        drawerRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());
        drawerLayout = (DrawerLayout) findViewById(R.id.DrawerLayout);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_drawer,R.string.close_drawer) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        final ViewPager pager = (ViewPager) findViewById(R.id.shows_pager);
        final ShowsPagerAdapter showsPagerAdapter = new ShowsPagerAdapter(getSupportFragmentManager(),
                tabLayout.getTabCount());
        pager.setOffscreenPageLimit(showsPagerAdapter.getCount());
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onItemSelected(Show show) {
        Intent intent = new Intent(this, ShowDetailsActivity.class);
        intent.putExtra("show",show);
        startActivity(intent);
    }
}
