package com.example.administrator.riskprojects.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.administrator.riskprojects.Adpter.HomePageTotalDetailAdapter;
import com.example.administrator.riskprojects.Adpter.IdentificationEvaluationAdapter;
import com.example.administrator.riskprojects.Adpter.SpinnerAdapter;
import com.example.administrator.riskprojects.BaseActivity;
import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.bean.HiddenDangerRecord;
import com.example.administrator.riskprojects.bean.IdentificationEvaluation;
import com.example.administrator.riskprojects.bean.RiskGrade;
import com.example.administrator.riskprojects.bean.SelectItem;
import com.example.administrator.riskprojects.bean.ThreeFix;
import com.example.administrator.riskprojects.common.NetUtil;
import com.example.administrator.riskprojects.dialog.FlippingLoadingDialog;
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

public class HomePageTotalDetailActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{
    private static final String TAG = "SuperviseRecordManageme";
    private TextView txtLeft;
    private ImageView imgLeft;
    private TextView txtTitle;
    private ImageView imgRight;
    private TextView txtRight;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    protected NetClient netClient;
    private boolean onLoading = false;
    protected FlippingLoadingDialog mLoadingDialog;
    private int page = 1;
    private int pagesize = 1;
    private TextView errorMessage;
    private TextView errorTips;
    private LinearLayoutCompat llOptionNew;
    private LinearLayoutCompat llSelectRiskGrade;
    private LinearLayoutCompat llExpand;
    private TextView tvExpand;
    private ImageView ivExpand;
    private Spinner riskSpinner;
    private SpinnerAdapter spRiskAdapter;
    private HomePageTotalDetailAdapter adapter;
    private List<HiddenDangerRecord> recordLists = new ArrayList<HiddenDangerRecord>();
    private List<ThreeFix> threeFixLists = new ArrayList<ThreeFix>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_totaldetail);
        netClient = new NetClient(HomePageTotalDetailActivity.this);
        initView();
        setView();
        initdata(Constants.PAGE);
        getRiskGrade();
    }

    private void setView() {
        txtTitle.setText(getIntent().getStringExtra("topname"));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void initView() {
        txtLeft = findViewById(R.id.txt_left);
        imgLeft = findViewById(R.id.img_left);
        txtTitle = findViewById(R.id.txt_title);
        imgRight = findViewById(R.id.img_right);
        txtRight = findViewById(R.id.txt_right);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerView = findViewById(R.id.recyclerView);
        errorMessage = findViewById(R.id.error_message);
        errorTips = findViewById(R.id.error_tips);
        riskSpinner = findViewById(R.id.sp_risk_grade);
        llOptionNew = findViewById(R.id.ll_option_new);
        llSelectRiskGrade = findViewById(R.id.ll_select_check_results);
        llExpand = findViewById(R.id.ll_expand);
        tvExpand = findViewById(R.id.tv_expand);
        ivExpand = findViewById(R.id.iv_expand);
        llExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llSelectRiskGrade.setVisibility(llSelectRiskGrade.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                tvExpand.setText(llSelectRiskGrade.getVisibility() == View.VISIBLE ? "收起选项" : "展开选项");
                ivExpand.setSelected(llSelectRiskGrade.getVisibility() == View.VISIBLE);
            }
        });
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
                                && Math.abs(lastChildBottom - recyclerBottom) <= DensityUtil.dip2px(HomePageTotalDetailActivity.this,
                                8)
                                && lastPosition == recyclerView.getLayoutManager().getItemCount() - 1
                                && newState == RecyclerView.SCROLL_STATE_IDLE) {
                            //loading more data

                            if (page < pagesize) {
                                initdata(Integer.toString(page + 1));
                            } else {
                                Toast.makeText(HomePageTotalDetailActivity.this, "全部加载完毕", Toast.LENGTH_SHORT).show();
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

    private void initdata(final String curpage){
        RequestParams params = new RequestParams();
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("page", curpage);
        paramsMap.put("rows", Constants.ROWS);
        paramsMap.put("employeeId", UserUtils.getUserID(HomePageTotalDetailActivity.this));
        String datatype = getIntent().getStringExtra("datatype");
        String url = "";
        if(datatype.equals("mLlDeleteNum")){
            paramsMap.put("ishandle","1");
            String jsonString = JSON.toJSONString(paramsMap);
            params.put("hiddenDangerRecordJsonData", jsonString);
            url = Constants.GET_XIAOHAOLIST;
        }else if(datatype.equals("mLlWithinTheTimeLimitNum")){
            paramsMap.put("customParamsSix","0");
            String jsonString = JSON.toJSONString(paramsMap);
            params.put("threeFixJsonData", jsonString);
            url = Constants.GET_WITHINTHETIMELIST;
        }else if(datatype.equals("mLlUnchangeNum")){
            paramsMap.put("ishandle","0");
            String jsonString = JSON.toJSONString(paramsMap);
            params.put("hiddenDangerRecordJsonData", jsonString);
            url = Constants.GET_XIAOHAOLIST;
        }else if(datatype.equals("mLlForAcceptanceNum")){
            paramsMap.put("customParamsSix","0");
            String jsonString = JSON.toJSONString(paramsMap);
            params.put("threeFixJsonData", jsonString);
            url = Constants.GET_FORACCEPTANCELIST;
        }else if(datatype.equals("mLlNianduNum")){
            llOptionNew.setVisibility(View.VISIBLE);
            paramsMap.put("evaluationType","0");
            SelectItem spRiskGradeItem = (SelectItem) riskSpinner.getSelectedItem();
            if (null != spRiskGradeItem) {
                if (!TextUtils.isEmpty(spRiskGradeItem.id)) {
                    paramsMap.put("riskGnameId", spRiskGradeItem.id);//状态
                }
            }
            String jsonString = JSON.toJSONString(paramsMap);
            params.put("evaluationJson", jsonString);
            url = Constants.GET_EVALUATIONCOUNT_LIST;
        }else if(datatype.equals("mLlZhuanxiangNum")){
            llOptionNew.setVisibility(View.VISIBLE);
            SelectItem spRiskGradeItem = (SelectItem) riskSpinner.getSelectedItem();
            if (null != spRiskGradeItem) {
                if (!TextUtils.isEmpty(spRiskGradeItem.id)) {
                    paramsMap.put("riskGnameId", spRiskGradeItem.id);//状态
                }
            }
            paramsMap.put("evaluationType","1");
            String jsonString = JSON.toJSONString(paramsMap);
            params.put("evaluationJson", jsonString);
            url = Constants.GET_EVALUATIONCOUNT_LIST;
        }
        getDetailRecord(url,params,datatype,curpage);
    }

    //查询督办列表
    private void getDetailRecord(String url,RequestParams params,final String datatype,final String curpage) {
        netClient.post(Data.getInstance().getIp()+ url, params, new BaseJsonRes() {
            @Override
            public void onStart() {
                super.onStart();
                onLoading = true;
                if (curpage.equals("1")) {
                    if (!swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(true);
                    }
                } else {
                    Toast.makeText(HomePageTotalDetailActivity.this, "正在加载" + curpage + "/" + pagesize, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "首页统计详情列表返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    JSONObject returndata = JSON.parseObject(data);
                    String rows = returndata.getString("rows");
                    page = Integer.parseInt(returndata.getString("page"));
                    pagesize = Integer.parseInt(returndata.getString("totalPage"));
                    if(datatype.equals("mLlDeleteNum")||datatype.equals("mLlUnchangeNum")){
                        List<HiddenDangerRecord> recordList = JSONArray.parseArray(rows, HiddenDangerRecord.class);
                        recordLists.addAll(recordList);
                    }else{
                        List<ThreeFix> recordList = JSONArray.parseArray(rows, ThreeFix.class);
                        threeFixLists.addAll(recordList);
                    }
                    if(datatype.equals("mLlZhuanxiangNum")||datatype.equals("mLlNianduNum")){
                        List<IdentificationEvaluation> recordList = JSONArray.parseArray(rows, IdentificationEvaluation.class);
                        recyclerView.setAdapter(new IdentificationEvaluationAdapter(recordList));
                    }else{
                        recyclerView.setAdapter(new HomePageTotalDetailAdapter(recordLists,threeFixLists,datatype));
                    }
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "查询督办列表返回错误信息：" + content);
                Utils.showLongToast(HomePageTotalDetailActivity.this, content);
                return;
            }

            @Override
            public void onFinish() {
                super.onFinish();
                getLoadingDialog("正在连接服务器...  ").dismiss();
                swipeRefreshLayout.setRefreshing(false);
                onLoading = false;
            }
        });
    }

    //获取风险等级
    private void getRiskGrade() {
        if (!NetUtil.checkNetWork(HomePageTotalDetailActivity.this)) {
            String jsondata = Utils.getValue(HomePageTotalDetailActivity.this, Constants.GET_RISKGRADE_LIST);
            if("".equals(jsondata)){
                Utils.showShortToast(HomePageTotalDetailActivity.this, "没有联网，没有请求到数据");
            }else{
                resultRiskGrade(jsondata);
            }
        }else{
            RequestParams params = new RequestParams();
            netClient.post(Data.getInstance().getIp() + Constants.GET_RISKGRADE_LIST, params, new BaseJsonRes() {

                @Override
                public void onMySuccess(String data) {
                    Log.i(TAG, "获取风险等级返回数据：" + data);
                    if (!TextUtils.isEmpty(data)) {
                        resultRiskGrade(data);
                    }

                }

                @Override
                public void onMyFailure(String content) {
                    Log.e(TAG, "获取风险等级返回错误信息：" + content);
                    Utils.showLongToast(HomePageTotalDetailActivity.this, content);
                }
            });
        }
    }

    private void resultRiskGrade(String data){
        List<RiskGrade> riskGradeList = JSONArray.parseArray(data, RiskGrade.class);
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        for (int i = 0; i < riskGradeList.size(); i++) {
            if (i == 0) {
                SelectItem selectItem = new SelectItem();
                selectItem.name = "请选择";
                selectItem.id = "";
                selectItems.add(selectItem);
            }
            SelectItem selectItem = new SelectItem();
            selectItem.name = riskGradeList.get(i).getGname();
            selectItem.id = riskGradeList.get(i).getId();
            selectItems.add(selectItem);
        }
        spRiskAdapter = SpinnerAdapter.createFromResource(HomePageTotalDetailActivity.this, selectItems);
        setSpinner(riskSpinner, spRiskAdapter);
    }

    //风险等级列表
    private void setSpinner(final Spinner spinner, final SpinnerAdapter adapter) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adapter.setSelectedPostion(position);
                initdata(Constants.PAGE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner.setAdapter(adapter);
    }

    public FlippingLoadingDialog getLoadingDialog(String msg) {
        if (mLoadingDialog == null)
            mLoadingDialog = new FlippingLoadingDialog(HomePageTotalDetailActivity.this, msg);
        return mLoadingDialog;
    }

    @Override
    public void onRefresh() {
        initdata(Constants.PAGE);
    }

}
