package com.example.administrator.riskprojects;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.administrator.riskprojects.activity.MainActivity;
import com.example.administrator.riskprojects.dialog.FlippingLoadingDialog;
import com.example.administrator.riskprojects.net.BaseJsonRes;
import com.example.administrator.riskprojects.net.NetClient;
import com.example.administrator.riskprojects.tools.Constants;
import com.example.administrator.riskprojects.tools.UserUtils;
import com.example.administrator.riskprojects.tools.Utils;
import com.juns.health.net.loopj.android.http.RequestParams;

public class LoginActivity extends BaseActivity implements View.OnClickListener{
    protected NetClient netClient;
    protected FlippingLoadingDialog mLoadingDialog;
    private EditText et_username, et_password;
    private Button btn_login;
    private RadioButton outer_net,intra_net;
    private RadioGroup radio_net;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        netClient = new NetClient(this);
        initControl();
        setListener();
    }


    protected void initControl() {
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        outer_net = (RadioButton) findViewById(R.id.outer_net);
        intra_net = (RadioButton) findViewById(R.id.intra_net);
        radio_net = (RadioGroup) findViewById(R.id.radio_net);
        btn_login = (Button) findViewById(R.id.btn_login);
    }

    protected void setListener() {
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                getLogin();
                break;
            default:
                break;
        }
    }

    private void getLogin() {
        String userName = et_username.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        getLogin(userName, password);
    }

    private void getLogin(final String userName, final String password) {
        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password)) {
            RequestParams params = new RequestParams();
            params.put("name", userName);
            params.put("password", password);
            getLoadingDialog("正在登录...  ").show();
            netClient.post(Constants.Login_URL, params, new BaseJsonRes() {

                @Override
                public void onMySuccess(String data) {
                    Utils.showLongToast(LoginActivity.this, "登录返回数据"+ data);

                    Utils.putValue(LoginActivity.this, Constants.UserInfo, data);
                    Utils.putBooleanValue(LoginActivity.this,
                            Constants.LoginState, true);
                    UserUtils.getUserModel(LoginActivity.this);
                    Utils.putValue(LoginActivity.this, Constants.NAME, userName);
                    Utils.putValue(LoginActivity.this, Constants.PWD,
                            password);
                    Intent intent = new Intent(LoginActivity.this,
                            MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.push_up_in,
                            R.anim.push_up_out);
                    finish();
                }

                @Override
                public void onMyFailure(String content) {
                    getLoadingDialog("正在登录").dismiss();
                    Utils.showLongToast(LoginActivity.this, content);
                }
            });
        } else {
            Utils.showLongToast(LoginActivity.this, "请填写账号或密码！");
        }
    }

    public FlippingLoadingDialog getLoadingDialog(String msg) {
        if (mLoadingDialog == null)
            mLoadingDialog = new FlippingLoadingDialog(this, msg);
        return mLoadingDialog;
    }
}
