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
import com.example.administrator.riskprojects.tools.UserUtils;
import com.example.administrator.riskprojects.tools.Utils;
import com.example.administrator.riskprojects.util.DensityUtil;
import com.juns.health.net.loopj.android.http.RequestParams;
import java.util.Calendar;
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
    private HiddenDangerRecord record = new HiddenDangerRecord();
    private String id;

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
        selectItemw.id = "0";
        selectItemy.name = "已处理";
        selectItemy.id = "1";
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
        tvOk.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                HiddenDangerRecord record = getHiddenDangerRecord();
                String flag = "add";
                if (!TextUtils.isEmpty(id)) {
                    flag = "update";
                    record.setId(id);
                }
                addEditHiddenDanger(record,flag);
            }
        });
    }

    private void setUpSpinner(Spinner spinner, final SpinnerAdapter adapter) {
        spinner.setBackgroundColor(0x0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            spinner.setPopupBackgroundResource(R.drawable.shape_rectangle_rounded);
            spinner.setDropDownVerticalOffset(DensityUtil.dip2px(this, 3));
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adapter.setSelectedPostion(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner.setAdapter(adapter);
    }


    private void initdata(){
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        if (!TextUtils.isEmpty(id)) {
            String hiddenrecordjson = getIntent().getStringExtra("hiddenrecordjson");
            Log.e(TAG, "initdata: hiddenrecordjson-------------"+hiddenrecordjson);
            record = JSONArray.parseObject(hiddenrecordjson, HiddenDangerRecord.class);
            etContent.setText(record.getContent());
            etLocation.setText(record.getHiddenPlace());
            String isuper = record.getIsupervision();
            if(TextUtils.equals(isuper,"0")){
                checkYes.setSelected(false);
                checkNos.setSelected(true);
            }else{
                checkYes.setSelected(true);
                checkNos.setSelected(false);
            }
            String ishandle = record.getIshandle();
            spIsHandleAdapter.notifyDataSetChanged();
            spIsHandle.setSelection(Integer.parseInt(ishandle));
        }
        getCollieryTeam();
        getSpecialty();
        getRiskGrade();
        getClassNumber();
        getArea();
        getHiddenType();
        getHiddenGrade();
        getHiddenYHGSLX();
        getCheckContent();
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
                    int collieryTeamsint = 0;
                    for (int i = 0; i < collieryTeams.size(); i++) {
                        SelectItem selectItem = new SelectItem();
                        selectItem.name = collieryTeams.get(i).getTeamName().replaceAll("&nbsp;","   ");
                        selectItem.id = collieryTeams.get(i).getId();
                        if(collieryTeams.get(i).getId().equals(record.getTeamGroupCode())){
                            collieryTeamsint = i;
                        }
                        selectItems.add(selectItem);
                    }
                    mSpHiddenUnitsAdapter = SpinnerAdapter.createFromResource(HiddenRiskRecordAddEditActivity.this, selectItems, Gravity.CENTER_VERTICAL|Gravity.LEFT);
                    setUpSpinner(spHiddenUnits, mSpHiddenUnitsAdapter);
                    mSpHiddenUnitsAdapter.notifyDataSetChanged();
                    spHiddenUnits.setSelection(collieryTeamsint);
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
                    int sint = 0;
                    for (int i = 0; i < collieryTeams.size(); i++) {
                        SelectItem selectItem = new SelectItem();
                        selectItem.name = collieryTeams.get(i).getSname();
                        selectItem.id = collieryTeams.get(i).getId();
                        if(collieryTeams.get(i).getId().equals(record.getSid())){
                            sint = i;
                        }
                        selectItems.add(selectItem);
                    }
                    spProfessionalAdapter = SpinnerAdapter.createFromResource(HiddenRiskRecordAddEditActivity.this, selectItems);
                    setUpSpinner(spProfessional, spProfessionalAdapter);
                    spProfessionalAdapter.notifyDataSetChanged();
                    spProfessional.setSelection(sint);
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
                    int riskGrade = 0;
                    for (int i = 0; i < collieryTeams.size(); i++) {
                        SelectItem selectItem = new SelectItem();
                        selectItem.name = collieryTeams.get(i).getGname();
                        selectItem.id = collieryTeams.get(i).getId();
                        selectItems.add(selectItem);
                        if(collieryTeams.get(i).getId().equals(record.getGid())){
                            riskGrade = i;
                        }
                    }
                    spHiddenClassAdapter = SpinnerAdapter.createFromResource(HiddenRiskRecordAddEditActivity.this, selectItems);
                    setUpSpinner(spHiddenClass, spHiddenClassAdapter);
                    spHiddenClassAdapter.notifyDataSetChanged();
                    spHiddenClass.setSelection(riskGrade);
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
                    int classNumberint = 0;
                    for (int i = 0; i < collieryTeams.size(); i++) {
                        SelectItem selectItem = new SelectItem();
                        selectItem.name = collieryTeams.get(i).getClassName();
                        selectItem.id = collieryTeams.get(i).getId();
                        selectItems.add(selectItem);
                        if(collieryTeams.get(i).getId().equals(record.getClassId())){
                            classNumberint = i;
                        }
                    }
                    spOrderAdapter = SpinnerAdapter.createFromResource(HiddenRiskRecordAddEditActivity.this, selectItems);
                    setUpSpinner(spOrder, spOrderAdapter);
                    spOrderAdapter.notifyDataSetChanged();
                    spOrder.setSelection(classNumberint);
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
                    int areaint = 0;
                    for (int i = 0; i < collieryTeams.size(); i++) {
                        SelectItem selectItem = new SelectItem();
                        selectItem.name = collieryTeams.get(i).getAreaName();
                        selectItem.id = collieryTeams.get(i).getId();
                        selectItems.add(selectItem);
                        if(collieryTeams.get(i).getId().equals(record.getAreaId())){
                            areaint = i;
                        }
                    }
                    spHiddenAreaAdapter = SpinnerAdapter.createFromResource(HiddenRiskRecordAddEditActivity.this, selectItems);
                    setUpSpinner(spHiddenArea, spHiddenAreaAdapter);
                    spHiddenAreaAdapter.notifyDataSetChanged();
                    spHiddenArea.setSelection(areaint);
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
                    int dataDictionary = 0;
                    for (int i = 0; i < collieryTeams.size(); i++) {
                        SelectItem selectItem = new SelectItem();
                        selectItem.name = collieryTeams.get(i).getName();
                        selectItem.id = collieryTeams.get(i).getId();
                        selectItems.add(selectItem);
                        if(collieryTeams.get(i).getId().equals(record.getTid())){
                            dataDictionary = i;
                        }
                    }
                    spHiddenTypesAdapter = SpinnerAdapter.createFromResource(HiddenRiskRecordAddEditActivity.this, selectItems);
                    setUpSpinner(spHiddenTypes, spHiddenTypesAdapter);
                    spHiddenTypesAdapter.notifyDataSetChanged();
                    spHiddenTypes.setSelection(dataDictionary);
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
                    int dataDictionary = 0;
                    for (int i = 0; i < collieryTeams.size(); i++) {
                        SelectItem selectItem = new SelectItem();
                        selectItem.name = collieryTeams.get(i).getName();
                        selectItem.id = collieryTeams.get(i).getId();
                        selectItems.add(selectItem);
                        if(collieryTeams.get(i).getName().equals(record.getJbName())){
                            dataDictionary = i;
                        }
                    }
                    spLevelAdapter = SpinnerAdapter.createFromResource(HiddenRiskRecordAddEditActivity.this, selectItems);
                    setUpSpinner(spLevel, spLevelAdapter);
                    spLevelAdapter.notifyDataSetChanged();
                    spLevel.setSelection(dataDictionary);

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
                    int dataDictionary = 0;
                    for (int i = 0; i < collieryTeams.size(); i++) {
                        SelectItem selectItem = new SelectItem();
                        selectItem.name = collieryTeams.get(i).getName();
                        selectItem.id = collieryTeams.get(i).getId();
                        selectItems.add(selectItem);
                        if(collieryTeams.get(i).getId().equals(record.getHiddenBelongId())){
                            dataDictionary = i;
                        }
                    }
                    spHiddenSortAdapter = SpinnerAdapter.createFromResource(HiddenRiskRecordAddEditActivity.this, selectItems);
                    setUpSpinner(spHiddenSort, spHiddenSortAdapter);
                    spHiddenSortAdapter.notifyDataSetChanged();
                    spHiddenSort.setSelection(dataDictionary);
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
                    int dataDictionary = 0;
                    for (int i = 0; i < collieryTeams.size(); i++) {
                        SelectItem selectItem = new SelectItem();
                        selectItem.name = collieryTeams.get(i).getName();
                        selectItem.id = collieryTeams.get(i).getId();
                        selectItems.add(selectItem);
                        if(collieryTeams.get(i).getId().equals(record.getHiddenCheckContentId())){
                            dataDictionary = i;
                        }
                    }
                    spCheckContentAdapter = SpinnerAdapter.createFromResource(HiddenRiskRecordAddEditActivity.this, selectItems);
                    setUpSpinner(spCheckContent, spCheckContentAdapter);
                    spCheckContentAdapter.notifyDataSetChanged();
                    Log.e(TAG, "onMySuccess: getHiddenCheckContentId===="+ dataDictionary);
                    spCheckContent.setSelection(dataDictionary);
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
                    String isuper = record.getIsupervision();
                    if(TextUtils.equals(isuper,"0")){
                        checkYes.setSelected(false);
                        checkNos.setSelected(true);
                    }else{
                        checkYes.setSelected(true);
                        checkNos.setSelected(false);
                    }
                    String ishandle = record.getIshandle();
                    spIsHandleAdapter.notifyDataSetChanged();
                    spIsHandle.setSelection(Integer.parseInt(ishandle));

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

    //添加修改隐患记录
    private void addEditHiddenDanger(HiddenDangerRecord hiddenDangerRecord,String flag) {
        RequestParams params = new RequestParams();
        hiddenDangerRecord.setFlag(record.getFlag());
        String hiddenDangerRecordStr = JSON.toJSONString(hiddenDangerRecord);
        Log.i(TAG, "addHiddenDanger: 隐患添加修改="+hiddenDangerRecordStr);
        params.put("hiddenDangerRecordJsonData",hiddenDangerRecordStr);
        if(flag.equals("add")){
            flag = Constants.ADD_HIDDENDANGERRECORD;
        }else{
            flag = Constants.UPDATE_HIDDENDANGERRECORD;
        }

        Log.e(TAG, "addEditHiddenDanger: flag==============="+flag);
        netClient.post(flag, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "添加修改记录返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    Utils.showLongToast(HiddenRiskRecordAddEditActivity.this, "隐患添加成功！");
                    finish();
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "添加修改隐患记录返回错误信息：" + content);
                Utils.showLongToast(HiddenRiskRecordAddEditActivity.this, content);
            }
        });
    }

    private HiddenDangerRecord getHiddenDangerRecord(){
        HiddenDangerRecord record = new HiddenDangerRecord();
        SelectItem spHiddenUnitItem = (SelectItem)spHiddenUnits.getSelectedItem();
        record.setTeamGroupCode(String.valueOf(spHiddenUnitItem.id));
        record.setTeamGroupName(spHiddenUnitItem.toString());

        SelectItem spProfessionalItem = (SelectItem)spProfessional.getSelectedItem();
        record.setSid(String.valueOf(spProfessionalItem.id));
        record.setSname(spProfessionalItem.toString());


        SelectItem spHiddenClassItem = (SelectItem)spHiddenClass.getSelectedItem();
        record.setGname(spHiddenClassItem.toString());
        record.setGid(String.valueOf(spHiddenClassItem.id));

        SelectItem spHiddenTypesItem = (SelectItem) spHiddenTypes.getSelectedItem();
        record.setTname(spHiddenTypesItem.toString());
        record.setTid(String.valueOf(spHiddenTypesItem.id));


        SelectItem spOrderItem = (SelectItem)spOrder.getSelectedItem();
        record.setClassName(spOrderItem.toString());
        record.setClassId(String.valueOf(spOrderItem.id));

        SelectItem spHiddenSortItem = (SelectItem)spHiddenSort.getSelectedItem();
        record.setHiddenBelong(spHiddenSortItem.toString());
        record.setHiddenBelongId(String.valueOf(spHiddenSortItem.id));

        SelectItem spHiddenAreaItem = (SelectItem)spHiddenArea.getSelectedItem();
        String hiddenareaItem = spHiddenAreaItem.toString();
        hiddenareaItem = hiddenareaItem.replaceAll("#","_");
        record.setAreaName(hiddenareaItem);
        record.setAreaId(String.valueOf(spHiddenAreaItem.id));

        SelectItem selectedItem =(SelectItem)spCheckContent.getSelectedItem();
        record.setHiddenCheckContent(selectedItem.getName());
        record.setHiddenCheckContentId(selectedItem.getId());

        SelectItem spIsHandleItem = (SelectItem)spIsHandle.getSelectedItem();
        record.setIshandle(String.valueOf(spIsHandleItem.id));

        if(checkYes.isSelected()){
            record.setIsupervision("1");
        }
        if(checkNos.isSelected()){
            record.setIsupervision("0");
        }

        record.setJbName(spLevel.getSelectedItem().toString());
        String dateStr = tvDate.getText().toString().replaceAll("\\.","-");
        Calendar cal = Calendar.getInstance();
        //当前时：HOUR_OF_DAY-24小时制；HOUR-12小时制
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        //当前分
        int minute = cal.get(Calendar.MINUTE);
        //当前秒
        int second = cal.get(Calendar.SECOND);
        dateStr = dateStr + " " + hour + ":" + + minute+ ":" + second;
        record.setFindTime(dateStr);
        record.setHiddenPlace(etLocation.getText().toString());
        record.setContent(etContent.getText().toString());
        record.setEmployeeId(UserUtils.getUserID(HiddenRiskRecordAddEditActivity.this));
        record.setRealName(UserUtils.getUserName(HiddenRiskRecordAddEditActivity.this));
        record.setFlag(record.getFlag());
        return record;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DatePickerActivity.REQUEST && resultCode == RESULT_OK) {
            tvDate.setText(data.getStringExtra(DatePickerActivity.DATE));
        }
    }
}
