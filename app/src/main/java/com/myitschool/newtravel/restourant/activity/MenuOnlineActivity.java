package com.myitschool.newtravel.restourant.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toolbar;

import com.myitschool.newtravel.R;

import java.util.ArrayList;
import java.util.List;

public class MenuOnlineActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private DrawerLayout drawerLayout;

    private ImageView imageMenu;
    private Toolbar toolbarM;
    private TabLayout menuItem;
    private ViewPager tab_pager;
    private String Pizza;
    private String Sushi;
    private String Zakuski;
    private String Salat;
    private String Soup;
    private String Meat;
    private String Fish;
    private String Sous;
    private String Desert;
    private String Drinks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_online);

        ActionBar ab=getSupportActionBar();

        viewPager=(ViewPager)findViewById(R.id.tab_pager);

        menuItem=(TabLayout)findViewById(R.id.menuItem);
//        menuItem.setupWithViewPager(viewPager);

//        menuItem.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });


        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            Pizza = extras.getString("Pizza");
            Sushi = extras.getString("Sushi");
            Zakuski=extras.getString("Zakuski");
            Salat=extras.getString("Salat");
            Soup=extras.getString("Soup");
            Meat=extras.getString("Meat");
            Fish=extras.getString("Fish");
            Sous=extras.getString("Sous");
            Desert = extras.getString("Desert");
            Drinks = extras.getString("Drinks");
        }
    }

    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.add(Pizza);
        adapter.add(Sushi);
        adapter.add(Zakuski);
        adapter.add(Salat);
        adapter.add(Soup);
        adapter.add(Meat);
        adapter.add(Fish);
        adapter.add(Sous);
        adapter.add(Desert);
        adapter.add(Drinks);
        viewPager.setAdapter(adapter);
    }



    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager){
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return null;
        }

        public void add(String title){
            mFragmentTitleList.add(title);
        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public CharSequence getPageTitle(int position){
            return mFragmentTitleList.get(position);
        }
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

        switch (id){
            case android.R.id.home:
                if (drawerLayout.isDrawerOpen(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }

                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
