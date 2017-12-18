package tech.ntam.babiesandbeyond.view.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tech.ntam.babiesandbeyond.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceFragment extends Fragment {

    private static ServiceFragment serviceFragment;

    public ServiceFragment() {
        // Required empty public constructor
    }

    public static ServiceFragment  newInstance(){
        if(serviceFragment == null)
            serviceFragment = new ServiceFragment();
        return serviceFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_service, container, false);
    }

}
