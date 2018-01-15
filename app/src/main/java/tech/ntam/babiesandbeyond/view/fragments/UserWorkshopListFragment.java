package tech.ntam.babiesandbeyond.view.fragments;


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
    private UserWorkshopController userWorkshopController;
    private static UserWorkshopListFragment userWorkshopFragment;

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
        recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        loadData();
    }


    private void loadData() {
        final MyDialog myDialog =new MyDialog();
        myDialog.showMyDialog(getContext());
        RequestAndResponse.getWorkshops(getContext(), new BaseResponseInterface<List<Workshop>>() {
            @Override
            public void onSuccess(List<Workshop> workshops) {
                WorkshopItemAdapter workshopItemAdapter = new WorkshopItemAdapter(getContext(),UserWorkshopListFragment.this,workshops);
                recycleView.setAdapter(workshopItemAdapter);
                myDialog.dismissMyDialog();
            }

            @Override
            public void onFailed(String errorMessage) {
                myDialog.dismissMyDialog();
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getContext() != null && isVisibleToUser) {
            MyToolbar.TitleToolbar titleToolbar = (MyToolbar.TitleToolbar) getActivity();
            titleToolbar.setTitleToolbar(getString(R.string.workshop));
        }
    }

    @Override
    public void getMyObject(Workshop workshop) {
        Intent intent = new Intent(getContext(), ShowWorkshopInfoActivity.class);
        intent.putExtra(IntentDataKey.SHOW_WORKSHOP_DATA_KEY,workshop);
        startActivity(intent);
    }
}
