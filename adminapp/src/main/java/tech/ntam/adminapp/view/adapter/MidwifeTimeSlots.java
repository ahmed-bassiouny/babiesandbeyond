package tech.ntam.adminapp.view.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import tech.ntam.adminapp.R;
import tech.ntam.adminapp.model.SectionOrRowMidwife;

/**
 * Created by Developer on 24/02/18.
 */

public class MidwifeTimeSlots extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SectionOrRowMidwife> sectionOrRowMidwives;
    private int SECTION = 0;
    private int ROW = 1;
    private Activity activity;

    public MidwifeTimeSlots(Activity activity, List<SectionOrRowMidwife> sectionOrRowMidwives) {
        this.sectionOrRowMidwives = sectionOrRowMidwives;
        this.activity = activity;
    }

    public class RowViewHolder extends RecyclerView.ViewHolder{
        private TextView tvFrom;
        private TextView tvTo;
        public RowViewHolder(View itemView) {
            super(itemView);
            tvFrom = itemView.findViewById(R.id.tv_from);
            tvTo = itemView.findViewById(R.id.tv_to);
        }
    }

    public class SectionViewHolder extends RecyclerView.ViewHolder{

        private TextView tvSectionName;
        private TextView tvSectionDate;

        public SectionViewHolder(View itemView) {
            super(itemView);
            tvSectionName = itemView.findViewById(R.id.tv_section_name);
            tvSectionDate = itemView.findViewById(R.id.tv_section_date);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == ROW) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_midwife_row_item, parent, false);
            return new RowViewHolder(itemView);
        } else {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_midwife_section_item, parent, false);
            return new SectionViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SectionOrRowMidwife item = sectionOrRowMidwives.get(position);
        if(item.isRow()) {
            RowViewHolder h = (RowViewHolder) holder;
            h.tvFrom.setText(item.getRow().getFrom());
            h.tvTo.setText(item.getRow().getTo());
        } else {
            SectionViewHolder h = (SectionViewHolder) holder;
            h.tvSectionName.setText(item.getSection());
            h.tvSectionDate.setText(item.getDate());
        }
    }

    @Override
    public int getItemCount() {
        return sectionOrRowMidwives.size();
    }

    @Override
    public int getItemViewType(int position) {
        super.getItemViewType(position);
        SectionOrRowMidwife item = sectionOrRowMidwives.get(position);
        if(item.isRow()) {
            return ROW;
        } else {
            return SECTION;
        }
    }
}
