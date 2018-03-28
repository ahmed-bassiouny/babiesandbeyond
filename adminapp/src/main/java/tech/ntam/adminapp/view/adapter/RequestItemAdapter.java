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
import tech.ntam.adminapp.interfaces.ParseTasks;
import tech.ntam.adminapp.model.Request;
import tech.ntam.mylibrary.Utils;

/**
 * Created by Developer on 22/12/17.
 */

public class RequestItemAdapter extends RecyclerView.Adapter<RequestItemAdapter.MyViewHolder> {
    private List<Request> requests;
    private ParseTasks parseTasks;
    private Fragment fragment;

    public RequestItemAdapter(Fragment fragment, List<Request> requests) {
        this.requests = requests;
        parseTasks = (ParseTasks) fragment;
        this.fragment = fragment;
    }

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
            btnViewDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Request item = requests.get(getAdapterPosition());
                    parseTasks.assignmentTask(item, getAdapterPosition());
                }
            });

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
        Request request = requests.get(position);
        if (!request.getUserPhoto().isEmpty())
            Utils.MyGlide(fragment.getActivity(), holder.ivUserImage, request.getUserPhoto());
        holder.tvDate.setText(request.getStartDate() + " " + request.getStartTime() + " to " + request.getEndTime());
        holder.tvRequestName.setText(request.getUserName());
        holder.tvRequestLocation.setText(request.getLocation());
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    public void updateRequest(List<Request> requests) {
        this.requests = requests;
        notifyDataSetChanged();
    }
    public void addRequest(Request request){
        this.requests.add(0,request);
        notifyItemInserted(0);
    }

    public void removeRequest(int position) {
        requests.remove(position);
        notifyItemRemoved(position);
    }


    public void deleteRequest(final int id) {
        final int size = requests.size();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < size; i++) {
                    Request item = requests.get(i);
                    if (item.getId() == id) {
                        final int position = i;
                        requests.remove(i);
                        fragment.getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                notifyItemRemoved(position);
                            }
                        });
                        break;
                    }
                }
            }
        }).start();
    }
}