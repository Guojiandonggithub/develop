package com.example.administrator.riskprojects.Adpter;

import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.bean.HomeHiddenRecord;

import java.util.List;

public class HomeHiddenDangerAdapter extends RecyclerView.Adapter {
    private static final String TAG = "HomeHiddenDangerAdapter";
    List<HomeHiddenRecord> recordList;

    public HomeHiddenDangerAdapter() {
    }

    public HomeHiddenDangerAdapter(List<HomeHiddenRecord> recordList) {
        this.recordList = recordList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hidden_danger_statistics_of_each_unit, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).mTvName01.setText(recordList.get(position).getTeamGroupName());
        ((ViewHolder) holder).mTvProcessedNum01.setText(recordList.get(position).getMonth());
        ((ViewHolder) holder).mTvUntreatedNum01.setText(recordList.get(position).getTotal());
        ((ViewHolder) holder).mTvName01.setSelected(true);
        ((ViewHolder) holder).mTvProcessedNum01.setSelected(true);
        ((ViewHolder) holder).mTvUntreatedNum01.setSelected(true);
    }

    @Override
    public int getItemCount() {
        return recordList.size();
    }



    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvName01;
        private TextView mTvProcessedNum01;
        private TextView mTvUntreatedNum01;
        private TextView mTvName02;
        private TextView mTvProcessedNum02;
        private TextView mTvUntreatedNum02;
        private TextView mTvName03;
        private TextView mTvProcessedNum03;
        private TextView mTvUntreatedNum03;
        private LinearLayoutCompat mClickMore;

        ViewHolder(View view) {
            super(view);
            mTvName01 = view.findViewById(R.id.tv_name_01);
            mTvProcessedNum01 = view.findViewById(R.id.tv_processed_num_01);
            mTvUntreatedNum01 = view.findViewById(R.id.tv_untreated_num_01);
            /*mTvName02 = view.findViewById(R.id.tv_name_02);
            mTvProcessedNum02 = view.findViewById(R.id.tv_processed_num_02);
            mTvName03 =view. findViewById(R.id.tv_name_03);
            mTvUntreatedNum02 = view.findViewById(R.id.tv_untreated_num_02);
            mTvProcessedNum03 = view.findViewById(R.id.tv_processed_num_03);
            mTvUntreatedNum03 =view.findViewById(R.id.tv_untreated_num_03);*/
            //if(recordList.size()>3){
                mClickMore =view. findViewById(R.id.click_more);
            //}
        }
    }
}
