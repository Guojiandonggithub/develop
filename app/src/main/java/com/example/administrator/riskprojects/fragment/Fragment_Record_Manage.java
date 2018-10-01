package com.example.administrator.riskprojects.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.administrator.riskprojects.bean.Area;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.administrator.riskprojects.Adpter.HiddenDangeMuitipleAdapter;
import com.example.administrator.riskprojects.Adpter.HiddenDangeRecordAdapter;
import com.example.administrator.riskprojects.Adpter.HiddenDangeTrackingAdapter;
import com.example.administrator.riskprojects.Adpter.SpinnerAdapter;
import com.example.administrator.riskprojects.OnItemClickListener;
import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.activity.Data;
import com.example.administrator.riskprojects.activity.DatePickerActivity;
import com.example.administrator.riskprojects.activity.HiddenDangerRectificationManagementActivity;
import com.example.administrator.riskprojects.activity.HiddenRiskRecordAddEditActivity;
import com.example.administrator.riskprojects.activity.MainActivity;
import com.example.administrator.riskprojects.bean.DataDictionary;
import com.example.administrator.riskprojects.bean.HiddenDangerRecord;
import com.example.administrator.riskprojects.bean.SelectItem;
import com.example.administrator.riskprojects.bean.Specialty;
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

import static android.app.Activity.RESULT_OK;


