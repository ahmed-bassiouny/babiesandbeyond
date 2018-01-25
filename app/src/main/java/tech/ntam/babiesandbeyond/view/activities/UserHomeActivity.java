package tech.ntam.babiesandbeyond.view.activities;

import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import java.lang.reflect.Field;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.utils.BottomNavigationViewHelper;
import tech.ntam.babiesandbeyond.view.fragments.OptionsFragment;
import tech.ntam.babiesandbeyond.view.fragments.UserAboutUsFragment;
import tech.ntam.babiesandbeyond.view.fragments.UserEventListFragment;
import tech.ntam.babiesandbeyond.view.fragments.UserEventsFragment;
import tech.ntam.babiesandbeyond.view.fragments.UserGroupsFragment;
import tech.ntam.babiesandbeyond.view.fragments.UserServiceFragment;
import tech.ntam.babiesandbeyond.view.fragments.UserServiceListFragment;
import tech.ntam.babiesandbeyond.view.fragments.UserWorkshopFragment;
import tech.ntam.babiesandbeyond.view.fragments.UserWorkshopListFragment;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;

public class UserHomeActivity extends MyToolbar{

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private BottomNavigationView bottomNavigation;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById();
        onClick();
        setupToolbar(this, true, false, true);
        tvTitle.setText(R.string.services);
        initObject();
    }

    private void initObject() {
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(0);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigation);
    }
    private void onClick() {
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.service:
                        mViewPager.setCurrentItem(0);
                        tvTitle.setText(R.string.services);
                        break;
                    case R.id.event:
                        mViewPager.setCurrentItem(1);
                        tvTitle.setText(R.string.events);
                        break;
                    case R.id.workshop:
                        mViewPager.setCurrentItem(2);
                        tvTitle.setText(R.string.workshop);
                        break;
                    case R.id.message:
                        mViewPager.setCurrentItem(3);
                        tvTitle.setText(R.string.groups);
                        break;
                    case R.id.more:
                        mViewPager.setCurrentItem(4);
                        tvTitle.setText(R.string.more);
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
                switch (position){
                    case 0:
                        tvTitle.setText(R.string.services);
                        break;
                    case 1:
                        tvTitle.setText(R.string.events);
                        break;
                    case 2:
                        tvTitle.setText(R.string.workshop);
                        break;
                    case 3:
                        tvTitle.setText(R.string.groups);
                        break;
                    case 4:
                        tvTitle.setText(R.string.more);
                        break;
                }
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
                    return UserServiceListFragment.newInstance();
                case 1:
                    return UserEventListFragment.newInstance();
                case 2:
                    return UserWorkshopListFragment.newInstance();
                case 3:
                    return UserGroupsFragment.newInstance();
                case 4:
                    return OptionsFragment.newInstance();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 5;
        }
    }
}
