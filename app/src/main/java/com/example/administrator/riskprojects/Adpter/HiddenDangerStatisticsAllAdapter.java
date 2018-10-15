package com.example.administrator.riskprojects.Adpter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.activity.HiddenDangerStatisticsEachUnitDetailActivity;
import com.example.administrator.riskprojects.bean.HomeHiddenRecord;

import java.util.List;

public class HiddenDangerStatisticsAllAdapter extends RecyclerView.Adapter {
    private static final String TAG = "HiddenDangerStatisticsA";
    private List<HomeHiddenRecord> dtatisticsList;
    private String tvStartDate;
    private String tvEndDate;



    public HiddenDangerStatisticsAllAdapter(List<HomeHiddenRecord> dtatisticsList,String tvStartDate,String tvEndDate) {
        this.dtatisticsList = dtatisticsList;
        this.tvStartDate = tvStartDate;
        this.tvEndDate = tvEndDate;
        Log.e(TAG, "HiddenDangerStatisticsAllAdapter=========="+tvStartDate+"===="+tvEndDate);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hidden_danger_statistics_all, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if(dtatisticsList.size()>0){
            ((ViewHolder) holder).tvHazardClassification.setText(dtatisticsList.get(position).getHiddenBelong());
            ((ViewHolder) holder).tvCheckTheNumber.setText(dtatisticsList.get(position).getCheckNum());
            ((ViewHolder) holder).tvNumberOfUntreated.setText(dtatisticsList.get(position).getQuestionNum());
            ((ViewHolder) holder).tvNumberOfProcessedBars.setText(dtatisticsList.get(position).getHandleNum());
            ((ViewHolder) holder).tvNumberOfUnconsumedBars.setText(dtatisticsList.get(position).getNotHandleNum());
            ((ViewHolder) holder).tvTotal.setText(dtatisticsList.get(position).getTotalMoney());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /*Intent intent = new Intent(holder.itemView.getContext(),
                    HiddenDangerStatisticsEachUnitDetailActivity.class);
            String teamGroupName = dtatisticsList.get(position).getHiddenBelong();
            intent.putExtra("teamGroupName",teamGroupName);
            intent.putExtra("statistics","true");
            intent.putExtra("tvEndDate",tvEndDate);
            intent.putExtra("tvStartDate",tvStartDate);
            holder.itemView.getContext().startActivity(intent);*/
        }
    });

    }

    @Override
    public int getItemCount() {
        return dtatisticsList.size();
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
            tvTotal.setVisibility(View.GONE);
        }
    }
}
