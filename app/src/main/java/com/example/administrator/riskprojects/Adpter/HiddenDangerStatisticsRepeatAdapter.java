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

import java.util.List;

public class HiddenDangerStatisticsRepeatAdapter extends RecyclerView.Adapter {
    private List<HiddenDangerRecord> recordList;



    public HiddenDangerStatisticsRepeatAdapter(List<HiddenDangerRecord> recordList) {
        this.recordList = recordList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hidden_danger_statistics_of_repeat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
//        if(recordList.size()>0){
//            ((ViewHolder) holder).tvHiddenUnits.setText(recordList.get(position).getTeamGroupName());
//            ((ViewHolder) holder).tvTimeOrOrder.setText(recordList.get(position).getFindTime()+"/"+recordList.get(position).getClassName());
//            ((ViewHolder) holder).tvHiddenContent.setText(recordList.get(position).getContent());
//            ((ViewHolder) holder).tvHiddenDangerBelongs.setText(recordList.get(position).getHiddenBelong());
//        }
//
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(holder.itemView.getContext(),
//                        HiddenRiskRecordDetailActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("hiddenDangerRecord",recordList.get(position));
//                intent.putExtra("hiddenrecordBundle",bundle);
//                holder.itemView.getContext().startActivity(intent);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return recordList.size();
    }



    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvHiddenContent;
        private TextView tvNameOfTheProfessional;
        private TextView tvHazardLevel;
        private TextView tvHiddenTroubleDescription;
        private TextView tvRepeatTheNumber;


        ViewHolder(View view) {
            super(view);

            tvHiddenContent = view.findViewById(R.id.tv_hidden_content);
            tvNameOfTheProfessional = view.findViewById(R.id.tv_name_of_the_professional);
            tvHazardLevel = view.findViewById(R.id.tv_hazard_level);
            tvHiddenTroubleDescription = view.findViewById(R.id.tv_hidden_trouble_description);
            tvRepeatTheNumber = view.findViewById(R.id.tv_repeat_the_number);
        }
    }
}
