package tech.ntam.adminapp.view.fragments;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
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
import tech.ntam.adminapp.model.Request;
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

    private final int REQUEST_CODE_WORKSHOP = 3;
    private static WorkshopFragment workshopFragment;
    private RecyclerView recyclerView;
    private ProgressBar progress;
    private TextView noInternet;
    private Button btnNoInternet;
    private WorkshopListItemAdapter listAdapter;
    private WorkshopListItemAdapter requestAdapter;
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
                fetchWorkshopRequest();
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
                if (btnRequest.isChecked()) {
                    fetchWorkshopRequest();
                } else {
                    fetchWorkshopList();
                }

            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (btnRequest.isChecked()) {
                    // get requests
                    RequestAndResponse.getWorkshopLRequest(getContext(), new BaseResponseInterface<List<Workshop>>() {
                        @Override
                        public void onSuccess(List<Workshop> workshops) {
                            requestAdapter.updateWorkshops(workshops);
                            swipeRefreshLayout.setRefreshing(false);
                        }

                        @Override
                        public void onFailed(String errorMessage) {
                            Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });
                } else {
                    // get lists
                    RequestAndResponse.getWorkshopLList(getContext(), new BaseResponseInterface<List<Workshop>>() {
                        @Override
                        public void onSuccess(List<Workshop> workshops) {
                            listAdapter.updateWorkshops(workshops);
                            swipeRefreshLayout.setRefreshing(false);
                        }

                        @Override
                        public void onFailed(String errorMessage) {
                            Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });
                }

            }
        });
        fetchWorkshopRequest();
    }

    private void fetchWorkshopList() {
        if (listAdapter != null) {
            recyclerView.setAdapter(listAdapter);
            recyclerView.setVisibility(View.VISIBLE);
            noInternet.setVisibility(View.INVISIBLE);
            btnNoInternet.setVisibility(View.INVISIBLE);
        } else {
            progress.setVisibility(View.VISIBLE);
            noInternet.setVisibility(View.INVISIBLE);
            btnNoInternet.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
            RequestAndResponse.getWorkshopLList(getContext(), new BaseResponseInterface<List<Workshop>>() {
                @Override
                public void onSuccess(List<Workshop> workshops) {
                    listAdapter = new WorkshopListItemAdapter(WorkshopFragment.this, workshops, false);
                    recyclerView.setAdapter(listAdapter);
                    progress.setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setEnabled(true);
                }

                @Override
                public void onFailed(String errorMessage) {
                    progress.setVisibility(View.INVISIBLE);
                    noInternet.setText(errorMessage);
                    noInternet.setVisibility(View.VISIBLE);
                    btnNoInternet.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setEnabled(false);
                }
            });
        }
    }

    private void fetchWorkshopRequest() {
        if (requestAdapter != null) {
            recyclerView.setAdapter(requestAdapter);
            recyclerView.setVisibility(View.VISIBLE);
            noInternet.setVisibility(View.INVISIBLE);
            btnNoInternet.setVisibility(View.INVISIBLE);
        } else {
            progress.setVisibility(View.VISIBLE);
            noInternet.setVisibility(View.INVISIBLE);
            btnNoInternet.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
            RequestAndResponse.getWorkshopLRequest(getContext(), new BaseResponseInterface<List<Workshop>>() {
                @Override
                public void onSuccess(List<Workshop> workshops) {
                    requestAdapter = new WorkshopListItemAdapter(WorkshopFragment.this, workshops, true);
                    recyclerView.setAdapter(requestAdapter);
                    progress.setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setEnabled(true);
                }

                @Override
                public void onFailed(String errorMessage) {
                    progress.setVisibility(View.INVISIBLE);
                    noInternet.setText(errorMessage);
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
        startActivityForResult(i, REQUEST_CODE_WORKSHOP);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_WORKSHOP) {
            int workshopId = data.getIntExtra(IntentDataKey.CHANGE_WORKSHOP_DATA_KEY, 0);
            if (workshopId == 0)
                return;
            requestAdapter.removeWorkshopRequest(workshopId);

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver((mMessageReceiver),
                new IntentFilter(IntentDataKey.NOTIFICATION_WORKSHOP));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mMessageReceiver);
    }


    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (requestAdapter == null)
                return;
            String action = intent.getStringExtra(IntentDataKey.NOTIFICATION_ACTION);
            switch (action) {
                case IntentDataKey.CANCEL_REQUEST: // delete request
                    String requestId = intent.getStringExtra(IntentDataKey.NOTIFICATION_ID);
                    requestAdapter.deleteRequest(Integer.parseInt(requestId));
                    break;
                case IntentDataKey.ADD_REQUEST: // add request
                    Workshop workshop = intent.getParcelableExtra(IntentDataKey.NOTIFICATION_WORKSHOP_OBJECT);
                    requestAdapter.addWorkshop(workshop);
                    break;
            }
        }
    };
}
