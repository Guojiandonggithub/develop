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
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.administrator.riskprojects.Adpter.SpinnerAdapter;
import com.example.administrator.riskprojects.BaseActivity;
import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.bean.CollieryTeam;
import com.example.administrator.riskprojects.bean.GpHiddenDanger;
import com.example.administrator.riskprojects.bean.SelectItem;
import com.example.administrator.riskprojects.bean.UserInfo;
import com.example.administrator.riskprojects.common.NetUtil;
import com.example.administrator.riskprojects.net.BaseJsonRes;
import com.example.administrator.riskprojects.net.NetClient;
import com.example.administrator.riskprojects.tools.Constants;
import com.example.administrator.riskprojects.tools.UserUtils;
import com.example.administrator.riskprojects.tools.Utils;
import com.example.administrator.riskprojects.util.DensityUtil;
import com.juns.health.net.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

/**
 * 挂牌督办
 */
public class AddHangRecordActivity extends BaseActivity {
    private static final String TAG = "AddHangRecordActivity";
    private TextView txtTitle;
    private CardView cvSelectDate;
    private TextView tvDate;
    private Spinner spTrackPeopleUnit;
    private Spinner spTrackPeople;
    private EditText etContent;
    private SpinnerAdapter spTrackPeopleUnitAdapter;
    private SpinnerAdapter spTrackPeopleAdapter;
    private LinearLayoutCompat llBottom;
    private TextView tvOk;
    protected NetClient netClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hang_record);
        netClient = new NetClient(AddHangRecordActivity.this);
        initView();
        setView();
    }

    private void setView() {
        txtTitle.setText("添加挂牌记录");
        getspTrackPeopleUnit();
    }

    //督办人单位查询
    private void getspTrackPeopleUnit() {
        if (!NetUtil.checkNetWork(AddHangRecordActivity.this)) {
            String jsondata = Utils.getValue(AddHangRecordActivity.this, Constants.GET_COLLIERYTEAM);
            if("".equals(jsondata)){
                Utils.showShortToast(AddHangRecordActivity.this, "没有联网，没有请求到数据");
            }else{
                resultColliery(jsondata);
            }
        }else{
            RequestParams params = new RequestParams();
            params.put("employeeId", UserUtils.getUserID(AddHangRecordActivity.this));
            netClient.post(Data.getInstance().getIp()+Constants.GET_COLLIERYTEAM, params, new BaseJsonRes() {

                @Override
                public void onMySuccess(String data) {
                    Log.i(TAG, "获取部门/队组成员返回数据：" + data);
                    if (!TextUtils.isEmpty(data)) {
                        resultColliery(data);
                    }
                }

                @Override
                public void onMyFailure(String content) {
                    Log.e(TAG, "获取部门/队组成员返回错误信息：" + content);
                    Utils.showShortToast(AddHangRecordActivity.this, content);
                }
            });
        }
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

    private void setUpSpinnerchild(Spinner spinner, final SpinnerAdapter adapter) {
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

    //督办人查询
    private void getTrackPeople(String teamId) {
        if (!NetUtil.checkNetWork(AddHangRecordActivity.this)) {
            String jsondata = Utils.getValue(AddHangRecordActivity.this, Constants.EmployeeList);
            if("".equals(jsondata)){
                Utils.showShortToast(AddHangRecordActivity.this, "没有联网，没有请求到数据");
            }else{
                List<UserInfo> userinfoList = JSONArray.parseArray(jsondata,UserInfo.class);
                for(UserInfo userInfo:userinfoList){
                    if(teamId.equals(userInfo.getTeamId())){
                        String userinfoStr = JSON.toJSONString(userInfo);
                        resultTrackPeople(userinfoStr);
                    }
                }
            }
        }else{
            RequestParams params = new RequestParams();
            params.put("teamId",teamId);
            netClient.post(Data.getInstance().getIp()+ Constants.GET_EMPLOYEELISTBYTEAMID, params, new BaseJsonRes() {

                @Override
                public void onMySuccess(String data) {
                    Log.i(TAG, "督办人查询返回数据：" + data);
                    if (!TextUtils.isEmpty(data)) {
                        resultTrackPeople(data);
                    }
                }

                @Override
                public void onMyFailure(String content) {
                    Log.e(TAG, "督办人查询返回错误信息：" + content);
                    Utils.showShortToast(AddHangRecordActivity.this, content);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DatePickerActivity.REQUEST && resultCode == RESULT_OK) {
            tvDate.setText(data.getStringExtra(DatePickerActivity.DATE));
        }
    }

    //检查输入
    private boolean checkInput() {
        if (TextUtils.isEmpty(etContent.getText().toString())) {
            Utils.showShortToast(AddHangRecordActivity.this, "整改要求不能为空!");
            return false;
        }

        if (TextUtils.isEmpty(tvDate.getText().toString())) {
            Utils.showShortToast(AddHangRecordActivity.this, "挂牌时间不能为空!");
            return false;
        }
        SelectItem peopleUnit = (SelectItem)spTrackPeopleUnit.getSelectedItem();
        if(null==peopleUnit){
            Utils.showShortToast(AddHangRecordActivity.this, "督办单位不能为空!");
        }
        SelectItem people = (SelectItem)spTrackPeople.getSelectedItem();
        if(null==people){
            Utils.showShortToast(AddHangRecordActivity.this, "督办人不能为空!");
        }
        return true;
    }

    private void initView() {
        txtTitle = findViewById(R.id.txt_title);
        cvSelectDate = findViewById(R.id.cv_select_date);
        tvDate = findViewById(R.id.tv_date);
        spTrackPeopleUnit = findViewById(R.id.sp_track_people_unit);
        spTrackPeople = findViewById(R.id.sp_track_people);
        etContent = findViewById(R.id.et_content);
        llBottom = findViewById(R.id.ll_bottom);
        tvOk = findViewById(R.id.tv_ok);
        //选择时间
        cvSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerActivity.startPickDate(AddHangRecordActivity.this, AddHangRecordActivity.this);
            }
        });
        tvOk.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                GpHiddenDanger record = getHiddenDangerRecord();
                if(checkInput()){
                    addGpHiddenDanger(record);
                }
            }
        });
    }

    //添加挂牌
    private void addGpHiddenDanger(GpHiddenDanger gpHiddenDanger) {
        RequestParams params = new RequestParams();
        String hiddenDangerRecordStr = JSON.toJSONString(gpHiddenDanger);
        Log.i(TAG, "gpHiddenDanger: 隐患添加挂牌参数="+hiddenDangerRecordStr);
        params.put("hiddenDangerRecordJsonData",hiddenDangerRecordStr);

        netClient.post(Data.getInstance().getIp()+Constants.ADD_GPHIDDENDANGER, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "隐患添加挂牌返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    Utils.showShortToast(AddHangRecordActivity.this, "挂牌添加成功");
                    finish();
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "隐患添加挂牌返回错误信息：" + content);
                Utils.showShortToast(AddHangRecordActivity.this, content);
            }
        });
    }

    private GpHiddenDanger getHiddenDangerRecord(){
        GpHiddenDanger record = new GpHiddenDanger();
        String id = getIntent().getStringExtra("id");
        SelectItem spHiddenUnitItem = (SelectItem)spTrackPeopleUnit.getSelectedItem();
        record.setSupervisionDeptName(spHiddenUnitItem.toString());
        record.setSupervisionDeptId(String.valueOf(spHiddenUnitItem.id));

        SelectItem spHiddenPeopleItem = (SelectItem) spTrackPeople.getSelectedItem();
        record.setSupervisionPersonName(spHiddenPeopleItem.toString());
        record.setSupervisionPersonId(String.valueOf(spHiddenPeopleItem.id));

        record.setSupervisionTime(tvDate.getText().toString());
        record.setSupervisionDetail(etContent.getText().toString());
        record.setId(id);
        return record;
    }

    private void resultColliery(String data){
        List<CollieryTeam> collieryTeams = JSONArray.parseArray(data, CollieryTeam.class);
        final List<SelectItem> selectItems = new ArrayList<SelectItem>();
        for (int i = 0; i < collieryTeams.size(); i++) {
            SelectItem selectItem = new SelectItem();
            selectItem.name = collieryTeams.get(i).getTeamName().replaceAll("&nbsp;","   ");
            selectItem.id = collieryTeams.get(i).getId();
            selectItems.add(selectItem);
        }
        spTrackPeopleUnitAdapter = SpinnerAdapter.createFromResource(AddHangRecordActivity.this, selectItems, Gravity.CENTER_VERTICAL|Gravity.LEFT);
        setUpSpinner(spTrackPeopleUnit, spTrackPeopleUnitAdapter);
        spTrackPeopleUnitAdapter.notifyDataSetChanged();
        spTrackPeopleUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                spTrackPeopleUnitAdapter.setSelectedPostion(position);
                getTrackPeople(selectItems.get(position).getId());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void resultTrackPeople(String data){
        Log.i(TAG, "onMySuccess: ");
        List<UserInfo> collieryTeams = JSONArray.parseArray(data, UserInfo.class);
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        for (int i = 0; i < collieryTeams.size(); i++) {
            Log.i(TAG, "realname：" + collieryTeams.get(i).getRealName());
            Log.i(TAG, "id：" + collieryTeams.get(i).getId());
            SelectItem selectItem = new SelectItem();
            selectItem.name = collieryTeams.get(i).getRealName();
            selectItem.id = collieryTeams.get(i).getId();
            selectItems.add(selectItem);
        }
        spTrackPeopleAdapter = SpinnerAdapter.createFromResource(AddHangRecordActivity.this, selectItems, Gravity.CENTER_VERTICAL|Gravity.LEFT);
        setUpSpinnerchild(spTrackPeople, spTrackPeopleAdapter);
        spTrackPeopleAdapter.notifyDataSetChanged();
    }
}
