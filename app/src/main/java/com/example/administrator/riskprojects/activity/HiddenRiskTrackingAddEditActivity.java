package com.example.administrator.riskprojects.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.riskprojects.BaseActivity;
import com.example.administrator.riskprojects.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HiddenRiskTrackingAddEditActivity extends BaseActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden_risk_tracking_add_edit);
        initView();
        setView();
    }

    private void setView() {
        mTvDate.setText(TextUtils.isEmpty(getIntent().getStringExtra(CHANGE_DATE)) ? new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(new Date()) : getIntent().getStringExtra(CHANGE_DATE));
        mEtContent.setText(TextUtils.isEmpty(getIntent().getStringExtra(CHANGE_CONTENT)) ? "" : getIntent().getStringExtra(CHANGE_CONTENT));
        mTxtTitle.setText(TextUtils.isEmpty(getIntent().getStringExtra(CHANGE_ID)) ? R.string.add : R.string.modification);
        mCvSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerActivity.startPickDate(HiddenRiskTrackingAddEditActivity.this, HiddenRiskTrackingAddEditActivity.this);
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
