package tech.ntam.adminapp.view.fragments.nurse;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tech.ntam.adminapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NurseHistoryFragment extends Fragment {


    public NurseHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history_calendar, container, false);
    }

}
