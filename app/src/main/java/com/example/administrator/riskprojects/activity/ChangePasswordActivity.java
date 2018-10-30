package com.example.administrator.riskprojects.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.example.administrator.riskprojects.Adpter.HomeHiddenDangerAdapter;
import com.example.administrator.riskprojects.BaseActivity;
import com.example.administrator.riskprojects.LoginActivity;
import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.bean.HomeHiddenRecord;
import com.example.administrator.riskprojects.net.BaseJsonRes;
import com.example.administrator.riskprojects.net.NetClient;
import com.example.administrator.riskprojects.tools.Constants;
import com.example.administrator.riskprojects.tools.UserUtils;
import com.example.administrator.riskprojects.tools.Utils;
import com.example.administrator.riskprojects.util.DensityUtil;
import com.example.administrator.riskprojects.view.MyDecoration;
import com.juns.health.net.loopj.android.http.RequestParams;

import java.util.List;

public class ChangePasswordActivity extends BaseActivity {
    private static final String TAG = "ChangePasswordActivity";
    private TextView mTxtLeft;
    private ImageView mImgLeft;
    private TextView mTxtTitle;
    private ImageView mImgRight;
    private TextView mTxtRight;
    private EditText mEtOldPassword;
    private EditText mEtNewPassword;
    private EditText mEtNewPasswordAgain;
    private Button mBtOk;
    protected NetClient netClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        netClient = new NetClient(ChangePasswordActivity.this);
        initView();
        setView();
    }


    private void initView() {
        mTxtLeft = findViewById(R.id.txt_left);
        mImgLeft = findViewById(R.id.img_left);
        mTxtTitle = findViewById(R.id.txt_title);
        mImgRight = findViewById(R.id.img_right);
        mTxtRight = findViewById(R.id.txt_right);
        mEtOldPassword = findViewById(R.id.et_old_password);
        mEtNewPassword = findViewById(R.id.et_new_password);
        mEtNewPasswordAgain = findViewById(R.id.et_new_password_again);
        mBtOk = findViewById(R.id.bt_ok);

    }


    private void setView() {
        mTxtTitle.setText(R.string.change_password);
        mBtOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInput()) {
                    chenge();
                }
            }
        });
    }
    //修改密码
    private void chenge() {
        RequestParams params = new RequestParams();
        params.put("userId", UserUtils.getUserID(ChangePasswordActivity.this));
        params.put("password",mEtOldPassword.getText().toString());
        params.put("newPwd",mEtNewPassword.getText().toString());
        netClient.post(Data.getInstance().getIp()+Constants.UPDATE_PWD, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "主页隐患数据返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "获取隐患数据返回错误信息：" + content);
                Utils.showShortToast(ChangePasswordActivity.this, content);
            }
        });

    }
    //检查输入
    private boolean checkInput() {
        if (TextUtils.isEmpty(mEtOldPassword.getText().toString())) {
            Toast.makeText(this, mEtOldPassword.getHint(), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(mEtNewPassword.getText().toString())) {
            Toast.makeText(this, mEtNewPassword.getHint(), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(mEtNewPasswordAgain.getText().toString())) {
            Toast.makeText(this, mEtNewPasswordAgain.getHint(), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!TextUtils.equals(mEtNewPassword.getText().toString(),mEtNewPasswordAgain.getText().toString())) {
            Toast.makeText(this,"两次密码输入不一致", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
