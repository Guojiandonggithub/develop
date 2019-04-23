package com.example.administrator.riskprojects.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.riskprojects.BaseActivity;
import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.activity.datepicker.CustomDatePicker;
import com.example.administrator.riskprojects.activity.datepicker.DateFormatUtils;
import com.example.administrator.riskprojects.bean.IdentificationEvaluation;
import com.example.administrator.riskprojects.net.BaseJsonRes;
import com.example.administrator.riskprojects.net.NetClient;
import com.example.administrator.riskprojects.tools.Constants;
import com.example.administrator.riskprojects.tools.UserUtils;
import com.example.administrator.riskprojects.tools.Utils;
import com.example.administrator.riskprojects.view.MyAlertDialog;
import com.juns.health.net.loopj.android.http.RequestParams;

/**
 * 辨识评估
 */
public class IndentificationEvaluationActivity extends BaseActivity {
    private static final String TAG = "IndentificationEvaluationActivity";
    protected NetClient netClient;
    private TextView txtLeft;
    private ImageView imgLeft;
    private TextView txtTitle;
    private ImageView imgRight;
    private TextView txtRight;
    private TextView tvKuangquName;
    private TextView tvRiskPlace;
    private TextView tvRiskContent;
    private TextView tvDsName;
    private TextView tvPossibility;
    private TextView tvExposureRate;
    private TextView tvResult;
    private TextView tvRiskScore;
    private TextView ivRiskGName;
    private TextView tvMeasures;
    private TextView tvDutyDeptName;
    private TextView tvDutyPersonName;
    private TextView tvControlTeamName;
    private TextView tvControlTime;
    private TextView tvPersonsControl;
    private TextView tvStartTime;
    private TextView tvEndTime;
    private TextView tvOpenType;
    private TextView tvRecordTime;
    private TextView tvOpen;
    private TextView tvClose;
    private TextView tvLuoshi;
    private TextView tvLuoshiRecord;
    private IdentificationEvaluation record;
    private CustomDatePicker mTimerPicker;
    private String time;
    private String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idenfitication_evaluation);
        netClient = new NetClient(IndentificationEvaluationActivity.this);
        initView();
        setView();
        initdate();
    }

    private void setView() {
        txtTitle.setText("评估详情");
        final Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("recordBund");
        record = (IdentificationEvaluation) bundle.getSerializable("hiddenDangerRecord");
    }

    private void initView() {
        txtLeft = findViewById(R.id.txt_left);
        imgLeft = findViewById(R.id.img_left);
        txtTitle = findViewById(R.id.txt_title);
        imgRight = findViewById(R.id.img_right);
        txtRight = findViewById(R.id.txt_right);
        tvKuangquName = findViewById(R.id.tv_kuangqu_name);
        tvRiskPlace = findViewById(R.id.tv_risk_place);
        tvRiskContent = findViewById(R.id.tv_risk_content);
        tvDsName = findViewById(R.id.tv_ds_name);
        tvPossibility = findViewById(R.id.tv_possibility);
        tvExposureRate = findViewById(R.id.tv_exposure_rate);
        tvResult = findViewById(R.id.tv_result);
        tvRiskScore = findViewById(R.id.tv_riskScore);
        ivRiskGName = findViewById(R.id.tv_risk_gname);
        tvMeasures = findViewById(R.id.tv_measures);
        tvDutyDeptName = findViewById(R.id.tv_duty_dept_name);
        tvDutyPersonName = findViewById(R.id.tv_duty_person_name);
        tvControlTeamName = findViewById(R.id.tv_control_team_name);
        tvControlTime = findViewById(R.id.tv_control_time);
        tvPersonsControl = findViewById(R.id.tv_persons_control);
        tvStartTime = findViewById(R.id.tv_start_time);
        tvEndTime = findViewById(R.id.tv_end_time);
        tvOpenType = findViewById(R.id.tv_open_type);
        tvRecordTime = findViewById(R.id.tv_record_time);
        tvOpen = findViewById(R.id.tv_open);
        tvClose = findViewById(R.id.tv_close);
        tvLuoshi = findViewById(R.id.tv_luoshi);
        tvLuoshiRecord = findViewById(R.id.tv_luoshi_record);
        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = "close";
                mTimerPicker.show(time);
            }
        });

        tvOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = "open";
                mTimerPicker.show(time);
            }
        });

        tvLuoshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IndentificationEvaluationActivity.this, ManagementControlActivity.class);
                intent.putExtra("kuangQuId",record.getKuangQuId());
                intent.putExtra("safeCheckId",record.getId());
                startActivity(intent);
            }
        });

        tvLuoshiRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IndentificationEvaluationActivity.this, IndentificationEvaluationRecordActivity.class);
                intent.putExtra("safeId",record.getId());
                startActivity(intent);
            }
        });
        initTimerPicker();
    }

    private void initdate(){
        tvKuangquName.setText(record.getKuangQuName());
        tvRiskPlace.setText(record.getRiskPlace());
        tvRiskContent.setText(record.getRiskContent());
        tvDsName.setText(record.getDsName());
        tvPossibility.setText(record.getPossibility());
        tvExposureRate.setText(record.getExposureRate());
        tvResult.setText(record.getResult());
        tvRiskScore.setText(record.getRiskScore());
        tvMeasures.setText(record.getMeasures());
        ivRiskGName.setText(record.getRiskGname());
        tvDutyDeptName.setText(record.getDutyDeptName());
        tvDutyPersonName.setText(record.getDutyPersonName());
        tvControlTeamName.setText(record.getControlTeamName());
        String year = record.getControlYear();
        if(record.getControlYear()==null){
            year = "";
        }
        String Months = record.getControlMonths();
        if(record.getControlMonths()==null){
            Months = "";
        }
        tvControlTime.setText(year+Months);
        tvPersonsControl.setText(record.getPersonsControl());
        tvStartTime.setText(record.getStarTime());
        tvEndTime.setText(record.getEndTime());
        tvRecordTime.setText(record.getRecordTime());
        String openType = record.getOpenType();
        String username = UserUtils.getUserName(IndentificationEvaluationActivity.this);
        String userRolea = UserUtils.getUserRoleids(IndentificationEvaluationActivity.this);
        if(!TextUtils.isEmpty(openType)){
            if("1".equals(openType)){
                openType = "开启";
                if(record.getDutyPersonName()!=null){
                    if(record.getDutyPersonName().equals(username)||userRolea.equals("1")){
                        tvClose.setVisibility(View.VISIBLE);
                    }
                }
            }else{
                openType = "关闭";
                if(record.getDutyPersonName()!=null){
                    if(record.getDutyPersonName().equals(username)||userRolea.equals("1")){
                        tvOpen.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
        tvOpenType.setText(openType);
        final Intent intent = getIntent();
        String datatype = intent.getStringExtra("datatype");
        if(datatype.equals("mLlRiskStatisticsNum")){
            txtTitle.setText("风险统计");
            tvClose.setVisibility(View.GONE);
            tvOpen.setVisibility(View.GONE);
            tvLuoshi.setVisibility(View.GONE);
            tvLuoshiRecord.setVisibility(View.GONE);
        }
    }

    //关闭辨识评估
    private void closeEvaluation(String openType) {
        RequestParams params = new RequestParams();
        params.put("ids",record.getId());
        params.put("openType",openType);
        if(status.equals("open")){
            params.put("starTime",time+":11");
        }else{
            params.put("endTime",time+":11");
        }
        netClient.post(Data.getInstance().getIp() + Constants.CLOSE_RISK_RVALUATION, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                if (!TextUtils.isEmpty(data)) {
                    Intent intent=new Intent(IndentificationEvaluationActivity.this,HomePageTotalDetailActivity.class);
                    setResult(11, intent);
                    Utils.showLongToast(IndentificationEvaluationActivity.this, "状态变更成功");
                    finish();
                }
            }

            @Override
            public void onMyFailure(String content) {
                Utils.showLongToast(IndentificationEvaluationActivity.this, content);
            }
        });
    }

    private void initTimerPicker() {
        String beginTime = "2016-01-01 00:00";
        String endTime = DateFormatUtils.long2Str(System.currentTimeMillis(), true);

        time = endTime;
        //mTvSelectedTime.setText(endTime);

        // 通过日期字符串初始化日期，格式请用：yyyy-MM-dd HH:mm
        mTimerPicker = new CustomDatePicker(this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                MyAlertDialog alertDialog = new MyAlertDialog(IndentificationEvaluationActivity.this, new MyAlertDialog.DialogListener() {
                    @Override
                    public void affirm() {
                        if(status.equals("open")){
                            closeEvaluation("1");
                        }else{
                            closeEvaluation("0");
                        }

                    }

                    @Override
                    public void cancel() {

                    }
                }, DateFormatUtils.long2Str(timestamp, true));
                alertDialog.show();
            }
        }, beginTime, endTime);
        // 允许点击屏幕或物理返回键关闭
        mTimerPicker.setCancelable(true);
        // 显示时和分
        mTimerPicker.setCanShowPreciseTime(true);
        // 允许循环滚动
        mTimerPicker.setScrollLoop(true);
        // 允许滚动动画
        mTimerPicker.setCanShowAnim(true);
    }
}
