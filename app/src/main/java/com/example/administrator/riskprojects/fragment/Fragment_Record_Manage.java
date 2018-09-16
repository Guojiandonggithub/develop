package com.example.administrator.riskprojects.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.riskprojects.Adpter.HiddenDangeMuitipleAdapter;
import com.example.administrator.riskprojects.Adpter.HiddenDangeRecordAdapter;
import com.example.administrator.riskprojects.Adpter.HiddenDangeTrackingAdapter;
import com.example.administrator.riskprojects.Adpter.ListingSupervisionAdapter;
import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.activity.HiddenDangeTrackingManagementActivity;
import com.example.administrator.riskprojects.activity.HiddenDangerOverdueManagementActivity;
import com.example.administrator.riskprojects.activity.HiddenDangerRectificationManagementActivity;
import com.example.administrator.riskprojects.activity.HiddenDangerReleaseManagementActivity;
import com.example.administrator.riskprojects.activity.HiddenDangerReviewManagementActivity;
import com.example.administrator.riskprojects.activity.HiddenRiskRecordAddEditActivity;


//我
public class Fragment_Record_Manage extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private Activity ctx;
    private View layout;
    private TextView tvname, tv_accout;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private int flag=0;//选择模块  默认记录管理

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (layout == null) {
            ctx = this.getActivity();
            layout = ctx.getLayoutInflater().inflate(R.layout.fragment_profile,
                    null);
        } else {
            ViewGroup parent = (ViewGroup) layout.getParent();
            if (parent != null) {
                parent.removeView(layout);
            }
        }
        initView(layout);
        return layout;
    }

    private void initView(View layout) {
        mSwipeRefreshLayout = layout.findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = layout.findViewById(R.id.recyclerView);

        mSwipeRefreshLayout = layout.findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = layout.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ctx));
        mRecyclerView.setAdapter(new HiddenDangeRecordAdapter());
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }



    @Override
    public void onRefresh() {
        mRecyclerView.getAdapter().notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public void onRightMenuClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_manage_detail:
                flag = 1;
                mRecyclerView.setAdapter(new HiddenDangeRecordAdapter());
                break;
            case R.id.ll_manage_release:
                flag = 2;
                mRecyclerView.setAdapter(new HiddenDangeMuitipleAdapter(HiddenDangeMuitipleAdapter.FLAG_REALEASE));
                break;
            case R.id.ll_manage_rectification:
                flag = 3;
                mRecyclerView.setAdapter(new HiddenDangeMuitipleAdapter(HiddenDangeMuitipleAdapter.FLAG_RECTIFICATION));
                break;
            case R.id.ll_manage_tracking:
                flag = 4;
                mRecyclerView.setAdapter(new HiddenDangeTrackingAdapter());
                break;
            case R.id.ll_manage_overdue:
                flag = 5;
                mRecyclerView.setAdapter(new HiddenDangeMuitipleAdapter(HiddenDangeMuitipleAdapter.FLAG_OVERDUE));
                break;
            case R.id.ll_manage_review:
                flag = 6;
                mRecyclerView.setAdapter(new HiddenDangeMuitipleAdapter(HiddenDangeMuitipleAdapter.FLAG_REVIEW));
                break;
            default:
                break;
        }
    }

    public String getTitle() {
        switch (flag) {
            case 0:
                return "隐患记录管理";
            case 1:
                return getResources().getString(R.string.hidden_danger_details_management);
            case 2:
                return getResources().getString(R.string.hidden_danger_management);
            case 3:
                return getResources().getString(R.string.hidden_danger_rectification_management);
            case 4:
                return getResources().getString(R.string.hidden_danger_tracking_management);
            case 5:
                return getResources().getString(R.string.risk_overdue_management);
            case 6:
                return getResources().getString(R.string.hidden_danger_review_management);

        }
        return "";
    }
}