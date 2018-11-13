package com.example.administrator.riskprojects.Adpter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.activity.HiddenDangerDetailManagementActivity;
import com.example.administrator.riskprojects.bean.HiddenDangerRecord;
import com.example.administrator.riskprojects.bean.ThreeFix;

import java.util.List;

public class HomePageTotalDetailAdapter extends RecyclerView.Adapter {


    List<HiddenDangerRecord> hiddenDangerRecordList;
    List<ThreeFix> threeFixList;
    String datatype;


    public HomePageTotalDetailAdapter(String rows,String datatype) {
        if(datatype.equals("mLlDeleteNum")||datatype.equals("mLlUnchangeNum")){
            List<HiddenDangerRecord> recordList = JSONArray.parseArray(rows, HiddenDangerRecord.class);
            this.hiddenDangerRecordList = recordList;
        }else{
            List<ThreeFix> recordList = JSONArray.parseArray(rows, ThreeFix.class);
            this.threeFixList = recordList;
        }
        this.datatype = datatype;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_homepage_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        String hiddenContent;
        String nameOfTheProfessional;
        String hazardLevel;
        String checkUnit;
        String hiddenUnits;
        String findTime;
        String findClass;
        String hiddenType;
        String area;
        if(datatype.equals("mLlDeleteNum")||datatype.equals("mLlUnchangeNum")){
            hiddenContent = hiddenDangerRecordList.get(position).getContent();
            nameOfTheProfessional = hiddenDangerRecordList.get(position).getSname();
            hazardLevel = hiddenDangerRecordList.get(position).getJbName();
            checkUnit = hiddenDangerRecordList.get(position).getHiddenBelong();
            hiddenUnits = hiddenDangerRecordList.get(position).getTeamGroupName();
            findTime = hiddenDangerRecordList.get(position).getFindTime();
            findClass = hiddenDangerRecordList.get(position).getClassName();
            hiddenType = hiddenDangerRecordList.get(position).getTname();
            area = hiddenDangerRecordList.get(position).getAreaName();
            ((HomePageTotalDetailAdapter.ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(holder.itemView.getContext(),
                            HiddenDangerDetailManagementActivity.class);
                    intent.putExtra("id",hiddenDangerRecordList.get(position).getId());
                    intent.putExtra("hiddenriskrecorddetail",hiddenDangerRecordList.get(position).getId());
                    holder.itemView.getContext().startActivity(intent);
                }
            });
        }else{
            hiddenContent = threeFixList.get(position).getContent();
            nameOfTheProfessional = threeFixList.get(position).getSname();
            hazardLevel = threeFixList.get(position).getJbName();
            checkUnit = threeFixList.get(position).getHiddenBelong();
            hiddenUnits = threeFixList.get(position).getTeamGroupName();
            findTime = threeFixList.get(position).getFindTime();
            findClass = threeFixList.get(position).getClassName();
            hiddenType = threeFixList.get(position).getTname();
            area = threeFixList.get(position).getAreaName();
            ((HomePageTotalDetailAdapter.ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(holder.itemView.getContext(),
                            HiddenDangerDetailManagementActivity.class);
                    intent.putExtra("id",threeFixList.get(position).getHiddenDangerId());
                    intent.putExtra("hiddenriskrecorddetail",threeFixList.get(position).getHiddenDangerId());
                    holder.itemView.getContext().startActivity(intent);
                }
            });
        }

        ((ViewHolder) holder).tvHiddenContent.setText(hiddenContent);
        ((ViewHolder) holder).tvNameOfTheProfessional.setText(nameOfTheProfessional);
        ((ViewHolder) holder).tvHazardLevel.setText(hazardLevel);
        ((ViewHolder) holder).tvCheckUnit.setText(checkUnit);
        ((ViewHolder) holder).tvHiddenUnits.setText(hiddenUnits);
        ((ViewHolder) holder).tvFindTime.setText(findTime);
        ((ViewHolder) holder).tvFindClass.setText(findClass);
        ((ViewHolder) holder).tvHiddenType.setText(hiddenType);
        ((ViewHolder) holder).tvArea.setText(area);

    }



    @Override
    public int getItemCount() {
        if(datatype.equals("mLlDeleteNum")||datatype.equals("mLlUnchangeNum")){
            return hiddenDangerRecordList.size();
        }else{
            return threeFixList.size();
        }
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvHiddenUnits;
        private TextView tvHiddenContent;
        private TextView tvNameOfTheProfessional;
        private TextView tvHazardLevel;
        private TextView tvFindTime;
        private TextView tvCheckUnit;
        private TextView tvFindClass;
        private TextView tvHiddenType;
        private TextView tvArea;

        ViewHolder(View view) {
            super(view);

            tvHiddenUnits = view.findViewById(R.id.tv_hidden_units);
            tvHiddenContent = view.findViewById(R.id.tv_hidden_content);
            tvNameOfTheProfessional = view.findViewById(R.id.tv_name_of_the_professional);
            tvHazardLevel = view.findViewById(R.id.tv_hazard_level);
            tvFindTime = view.findViewById(R.id.tv_find_time);
            tvCheckUnit = view.findViewById(R.id.tv_check_unit);
            tvFindClass = view.findViewById(R.id.tv_find_class);
            tvHiddenType = view.findViewById(R.id.tv_hidden_type);
            tvArea = view.findViewById(R.id.tv_area);
        }
    }
}