package com.example.administrator.riskprojects.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.riskprojects.BaseActivity;
import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.view.MyAlertDialog;

public class HiddenDangerDetailManagementActivity extends BaseActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden_danger_detail_management);
        initView();
        setView();
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
                startActivity(new Intent(HiddenDangerDetailManagementActivity.this, HiddenRiskRecordAddEditActivity.class));
            }
        });

    }

    private void initView() {
        txtLeft = findViewById(R.id.txt_left);
        imgLeft = findViewById(R.id.img_left);
        txtTitle = findViewById(R.id.txt_title);
        imgRight = findViewById(R.id.img_right);
        txtRight = findViewById(R.id.txt_right);
        tvHiddenUnits = findViewById(R.id.tv_hidden_units);
        tvTimeOrOrder = findViewById(R.id.tv_time_or_order);
        tvHiddenContent = findViewById(R.id.tv_hidden_content);
        tvHiddenDangerBelongs = findViewById(R.id.tv_hidden_danger_belongs);
        expand = findViewById(R.id.expand);
        tvProfessional = findViewById(R.id.tv_professional);
        tvArea = findViewById(R.id.tv_area);
        tvClasses = findViewById(R.id.tv_classes);
        tvOversee = findViewById(R.id.tv_oversee);
        ivStatusSecond = findViewById(R.id.iv_status_second);
        clickMore = findViewById(R.id.click_more);
        ivStatus = findViewById(R.id.iv_status);
        llBottom = findViewById(R.id.ll_bottom);
        tvDelete = findViewById(R.id.tv_delete);
        tvChange = findViewById(R.id.tv_change);
    }
}
