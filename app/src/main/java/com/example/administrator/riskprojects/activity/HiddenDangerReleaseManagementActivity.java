package com.example.administrator.riskprojects.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.riskprojects.BaseActivity;
import com.example.administrator.riskprojects.R;

public class HiddenDangerReleaseManagementActivity extends BaseActivity {

    private TextView mTxtLeft;
    private ImageView mImgLeft;
    private TextView mTxtTitle;
    private ImageView mImgRight;
    private TextView mTxtRight;
    private TextView mTvHiddenContent;
    private TextView mTvArea;
    private TextView mTvSpecialty;
    private TextView mTvTimeOrOrder;
    private TextView mTvCategory;
    private TextView mTvSupervise;
    private TextView mTvFinishTime;
    private TextView mTvPrincipal;
    private TextView mTvMeasure;
    private TextView mTvCapital;
    private TextView mTvTheNumberOfProcessing;
    private TextView mTvToCarryOutThePeople;
    private TextView mTvDepartment;
    private TextView mTvHeadquarters;
    private LinearLayoutCompat mLlBottom;
    private TextView mTvOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden_danger_release_management);
        initView();
        setView();
    }

    private void initView() {
        mTxtLeft = findViewById(R.id.txt_left);
        mImgLeft = findViewById(R.id.img_left);
        mTxtTitle = findViewById(R.id.txt_title);
        mImgRight = findViewById(R.id.img_right);
        mTxtRight = findViewById(R.id.txt_right);
        mTvHiddenContent = findViewById(R.id.tv_hidden_content);
        mTvArea = findViewById(R.id.tv_area);
        mTvSpecialty = findViewById(R.id.tv_specialty);
        mTvTimeOrOrder = findViewById(R.id.tv_time_or_order);
        mTvCategory = findViewById(R.id.tv_category);
        mTvSupervise = findViewById(R.id.tv_supervise);
        mTvFinishTime = findViewById(R.id.tv_finish_time);
        mTvPrincipal = findViewById(R.id.tv_principal);
        mTvMeasure = findViewById(R.id.tv_measure);
        mTvCapital = findViewById(R.id.tv_capital);
        mTvTheNumberOfProcessing = findViewById(R.id.tv_the_number_of_processing);
        mTvToCarryOutThePeople = findViewById(R.id.tv_to_carry_out_the_people);
        mTvDepartment = findViewById(R.id.tv_department);
        mTvHeadquarters = findViewById(R.id.tv_headquarters);
        mLlBottom = findViewById(R.id.ll_bottom);
        mTvOk = findViewById(R.id.tv_ok);
    }

    private void setView() {
        mTxtTitle.setText(R.string.hidden_danger_management);
    }
}
