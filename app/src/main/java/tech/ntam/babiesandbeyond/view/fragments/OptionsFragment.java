package tech.ntam.babiesandbeyond.view.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.mylibrary.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class OptionsFragment extends Fragment {

    private static OptionsFragment optionsFragment;
    private LinearLayout linearAbout,linearLogout;
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
        linearAbout = view.findViewById(R.id.linear_about);
        linearLogout = view.findViewById(R.id.linear_logout);
        linearAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.goToFragment(R.id.container,getActivity(),UserAboutUsFragment.newInstance(),true,null);

            }
        });
    }
}
