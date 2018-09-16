package com.example.administrator.riskprojects.Adpter;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.activity.HiddenDangeTrackingManagementActivity;

public class HiddenDangeTrackingAdapter extends RecyclerView.Adapter {




    public HiddenDangeTrackingAdapter() {
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hidden_danger_tracking_management, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.itemView.getContext().startActivity(new Intent( holder.itemView.getContext(),
                        HiddenDangeTrackingManagementActivity.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return 6;
    }

    private void initView(View view) {


    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvHiddenContent;
        private TextView tvArea;
        private TextView tvSpecialty;
        private TextView tvTimeOrOrder;
        private TextView tvCategory;
        private TextView tvSupervise;

        ViewHolder(View view) {
            super(view);
            tvHiddenContent =  view.findViewById(R.id.tv_hidden_content);
            tvArea =  view.findViewById(R.id.tv_area);
            tvSpecialty =  view.findViewById(R.id.tv_specialty);
            tvTimeOrOrder =  view.findViewById(R.id.tv_time_or_order);
            tvCategory =  view.findViewById(R.id.tv_category);
            tvSupervise =  view.findViewById(R.id.tv_supervise);
        }
    }
}
