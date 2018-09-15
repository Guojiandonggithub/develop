package com.example.administrator.riskprojects.Adpter;

import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.riskprojects.Constants;
import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.bean.HomeHiddenRecord;

import java.util.List;

public class HomeHiddenDangerAdapter extends RecyclerView.Adapter {
    private static final String TAG = "HomeHiddenDangerAdapter";
    List<HomeHiddenRecord> recordList;
    private boolean isShowMoreButton = true;
    private int needShowMoreButtonNum = 3;


    public HomeHiddenDangerAdapter() {
    }

    public HomeHiddenDangerAdapter(List<HomeHiddenRecord> recordList) {
        this.recordList = recordList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == Constants.TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hidden_danger_statistics_of_each_unit, parent, false);
            return new ViewHolder(view);
        } else if (viewType == Constants.TYPE_FOOTER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footview_more, parent, false);
            return new FootViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).mLlTop.setVisibility(position == 0?View.VISIBLE:View.GONE);
            ((ViewHolder) holder).mLlTopView.setVisibility(position == 0?View.VISIBLE:View.GONE);
            ((ViewHolder) holder).mTvName01.setText(recordList.get(position).getTeamGroupName());
            ((ViewHolder) holder).mTvProcessedNum01.setText(recordList.get(position).getMonth());
            ((ViewHolder) holder).mTvUntreatedNum01.setText(recordList.get(position).getTotal());
            ((ViewHolder) holder).mTvName01.setSelected(true);
            ((ViewHolder) holder).mTvProcessedNum01.setSelected(true);
            ((ViewHolder) holder).mTvUntreatedNum01.setSelected(true);
        } else if (holder instanceof FootViewHolder) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isShowMoreButton = false;
                    notifyItemRangeChanged(needShowMoreButtonNum+1, recordList.size() - needShowMoreButtonNum);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return recordList.size() > needShowMoreButtonNum && isShowMoreButton ? needShowMoreButtonNum+1 : recordList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayoutCompat mLlTop;
        private View mLlTopView;
        private TextView mTvName01;
        private TextView mTvProcessedNum01;
        private TextView mTvUntreatedNum01;

        ViewHolder(View view) {
            super(view);
            mLlTop = view.findViewById(R.id.ll_top);
            mLlTopView = view.findViewById(R.id.ll_top_view);
            mTvName01 = view.findViewById(R.id.tv_name_01);
            mTvProcessedNum01 = view.findViewById(R.id.tv_processed_num_01);
            mTvUntreatedNum01 = view.findViewById(R.id.tv_untreated_num_01);

        }
    }

    static class FootViewHolder extends RecyclerView.ViewHolder {

        FootViewHolder(View view) {
            super(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position != 0 && (isShowMoreButton && recordList.size() > needShowMoreButtonNum && position > needShowMoreButtonNum-1)) {
            return Constants.TYPE_FOOTER;
        } else {
            return Constants.TYPE_ITEM;
        }
    }


}
