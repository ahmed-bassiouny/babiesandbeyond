package tech.ntam.babiesandbeyond.view.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserAboutUsFragment extends Fragment {

    public static UserAboutUsFragment userAboutUsFragment;

    public static UserAboutUsFragment newInstance() {
        if (userAboutUsFragment == null) {
            userAboutUsFragment = new UserAboutUsFragment();
        }
        return userAboutUsFragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getContext()!=null && isVisibleToUser){
            MyToolbar.TitleToolbar titleToolbar = (MyToolbar.TitleToolbar) getActivity();
            titleToolbar.setTitleToolbar(getString(R.string.about_us));
        }
    }
    public UserAboutUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_about_us, container, false);
    }

}
