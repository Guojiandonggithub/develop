package com.example.administrator.riskprojects.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.administrator.riskprojects.Adpter.InspectionAdapter;
import com.example.administrator.riskprojects.BaseActivity;
import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.bean.CarRecord;
import com.example.administrator.riskprojects.bean.HiddenDangerRecord;
import com.example.administrator.riskprojects.net.BaseJsonRes;
import com.example.administrator.riskprojects.net.NetClient;
import com.example.administrator.riskprojects.tools.Constants;
import com.example.administrator.riskprojects.tools.UserUtils;
import com.example.administrator.riskprojects.tools.Utils;
import com.example.administrator.riskprojects.util.TimeCount;
import com.juns.health.net.loopj.android.http.RequestParams;
import com.khdz.patrol.bleSdk.BleScanner;
import com.khdz.patrol.bleSdk.IScanCardCallback;

import java.util.Date;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class InspectionActivity extends BaseActivity {
    private BleScanner mBleScanner;
    private static final String TAG = "InspectionActivity";
    private TextView txtLeft;
    private ImageView imgLeft;
    private TextView txtTitle;
    private ImageView imgRight;
    private TextView txtRight;
    private TextView tvName;
    private TextView tvTime;
    private RecyclerView recyclerView;
    private LinearLayoutCompat llBottom;
    private TextView tvOk;
    private TextView tvAccredit;
    private InspectionAdapter adapter;
    private TimeCount timeCount;
    private List<String> test;
    protected NetClient netClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection);
        netClient = new NetClient(InspectionActivity.this);
        mBleScanner = new BleScanner(this);
        //在扫描过程中如果蓝牙关闭并重新打开后是否自动开始扫描, true:将在重新打开蓝牙后,自动开始扫描
        mBleScanner.setAutoContinue(true);
        initView();
        setView();
        setUpData();
    }

    private void setUpData() {
        tvName.setText(UserUtils.getUserName(InspectionActivity.this));
        SimpleDateFormat simpleDateDate = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateTime = new SimpleDateFormat("HH:mm:ss");
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        String classname="";
        try {
            Date date0 = simpleDateTime.parse("00:00:00");
            Date date8 = simpleDateTime.parse("07:59:59");
            Date date16 = simpleDateTime.parse("15:59:59");
            Date date23 = simpleDateTime.parse("23:59:59");
            String time = simpleDateTime.format(date);
            Date currenttime = simpleDateTime.parse(time);
            if(isDate2Bigger(currenttime,date8,date0)){
                classname= "0点班";
            }else if(isDate2Bigger(currenttime,date16,date8)){
                classname= "8点班";
            }else if(isDate2Bigger(currenttime,date23,date16)){
                classname= "4点班";
            }
        }catch (Exception e){

        }

        tvTime.setText("今日班次："+simpleDateDate.format(date)+" "+classname);
        getCardRecordList(Integer.parseInt(Constants.PAGE));
    }

    private void setView() {
        txtTitle.setText("巡检记录");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //开始扫描,设置监听
                    boolean startScanSuccessful = mBleScanner.StartScan(new IScanCardCallback() {
                        @Override
                        public void onScanCard(String card, boolean isLowPower) {
                            addResult("扫描到蓝牙卡,卡号[" + card + "],电量[:" + (isLowPower ? "不足]" : "充足]"));
                            mBleScanner.StopScan();
                            addCardRecord(card);
                        }
                    }, BleScanner.ScanMode.SCAN_MODE_BALANCED);
                    if (startScanSuccessful) {
                        addResult("---开始扫描---");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    addResult(e.toString());
                }
            }
        });
        tvAccredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //当版本>=6.0时,需要弹窗请求用户授予权限
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions();
                } else {
                    addResult("当前手机版本低于6.0,无需手动请求权限");
                }
            }
        });
    }

    private void initView() {
        txtLeft = findViewById(R.id.txt_left);
        imgLeft = findViewById(R.id.img_left);
        txtTitle = findViewById(R.id.txt_title);
        imgRight = findViewById(R.id.img_right);
        txtRight = findViewById(R.id.txt_right);
        tvName = findViewById(R.id.tv_name);
        tvTime = findViewById(R.id.tv_time);
        recyclerView = findViewById(R.id.recyclerView);
        llBottom = findViewById(R.id.ll_bottom);
        tvOk = findViewById(R.id.tv_ok);
        tvAccredit = findViewById(R.id.tv_accredit);
    }

    //获取巡检记录列表
    private void getCardRecordList(final int page) {
        RequestParams params = new RequestParams();
        Map<String, String> paramsMap = new HashMap();
        paramsMap.put("page", Integer.toString(page));
        paramsMap.put("rows", Constants.ROWS);
        paramsMap.put("userId", UserUtils.getUserID(InspectionActivity.this));
        String jsonString = JSON.toJSONString(paramsMap);
        params.put("carRecordJson", jsonString);
            netClient.post(Data.getInstance().getIp()+ Constants.GET_CARDRECORDLIST, params, new BaseJsonRes() {

                @Override
                public void onMySuccess(String data) {
                    Log.i(TAG, "巡检记录返回数据：" + data);
                    if (!TextUtils.isEmpty(data)) {
                        JSONObject returndata = JSON.parseObject(data);
                        String rows = returndata.getString("rows");
                        int page = Integer.parseInt(returndata.getString("page"));
                        int pagesize = Integer.parseInt(returndata.getString("pagesize"));
                        List<CarRecord> recordList = JSONArray.parseArray(rows, CarRecord.class);
                        adapter = new InspectionAdapter(recordList);
                        recyclerView.setAdapter(adapter);
                    }

                }

                @Override
                public void onMyFailure(String content) {
                    Log.e(TAG, "巡检记录返回错误信息：" + content);
                    Utils.showLongToast(InspectionActivity.this, content);
                }
            });
    }

    //提交巡检记录
    private void addCardRecord(String cardCode) {
        SimpleDateFormat simpleDateDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        RequestParams params = new RequestParams();
        Map<String, String> paramsMap = new HashMap();
        paramsMap.put("cardCode", cardCode);
        paramsMap.put("cardTime", simpleDateDate.format(date));
        paramsMap.put("userId", UserUtils.getUserID(InspectionActivity.this));
        String jsonString = JSON.toJSONString(paramsMap);
        params.put("carRecordJson", jsonString);
        netClient.post(Data.getInstance().getIp()+ Constants.ADD_CARDRECORDLIST, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "提交巡检记录返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    if (!tvOk.isSelected()) {
                        //获取新记录
                        tvOk.setEnabled(false);
                        getCardRecordList(Integer.parseInt(Constants.PAGE));
                        adapter.notifyDataSetChanged();
                        adapter.setWait(true);
                        adapter.notifyDataSetChanged();
                        //获取后倒计时
                        timeCount = new TimeCount(60000, 1000, tvOk);
                        timeCount.setListener(new TimeCount.OnTimeFinishListener() {
                            @Override
                            public void onTimeFinish() {
                                //倒计时结束，列表最后的巡检显示
                                adapter.setWait(false);
                                adapter.notifyDataSetChanged();
                                tvOk.setEnabled(true);
                            }
                        });
                        timeCount.start();


                    }
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "提交巡检记录返回错误信息：" + content);
                Utils.showLongToast(InspectionActivity.this, content);
            }
        });
    }

    //region 申请位置权限,安卓手机6.0以上读蓝牙广播需要该权限
    public final static int REQUEST_BLE_PATROL_PERMISSION = 1001;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestPermissions() {
        //判断是否已有权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //申请权限 位置权限
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_BLE_PATROL_PERMISSION);
        } else {
            addResult("APP已有权限,无需再次申请");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_BLE_PATROL_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    addResult("用户接受了[位置]权限");
                } else {
                    addResult("用户拒绝了[位置]权限");
                }
                break;
        }
    }

    private void addResult(final String result) {
        Utils.showLongToast(InspectionActivity.this, result);
    }


    /**
     * 比较两个日期的大小，日期格式为yyyy-MM-dd
     *
     * @return true <br/>false
     */
    public static boolean isDate2Bigger(Date current,Date dt1, Date dt2) {

        boolean isBigger = false;
        if (dt1.getTime() >= current.getTime()&&dt2.getTime()<current.getTime()) {
            isBigger = true;
        }
        return isBigger;
    }

}
