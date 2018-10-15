package com.example.administrator.riskprojects.Adpter;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.administrator.riskprojects.activity.HiddenRiskRecordDetailActivity;
import com.example.administrator.riskprojects.bean.HiddenDangerRecord;
import com.example.administrator.riskprojects.bean.HomeHiddenRecord;
import com.example.administrator.riskprojects.bean.ThreeFix;

import java.util.List;

public class HiddenDangerStatisticsEachUnitDetailAdapter extends RecyclerView.Adapter {
    private List<HiddenDangerRecord> recordList;



    public HiddenDangerStatisticsEachUnitDetailAdapter(List<HiddenDangerRecord> recordList) {
        this.recordList = recordList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hidden_danger_statistics_of_each_unit_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if(recordList.size()>0){
            ((ViewHolder) holder).tvHiddenUnits.setText(recordList.get(position).getTeamGroupName());
            String findTimeStr = recordList.get(position).getFindTime();
            String findTime = findTimeStr.substring(0,10);
            ((ViewHolder) holder).tvTimeOrOrder.setText(findTime+"/"+recordList.get(position).getClassName());
            ((ViewHolder) holder).tvHiddenContent.setText(recordList.get(position).getContent());
            ((ViewHolder) holder).tvHiddenDangerBelongs.setText(recordList.get(position).getHiddenBelong());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(holder.itemView.getContext(),
                        //HiddenRiskRecordDetailActivity.class);
                //Bundle bundle = new Bundle();
                //bundle.putSerializable("hiddenDangerRecord",recordList.get(position));
                //intent.putExtra("hiddenrecordBundle",bundle);
                Intent intent = new Intent(holder.itemView.getContext(),
                        HiddenDangerDetailManagementActivity.class);
                intent.putExtra("id",recordList.get(position).getId());
                intent.putExtra("hiddenriskrecorddetail",recordList.get(position).getId());
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return recordList.size();
    }



    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvHiddenUnits;
        private TextView tvTimeOrOrder;
        private TextView tvHiddenContent;
        private TextView tvHiddenDangerBelongs;
        private LinearLayoutCompat clickMore;
        private ImageView ivStatus;


        ViewHolder(View view) {
            super(view);

            tvHiddenUnits = view.findViewById(R.id.tv_hidden_units);
            tvTimeOrOrder = view.findViewById(R.id.tv_time_or_order);
            tvHiddenContent = view.findViewById(R.id.tv_hidden_content);
            tvHiddenDangerBelongs = view.findViewById(R.id.tv_hidden_danger_belongs);
            clickMore = view.findViewById(R.id.click_more);
            ivStatus = view.findViewById(R.id.iv_status);
        }
    }
}
