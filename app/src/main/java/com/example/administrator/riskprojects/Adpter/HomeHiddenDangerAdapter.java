package com.example.administrator.riskprojects.Adpter;

import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.riskprojects.R;

public class HomeHiddenDangerAdapter extends RecyclerView.Adapter {


    public HomeHiddenDangerAdapter() {
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hidden_danger_statistics_of_each_unit, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
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
            mTvName02 = view.findViewById(R.id.tv_name_02);
            mTvProcessedNum02 = view.findViewById(R.id.tv_processed_num_02);
            mTvUntreatedNum02 = view.findViewById(R.id.tv_untreated_num_02);
            mTvName03 =view. findViewById(R.id.tv_name_03);
            mTvProcessedNum03 = view.findViewById(R.id.tv_processed_num_03);
            mTvUntreatedNum03 =view. findViewById(R.id.tv_untreated_num_03);
            mClickMore =view. findViewById(R.id.click_more);
        }
    }
}
