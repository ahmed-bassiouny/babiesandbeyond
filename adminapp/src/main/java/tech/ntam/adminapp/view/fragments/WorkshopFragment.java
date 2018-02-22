package tech.ntam.adminapp.view.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import tech.ntam.adminapp.R;
import tech.ntam.adminapp.api.RequestAndResponse;
import tech.ntam.adminapp.interfaces.ParseWorkshop;
import tech.ntam.adminapp.model.Staff;
import tech.ntam.adminapp.model.User;
import tech.ntam.adminapp.model.Workshop;
import tech.ntam.adminapp.view.activities.WorkshopDetailsActivity;
import tech.ntam.adminapp.view.adapter.RequestItemAdapter;
import tech.ntam.adminapp.view.adapter.ServiceItemAdapter;
import tech.ntam.adminapp.view.adapter.WorkshopListItemAdapter;
import tech.ntam.mylibrary.IntentDataKey;
import tech.ntam.mylibrary.SimpleDividerItemDecoration;
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorkshopFragment extends Fragment implements ParseWorkshop {

    private static WorkshopFragment workshopFragment;
    private RecyclerView recyclerView;
    private ProgressBar progress;
    private TextView noInternet;
    private Button btnNoInternet;
    private WorkshopListItemAdapter workshopListItemAdapter;
    private RadioButton btnRequest;
    private RadioButton btnList;
    private SwipeRefreshLayout swipeRefreshLayout;

    public static WorkshopFragment newInstance() {
        if (workshopFragment == null)
            workshopFragment = new WorkshopFragment();
        return workshopFragment;
    }

    public WorkshopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_workshop, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view);
        progress = view.findViewById(R.id.progress);
        noInternet = view.findViewById(R.id.no_internet);
        btnNoInternet = view.findViewById(R.id.btn_no_internet);
        btnRequest = view.findViewById(R.id.btn_request);
        btnList = view.findViewById(R.id.btn_list);
        swipeRefreshLayout = view.findViewById(R.id.swpie_refresh_layout);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setVisibility(View.INVISIBLE);
            }
        });
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchWorkshopList();
            }
        });
        btnNoInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRequest.setChecked(true);
                fetchWorkshopList();
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                RequestAndResponse.getWorkshopLList(getContext(), new BaseResponseInterface<List<Workshop>>() {
                    @Override
                    public void onSuccess(List<Workshop> workshops) {
                        workshopListItemAdapter.updateWorkshops(workshops);
                        swipeRefreshLayout.setRefreshing(false);
                        btnList.setChecked(true);
                    }

                    @Override
                    public void onFailed(String errorMessage) {
                        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
        fetchWorkshopList();
    }

    private void fetchWorkshopList() {
        if (workshopListItemAdapter != null) {
            recyclerView.setAdapter(workshopListItemAdapter);
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            progress.setVisibility(View.VISIBLE);
            noInternet.setVisibility(View.INVISIBLE);
            btnNoInternet.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
            RequestAndResponse.getWorkshopLList(getContext(), new BaseResponseInterface<List<Workshop>>() {
                @Override
                public void onSuccess(List<Workshop> workshops) {
                    workshopListItemAdapter = new WorkshopListItemAdapter(WorkshopFragment.this, workshops);
                    recyclerView.setAdapter(workshopListItemAdapter);
                    progress.setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setEnabled(true);
                    btnList.setChecked(true);
                }

                @Override
                public void onFailed(String errorMessage) {
                    progress.setVisibility(View.INVISIBLE);
                    noInternet.setVisibility(View.VISIBLE);
                    btnNoInternet.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setEnabled(false);
                }
            });
        }
    }

    @Override
    public void viewWorkshop(Workshop workshop) {
        Intent i = new Intent(getActivity(), WorkshopDetailsActivity.class);
        i.putExtra(IntentDataKey.MY_WORKSHOP, workshop);
        startActivity(i);
    }
}
