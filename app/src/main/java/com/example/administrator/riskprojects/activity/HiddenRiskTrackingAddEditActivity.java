package com.example.administrator.riskprojects.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.administrator.riskprojects.BaseActivity;
import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.bean.HiddenFollingRecord;
import com.example.administrator.riskprojects.bean.ThreeFix;
import com.example.administrator.riskprojects.net.BaseJsonRes;
import com.example.administrator.riskprojects.net.NetClient;
import com.example.administrator.riskprojects.tools.Constants;
import com.example.administrator.riskprojects.tools.UserUtils;
import com.example.administrator.riskprojects.tools.Utils;
import com.juns.health.net.loopj.android.http.RequestParams;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HiddenRiskTrackingAddEditActivity extends BaseActivity {
    private static final String TAG = "HiddenRiskTrackingAddEd";
    NetClient netClient;
    public static final String CHANGE_ID = "id";
    public static final String CHANGE_DATE = "date";
    public static final String CHANGE_CONTENT = "content";
    private TextView mTxtLeft;
    private ImageView mImgLeft;
    private TextView mTxtTitle;
    private ImageView mImgRight;
    private TextView mTxtRight;
    private CardView mCvSelectDate;
    private LinearLayoutCompat mLlBottom;
    private TextView mTvOk;
    private TextView mTvDate;
    private EditText mEtContent;
    private HiddenFollingRecord hiddenFollingRecord;
    private String flag = Constants.ADD_FOLLINGRECORD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden_risk_tracking_add_edit);
        netClient = new NetClient(HiddenRiskTrackingAddEditActivity.this);
        initView();
        setView();
    }

    private void setView() {
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        if(!TextUtils.isEmpty(id)){
            String follingRecord = intent.getStringExtra("follingRecord");
            hiddenFollingRecord = JSON.parseObject(follingRecord,HiddenFollingRecord.class);
            flag = Constants.UPDATE_FOLLINGRECORD;
        }else{
            String threeFixId = intent.getStringExtra("threeFixId");
            String follingPersonName = UserUtils.getUserName(HiddenRiskTrackingAddEditActivity.this);
            String follingPersonId = UserUtils.getUserID(HiddenRiskTrackingAddEditActivity.this);
            hiddenFollingRecord = new HiddenFollingRecord(threeFixId,follingPersonId,follingPersonName,"","");
        }

        mTvDate.setText(TextUtils.isEmpty(hiddenFollingRecord.getFollingRecordTime()) ? new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(new Date()) : hiddenFollingRecord.getFollingRecordTime());
        mEtContent.setText(TextUtils.isEmpty(hiddenFollingRecord.getFollingRecord()) ? "" : hiddenFollingRecord.getFollingRecord());
        mTxtTitle.setText(TextUtils.isEmpty(hiddenFollingRecord.getFollingRecordId()) ? R.string.add : R.string.modification);
        mCvSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerActivity.startPickDate(HiddenRiskTrackingAddEditActivity.this, HiddenRiskTrackingAddEditActivity.this);
            }
        });
        mTvOk.setClickable(true);
        mTvOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = getIntent();
                String follingRecordTime = mTvDate.getText().toString();
                String follingRecord = mEtContent.getText().toString();
                hiddenFollingRecord.setFollingRecord(follingRecord);
                hiddenFollingRecord.setFollingRecordTime(follingRecordTime);
                addEditTracking(hiddenFollingRecord,flag);
            }

        });
    }

    //添加跟踪记录
    private void addEditTracking(final HiddenFollingRecord hiddenFollingRecord,String flag) {
        RequestParams params = new RequestParams();
        String hiddenFollingRecordStr = JSON.toJSONString(hiddenFollingRecord);
        params.put("follingRecordJsonData",hiddenFollingRecordStr);
        netClient.post(Data.getInstance().getIp()+flag, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "添加跟踪记录返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    Intent intent = new Intent(HiddenRiskTrackingAddEditActivity.this, HiddenDangeTrackingDetailListActivity.class);
                    intent.putExtra("threeFixId", hiddenFollingRecord.getThreeFixId());
                    startActivity(intent);
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "添加跟踪记录返回错误信息：" + content);
                Utils.showLongToast(HiddenRiskTrackingAddEditActivity.this, content);
                return;
            }
        });
    }

    private void initView() {
        mTxtLeft = findViewById(R.id.txt_left);
        mImgLeft = findViewById(R.id.img_left);
        mTxtTitle = findViewById(R.id.txt_title);
        mImgRight = findViewById(R.id.img_right);
        mTxtRight = findViewById(R.id.txt_right);
        mCvSelectDate = findViewById(R.id.cv_select_date);
        mLlBottom = findViewById(R.id.ll_bottom);
        mTvOk = findViewById(R.id.tv_ok);
        mTvDate = findViewById(R.id.tv_date);
        mEtContent = findViewById(R.id.et_content);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DatePickerActivity.REQUEST && resultCode == RESULT_OK) {
            mTvDate.setText(data.getStringExtra(DatePickerActivity.DATE));
        }
    }
}
