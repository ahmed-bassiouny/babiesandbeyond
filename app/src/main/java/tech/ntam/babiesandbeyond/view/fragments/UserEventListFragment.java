package tech.ntam.babiesandbeyond.view.fragments;


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
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.interfaces.ParseObject;
import tech.ntam.babiesandbeyond.model.Event;
import tech.ntam.babiesandbeyond.view.activities.ShowEventInfoActivity;
import tech.ntam.babiesandbeyond.view.adapter.EventItemAdapter;
import tech.ntam.mylibrary.IntentDataKey;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserEventListFragment extends Fragment implements ParseObject<Event> {

    private RecyclerView recycleView;
    private static UserEventListFragment userEventListFragment;
    private EventItemAdapter eventItemAdapter;
    private boolean isViewShown = false;
    private ProgressBar progress;

    private TextView noInternet;
    private Button btnNoInternet;
    private SwipeRefreshLayout swipeRefreshLayout;

    public UserEventListFragment() {
        // Required empty public constructor
    }


    public static UserEventListFragment newInstance() {
        if (userEventListFragment == null) {
            userEventListFragment = new UserEventListFragment();
        }
        return userEventListFragment;
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
            loadEvents();
        }
        onClick();
    }

    private void onClick() {
        btnNoInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadEvents();
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                RequestAndResponse.getEvents(getContext(), new BaseResponseInterface<List<Event>>() {
                    @Override
                    public void onSuccess(List<Event> events) {
                        eventItemAdapter.updateEvents(events);
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

    private void loadEvents() {
        // check if adapter is null that mean i don't load data from backend
        // if adapter not equal null that mean i loaded data so i set it in recycler view
        if (eventItemAdapter != null) {
            recycleView.setAdapter(eventItemAdapter);
            progress.setVisibility(View.INVISIBLE);
            noInternet.setVisibility(View.INVISIBLE);
            noInternet.setVisibility(View.INVISIBLE);
            recycleView.setVisibility(View.VISIBLE);
            return;
        }
        progress.setVisibility(View.VISIBLE);
        recycleView.setVisibility(View.INVISIBLE);
        noInternet.setVisibility(View.INVISIBLE);
        btnNoInternet.setVisibility(View.INVISIBLE);
        RequestAndResponse.getEvents(getContext(), new BaseResponseInterface<List<Event>>() {
            @Override
            public void onSuccess(List<Event> events) {
                eventItemAdapter = new EventItemAdapter(getContext(), UserEventListFragment.this, events);
                recycleView.setAdapter(eventItemAdapter);
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
                noInternet.setText(errorMessage);
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getView() != null) {
            isViewShown = true;
            loadEvents();
        } else {
            isViewShown = false;
        }
    }

    @Override
    public void getMyObject(Event event) {
        Intent intent = new Intent(getContext(), ShowEventInfoActivity.class);
        intent.putExtra(IntentDataKey.SHOW_EVENT_DATA_KEY, event);
        startActivityForResult(intent, IntentDataKey.CHANGE_EVENT_DATA_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentDataKey.CHANGE_EVENT_DATA_CODE && resultCode == Activity.RESULT_OK && data != null) {
            Event event = data.getParcelableExtra(IntentDataKey.CHANGE_EVENT_DATA_KEY);
            if (event != null) {
                eventItemAdapter.updateEvent(event);
            }
        }
    }
}
