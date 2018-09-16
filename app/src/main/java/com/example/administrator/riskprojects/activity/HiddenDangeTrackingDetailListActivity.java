package com.example.administrator.riskprojects.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.riskprojects.Adpter.HomeHiddenDangerdetailListAdapter;
import com.example.administrator.riskprojects.BaseActivity;
import com.example.administrator.riskprojects.OnItemClickListener;
import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.view.MyAlertDialog;

public class HiddenDangeTrackingDetailListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    private TextView mTxtLeft;
    private ImageView mImgLeft;
    private TextView mTxtTitle;
    private ImageView mImgRight;
    private TextView mTxtRight;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private HomeHiddenDangerdetailListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden_dange_tracking_detail_list);
        initView();
        setView();
    }

    private void setView() {
        mTxtTitle.setText(R.string.detail);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HomeHiddenDangerdetailListAdapter();
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position, int flag) {
                switch (flag) {
                    case HomeHiddenDangerdetailListAdapter.FLAG_CHANGE:
                        Intent intent = new Intent(HiddenDangeTrackingDetailListActivity.this,
                                HiddenRiskTrackingAddEditActivity.class);
                        intent.putExtra(HiddenRiskTrackingAddEditActivity.CHANGE_ID, "" + position);
                        intent.putExtra(HiddenRiskTrackingAddEditActivity.CHANGE_DATE, "2018-09-1" + position);
                        intent.putExtra(HiddenRiskTrackingAddEditActivity.CHANGE_CONTENT, "隐患内容" + position);
                        startActivity(intent);
                        break;
                    case HomeHiddenDangerdetailListAdapter.FLAG_DELETE:
                        MyAlertDialog alertDialog = new MyAlertDialog(HiddenDangeTrackingDetailListActivity.this
                                , new MyAlertDialog.DialogListener() {
                            @Override
                            public void affirm() {
                                adapter.notifyItemRemoved(position);
                            }

                            @Override
                            public void cancel() {

                            }
                        }, "你确定要删除选中的数据么？");
                        alertDialog.show();
                        break;
                }
            }

            @Override
            public boolean onItemLongClick(View view, int position) {
                return false;
            }
        });
        mRecyclerView.setAdapter(adapter);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void initView() {
        mTxtLeft = findViewById(R.id.txt_left);
        mImgLeft = findViewById(R.id.img_left);
        mTxtTitle = findViewById(R.id.txt_title);
        mImgRight = findViewById(R.id.img_right);
        mTxtRight = findViewById(R.id.txt_right);
        mSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = findViewById(R.id.recyclerView);
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
