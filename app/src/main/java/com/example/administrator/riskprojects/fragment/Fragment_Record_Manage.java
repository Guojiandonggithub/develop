package com.example.administrator.riskprojects.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.administrator.riskprojects.Adpter.HiddenDangeMuitipleAdapter;
import com.example.administrator.riskprojects.Adpter.HiddenDangeRecordAdapter;
import com.example.administrator.riskprojects.Adpter.HiddenDangeTrackingAdapter;
import com.alibaba.fastjson.JSONArray;
import com.example.administrator.riskprojects.Adpter.ListingSupervisionAdapter;
import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.activity.HiddenDangeTrackingManagementActivity;
import com.example.administrator.riskprojects.activity.HiddenDangerOverdueManagementActivity;
import com.example.administrator.riskprojects.activity.HiddenDangerRectificationManagementActivity;
import com.example.administrator.riskprojects.activity.HiddenDangerReleaseManagementActivity;
import com.example.administrator.riskprojects.activity.HiddenDangerReviewManagementActivity;
import com.example.administrator.riskprojects.activity.HiddenRiskRecordAddEditActivity;
import com.example.administrator.riskprojects.bean.HiddenDangerRecord;
import com.example.administrator.riskprojects.bean.ThreeFix;
import com.example.administrator.riskprojects.net.BaseJsonRes;
import com.example.administrator.riskprojects.net.NetClient;
import com.example.administrator.riskprojects.tools.Constants;
import com.example.administrator.riskprojects.tools.UserUtils;
import com.example.administrator.riskprojects.tools.Utils;
import com.juns.health.net.loopj.android.http.RequestParams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


