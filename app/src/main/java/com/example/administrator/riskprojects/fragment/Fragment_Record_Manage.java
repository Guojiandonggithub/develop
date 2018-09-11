package com.example.administrator.riskprojects.fragment;

import android.app.Activity;
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

import com.example.administrator.riskprojects.Adpter.ListingSupervisionAdapter;
import com.example.administrator.riskprojects.R;


//我
public class Fragment_Record_Manage extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private Activity ctx;
    private View layout;
    private TextView tvname, tv_accout;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private LinearLayoutCompat mLlDialog;
    private LinearLayoutCompat mLlManageDetail;
    private LinearLayoutCompat mLlManageRelease;
    private LinearLayoutCompat mLlManageRectification;
    private LinearLayoutCompat mLlManageTracking;
    private LinearLayoutCompat mLlManageOverdue;
    private LinearLayoutCompat mLlManageReview;
    private View.OnClickListener menuListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onMenuClicked(v);
        }
    };

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
        mLlDialog = layout.findViewById(R.id.ll_dialog);
        mLlManageDetail = layout.findViewById(R.id.ll_manage_detail);
        mLlManageRelease = layout.findViewById(R.id.ll_manage_release);
        mLlManageRectification = layout.findViewById(R.id.ll_manage_rectification);
        mLlManageTracking = layout.findViewById(R.id.ll_manage_tracking);
        mLlManageOverdue = layout.findViewById(R.id.ll_manage_overdue);
        mLlManageReview = layout.findViewById(R.id.ll_manage_review);
        mLlManageDetail.setOnClickListener(menuListener);
        mLlManageRelease.setOnClickListener(menuListener);
        mLlManageRectification.setOnClickListener(menuListener);
        mLlManageTracking.setOnClickListener(menuListener);
        mLlManageOverdue.setOnClickListener(menuListener);
        mLlManageReview.setOnClickListener(menuListener);


        mSwipeRefreshLayout = layout.findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = layout.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ctx));
        mRecyclerView.setAdapter(new ListingSupervisionAdapter());
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    public void onMenuClicked(View view) {
        //img_right.setVisibility(View.GONE);
        switch (view.getId()) {
            case R.id.ll_manage_detail:
                //img_right.setVisibility(View.VISIBLE);

                break;
            case R.id.ll_manage_release:

                break;
            case R.id.ll_manage_rectification:

                break;
            case R.id.ll_manage_tracking:

                break;
            case R.id.ll_manage_overdue:

                break;
            case R.id.ll_manage_review:

                break;
        }
        mLlDialog.setVisibility(View.GONE);
    }

    public void ShowMenuDialog() {
        if (mLlDialog.getVisibility() == View.VISIBLE) {
            mLlDialog.setVisibility(View.GONE);
        } else {
            mLlDialog.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public void onRefresh() {
        mRecyclerView.getAdapter().notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }
}