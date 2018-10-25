package com.example.administrator.riskprojects.Adpter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.bean.HomeHiddenRecord;

import java.util.List;

public class HiddenDangerStatisticsRepeatAdapter extends RecyclerView.Adapter {
    private List<HomeHiddenRecord> recordList;



    public HiddenDangerStatisticsRepeatAdapter(List<HomeHiddenRecord> recordList) {
        this.recordList = recordList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hidden_danger_statistics_of_repeat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if(recordList.size()>0){
            ((ViewHolder) holder).tvHiddenContent.setText(recordList.get(position).getContent());
            ((ViewHolder) holder).tvNameOfTheProfessional.setText(recordList.get(position).getSname());
            ((ViewHolder) holder).tvHazardLevel.setText(recordList.get(position).getJbName());
            //((ViewHolder) holder).tvHiddenTroubleDescription.setText(recordList.get(position).getHiddenBelong());
            ((ViewHolder) holder).tvRepeatTheNumber.setText(recordList.get(position).getTotal());
        }

        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(),
                        HiddenRiskRecordDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("hiddenDangerRecord",recordList.get(position));
                intent.putExtra("hiddenrecordBundle",bundle);
                holder.itemView.getContext().startActivity(intent);
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return recordList.size();
    }



    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvHiddenContent;
        private TextView tvNameOfTheProfessional;
        private TextView tvHazardLevel;
        private TextView tvHiddenTroubleDescription;
        private TextView tvRepeatTheNumber;


        ViewHolder(View view) {
            super(view);

            tvHiddenContent = view.findViewById(R.id.tv_hidden_content);
            tvNameOfTheProfessional = view.findViewById(R.id.tv_name_of_the_professional);
            tvHazardLevel = view.findViewById(R.id.tv_hazard_level);
            //tvHiddenTroubleDescription = view.findViewById(R.id.tv_hidden_trouble_description);
            tvRepeatTheNumber = view.findViewById(R.id.tv_repeat_the_number);
        }
    }
}

