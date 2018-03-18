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
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import tech.ntam.babiesandbeyond.R;
import tech.ntam.babiesandbeyond.api.request.RequestAndResponse;
import tech.ntam.babiesandbeyond.model.MessageAdmin;
import tech.ntam.babiesandbeyond.view.adapter.MessageAdminItemAdapter;
import tech.ntam.babiesandbeyond.view.dialog.NewMessageToAdminActivity;
import tech.ntam.mylibrary.IntentDataKey;
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageToAdminFragment extends Fragment {


    private static MessageToAdminFragment messageToAdminFragment;
    private MessageAdminItemAdapter messageAdminItemAdapter;
    private RecyclerView recycleView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView toolbarTitle;
    private final int NEW_MESSAGE = 5;

    public MessageToAdminFragment() {
        // Required empty public constructor
    }

    public static MessageToAdminFragment newInstance() {
        if (messageToAdminFragment == null)
            messageToAdminFragment = new MessageToAdminFragment();
        return messageToAdminFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message_to_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        recycleView = view.findViewById(R.id.recycle_view);
        recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        toolbarTitle = view.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(R.string.message);
        loadMessage();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadMessage();
            }
        });
        view.findViewById(R.id.btn_new_message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),NewMessageToAdminActivity.class);
                startActivityForResult(intent,NEW_MESSAGE);
            }
        });
    }

    private void loadMessage() {
        swipeRefreshLayout.setRefreshing(true);
        RequestAndResponse.getMessageAdmin(getContext(), new BaseResponseInterface<List<MessageAdmin>>() {
            @Override
            public void onSuccess(List<MessageAdmin> messageAdmins) {
                if (messageAdminItemAdapter == null) {
                    messageAdminItemAdapter = new MessageAdminItemAdapter(messageAdmins);
                    recycleView.setAdapter(messageAdminItemAdapter);
                } else {
                    messageAdminItemAdapter.updateList(messageAdmins);
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailed(String errorMessage) {
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == NEW_MESSAGE) {
            MessageAdmin item = data.getParcelableExtra(IntentDataKey.MESSAGE);
            if (item != null) {
                messageAdminItemAdapter.addMessage(item);
            }
        }
    }
}
