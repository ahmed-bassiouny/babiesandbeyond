package tech.ntam.babiesandbeyond.view.fragments;


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
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.helper.ServiceSharedPref;
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.interfaces.ParseObject;
import tech.ntam.babiesandbeyond.model.Workshop;
import tech.ntam.babiesandbeyond.view.activities.ShowWorkshopInfoActivity;
import tech.ntam.babiesandbeyond.view.adapter.WorkshopItemAdapter;
import tech.ntam.mylibrary.IntentDataKey;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserWorkshopListFragment extends Fragment implements ParseObject<Workshop> {

    private RecyclerView recycleView;
    private static UserWorkshopListFragment userWorkshopFragment;
    private WorkshopItemAdapter workshopItemAdapter;
    private boolean isViewShown = false;
    private ProgressBar progress;
    private TextView noInternet;
    private Button btnNoInternet;
    private SwipeRefreshLayout swipeRefreshLayout;

    public UserWorkshopListFragment() {
        // Required empty public constructor
    }


    public static UserWorkshopListFragment newInstance() {
        if (userWorkshopFragment == null) {
            userWorkshopFragment = new UserWorkshopListFragment();
        }
        return userWorkshopFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_user_workshop, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycleView = view.findViewById(R.id.recycle_view);
        progress = view.findViewById(R.id.progress);
        noInternet = view.findViewById(R.id.no_internet);
        btnNoInternet = view.findViewById(R.id.btn_no_internet);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (!isViewShown) {
            loadWorkshop();
        }
        onClick();
    }

    private void onClick() {
        btnNoInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadWorkshop();
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                RequestAndResponse.getWorkshops(getContext(), new BaseResponseInterface<List<Workshop>>() {
                    @Override
                    public void onSuccess(List<Workshop> workshops) {
                        swipeRefreshLayout.setRefreshing(false);
                        workshopItemAdapter.updateWorkshops(workshops);
                    }

                    @Override
                    public void onFailed(String errorMessage) {
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void loadWorkshop() {
        // check if adapter is null that mean i don't load data from backend
        // if adapter not equal null that mean i loaded data so i set it in recycler view
        if (workshopItemAdapter != null) {
            recycleView.setAdapter(workshopItemAdapter);
            progress.setVisibility(View.INVISIBLE);
            noInternet.setVisibility(View.INVISIBLE);
            btnNoInternet.setVisibility(View.INVISIBLE);
            recycleView.setVisibility(View.VISIBLE);
            return;
        }
        progress.setVisibility(View.VISIBLE);
        recycleView.setVisibility(View.INVISIBLE);
        noInternet.setVisibility(View.INVISIBLE);
        btnNoInternet.setVisibility(View.INVISIBLE);
        RequestAndResponse.getWorkshops(getContext(), new BaseResponseInterface<List<Workshop>>() {
            @Override
            public void onSuccess(List<Workshop> workshops) {
                workshopItemAdapter = new WorkshopItemAdapter(getActivity(), UserWorkshopListFragment.this, workshops);
                recycleView.setAdapter(workshopItemAdapter);
                progress.setVisibility(View.INVISIBLE);
                recycleView.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setEnabled(true);
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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getView() != null) {
            isViewShown = true;
            loadWorkshop();
        } else {
            isViewShown = false;
        }
    }

    @Override
    public void getMyObject(Workshop workshop) {
        ServiceSharedPref.setMyWorkshop(getContext(),workshop);
        startActivity(new Intent(getContext(), ShowWorkshopInfoActivity.class));
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
            String serviceId = intent.getStringExtra(IntentDataKey.NOTIFICATION_ID);
            if (serviceId == null || serviceId.isEmpty() || workshopItemAdapter == null)
                return;
            String action = intent.getStringExtra(IntentDataKey.NOTIFICATION_ACTION);
            switch (action) {
                case "0":
                    workshopItemAdapter.deleteWorkshop(Integer.parseInt(serviceId));
                    break;
                case "1":
                    workshopItemAdapter.updateWorkshopToConfirmationWithoutPayment(Integer.parseInt(serviceId));
                    break;
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        Workshop workshop = ServiceSharedPref.getMyWorkshop(getContext());
        if(workshopItemAdapter !=null && workshop != null){
            workshopItemAdapter.updateWorkshop(workshop);
            ServiceSharedPref.clearMyWorkshop(getContext());
        }
    }
}
