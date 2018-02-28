package tech.ntam.adminapp.view.fragments;


import android.app.Activity;
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
import tech.ntam.adminapp.interfaces.ParseMidwife;
import tech.ntam.adminapp.model.Midwife;
import tech.ntam.adminapp.model.MidwifeService;
import tech.ntam.adminapp.view.activities.MidwifeRequestAndDetailsActivity;
import tech.ntam.adminapp.view.adapter.MidwifeItemListAdapter;
import tech.ntam.adminapp.view.adapter.MidwifeRequestItemAdapter;
import tech.ntam.mylibrary.IntentDataKey;
import tech.ntam.mylibrary.SimpleDividerItemDecoration;
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;

/**
 * A simple {@link Fragment} subclass.
 */
public class MidwifeFragment extends Fragment implements ParseMidwife {

    private final int REQUEST_CODE_MIDWIFE = 23;
    private RecyclerView recyclerView;
    private ProgressBar progress;
    private TextView noInternet;
    private Button btnNoInternet;
    private RadioButton btnRequest;
    private RadioButton btnList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MidwifeItemListAdapter midwifeItemListAdapter;
    private MidwifeRequestItemAdapter midwifeRequestItemAdapter;
    private static MidwifeFragment midwifeFragment;

    public MidwifeFragment() {
        // Required empty public constructor
    }

    public static MidwifeFragment newInstance(){
        if(midwifeFragment == null)
            midwifeFragment = new MidwifeFragment();
        return midwifeFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_midwife, container, false);
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
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchMidwifeList();
            }
        });
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchMidwifeRequests();
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(btnRequest.isChecked()){
                    // get requests
                    RequestAndResponse.getMidwifeRequests(getContext(), new BaseResponseInterface<List<MidwifeService>>() {
                        @Override
                        public void onSuccess(List<MidwifeService> midwifeServices) {
                            midwifeRequestItemAdapter.updateRequests(midwifeServices);
                            swipeRefreshLayout.setRefreshing(false);
                        }

                        @Override
                        public void onFailed(String errorMessage) {
                            Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });
                }else {
                    // get lists
                    RequestAndResponse.getAllMidwife(getContext(), new BaseResponseInterface<List<Midwife>>() {
                        @Override
                        public void onSuccess(List<Midwife> midwifeList) {
                            midwifeItemListAdapter.updateList(midwifeList);
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
        fetchMidwifeRequests();
    }

    private void fetchMidwifeList() {
        if (midwifeItemListAdapter != null) {
            recyclerView.setAdapter(midwifeItemListAdapter);
            recyclerView.setVisibility(View.VISIBLE);
            noInternet.setVisibility(View.INVISIBLE);
            btnNoInternet.setVisibility(View.INVISIBLE);
        } else {
            progress.setVisibility(View.VISIBLE);
            noInternet.setVisibility(View.INVISIBLE);
            btnNoInternet.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
            RequestAndResponse.getAllMidwife(getContext(), new BaseResponseInterface<List<Midwife>>() {
                @Override
                public void onSuccess(List<Midwife> midwives) {
                    midwifeItemListAdapter = new MidwifeItemListAdapter(getActivity(),MidwifeFragment.this,midwives);
                    recyclerView.setAdapter(midwifeItemListAdapter);
                    progress.setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
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
    }

    private void fetchMidwifeRequests() {
        if (midwifeRequestItemAdapter != null) {
            recyclerView.setAdapter(midwifeRequestItemAdapter);
            recyclerView.setVisibility(View.VISIBLE);
            noInternet.setVisibility(View.INVISIBLE);
            btnNoInternet.setVisibility(View.INVISIBLE);
        } else {
            progress.setVisibility(View.VISIBLE);
            noInternet.setVisibility(View.INVISIBLE);
            btnNoInternet.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
            RequestAndResponse.getMidwifeRequests(getContext(), new BaseResponseInterface<List<MidwifeService>>() {
                @Override
                public void onSuccess(List<MidwifeService> midwifeServices) {
                    midwifeRequestItemAdapter = new MidwifeRequestItemAdapter(getActivity(),MidwifeFragment.this,midwifeServices);
                    recyclerView.setAdapter(midwifeRequestItemAdapter);
                    progress.setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
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
    }

    @Override
    public void assignmentMidwife(MidwifeService request, int position) {
        Intent intent = new Intent(getActivity(), MidwifeRequestAndDetailsActivity.class);
        intent.putExtra(IntentDataKey.MIDWIFE_SERVICE,request);
        startActivityForResult(intent,REQUEST_CODE_MIDWIFE);
    }

    @Override
    public void viewMidwife(Midwife midwife) {
        Intent intent = new Intent(getActivity(), MidwifeRequestAndDetailsActivity.class);
        intent.putExtra(IntentDataKey.MIDWIFE,midwife);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_MIDWIFE){
            String uniquKey = data.getStringExtra(IntentDataKey.MIDWIFE_SERVICE);
            if(uniquKey == null || uniquKey.isEmpty())
                return;
            midwifeRequestItemAdapter.deleteService(uniquKey);
        }
    }
}
