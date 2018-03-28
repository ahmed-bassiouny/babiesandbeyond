package tech.ntam.adminapp.view.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import tech.ntam.adminapp.R;
import tech.ntam.adminapp.interfaces.ParseObject;
import tech.ntam.adminapp.interfaces.ParseTasks;
import tech.ntam.adminapp.model.Client;
import tech.ntam.adminapp.model.Service;
import tech.ntam.mylibrary.Utils;

/**
 * Created by Developer on 22/12/17.
 */

public class ClientItemAdapter extends RecyclerView.Adapter<ClientItemAdapter.MyViewHolder> {
    private List<Client> clients;
    private ParseObject parseObject;
    private Activity activity;


    public ClientItemAdapter(Activity activity, List<Client> clients) {
        parseObject = (ParseObject) activity;
        this.clients = clients;
        this.activity = activity;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView ivUserImage;
        private TextView tvClientName;
        private Button btnViewDetails;

        public MyViewHolder(View view) {
            super(view);
            ivUserImage = view.findViewById(R.id.iv_user_image);
            tvClientName = view.findViewById(R.id.tv_client_name);
            btnViewDetails = view.findViewById(R.id.btn_view_details);
            btnViewDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Client item = clients.get(getAdapterPosition());
                    parseObject.view(item);
                }
            });
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_client_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Client client = clients.get(position);
        if (!client.getPhoto().isEmpty())
            Utils.MyGlide(activity, holder.ivUserImage, client.getPhone());
        holder.tvClientName.setText(client.getName());
    }

    @Override
    public int getItemCount() {
        return clients.size();
    }

    public void updateClients(List<Client> clients) {
        this.clients = clients;
        notifyDataSetChanged();
    }
    public void addMoreClients(List<Client> clients) {
        this.clients.addAll(clients);
        notifyDataSetChanged();
    }
    public void addClient(Client client) {
        this.clients.add(0,client);
        notifyItemInserted(0);
    }
}