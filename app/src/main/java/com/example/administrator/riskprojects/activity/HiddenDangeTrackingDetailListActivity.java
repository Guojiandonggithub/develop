package com.example.administrator.riskprojects.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.administrator.riskprojects.Adpter.HiddenDangeMuitipleAdapter;
import com.example.administrator.riskprojects.Adpter.HomeHiddenDangerdetailListAdapter;
import com.example.administrator.riskprojects.BaseActivity;
import com.example.administrator.riskprojects.OnItemClickListener;
import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.bean.HiddenFollingRecord;
import com.example.administrator.riskprojects.bean.ThreeFix;
import com.example.administrator.riskprojects.net.BaseJsonRes;
import com.example.administrator.riskprojects.net.NetClient;
import com.example.administrator.riskprojects.tools.Constants;
import com.example.administrator.riskprojects.tools.UserUtils;
import com.example.administrator.riskprojects.tools.Utils;
import com.example.administrator.riskprojects.view.MyAlertDialog;
import com.juns.health.net.loopj.android.http.RequestParams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HiddenDangeTrackingDetailListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "HiddenDangeTrackingDeta";
    protected NetClient netClient;
    private TextView mTxtLeft;
    private ImageView mImgLeft;
    private TextView mTxtTitle;
    private ImageView mImgRight;
    private TextView mTxtRight;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private HomeHiddenDangerdetailListAdapter adapter;
    private List<HiddenFollingRecord> hiddenFollingRecordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden_dange_tracking_detail_list);
        netClient = new NetClient(HiddenDangeTrackingDetailListActivity.this);
        initView();
        setView();
        initdata();
    }

    private void setView() {
        mTxtTitle.setText(R.string.detail);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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

    private void initdata(){
        Intent intent = getIntent();
        String threeFixId = intent.getStringExtra("threeFixId");
        getFollingRecordList(threeFixId,Constants.PAGE);
    }

    private void getFollingRecordList(String threeFixId,String page) {//隐患跟踪记录查询
        RequestParams params = new RequestParams();
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("page", page);
        paramsMap.put("rows", Constants.ROWS);
        paramsMap.put("threeFixId", threeFixId);
        String jsonString = JSON.toJSONString(paramsMap);
        params.put("follingRecordJsonData", jsonString);
        netClient.post(Constants.GET_FOLLINGRECORD, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "隐患跟踪记录查询返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    JSONObject returndata = JSON.parseObject(data);
                    String rows = returndata.getString("rows");
                    String page = returndata.getString("page");
                    String pagesize = returndata.getString("pagesize");
                    hiddenFollingRecordList = JSONArray.parseArray(rows, HiddenFollingRecord.class);
                    adapter = new HomeHiddenDangerdetailListAdapter(hiddenFollingRecordList);
                    mRecyclerView.setAdapter(adapter);
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
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "隐患跟踪记录查询返回错误信息：" + content);
                Utils.showLongToast(HiddenDangeTrackingDetailListActivity.this, content);
            }
        });
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
