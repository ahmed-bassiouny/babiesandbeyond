package tech.ntam.adminapp.view.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import tech.ntam.adminapp.R;
import tech.ntam.adminapp.api.RequestAndResponse;
import tech.ntam.adminapp.model.Staff;
import tech.ntam.adminapp.model.User;
import tech.ntam.adminapp.view.adapter.RequestItemAdapter;
import tech.ntam.adminapp.view.adapter.ServiceItemAdapter;
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;

/**
 * A simple {@link Fragment} subclass.
 */
public class BabysitterFragment extends Fragment {

    private static BabysitterFragment nurseFragment;
    private RecyclerView recyclerView;
    private ProgressBar progress;
    private TextView noInternet;
    private Button btnNoInternet;
    private Staff myStaff;
    private ServiceItemAdapter serviceItemAdapter;
    private RequestItemAdapter requestItemAdapter;

    public static BabysitterFragment newInstance() {
        if (nurseFragment == null)
            nurseFragment = new BabysitterFragment();
        return nurseFragment;
    }

    public BabysitterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_babysitter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view);
        progress = view.findViewById(R.id.progress);
        noInternet = view.findViewById(R.id.no_internet);
        btnNoInternet = view.findViewById(R.id.btn_no_internet);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        view.findViewById(R.id.btn_request).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myStaff == null)
                    return;
                if (requestItemAdapter == null)
                    requestItemAdapter = new RequestItemAdapter(getActivity(), myStaff.getRequests());
                recyclerView.setAdapter(requestItemAdapter);
            }
        });
        view.findViewById(R.id.btn_calendar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myStaff == null)
                    return;
                if (serviceItemAdapter == null)
                    serviceItemAdapter = new ServiceItemAdapter(getActivity(), myStaff.getServices());
                recyclerView.setAdapter(serviceItemAdapter);
            }
        });
        btnNoInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchData();
            }
        });
        fetchData();
    }

    private void fetchData() {
        if(myStaff != null)
            return;
        progress.setVisibility(View.VISIBLE);
        noInternet.setVisibility(View.INVISIBLE);
        btnNoInternet.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        RequestAndResponse.getStaff(getContext(), User.BABYSITTER, new BaseResponseInterface<Staff>() {
            @Override
            public void onSuccess(Staff staff) {
                myStaff = staff;
                requestItemAdapter = new RequestItemAdapter(getActivity(), staff.getRequests());
                recyclerView.setAdapter(requestItemAdapter);
                progress.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailed(String errorMessage) {
                progress.setVisibility(View.INVISIBLE);
                noInternet.setVisibility(View.VISIBLE);
                btnNoInternet.setVisibility(View.VISIBLE);
            }
        });
    }
}
