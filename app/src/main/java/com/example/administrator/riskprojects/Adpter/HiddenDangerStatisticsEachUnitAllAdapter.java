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
import com.example.administrator.riskprojects.activity.HiddenDangerStatisticsEachUnitDetailActivity;
import com.example.administrator.riskprojects.activity.HiddenRiskRecordDetailActivity;

public class HiddenDangerStatisticsEachUnitAllAdapter extends RecyclerView.Adapter {
//    private List<ThreeFix> threeFixList;



    public HiddenDangerStatisticsEachUnitAllAdapter() {
//        this.threeFixList = threeFixList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hidden_danger_statistics_of_each_unit_all, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
//        if(threeFixList.size()>0){
//            ((ViewHolder) holder).tvHiddenContent.setText(threeFixList.get(position).getContent());
//            ((ViewHolder) holder).tvArea.setText(threeFixList.get(position).getAreaName());
//            ((ViewHolder) holder).tvSpecialty.setText(threeFixList.get(position).getSname());
//            ((ViewHolder) holder).tvTimeOrOrder.setText(threeFixList.get(position).getFindTime()+"/"+threeFixList.get(position).getClassName());
//            ((ViewHolder) holder).tvCategory.setText(threeFixList.get(position).getGname());
//            ((ViewHolder) holder).tvSupervise.setText(threeFixList.get(position).getIshandle());
//        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=  new Intent( holder.itemView.getContext(),
                        HiddenDangerStatisticsEachUnitDetailActivity.class);
//                ThreeFix threeFix = threeFixList.get(position);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("threeFix",threeFix);
//                intent.putExtra("threeBund",bundle);
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return 6;
    }



    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvHiddenUnits;
        private TextView tvNumberOfProcessed;
        private TextView tvNumberOfUntreated;
        private TextView tvNumberOdAll;
        private TextView tvMoney;

        ViewHolder(View view) {
            super(view);



            tvHiddenUnits = view.findViewById(R.id.tv_hidden_units);
            tvNumberOfProcessed = view.findViewById(R.id.tv_number_of_processed);
            tvNumberOfUntreated = view.findViewById(R.id.tv_number_of_untreated);
            tvNumberOdAll = view.findViewById(R.id.tv_number_od_all);
            tvMoney = view.findViewById(R.id.tv_money);
        }
    }
}
