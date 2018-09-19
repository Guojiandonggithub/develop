package com.example.administrator.riskprojects.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.administrator.riskprojects.Adpter.SpinnerAdapter;
import com.example.administrator.riskprojects.BaseActivity;
import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.util.DensityUtil;

public class FiveDecisionsActivity extends BaseActivity {

    private TextView txtLeft;
    private ImageView imgLeft;
    private TextView txtTitle;
    private ImageView imgRight;
    private TextView txtRight;
    private EditText etAddLocation;
    private CardView cvSelectDate;
    private TextView tvDate;
    private Spinner spMineArea;
    private SpinnerAdapter spMineAreaAdapter;
    private Spinner spDepartment;
    private SpinnerAdapter spDepartmentAdapter;
    private Spinner spResponsibleThose;
    private SpinnerAdapter spResponsibleThoseAdapter;
    private EditText etMoney;
    private EditText etNum;
    private Spinner spTrackPeople;
    private SpinnerAdapter spTrackPeopleAdapter;
    private Spinner spTrackPeopleUnit;
    private SpinnerAdapter spTrackPeopleUnitAdapter;
    private EditText etContent;
    private LinearLayoutCompat llBottom;
    private TextView tvOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five_decisions);
        initView();
        setView();
    }

    private void setView() {
        txtTitle.setText("五定");
        cvSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerActivity.startPickDate(FiveDecisionsActivity.this, FiveDecisionsActivity.this);
            }
        });
    }

    private void initView() {
        txtLeft = findViewById(R.id.txt_left);
        imgLeft = findViewById(R.id.img_left);
        txtTitle = findViewById(R.id.txt_title);
        imgRight = findViewById(R.id.img_right);
        txtRight = findViewById(R.id.txt_right);
        etAddLocation = findViewById(R.id.et_add_location);
        cvSelectDate = findViewById(R.id.cv_select_date);
        tvDate = findViewById(R.id.tv_date);
        spMineArea = findViewById(R.id.sp_mine_area);
        spDepartment = findViewById(R.id.sp_department);
        spResponsibleThose = findViewById(R.id.sp_responsible_those);
        etMoney = findViewById(R.id.et_money);
        etNum = findViewById(R.id.et_num);
        spTrackPeople = findViewById(R.id.sp_track_people);
        spTrackPeopleUnit = findViewById(R.id.sp_track_people_unit);
        etContent = findViewById(R.id.et_content);
        llBottom = findViewById(R.id.ll_bottom);
        tvOk = findViewById(R.id.tv_ok);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DatePickerActivity.REQUEST && resultCode == RESULT_OK) {
            tvDate.setText(data.getStringExtra(DatePickerActivity.DATE));
        }
    }


    private void setUpSpinner(Spinner spinner, final SpinnerAdapter adapter) {
        spinner.setBackgroundColor(0x0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            spinner.setPopupBackgroundResource(R.drawable.shape_rectangle_rounded);
            spinner.setDropDownVerticalOffset(DensityUtil.dip2px(this, 3));
        }
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adapter.setSelectedPostion(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
