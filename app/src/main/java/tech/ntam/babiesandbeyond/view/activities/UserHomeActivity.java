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
import android.widget.Toast;

import java.util.List;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.api.api_model.response.EventsResponse;
import tech.ntam.babiesandbeyond.api.config.BaseResponseInterface;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.database.EventsDatabase;
import tech.ntam.babiesandbeyond.database.GroupsDatabase;
import tech.ntam.babiesandbeyond.database.ServiceDatabase;
import tech.ntam.babiesandbeyond.database.ServiceTypeDatabase;
import tech.ntam.babiesandbeyond.model.Event;
import tech.ntam.babiesandbeyond.model.Group;
import tech.ntam.babiesandbeyond.model.UserService;
import tech.ntam.babiesandbeyond.view.dialog.MyDialog;
import tech.ntam.babiesandbeyond.view.fragments.UserAboutUsFragment;
import tech.ntam.babiesandbeyond.view.fragments.UserEventsFragment;
import tech.ntam.babiesandbeyond.view.fragments.UserGroupsFragment;
import tech.ntam.babiesandbeyond.view.fragments.UserServiceFragment;
import tech.ntam.babiesandbeyond.view.fragments.UserWorkshopFragment;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;

public class UserHomeActivity extends MyToolbar implements MyToolbar.TitleToolbar {

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
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadEvents();
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
        if (tvTitle != null && title != null)
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
                case 4:
                    return UserAboutUsFragment.newInstance();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 5;
        }
    }

    private void loadEvents() {
        MyDialog.showMyDialog(this);
        RequestAndResponse.getEvents(this, new BaseResponseInterface<List<Event>>() {
            @Override
            public void onSuccess(List<Event> events) {
                EventsDatabase.setEvents(events);
            }

            @Override
            public void onFailed(String errorMessage) {
            }
        });
        RequestAndResponse.getGroups(this, new BaseResponseInterface<List<Group>>() {
            @Override
            public void onSuccess(List<Group> groups) {
                GroupsDatabase.setGroups(groups);
            }

            @Override
            public void onFailed(String errorMessage) {
                Toast.makeText(UserHomeActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
        RequestAndResponse.getMyService(this, new BaseResponseInterface<UserService>() {
            @Override
            public void onSuccess(UserService userService) {
                if (userService != null) {
                    ServiceTypeDatabase.setServiceTypes(userService.getServiceTypes());
                    ServiceDatabase.setServices(userService.getServices());
                    MyDialog.dismissMyDialog();
                    initObject();
                }
            }

            @Override
            public void onFailed(String errorMessage) {
                MyDialog.dismissMyDialog();
                Toast.makeText(UserHomeActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
