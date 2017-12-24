package tech.ntam.adminapp.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import tech.ntam.adminapp.R;

/**
 * Created by bassiouny on 22/12/17.
 */

public class NurseRequestItemAdapter extends RecyclerView.Adapter<NurseRequestItemAdapter.MyViewHolder> {

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView ivUserImage;
        private TextView tvDate;
        private TextView tvRequestName;
        private TextView tvRequestLocation;
        private Button btnViewDetails;

        public MyViewHolder(View view) {
            super(view);
            ivUserImage = view.findViewById(R.id.iv_user_image);
            tvDate = view.findViewById(R.id.tv_date);
            tvRequestName = view.findViewById(R.id.tv_request_name);
            tvRequestLocation = view.findViewById(R.id.tv_request_location);
            btnViewDetails = view.findViewById(R.id.btn_view_details);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_request_nurse_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}