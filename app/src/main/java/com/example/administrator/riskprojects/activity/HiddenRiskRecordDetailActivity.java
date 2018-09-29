package com.example.administrator.riskprojects.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.riskprojects.BaseActivity;
import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.bean.HiddenDangerRecord;
import com.example.administrator.riskprojects.bean.ThreeFix;

public class HiddenRiskRecordDetailActivity extends BaseActivity {

    private TextView txtLeft;
    private ImageView imgLeft;
    private TextView txtTitle;
    private ImageView imgRight;
    private TextView txtRight;
    private TextView tvHiddenUnits;
    private TextView tvProfessional;
    //private TextView tvHiddenClass;
    private TextView tvEnterMan;
    private TextView tvLevel;
    private TextView tvDate;
    private TextView tvOrder;
    private TextView tvHiddenSort;
    private TextView tvHiddenArea;
    private TextView tvDiscoverer;
    private TextView tvIsHandle;
    private TextView etContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden_risk_record_detail);
        initView();
        setVeiw();
    }

    private void setVeiw() {
        Bundle  bundle = getIntent().getBundleExtra("hiddenrecordBundle");
        HiddenDangerRecord hiddenDangerRecord = (HiddenDangerRecord) bundle.getSerializable("hiddenDangerRecord");
        txtTitle.setText("隐患详情");
        tvHiddenUnits.setText(hiddenDangerRecord.getTeamGroupName());
        tvProfessional.setText(hiddenDangerRecord.getSname());
        //tvHiddenClass.setText(hiddenDangerRecord.getGname());
        tvEnterMan.setText(hiddenDangerRecord.getRealName());
        tvLevel.setText(hiddenDangerRecord.getJbName());
        tvDate.setText(hiddenDangerRecord.getFindTime());
        tvOrder.setText(hiddenDangerRecord.getClassName());
        tvHiddenSort.setText(hiddenDangerRecord.getHiddenBelong());
        tvHiddenArea.setText(hiddenDangerRecord.getAreaName());
        tvDiscoverer.setText(hiddenDangerRecord.getRecheckPersonName());
        tvIsHandle.setText(hiddenDangerRecord.getIshandle().equals("0")?"未处理":"已处理");
        etContent.setText(hiddenDangerRecord.getContent());
    }

    private void initView() {
        txtLeft = findViewById(R.id.txt_left);
        imgLeft = findViewById(R.id.img_left);
        txtTitle = findViewById(R.id.txt_title);
        imgRight = findViewById(R.id.img_right);
        txtRight = findViewById(R.id.txt_right);
        tvHiddenUnits = findViewById(R.id.tv_hidden_units);
        tvProfessional = findViewById(R.id.tv_professional);
        //tvHiddenClass = findViewById(R.id.tv_hidden_class);
        tvEnterMan = findViewById(R.id.tv_enter_man);
        tvLevel = findViewById(R.id.tv_level);
        tvDate = findViewById(R.id.tv_date);
        tvOrder = findViewById(R.id.tv_order);
        tvHiddenSort = findViewById(R.id.tv_hidden_sort);
        tvHiddenArea = findViewById(R.id.tv_hidden_area);
        tvDiscoverer = findViewById(R.id.tv_discoverer);
        tvIsHandle = findViewById(R.id.tv_is_handle);
        etContent = findViewById(R.id.et_content);
    }
}
