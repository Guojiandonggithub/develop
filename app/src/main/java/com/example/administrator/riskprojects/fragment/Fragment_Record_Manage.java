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
        mRecyclerView.setAdapter(new ListingSupervisionAdapter());
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
            default:
                break;
        }
    }
}