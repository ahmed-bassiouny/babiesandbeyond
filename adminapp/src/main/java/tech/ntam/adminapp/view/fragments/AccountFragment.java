package tech.ntam.adminapp.view.fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import tech.ntam.adminapp.R;
import tech.ntam.adminapp.view.activities.SignInActivity;
import tech.ntam.mylibrary.UserSharedPref;
import tech.ntam.mylibrary.Utils;
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {


    private static AccountFragment accountFragment;
    private EditText etName;
    private EditText etPhone;
    private EditText etEmail;
    private ImageView ivProfilePhoto;


    public AccountFragment() {
        // Required empty public constructor
    }

    public static AccountFragment newInstance() {
        if (accountFragment == null)
            accountFragment = new AccountFragment();
        return accountFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewById(view);
        setData();
        view.findViewById(R.id.btn_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogStyle);
                } else {
                    builder = new AlertDialog.Builder(getContext());
                }
                builder.setMessage("Are you sure you want to Logout?")
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                UserSharedPref.clearShared(getContext());
                                getActivity().finish();
                                startActivity(new Intent(getContext(), SignInActivity.class));
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }

    private void setData() {
        etName.setText(UserSharedPref.getName(getContext()));
        etPhone.setText(UserSharedPref.getPhone(getContext()));
        etEmail.setText(UserSharedPref.getEmail(getContext()));
        Utils.MyGlide(getActivity(),ivProfilePhoto,UserSharedPref.getPhoto(getContext()));
    }

    private void findViewById(View view) {
        etName = view.findViewById(R.id.et_name);
        etPhone = view.findViewById(R.id.et_phone);
        etEmail = view.findViewById(R.id.et_email);
        ivProfilePhoto = view.findViewById(R.id.iv_profile_photo);
    }
}
