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
import com.example.administrator.riskprojects.activity.HiddenRiskRecordDetailActivity;
import com.example.administrator.riskprojects.bean.HiddenDangerRecord;
import com.example.administrator.riskprojects.bean.HomeHiddenRecord;

import java.util.List;

public class HiddenDangerStatisticsRepeatAdapter extends RecyclerView.Adapter {
    private List<HomeHiddenRecord> recordList;


    public HiddenDangerStatisticsRepeatAdapter(List<HomeHiddenRecord> recordList) {
        this.recordList = recordList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hidden_danger_statistics_of_repeat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (recordList.size() > 0) {
            ((ViewHolder) holder).tvHiddenContent.setText(recordList.get(position).getContent());
            ((ViewHolder) holder).tvNameOfTheProfessional.setText(recordList.get(position).getSname());
            ((ViewHolder) holder).tvHazardLevel.setText(recordList.get(position).getGname());
            //((ViewHolder) holder).tvHiddenTroubleDescription.setText(recordList.get(position).getHiddenBelong());
//            ((ViewHolder) holder).tvRepeatTheNumber.setText(recordList.get(position).getRepeatNum());
        }

        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(),
                        HiddenRiskRecordDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("hiddenDangerRecord",recordList.get(position));
                intent.putExtra("hiddenrecordBundle",bundle);
                holder.itemView.getContext().startActivity(intent);
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return recordList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvHiddenUnits;
        private TextView tvHiddenContent;
        private TextView tvNameOfTheProfessional;
        private TextView tvHazardLevel;
        private TextView tvIsSupervisor;
        private TextView tvFindTime;
        private TextView tvCheckUnit;


        ViewHolder(View view) {
            super(view);

            tvHiddenUnits = view.findViewById(R.id.tv_hidden_units);
            tvHiddenContent = view.findViewById(R.id.tv_hidden_content);
            tvNameOfTheProfessional = view.findViewById(R.id.tv_name_of_the_professional);
            tvHazardLevel = view.findViewById(R.id.tv_hazard_level);
            tvIsSupervisor = view.findViewById(R.id.tv_is_supervisor);
            tvFindTime = view.findViewById(R.id.tv_find_time);
            tvCheckUnit = view.findViewById(R.id.tv_check_unit);
        }
    }
}
