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
import com.example.administrator.riskprojects.Adpter.SpinnerAdapter;
import com.example.administrator.riskprojects.BaseActivity;
import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.bean.CollieryTeam;
import com.example.administrator.riskprojects.bean.SelectItem;
import com.example.administrator.riskprojects.bean.UserInfo;
import com.example.administrator.riskprojects.common.NetUtil;
import com.example.administrator.riskprojects.dialog.FlippingLoadingDialog;
import com.example.administrator.riskprojects.net.BaseJsonRes;
import com.example.administrator.riskprojects.net.NetClient;
import com.example.administrator.riskprojects.tools.Constants;
import com.example.administrator.riskprojects.tools.UserUtils;
import com.example.administrator.riskprojects.tools.Utils;
import com.example.administrator.riskprojects.util.DensityUtil;
import com.juns.health.net.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManagementControlActivity extends BaseActivity {
    private static final String TAG = "ManagementControlActivi";
    private TextView txtLeft;
    private ImageView imgLeft;
    private TextView txtTitle;
    private ImageView imgRight;
    private TextView txtRight;
    private CardView cvSelectDate;
    private TextView tvDate;
    private EditText etManagement;
    private EditText etApproved;
    private EditText etConsider;
    private LinearLayoutCompat llBottom;
    private TextView tvOk;
    protected NetClient netClient;
    protected FlippingLoadingDialog mLoadingDialog;
    private Spinner spTrackPeople;
    private SpinnerAdapter spTrackPeopleAdapter;
    private Spinner spTrackPeopleUnit;
    private SpinnerAdapter spTrackPeopleUnitAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_control);
        netClient = new NetClient(ManagementControlActivity.this);
        initView();
        setView();
        getspTrackPeopleUnit();
    }

    private void setView() {
        Bundle  bundle = getIntent().getBundleExtra("threeBund");
        txtTitle.setText("落实跟踪记录");
        cvSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerActivity.startPickDate(ManagementControlActivity.this, ManagementControlActivity.this);
            }
        });
    }

    private void initView() {
        txtLeft = findViewById(R.id.txt_left);
        imgLeft = findViewById(R.id.img_left);
        txtTitle = findViewById(R.id.txt_title);
        imgRight = findViewById(R.id.img_right);
        txtRight = findViewById(R.id.txt_right);
        cvSelectDate = findViewById(R.id.cv_select_date);
        tvDate = findViewById(R.id.tv_date);
        etManagement = findViewById(R.id.et_management);
        etApproved = findViewById(R.id.et_approved);
        etConsider = findViewById(R.id.et_consider);
        tvDate.setSelected(true);
        llBottom = findViewById(R.id.ll_bottom);
        tvOk = findViewById(R.id.tv_ok);
        spTrackPeople = findViewById(R.id.sp_track_people);
        spTrackPeopleUnit = findViewById(R.id.sp_track_people_unit);
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*String dateStr = tvDate.getText().toString().replaceAll("\\.", "-");
                Calendar cal = Calendar.getInstance();
                //当前时：HOUR_OF_DAY-24小时制；HOUR-12小时制
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                //当前分
                int minute = cal.get(Calendar.MINUTE);
                //当前秒
                int second = cal.get(Calendar.SECOND);
                dateStr = dateStr.split(" ")[0] + " " + hour + ":" + +minute + ":" + second;*/
                Map<String,String> map = new HashMap<>();
                Intent ins = getIntent();
                String kuangQuId = ins.getStringExtra("kuangQuId");
                String safeCheckId = ins.getStringExtra("safeCheckId");
                map.put("management",etManagement.getText().toString());
                map.put("approvedPersonName",etApproved.getText().toString());
                map.put("consider",etConsider.getText().toString());
                map.put("addTime",tvDate.getText().toString());
                //责任人
                SelectItem spTrackPeopleItem = (SelectItem)spTrackPeople.getSelectedItem();
                //责任人单位
                SelectItem spTrackPeopleUnitItem = (SelectItem)spTrackPeopleUnit.getSelectedItem();
                map.put("conCollieryId",kuangQuId);
                map.put("safeCheckId",safeCheckId);
                map.put("dutyPerId",spTrackPeopleItem.getId());
                map.put("dutyPerName",spTrackPeopleItem.toString());
                map.put("deptId",spTrackPeopleUnitItem.getId());
                map.put("deptName",spTrackPeopleUnitItem.toString());
                if(checkInput(map)){
                    addManagementControl(map);
                }
            }
        });

    }

    //检查输入
    private boolean checkInput(Map<String,String> map) {
        if (TextUtils.isEmpty(map.get("management"))) {
            Utils.showShortToast(ManagementControlActivity.this, "管理流程不能为空!");
            return false;
        }

        if (TextUtils.isEmpty(map.get("approvedPersonName"))) {
            Utils.showShortToast(ManagementControlActivity.this, "审批人不能为空!");
            return false;
        }

        if (TextUtils.isEmpty(map.get("consider"))) {
            Utils.showShortToast(ManagementControlActivity.this, "审议内容不能为空!");
            return false;
        }

        if (TextUtils.isEmpty(map.get("addTime"))) {
            Utils.showShortToast(ManagementControlActivity.this, "审议时间不能为空!");
            return false;
        }
        if (TextUtils.isEmpty(map.get("dutyPerId"))) {
            Utils.showShortToast(ManagementControlActivity.this, "责任人不能为空!");
            return false;
        }
        if (TextUtils.isEmpty(map.get("deptId"))) {
            Utils.showShortToast(ManagementControlActivity.this, "责任单位不能为空!");
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DatePickerActivity.REQUEST && resultCode == RESULT_OK) {
            Calendar cal = Calendar.getInstance();
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            //当前分
            int minute = cal.get(Calendar.MINUTE);
            //当前秒
            int second = cal.get(Calendar.SECOND);
            String dataStrs = data.getStringExtra(DatePickerActivity.DATE);
            dataStrs = dataStrs.split(" ")[0] + " " + hour + ":" + +minute + ":" + second;
            tvDate.setText(dataStrs);
        }
    }

    //落实跟踪记录
    private void addManagementControl(Map<String,String> map) {
        tvOk.setClickable(false);
        RequestParams params = new RequestParams();
        String managementControlJson = JSON.toJSONString(map);
        Log.i(TAG, "addManagementControl: 落实跟踪记录======"+managementControlJson);
        params.put("managementControlJson",managementControlJson);
        getLoadingDialog("正在连接服务器...  ").show();
        netClient.post(Data.getInstance().getIp()+Constants.ADD_MANAGEMENT_CONTROL, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                getLoadingDialog("正在连接服务器...  ").dismiss();
                Log.i(TAG, "落实跟踪记录返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    Utils.showShortToast(ManagementControlActivity.this, "落实跟踪记录成功！");
                    //返回finish 测试
                    //setResult(RESULT_OK);
                    finish();
                }

            }

            @Override
            public void onMyFailure(String content) {
                getLoadingDialog("正在连接服务器...  ").dismiss();
                Log.e(TAG, "落实跟踪记录返回错误信息：" + content);
                Utils.showShortToast(ManagementControlActivity.this, content);
                tvOk.setClickable(true);
            }
        });
    }

    public FlippingLoadingDialog getLoadingDialog(String msg) {
        if (mLoadingDialog == null)
            mLoadingDialog = new FlippingLoadingDialog(ManagementControlActivity.this, msg);
        return mLoadingDialog;
    }

    private void resultTrackPeople(String data){
        //if(!TextUtils.isEmpty(data)){
        Log.i(TAG, "onMySuccess: ");
        List<UserInfo> collieryTeams = JSONArray.parseArray(data, UserInfo.class);
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        int collieryTeamsint = 0;
        for (int i = 0; i < collieryTeams.size(); i++) {
            Log.i(TAG, "realname：" + collieryTeams.get(i).getRealName());
            Log.i(TAG, "id：" + collieryTeams.get(i).getId());
            SelectItem selectItem = new SelectItem();
            selectItem.name = collieryTeams.get(i).getRealName();
            selectItem.id = collieryTeams.get(i).getId();
            /*if(collieryTeams.get(i).getId().equals(threeFix.getEmployeeId())){
                collieryTeamsint = i;
            }*/
            selectItems.add(selectItem);
        }
        spTrackPeopleAdapter = SpinnerAdapter.createFromResource(ManagementControlActivity.this, selectItems, Gravity.CENTER_VERTICAL|Gravity.LEFT);
        setUpSpinnerchild(spTrackPeople, spTrackPeopleAdapter);
        spTrackPeopleAdapter.notifyDataSetChanged();
        spTrackPeople.setSelection(collieryTeamsint);
        //}else{

        //}
    }

    private void resultSpTrackPeople(String data){
        List<CollieryTeam> collieryTeams = JSONArray.parseArray(data, CollieryTeam.class);
        final List<SelectItem> selectItems = new ArrayList<SelectItem>();
        int collieryTeamsint = 0;
        for (int i = 0; i < collieryTeams.size(); i++) {
            SelectItem selectItem = new SelectItem();
            selectItem.name = collieryTeams.get(i).getTeamName().replaceAll("&nbsp;"," ");
            selectItem.id = collieryTeams.get(i).getId();
            /*if(collieryTeams.get(i).getId().equals(threeFix.getFollingTeamId())){
                collieryTeamsint = i;
            }*/
            selectItems.add(selectItem);
        }
        spTrackPeopleUnitAdapter = SpinnerAdapter.createFromResource(ManagementControlActivity.this, selectItems, Gravity.CENTER_VERTICAL|Gravity.LEFT);
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

    //跟踪人单位查询
    private void getspTrackPeopleUnit() {
        if (!NetUtil.checkNetWork(ManagementControlActivity.this)) {
            String jsondata = Utils.getValue(ManagementControlActivity.this, Constants.GET_COLLIERYTEAM);
            if("".equals(jsondata)){
                Utils.showShortToast(ManagementControlActivity.this, "没有联网，没有请求到数据");
            }else{
                resultSpTrackPeople(jsondata);
            }
        }else{
            RequestParams params = new RequestParams();
            params.put("employeeId", UserUtils.getUserID(ManagementControlActivity.this));
            netClient.post(Data.getInstance().getIp()+Constants.GET_COLLIERYTEAM, params, new BaseJsonRes() {

                @Override
                public void onMySuccess(String data) {
                    Log.i(TAG, "获取部门/队组成员返回数据：" + data);
                    if (!TextUtils.isEmpty(data)) {
                        resultSpTrackPeople(data);
                    }

                }

                @Override
                public void onMyFailure(String content) {
                    Log.e(TAG, "获取部门/队组成员返回错误信息：" + content);
                    Utils.showShortToast(ManagementControlActivity.this, content);
                }
            });
        }
    }

    //跟踪人查询
    private void getTrackPeople(String teamId) {
        if (!NetUtil.checkNetWork(ManagementControlActivity.this)) {
            String jsondata = Utils.getValue(ManagementControlActivity.this, Constants.EmployeeList);
            if("".equals(jsondata)){
                Utils.showShortToast(ManagementControlActivity.this, "没有联网，请到个人中心获取基础数据");
            }else{
                List<UserInfo> userinfoList = JSONArray.parseArray(jsondata,UserInfo.class);
                List<UserInfo> userinfos = new ArrayList<>();
                for(UserInfo userInfo:userinfoList){
                    if(teamId.equals(userInfo.getTeamId())){
                        userinfos.add(userInfo);
                    }
                }
                String userinfoStr = JSONArray.toJSONString(userinfos);
                resultTrackPeople(userinfoStr);
            }
        }else{
            RequestParams params = new RequestParams();
            params.put("teamId",teamId);
            netClient.post(Data.getInstance().getIp()+Constants.GET_EMPLOYEELISTBYTEAMID, params, new BaseJsonRes() {

                @Override
                public void onMySuccess(String data) {
                    Log.i(TAG, "跟踪人查询返回数据：" + data);
                    if (!TextUtils.isEmpty(data)) {
                        resultTrackPeople(data);
                    }
                }

                @Override
                public void onMyFailure(String content) {
                    Log.e(TAG, "跟踪人查询返回错误信息：" + content);
                    Utils.showShortToast(ManagementControlActivity.this, content);
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

}
