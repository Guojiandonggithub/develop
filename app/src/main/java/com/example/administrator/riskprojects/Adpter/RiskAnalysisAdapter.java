package com.example.administrator.riskprojects.Adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.bean.RiskCountJb;

import java.util.List;

public class RiskAnalysisAdapter extends RecyclerView.Adapter {

    Context context;
    List<RiskCountJb> riskCountJbList;

    public RiskAnalysisAdapter(List<RiskCountJb> riskCountJbList, Context context) {
            this.riskCountJbList = riskCountJbList;
            this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_risk_analysis, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        String riskGname = riskCountJbList.get(position).getRiskGname();
        String total = riskCountJbList.get(position).getTotal();
        int color = riskCountJbList.get(position).getColor();
        ((ViewHolder) holder).riskname.setText(riskGname);
        ((ViewHolder) holder).riskname.setTextColor(color);
        ((ViewHolder) holder).risktotal.setText(total);
    }

    @Override
    public int getItemCount() {
        return riskCountJbList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView riskname;
        private TextView risktotal;

        ViewHolder(View view) {
            super(view);
            riskname = view.findViewById(R.id.riskname);
            risktotal = view.findViewById(R.id.risktotal);
        }
    }
}