//隐患跟踪
public class Fragment_Record_Manage extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "Fragment_Record_Manage";
    private Activity ctx;
    private View layout;
    private TextView tvname, tv_accout;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private int flag=0;//选择模块  默认记录管理
    protected NetClient netClient;
    List<HiddenDangerRecord> recordList;
    List<ThreeFix> threeFixesList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        netClient = new NetClient(getActivity());
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
        initViews();
        return layout;
    }

    private void initView(View layout) {
        mSwipeRefreshLayout = layout.findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = layout.findViewById(R.id.recyclerView);

        mSwipeRefreshLayout = layout.findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = layout.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ctx));
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void initViews(){
        getHiddenRecord(Constants.PAGE);
    }

    private void getHiddenRecord(String page) {//分页当前页
        RequestParams params = new RequestParams();
        Map<String,String> paramsMap = new HashMap<String,String>();
        paramsMap.put("page",page);
        paramsMap.put("rows",Constants.ROWS);
        paramsMap.put("employeeId", UserUtils.getUserID(getActivity()));
        String jsonString = JSON.toJSONString(paramsMap);
        params.put("hiddenDangerRecordJsonData",jsonString);
        netClient.post(Constants.GET_HIDDENRECORD, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "隐患数据返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    JSONObject returndata  = JSON.parseObject(data);
                    String rows = returndata.getString("rows");
                    String page = returndata.getString("page");
                    String pagesize = returndata.getString("pagesize");
                    recordList = JSONArray.parseArray(rows, HiddenDangerRecord.class);
                    mRecyclerView.setAdapter(new ListingSupervisionAdapter(recordList));
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "隐患数据返回错误信息：" + content);
                Utils.showLongToast(getContext(), content);
            }
        });
    }

    private void getReleaseList(String page) {//下达隐患查询
        RequestParams params = new RequestParams();
        Map<String,String> paramsMap = new HashMap<String,String>();
        paramsMap.put("page",page);
        paramsMap.put("rows",Constants.ROWS);
        paramsMap.put("employeeId", UserUtils.getUserID(getActivity()));
        String jsonString = JSON.toJSONString(paramsMap);
        params.put("threeFixJsonData",jsonString);
        netClient.post(Constants.GET_HIDDENRELEASELIST, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "隐患数据返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    JSONObject returndata  = JSON.parseObject(data);
                    String rows = returndata.getString("rows");
                    String page = returndata.getString("page");
                    String pagesize = returndata.getString("pagesize");
                    threeFixesList = JSONArray.parseArray(rows, ThreeFix.class);
                    mRecyclerView.setAdapter(new HiddenDangeMuitipleAdapter(HiddenDangeMuitipleAdapter.FLAG_REALEASE,threeFixesList));
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "隐患数据返回错误信息：" + content);
                Utils.showLongToast(getContext(), content);
            }
        });
    }

    private void getRectificationList(String page) {//整改隐患查询
        RequestParams params = new RequestParams();
        Map<String,String> paramsMap = new HashMap<String,String>();
        paramsMap.put("page",page);
        paramsMap.put("rows",Constants.ROWS);
        paramsMap.put("employeeId", UserUtils.getUserID(getActivity()));
        String jsonString = JSON.toJSONString(paramsMap);
        params.put("threeFixJsonData",jsonString);
        netClient.post(Constants.GET_RECTIFICATIONLIST, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "隐患数据返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    JSONObject returndata  = JSON.parseObject(data);
                    String rows = returndata.getString("rows");
                    String page = returndata.getString("page");
                    String pagesize = returndata.getString("pagesize");
                    threeFixesList = JSONArray.parseArray(rows, ThreeFix.class);
                    mRecyclerView.setAdapter(new HiddenDangeMuitipleAdapter(HiddenDangeMuitipleAdapter.FLAG_RECTIFICATION,threeFixesList));
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "隐患数据返回错误信息：" + content);
                Utils.showLongToast(getContext(), content);
            }
        });
    }


    private void getTrackingList(String page) {//跟踪隐患查询
        RequestParams params = new RequestParams();
        Map<String,String> paramsMap = new HashMap<String,String>();
        paramsMap.put("page",page);
        paramsMap.put("rows",Constants.ROWS);
        paramsMap.put("employeeId", UserUtils.getUserID(getActivity()));
        String jsonString = JSON.toJSONString(paramsMap);
        params.put("threeFixJsonData",jsonString);
        netClient.post(Constants.GET_TRACKINGLIST, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "隐患数据返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    JSONObject returndata  = JSON.parseObject(data);
                    String rows = returndata.getString("rows");
                    String page = returndata.getString("page");
                    String pagesize = returndata.getString("pagesize");
                    threeFixesList = JSONArray.parseArray(rows, ThreeFix.class);
                    mRecyclerView.setAdapter(new HiddenDangeTrackingAdapter(threeFixesList));
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "隐患数据返回错误信息：" + content);
                Utils.showLongToast(getContext(), content);
            }
        });
    }

    private void getOverdueList(String page) {//逾期隐患查询
        RequestParams params = new RequestParams();
        Map<String,String> paramsMap = new HashMap<String,String>();
        paramsMap.put("page",page);
        paramsMap.put("rows",Constants.ROWS);
        paramsMap.put("employeeId", UserUtils.getUserID(getActivity()));
        String jsonString = JSON.toJSONString(paramsMap);
        params.put("threeFixJsonData",jsonString);
        netClient.post(Constants.GET_OVERDUELIST, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "隐患数据返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    JSONObject returndata  = JSON.parseObject(data);
                    String rows = returndata.getString("rows");
                    String page = returndata.getString("page");
                    String pagesize = returndata.getString("pagesize");
                    threeFixesList = JSONArray.parseArray(rows, ThreeFix.class);
                    mRecyclerView.setAdapter(new HiddenDangeMuitipleAdapter(HiddenDangeMuitipleAdapter.FLAG_OVERDUE,threeFixesList));
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "隐患数据返回错误信息：" + content);
                Utils.showLongToast(getContext(), content);
            }
        });
    }
    private void getReviewList(String page) {//验收隐患查询
        RequestParams params = new RequestParams();
        Map<String,String> paramsMap = new HashMap<String,String>();
        paramsMap.put("page",page);
        paramsMap.put("rows",Constants.ROWS);
        paramsMap.put("employeeId", UserUtils.getUserID(getActivity()));
        String jsonString = JSON.toJSONString(paramsMap);
        params.put("threeFixJsonData",jsonString);
        netClient.post(Constants.GET_REVIEWLIST, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "隐患数据返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    JSONObject returndata  = JSON.parseObject(data);
                    String rows = returndata.getString("rows");
                    String page = returndata.getString("page");
                    String pagesize = returndata.getString("pagesize");
                    threeFixesList = JSONArray.parseArray(rows, ThreeFix.class);
                    mRecyclerView.setAdapter(new HiddenDangeMuitipleAdapter(HiddenDangeMuitipleAdapter.FLAG_REVIEW,threeFixesList));
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "隐患数据返回错误信息：" + content);
                Utils.showLongToast(getContext(), content);
            }
        });
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
                initViews();
                mRecyclerView.setAdapter(new HiddenDangeRecordAdapter(recordList));
                break;
            case R.id.ll_manage_release:
                flag = 2;
                getReleaseList("1");
                break;
            case R.id.ll_manage_rectification:
                flag = 3;
                getRectificationList("1");
                break;
            case R.id.ll_manage_tracking:
                flag = 4;
                getTrackingList("1");
                break;
            case R.id.ll_manage_overdue:
                flag = 5;
                getOverdueList("1");
                break;
            case R.id.ll_manage_review:
                flag = 6;
                getReviewList("1");
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