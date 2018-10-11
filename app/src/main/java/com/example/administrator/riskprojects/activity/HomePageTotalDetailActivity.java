package com.example.administrator.riskprojects.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.administrator.riskprojects.Adpter.HomePageTotalDetailAdapter;
import com.example.administrator.riskprojects.Adpter.SupervisorRecordManageAdapter;
import com.example.administrator.riskprojects.BaseActivity;
import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.bean.HiddenDangerRecord;
import com.example.administrator.riskprojects.bean.SupervisionRecord;
import com.example.administrator.riskprojects.bean.ThreeFix;
import com.example.administrator.riskprojects.bean.UserInfo;
import com.example.administrator.riskprojects.net.BaseJsonRes;
import com.example.administrator.riskprojects.net.NetClient;
import com.example.administrator.riskprojects.tools.Constants;
import com.example.administrator.riskprojects.tools.UserUtils;
import com.example.administrator.riskprojects.tools.Utils;
import com.juns.health.net.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomePageTotalDetailActivity extends BaseActivity {
    private static final String TAG = "SuperviseRecordManageme";
    private TextView txtLeft;
    private ImageView imgLeft;
    private TextView txtTitle;
    private ImageView imgRight;
    private TextView txtRight;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    protected NetClient netClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_totaldetail);
        netClient = new NetClient(HomePageTotalDetailActivity.this);
        initView();
        setView();
        initdata();
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
    }

    private void initdata(){
        RequestParams params = new RequestParams();
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("page", Constants.PAGE);
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
            String jsonString = JSON.toJSONString(paramsMap);
            params.put("threeFixJsonData", jsonString);
            url = Constants.GET_WITHINTHETIMELIST;
        }else if(datatype.equals("mLlUnchangeNum")){
            paramsMap.put("ishandle","0");
            String jsonString = JSON.toJSONString(paramsMap);
            params.put("hiddenDangerRecordJsonData", jsonString);
            url = Constants.GET_XIAOHAOLIST;
        }else if(datatype.equals("mLlForAcceptanceNum")){
            String jsonString = JSON.toJSONString(paramsMap);
            params.put("threeFixJsonData", jsonString);
            url = Constants.GET_FORACCEPTANCELIST;
        }
        getDetailRecord(url,params,datatype);
    }

    //查询督办列表
    private void getDetailRecord(String url,RequestParams params,final String datatype) {
        netClient.post(Data.getInstance().getIp()+ url, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "首页统计详情列表返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    JSONObject returndata = JSON.parseObject(data);
                    String rows = returndata.getString("rows");
                    recyclerView.setAdapter(new HomePageTotalDetailAdapter(rows,datatype));
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "查询督办列表返回错误信息：" + content);
                Utils.showLongToast(HomePageTotalDetailActivity.this, content);
                return;
            }
        });
    }
}
