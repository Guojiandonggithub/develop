package com.example.administrator.riskprojects.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.administrator.riskprojects.Adpter.SpinnerAdapter;
import com.example.administrator.riskprojects.BaseActivity;
import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.bean.Area;
import com.example.administrator.riskprojects.bean.ClassNumber;
import com.example.administrator.riskprojects.bean.CollieryTeam;
import com.example.administrator.riskprojects.bean.DataDictionary;
import com.example.administrator.riskprojects.bean.HiddenDangerRecord;
import com.example.administrator.riskprojects.bean.RiskGrade;
import com.example.administrator.riskprojects.bean.SelectItem;
import com.example.administrator.riskprojects.bean.Specialty;
import com.example.administrator.riskprojects.net.BaseJsonRes;
import com.example.administrator.riskprojects.net.NetClient;
import com.example.administrator.riskprojects.tools.Constants;
import com.example.administrator.riskprojects.tools.Utils;
import com.example.administrator.riskprojects.util.DensityUtil;
import com.juns.health.net.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

/**
 * 隐患记录 添加修改
 */
public class HiddenRiskRecordAddEditActivity extends BaseActivity {
    private static final String TAG = "HiddenRiskRecordAddEdit";
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
    private EditText etLocation;
    private LinearLayoutCompat llBottom;
    private TextView tvOk;
    protected NetClient netClient;
    private HiddenDangerRecord record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden_risk_record_add_edit);
        netClient = new NetClient(HiddenRiskRecordAddEditActivity.this);
        initView();
        initdata();
    }

    private void initView() {
        mTxtLeft = findViewById(R.id.txt_left);
        mImgLeft = findViewById(R.id.img_left);
        mTxtTitle = findViewById(R.id.txt_title);
        mTxtTitle.setText("隐患记录新增");
        mImgRight = findViewById(R.id.img_right);
        mTxtRight = findViewById(R.id.txt_right);
        spHiddenUnits = findViewById(R.id.sp_hidden_units);

        txtLeft = findViewById(R.id.txt_left);
        imgLeft = findViewById(R.id.img_left);
        txtTitle = findViewById(R.id.txt_title);
        imgRight = findViewById(R.id.img_right);
        txtRight = findViewById(R.id.txt_right);
        //隐患单位
        spHiddenUnits = findViewById(R.id.sp_hidden_units);

        //所属专业
        spProfessional = findViewById(R.id.sp_professional);

        //隐患类别
        spHiddenClass = findViewById(R.id.sp_hidden_class);

        //隐患类型
        spHiddenTypes = findViewById(R.id.sp_hidden_types);

        //级别
        spLevel = findViewById(R.id.sp_level);

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

        //隐患归类
        spHiddenSort = findViewById(R.id.sp_hidden_sort);

        //隐患区域
        spHiddenArea = findViewById(R.id.sp_hidden_area);

        ////检查内容
        spCheckContent = findViewById(R.id.sp_check_content);

        //处理否
        spIsHandle = findViewById(R.id.sp_is_handle);
        List<SelectItem> selectItems = new ArrayList<>();
        SelectItem selectItemw = new SelectItem();
        SelectItem selectItemy = new SelectItem();
        selectItemw.name = "未处理";
        selectItemw.id = "未处理";
        selectItemy.name = "已处理";
        selectItemy.id = "已处理";
        selectItems.add(selectItemw);
        selectItems.add(selectItemy);
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
        etLocation = findViewById(R.id.et_add_location);
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


    private void initdata(){
        getCollieryTeam();
        getSpecialty();
        getRiskGrade();
        getClassNumber();
        getArea();
        getHiddenType();
        getHiddenGrade();
        getHiddenYHGSLX();
        getCheckContent();
        spProfessional.setSelection(5,true);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        getHiddenRecord(id);
    }

    //获取部门/队组成员
    private void getCollieryTeam() {
        RequestParams params = new RequestParams();
        netClient.post(Constants.GET_COLLIERYTEAM, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "获取部门/队组成员返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    List<CollieryTeam> collieryTeams = JSONArray.parseArray(data, CollieryTeam.class);
                    List<SelectItem> selectItems = new ArrayList<>();
                    for (int i = 0; i < collieryTeams.size(); i++) {
                        SelectItem selectItem = new SelectItem();
                        selectItem.name = collieryTeams.get(i).getTeamName().replaceAll("&nbsp;","   ");
                        selectItem.id = collieryTeams.get(i).getId();
                        selectItems.add(selectItem);
                    }
                    mSpHiddenUnitsAdapter = SpinnerAdapter.createFromResource(HiddenRiskRecordAddEditActivity.this, selectItems, Gravity.CENTER_VERTICAL|Gravity.LEFT);
                    setUpSpinner(spHiddenUnits, mSpHiddenUnitsAdapter);
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "获取部门/队组成员返回错误信息：" + content);
                Utils.showLongToast(HiddenRiskRecordAddEditActivity.this, content);
            }
        });
    }

    //获取所属专业
    private void getSpecialty() {
        RequestParams params = new RequestParams();
        netClient.post(Constants.GET_SPECIALTY, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "获取所属专业返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    List<Specialty> collieryTeams = JSONArray.parseArray(data, Specialty.class);
                    List<SelectItem> selectItems = new ArrayList<>();
                    for (int i = 0; i < collieryTeams.size(); i++) {
                        SelectItem selectItem = new SelectItem();
                        selectItem.name = collieryTeams.get(i).getSname();
                        selectItem.id = collieryTeams.get(i).getId();
                        selectItems.add(selectItem);
                    }
                    spProfessionalAdapter = SpinnerAdapter.createFromResource(HiddenRiskRecordAddEditActivity.this, selectItems);
                    setUpSpinner(spProfessional, spProfessionalAdapter);
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "获取所属专业返回错误信息：" + content);
                Utils.showLongToast(HiddenRiskRecordAddEditActivity.this, content);
            }
        });
    }

    //获取隐患类别
    private void getRiskGrade() {
        RequestParams params = new RequestParams();
        netClient.post(Constants.GET_RISKGRADE, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "获取隐患类别返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    List<RiskGrade> collieryTeams = JSONArray.parseArray(data, RiskGrade.class);
                    List<SelectItem> selectItems = new ArrayList<>();
                    for (int i = 0; i < collieryTeams.size(); i++) {
                        SelectItem selectItem = new SelectItem();
                        selectItem.name = collieryTeams.get(i).getGname();
                        selectItem.id = collieryTeams.get(i).getId();
                        selectItems.add(selectItem);
                    }
                    spHiddenClassAdapter = SpinnerAdapter.createFromResource(HiddenRiskRecordAddEditActivity.this, selectItems);
                    setUpSpinner(spHiddenClass, spHiddenClassAdapter);
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "获取隐患类别返回错误信息：" + content);
                Utils.showLongToast(HiddenRiskRecordAddEditActivity.this, content);
            }
        });
    }

    //获取班次
    private void getClassNumber() {
        RequestParams params = new RequestParams();
        netClient.post(Constants.GET_CLASSNUMBER, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "获取班次返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    List<ClassNumber> collieryTeams = JSONArray.parseArray(data, ClassNumber.class);
                    List<SelectItem> selectItems = new ArrayList<>();
                    for (int i = 0; i < collieryTeams.size(); i++) {
                        SelectItem selectItem = new SelectItem();
                        selectItem.name = collieryTeams.get(i).getClassName();
                        selectItem.id = collieryTeams.get(i).getId();
                        selectItems.add(selectItem);
                    }
                    spOrderAdapter = SpinnerAdapter.createFromResource(HiddenRiskRecordAddEditActivity.this, selectItems);
                    setUpSpinner(spOrder, spOrderAdapter);
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "获取班次返回错误信息：" + content);
                Utils.showLongToast(HiddenRiskRecordAddEditActivity.this, content);
            }
        });
    }

    //获取区域
    private void getArea() {
        RequestParams params = new RequestParams();
        netClient.post(Constants.GET_AREA, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "获取区域返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    List<Area> collieryTeams = JSONArray.parseArray(data, Area.class);
                    List<SelectItem> selectItems = new ArrayList<>();
                    for (int i = 0; i < collieryTeams.size(); i++) {
                        SelectItem selectItem = new SelectItem();
                        selectItem.name = collieryTeams.get(i).getAreaName();
                        selectItem.id = collieryTeams.get(i).getId();
                        selectItems.add(selectItem);
                    }
                    spHiddenAreaAdapter = SpinnerAdapter.createFromResource(HiddenRiskRecordAddEditActivity.this, selectItems);
                    setUpSpinner(spHiddenArea, spHiddenAreaAdapter);
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "获取区域返回错误信息：" + content);
                Utils.showLongToast(HiddenRiskRecordAddEditActivity.this, content);
            }
        });
    }

    //获取隐患类型
    private void getHiddenType() {
        RequestParams params = new RequestParams();
        params.put("dictTypeCode","YHLX");
        netClient.post(Constants.GET_DATADICT, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "获取隐患类型返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    List<DataDictionary> collieryTeams = JSONArray.parseArray(data, DataDictionary.class);
                    List<SelectItem> selectItems = new ArrayList<>();
                    for (int i = 0; i < collieryTeams.size(); i++) {
                        SelectItem selectItem = new SelectItem();
                        selectItem.name = collieryTeams.get(i).getName();
                        selectItem.id = collieryTeams.get(i).getId();
                        selectItems.add(selectItem);
                    }
                    spHiddenTypesAdapter = SpinnerAdapter.createFromResource(HiddenRiskRecordAddEditActivity.this, selectItems);
                    setUpSpinner(spHiddenTypes, spHiddenTypesAdapter);
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "获取隐患类型返回错误信息：" + content);
                Utils.showLongToast(HiddenRiskRecordAddEditActivity.this, content);
            }
        });
    }

    //获取隐患级别
    private void getHiddenGrade() {
        RequestParams params = new RequestParams();
        params.put("dictTypeCode","YHJB");
        netClient.post(Constants.GET_DATADICT, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "获取隐患级别返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    List<DataDictionary> collieryTeams = JSONArray.parseArray(data, DataDictionary.class);
                    List<SelectItem> selectItems = new ArrayList<>();
                    for (int i = 0; i < collieryTeams.size(); i++) {
                        SelectItem selectItem = new SelectItem();
                        selectItem.name = collieryTeams.get(i).getName();
                        selectItem.id = collieryTeams.get(i).getId();
                        selectItems.add(selectItem);
                    }
                    spLevelAdapter = SpinnerAdapter.createFromResource(HiddenRiskRecordAddEditActivity.this, selectItems);
                    setUpSpinner(spLevel, spLevelAdapter);

                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "获取隐患级别返回错误信息：" + content);
                Utils.showLongToast(HiddenRiskRecordAddEditActivity.this, content);
            }
        });
    }

    //获取隐患归类
    private void getHiddenYHGSLX() {
        RequestParams params = new RequestParams();
        params.put("dictTypeCode","YHGSLX");
        netClient.post(Constants.GET_DATADICT, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "获取隐患归类返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    List<DataDictionary> collieryTeams = JSONArray.parseArray(data, DataDictionary.class);
                    List<SelectItem> selectItems = new ArrayList<>();
                    for (int i = 0; i < collieryTeams.size(); i++) {
                        SelectItem selectItem = new SelectItem();
                        selectItem.name = collieryTeams.get(i).getName();
                        selectItem.id = collieryTeams.get(i).getId();
                        selectItems.add(selectItem);
                    }
                    spHiddenSortAdapter = SpinnerAdapter.createFromResource(HiddenRiskRecordAddEditActivity.this, selectItems);
                    setUpSpinner(spHiddenSort, spHiddenSortAdapter);
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "获取隐患归类返回错误信息：" + content);
                Utils.showLongToast(HiddenRiskRecordAddEditActivity.this, content);
            }
        });
    }

    //获取检查内容
    private void getCheckContent() {
        RequestParams params = new RequestParams();
        params.put("dictTypeCode","JCNR");
        netClient.post(Constants.GET_DATADICT, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "获取检查内容返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    List<DataDictionary> collieryTeams = JSONArray.parseArray(data, DataDictionary.class);
                    List<SelectItem> selectItems = new ArrayList<>();
                    for (int i = 0; i < collieryTeams.size(); i++) {
                        SelectItem selectItem = new SelectItem();
                        selectItem.name = collieryTeams.get(i).getName();
                        selectItem.id = collieryTeams.get(i).getId();
                        selectItems.add(selectItem);
                    }
                    spCheckContentAdapter = SpinnerAdapter.createFromResource(HiddenRiskRecordAddEditActivity.this, selectItems);
                    setUpSpinner(spCheckContent, spCheckContentAdapter);
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "获取检查内容返回错误信息：" + content);
                Utils.showLongToast(HiddenRiskRecordAddEditActivity.this, content);
            }
        });
    }

    private void getHiddenRecord(String id) {//隐患id
        RequestParams params = new RequestParams();
        params.put("hiddenDangerRecordId",id);
        netClient.post(Constants.HIDDENDANGERRECORD, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "隐患数据返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    JSONObject returndata  = JSON.parseObject(data);
                    record = JSONArray.parseObject(data, HiddenDangerRecord.class);
                    etContent.setText(record.getContent());
                    etLocation.setText(record.getHiddenPlace());

                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "隐患数据返回错误信息：" + content);
                Utils.showLongToast(HiddenRiskRecordAddEditActivity.this, content);
                return;
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
