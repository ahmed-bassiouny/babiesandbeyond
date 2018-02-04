package tech.ntam.adminapp.view.fragments.nurse;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import tech.ntam.adminapp.R;
import tech.ntam.adminapp.api.RequestAndResponse;
import tech.ntam.adminapp.model.Service;
import tech.ntam.adminapp.model.Staff;
import tech.ntam.adminapp.model.User;
import tech.ntam.adminapp.view.adapter.NurseRequestItemAdapter;
import tech.ntam.mylibrary.MyDialog;
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;

/**
 * A simple {@link Fragment} subclass.
 */
public class NurseFragment extends Fragment {

    private static NurseFragment nurseFragment;
    private RecyclerView recyclerView;

    public static NurseFragment newInstance() {
        if (nurseFragment == null)
            nurseFragment = new NurseFragment();
        return nurseFragment;
    }

    public NurseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nurse, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        view.findViewById(R.id.btn_request).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        view.findViewById(R.id.btn_calendar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        fetchData();
    }
    private void fetchData(){
        final MyDialog myDialog = new MyDialog();
        myDialog.showMyDialog(getActivity());
        RequestAndResponse.getStaff(getContext(), User.NURSE, new BaseResponseInterface<Staff>() {
            @Override
            public void onSuccess(Staff staff) {
                NurseRequestItemAdapter adapter = new NurseRequestItemAdapter(getActivity(),true,staff.getServices());
                recyclerView.setAdapter(adapter);
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
