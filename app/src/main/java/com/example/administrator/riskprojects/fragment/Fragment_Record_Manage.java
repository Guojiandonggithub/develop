package com.example.administrator.riskprojects.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.administrator.riskprojects.Adpter.HiddenDangeMuitipleAdapter;
import com.example.administrator.riskprojects.Adpter.HiddenDangeRecordAdapter;
import com.example.administrator.riskprojects.Adpter.HiddenDangeTrackingAdapter;
import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.activity.Data;
import com.example.administrator.riskprojects.activity.HiddenRiskRecordAddEditActivity;
import com.example.administrator.riskprojects.activity.MainActivity;
import com.example.administrator.riskprojects.bean.HiddenDangerRecord;
import com.example.administrator.riskprojects.bean.ThreeFix;
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


//隐患跟踪
public class Fragment_Record_Manage extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "Fragment_Record_Manage";
    private Activity ctx;
    private View layout;
    private TextView tvname, tv_accout;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView recyclerView;
    private int flag = 0;//选择模块  默认记录管理
    protected NetClient netClient;
    List<HiddenDangerRecord> recordList = new ArrayList<HiddenDangerRecord>();
    List<ThreeFix> threeFixesList = new ArrayList<ThreeFix>();
    private LinearLayoutCompat llAdd;
    private LinearLayoutCompat llOption;
    private TextView tvProfession;
    private TextView tvHiddenUnits;
    private TextView tvArea;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean onLoading = false;
    private int curpage = 1;
    private int pagesize = 1;
    private RecyclerView.Adapter adapter;
    private String sid,areaid;


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
        recyclerView = layout.findViewById(R.id.recyclerView);
        mSwipeRefreshLayout = layout.findViewById(R.id.swipeRefreshLayout);
        recyclerView = layout.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(ctx));

        mSwipeRefreshLayout.setOnRefreshListener(this);
        llAdd = layout.findViewById(R.id.ll_add);
        llAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ctx, HiddenRiskRecordAddEditActivity.class));
            }
        });
        llOption = layout.findViewById(R.id.ll_option);
        tvProfession = layout.findViewById(R.id.tv_profession);
        tvHiddenUnits = layout.findViewById(R.id.tv_hidden_units);
        tvArea = layout.findViewById(R.id.tv_area);
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
                                && Math.abs(lastChildBottom - recyclerBottom) <= DensityUtil.dip2px(ctx, 8)
                                && lastPosition == recyclerView.getLayoutManager().getItemCount() - 1
                                && newState == RecyclerView.SCROLL_STATE_IDLE) {
                            //loading more data

                            if (curpage < pagesize) {
                                getDataByPage(curpage + 1);
                            } else {
                                Toast.makeText(ctx, "全部加载完毕", Toast.LENGTH_SHORT).show();
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

    private void initViews() {
        flag = 1;
        adapter = new HiddenDangeRecordAdapter(recordList);
        recyclerView.setAdapter(adapter);
        getDataByPage(1);
    }

    private void getHiddenRecord(final String page) {//分页当前页
        RequestParams params = new RequestParams();
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("page", page);
        paramsMap.put("rows", Constants.ROWS);
        paramsMap.put("employeeId", UserUtils.getUserID(getActivity()));
        if(!TextUtils.isEmpty(tvArea.getText())){
            paramsMap.put("customParamsThree", areaid);
        }
        if(!TextUtils.isEmpty(tvProfession.getText())){
            paramsMap.put("customParamsTwo",sid);
        }
        if(!TextUtils.isEmpty(tvHiddenUnits.getText())){
            //paramsMap.put("customParamsThree", tvHiddenUnits.getText().toString());
        }
        String jsonString = JSON.toJSONString(paramsMap);
        params.put("hiddenDangerRecordJsonData", jsonString);
        netClient.post(Data.getInstance().getIp()+Constants.GET_HIDDENRECORD, params, new BaseJsonRes() {
            @Override
            public void onStart() {
                super.onStart();
                onLoading = true;
                if (page.equals("1")) {
                    if (!mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(true);
                    }
                } else if (((MainActivity) ctx).index == 2) {
                    Toast.makeText(ctx, "正在加载" + page + "/" + pagesize, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "隐患数据返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    JSONObject returndata = JSON.parseObject(data);
                    String rows = returndata.getString("rows");
                    curpage = Integer.parseInt(returndata.getString("page"));
                    pagesize = Integer.parseInt(returndata.getString("pagesize"));
                    if (curpage == 1) {
                        recordList.clear();
                    }
                    List<HiddenDangerRecord> tempList = JSONArray.parseArray(rows, HiddenDangerRecord.class);
                    recordList.addAll(tempList);
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "隐患数据返回错误信息：" + content);
                Utils.showLongToast(getContext(), content);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mSwipeRefreshLayout.setRefreshing(false);
                onLoading = false;
            }
        });
    }

    private void getReleaseList(final String page) {//下达隐患查询
        RequestParams params = new RequestParams();
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("page", page);
        paramsMap.put("rows", Constants.ROWS);
        paramsMap.put("employeeId", UserUtils.getUserID(getActivity()));
        if(!TextUtils.isEmpty(tvArea.getText())){
            paramsMap.put("customParamsTwo", areaid);
        }
        if(!TextUtils.isEmpty(tvProfession.getText())){
            paramsMap.put("customParamsOne",sid);
        }
        String jsonString = JSON.toJSONString(paramsMap);
        params.put("threeFixJsonData", jsonString);
        netClient.post(Data.getInstance().getIp()+Constants.GET_HIDDENRELEASELIST, params, new BaseJsonRes() {
            @Override
            public void onStart() {
                super.onStart();
                onLoading = true;
                if (page.equals("1")) {
                    if (!mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(true);
                    }
                } else if (((MainActivity) ctx).index == 2) {
                    Toast.makeText(ctx, "正在加载" + page + "/" + pagesize, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "下达隐患查询返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    JSONObject returndata = JSON.parseObject(data);
                    String rows = returndata.getString("rows");
                    curpage = Integer.parseInt(returndata.getString("page"));
                    pagesize = Integer.parseInt(returndata.getString("pagesize"));
                    if (curpage == 1) {
                        threeFixesList.clear();
                    }
                    List<ThreeFix> tempList = JSONArray.parseArray(rows, ThreeFix.class);
                    threeFixesList.addAll(tempList);
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "下达隐患查询返回错误信息：" + content);
                Utils.showLongToast(getContext(), content);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mSwipeRefreshLayout.setRefreshing(false);
                onLoading = false;
            }
        });
    }

    private void getRectificationList(final String page) {//整改隐患查询
        RequestParams params = new RequestParams();
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("page", page);
        paramsMap.put("rows", Constants.ROWS);
        paramsMap.put("employeeId", UserUtils.getUserID(getActivity()));
        if(!TextUtils.isEmpty(tvArea.getText())){
            paramsMap.put("customParamsThree", areaid);
        }
        if(!TextUtils.isEmpty(tvProfession.getText())){
            paramsMap.put("customParamsTwo",sid);
        }
        String jsonString = JSON.toJSONString(paramsMap);
        params.put("threeFixJsonData", jsonString);
        netClient.post(Data.getInstance().getIp()+Constants.GET_RECTIFICATIONLIST, params, new BaseJsonRes() {
            @Override
            public void onStart() {
                super.onStart();
                onLoading = true;
                if (page.equals("1")) {
                    if (!mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(true);
                    }
                } else if (((MainActivity) ctx).index == 2) {
                    Toast.makeText(ctx, "正在加载" + page + "/" + pagesize, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "整改隐患查询返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    JSONObject returndata = JSON.parseObject(data);
                    String rows = returndata.getString("rows");
                    curpage = Integer.parseInt(returndata.getString("page"));
                    pagesize = Integer.parseInt(returndata.getString("pagesize"));
                    if (curpage == 1) {
                        threeFixesList.clear();
                    }
                    List<ThreeFix> tempList = JSONArray.parseArray(rows, ThreeFix.class);
                    threeFixesList.addAll(tempList);
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "整改隐患查询返回错误信息：" + content);
                Utils.showLongToast(getContext(), content);
            }


            @Override
            public void onFinish() {
                super.onFinish();
                mSwipeRefreshLayout.setRefreshing(false);
                onLoading = false;
            }
        });
    }


    private void getTrackingList(final String page) {//跟踪隐患查询
        RequestParams params = new RequestParams();
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("page", page);
        paramsMap.put("rows", Constants.ROWS);
        paramsMap.put("employeeId", UserUtils.getUserID(getActivity()));
        if(!TextUtils.isEmpty(tvArea.getText())){
            paramsMap.put("customParamsTwo", areaid);
        }
        if(!TextUtils.isEmpty(tvProfession.getText())){
            paramsMap.put("customParamsOne",sid);
        }
        String jsonString = JSON.toJSONString(paramsMap);
        params.put("threeFixJsonData", jsonString);
        netClient.post(Data.getInstance().getIp()+Constants.GET_RECTIFICATIONLIST, params, new BaseJsonRes() {

            @Override
            public void onStart() {
                super.onStart();
                onLoading = true;
                if (page.equals("1")) {
                    if (!mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(true);
                    }
                } else if (((MainActivity) ctx).index == 2) {
                    Toast.makeText(ctx, "正在加载" + page + "/" + pagesize, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "跟踪隐患查询返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    JSONObject returndata = JSON.parseObject(data);
                    String rows = returndata.getString("rows");
                    curpage = Integer.parseInt(returndata.getString("page"));
                    pagesize = Integer.parseInt(returndata.getString("pagesize"));
                    if (curpage == 1) {
                        threeFixesList.clear();
                    }
                    List<ThreeFix> tempList = JSONArray.parseArray(rows, ThreeFix.class);
                    threeFixesList.addAll(tempList);
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "跟踪隐患查询返回错误信息：" + content);
                Utils.showLongToast(getContext(), content);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mSwipeRefreshLayout.setRefreshing(false);
                onLoading = false;
            }
        });
    }

    private void getOverdueList(final String page) {//逾期隐患查询
        RequestParams params = new RequestParams();
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("page", page);
        paramsMap.put("rows", Constants.ROWS);
        paramsMap.put("employeeId", UserUtils.getUserID(getActivity()));
        if(!TextUtils.isEmpty(tvArea.getText())){
            paramsMap.put("customParamsThree", areaid);
        }
        if(!TextUtils.isEmpty(tvProfession.getText())){
            paramsMap.put("customParamsTwo",sid);
        }
        String jsonString = JSON.toJSONString(paramsMap);
        params.put("threeFixJsonData", jsonString);
        netClient.post(Data.getInstance().getIp()+Constants.GET_OVERDUELIST, params, new BaseJsonRes() {

            @Override
            public void onStart() {
                super.onStart();
                onLoading = true;
                if (page.equals("1")) {
                    if (!mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(true);
                    }
                } else if (((MainActivity) ctx).index == 2) {
                    Toast.makeText(ctx, "正在加载" + page + "/" + pagesize, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "逾期隐患查询返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    JSONObject returndata = JSON.parseObject(data);
                    String rows = returndata.getString("rows");
                    curpage = Integer.parseInt(returndata.getString("page"));
                    pagesize = Integer.parseInt(returndata.getString("pagesize"));
                    if (curpage == 1) {
                        threeFixesList.clear();
                    }
                    List<ThreeFix> tempList = JSONArray.parseArray(rows, ThreeFix.class);
                    threeFixesList.addAll(tempList);
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "逾期隐患查询返回错误信息：" + content);
                Utils.showLongToast(getContext(), content);
            }


            @Override
            public void onFinish() {
                super.onFinish();
                mSwipeRefreshLayout.setRefreshing(false);
                onLoading = false;
            }
        });
    }

    private void getReviewList(final String page) {//验收隐患查询
        RequestParams params = new RequestParams();
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("page", page);
        paramsMap.put("rows", Constants.ROWS);
        paramsMap.put("employeeId", UserUtils.getUserID(getActivity()));
        if(!TextUtils.isEmpty(tvArea.getText())){
            paramsMap.put("customParamsThree", areaid);
        }
        if(!TextUtils.isEmpty(tvProfession.getText())){
            paramsMap.put("customParamsTwo",sid);
        }
        String jsonString = JSON.toJSONString(paramsMap);
        params.put("threeFixJsonData", jsonString);
        netClient.post(Data.getInstance().getIp()+Constants.GET_REVIEWLIST, params, new BaseJsonRes() {

            @Override
            public void onStart() {
                super.onStart();
                onLoading = true;
                if (page.equals("1")) {
                    if (!mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(true);
                    }
                } else if (((MainActivity) ctx).index == 2) {
                    Toast.makeText(ctx, "正在加载" + page + "/" + pagesize, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "验收隐患查询返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    JSONObject returndata = JSON.parseObject(data);
                    String rows = returndata.getString("rows");
                    curpage = Integer.parseInt(returndata.getString("page"));
                    pagesize = Integer.parseInt(returndata.getString("pagesize"));
                    if (curpage == 1) {
                        threeFixesList.clear();
                    }
                    List<ThreeFix> tempList = JSONArray.parseArray(rows, ThreeFix.class);
                    threeFixesList.addAll(tempList);
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "验收隐患查询返回错误信息：" + content);
                Utils.showLongToast(getContext(), content);
            }


            @Override
            public void onFinish() {
                super.onFinish();
                mSwipeRefreshLayout.setRefreshing(false);
                onLoading = false;
            }
        });
    }


    @Override
    public void onRefresh() {
        getDataByPage(1);
    }

    public void onRightMenuClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_manage_detail:
                llAdd.setVisibility(View.VISIBLE);
                flag = 1;
                adapter = new HiddenDangeRecordAdapter(recordList);
                recyclerView.setAdapter(adapter);
                break;
            case R.id.ll_manage_release:
                llAdd.setVisibility(View.VISIBLE);
                flag = 2;
                String userRole = UserUtils.getUserRoleids(getActivity());
                if(!"8".equals(userRole)&&!"62".equals(userRole)){
                    threeFixesList.clear();
                    adapter = new HiddenDangeMuitipleAdapter(HiddenDangeMuitipleAdapter.FLAG_REALEASE, threeFixesList);
                    recyclerView.setAdapter(adapter);
                }else{
                    Utils.showLongToast(getContext(), "没有权限进行该操作!");
                }
                break;
            case R.id.ll_manage_rectification:
                llAdd.setVisibility(View.VISIBLE);
                flag = 3;
                String userRoles = UserUtils.getUserRoleids(getActivity());
                if(!"8".equals(userRoles)&&!"62".equals(userRoles)){
                    threeFixesList.clear();
                    adapter = new HiddenDangeMuitipleAdapter(HiddenDangeMuitipleAdapter.FLAG_RECTIFICATION, threeFixesList);
                    recyclerView.setAdapter(adapter);
                }else{
                    Utils.showLongToast(getContext(), "没有权限进行该操作!");
                }
                break;
            case R.id.ll_manage_tracking:
                llAdd.setVisibility(View.VISIBLE);
                flag = 4;
                threeFixesList.clear();
                adapter = new HiddenDangeTrackingAdapter(threeFixesList);
                recyclerView.setAdapter(adapter);
                break;
            case R.id.ll_manage_overdue:
                llAdd.setVisibility(View.VISIBLE);
                flag = 5;
                threeFixesList.clear();
                adapter = new HiddenDangeMuitipleAdapter(HiddenDangeMuitipleAdapter.FLAG_OVERDUE,threeFixesList);
                recyclerView.setAdapter(adapter);
                break;
            case R.id.ll_manage_review:
                llAdd.setVisibility(View.VISIBLE);
                flag = 6;
                threeFixesList.clear();
                adapter = new HiddenDangeMuitipleAdapter(HiddenDangeMuitipleAdapter.FLAG_REVIEW, threeFixesList);
                recyclerView.setAdapter(adapter);
                break;
            default:
                break;
        }
        llOption.setVisibility(View.GONE);
        tvArea.setText("");
        tvProfession.setText("");
        sid = "";
        areaid = "";
        tvHiddenUnits.setText("");
        getDataByPage(1);
    }

    public void onLeftMenuClicked(String aname, String aid, String pname, String pid, String hname, String hid) {
        llOption.setVisibility(View.VISIBLE);
        tvArea.setText(aname);
        tvProfession.setText(pname);
        sid = pid;
        areaid = aid;
        tvHiddenUnits.setText(hname);
    }

    public String getTitle() {
        switch (flag) {
            case 0:
                return getResources().getString(R.string.hidden_danger_record_management);
            case 1:
                return getResources().getString(R.string.hidden_danger_record_management);
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

    @Override
    public void onResume() {
        super.onResume();
        getDataByPage(1);
    }

    private void getDataByPage(int page) {
        if (page == 1) {
            curpage = 1;
            pagesize = 1;
        }
        switch (flag) {
            case 0:
            case 1:
                getHiddenRecord(Integer.toString(page));
                break;
            case 2:
                getReleaseList(Integer.toString(page));
                break;
            case 3:
                getRectificationList(Integer.toString(page));
                break;
            case 4:
                getTrackingList(Integer.toString(page));
                break;
            case 5:
                getOverdueList(Integer.toString(page));
                break;
            case 6:
                getReviewList(Integer.toString(page));
                break;

        }
    }
}