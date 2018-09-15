package com.example.administrator.riskprojects.Adpter;

import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.riskprojects.R;

public class ListingSupervisionAdapter extends RecyclerView.Adapter {


    public ListingSupervisionAdapter() {
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listing_supervision, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).mTvHiddenUnits.setText("综合挖掘队");
        ((ViewHolder) holder).mTvTimeOrOrder.setText("2016.09.26/0点班");
        ((ViewHolder) holder).mTvHiddenContent.setText("规划局观后感");
        ((ViewHolder) holder).mTvHiddenDangerBelongs.setText("机电矿长杨海涛有点厉害哦。就问你怕不怕");
        ((ViewHolder) holder).mTvHiddenUnits.setSelected(true);
        ((ViewHolder) holder).mTvTimeOrOrder.setSelected(true);
        ((ViewHolder) holder).mTvHiddenContent.setSelected(true);
        ((ViewHolder) holder).mTvHiddenDangerBelongs.setSelected(true);
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    private void initView(View view) {

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvHiddenUnits;
        private TextView mTvTimeOrOrder;
        private TextView mTvHiddenContent;
        private TextView mTvHiddenDangerBelongs;
        private LinearLayoutCompat mClickMore;
        private ImageView mIvStatus;

        ViewHolder(View view) {
            super(view);
            mTvHiddenUnits = view.findViewById(R.id.tv_hidden_units);
            mTvTimeOrOrder = view.findViewById(R.id.tv_time_or_order);
            mTvHiddenContent = view.findViewById(R.id.tv_hidden_content);
            mTvHiddenDangerBelongs = view.findViewById(R.id.tv_hidden_danger_belongs);
            mClickMore = view.findViewById(R.id.click_more);
            mIvStatus = view.findViewById(R.id.iv_status);
        }
    }
}
