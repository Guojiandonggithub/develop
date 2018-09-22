package com.example.administrator.riskprojects.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.riskprojects.BaseActivity;
import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.bean.ThreeFix;

/**
 * 隐患验收
 */
public class HiddenDangerReviewManagementActivity extends BaseActivity {

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
    private TextView mTvHeadquarters;
    private TextView mTvPrincipal;
    private TextView mTvMeasure;
    private TextView mTvCapital;
    private TextView mTvToCarryOutThePeople;
    private TextView mTvAcceptanceOfThePeople;
    private TextView mTvAcceptanceOfTheResults;
    private LinearLayoutCompat mLlBottom;
    private TextView mTvOk;
    private ThreeFix threeFix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden_danger_review_management);
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
        mTvHeadquarters = findViewById(R.id.tv_headquarters);
        mTvPrincipal = findViewById(R.id.tv_principal);
        mTvMeasure = findViewById(R.id.tv_measure);
        mTvCapital = findViewById(R.id.tv_capital);
        mTvToCarryOutThePeople = findViewById(R.id.tv_to_carry_out_the_people);
        mTvAcceptanceOfThePeople = findViewById(R.id.tv_acceptance_of_the_people);
        mTvAcceptanceOfTheResults = findViewById(R.id.tv_acceptance_of_the_results);
        mLlBottom = findViewById(R.id.ll_bottom);
        mTvOk = findViewById(R.id.tv_ok);
    }

    private void setView() {
        mTxtTitle.setText(R.string.hidden_danger_review_management);
        mTvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HiddenDangerAcceptanceActivity.class);
                intent.putExtra("threeFixId",threeFix.getId());
                intent.putExtra("recheckresult",threeFix.getRecheckResult());
                intent.putExtra("description",threeFix.getDescription());
                intent.putExtra("recheckPersonId",threeFix.getRecheckPersonId());
                intent.putExtra("recheckPersonName",threeFix.getRecheckPersonName());
                startActivity(intent);
            }
        });

        Bundle  bundle = getIntent().getBundleExtra("threeBund");
        threeFix = (ThreeFix) bundle.getSerializable("threeFix");
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
        mTvToCarryOutThePeople.setText(threeFix.getPracticablePerson());
        mTvDepartment.setText(threeFix.getFollingTeamName());
        mTvHeadquarters.setText(threeFix.getFollingPersonName());
        mTvAcceptanceOfThePeople.setText(threeFix.getRecheckPersonName());
        String result = threeFix.getRecheckResult();
        if(TextUtils.isEmpty(result)||TextUtils.equals(result,"1")){
            result = "未通过";
        }else{
            result = "已通过";
        }
        mTvAcceptanceOfTheResults.setText(result);
    }
}
