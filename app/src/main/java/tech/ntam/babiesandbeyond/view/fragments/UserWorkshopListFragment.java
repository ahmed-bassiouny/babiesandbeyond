package tech.ntam.babiesandbeyond.view.fragments;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.api.config.BaseResponseInterface;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.controller.fragments.UserWorkshopController;
import tech.ntam.babiesandbeyond.interfaces.ParseObject;
import tech.ntam.babiesandbeyond.model.Service;
import tech.ntam.babiesandbeyond.model.Workshop;
import tech.ntam.babiesandbeyond.view.activities.ShowServiceInfoActivity;
import tech.ntam.babiesandbeyond.view.activities.ShowWorkshopInfoActivity;
import tech.ntam.babiesandbeyond.view.adapter.WorkshopItemAdapter;
import tech.ntam.babiesandbeyond.view.dialog.MyDialog;
import tech.ntam.babiesandbeyond.view.dialog.WorkShopDialogActivity;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;
import tech.ntam.mylibrary.IntentDataKey;
import tech.ntam.mylibrary.Utils;

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
        recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (!isViewShown) {
            loadWorkshop();
        }
        onClick();
    }
    private void onClick() {
        noInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadWorkshop();
            }
        });
    }

    private void loadWorkshop() {
        // check if adapter is null that mean i don't load data from backend
        // if adapter not equal null that mean i loaded data so i set it in recycler view
        if(workshopItemAdapter !=null) {
            recycleView.setAdapter(workshopItemAdapter);
            progress.setVisibility(View.INVISIBLE);
            noInternet.setVisibility(View.INVISIBLE);
            recycleView.setVisibility(View.VISIBLE);
            return;
        }
        progress.setVisibility(View.VISIBLE);
        recycleView.setVisibility(View.INVISIBLE);
        noInternet.setVisibility(View.INVISIBLE);
        RequestAndResponse.getWorkshops(getContext(), new BaseResponseInterface<List<Workshop>>() {
            @Override
            public void onSuccess(List<Workshop> workshops) {
                workshopItemAdapter = new WorkshopItemAdapter(getContext(), UserWorkshopListFragment.this, workshops);
                recycleView.setAdapter(workshopItemAdapter);
                progress.setVisibility(View.INVISIBLE);
                noInternet.setVisibility(View.INVISIBLE);
                recycleView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailed(String errorMessage) {
                progress.setVisibility(View.INVISIBLE);
                recycleView.setVisibility(View.INVISIBLE);
                noInternet.setVisibility(View.VISIBLE);
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
        Intent intent = new Intent(getContext(), ShowWorkshopInfoActivity.class);
        intent.putExtra(IntentDataKey.SHOW_WORKSHOP_DATA_KEY, workshop);
        startActivityForResult(intent, IntentDataKey.CHANGE_WORKSHOP_DATA_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentDataKey.CHANGE_WORKSHOP_DATA_CODE && resultCode == Activity.RESULT_OK && data != null) {
            Workshop workshop = data.getParcelableExtra(IntentDataKey.CHANGE_WORKSHOP_DATA_KEY);
            if (workshop != null) {
                workshopItemAdapter.updateWorkshop(workshop);
            }
        }
    }
}
