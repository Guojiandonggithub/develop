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
import com.example.administrator.riskprojects.bean.ThreeFix;
import com.example.administrator.riskprojects.net.BaseJsonRes;
import com.example.administrator.riskprojects.net.NetClient;
import com.example.administrator.riskprojects.tools.Constants;
import com.example.administrator.riskprojects.tools.Utils;
import com.juns.health.net.loopj.android.http.RequestParams;

import java.io.Serializable;

/**
 * 隐患下达
 */
public class HiddenDangerReleaseManagementActivity extends BaseActivity {
    private static final String TAG = "HiddenDangerReleaseMana";
    protected NetClient netClient;
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
        netClient = new NetClient(HiddenDangerReleaseManagementActivity.this);
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
        mTvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HiddenDangerReleaseManagementActivity.this,FiveDecisionsActivity.class)) ;
            }
        });
    }

    private void setView() {
        mTxtTitle.setText(R.string.hidden_danger_management);
        Bundle  bundle = getIntent().getBundleExtra("threeBund");
        ThreeFix threeFix = (ThreeFix) bundle.getSerializable("threeFix");
        mTvHiddenContent.setText(threeFix.getContent());
        mTvArea.setText(threeFix.getAreaName());
        mTvSpecialty.setText(threeFix.getSname());
        mTvTimeOrOrder.setText(threeFix.getFindTime()+"/"+threeFix.getClassName());
        mTvCategory.setText(threeFix.getGname());
        String isuper = threeFix.getIsupervision();
        if(TextUtils.isEmpty(isuper)||TextUtils.equals(isuper,"0")){
            isuper = "未督办";
        }else{
            isuper = "已督办";
        }
        mTvSupervise.setText(isuper);
        mTvFinishTime.setText(threeFix.getCompleteTime());
        mTvDepartment.setText(threeFix.getLsdeptName()+"/"+threeFix.getLsteamName());
        mTvMeasure.setText(threeFix.getMeasure());
        mTvCapital.setText(threeFix.getMoney());
        mTvPrincipal.setText(threeFix.getRealName());
        mTvTheNumberOfProcessing.setText(threeFix.getPersonNum());
        mTvToCarryOutThePeople.setText(threeFix.getPracticablePerson());
        mTvDepartment.setText(threeFix.getFollingTeamName());
        mTvHeadquarters.setText(threeFix.getFollingPersonName());
    }

}
