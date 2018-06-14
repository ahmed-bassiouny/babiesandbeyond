package tech.ntam.babiesandbeyond.view.fragments;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.view.activities.ArticleListActivity;
import tech.ntam.babiesandbeyond.view.activities.ContactActivity;
import tech.ntam.babiesandbeyond.view.activities.GetServiceQuotationActivity;
import tech.ntam.babiesandbeyond.view.activities.LoginOptionsActivity;
import tech.ntam.babiesandbeyond.view.activities.TermsActivity;
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.mylibrary.UserSharedPref;
import tech.ntam.babiesandbeyond.view.activities.AboutUsActivity;
import tech.ntam.babiesandbeyond.view.activities.SignIn_UpActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class OptionsFragment extends Fragment {

    private static final int REQUEST_PHONE_CALL = 2;
    private static OptionsFragment optionsFragment;

    public OptionsFragment() {
        // Required empty public constructor
    }

    public static OptionsFragment newInstance() {
        if (optionsFragment == null)
            optionsFragment = new OptionsFragment();
        return optionsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_options, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.linear_about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AboutUsActivity.class));
            }
        });
        view.findViewById(R.id.linear_contact_us).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ContactActivity.class));
            }
        });
        view.findViewById(R.id.linear_terms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), TermsActivity.class));
            }
        });
        view.findViewById(R.id.linear_articles).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ArticleListActivity.class));
            }
        });
        view.findViewById(R.id.linear_call_us).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                } else {
                    Intent in = new Intent(Intent.ACTION_CALL);
                    in.setData(Uri.parse("tel:" + "+971044308900"));
                    getContext().startActivity(in);                }
            }
        });
        view.findViewById(R.id.linear_get_service_quotation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), GetServiceQuotationActivity.class));
            }
        });
        view.findViewById(R.id.linear_logout).setOnClickListener(new View.OnClickListener() {
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
                                // continue with delete
                                RequestAndResponse.logout(getContext(), new BaseResponseInterface<String>() {
                                    @Override
                                    public void onSuccess(String s) {

                                    }

                                    @Override
                                    public void onFailed(String errorMessage) {
                                    }
                                });
                                UserSharedPref.clearShared(getContext());
                                FirebaseAuth.getInstance().signOut();
                                LoginManager.getInstance().logOut();
                                getActivity().finish();
                                startActivity(new Intent(getContext(), LoginOptionsActivity.class));
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
}
