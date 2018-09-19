package com.example.administrator.riskprojects.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.riskprojects.BaseActivity;
import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.view.MyAlertDialog;
import com.example.administrator.riskprojects.bean.ThreeFix;

/**
 * 隐患整改
 */
public class HiddenDangerRectificationManagementActivity extends BaseActivity {

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
    private TextView mTvTheRectificationResults;
    private TextView mTvToCarryOutThePeople;
    private LinearLayoutCompat mLlBottom;
    private TextView mTvOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden_danger_rectification_management);
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
        mTvDepartment = findViewById(R.id.tv_department);
        mTvMeasure = findViewById(R.id.tv_measure);
        mTvCapital = findViewById(R.id.tv_capital);
        mTvPrincipal = findViewById(R.id.tv_principal);
        mTvTheRectificationResults = findViewById(R.id.tv_the_rectification_results);
        mTvToCarryOutThePeople = findViewById(R.id.tv_to_carry_out_the_people);
        mLlBottom = findViewById(R.id.ll_bottom);
        mTvOk = findViewById(R.id.tv_ok);
        mTvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyAlertDialog myAlertDialog = new MyAlertDialog(HiddenDangerRectificationManagementActivity.this,
                        new MyAlertDialog.DialogListener() {
                            @Override
                            public void affirm() {
                                //确定入口
                            }

                            @Override
                            public void cancel() {

                            }
                        },"你确定要整改此么？" );
                myAlertDialog.show();
            }
        });
    }

    private void setView() {
        mTxtTitle.setText(R.string.hidden_danger_rectification_management);
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
        mTvDepartment.setText(threeFix.getLuoshidanwei());
        mTvMeasure.setText(threeFix.getMeasure());
        mTvCapital.setText(threeFix.getMoney());
        mTvPrincipal.setText(threeFix.getRealName());
        mTvTheRectificationResults.setText(threeFix.getRectifyResult());
        mTvToCarryOutThePeople.setText(threeFix.getPracticablePerson());
    }
}
