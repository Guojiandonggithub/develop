package com.example.administrator.riskprojects.Adpter;

import android.content.Intent;
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
import com.example.administrator.riskprojects.bean.HiddenDangerRecord;
import com.example.administrator.riskprojects.bean.SupervisionRecord;

import java.util.List;

public class SupervisorRecordManageAdapter extends RecyclerView.Adapter {


    List<SupervisionRecord> recordList;


    public SupervisorRecordManageAdapter(List<SupervisionRecord> recordList) {
        this.recordList = recordList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_supervise_record_management, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((ViewHolder) holder).tvSuperviseDepartment.setText(recordList.get(position).getSupervisionRecordDeptName());
        ((ViewHolder) holder).tvSuperviseMan.setText(recordList.get(position).getSupervisionRecordPersonName());
        ((ViewHolder) holder).tvRecordContents.setText(recordList.get(position).getSupervisionRecord());
        ((ViewHolder) holder).tvRecordingTime.setText(recordList.get(position).getSupervisionTime());

    }



    @Override
    public int getItemCount() {
        return recordList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvSuperviseDepartment;
        private TextView tvSuperviseMan;
        private TextView tvRecordContents;
        private TextView tvRecordingTime;

        ViewHolder(View view) {
            super(view);
            tvSuperviseDepartment = view.findViewById(R.id.tv_supervise_department);
            tvSuperviseMan = view.findViewById(R.id.tv_supervise_man);
            tvRecordContents = view.findViewById(R.id.tv_record_contents);
            tvRecordingTime = view.findViewById(R.id.tv_recording_time);
        }
    }
}