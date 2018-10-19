package com.example.administrator.riskprojects.activity;

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
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.administrator.riskprojects.Adpter.HiddenDangerStatisticsEachUnitDetailAdapter;
import com.example.administrator.riskprojects.BaseActivity;
import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.bean.HiddenDangerRecord;
import com.example.administrator.riskprojects.bean.HomeHiddenRecord;
import com.example.administrator.riskprojects.net.BaseJsonRes;
import com.example.administrator.riskprojects.net.NetClient;
import com.example.administrator.riskprojects.tools.Constants;
import com.example.administrator.riskprojects.tools.UserUtils;
import com.example.administrator.riskprojects.tools.Utils;
import com.example.administrator.riskprojects.util.DensityUtil;
import com.juns.health.net.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//隐患列表
public class HiddenDangerStatisticsEachUnitDetailActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "HiddenDangerStatisticsE";
    private TextView txtLeft;
    private ImageView imgLeft;
    private TextView txtTitle;
    private ImageView imgRight;
    private TextView txtRight;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    protected NetClient netClient;
    private boolean onLoading = false;
    private int page = 1;
    private int pagesize = 1;
    private HiddenDangerStatisticsEachUnitDetailAdapter adapter;
    List<HiddenDangerRecord> list = new ArrayList<>();
    private LinearLayoutCompat layoutEmptyList;
    private TextView errorMessage;
    private TextView errorTips;
    private Button btnRefresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden_danger_statistics_each_unit_detail);
        netClient = new NetClient(HiddenDangerStatisticsEachUnitDetailActivity.this);
        initView();
        setView("1");
    }

    private void setView(String curpage) {
        String teamGroupCode = getIntent().getStringExtra("teamGroupCode");
        String tvStartDate = getIntent().getStringExtra("tvStartDate");
        String tvEndDate = getIntent().getStringExtra("tvEndDate");
        Log.i(TAG, "HiddenDangerStatisticsEachUnitDetailActivity========: " + tvStartDate + "===========" + tvEndDate);
        String State = getIntent().getStringExtra("State");
        String statistics = getIntent().getStringExtra("statistics");
        String getTeamdetaillist = getIntent().getStringExtra("getTeamdetaillist");
        if (TextUtils.isEmpty(statistics)) {
            Log.e(TAG, "详情参数:getHiddenRecord==========" + statistics);
            txtTitle.setText(getResources().getString(R.string.hidden_danger_statistics_of_each_unit));
            getHiddenRecord(curpage, teamGroupCode, State, getTeamdetaillist, tvStartDate, tvEndDate);
        } else {
            txtTitle.setText(getResources().getString(R.string.summary_of_hazards));
            String teamGroupName = getIntent().getStringExtra("teamGroupName");
            Log.e(TAG, "详情参数:getNotHandleList==========" + teamGroupName);
            getNotHandleList(curpage, teamGroupName, tvStartDate, tvEndDate);
        }
    }

    private void initView() {

        layoutEmptyList = findViewById(R.id.layout_empty_list);
        errorMessage = findViewById(R.id.error_message);
        errorTips = findViewById(R.id.error_tips);
        btnRefresh = findViewById(R.id.btn_refresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRefresh();
            }
        });
        txtLeft = findViewById(R.id.txt_left);
        imgLeft = findViewById(R.id.img_left);
        txtTitle = findViewById(R.id.txt_title);
        imgRight = findViewById(R.id.img_right);
        txtRight = findViewById(R.id.txt_right);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipeRefreshLayout.setOnRefreshListener(this);
        adapter = new HiddenDangerStatisticsEachUnitDetailAdapter(list);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

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
                        //判断lastChildView的bottom值跟recyclerBottom
                        //判断lastPosition是不是最后一个position
                        //如果两个条件都满足则说明是真正的滑动到了底部
                        if (!onLoading && 0 != (((LinearLayoutManager) recyclerView.getLayoutManager())
                                .findFirstCompletelyVisibleItemPosition())
                                && Math.abs(lastChildBottom - recyclerBottom) <= DensityUtil.dip2px(HiddenDangerStatisticsEachUnitDetailActivity.this, 8)
                                && lastPosition == recyclerView.getLayoutManager().getItemCount() - 1
                                && newState == RecyclerView.SCROLL_STATE_IDLE) {
                            //loading more data

                            if (page < pagesize) {
                                setView(Integer.toString(page + 1));
                            } else {
                                Toast.makeText(HiddenDangerStatisticsEachUnitDetailActivity.this, "全部加载完毕", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onRefresh() {
        setView("1");
    }

    //获取未消耗的数据
    private void getNotHandleList(final String curpage, String teamGroupName, String tvStartDate, String tvEndDate) {
        RequestParams params = new RequestParams();
        Map<String, String> paramsMap = new HashMap<String, String>();
        if (!TextUtils.isEmpty(teamGroupName)) {
            paramsMap.put("teamGroupName", teamGroupName);
        }
        if (!TextUtils.isEmpty(tvStartDate)) {
            params.put("customParamsOne", tvStartDate);//开始时间
        }
        if (!TextUtils.isEmpty(tvEndDate)) {
            params.put("customParamsTwo", tvEndDate);//结束时间
        }
        Log.i(TAG, "详情参数:" + params);
        paramsMap.put("page", curpage);
        paramsMap.put("rows", "100");
        paramsMap.put("employeeId", UserUtils.getUserID(HiddenDangerStatisticsEachUnitDetailActivity.this));
        netClient.post(Data.getInstance().getIp() + Constants.GET_NOTHANDLELIST, params, new BaseJsonRes() {

            @Override
            public void onStart() {
                super.onStart();
                onLoading = true;
                layoutEmptyList.setVisibility(View.GONE);
                if ("1".equals(curpage)) {
                    if (!swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(true);
                    }
                } else {
                    Toast.makeText(HiddenDangerStatisticsEachUnitDetailActivity.this,
                            "正在加载" + curpage + "/" + pagesize, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "获取未消耗的数据返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    JSONObject returndata = JSON.parseObject(data);
                    String rows = returndata.getString("rows");
                    page = Integer.parseInt(returndata.getString("page"));
                    pagesize = Integer.parseInt(returndata.getString("totalPage"));
                    List<HiddenDangerRecord> recordList = JSONArray.parseArray(rows, HiddenDangerRecord.class);
                    if (page == 1) {
                        list.clear();
                    }
                    list.addAll(recordList);
                    adapter.notifyDataSetChanged();
                   }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "获取未消耗的数据返回错误信息：" + content);
                Utils.showLongToast(HiddenDangerStatisticsEachUnitDetailActivity.this, content);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                onLoading = false;
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                if (adapter.getItemCount() == 0) {
                    layoutEmptyList.setVisibility(View.VISIBLE);
                    errorMessage.setText("没有查询到数据");
                    errorTips.setVisibility(View.GONE);
                }
            }
        });
    }


    //没有页数信息
    private void getHiddenRecord(final String curpage, String teamGroupCode, String State, String getTeamdetaillist, String tvStartDate, String tvEndDate) {
        String ishandle = getIntent().getStringExtra("ishandle");
        RequestParams params = new RequestParams();
        String requesturl = Constants.GET_HIDDENDANGERDETAILLIST;
        Map<String, String> paramsMap = new HashMap<String, String>();
        if (!TextUtils.isEmpty(State)) {
            paramsMap.put("State", State);
        }
        if (!TextUtils.isEmpty(getTeamdetaillist)) {
            requesturl = Constants.GET_TEAMDETAILLIST;
        }
        if (!TextUtils.isEmpty(teamGroupCode)) {
            paramsMap.put("teamGroupId", teamGroupCode);
        }
        if (!TextUtils.isEmpty(ishandle)) {
            paramsMap.put("ishandle", ishandle);
        }
        if (!TextUtils.isEmpty(tvStartDate)) {
            params.put("customParamsOne", tvStartDate);//开始时间
        }
        if (!TextUtils.isEmpty(tvEndDate)) {
            params.put("customParamsTwo", tvEndDate);//结束时间
        }
        Log.i(TAG, "详情参数:" + params);
        paramsMap.put("page", curpage);
        paramsMap.put("rows", "100");
        paramsMap.put("employeeId", UserUtils.getUserID(HiddenDangerStatisticsEachUnitDetailActivity.this));
        String jsonString = JSON.toJSONString(paramsMap);
        params.put("hiddenDangerRecordJsonData", jsonString);
        netClient.post(Data.getInstance().getIp() + requesturl, params, new BaseJsonRes() {
            @Override
            public void onStart() {
                super.onStart();
                onLoading = true;
                layoutEmptyList.setVisibility(View.GONE);
                if ("1".equals(curpage)) {
                    if (!swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(true);
                    }
                } else {
                    Toast.makeText(HiddenDangerStatisticsEachUnitDetailActivity.this,
                            "正在加载" + curpage + "/" + pagesize, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "统计详情返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    JSONObject returndata = JSON.parseObject(data);
                    String rows = returndata.getString("rows");
                    page = Integer.parseInt(returndata.getString("page"));
                    pagesize = Integer.parseInt(returndata.getString("totalPage"));
                    List<HiddenDangerRecord> recordList = JSONArray.parseArray(rows, HiddenDangerRecord.class);
                    if (page == 1) {
                        list.clear();
                    }
                    list.addAll(recordList);
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "统计详情返回错误信息：" + content);
                Utils.showLongToast(HiddenDangerStatisticsEachUnitDetailActivity.this, content);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                onLoading = false;
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                if (adapter.getItemCount() == 0) {
                    layoutEmptyList.setVisibility(View.VISIBLE);
                    errorMessage.setText("没有查询到数据");
                    errorTips.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initData(List<HiddenDangerRecord> recordList) {

    }
}
