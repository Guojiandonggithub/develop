package com.example.administrator.riskprojects.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.riskprojects.Adpter.ListingSupervisionAdapter;
import com.example.administrator.riskprojects.Adpter.SideBar;
import com.example.administrator.riskprojects.R;

//通讯录

public class Fragment_Supervision extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private Activity ctx;
    private View layout;
    private WindowManager mWindowManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (layout == null) {
            ctx = this.getActivity();
            layout = ctx.getLayoutInflater().inflate(R.layout.fragment_friends,
                    null);
            mWindowManager = (WindowManager) ctx
                    .getSystemService(Context.WINDOW_SERVICE);
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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ctx));
        mRecyclerView.setAdapter(new ListingSupervisionAdapter());
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    //下拉刷新
    //加载结束后需要调用    mSwipeRefreshLayout.setRefreshing(false);
    @Override
    public void onRefresh() {
        mRecyclerView.getAdapter().notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
