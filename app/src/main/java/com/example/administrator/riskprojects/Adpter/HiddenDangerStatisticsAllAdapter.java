package com.example.administrator.riskprojects.Adpter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.activity.HiddenDangerStatisticsEachUnitDetailActivity;
import com.example.administrator.riskprojects.bean.HomeHiddenRecord;

import java.util.List;

public class HiddenDangerStatisticsAllAdapter extends RecyclerView.Adapter {
//    private List<HomeHiddenRecord> dtatisticsList;



    public HiddenDangerStatisticsAllAdapter() {
//        this.dtatisticsList = dtatisticsList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hidden_danger_statistics_all, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
//        if(dtatisticsList.size()>0){
//            ((ViewHolder) holder).tvHiddenUnits.setText(dtatisticsList.get(position).getTeamGroupName());
//            ((ViewHolder) holder).tvNumberOfProcessed.setText(dtatisticsList.get(position).getMonth());
//            ((ViewHolder) holder).tvNumberOfUntreated.setText(dtatisticsList.get(position).getTotal());
//            ((ViewHolder) holder).tvNumberOdAll.setText(dtatisticsList.get(position).getTotalNum());
//            ((ViewHolder) holder).tvMoney.setText(dtatisticsList.get(position).getMoney());
//        }

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(holder.itemView.getContext(),
//                        HiddenDangerStatisticsEachUnitDetailActivity.class);
//                String teamGroupCode = dtatisticsList.get(position).getTeamGroupCode();
//                intent.putExtra("teamGroupCode",teamGroupCode);
//                holder.itemView.getContext().startActivity(intent);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return 6;
    }



    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvHazardClassification;
        private TextView tvCheckTheNumber;
        private TextView tvNumberOfUntreated;
        private TextView tvNumberOfProcessedBars;
        private TextView tvNumberOfUnconsumedBars;
        private TextView tvTotal;

        ViewHolder(View view) {
            super(view);

            tvHazardClassification = view.findViewById(R.id.tv_hazard_classification);
            tvCheckTheNumber = view.findViewById(R.id.tv_check_the_number);
            tvNumberOfUntreated = view.findViewById(R.id.tv_number_of_untreated);
            tvNumberOfProcessedBars = view.findViewById(R.id.tv_number_of_processed_bars);
            tvNumberOfUnconsumedBars = view.findViewById(R.id.tv_number_of_unconsumed_bars);
            tvTotal = view.findViewById(R.id.tv_total);
        }
    }
}
