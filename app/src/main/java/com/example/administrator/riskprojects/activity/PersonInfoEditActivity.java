package com.example.administrator.riskprojects.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.administrator.riskprojects.BaseActivity;
import com.example.administrator.riskprojects.LoginActivity;
import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.bean.GpHiddenDanger;
import com.example.administrator.riskprojects.bean.SelectItem;
import com.example.administrator.riskprojects.bean.UserInfo;
import com.example.administrator.riskprojects.net.BaseJsonRes;
import com.example.administrator.riskprojects.net.NetClient;
import com.example.administrator.riskprojects.tools.Constants;
import com.example.administrator.riskprojects.tools.UserUtils;
import com.example.administrator.riskprojects.tools.Utils;
import com.juns.health.net.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonInfoEditActivity extends BaseActivity {
    private static final String TAG = "PersonInfoEditActivity";
    private TextView mTxtLeft;
    private ImageView mImgLeft;
    private TextView mTxtTitle;
    private ImageView mImgRight;
    private TextView mTxtRight;
    private CircleImageView mIvHead;
    private EditText mEtName;
    private EditText mEtPhone;
    private EditText mEtDepartment;
    private EditText mEtPosition;
    private LinearLayoutCompat mLlBottom;
    private TextView mTvOk;
    protected NetClient netClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info_edit);
        netClient = new NetClient(PersonInfoEditActivity.this);
        initView();
        setView();
    }

    private void setView() {
        mTxtTitle.setText(R.string.person_info);
        mImgRight.setVisibility(View.VISIBLE);
        mEtName.setEnabled(false);
        mEtPhone.setEnabled(false);
        mEtDepartment.setEnabled(false);
        mEtPosition.setEnabled(false);
        UserInfo userinfo = UserUtils.getUserModel(PersonInfoEditActivity.this);
        mEtName.setText(userinfo.getRealName());
        mEtPhone.setText(userinfo.getPhone());
        mEtDepartment.setText(userinfo.getDepartmentName());
        mEtPosition.setText(userinfo.getPname());
        mImgRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEtPhone.setEnabled(true);
                mEtName.setEnabled(true);
                mLlBottom.setVisibility(View.VISIBLE);
            }
        });
        mTvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkInput()){
                    updatePersonInfo();
                }
            }
        });
    }

    //修改用户信息
    private void updatePersonInfo() {
        RequestParams params = new RequestParams();
        UserInfo userInfo = new UserInfo();
        userInfo.setId(UserUtils.getUserID(PersonInfoEditActivity.this));
        userInfo.setRealName(mEtName.getText().toString());
        userInfo.setPhone(mEtPhone.getText().toString());
        String employeeJsonDataStr = JSON.toJSONString(userInfo);
        Log.i(TAG, "gpHiddenDanger: 修改用户信息参数="+employeeJsonDataStr);
        params.put("employeeJsonData",employeeJsonDataStr);

        netClient.post(Data.getInstance().getIp()+ Constants.UPDATE_PERSONINFO, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "修改用户信息返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    UserInfo userInfos = UserUtils.getUserModel(PersonInfoEditActivity.this);
                    userInfos.setRealName(mEtName.getText().toString());
                    userInfos.setPhone(mEtPhone.getText().toString());
                    List<UserInfo> userInfoList = new ArrayList();
                    userInfoList.add(userInfos);
                    String userStr = JSONArray.toJSONString(userInfoList);
                    Utils.putValue(PersonInfoEditActivity.this, Constants.UserInfo, userStr);
                    Intent intent = new Intent();
                    intent.putExtra("phone",mEtPhone.getText().toString());
                    setResult(RESULT_OK, intent);
                    Utils.showLongToast(PersonInfoEditActivity.this, "用户数据修改成功");
                    finish();
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "修改用户信息返回错误信息：" + content);
                Utils.showLongToast(PersonInfoEditActivity.this, content);
            }
        });
    }

    //检查输入
    private boolean checkInput() {
        if (TextUtils.isEmpty(mEtName.getText().toString())) {
            Utils.showLongToast(PersonInfoEditActivity.this, "姓名不能为空!");
            return false;
        }

        if (TextUtils.isEmpty(mEtPhone.getText().toString())) {
            Utils.showLongToast(PersonInfoEditActivity.this, "手机号不能为空!");
            return false;
        }
        return true;
    }

    private void initView() {
        mTxtLeft = findViewById(R.id.txt_left);
        mImgLeft = findViewById(R.id.img_left);
        mTxtTitle = findViewById(R.id.txt_title);
        mImgRight = findViewById(R.id.img_right);
        mTxtRight = findViewById(R.id.txt_right);
        mIvHead = findViewById(R.id.iv_head);
        mEtName = findViewById(R.id.et_name);
        mEtPhone = findViewById(R.id.et_phone);
        mEtDepartment = findViewById(R.id.et_department);
        mEtPosition = findViewById(R.id.et_position);
        mLlBottom = findViewById(R.id.ll_bottom);
        mTvOk = findViewById(R.id.tv_ok);
    }
}
