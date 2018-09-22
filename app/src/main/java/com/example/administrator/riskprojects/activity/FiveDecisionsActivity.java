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
import com.example.administrator.riskprojects.bean.CollieryTeam;
import com.example.administrator.riskprojects.bean.HiddenDangerRecord;
import com.example.administrator.riskprojects.bean.SelectItem;
import com.example.administrator.riskprojects.bean.ThreeFix;
import com.example.administrator.riskprojects.bean.UserInfo;
import com.example.administrator.riskprojects.net.BaseJsonRes;
import com.example.administrator.riskprojects.net.NetClient;
import com.example.administrator.riskprojects.tools.Constants;
import com.example.administrator.riskprojects.tools.Utils;
import com.example.administrator.riskprojects.util.DensityUtil;
import com.juns.health.net.loopj.android.http.RequestParams;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FiveDecisionsActivity extends BaseActivity {
    private static final String TAG = "FiveDecisionsActivity";
    private TextView txtLeft;
    private ImageView imgLeft;
    private TextView txtTitle;
    private ImageView imgRight;
    private TextView txtRight;
    private TextView etAddLocation;
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
    private ThreeFix threeFix;
    protected NetClient netClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five_decisions);
        netClient = new NetClient(FiveDecisionsActivity.this);
        initView();
        setView();
    }

    private void setView() {
        Bundle  bundle = getIntent().getBundleExtra("threeBund");
        threeFix = (ThreeFix) bundle.getSerializable("threeFix");
        etAddLocation.setText(threeFix.getTeamGroupName());
        tvDate.setText(threeFix.getCompleteTime());
        SelectItem selectItem = new SelectItem();
        List<SelectItem> selectItems = new ArrayList<>();
        selectItem.name = "矿区";
        selectItem.id = "KG";
        selectItems.add(selectItem);
        spMineAreaAdapter = SpinnerAdapter.createFromResource(this, selectItems);
        setUpSpinner(spMineArea, spMineAreaAdapter);
        getCollieryTeam();
        getspTrackPeopleUnit();
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
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                threeFix.setCompleteTime(tvDate.getText().toString());
                SelectItem deptType = (SelectItem)spMineArea.getSelectedItem();
                SelectItem spDepartmentItem = (SelectItem)spDepartment.getSelectedItem();
                threeFix.setLsdeptType(deptType.id);
                threeFix.setTeamGroupCode(spDepartmentItem.id);
                threeFix.setTeamGroupName(spDepartmentItem.toString());
                threeFix.setMoney(etMoney.getText().toString());
                threeFix.setPersonNum(etNum.getText().toString());
                threeFix.setMeasure(etContent.getText().toString());
                //跟踪人
                SelectItem spTrackPeopleItem = (SelectItem)spTrackPeople.getSelectedItem();
                //跟踪人单位
                SelectItem spTrackPeopleUnitItem = (SelectItem)spTrackPeopleUnit.getSelectedItem();

                //负责人
                SelectItem ponsibleThoseItem = (SelectItem)spResponsibleThose.getSelectedItem();
                threeFix.setFollingPersonId(spTrackPeopleItem.id);
                threeFix.setFollingPersonName(spTrackPeopleItem.toString());

                threeFix.setFollingTeamId(spTrackPeopleUnitItem.id);
                threeFix.setFollingTeamName(spTrackPeopleUnitItem.toString());
                //addThreeFixAndConfirm(threeFix);
                Utils.showLongToast(FiveDecisionsActivity.this, "下达参数负责人接口未提供:");


                //返回finish 测试
                setResult(RESULT_OK);
                finish();
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

    //获取部门/队组成员
    private void getCollieryTeam() {
        RequestParams params = new RequestParams();
        netClient.post(Constants.GET_COLLIERYTEAM, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "获取部门/队组成员返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    final List<CollieryTeam> collieryTeams = JSONArray.parseArray(data, CollieryTeam.class);
                    final List<SelectItem> selectItems = new ArrayList<>();
                    int collieryTeamsint = 0;
                    for (int i = 0; i < collieryTeams.size(); i++) {
                        SelectItem selectItem = new SelectItem();
                        selectItem.name = collieryTeams.get(i).getTeamName().replaceAll("&nbsp;","   ");
                        selectItem.id = collieryTeams.get(i).getId();
                        if(collieryTeams.get(i).getId().equals(threeFix.getTeamGroupCode())){
                            collieryTeamsint = i;
                        }
                        selectItems.add(selectItem);
                    }
                    spDepartmentAdapter = SpinnerAdapter.createFromResource(FiveDecisionsActivity.this, selectItems, Gravity.CENTER_VERTICAL|Gravity.LEFT);
                    setUpSpinner(spDepartment, spDepartmentAdapter);
                    spDepartmentAdapter.notifyDataSetChanged();
                    spDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                            spDepartmentAdapter.setSelectedPostion(position);
                            getResponsibleThose(selectItems.get(position).getId());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                    spDepartment.setSelection(collieryTeamsint);
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "获取部门/队组成员返回错误信息：" + content);
                Utils.showLongToast(FiveDecisionsActivity.this, content);
            }
        });
    }

    //跟踪人单位查询
    private void getspTrackPeopleUnit() {
        RequestParams params = new RequestParams();
        netClient.post(Constants.GET_COLLIERYTEAM, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "获取部门/队组成员返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    List<CollieryTeam> collieryTeams = JSONArray.parseArray(data, CollieryTeam.class);
                    final List<SelectItem> selectItems = new ArrayList<>();
                    int collieryTeamsint = 0;
                    for (int i = 0; i < collieryTeams.size(); i++) {
                        SelectItem selectItem = new SelectItem();
                        selectItem.name = collieryTeams.get(i).getTeamName().replaceAll("&nbsp;","   ");
                        selectItem.id = collieryTeams.get(i).getId();
                        if(collieryTeams.get(i).getId().equals(threeFix.getFollingTeamId())){
                            collieryTeamsint = i;
                        }
                        selectItems.add(selectItem);
                    }
                    spTrackPeopleUnitAdapter = SpinnerAdapter.createFromResource(FiveDecisionsActivity.this, selectItems, Gravity.CENTER_VERTICAL|Gravity.LEFT);
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
                    spTrackPeopleUnit.setSelection(collieryTeamsint);
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "获取部门/队组成员返回错误信息：" + content);
                Utils.showLongToast(FiveDecisionsActivity.this, content);
            }
        });
    }

    //整改负责人查询
    private void getResponsibleThose(String teamId) {
        RequestParams params = new RequestParams();
        params.put("teamId",teamId);
        netClient.post(Constants.GET_EMPLOYEELISTBYTEAMID, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "整改负责人查询返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    Log.i(TAG, "collieryTeams查询返回数据：" + data);
                    List<UserInfo> userInfoList = JSONArray.parseArray(data, UserInfo.class);
                    Log.i(TAG, "collieryTeams查询返回数据：" + userInfoList);
                    List<SelectItem> selectItems = new ArrayList<>();
                    int collieryTeamsint = 0;
                    for (int i = 0; i < userInfoList.size(); i++) {
                        SelectItem selectItem = new SelectItem();
                        selectItem.name = userInfoList.get(i).getRealName();
                        selectItem.id = userInfoList.get(i).getId();
                        if(userInfoList.get(i).getId().equals(threeFix.getEmployeeId())){
                            collieryTeamsint = i;
                        }
                        selectItems.add(selectItem);
                    }
                    spResponsibleThoseAdapter = SpinnerAdapter.createFromResource(FiveDecisionsActivity.this, selectItems, Gravity.CENTER_VERTICAL|Gravity.LEFT);
                    setUpSpinnerchild(spResponsibleThose, spResponsibleThoseAdapter);
                    spResponsibleThoseAdapter.notifyDataSetChanged();
                    spResponsibleThose.setSelection(collieryTeamsint);
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "整改负责人查询返回错误信息：" + content);
                Utils.showLongToast(FiveDecisionsActivity.this, content);
            }
        });
    }

    //跟踪人查询
    private void getTrackPeople(String teamId) {
        RequestParams params = new RequestParams();
        params.put("teamId",teamId);
        netClient.post(Constants.GET_EMPLOYEELISTBYTEAMID, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "跟踪人查询返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    Log.i(TAG, "onMySuccess: ");
                    List<UserInfo> collieryTeams = JSONArray.parseArray(data, UserInfo.class);
                    List<SelectItem> selectItems = new ArrayList<>();
                    int collieryTeamsint = 0;
                    for (int i = 0; i < collieryTeams.size(); i++) {
                        Log.i(TAG, "realname：" + collieryTeams.get(i).getRealName());
                        Log.i(TAG, "id：" + collieryTeams.get(i).getId());
                        SelectItem selectItem = new SelectItem();
                        selectItem.name = collieryTeams.get(i).getRealName();
                        selectItem.id = collieryTeams.get(i).getId();
                        if(collieryTeams.get(i).getId().equals(threeFix.getEmployeeId())){
                            collieryTeamsint = i;
                        }
                        selectItems.add(selectItem);
                    }
                    spTrackPeopleAdapter = SpinnerAdapter.createFromResource(FiveDecisionsActivity.this, selectItems, Gravity.CENTER_VERTICAL|Gravity.LEFT);
                    setUpSpinnerchild(spTrackPeople, spTrackPeopleAdapter);
                    spTrackPeopleAdapter.notifyDataSetChanged();
                    spTrackPeople.setSelection(collieryTeamsint);
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "跟踪人查询返回错误信息：" + content);
                Utils.showLongToast(FiveDecisionsActivity.this, content);
            }
        });
    }


    //隐患下达
    private void addThreeFixAndConfirm(ThreeFix threeFix) {
        RequestParams params = new RequestParams();
        String threeFixStr = JSON.toJSONString(threeFix);
        Log.i(TAG, "addHiddenDanger: 隐患下达修改="+threeFixStr);
        params.put("threeFixRecord",threeFixStr);
        netClient.post(Constants.ADD_THREEFIXANDCONFIRM, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "隐患下达返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    Utils.showLongToast(FiveDecisionsActivity.this, "隐患下达成功！");
                    finish();
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "隐患下达返回错误信息：" + content);
                Utils.showLongToast(FiveDecisionsActivity.this, content);
            }
        });
    }
}
