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
import com.example.administrator.riskprojects.activity.HiddenDangerOverdueManagementActivity;
import com.example.administrator.riskprojects.activity.HiddenDangerRectificationManagementActivity;
import com.example.administrator.riskprojects.activity.HiddenDangerReleaseManagementActivity;
import com.example.administrator.riskprojects.activity.HiddenDangerReviewManagementActivity;
import com.example.administrator.riskprojects.bean.ThreeFix;

import java.util.List;

public class HiddenDangeMuitipleAdapter extends RecyclerView.Adapter {

    public static final int FLAG_OVERDUE = 0;
    public static final int FLAG_REALEASE = 1;
    public static final int FLAG_REVIEW = 2;
    public static final int FLAG_RECTIFICATION = 3;
    private int flag = FLAG_OVERDUE;
    List<ThreeFix> threeFixList;

    public HiddenDangeMuitipleAdapter(int flag, List<ThreeFix> threeFixList) {
        this.flag = flag;
        this.threeFixList = threeFixList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hidden_danger_management, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((ViewHolder) holder).tvHiddenContent.setText(threeFixList.get(position).getContent());
        ((ViewHolder) holder).tvArea.setText(threeFixList.get(position).getAreaName());
        ((ViewHolder) holder).tvSpecialty.setText(threeFixList.get(position).getSname());
        ((ViewHolder) holder).tvTimeOrOrder.setText(threeFixList.get(position).getClassName());
        ((ViewHolder) holder).tvCategory.setText(threeFixList.get(position).getGname());
        ((ViewHolder) holder).tvSupervise.setText(threeFixList.get(position).getIshandle());
        switch (flag) {
            case FLAG_OVERDUE:
                ((ViewHolder) holder).button.setText("重新下达");
                ((ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(holder.itemView.getContext(),
                                HiddenDangerOverdueManagementActivity.class);
                        intent.putExtra("id",threeFixList.get(position).getId());
                        intent.putExtra("hiddenDangerId",threeFixList.get(position).getHiddenDangerId());
                        holder.itemView.getContext().startActivity(intent);
                    }
                });
                break;
            case FLAG_REALEASE:
                ((ViewHolder) holder).button.setText("五定");
                ((ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(holder.itemView.getContext(),
                                HiddenDangerReleaseManagementActivity.class);
                        intent.putExtra("id",threeFixList.get(position).getId());
                        intent.putExtra("hiddenDangerId",threeFixList.get(position).getHiddenDangerId());
                        holder.itemView.getContext().startActivity(intent);
                    }
                });
                break;

            case FLAG_REVIEW:
                ((ViewHolder) holder).button.setText("验收");
                ((ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(holder.itemView.getContext(),
                                HiddenDangerReviewManagementActivity.class);
                        intent.putExtra("id",threeFixList.get(position).getId());
                        intent.putExtra("hiddenDangerId",threeFixList.get(position).getHiddenDangerId());
                        holder.itemView.getContext().startActivity(intent);
                    }
                });
                break;

            case FLAG_RECTIFICATION:
                ((ViewHolder) holder).button.setText("整改");
                ((ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(holder.itemView.getContext(),
                                HiddenDangerRectificationManagementActivity.class);
                        intent.putExtra("id",threeFixList.get(position).getId());
                        intent.putExtra("hiddenDangerId",threeFixList.get(position).getHiddenDangerId());
                        holder.itemView.getContext().startActivity(intent);
                    }
                });
                break;
        }

    }

    @Override
    public int getItemCount() {
        return threeFixList.size();
    }

    private void initView(View view) {

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvHiddenContent;
        private TextView tvArea;
        private TextView tvSpecialty;
        private TextView tvTimeOrOrder;
        private TextView tvCategory;
        private TextView tvSupervise;
        private TextView button;
        private LinearLayoutCompat clickMore;

        ViewHolder(View view) {
            super(view);
            tvHiddenContent = view.findViewById(R.id.tv_hidden_content);
            tvArea = view.findViewById(R.id.tv_area);
            tvSpecialty = view.findViewById(R.id.tv_specialty);
            tvTimeOrOrder = view.findViewById(R.id.tv_time_or_order);
            tvCategory = view.findViewById(R.id.tv_category);
            tvSupervise = view.findViewById(R.id.tv_supervise);
            clickMore = view.findViewById(R.id.click_more);
            button = view.findViewById(R.id.button);
        }
    }
}
