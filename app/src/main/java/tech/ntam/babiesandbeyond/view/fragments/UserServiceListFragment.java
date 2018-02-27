package tech.ntam.babiesandbeyond.view.fragments;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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


import com.w9jds.FloatingActionMenu;
import com.w9jds.floatingactionmenu.OnMenuItemClickListener;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.helper.ServiceSharedPref;
import tech.ntam.babiesandbeyond.interfaces.ParseService;
import tech.ntam.babiesandbeyond.model.MidwifeService;
import tech.ntam.babiesandbeyond.view.activities.UserRequestMidwifeActivity;
import tech.ntam.babiesandbeyond.view.adapter.MidwifeServiceItemAdapter;
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.model.Service;
import tech.ntam.babiesandbeyond.model.UserService;
import tech.ntam.babiesandbeyond.view.activities.MidwifeActivity;
import tech.ntam.babiesandbeyond.view.activities.ShowServiceInfoActivity;
import tech.ntam.babiesandbeyond.view.activities.UserRequestNurseAndBabysitterActivity;
import tech.ntam.babiesandbeyond.view.adapter.ServiceItemAdapter;
import tech.ntam.mylibrary.IntentDataKey;
import tech.ntam.mylibrary.interfaces.Constant;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserServiceListFragment extends Fragment implements ParseService {

    private RecyclerView recycleViewService;
    private RecyclerView recycleViewMidwife;
    private static UserServiceListFragment userServiceListFragment;
    private ServiceItemAdapter serviceItemAdapter;
    private MidwifeServiceItemAdapter midwifeServiceItemAdapter;
    private boolean isViewShown = false;
    private FloatingActionMenu multipleActions;
    private ProgressBar progress;

    private TextView noInternet;
    private Button btnNoInternet;
    private SwipeRefreshLayout swipeRefreshLayout;

    public UserServiceListFragment() {
        // Required empty public constructor
    }

    public static UserServiceListFragment newInstance() {
        if (userServiceListFragment == null)
            userServiceListFragment = new UserServiceListFragment();
        return userServiceListFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_service_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycleViewService = view.findViewById(R.id.recycle_view);
        recycleViewMidwife = view.findViewById(R.id.recycle_view_midwife);
        multipleActions = view.findViewById(R.id.action_menu);
        progress = view.findViewById(R.id.progress);
        noInternet = view.findViewById(R.id.no_internet);
        btnNoInternet = view.findViewById(R.id.btn_no_internet);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        recycleViewService.setLayoutManager(new LinearLayoutManager(getContext()));
        recycleViewMidwife.setLayoutManager(new LinearLayoutManager(getContext()));
        if (!isViewShown) {
            loadService();
        }
        onClick();
    }

    private void onClick() {
        multipleActions.addOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(FloatingActionMenu floatingActionMenu, int i, FloatingActionButton floatingActionButton) {
                Intent intent ;
                switch (i){
                    // request nurse
                    case 0:
                        intent = new Intent(getContext(), UserRequestNurseAndBabysitterActivity.class);
                        intent.putExtra(IntentDataKey.SERVICE, IntentDataKey.NURSE_SERVICE);
                        startActivityForResult(intent, IntentDataKey.ADD_SERVICE_DATA_CODE);
                        break;
                    // request midwife
                    case 1:
                        intent = new Intent(getContext(), MidwifeActivity.class);
                        startActivityForResult(intent, IntentDataKey.ADD_SERVICE_DATA_CODE);
                        break;
                    // request babysitter
                    case 2:
                        intent = new Intent(getContext(), UserRequestNurseAndBabysitterActivity.class);
                        intent.putExtra(IntentDataKey.SERVICE, IntentDataKey.BABYSITTER_SERVICE);
                        startActivityForResult(intent, IntentDataKey.ADD_SERVICE_DATA_CODE);
                        break;
                }
            }
        });
        btnNoInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadService();
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                RequestAndResponse.getMyService(getContext(), new BaseResponseInterface<UserService>() {
                    @Override
                    public void onSuccess(UserService userService) {
                        serviceItemAdapter.updateServices(userService.getServices());
                        midwifeServiceItemAdapter.updateMidwifeServices(userService.getMidwifeServices());
                        swipeRefreshLayout.setRefreshing(false);

                    }

                    @Override
                    public void onFailed(String errorMessage) {
                        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);

                    }
                });
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (multipleActions.isOpened())
            multipleActions.close();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver((mMessageReceiver),
                new IntentFilter(IntentDataKey.NOTIFICATION_SERVICE));
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
            if (serviceId == null || serviceId.isEmpty() || serviceItemAdapter == null)
                return;
            String action = intent.getStringExtra(IntentDataKey.NOTIFICATION_ACTION);
            String price = intent.getStringExtra(IntentDataKey.NOTIFICATION_SERVICE_PRICE);
            String staffName = intent.getStringExtra(IntentDataKey.NOTIFICATION_STAFF_NAME);
            switch (action) {
                case "0":
                    serviceItemAdapter.deleteService(Integer.parseInt(serviceId));
                    break;
                case "1":
                    serviceItemAdapter.updateService(Integer.parseInt(serviceId),price,staffName);
                    break;
            }

        }
    };

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getView() != null) {
            isViewShown = true;
            loadService();
        } else {
            isViewShown = false;
        }

    }


    private void loadService() {
        // check if adapter is null that mean i don't load data from backend
        // if adapter not equal null that mean i loaded data so i set it in recycler view
        if (serviceItemAdapter != null && midwifeServiceItemAdapter !=null) {
            recycleViewService.setAdapter(serviceItemAdapter);
            recycleViewMidwife.setAdapter(midwifeServiceItemAdapter);
            progress.setVisibility(View.INVISIBLE);
            noInternet.setVisibility(View.INVISIBLE);
            btnNoInternet.setVisibility(View.INVISIBLE);
            recycleViewService.setVisibility(View.VISIBLE);
            recycleViewMidwife.setVisibility(View.VISIBLE);

            return;
        }
        progress.setVisibility(View.VISIBLE);
        recycleViewService.setVisibility(View.INVISIBLE);
        recycleViewMidwife.setVisibility(View.INVISIBLE);
        noInternet.setVisibility(View.INVISIBLE);
        btnNoInternet.setVisibility(View.INVISIBLE);
        RequestAndResponse.getMyService(getContext(), new BaseResponseInterface<UserService>() {
            @Override
            public void onSuccess(UserService userService) {
                if (userService != null) {
                    serviceItemAdapter = new ServiceItemAdapter(getActivity(), UserServiceListFragment.this, userService.getServices());
                    midwifeServiceItemAdapter = new MidwifeServiceItemAdapter(getActivity(), UserServiceListFragment.this, userService.getMidwifeServices());
                    recycleViewService.setAdapter(serviceItemAdapter);
                    recycleViewMidwife.setAdapter(midwifeServiceItemAdapter);
                    progress.setVisibility(View.INVISIBLE);
                    recycleViewService.setVisibility(View.VISIBLE);
                    recycleViewMidwife.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setEnabled(true);
                }
            }

            @Override
            public void onFailed(String errorMessage) {
                progress.setVisibility(View.INVISIBLE);
                noInternet.setVisibility(View.VISIBLE);
                btnNoInternet.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setEnabled(false);
                noInternet.setText(errorMessage);
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        Service item = ServiceSharedPref.getMyService(getContext());
        if (serviceItemAdapter != null && item != null) {
            if(item.getServiceStatusString().equals(Constant.CANCEL)){
                serviceItemAdapter.deleteService(item.getId());
            }else {
                serviceItemAdapter.updateService(item);
            }
            ServiceSharedPref.clearMyService(getContext());
        }

        MidwifeService midwifeService = ServiceSharedPref.getMyMidwife(getContext());
        if (midwifeServiceItemAdapter != null && midwifeService != null) {
            if(midwifeService.getMidwifeStatus().equals(Constant.CANCEL)){
                midwifeServiceItemAdapter.deleteService(midwifeService.getUniqueKey());
            }else {
                midwifeServiceItemAdapter.updateService(midwifeService);
            }
            ServiceSharedPref.clearMyMidwife(getContext());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            if (requestCode == IntentDataKey.ADD_SERVICE_DATA_CODE) {
                Service service = data.getParcelableExtra(IntentDataKey.ADD_SERVICE_DATA_KEY);
                if (service != null) {
                    serviceItemAdapter.addService(service);
                }
            }
        }
    }

    @Override
    public void getService(Service service) {
        ServiceSharedPref.setMyService(getContext(), service);
        startActivity(new Intent(getContext(), ShowServiceInfoActivity.class));
    }

    @Override
    public void getMidwife(MidwifeService midwifeService) {
        ServiceSharedPref.setMyMidwife(getContext(),midwifeService);
        startActivity(new Intent(getContext(), UserRequestMidwifeActivity.class));

    }
}
