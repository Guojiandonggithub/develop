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
import com.example.administrator.riskprojects.bean.SelectItem;
import com.example.administrator.riskprojects.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 隐患记录 添加修改
 */
public class HiddenRiskRecordAddEditActivity extends BaseActivity {

    private TextView mTxtLeft;
    private ImageView mImgLeft;
    private TextView mTxtTitle;
    private ImageView mImgRight;
    private TextView mTxtRight;
    private TextView txtLeft;
    private ImageView imgLeft;
    private TextView txtTitle;
    private ImageView imgRight;
    private TextView txtRight;
    private Spinner spHiddenUnits;
    private SpinnerAdapter mSpHiddenUnitsAdapter;
    private Spinner spProfessional;
    private SpinnerAdapter spProfessionalAdapter;
    private Spinner spHiddenClass;
    private SpinnerAdapter spHiddenClassAdapter;
    private Spinner spHiddenTypes;
    private SpinnerAdapter spHiddenTypesAdapter;
    private Spinner spLevel;
    private SpinnerAdapter spLevelAdapter;
    private CardView cvSelectDate;
    private TextView tvDate;
    private Spinner spOrder;
    private SpinnerAdapter spOrderAdapter;
    private Spinner spHiddenSort;
    private SpinnerAdapter spHiddenSortAdapter;
    private Spinner spHiddenArea;
    private SpinnerAdapter spHiddenAreaAdapter;
    private Spinner spCheckContent;
    private SpinnerAdapter spCheckContentAdapter;
    private Spinner spIsHandle;
    private SpinnerAdapter spIsHandleAdapter;
    private ImageView checkYes;
    private ImageView checkNos;
    private EditText etContent;
    private LinearLayoutCompat llBottom;
    private TextView tvOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden_risk_record_add_edit);
        initView();
    }

    private void initView() {
        mTxtLeft = findViewById(R.id.txt_left);
        mImgLeft = findViewById(R.id.img_left);
        mTxtTitle = findViewById(R.id.txt_title);
        mTxtTitle.setText("隐患记录新增");
        mImgRight = findViewById(R.id.img_right);
        mTxtRight = findViewById(R.id.txt_right);
        spHiddenUnits = findViewById(R.id.sp_hidden_units);
        List<SelectItem> selectItems = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            SelectItem selectItem = new SelectItem();
            selectItem.name = "测试选项测试选项测试选项-" + i;
            selectItem.id = "id-" + i;
            selectItems.add(selectItem);
        }


        txtLeft = findViewById(R.id.txt_left);
        imgLeft = findViewById(R.id.img_left);
        txtTitle = findViewById(R.id.txt_title);
        imgRight = findViewById(R.id.img_right);
        txtRight = findViewById(R.id.txt_right);
        //隐患单位
        spHiddenUnits = findViewById(R.id.sp_hidden_units);
        mSpHiddenUnitsAdapter = SpinnerAdapter.createFromResource(this, selectItems);
        setUpSpinner(spHiddenUnits, mSpHiddenUnitsAdapter);
        //所属专业
        spProfessional = findViewById(R.id.sp_professional);
        spProfessionalAdapter = SpinnerAdapter.createFromResource(this, selectItems);
        setUpSpinner(spProfessional, spProfessionalAdapter);
        //隐患类别
        spHiddenClass = findViewById(R.id.sp_hidden_class);
        spHiddenClassAdapter = SpinnerAdapter.createFromResource(this, selectItems);
        setUpSpinner(spHiddenClass, spHiddenClassAdapter);
        //隐患类型
        spHiddenTypes = findViewById(R.id.sp_hidden_types);
        spHiddenTypesAdapter = SpinnerAdapter.createFromResource(this, selectItems);
        setUpSpinner(spHiddenTypes, spHiddenTypesAdapter);
        //级别
        spLevel = findViewById(R.id.sp_level);
        spLevelAdapter = SpinnerAdapter.createFromResource(this, selectItems);
        setUpSpinner(spLevel, spLevelAdapter);
        //选择时间
        cvSelectDate = findViewById(R.id.cv_select_date);
        tvDate = findViewById(R.id.tv_date);
        cvSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerActivity.startPickDate(HiddenRiskRecordAddEditActivity.this, HiddenRiskRecordAddEditActivity.this);
            }
        });
        //隐患班次
        spOrder = findViewById(R.id.sp_order);
        spOrderAdapter = SpinnerAdapter.createFromResource(this, selectItems);
        setUpSpinner(spOrder, spOrderAdapter);
        //隐患归类
        spHiddenSort = findViewById(R.id.sp_hidden_sort);
        spHiddenSortAdapter = SpinnerAdapter.createFromResource(this, selectItems);
        setUpSpinner(spHiddenSort, spHiddenSortAdapter);
        //隐患区域
        spHiddenArea = findViewById(R.id.sp_hidden_area);
        spHiddenAreaAdapter = SpinnerAdapter.createFromResource(this, selectItems);
        setUpSpinner(spHiddenArea, spHiddenAreaAdapter);
        ////检查内容
        spCheckContent = findViewById(R.id.sp_check_content);
        spCheckContentAdapter = SpinnerAdapter.createFromResource(this, selectItems);
        setUpSpinner(spCheckContent, spCheckContentAdapter);
        //处理否
        spIsHandle = findViewById(R.id.sp_is_handle);
        spIsHandleAdapter = SpinnerAdapter.createFromResource(this, selectItems);
        setUpSpinner(spIsHandle, spIsHandleAdapter);
        checkYes = findViewById(R.id.check_yes);
        checkNos = findViewById(R.id.check_nos);
        checkNos.setSelected(true);
        checkYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkYes.setSelected(true);
                checkNos.setSelected(false);
            }
        });
        checkNos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkYes.setSelected(false);
                checkNos.setSelected(true);
            }
        });
        etContent = findViewById(R.id.et_content);
        llBottom = findViewById(R.id.ll_bottom);
        tvOk = findViewById(R.id.tv_ok);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DatePickerActivity.REQUEST && resultCode == RESULT_OK) {
            tvDate.setText(data.getStringExtra(DatePickerActivity.DATE));
        }
    }
}
