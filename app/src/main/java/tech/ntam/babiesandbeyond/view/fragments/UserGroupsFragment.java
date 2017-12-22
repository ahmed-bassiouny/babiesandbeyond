package tech.ntam.babiesandbeyond.view.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import info.hoang8f.android.segmented.SegmentedGroup;
import tech.ntam.babiesandbeyond.R;

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
    }
}
