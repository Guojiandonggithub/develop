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
import com.example.administrator.riskprojects.tools.UserUtils;
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
    private HiddenDangerRecord record;
    private String hiddenrecordjson;
    private TextView tvHiddenBelong;
    private TextView tvIsHang;
    private TextView tvDiscoveryTime;
    private TextView tvCheckTheContent;
    private TextView tvStatus;
    private TextView tvIsHandle;
    private TextView tvHiddenDangerLogger;
    private TextView tvFinishTime;
    private TextView tvPrincipal;
    private TextView tvMeasure;
    private TextView tvCapital;
    private TextView tvTheNumberOfProcessing;
    private TextView tvToCarryOutThePeople;
    private TextView tvDepartment;
    private TextView tvHeadquarters;
    private TextView tvTrackingUnit;
    private TextView tvTrackPeople;
    private TextView tvAcceptanceOfThePeople;
    private TextView tvAcceptanceOfTheResults;

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
        txtTitle.setText(R.string.hidden_danger_record_management);
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyAlertDialog alertDialog = new MyAlertDialog(HiddenDangerDetailManagementActivity.this
                        , new MyAlertDialog.DialogListener() {
                    @Override
                    public void affirm() {
                        Intent intent = getIntent();
                        String id = intent.getStringExtra("id");
                        if (checkparam(intent)) {
                            deleteHiddenRecord(id);
                        }
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
                Intent intents = new Intent(HiddenDangerDetailManagementActivity.this, HiddenRiskRecordAddEditActivity.class);
                Log.e(TAG, "onClick: hiddenrecordjson=============" + hiddenrecordjson);
                intents.putExtra("hiddenrecordjson", hiddenrecordjson);
                intents.putExtra("id", record.getId());
                startActivity(intents);
            }
        });

    }

    private boolean checkparam(Intent intent) {
        String flag = intent.getStringExtra("flag");
        Log.e(TAG, "flag================: " + flag);
        String employeeId = intent.getStringExtra("employeeId");
        String role = UserUtils.getUserRoleids(HiddenDangerDetailManagementActivity.this);
        String userid = UserUtils.getUserID(HiddenDangerDetailManagementActivity.this);
        if (!"1".equals(role) && !userid.equals(employeeId)) {
            Utils.showLongToast(HiddenDangerDetailManagementActivity.this, "您不是管理员或该隐患不是您上报的,不能进行删除!");
            return false;
        }
        if (null != flag) {
            if (Integer.parseInt(flag) >= 2) {
                Utils.showLongToast(HiddenDangerDetailManagementActivity.this, "该隐患已经下达不能修改!");
            }
        }
        return true;
    }

    private void initView() {
        txtLeft = findViewById(R.id.txt_left);
        imgLeft = findViewById(R.id.img_left);
        txtTitle = findViewById(R.id.txt_title);
        imgRight = findViewById(R.id.img_right);
        txtRight = findViewById(R.id.txt_right);
        //
        tvHiddenUnits = findViewById(R.id.tv_hidden_units);
        tvTimeOrOrder = findViewById(R.id.tv_time_or_order);
        tvHiddenContent = findViewById(R.id.tv_hidden_content);
        tvHiddenDangerBelongs = findViewById(R.id.tv_hidden_danger_belongs);
        expand = findViewById(R.id.expand);
        tvProfessional = findViewById(R.id.tv_professional);
        tvArea = findViewById(R.id.tv_area);
        tvClasses = findViewById(R.id.tv_classes);
        tvOversee = findViewById(R.id.tv_oversee);
//        ivStatusSecond = findViewById(R.id.iv_status_second);
        clickMore = findViewById(R.id.click_more);
        ivStatus = findViewById(R.id.iv_status);
        llBottom = findViewById(R.id.ll_bottom);
        tvDelete = findViewById(R.id.tv_delete);
        tvChange = findViewById(R.id.tv_change);
        tvHiddenBelong = findViewById(R.id.tv_hidden_belong);
        tvIsHang = findViewById(R.id.tv_is_hang);
        tvDiscoveryTime = findViewById(R.id.tv_discovery_time);
        tvCheckTheContent = findViewById(R.id.tv_check_the_content);
        tvStatus = findViewById(R.id.tv_status);
        tvIsHandle = findViewById(R.id.tv_is_handle);
        tvHiddenDangerLogger = findViewById(R.id.tv_hidden_danger_logger);
        tvFinishTime = findViewById(R.id.tv_finish_time);
        tvPrincipal = findViewById(R.id.tv_principal);
        tvMeasure = findViewById(R.id.tv_measure);
        tvCapital = findViewById(R.id.tv_capital);
        tvTheNumberOfProcessing = findViewById(R.id.tv_the_number_of_processing);
        tvToCarryOutThePeople = findViewById(R.id.tv_to_carry_out_the_people);
        tvDepartment = findViewById(R.id.tv_department);
        tvHeadquarters = findViewById(R.id.tv_headquarters);
        tvTrackingUnit = findViewById(R.id.tv_tracking_unit);
        tvTrackPeople = findViewById(R.id.tv_track_people);
        tvAcceptanceOfThePeople = findViewById(R.id.tv_acceptance_of_the_people);
        tvAcceptanceOfTheResults = findViewById(R.id.tv_acceptance_of_the_results);
    }

    private void initdata() {
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        getHiddenRecord(id);

    }

    private void getHiddenRecord(String id) {//隐患id
        RequestParams params = new RequestParams();
        params.put("hiddenDangerRecordId", id);
        netClient.post(Data.getInstance().getIp() + Constants.HIDDENDANGERRECORD, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "隐患数据返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    JSONObject returndata = JSON.parseObject(data);
                    hiddenrecordjson = data;
                    record = JSONArray.parseObject(data, HiddenDangerRecord.class);
                    tvHiddenUnits.setText(record.getTeamGroupName());
                    tvTimeOrOrder.setText(record.getFindTime() + "/" + record.getClassName());
                    tvHiddenContent.setText(record.getContent());
                    tvHiddenDangerBelongs.setText(record.getHiddenBelong());
                    tvProfessional.setText(record.getSname());
                    tvArea.setText(record.getAreaName());
                    tvClasses.setText(record.getJbName());
                    ivStatus.setImageResource(getImageResourceByFlag(record.getFlag(), record.getOutTimeFlag()));
//                    ivStatusSecond.setImageResource(getImageResourceByFlag(record.getFlag(),record.getOutTimeFlag()));

                    String isuper = record.getIsupervision();
                    if (TextUtils.isEmpty(isuper) || TextUtils.equals(isuper, "0")) {
                        isuper = "未督办";
                    } else {
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
        params.put("hiddenDangerRecordId", id);
        netClient.post(Data.getInstance().getIp() + Constants.DELETE_HIDDEN, params, new BaseJsonRes() {

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

    private int getImageResourceByFlag(String flag, String outTimeFlag) {
        if ("1".equals(outTimeFlag)) {
            return R.mipmap.ic_status_overdue;
        }
        switch (flag) {
            case "0":
                return R.mipmap.ic_status_shaixuan;
            case "1":
                return R.mipmap.ic_status_release;
            case "2":
                return R.mipmap.ic_status_rectificationg;
            case "3":
                return R.mipmap.ic_recheck;
            case "4":
                return R.mipmap.ic_status_dispelling;
            case "5":
                return R.mipmap.ic_status_release;
            default:
                return R.mipmap.ic_status_overdue;
        }
    }

}
