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

public class HiddenDangeTrackingManagementActivity extends BaseActivity {
    private static final String TAG = "HiddenDangeTrackingMana";
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
    private TextView mTvDepartment;
    private TextView mTvMeasure;
    private TextView mTvCapital;
    private TextView mTvPrincipal;
    private TextView mTvHandler;
    private TextView mTvTrackPeople;
    private LinearLayoutCompat mLlBottom;
    private TextView mTvAdd;
    private TextView mTvDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden_dange_tracking_management);
        netClient = new NetClient(HiddenDangeTrackingManagementActivity.this);
        initView();
        setView();
        initdata();
    }

    private void setView() {
        mTxtTitle.setText(R.string.hidden_danger_tracking_management);
        mTvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HiddenDangeTrackingManagementActivity.this, HiddenRiskTrackingAddEditActivity.class));
            }
        });

        mTvDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HiddenDangeTrackingManagementActivity.this, HiddenDangeTrackingDetailListActivity.class));

            }
        });
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
        mTvDepartment = findViewById(R.id.tv_department);
        mTvMeasure = findViewById(R.id.tv_measure);
        mTvCapital = findViewById(R.id.tv_capital);
        mTvPrincipal = findViewById(R.id.tv_principal);
        mTvHandler = findViewById(R.id.tv_handler);
        mTvTrackPeople = findViewById(R.id.tv_track_people);
        mLlBottom = findViewById(R.id.ll_bottom);
        mTvAdd = findViewById(R.id.tv_add);
        mTvDetail = findViewById(R.id.tv_detail);
    }

    private void initdata(){
        //getThreeFix();
    }

    //查询跟踪实体
    private void getThreeFix() {
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String hiddenDangerId = intent.getStringExtra("hiddenDangerId");
        RequestParams params = new RequestParams();
        params.put("id",id);
        params.put("hiddenDangerId",hiddenDangerId);
        netClient.post(Constants.GET_THREEFIX, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "查询跟踪实体数据返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    JSONObject returndata  = JSON.parseObject(data);
                    String hiddenDanger = returndata.getString("hiddenDanger");
                    JSONObject threeFixData = JSON.parseObject(hiddenDanger);
                    String threeFixDataStr = threeFixData.getString("obj");
                    ThreeFix threeFix = JSONArray.parseObject(threeFixDataStr, ThreeFix.class);
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
                    mTvHandler.setText(threeFix.getPracticablePerson());
                    mTvTrackPeople.setText(threeFix.getFollingPersonName());
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "查询跟踪实体数据返回错误信息：" + content);
                Utils.showLongToast(HiddenDangeTrackingManagementActivity.this, content);
                return;
            }
        });
    }

}
