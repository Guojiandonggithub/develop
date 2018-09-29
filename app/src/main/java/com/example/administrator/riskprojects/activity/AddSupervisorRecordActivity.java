package com.example.administrator.riskprojects.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.administrator.riskprojects.BaseActivity;
import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.bean.HiddenDangerRecord;
import com.example.administrator.riskprojects.bean.HiddenFollingRecord;
import com.example.administrator.riskprojects.bean.SupervisionRecord;
import com.example.administrator.riskprojects.net.BaseJsonRes;
import com.example.administrator.riskprojects.net.NetClient;
import com.example.administrator.riskprojects.tools.Constants;
import com.example.administrator.riskprojects.tools.Utils;
import com.juns.health.net.loopj.android.http.RequestParams;

public class AddSupervisorRecordActivity extends BaseActivity {
    private static final String TAG = "AddSupervisorRecordActi";
    NetClient netClient;
    private TextView txtLeft;
    private ImageView imgLeft;
    private TextView txtTitle;
    private ImageView imgRight;
    private TextView txtRight;
    private EditText etDepartment;
    private EditText etOverseePeople;
    private CardView cvSelectDate;
    private TextView tvDate;
    private EditText etContent;
    private LinearLayoutCompat llBottom;
    private TextView tvOk;
    private String hiddenDangerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_supervisor_record);
        netClient = new NetClient(AddSupervisorRecordActivity.this);
        initView();
        setView();
    }

    private void setView() {
        txtTitle.setText("添加督办记录");
        cvSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerActivity.startPickDate(AddSupervisorRecordActivity.this, AddSupervisorRecordActivity.this);
            }
        });
        tvOk.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(checkInput()){
                    SupervisionRecord supervisionRecord = getSupervision();
                    addSupervisionRecord(supervisionRecord);
                }
            }
        });
    }

    private void initView() {
        txtLeft = findViewById(R.id.txt_left);
        imgLeft = findViewById(R.id.img_left);
        txtTitle = findViewById(R.id.txt_title);
        imgRight = findViewById(R.id.img_right);
        txtRight = findViewById(R.id.txt_right);
        etDepartment = findViewById(R.id.et_department);
        etOverseePeople = findViewById(R.id.et_oversee_people);
        cvSelectDate = findViewById(R.id.cv_select_date);
        tvDate = findViewById(R.id.tv_date);
        etContent = findViewById(R.id.et_content);
        llBottom = findViewById(R.id.ll_bottom);
        tvOk = findViewById(R.id.tv_ok);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DatePickerActivity.REQUEST && resultCode == RESULT_OK) {
            String date = data.getStringExtra(DatePickerActivity.DATE);
            tvDate.setText(date);
        }
    }

    //添加督办
    private void addSupervisionRecord(SupervisionRecord supervisionRecord) {
        RequestParams params = new RequestParams();
        String supervisionRecordStr = JSON.toJSONString(supervisionRecord);
        params.put("supervisionRecordJsonData",supervisionRecordStr);
        netClient.post(Data.getInstance().getIp()+Constants.ADD_SUPERVISIONRECORD, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "添加督办返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    Utils.showLongToast(AddSupervisorRecordActivity.this, "督办记录添加成功！");
                    Intent intent = new Intent(AddSupervisorRecordActivity.this, SuperviseRecordManagementActivity.class);
                    intent.putExtra("hiddenDangerId",hiddenDangerId);
                    startActivity(intent);
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "添加督办返回错误信息：" + content);
                Utils.showLongToast(AddSupervisorRecordActivity.this, content);
                return;
            }
        });
    }

    private SupervisionRecord  getSupervision(){
        hiddenDangerId = getIntent().getStringExtra("id");
        SupervisionRecord supervisionRecord = new SupervisionRecord();
        supervisionRecord.setHiddenDangerId(hiddenDangerId);
        supervisionRecord.setSupervisionRecord(etContent.getText().toString());
        supervisionRecord.setSupervisionTime(tvDate.getText().toString());
        supervisionRecord.setSupervisionRecordDeptName(etDepartment.getText().toString());
        supervisionRecord.setSupervisionRecordPersonName(etOverseePeople.getText().toString());
        return supervisionRecord;
    }

    //检查输入
    private boolean checkInput() {
        if (TextUtils.isEmpty(etContent.getText().toString())) {
            Utils.showLongToast(AddSupervisorRecordActivity.this, "督办记录不能为空!");
            return false;
        }

        if (TextUtils.isEmpty(tvDate.getText().toString())) {
            Utils.showLongToast(AddSupervisorRecordActivity.this, "记录时间不能为空!");
            return false;
        }
        if (TextUtils.isEmpty(etDepartment.getText().toString())) {
            Utils.showLongToast(AddSupervisorRecordActivity.this, "部门不能为空!");
            return false;
        }
        if (TextUtils.isEmpty(etOverseePeople.getText().toString())) {
            Utils.showLongToast(AddSupervisorRecordActivity.this, "督办人不能为空!");
            return false;
        }
        return true;
    }

}
