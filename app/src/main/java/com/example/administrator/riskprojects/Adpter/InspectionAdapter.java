package com.example.administrator.riskprojects.Adpter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.riskprojects.OnItemClickListener;
import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.bean.CarRecord;

import java.util.List;


public class InspectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CarRecord> recordList;
    private OnItemClickListener onItemClickListener;

    public void setWait(boolean wait) {
        isWait = wait;
    }

    boolean isWait = false;//倒计时

    public InspectionAdapter(List<CarRecord> recordList) {
        this.recordList = recordList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inspection, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        ((ViewHolder) holder).viewTopTop.setVisibility(position == 0 ? View.INVISIBLE : View.VISIBLE);
        ((ViewHolder) holder).viewTop.setSelected(position == getItemCount() - 1);
        ((ViewHolder) holder).viewBottom.setVisibility(position == getItemCount() - 1 ? View.GONE : View.VISIBLE);
        ((ViewHolder) holder).llBottom.setVisibility(position == getItemCount() - 1 && !isWait ? View.GONE : View.VISIBLE);
        if (position == getItemCount() - 1 && !isWait) {
            ((ViewHolder) holder).tvTop.setText("巡检");
            ((ViewHolder) holder).tvTop.setTextColor(Color.parseColor("#999999"));
            ((ViewHolder) holder).tvTop.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        } else {
            ((ViewHolder) holder).tvTop.setText("巡检时间 "+recordList.get(position).getCardTime());
            ((ViewHolder) holder).tvBottom.setText(recordList.get(position).getCardAdd());
            ((ViewHolder) holder).tvTop.setTextColor(Color.parseColor("#000000"));
            ((ViewHolder) holder).tvTop.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

        }


    }

    @Override
    public int getItemCount() {
        return isWait ? recordList.size() : recordList.size() + 1;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        private View viewTopTop;
        private View viewTop;
        private View viewTopBottom;
        private TextView tvTop;
        private LinearLayoutCompat llBottom;
        private View viewBottom;
        private TextView tvBottom;


        ViewHolder(View view) {
            super(view);

            viewTopTop = view.findViewById(R.id.view_top_top);
            viewTop = view.findViewById(R.id.view_top);
            viewTopBottom = view.findViewById(R.id.view_top_bottom);
            tvTop = view.findViewById(R.id.tv_top);
            llBottom = view.findViewById(R.id.ll_bottom);
            viewBottom = view.findViewById(R.id.view_bottom);
            tvBottom = view.findViewById(R.id.tv_bottom);

        }
    }

}
