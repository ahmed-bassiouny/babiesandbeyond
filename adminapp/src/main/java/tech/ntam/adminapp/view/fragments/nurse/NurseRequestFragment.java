package tech.ntam.adminapp.view.fragments.nurse;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Toast;

import tech.ntam.adminapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NurseRequestFragment extends Fragment {

    private Spinner spinner;
    private RecyclerView recycleView;

    private static NurseRequestFragment nurseRequestFragment;

    public NurseRequestFragment() {
        // Required empty public constructor
    }

    public static NurseRequestFragment newInstance() {
        if (nurseRequestFragment == null)
            nurseRequestFragment = new NurseRequestFragment();
        return nurseRequestFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nurse_request, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinner = view.findViewById(R.id.spinner);
        recycleView = view.findViewById(R.id.recycle_view);
    }
}