//隐患跟踪
public class Fragment_Record_Manage extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "Fragment_Record_Manage";
    private Activity ctx;
    private View layout;
    private TextView tvname, tv_accout;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView recyclerView;
    private int flag = 1;//选择模块  默认记录管理
    protected NetClient netClient;
    List<HiddenDangerRecord> recordList = new ArrayList<HiddenDangerRecord>();
    List<ThreeFix> threeFixesList = new ArrayList<ThreeFix>();
    private LinearLayoutCompat llOption;
    private TextView tvProfession;
    private TextView tvHiddenUnits;
    private TextView tvArea;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean onLoading = false;
    private int curpage = 1;
    private int pagesize = 1;
    private RecyclerView.Adapter adapter;
    private String sid, areaid;
    private LinearLayoutCompat llOptionNew;
    private LinearLayoutCompat llContent;
    private CardView cvSelectStartDate;
    private TextView tvStartDate;
    private View llSelectTop;
    private LinearLayoutCompat llSelectProfession;
    private TextView tvSpProfession;
    private Spinner spProfession;
    private SpinnerAdapter spProfessionAdapter;
    private LinearLayoutCompat llSelectArea;
    private TextView tvSpArea;
    private Spinner spArea;
    private SpinnerAdapter spAreaAdapter;
    private LinearLayoutCompat llSelectCheckUnits;
    private TextView tvSpCheckUnits;
    private Spinner spCheckUnits;
    private SpinnerAdapter spCheckUnitsAdapter;
    private LinearLayoutCompat llSelectIsHandle;
    private TextView tvSpIsHandle;
    private Spinner spIsHandle;
    private SpinnerAdapter spIsHandleAdapter;
    private LinearLayoutCompat llSelectStatus;
    private TextView tvSpStatus;
    private Spinner spStatus;
    private SpinnerAdapter spStatusAdapter;
    private LinearLayoutCompat llSelectRectificationResults;
    private TextView tvRectificationResults;
    private Spinner spRectificationResults;
    private LinearLayoutCompat llSelectCheckResults;
    private TextView tvCheckResults;
    private Spinner spCheckResults;
    private LinearLayoutCompat llExpand;
    private TextView tvExpand;
    private ImageView ivExpand;


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
        llOptionNew = layout.findViewById(R.id.ll_option_new);
        llContent = layout.findViewById(R.id.ll_content);
        cvSelectStartDate = layout.findViewById(R.id.cv_select_start_date);
        tvStartDate = layout.findViewById(R.id.tv_start_date);
        llSelectTop = layout.findViewById(R.id.ll_select_top);
        llSelectProfession = layout.findViewById(R.id.ll_select_profession);
        tvSpProfession = layout.findViewById(R.id.tv_sp_profession);
        spProfession = layout.findViewById(R.id.sp_profession);
        llSelectArea = layout.findViewById(R.id.ll_select_area);
        tvSpArea = layout.findViewById(R.id.tv_sp_area);
        spArea = layout.findViewById(R.id.sp_area);
        llSelectCheckUnits = layout.findViewById(R.id.ll_select_check_units);
        tvSpCheckUnits = layout.findViewById(R.id.tv_sp_check_units);
        spCheckUnits = layout.findViewById(R.id.sp_check_units);
        llSelectIsHandle = layout.findViewById(R.id.ll_select_is_handle);
        tvSpIsHandle = layout.findViewById(R.id.tv_sp_is_handle);
        spIsHandle = layout.findViewById(R.id.sp_is_handle);
        llSelectStatus = layout.findViewById(R.id.ll_select_status);
        tvSpStatus = layout.findViewById(R.id.tv_sp_status);
        spStatus = layout.findViewById(R.id.sp_status);
        llSelectRectificationResults = layout.findViewById(R.id.ll_select_rectification_results);
        tvRectificationResults = layout.findViewById(R.id.tv_rectification_results);
        spRectificationResults = layout.findViewById(R.id.sp_rectification_results);
        llSelectCheckResults = layout.findViewById(R.id.ll_select_check_results);
        tvCheckResults = layout.findViewById(R.id.tv_check_results);
        spCheckResults = layout.findViewById(R.id.sp_check_results);
        llExpand = layout.findViewById(R.id.ll_expand);
        tvExpand = layout.findViewById(R.id.tv_expand);
        ivExpand = layout.findViewById(R.id.iv_expand);
        llExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llContent.setVisibility(llContent.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                tvExpand.setText(llContent.getVisibility() == View.VISIBLE ? "收起选项" : "展开选项");
                ivExpand.setSelected(llContent.getVisibility() == View.VISIBLE);
            }
        });
        cvSelectStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                DatePickerActivity.startPickDate(ctx, ctx);
                Fragment_Record_Manage.this.startActivityForResult(new Intent(ctx, DatePickerActivity.class), DatePickerActivity.REQUEST);
            }
        });
        setUpTopView();
    }

    private void setUpTopView() {
        llSelectArea.setVisibility(View.GONE);
        llSelectCheckUnits.setVisibility(View.GONE);
        llSelectIsHandle.setVisibility(View.GONE);
        llSelectStatus.setVisibility(View.GONE);
        llSelectCheckResults.setVisibility(View.GONE);
        llSelectProfession.setVisibility(View.GONE);
        llSelectRectificationResults.setVisibility(View.GONE);
        getSpecialty(flag);
        getArea(flag);
        switch (flag) {
            case 1:
                llSelectProfession.setVisibility(View.VISIBLE);
                llSelectArea.setVisibility(View.VISIBLE);
                llSelectIsHandle.setVisibility(View.VISIBLE);
                llSelectCheckUnits.setVisibility(View.VISIBLE);
                llSelectStatus.setVisibility(View.VISIBLE);
                setIshandleSpinner(spIsHandle);
                setHiddenStatusSpinner(spStatus);
                getHiddenYHGSLX(flag);
                break;
            case 2:
                llSelectProfession.setVisibility(View.VISIBLE);
                llSelectArea.setVisibility(View.VISIBLE);
                break;
            case 3:
                llSelectProfession.setVisibility(View.VISIBLE);
                llSelectArea.setVisibility(View.VISIBLE);
                llSelectRectificationResults.setVisibility(View.GONE);
                llSelectCheckUnits.setVisibility(View.VISIBLE);
                tvSpCheckUnits.setText("整改结果");
                setzhenggaiResultSpinner(spCheckUnits);
                break;
            case 4:
                llSelectProfession.setVisibility(View.VISIBLE);
                llSelectArea.setVisibility(View.VISIBLE);
                llSelectRectificationResults.setVisibility(View.GONE);
                break;
            case 5:
                llSelectProfession.setVisibility(View.VISIBLE);
                llSelectArea.setVisibility(View.VISIBLE);
                break;
            case 6:
                llSelectProfession.setVisibility(View.VISIBLE);
                llSelectArea.setVisibility(View.VISIBLE);
                llSelectCheckUnits.setVisibility(View.VISIBLE);
                tvSpCheckUnits.setText("验收结果");
                //getHiddenYHGSLX(flag);
                setCheckResultSpinner(spCheckUnits);
                break;

        }
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
        if(TextUtils.isEmpty(tvStartDate.getText())){
            paramsMap.put("customParamsFour", tvStartDate.getText().toString());
        }
        SelectItem spProfessionItem = (SelectItem) spProfession.getSelectedItem();
        if (null != spProfessionItem) {
            if (!TextUtils.isEmpty(spProfessionItem.id)) {
                paramsMap.put("customParamsTwo", spProfessionItem.id);//状态
            }
        }
        SelectItem spAreaItem = (SelectItem) spArea.getSelectedItem();
        if (null != spAreaItem) {
            if (!TextUtils.isEmpty(spAreaItem.id)) {
                paramsMap.put("customParamsThree", spAreaItem.id);//状态
            }
        }
        SelectItem spCheckUnitsItem = (SelectItem) spCheckUnits.getSelectedItem();
        if (null != spCheckUnitsItem) {
            if (!TextUtils.isEmpty(spCheckUnitsItem.id)) {
                paramsMap.put("customParamsSeven", spCheckUnitsItem.id);//状态
            }
        }
        SelectItem spIsHandleAdapterItem = (SelectItem) spIsHandle.getSelectedItem();
        if (null != spIsHandleAdapterItem) {
            if (!TextUtils.isEmpty(spIsHandleAdapterItem.id)) {
                paramsMap.put("customParamsFive", spIsHandleAdapterItem.id);//状态
            }
        }
        SelectItem spStatusItem = (SelectItem) spStatus.getSelectedItem();
        if (null != spStatusItem) {
            if (Integer.parseInt(spStatusItem.id)>=0) {
                paramsMap.put("customParamsSix", spStatusItem.id);//状态
            }
        }
        String jsonString = JSON.toJSONString(paramsMap);
        params.put("hiddenDangerRecordJsonData", jsonString);
        netClient.post(Data.getInstance().getIp() + Constants.GET_HIDDENRECORD, params, new BaseJsonRes() {
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
                    List<HiddenDangerRecord> tempList = JSONArray.parseArray(rows, HiddenDangerRecord.class);
                    if (curpage == 1) {
                        recordList.clear();
                        if(tempList.size()==0){
                            Utils.showLongToast(getContext(), "没有查询到数据!");
                        }
                    }
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
        if(TextUtils.isEmpty(tvStartDate.getText())){
            paramsMap.put("customParamsThree", tvStartDate.getText().toString());
        }
        SelectItem spProfessionItem = (SelectItem) spProfession.getSelectedItem();
        if (null != spProfessionItem) {
            if (!TextUtils.isEmpty(spProfessionItem.id)) {
                paramsMap.put("customParamsOne", spProfessionItem.id);//状态
            }
        }
        SelectItem spAreaItem = (SelectItem) spArea.getSelectedItem();
        if (null != spAreaItem) {
            if (!TextUtils.isEmpty(spAreaItem.id)) {
                paramsMap.put("customParamsTwo", spAreaItem.id);//状态
            }
        }
        String jsonString = JSON.toJSONString(paramsMap);
        params.put("threeFixJsonData", jsonString);
        netClient.post(Data.getInstance().getIp() + Constants.GET_HIDDENRELEASELIST, params, new BaseJsonRes() {
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
                    List<ThreeFix> tempList = JSONArray.parseArray(rows, ThreeFix.class);
                    if (curpage == 1) {
                        threeFixesList.clear();
                        if(tempList.size()==0){
                            Utils.showLongToast(getContext(), "没有查询到数据!");
                        }
                    }
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
        if(TextUtils.isEmpty(tvStartDate.getText())){
            paramsMap.put("customParamsFour", tvStartDate.getText().toString());
        }
        SelectItem spProfessionItem = (SelectItem) spProfession.getSelectedItem();
        if (null != spProfessionItem) {
            if (!TextUtils.isEmpty(spProfessionItem.id)) {
                paramsMap.put("customParamsTwo", spProfessionItem.id);//状态
            }
        }
        SelectItem spAreaItem = (SelectItem) spArea.getSelectedItem();
        if (null != spAreaItem) {
            if (!TextUtils.isEmpty(spAreaItem.id)) {
                paramsMap.put("customParamsThree", spAreaItem.id);//状态
            }
        }
        SelectItem spCheckUnitsItem = (SelectItem) spCheckUnits.getSelectedItem();
        if (null != spCheckUnitsItem) {
            if (!TextUtils.isEmpty(spCheckUnitsItem.id)) {
                paramsMap.put("customParamsThree", spCheckUnitsItem.id);//状态
            }
        }
        String jsonString = JSON.toJSONString(paramsMap);
        params.put("threeFixJsonData", jsonString);
        netClient.post(Data.getInstance().getIp() + Constants.GET_RECTIFICATIONLIST, params, new BaseJsonRes() {
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
                    List<ThreeFix> tempList = JSONArray.parseArray(rows, ThreeFix.class);
                    if (curpage == 1) {
                        threeFixesList.clear();
                        if(tempList.size()==0){
                            Utils.showLongToast(getContext(), "没有查询到数据!");
                        }
                    }
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
        if(TextUtils.isEmpty(tvStartDate.getText())){
            paramsMap.put("customParamsThree", tvStartDate.getText().toString());
        }
        SelectItem spProfessionItem = (SelectItem) spProfession.getSelectedItem();
        if (null != spProfessionItem) {
            if (!TextUtils.isEmpty(spProfessionItem.id)) {
                paramsMap.put("customParamsOne", spProfessionItem.id);//状态
            }
        }
        SelectItem spAreaItem = (SelectItem) spArea.getSelectedItem();
        if (null != spAreaItem) {
            if (!TextUtils.isEmpty(spAreaItem.id)) {
                paramsMap.put("customParamsTwo", spAreaItem.id);//状态
            }
        }
        String jsonString = JSON.toJSONString(paramsMap);
        params.put("threeFixJsonData", jsonString);
        netClient.post(Data.getInstance().getIp() + Constants.GET_RECTIFICATIONLIST, params, new BaseJsonRes() {

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
                    List<ThreeFix> tempList = JSONArray.parseArray(rows, ThreeFix.class);
                    if (curpage == 1) {
                        threeFixesList.clear();
                        if(tempList.size()==0){
                            Utils.showLongToast(getContext(), "没有查询到数据!");
                        }
                    }
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
        if(TextUtils.isEmpty(tvStartDate.getText())){
            paramsMap.put("customParamsFour", tvStartDate.getText().toString());
        }
        SelectItem spProfessionItem = (SelectItem) spProfession.getSelectedItem();
        if (null != spProfessionItem) {
            if (!TextUtils.isEmpty(spProfessionItem.id)) {
                paramsMap.put("customParamsTwo", spProfessionItem.id);//状态
            }
        }
        SelectItem spAreaItem = (SelectItem) spArea.getSelectedItem();
        if (null != spAreaItem) {
            if (!TextUtils.isEmpty(spAreaItem.id)) {
                paramsMap.put("customParamsThree", spAreaItem.id);//状态
            }
        }
        String jsonString = JSON.toJSONString(paramsMap);
        params.put("threeFixJsonData", jsonString);
        netClient.post(Data.getInstance().getIp() + Constants.GET_OVERDUELIST, params, new BaseJsonRes() {

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
                    List<ThreeFix> tempList = JSONArray.parseArray(rows, ThreeFix.class);
                    if (curpage == 1) {
                        threeFixesList.clear();
                        if(tempList.size()==0){
                            Utils.showLongToast(getContext(), "没有查询到数据!");
                        }
                    }
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
        if(TextUtils.isEmpty(tvStartDate.getText())){
            paramsMap.put("customParamsFour", tvStartDate.getText().toString());
        }
        SelectItem spProfessionItem = (SelectItem) spProfession.getSelectedItem();
        if (null != spProfessionItem) {
            if (!TextUtils.isEmpty(spProfessionItem.id)) {
                paramsMap.put("customParamsTwo", spProfessionItem.id);//状态
            }
        }
        SelectItem spAreaItem = (SelectItem) spArea.getSelectedItem();
        if (null != spAreaItem) {
            if (!TextUtils.isEmpty(spAreaItem.id)) {
                paramsMap.put("customParamsThree", spAreaItem.id);//状态
            }
        }
        SelectItem spCheckUnitsItem = (SelectItem) spCheckUnits.getSelectedItem();
        if (null != spCheckUnitsItem) {
            if (!TextUtils.isEmpty(spCheckUnitsItem.id)) {
                paramsMap.put("customParamsFive", spCheckUnitsItem.id);//状态
            }
        }
        String jsonString = JSON.toJSONString(paramsMap);
        params.put("threeFixJsonData", jsonString);
        netClient.post(Data.getInstance().getIp() + Constants.GET_REVIEWLIST, params, new BaseJsonRes() {

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
                    List<ThreeFix> tempList = JSONArray.parseArray(rows, ThreeFix.class);
                    if (curpage == 1) {
                        threeFixesList.clear();
                        if(tempList.size()==0){
                            Utils.showLongToast(getContext(), "没有查询到数据!");
                        }
                    }
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
                flag = 1;
                adapter = new HiddenDangeRecordAdapter(recordList);
                recyclerView.setAdapter(adapter);
                break;
            case R.id.ll_manage_release:
                flag = 2;
                String userRole = UserUtils.getUserRoleids(getActivity());
                if (!"8".equals(userRole) && !"62".equals(userRole)) {
                    threeFixesList.clear();
                    adapter = new HiddenDangeMuitipleAdapter(HiddenDangeMuitipleAdapter.FLAG_REALEASE, threeFixesList,getActivity());
                    recyclerView.setAdapter(adapter);
                } else {
                    Utils.showLongToast(getContext(), "没有权限进行该操作!");
                }
                break;
            case R.id.ll_manage_rectification:
                flag = 3;
                String userRoles = UserUtils.getUserRoleids(getActivity());
                if (!"8".equals(userRoles) && !"62".equals(userRoles)) {
                    threeFixesList.clear();
                    adapter = new HiddenDangeMuitipleAdapter(HiddenDangeMuitipleAdapter.FLAG_RECTIFICATION, threeFixesList,getActivity());
                    ((HiddenDangeMuitipleAdapter) adapter).setItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, final int position, int flag) {
                            if (flag == HiddenDangeMuitipleAdapter.FLAG_RECTIFICATION) {
                                //@Todo 直接整改
                                MyAlertDialog myAlertDialog = new MyAlertDialog(ctx,
                                        new MyAlertDialog.DialogListener() {
                                            @Override
                                            public void affirm() {
                                                //确定入口
                                                goRectification(position);
                                            }

                                            @Override
                                            public void cancel() {

                                            }
                                        }, "你确定要整改此么？");
                                myAlertDialog.show();

                            }
                        }

                        @Override
                        public boolean onItemLongClick(View view, int position) {
                            return false;
                        }
                    });
                    recyclerView.setAdapter(adapter);
                } else {
                    Utils.showLongToast(getContext(), "没有权限进行该操作!");
                }
                break;
            case R.id.ll_manage_tracking:
                flag = 4;
                threeFixesList.clear();
                adapter = new HiddenDangeTrackingAdapter(threeFixesList);
                recyclerView.setAdapter(adapter);
                break;
            case R.id.ll_manage_overdue:
                flag = 5;
                threeFixesList.clear();
                adapter = new HiddenDangeMuitipleAdapter(HiddenDangeMuitipleAdapter.FLAG_OVERDUE,threeFixesList,getActivity());
                recyclerView.setAdapter(adapter);
                break;
            case R.id.ll_manage_review:
                flag = 6;
                threeFixesList.clear();
                adapter = new HiddenDangeMuitipleAdapter(HiddenDangeMuitipleAdapter.FLAG_REVIEW, threeFixesList,getActivity());
                recyclerView.setAdapter(adapter);
                break;
            default:
                break;
        }
        llOption.setVisibility(View.GONE);
        setUpTopView();
        tvArea.setText("");
        tvProfession.setText("");
        sid = "";
        areaid = "";
        tvHiddenUnits.setText("");
        getDataByPage(1);
    }

    //根据位置整改
    private void goRectification(final int position) {
        RequestParams params = new RequestParams();
        params.put("ids", threeFixesList.get(position).getId());
        netClient.post(Data.getInstance().getIp() + Constants.COMPLETERECTIFY, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "完成整改返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    Utils.showLongToast(ctx, "隐患整改成功！");
                    threeFixesList.remove(position);
                    adapter.notifyItemRemoved(position);
                }
            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "完成整改返回错误信息：" + content);
                Utils.showLongToast(ctx, content);
            }
        });
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

    public int getAddVisible() {
        switch (flag) {
            case 0:
            case 1:
                return View.VISIBLE;
        }
        return View.GONE;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DatePickerActivity.REQUEST && resultCode == RESULT_OK) {
            String date = data.getStringExtra(DatePickerActivity.DATE);
            tvStartDate.setText(date);
            //刷新
        }
    }


    //获取所属专业
    private void getSpecialty(final int flag) {
        RequestParams params = new RequestParams();
        netClient.post(Data.getInstance().getIp() + Constants.GET_SPECIALTY, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "获取所属专业返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    List<Specialty> collieryTeams = JSONArray.parseArray(data, Specialty.class);
                    List<SelectItem> selectItems = new ArrayList<SelectItem>();
                    for (int i = 0; i < collieryTeams.size(); i++) {
                        if (i == 0) {
                            SelectItem selectItem = new SelectItem();
                            selectItem.name = "请选择";
                            selectItem.id = "";
                            selectItems.add(selectItem);
                        }
                        SelectItem selectItem = new SelectItem();
                        selectItem.name = collieryTeams.get(i).getSname();
                        selectItem.id = collieryTeams.get(i).getId();
                        selectItems.add(selectItem);
                    }
                    spProfessionAdapter = SpinnerAdapter.createFromResource(getActivity(), selectItems);
                    setSpinner(spProfession,spProfessionAdapter,flag);
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "获取所属专业返回错误信息：" + content);
                com.example.administrator.riskprojects.tools.Utils.showLongToast(getActivity(), content);
            }
        });
    }

    //复查
    private void setCheckResultSpinner(Spinner spinner) {
        List<SelectItem> selectItems = new ArrayList<>();
        SelectItem selectItem = new SelectItem();
        selectItem.name = "请选择";
        selectItem.id = "";
        SelectItem selectItemy = new SelectItem();
        selectItemy.name = "已通过";
        selectItemy.id = "0";
        SelectItem selectItemw = new SelectItem();
        selectItemw.name = "未通过";
        selectItemw.id = "1";
        selectItems.add(selectItem);
        selectItems.add(selectItemy);
        selectItems.add(selectItemw);
        spCheckUnitsAdapter = SpinnerAdapter.createFromResource(ctx, selectItems, Gravity.CENTER_VERTICAL | Gravity.LEFT);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spCheckUnitsAdapter.setSelectedPostion(position);
                getHiddenRecord(Constants.PAGE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner.setAdapter(spCheckUnitsAdapter);
    }

    //整改
    private void setzhenggaiResultSpinner(Spinner spinner) {
        List<SelectItem> selectItems = new ArrayList<>();
        SelectItem selectItem = new SelectItem();
        selectItem.name = "请选择";
        selectItem.id = "";
        SelectItem selectItemy = new SelectItem();
        selectItemy.name = "已整改";
        selectItemy.id = "1";
        SelectItem selectItemw = new SelectItem();
        selectItemw.name = "未通过";
        selectItemw.id = "0";
        selectItems.add(selectItem);
        selectItems.add(selectItemy);
        selectItems.add(selectItemw);
        spCheckUnitsAdapter = SpinnerAdapter.createFromResource(ctx, selectItems, Gravity.CENTER_VERTICAL | Gravity.LEFT);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spCheckUnitsAdapter.setSelectedPostion(position);
                getRectificationList(Constants.PAGE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner.setAdapter(spCheckUnitsAdapter);
    }

    //处理否设置
    private void setIshandleSpinner(Spinner spinner) {
        List<SelectItem> selectItems = new ArrayList<>();
        SelectItem selectItem = new SelectItem();
        selectItem.name = "请选择";
        selectItem.id = "";
        SelectItem selectItemw = new SelectItem();
        selectItemw.name = "未处理";
        selectItemw.id = "0";
        SelectItem selectItemy = new SelectItem();
        selectItemy.name = "已处理";
        selectItemy.id = "1";
        selectItems.add(selectItem);
        selectItems.add(selectItemw);
        selectItems.add(selectItemy);
        spProfessionAdapter = SpinnerAdapter.createFromResource(ctx, selectItems, Gravity.CENTER_VERTICAL | Gravity.LEFT);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spProfessionAdapter.setSelectedPostion(position);
                getHiddenRecord(Constants.PAGE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner.setAdapter(spProfessionAdapter);
    }

    //获取检查单位
    private void getHiddenYHGSLX(final int flag) {
        RequestParams params = new RequestParams();
        params.put("dictTypeCode","YHGSLX");
        netClient.post(Data.getInstance().getIp()+Constants.GET_DATADICT, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "获取检查单位返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    List<DataDictionary> collieryTeams = JSONArray.parseArray(data, DataDictionary.class);
                    List<SelectItem> selectItems = new ArrayList<SelectItem>();
                    for (int i = 0; i < collieryTeams.size(); i++) {
                        if (i == 0) {
                            SelectItem selectItem = new SelectItem();
                            selectItem.name = "请选择";
                            selectItem.id = "";
                            selectItems.add(selectItem);
                        }
                        SelectItem selectItem = new SelectItem();
                        selectItem.name = collieryTeams.get(i).getName();
                        selectItem.id = collieryTeams.get(i).getId();
                        selectItems.add(selectItem);
                    }
                    spCheckUnitsAdapter = SpinnerAdapter.createFromResource(getActivity(), selectItems);
                    spCheckUnits.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            spCheckUnitsAdapter.setSelectedPostion(position);
                            if(flag==1){
                                getHiddenRecord(Constants.PAGE);
                            }else{
                                getReviewList(Constants.PAGE);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    spCheckUnits.setAdapter(spCheckUnitsAdapter);
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "获取检查单位返回错误信息：" + content);
                com.example.administrator.riskprojects.tools.Utils.showLongToast(getActivity(), content);
            }
        });
    }

    //隐患状态
    private void setHiddenStatusSpinner(Spinner spinner) {
        List<SelectItem> selectItems = new ArrayList<>();
        String[] hiddenStatus = {"请选择", "筛选", "五定中", "整改中", "验收中", "销项"};
        for (int i = 0; i < hiddenStatus.length; i++) {
            SelectItem selectItem = new SelectItem();
            selectItem.name = hiddenStatus[i];
            selectItem.id = Integer.toString(i - 1);
            selectItems.add(selectItem);
        }

        spStatusAdapter = SpinnerAdapter.createFromResource(ctx, selectItems, Gravity.CENTER_VERTICAL | Gravity.LEFT);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spStatusAdapter.setSelectedPostion(position);
                getHiddenRecord(Constants.PAGE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spStatus.setAdapter(spStatusAdapter);
    }

    //获取区域
    private void getArea(final int flag) {
        RequestParams params = new RequestParams();
        params.put("employeeId", UserUtils.getUserID(getActivity()));
        netClient.post(Data.getInstance().getIp()+Constants.GET_AREA, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "获取区域返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    List<Area> collieryTeams = JSONArray.parseArray(data, Area.class);
                    List<SelectItem> selectItems = new ArrayList<SelectItem>();
                    for (int i = 0; i < collieryTeams.size(); i++) {
                        if (i == 0) {
                            SelectItem selectItem = new SelectItem();
                            selectItem.name = "请选择";
                            selectItem.id = "";
                            selectItems.add(selectItem);
                        }
                        SelectItem selectItem = new SelectItem();
                        selectItem.name = collieryTeams.get(i).getAreaName();
                        selectItem.id = collieryTeams.get(i).getId();
                        selectItems.add(selectItem);
                    }
                    spAreaAdapter = SpinnerAdapter.createFromResource(getActivity(), selectItems);
                    setSpinner(spArea, spAreaAdapter,flag);
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "获取区域返回错误信息：" + content);
                Utils.showLongToast(getActivity(), content);
            }
        });
    }

    //专业列表
    private void setSpinner(final Spinner spinner,final SpinnerAdapter adapter,final int flag) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adapter.setSelectedPostion(position);
                if(flag==1){
                    getHiddenRecord(Constants.PAGE);
                }else if(flag==2){
                    getReleaseList(Constants.PAGE);
                }else if(flag==3){
                    getRectificationList(Constants.PAGE);
                }else if(flag==4){
                    getTrackingList(Constants.PAGE);
                }else if(flag==5){
                    getOverdueList(Constants.PAGE);
                }else if(flag==6){
                    getReviewList(Constants.PAGE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
            spinner.setAdapter(adapter);
    }

}