package tech.ntam.adminapp.view.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import tech.ntam.adminapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NurseCalendarFragment extends Fragment {


    public NurseCalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nurse_calendar, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(getActivity(), "NurseCalendarFragment", Toast.LENGTH_SHORT).show();
    }
}
