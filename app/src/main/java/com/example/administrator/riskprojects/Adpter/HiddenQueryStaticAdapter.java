package com.example.administrator.riskprojects.Adpter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.activity.HiddenDangerDetailManagementActivity;
import com.example.administrator.riskprojects.activity.HiddenDangerDetailManagementAddOrDetailActivity;
import com.example.administrator.riskprojects.activity.HiddenRiskRecordDetailActivity;
import com.example.administrator.riskprojects.bean.HomeHiddenRecord;

import java.util.List;

public class HiddenQueryStaticAdapter extends RecyclerView.Adapter {
    private List<HomeHiddenRecord> recordList;


    public HiddenQueryStaticAdapter(List<HomeHiddenRecord> recordList) {
        this.recordList = recordList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hidden_query_static, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (recordList.size() > 0) {
            ((ViewHolder) holder).tvHiddenContent.setText(recordList.get(position).getContent());
            ((ViewHolder) holder).tvNameOfTheProfessional.setText(recordList.get(position).getSname());
            ((ViewHolder) holder).tvHazardLevel.setText(recordList.get(position).getJbName());
            ((ViewHolder) holder).tvCheckUnit.setText(recordList.get(position).getHiddenBelong());
            ((ViewHolder) holder).tvHiddenUnits.setText(recordList.get(position).getTeamGroupName());
            String isuper = recordList.get(position).getIsupervision();
            if (TextUtils.isEmpty(isuper) || TextUtils.equals(isuper, "0")) {
                isuper = "否";
            } else {
                isuper = "是";
            }
            ((ViewHolder) holder).tvIsSupervisor.setText(isuper);
            ((ViewHolder) holder).tvFindTime.setText(recordList.get(position).getFindtime());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(),
                        HiddenDangerDetailManagementActivity.class);
                String id = recordList.get(position).getId();
                String employeeId = recordList.get(position).getEmployeeId();
                intent.putExtra("id", id);
                intent.putExtra("employeeId", employeeId);
                intent.putExtra("statistics", "statistics");
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return recordList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvHiddenUnits;
        private TextView tvHiddenContent;
        private TextView tvNameOfTheProfessional;
        private TextView tvHazardLevel;
        private TextView tvIsSupervisor;
        private TextView tvFindTime;
        private TextView tvCheckUnit;


        ViewHolder(View view) {
            super(view);

            tvHiddenUnits = view.findViewById(R.id.tv_hidden_units);
            tvHiddenContent = view.findViewById(R.id.tv_hidden_content);
            tvNameOfTheProfessional = view.findViewById(R.id.tv_name_of_the_professional);
            tvHazardLevel = view.findViewById(R.id.tv_hazard_level);
            tvIsSupervisor = view.findViewById(R.id.tv_is_supervisor);
            tvFindTime = view.findViewById(R.id.tv_find_time);
            tvCheckUnit = view.findViewById(R.id.tv_check_unit);
        }
    }
}
