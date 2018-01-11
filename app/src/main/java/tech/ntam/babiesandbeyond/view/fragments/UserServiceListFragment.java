package tech.ntam.babiesandbeyond.view.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.api.config.BaseResponseInterface;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.interfaces.ParseObject;
import tech.ntam.babiesandbeyond.model.Service;
import tech.ntam.babiesandbeyond.model.ServiceTypeList;
import tech.ntam.babiesandbeyond.model.UserService;
import tech.ntam.babiesandbeyond.view.activities.ShowServiceInfoActivity;
import tech.ntam.babiesandbeyond.view.activities.UserSendRequestActivity;
import tech.ntam.babiesandbeyond.view.adapter.ServiceItemAdapter;
import tech.ntam.babiesandbeyond.view.dialog.MyDialog;
import tech.ntam.mylibrary.IntentDataKey;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserServiceListFragment extends Fragment implements ParseObject<Service> {

    private RecyclerView recycleView;
    private static UserServiceListFragment userServiceListFragment;

    public UserServiceListFragment() {
        // Required empty public constructor
    }
    public static UserServiceListFragment newInstance(){
        if(userServiceListFragment == null)
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
        recycleView = view.findViewById(R.id.recycle_view);
        recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        view.findViewById(R.id.btn_send_request).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), UserSendRequestActivity.class));
            }
        });
        loadService();
    }

    private void loadService() {
        final MyDialog myDialog = new MyDialog();
        myDialog.showMyDialog(getContext());
        RequestAndResponse.getMyService(getContext(), new BaseResponseInterface<UserService>() {
            @Override
            public void onSuccess(UserService userService) {
                if (userService != null) {
                    ServiceTypeList.setServiceTypes(userService.getServiceTypes());
                    ServiceItemAdapter serviceItemAdapter = new ServiceItemAdapter(getContext(),UserServiceListFragment.this,userService.getServices());
                    recycleView.setAdapter(serviceItemAdapter);
                    myDialog.dismissMyDialog();
                }
            }

            @Override
            public void onFailed(String errorMessage) {
                myDialog.dismissMyDialog();
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void OnClick(Service service) {
        Intent intent = new Intent(getContext(), ShowServiceInfoActivity.class);
        intent.putExtra(IntentDataKey.SHOW_SERVICE_DATA_KEY,service);
        startActivity(intent);
    }
}
