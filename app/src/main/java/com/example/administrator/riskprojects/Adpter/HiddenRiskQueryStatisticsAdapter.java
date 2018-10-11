package com.example.administrator.riskprojects.Adpter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.activity.HiddenDangerDetailManagementActivity;
import com.example.administrator.riskprojects.activity.HiddenRiskRecordDetailActivity;
import com.example.administrator.riskprojects.bean.HiddenDangerRecord;

import java.util.List;

public class HiddenRiskQueryStatisticsAdapter extends RecyclerView.Adapter {


    List<HiddenDangerRecord> recordList;


    public HiddenRiskQueryStatisticsAdapter(List<HiddenDangerRecord> recordList) {
        this.recordList = recordList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hidden_danger_record_management, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((ViewHolder) holder).tvHiddenUnits.setText(recordList.get(position).getTeamGroupName());
        ((ViewHolder) holder).tvTimeOrOrder.setText(recordList.get(position).getFindTime() + "/" + recordList.get(position).getClassName());
        ((ViewHolder) holder).tvHiddenContent.setText(recordList.get(position).getContent());
        ((ViewHolder) holder).tvHiddenDangerBelongs.setText(recordList.get(position).getHiddenBelong());
        ((ViewHolder) holder).tvProfessional.setText(recordList.get(position).getSname());
        ((ViewHolder) holder).tvArea.setText(recordList.get(position).getAreaName());
        ((ViewHolder) holder).tvClasses.setText(recordList.get(position).getGname());
        ((ViewHolder) holder).ivStatus.setVisibility(View.GONE);
        ((ViewHolder) holder).ivStatusSecond.setVisibility(View.GONE);
        String isuper = recordList.get(position).getIsupervision();
        if (TextUtils.isEmpty(isuper) || TextUtils.equals(isuper, "0")) {
            isuper = "未挂牌";
        } else {
            isuper = "已挂牌";
        }
        ((ViewHolder) holder).tvOversee.setText(isuper);
        ((ViewHolder) holder).expand.setVisibility(View.VISIBLE);
        ((ViewHolder) holder).clickMore.setVisibility(View.GONE);

        ((ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(),
                        HiddenRiskRecordDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("hiddenDangerRecord",recordList.get(position));
                intent.putExtra("hiddenrecordBundle",bundle);
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    private int getImageResourceByFlag(String flag,String outTimeFlag) {
        if("1".equals(outTimeFlag)){
            return R.mipmap.ic_status_overdue;
        }
        switch (flag) {
            case "0":
                return R.mipmap.ic_status_shaixuan;
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

    @Override
    public int getItemCount() {
        return recordList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvHiddenUnits;
        private TextView tvTimeOrOrder;
        private TextView tvHiddenContent;
        private TextView tvHiddenDangerBelongs;
        private LinearLayoutCompat expand;
        private TextView tvProfessional;
        private TextView tvArea;
        private TextView tvClasses;
        private TextView tvOversee;
        private ImageView ivStatusSecond;
        private LinearLayoutCompat clickMore;
        private ImageView ivStatus;

        ViewHolder(View view) {
            super(view);
            tvHiddenUnits = view.findViewById(R.id.tv_hidden_units);
            tvTimeOrOrder = view.findViewById(R.id.tv_time_or_order);
            tvHiddenContent = view.findViewById(R.id.tv_hidden_content);
            tvHiddenDangerBelongs = view.findViewById(R.id.tv_hidden_danger_belongs);
            expand = view.findViewById(R.id.expand);
            tvProfessional = view.findViewById(R.id.tv_professional);
            tvArea = view.findViewById(R.id.tv_area);
            tvClasses = view.findViewById(R.id.tv_classes);
            tvOversee = view.findViewById(R.id.tv_oversee);
            ivStatusSecond = view.findViewById(R.id.iv_status_second);
            clickMore = view.findViewById(R.id.click_more);
            ivStatus = view.findViewById(R.id.iv_status);
        }
    }
}
