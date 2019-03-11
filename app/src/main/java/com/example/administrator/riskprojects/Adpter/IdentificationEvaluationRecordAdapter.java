package com.example.administrator.riskprojects.Adpter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.bean.IdentificationEvaluationRecord;

import java.util.List;

public class IdentificationEvaluationRecordAdapter extends RecyclerView.Adapter {


    List<IdentificationEvaluationRecord> hiddenDangerRecordList;

    public IdentificationEvaluationRecordAdapter(List<IdentificationEvaluationRecord> recordList) {
            this.hiddenDangerRecordList = recordList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_identification_evaluatioin_record, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        String addTime = hiddenDangerRecordList.get(position).getAddTime();
        String consider = hiddenDangerRecordList.get(position).getConsider();
        String deptName = hiddenDangerRecordList.get(position).getDeptName();
        String dutyPerName = hiddenDangerRecordList.get(position).getDutyPerName();
        String management = hiddenDangerRecordList.get(position).getManagement();
        ((ViewHolder) holder).tvAddTime.setText(addTime);
        ((ViewHolder) holder).tvConsider.setText(consider);
        ((ViewHolder) holder).tvDeptName.setText(deptName);
        ((ViewHolder) holder).tvDutyPerName.setText(dutyPerName);
        ((ViewHolder) holder).tvManagement.setText(management);
    }



    @Override
    public int getItemCount() {
        return hiddenDangerRecordList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvAddTime;
        private TextView tvConsider;
        private TextView tvDeptName;
        private TextView tvDutyPerName;
        private TextView tvManagement;

        ViewHolder(View view) {
            super(view);

            tvAddTime = view.findViewById(R.id.tv_addTime);
            tvConsider = view.findViewById(R.id.tv_consider);
            tvDeptName = view.findViewById(R.id.tv_deptName);
            tvDutyPerName = view.findViewById(R.id.tv_dutyPerName);
            tvManagement = view.findViewById(R.id.tv_management);
        }
    }
}