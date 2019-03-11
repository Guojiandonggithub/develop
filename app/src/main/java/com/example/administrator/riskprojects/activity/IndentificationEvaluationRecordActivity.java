package com.example.administrator.riskprojects.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.example.administrator.riskprojects.Adpter.IdentificationEvaluationRecordAdapter;
import com.example.administrator.riskprojects.BaseActivity;
import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.bean.IdentificationEvaluationRecord;
import com.example.administrator.riskprojects.net.BaseJsonRes;
import com.example.administrator.riskprojects.net.NetClient;
import com.example.administrator.riskprojects.tools.Constants;
import com.example.administrator.riskprojects.tools.Utils;
import com.example.administrator.riskprojects.util.DensityUtil;
import com.juns.health.net.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

public class IndentificationEvaluationRecordActivity extends BaseActivity{
    private static final String TAG = "HiddenDangeTrackingDeta";
    protected NetClient netClient;
    private TextView mTxtLeft;
    private ImageView mImgLeft;
    private TextView mTxtTitle;
    private ImageView mImgRight;
    private TextView mTxtRight;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private IdentificationEvaluationRecordAdapter adapter;
    private List<IdentificationEvaluationRecord> identificationEvaluationRecords = new ArrayList<IdentificationEvaluationRecord>();
    private JSONArray jsonArray;
    private boolean onLoading = false;
    private int page = 1;
    private int pagesize = 1;
    private LinearLayoutCompat layoutEmptyList;
    private TextView errorMessage;
    private TextView errorTips;
    private Button btnRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idenfitication_evaluation_record);
        netClient = new NetClient(IndentificationEvaluationRecordActivity.this);
        initView();
        setView();
        initdata();
    }

    private void setView() {
        mTxtTitle.setText("跟踪记录");
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //mSwipeRefreshLayout.setOnRefreshListener(this);
        adapter = new IdentificationEvaluationRecordAdapter(identificationEvaluationRecords);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (recyclerView.getAdapter().getItemCount() != 0) {
                    //得到当前显示的最后一个item的view
                    View lastChildView = recyclerView.getLayoutManager().getChildAt(recyclerView.getLayoutManager().getChildCount() - 1);
                    if (lastChildView != null) {
                        //得到lastChildView的bottom坐标值
                        int lastChildBottom = lastChildView.getBottom();
                        //得到Recyclerview的底部坐标减去底部padding值，也就是显示内容最底部的坐标
                        int recyclerBottom = recyclerView.getBottom() - recyclerView.getPaddingBottom();
                        //通过这个lastChildView得到这个view当前的position值
                        int lastPosition = recyclerView.getLayoutManager().getPosition(lastChildView);
                        System.out.println("findFirstCompletelyVisibleItemPosition:" + (((LinearLayoutManager) recyclerView.getLayoutManager())
                                .findFirstCompletelyVisibleItemPosition()));
                        System.out.println("Math.abs(lastChildBottom - recyclerBottom):" + Math.abs(lastChildBottom - recyclerBottom));
                        System.out.println("lastPosition:" + lastPosition);
                        System.out.println("recyclerView.getLayoutManager().getItemCount() - 1:" + (recyclerView.getLayoutManager().getItemCount() - 1));
                        //判断lastChildView的bottom值跟recyclerBottom
                        //判断lastPosition是不是最后一个position
                        //如果两个条件都满足则说明是真正的滑动到了底部
                        if (!onLoading && 0 != (((LinearLayoutManager) recyclerView.getLayoutManager())
                                .findFirstCompletelyVisibleItemPosition())
                                && Math.abs(lastChildBottom - recyclerBottom) <= DensityUtil.dip2px(IndentificationEvaluationRecordActivity.this,
                                8)
                                && lastPosition == recyclerView.getLayoutManager().getItemCount() - 1
                                && newState == RecyclerView.SCROLL_STATE_IDLE) {
                            //loading more data

                            /*if (page < pagesize) {
                                getFollingRecordList(getIntent().getStringExtra("threeFixId"), Integer.toString(page + 1));
                            } else {
                                Toast.makeText(IndentificationEvaluationRecordActivity.this, "全部加载完毕", Toast.LENGTH_SHORT).show();
                            }*/
                        }
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }


    private void initView() {
        mTxtLeft = findViewById(R.id.txt_left);
        mImgLeft = findViewById(R.id.img_left);
        mTxtTitle = findViewById(R.id.txt_title);
        mImgRight = findViewById(R.id.img_right);
        mTxtRight = findViewById(R.id.txt_right);
        mSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = findViewById(R.id.recyclerView);
        layoutEmptyList = findViewById(R.id.layout_empty_list);
        errorMessage = findViewById(R.id.error_message);
        errorTips = findViewById(R.id.error_tips);
        btnRefresh = findViewById(R.id.btn_refresh);
        /*btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRefresh();
            }
        });*/
    }

    private void initdata() {
        Intent intent = getIntent();
        String safeId = intent.getStringExtra("safeId");
        getFollingRecordList(safeId, Constants.PAGE);
    }

    private void  getFollingRecordList(String safeId, final String curpage) {//跟踪记录查询
        RequestParams params = new RequestParams();
        params.put("safeId", safeId);
        netClient.post(Data.getInstance().getIp() + Constants.GET_MANAGEMENT_CONTROL, params, new BaseJsonRes() {

            @Override
            public void onFinish() {
                super.onFinish();
                mSwipeRefreshLayout.setRefreshing(false);
                onLoading = false;
                if (adapter.getItemCount() == 0) {
                    layoutEmptyList.setVisibility(View.VISIBLE);
                    errorMessage.setText("没有查询到数据");
                    errorTips.setVisibility(View.GONE);
                }
            }

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "跟踪记录查询返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    List<IdentificationEvaluationRecord> tempList = JSONArray.parseArray(data, IdentificationEvaluationRecord.class);
                    identificationEvaluationRecords.addAll(tempList);
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "跟踪记录查询返回错误信息：" + content);
                Utils.showShortToast(IndentificationEvaluationRecordActivity.this, content);
            }
        });
    }

    //删除跟踪记录
    private void deleteHiddenTrackingRecord(String id) {//隐患跟踪记录id
        RequestParams params = new RequestParams();
        params.put("follingRecordId", id);
        netClient.post(Data.getInstance().getIp() + Constants.DELETE_FOLLINGRECORD, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "删除跟踪记录返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    Utils.showShortToast(IndentificationEvaluationRecordActivity.this, "删除成功");
                }
                finish();
            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "删除跟踪记录返回错误信息：" + content);
                Utils.showShortToast(IndentificationEvaluationRecordActivity.this, content);
                return;
            }
        });
    }
}
