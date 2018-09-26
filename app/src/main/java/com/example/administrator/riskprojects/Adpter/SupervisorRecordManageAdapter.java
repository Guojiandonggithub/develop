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

import java.util.List;

public class SupervisorRecordManageAdapter extends RecyclerView.Adapter {


    List<HiddenDangerRecord> recordList;


    public SupervisorRecordManageAdapter(List<HiddenDangerRecord> recordList) {
        this.recordList = recordList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_supervise_record_management, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {


    }



    @Override
    public int getItemCount() {
        return 6;
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