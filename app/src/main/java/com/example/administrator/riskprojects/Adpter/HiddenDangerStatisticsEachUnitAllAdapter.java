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
import com.example.administrator.riskprojects.activity.HiddenDangerStatisticsEachUnitDetailActivity;
import com.example.administrator.riskprojects.activity.HiddenRiskRecordDetailActivity;
import com.example.administrator.riskprojects.bean.HomeHiddenRecord;
import com.example.administrator.riskprojects.bean.ThreeFix;

import java.util.List;

public class HiddenDangerStatisticsEachUnitAllAdapter extends RecyclerView.Adapter {
    private List<HomeHiddenRecord> dtatisticsList;
    private String tvStartDate;
    private String tvEndDate;


    public HiddenDangerStatisticsEachUnitAllAdapter(List<HomeHiddenRecord> dtatisticsList,String tvStartDate,String tvEndDate) {
        this.dtatisticsList = dtatisticsList;
        this.tvStartDate = tvStartDate;
        this.tvEndDate = tvEndDate;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hidden_danger_statistics_of_each_unit_all, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if(dtatisticsList.size()>0){
            ((ViewHolder) holder).tvHiddenUnits.setText(dtatisticsList.get(position).getTeamGroupName());
            ((ViewHolder) holder).tvNumberOfProcessed.setText(dtatisticsList.get(position).getMonth());
            ((ViewHolder) holder).tvNumberOfUntreated.setText(dtatisticsList.get(position).getTotal());
            ((ViewHolder) holder).tvNumberOdAll.setText(dtatisticsList.get(position).getTotalNum());
            ((ViewHolder) holder).tvMoney.setText(dtatisticsList.get(position).getMoney());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(),
                        HiddenDangerStatisticsEachUnitDetailActivity.class);
                String teamGroupCode = dtatisticsList.get(position).getTeamGroupId();
                intent.putExtra("teamGroupCode",teamGroupCode);
                intent.putExtra("getTeamdetaillist","getTeamdetaillist");
                intent.putExtra("tvStartDate",tvStartDate);
                intent.putExtra("tvEndDate",tvEndDate);
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dtatisticsList.size();
    }



    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvHiddenUnits;
        private TextView tvNumberOfProcessed;
        private TextView tvNumberOfUntreated;
        private TextView tvNumberOdAll;
        private TextView tvMoney;

        ViewHolder(View view) {
            super(view);



            tvHiddenUnits = view.findViewById(R.id.tv_hidden_units);
            tvNumberOfProcessed = view.findViewById(R.id.tv_number_of_processed);
            tvNumberOfUntreated = view.findViewById(R.id.tv_number_of_untreated);
            tvNumberOdAll = view.findViewById(R.id.tv_number_od_all);
            tvMoney = view.findViewById(R.id.tv_money);
            tvMoney.setVisibility(View.GONE);
        }
    }
}
