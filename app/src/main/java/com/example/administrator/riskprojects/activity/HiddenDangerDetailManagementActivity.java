package com.example.administrator.riskprojects.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.administrator.riskprojects.BaseActivity;
import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.bean.HiddenDangerRecord;
import com.example.administrator.riskprojects.net.BaseJsonRes;
import com.example.administrator.riskprojects.net.NetClient;
import com.example.administrator.riskprojects.tools.Constants;
import com.example.administrator.riskprojects.tools.Utils;
import com.example.administrator.riskprojects.view.MyAlertDialog;
import com.juns.health.net.loopj.android.http.RequestParams;

public class HiddenDangerDetailManagementActivity extends BaseActivity {
    private static final String TAG = "HiddenDangerDetailManag";
    protected NetClient netClient;
    private TextView txtLeft;
    private ImageView imgLeft;
    private TextView txtTitle;
    private ImageView imgRight;
    private TextView txtRight;
    private TextView tvHiddenUnits;
    private TextView tvTimeOrOrder;
    private TextView tvHiddenContent;
    private TextView tvHiddenDangerBelongs;
    private LinearLayoutCompat expand;
    private TextView tvProfessional;
    private TextView tvArea;
    private TextView tvClasses;
    private TextView tvOversee;
    private ImageView ivStatusSecond;
    private LinearLayoutCompat clickMore;
    private ImageView ivStatus;
    private LinearLayoutCompat llBottom;
    private TextView tvDelete;
    private TextView tvChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden_danger_detail_management);
        netClient = new NetClient(HiddenDangerDetailManagementActivity.this);
        initView();
        setView();
        initdata();
    }

    private void setView() {
        txtTitle.setText(R.string.hidden_danger_details_management);
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyAlertDialog alertDialog = new MyAlertDialog(HiddenDangerDetailManagementActivity.this
                        , new MyAlertDialog.DialogListener() {
                    @Override
                    public void affirm() {
                        Intent intent = getIntent();
                        String id = intent.getStringExtra("id");
                        Utils.showLongToast(HiddenDangerDetailManagementActivity.this, "删除成功:"+id);
                        deleteHiddenRecord(id);
                        //Intent intents = new Intent(HiddenDangerDetailManagementActivity.this, HiddenRiskRecordAddEditActivity.class);
                        //intents.putExtra("id",id);
                        //startActivity(intents);
                    }

                    @Override
                    public void cancel() {

                    }
                }, "你确定要删除选中的数据么？");
                alertDialog.show();
            }
        });

        tvChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String id = intent.getStringExtra("id");
                Intent intents = new Intent(HiddenDangerDetailManagementActivity.this, HiddenRiskRecordAddEditActivity.class);
                intents.putExtra("id",id);
                startActivity(intents);
            }
        });

    }

    private void initView() {
        txtLeft = findViewById(R.id.txt_left);
        imgLeft = findViewById(R.id.img_left);
        txtTitle = findViewById(R.id.txt_title);
        imgRight = findViewById(R.id.img_right);
        txtRight = findViewById(R.id.txt_right);
        tvHiddenUnits = findViewById(R.id.tv_hidden_units);
        tvTimeOrOrder = findViewById(R.id.tv_time_or_order);
        tvHiddenContent = findViewById(R.id.tv_hidden_content);
        tvHiddenDangerBelongs = findViewById(R.id.tv_hidden_danger_belongs);
        expand = findViewById(R.id.expand);
        tvProfessional = findViewById(R.id.tv_professional);
        tvArea = findViewById(R.id.tv_area);
        tvClasses = findViewById(R.id.tv_classes);
        tvOversee = findViewById(R.id.tv_oversee);
        ivStatusSecond = findViewById(R.id.iv_status_second);
        clickMore = findViewById(R.id.click_more);
        ivStatus = findViewById(R.id.iv_status);
        llBottom = findViewById(R.id.ll_bottom);
        tvDelete = findViewById(R.id.tv_delete);
        tvChange = findViewById(R.id.tv_change);
    }

    private void initdata(){
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        getHiddenRecord(id);

    }

    private void getHiddenRecord(String id) {//隐患id
        RequestParams params = new RequestParams();
        params.put("hiddenDangerRecordId",id);
        netClient.post(Constants.HIDDENDANGERRECORD, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "隐患数据返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    JSONObject returndata  = JSON.parseObject(data);
                    HiddenDangerRecord record = JSONArray.parseObject(data, HiddenDangerRecord.class);
                    tvHiddenUnits.setText(record.getTeamGroupName());
                    tvTimeOrOrder.setText(record.getFindTime()+"/"+record.getClassName());
                    tvHiddenContent.setText(record.getContent());
                    tvHiddenDangerBelongs.setText(record.getHiddenBelong());
                    tvProfessional.setText(record.getSname());
                    tvArea.setText(record.getAreaName());
                    tvClasses.setText(record.getGname());
                    String isuper = record.getIsupervision();
                    if(TextUtils.isEmpty(isuper)||TextUtils.equals(isuper,"0")){
                        isuper = "未督办";
                    }else{
                        isuper = "已督办";
                    }
                    tvOversee.setText(isuper);
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "隐患数据返回错误信息：" + content);
                Utils.showLongToast(HiddenDangerDetailManagementActivity.this, content);
                return;
            }
        });
    }

    //删除隐患
    private void deleteHiddenRecord(String id) {//隐患id
        RequestParams params = new RequestParams();
        params.put("hiddenDangerRecordId",id);
        netClient.post(Constants.DELETE_HIDDEN, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "删除隐患返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    Utils.showLongToast(HiddenDangerDetailManagementActivity.this, "删除成功");
                }
                finish();
            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "删除隐患返回错误信息：" + content);
                Utils.showLongToast(HiddenDangerDetailManagementActivity.this, content);
                return;
            }
        });
    }
}
