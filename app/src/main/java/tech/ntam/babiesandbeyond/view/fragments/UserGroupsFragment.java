package tech.ntam.babiesandbeyond.view.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.List;

import info.hoang8f.android.segmented.SegmentedGroup;
import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.view.adapter.GroupItemAdapter;
import tech.ntam.babiesandbeyond.view.toolbar.MyToolbar;
import tech.ntam.mylibrary.DummyGroupModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserGroupsFragment extends Fragment {



    private SegmentedGroup segmentedGroup;
    private RadioButton btnAllGroups;
    private RadioButton btnMostPopular;
    private RadioButton btnMyGroups;
    private RecyclerView recycleView;
    private static UserGroupsFragment userGroupsFragment;
    private List<DummyGroupModel> dummyGroupModels;

    public UserGroupsFragment() {
        // Required empty public constructor
    }


    public static UserGroupsFragment newInstance(){
        if(userGroupsFragment == null){
            userGroupsFragment = new UserGroupsFragment();
        }
        return userGroupsFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_groups, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        segmentedGroup = view.findViewById(R.id.segmented2);
        btnAllGroups = view.findViewById(R.id.btn_all_groups);
        btnMostPopular = view.findViewById(R.id.btn_most_popular);
        btnMyGroups = view.findViewById(R.id.btn_my_groups);
        recycleView = view.findViewById(R.id.recycle_view);
        setData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getContext()!=null && isVisibleToUser){
            MyToolbar.TitleToolbar titleToolbar = (MyToolbar.TitleToolbar) getActivity();
            titleToolbar.setTitleToolbar(getString(R.string.groups));
        }
    }


    private void setData() {
        dummyGroupModels = new ArrayList<>();
        DummyGroupModel dummyGroupModel1 = new DummyGroupModel("Approved","Group 1","Admin 1","description About Group 1","2 nov",false);
        dummyGroupModels.add(dummyGroupModel1);
        DummyGroupModel dummyGroupModel2 = new DummyGroupModel("Pending","Group 2","Admin 2","description About Group 2","5 nov",true);
        dummyGroupModels.add(dummyGroupModel2);
        DummyGroupModel dummyGroupModel3 = new DummyGroupModel("Pending","Group 3","User 1","description About Group 3","7 nov",true);
        dummyGroupModels.add(dummyGroupModel3);
        DummyGroupModel dummyGroupModel4 = new DummyGroupModel("Approved","Group 4","User 2","description About Group 4","9 nov",false);
        dummyGroupModels.add(dummyGroupModel4);
        recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        recycleView.setAdapter(new GroupItemAdapter(dummyGroupModels));
    }
}
