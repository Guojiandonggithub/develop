package com.example.administrator.riskprojects.activity;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.administrator.riskprojects.BaseActivity;
import com.example.administrator.riskprojects.R;

public class AddHangRecordActivity extends BaseActivity {

    private TextView txtLeft;
    private ImageView imgLeft;
    private TextView txtTitle;
    private ImageView imgRight;
    private TextView txtRight;
    private CardView cvSelectDate;
    private TextView tvDate;
    private Spinner spDepartments;
    private Spinner spTrackPeopleUnit;
    private Spinner spTrackPeople;
    private EditText etContent;
    private LinearLayoutCompat llBottom;
    private TextView tvOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hang_record);
        initView();
        setView();
    }

    private void setView() {
        txtTitle.setText("添加挂牌记录");
    }

    private void initView() {
        txtLeft = findViewById(R.id.txt_left);
        imgLeft = findViewById(R.id.img_left);
        txtTitle = findViewById(R.id.txt_title);
        imgRight = findViewById(R.id.img_right);
        txtRight = findViewById(R.id.txt_right);
        cvSelectDate = findViewById(R.id.cv_select_date);
        tvDate = findViewById(R.id.tv_date);
        spDepartments = findViewById(R.id.sp_departments);
        spTrackPeopleUnit = findViewById(R.id.sp_track_people_unit);
        spTrackPeople = findViewById(R.id.sp_track_people);
        etContent = findViewById(R.id.et_content);
        llBottom = findViewById(R.id.ll_bottom);
        tvOk = findViewById(R.id.tv_ok);
    }
}
