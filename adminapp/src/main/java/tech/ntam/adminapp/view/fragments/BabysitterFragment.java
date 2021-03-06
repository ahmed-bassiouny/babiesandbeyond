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

import tech.ntam.adminapp.R;
import tech.ntam.adminapp.api.RequestAndResponse;
import tech.ntam.adminapp.interfaces.ParseTasks;
import tech.ntam.adminapp.model.Request;
import tech.ntam.adminapp.model.Service;
import tech.ntam.adminapp.model.Staff;
import tech.ntam.adminapp.model.User;
import tech.ntam.adminapp.view.activities.TaskAssigmentActivity;
import tech.ntam.adminapp.view.adapter.RequestItemAdapter;
import tech.ntam.adminapp.view.adapter.ServiceItemAdapter;
import tech.ntam.adminapp.view.dialog.ServiceDetailsActivity;
import tech.ntam.mylibrary.IntentDataKey;
import tech.ntam.mylibrary.SimpleDividerItemDecoration;
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;

/**
 * A simple {@link Fragment} subclass.
 */
public class BabysitterFragment extends Fragment implements ParseTasks {

    private static final int REQUEST_CODE_TASK = 2;
    private static BabysitterFragment babysitterFragment;
    private RecyclerView recyclerView;
    private ProgressBar progress;
    private TextView noInternet;
    private Button btnNoInternet;
    private Staff myStaff;
    private ServiceItemAdapter serviceItemAdapter;
    private RequestItemAdapter requestItemAdapter;
    private RadioButton btnRequest;
    private RadioButton btnList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int currentPosition;

    public static BabysitterFragment newInstance() {
        if (babysitterFragment == null)
            babysitterFragment = new BabysitterFragment();
        return babysitterFragment;
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
        btnRequest = view.findViewById(R.id.btn_request);
        btnList = view.findViewById(R.id.btn_list);
        swipeRefreshLayout = view.findViewById(R.id.swpie_refresh_layout);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myStaff == null)
                    return;
                if (requestItemAdapter == null)
                    requestItemAdapter = new RequestItemAdapter(BabysitterFragment.this, myStaff.getRequests());
                recyclerView.setAdapter(requestItemAdapter);
            }
        });
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myStaff == null)
                    return;
                if (serviceItemAdapter == null)
                    serviceItemAdapter = new ServiceItemAdapter(BabysitterFragment.this, myStaff.getServices());
                recyclerView.setAdapter(serviceItemAdapter);
            }
        });
        btnNoInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRequest.setChecked(true);
                fetchData();
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                RequestAndResponse.getStaff(getContext(), User.BABYSITTER, new BaseResponseInterface<Staff>() {
                    @Override
                    public void onSuccess(Staff staff) {
                        myStaff = staff;
                        if (btnRequest.isChecked()) {
                            requestItemAdapter.updateRequest(myStaff.getRequests());
                        } else {
                            serviceItemAdapter.updateService(myStaff.getServices());
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onFailed(String errorMessage) {
                        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
        fetchData();
    }


    private void fetchData() {
        if (myStaff != null) {
            if (requestItemAdapter == null)
                requestItemAdapter = new RequestItemAdapter(BabysitterFragment.this, myStaff.getRequests());
            recyclerView.setAdapter(requestItemAdapter);
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            progress.setVisibility(View.VISIBLE);
            noInternet.setVisibility(View.INVISIBLE);
            btnNoInternet.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
            RequestAndResponse.getStaff(getContext(), User.BABYSITTER, new BaseResponseInterface<Staff>() {
                @Override
                public void onSuccess(Staff staff) {
                    myStaff = staff;
                    requestItemAdapter = new RequestItemAdapter(BabysitterFragment.this, staff.getRequests());
                    recyclerView.setAdapter(requestItemAdapter);
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
    public void assignmentTask(Request request, int position) {
        Intent intent = new Intent(getActivity(), TaskAssigmentActivity.class);
        intent.putExtra(IntentDataKey.REQUEST, request);
        currentPosition = position;
        startActivityForResult(intent, REQUEST_CODE_TASK);
    }

    @Override
    public void viewService(Service service) {
        Intent intent = new Intent(getActivity(), ServiceDetailsActivity.class);
        intent.putExtra(IntentDataKey.SERVICE, service);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_TASK) {
            requestItemAdapter.removeRequest(currentPosition);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver((mMessageReceiver),
                new IntentFilter(IntentDataKey.NOTIFICATION_BABYSITTER));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mMessageReceiver);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (requestItemAdapter == null)
                return;
            String action = intent.getStringExtra(IntentDataKey.NOTIFICATION_ACTION);
            switch (action) {
                case IntentDataKey.CANCEL_REQUEST: // delete request
                    String requestId = intent.getStringExtra(IntentDataKey.NOTIFICATION_ID);
                    requestItemAdapter.deleteRequest(Integer.parseInt(requestId));
                    break;
                case IntentDataKey.ADD_REQUEST: // add request
                    Request request = intent.getParcelableExtra(IntentDataKey.NOTIFICATION_SERVICE_OBJECT);
                    requestItemAdapter.addRequest(request);
                    break;
            }
        }
    };
}
