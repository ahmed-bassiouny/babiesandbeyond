package tech.ntam.babiesandbeyond.view.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.mylibrary.MyDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserAboutUsFragment extends Fragment {

    public static UserAboutUsFragment userAboutUsFragment;
    private TextView tvAbout;
    private String about = "";
    private boolean isViewShown = false;

    public static UserAboutUsFragment newInstance() {
        if (userAboutUsFragment == null) {
            userAboutUsFragment = new UserAboutUsFragment();
        }
        return userAboutUsFragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getView() != null) {
            isViewShown = true;
            loadAbout();
        } else {
            isViewShown = false;
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvAbout = view.findViewById(R.id.tv_about);
        Toast.makeText(getActivity(), "fsdf", Toast.LENGTH_SHORT).show();
        if (!isViewShown) {
            loadAbout();
        }
    }

    private void loadAbout() {
        // check if about string is empty that mean i don't load data from backend
        // if about string not empty that mean i loaded data so i set it in string
        if (!about.isEmpty()) {
            tvAbout.setText(about);
            return;
        }
        final MyDialog myDialog = new MyDialog();
        myDialog.showMyDialog(getActivity());
        RequestAndResponse.getAbout(new BaseResponseInterface<String>() {
            @Override
            public void onSuccess(String s) {
                about = s;
                tvAbout.setText(about);
                myDialog.dismissMyDialog();
            }

            @Override
            public void onFailed(String errorMessage) {
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                myDialog.dismissMyDialog();
            }
        });
    }

}
