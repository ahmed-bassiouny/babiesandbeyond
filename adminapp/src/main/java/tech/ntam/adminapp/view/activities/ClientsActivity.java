package tech.ntam.adminapp.view.activities;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import tech.ntam.adminapp.R;
import tech.ntam.adminapp.api.RequestAndResponse;
import tech.ntam.adminapp.interfaces.ParseObject;
import tech.ntam.adminapp.model.Client;
import tech.ntam.adminapp.view.adapter.ClientItemAdapter;
import tech.ntam.mylibrary.IntentDataKey;
import tech.ntam.mylibrary.MyDialog;
import tech.ntam.mylibrary.apiCongif.BaseResponseInterface;

public class ClientsActivity extends AppCompatActivity implements ParseObject<Client> {

    private RecyclerView recycleView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int currentPage = 1;
    private ClientItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clients);
        findView();
        loadClients();
    }

    private void loadClients() {
        final MyDialog dialog = new MyDialog();
        dialog.showMyDialog(this);
        RequestAndResponse.getClients(this, currentPage, new BaseResponseInterface<List<Client>>() {
            @Override
            public void onSuccess(List<Client> clients) {
                adapter = new ClientItemAdapter(ClientsActivity.this, clients);
                recycleView.setAdapter(adapter);
                dialog.dismissMyDialog();
            }

            @Override
            public void onFailed(String errorMessage) {
                Toast.makeText(ClientsActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                dialog.dismissMyDialog();
            }
        });
    }

    private void refreshClients() {
        RequestAndResponse.getClients(this, currentPage, new BaseResponseInterface<List<Client>>() {
            @Override
            public void onSuccess(List<Client> clients) {
                if (adapter == null) {
                    adapter = new ClientItemAdapter(ClientsActivity.this, clients);
                    recycleView.setAdapter(adapter);
                } else
                    adapter.updateClients(clients);

                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailed(String errorMessage) {
                Toast.makeText(ClientsActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void findView() {
        recycleView = findViewById(R.id.recycle_view);
        swipeRefreshLayout = findViewById(R.id.swpie_refresh_layout);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshClients();
            }
        });
    }

    @Override
    public void view(Client client) {
        Intent intent = new Intent(this,ClientActivity.class);
        intent.putExtra(IntentDataKey.CLIENT,client);
        startActivity(intent);
    }
}
