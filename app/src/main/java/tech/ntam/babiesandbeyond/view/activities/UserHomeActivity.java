package tech.ntam.babiesandbeyond.view.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.view.fragments.UserEventsFragment;
import tech.ntam.babiesandbeyond.view.fragments.UserGroupsFragment;
import tech.ntam.babiesandbeyond.view.fragments.UserServiceFragment;
import tech.ntam.babiesandbeyond.view.fragments.UserWorkshopFragment;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;

public class UserHomeActivity extends MyToolbar implements MyToolbar.TitleToolbar{

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private BottomNavigationView bottomNavigation;
    private ViewPager mViewPager;
    static TextView v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById();
        initObject();
        onClick();
        setupToolbar(this,true,false);
    }

    private void initObject() {
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    private void onClick() {
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.service:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.event:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.workshop:
                        mViewPager.setCurrentItem(2);
                        break;
                    case R.id.message:
                        mViewPager.setCurrentItem(3);
                        break;
                    case R.id.about_us:
                        mViewPager.setCurrentItem(4);
                        break;
                }
                return true;
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bottomNavigation.getMenu().getItem(position).setChecked(true);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void findViewById() {
        mViewPager = findViewById(R.id.container);
        bottomNavigation = findViewById(R.id.bottom_navigation);
    }

    @Override
    public void setTitleToolbar(String title) {
        tvTitle.setText(title);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return UserServiceFragment.newInstance();
                case 1:
                    return UserEventsFragment.newInstance();
                case 2:
                    return UserWorkshopFragment.newInstance();
                case 3:
                    return UserGroupsFragment.newInstance();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
