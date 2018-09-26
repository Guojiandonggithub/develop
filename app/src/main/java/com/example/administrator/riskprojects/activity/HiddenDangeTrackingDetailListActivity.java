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
import android.widget.Toast;

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
import com.example.administrator.riskprojects.util.DensityUtil;
import com.example.administrator.riskprojects.view.MyAlertDialog;
import com.juns.health.net.loopj.android.http.RequestParams;

import java.util.ArrayList;
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
    private List<HiddenFollingRecord> hiddenFollingRecordList = new ArrayList<HiddenFollingRecord>();
    private JSONArray jsonArray;
    private boolean onLoading = false;
    private int page = 1;
    private int pagesize = 1;

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
        adapter = new HomeHiddenDangerdetailListAdapter(hiddenFollingRecordList);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position, int flag) {
                switch (flag) {
                    case HomeHiddenDangerdetailListAdapter.FLAG_CHANGE:
                        Intent intent = new Intent(HiddenDangeTrackingDetailListActivity.this,
                                HiddenRiskTrackingAddEditActivity.class);
                        JSONObject jsonObject = jsonArray.getJSONObject(position);
                        intent.putExtra("follingRecord", jsonObject.toString());
                        String id = jsonObject.getString("follingRecordId");
                        Log.e(TAG, "onItemClick: 跟踪id======" + id);
                        intent.putExtra("id", id);
                        startActivity(intent);
                        break;
                    case HomeHiddenDangerdetailListAdapter.FLAG_DELETE:
                        MyAlertDialog alertDialog = new MyAlertDialog(HiddenDangeTrackingDetailListActivity.this
                                , new MyAlertDialog.DialogListener() {
                            @Override
                            public void affirm() {
                                JSONObject jsonObject = jsonArray.getJSONObject(position);
                                String id = jsonObject.getString("follingRecordId");
                                Log.e(TAG, "onItemClick: 跟踪id======" + id);
                                deleteHiddenTrackingRecord(id);
                                //adapter.notifyItemRemoved(position);
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
                                && Math.abs(lastChildBottom - recyclerBottom) <= DensityUtil.dip2px(HiddenDangeTrackingDetailListActivity.this,
                                8)
                                && lastPosition == recyclerView.getLayoutManager().getItemCount() - 1
                                && newState == RecyclerView.SCROLL_STATE_IDLE) {
                            //loading more data

                            if (page < pagesize) {
                                getFollingRecordList(getIntent().getStringExtra("threeFixId"),Integer.toString(page + 1));
                            } else {
                                Toast.makeText(HiddenDangeTrackingDetailListActivity.this, "全部加载完毕", Toast.LENGTH_SHORT).show();
                            }


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
    }

    private void initdata() {
        Intent intent = getIntent();
        String threeFixId = intent.getStringExtra("threeFixId");
        getFollingRecordList(threeFixId, Constants.PAGE);
    }

    private void getFollingRecordList(String threeFixId, final String curpage) {//隐患跟踪记录查询
        RequestParams params = new RequestParams();
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("page", curpage);
        paramsMap.put("rows", Constants.ROWS);
        paramsMap.put("threeFixId", threeFixId);
        String jsonString = JSON.toJSONString(paramsMap);
        params.put("follingRecordJsonData", jsonString);
        netClient.post(Data.getInstance().getIp()+Constants.GET_FOLLINGRECORD, params, new BaseJsonRes() {

            @Override
            public void onStart() {
                super.onStart();
                onLoading = true;
                if (curpage.equals("1")) {
                    if (!mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(true);
                    }
                } {
                    Toast.makeText(HiddenDangeTrackingDetailListActivity.this, "正在加载" + curpage + "/" + pagesize, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mSwipeRefreshLayout.setRefreshing(false);
                onLoading = false;
            }

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "隐患跟踪记录查询返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    JSONObject returndata = JSON.parseObject(data);
                    String rows = returndata.getString("rows");
                    String page = returndata.getString("page");
                    String pagesize = returndata.getString("pagesize");
                    List<HiddenFollingRecord>  tempList = JSONArray.parseArray(rows, HiddenFollingRecord.class);
                    jsonArray = returndata.getJSONArray("rows");
                    hiddenFollingRecordList.addAll(tempList);
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "隐患跟踪记录查询返回错误信息：" + content);
                Utils.showLongToast(HiddenDangeTrackingDetailListActivity.this, content);
            }
        });
    }

    //删除跟踪记录
    private void deleteHiddenTrackingRecord(String id) {//隐患跟踪记录id
        RequestParams params = new RequestParams();
        params.put("follingRecordId", id);
        netClient.post(Data.getInstance().getIp()+Constants.DELETE_FOLLINGRECORD, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "删除隐患记录返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    Utils.showLongToast(HiddenDangeTrackingDetailListActivity.this, "删除成功");
                }
                finish();
            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "删除隐患记录返回错误信息：" + content);
                Utils.showLongToast(HiddenDangeTrackingDetailListActivity.this, content);
                return;
            }
        });
    }

    @Override
    public void onRefresh() {
        initdata();
    }
}
