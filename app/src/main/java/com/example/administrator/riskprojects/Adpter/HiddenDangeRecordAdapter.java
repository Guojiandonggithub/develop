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
import com.example.administrator.riskprojects.activity.HiddenDangerDetailManagementActivity;

import java.util.Arrays;
import java.util.List;

public class HiddenDangeRecordAdapter extends RecyclerView.Adapter {

    private boolean[] expands = new boolean[]{false, false, false, false, false, false};


    public HiddenDangeRecordAdapter() {
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hidden_danger_record_management, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (expands[position]) {
            ((ViewHolder) holder).expand.setVisibility(View.VISIBLE);
            ((ViewHolder) holder).clickMore.setVisibility(View.GONE);
        } else {
            ((ViewHolder) holder).expand.setVisibility(View.GONE);
            ((ViewHolder) holder).clickMore.setVisibility(View.VISIBLE);
        }
        ((ViewHolder) holder).clickMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expands[position] = true;
                notifyItemChanged(position);
            }
        });


        ((ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.itemView.getContext().startActivity(new Intent( holder.itemView.getContext(),
                        HiddenDangerDetailManagementActivity.class));
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
        private TextView tvHiddenUnits;
        private TextView tvTimeOrOrder;
        private TextView tvHiddenContent;
        private TextView tvHiddenDangerBelongs;
        private LinearLayoutCompat expand;
        private TextView tvProfessional;
        private TextView tvArea;
        private TextView tvClasses;
        private TextView tvOversee;
        private ImageView ivStatusSecond;
        private LinearLayoutCompat clickMore;
        private ImageView ivStatus;

        ViewHolder(View view) {
            super(view);
            tvHiddenUnits = view.findViewById(R.id.tv_hidden_units);
            tvTimeOrOrder = view.findViewById(R.id.tv_time_or_order);
            tvHiddenContent = view.findViewById(R.id.tv_hidden_content);
            tvHiddenDangerBelongs = view.findViewById(R.id.tv_hidden_danger_belongs);
            expand = view.findViewById(R.id.expand);
            tvArea = view.findViewById(R.id.tv_area);
            tvClasses = view.findViewById(R.id.tv_classes);
            tvOversee = view.findViewById(R.id.tv_oversee);
            ivStatusSecond = view.findViewById(R.id.iv_status_second);
            clickMore = view.findViewById(R.id.click_more);
            ivStatus = view.findViewById(R.id.iv_status);
        }
    }
}
