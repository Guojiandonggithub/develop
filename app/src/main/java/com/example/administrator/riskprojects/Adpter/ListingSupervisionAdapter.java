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
import com.example.administrator.riskprojects.activity.HiddenDangerDetailManagementActivity;
import com.example.administrator.riskprojects.activity.HiddenDangerDetailManagementAddOrDetailActivity;
import com.example.administrator.riskprojects.bean.HiddenDangerRecord;

import java.util.List;

public class ListingSupervisionAdapter extends RecyclerView.Adapter {
    List<HiddenDangerRecord> recordList;


    public ListingSupervisionAdapter(List<HiddenDangerRecord> recordList) {
        this.recordList = recordList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listing_supervision, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        ((ViewHolder) holder).mTvHiddenUnits.setText(recordList.get(position).getTeamGroupName());
        ((ViewHolder) holder).mTvTimeOrOrder.setText(recordList.get(position).getFindTime()+"/"+recordList.get(position).getClassName());
        ((ViewHolder) holder).mIvStatus.setImageResource(getImageResourceByFlag(recordList.get(position).getFlag()));
        ((ViewHolder) holder).mTvHiddenContent.setText(recordList.get(position).getContent());
        ((ViewHolder) holder).mTvHiddenDangerBelongs.setText(recordList.get(position).getHiddenBelong());
        ((ViewHolder) holder).mTvHiddenUnits.setSelected(true);
        ((ViewHolder) holder).mTvTimeOrOrder.setSelected(true);
        ((ViewHolder) holder).mTvHiddenContent.setSelected(true);
        ((ViewHolder) holder).mTvHiddenDangerBelongs.setSelected(true);
        ((ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(),
                        HiddenDangerDetailManagementAddOrDetailActivity.class);
                String id = recordList.get(position).getId();
                intent.putExtra("id", id);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recordList.size();
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

    private int getImageResourceByFlag(String flag) {
        switch (flag) {
            case "1":
                return R.mipmap.ic_status_release;
            case "2":
                return R.mipmap.ic_status_rectificationg;
            case "3":
                return R.mipmap.ic_recheck;
            case "4":
                return R.mipmap.ic_status_dispelling;
            case "5":
                return R.mipmap.ic_status_release;
            default:
                return R.mipmap.ic_status_overdue;
        }
    }

}
