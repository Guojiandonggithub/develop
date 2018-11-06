package com.example.administrator.riskprojects.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.administrator.riskprojects.Adpter.SupervisorRecordManageAdapter;
import com.example.administrator.riskprojects.BaseActivity;
import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.bean.SupervisionRecord;
import com.example.administrator.riskprojects.common.NetUtil;
import com.example.administrator.riskprojects.net.BaseJsonRes;
import com.example.administrator.riskprojects.net.NetClient;
import com.example.administrator.riskprojects.tools.Constants;
import com.example.administrator.riskprojects.tools.Utils;
import com.juns.health.net.loopj.android.http.RequestParams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuperviseRecordManagementActivity extends BaseActivity {
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
        setContentView(R.layout.activity_supervise_record_management);
        netClient = new NetClient(SuperviseRecordManagementActivity.this);
        initView();
        setView();
        initdata();
    }

    private void setView() {
        txtTitle.setText("督办记录管理");
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
        addSupervisionRecord(Constants.PAGE);
    }

    //查询督办列表
    private void addSupervisionRecord(String page) {
        if (!NetUtil.checkNetWork(SuperviseRecordManagementActivity.this)) {
            String jsondata = Utils.getValue(SuperviseRecordManagementActivity.this, Constants.GET_SUPERVISIONRECORDLIST);
            Log.e(TAG, "jsondata: "+jsondata );
            if("".equals(jsondata)){
                Utils.showLongToast(SuperviseRecordManagementActivity.this, "网络异常，没有请求到数据");
            }else{
                resultHiddenRecord(jsondata);
            }
        }else{
            String hiddenDangerId = getIntent().getStringExtra("hiddenDangerId");
            RequestParams params = new RequestParams();
            Map<String, String> paramsMap = new HashMap<String, String>();
            paramsMap.put("page", page);
            paramsMap.put("rows", Constants.ROWS);
            paramsMap.put("hiddenDangerId", hiddenDangerId);
            String jsonString = JSON.toJSONString(paramsMap);
            params.put("supervisionRecordJsonData", jsonString);
            Log.e(TAG, "getHiddenRecord: 督办参数======"+params);
            netClient.post(Data.getInstance().getIp()+ Constants.GET_SUPERVISIONRECORDLIST, params, new BaseJsonRes() {

                @Override
                public void onMySuccess(String data) {
                    Log.i(TAG, "查询督办列表返回数据：" + data);
                    if (!TextUtils.isEmpty(data)) {
                        Utils.putValue(SuperviseRecordManagementActivity.this, Constants.GET_SUPERVISIONRECORDLIST, data);
                        resultHiddenRecord(data);
                    }

                }

                @Override
                public void onMyFailure(String content) {
                    Log.e(TAG, "查询督办列表返回错误信息：" + content);
                    Utils.showShortToast(SuperviseRecordManagementActivity.this, content);
                    return;
                }
            });
        }
    }

    private void resultHiddenRecord(String data){
        JSONObject returndata = JSON.parseObject(data);
        String rows = returndata.getString("rows");
        List<SupervisionRecord> recordList = JSONArray.parseArray(rows, SupervisionRecord.class);
        recyclerView.setAdapter(new SupervisorRecordManageAdapter(recordList));
    }
}
