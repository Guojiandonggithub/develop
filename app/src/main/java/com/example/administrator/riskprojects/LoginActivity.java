package com.example.administrator.riskprojects;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.administrator.riskprojects.activity.Data;
import com.example.administrator.riskprojects.activity.MainActivity;
import com.example.administrator.riskprojects.common.NetUtil;
import com.example.administrator.riskprojects.dialog.FlippingLoadingDialog;
import com.example.administrator.riskprojects.net.BaseJsonRes;
import com.example.administrator.riskprojects.net.NetClient;
import com.example.administrator.riskprojects.tools.Constants;
import com.example.administrator.riskprojects.tools.UserUtils;
import com.example.administrator.riskprojects.tools.Utils;
import com.juns.health.net.loopj.android.http.RequestParams;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class LoginActivity extends BaseActivity implements View.OnClickListener, EasyPermissions.PermissionCallbacks{
    private static final String TAG = "LoginActivity";
    protected NetClient netClient;
    protected FlippingLoadingDialog mLoadingDialog;
    private EditText et_username, et_password;
    private Button btn_login;
    private RadioButton outer_net,intra_net;
    private RadioGroup radio_net;
    private String[] perms = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };
    private static final int num = 123;//用于验证获取的权

    @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_login);
                netClient = new NetClient(this);
                String jsondata = Utils.getValue(LoginActivity.this, Constants.UserInfo);
                if(jsondata.equals("")){
                    init();
                }else{
                    if (!NetUtil.checkNetWork(LoginActivity.this)) {
                        Intent intent = new Intent(LoginActivity.this,
                                MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.push_up_in,
                                R.anim.push_up_out);
                    }else{
                        String userName = Utils.getValue(LoginActivity.this, Constants.NAME);
                        String password = Utils.getValue(LoginActivity.this, Constants.PWD);
                        getLogin(userName,password);
                    }
        }

    }

    private void init(){
        initControl();
        setListener();
        requireSomePermission();
    }

    @AfterPermissionGranted(num)
    private void requireSomePermission() {
        if (!EasyPermissions.hasPermissions(this, perms)) {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "请求程序运行必要的权限",
                    num, perms);
        }
    }


    protected void initControl() {
            et_username = (EditText) findViewById(R.id.et_username);
            et_password = (EditText) findViewById(R.id.et_password);
            outer_net = (RadioButton) findViewById(R.id.outer_net);
            intra_net = (RadioButton) findViewById(R.id.intra_net);
            radio_net = (RadioGroup) findViewById(R.id.radio_net);
            btn_login = (Button) findViewById(R.id.btn_login);
            radio_net.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    selectRadioButton();
                }
            });
    }

    private void selectRadioButton(){
        RadioButton rb = (RadioButton)LoginActivity.this.findViewById(radio_net.getCheckedRadioButtonId());
        Utils.showLongToast(LoginActivity.this, rb.getText().toString());
        Data app = (Data)getApplication();
        if(rb.getText().equals("内网")){
            app.setIp(Constants.INNER_MAIN_ENGINE);
        }else{
            app.setIp(Constants.MAIN_ENGINE);
        }
    }

    protected void setListener() {
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                RadioButton rb = (RadioButton)LoginActivity.this.findViewById(radio_net.getCheckedRadioButtonId());
                if(rb.getText().equals("内网")){
                    Utils.showLongToast(LoginActivity.this, "内网地址未提供！");
                }else{
                    getLogin();
                }
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
            netClient.post(Data.getInstance().getIp()+Constants.Login_URL, params, new BaseJsonRes() {

                @Override
                public void onMySuccess(String data) {
                    getLoadingDialog("正在登录").dismiss();
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
                    //finish();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        finish();
        //如果曾经有勾选《拒绝后不再询问》，则会进入下面这个条件
        //建议做一个判断，判断用户是不是刚刚勾选的《拒绝后不再询问》，如果是，就不做下面这个判断，而只进行相应提示，这样就可以避免再一次弹框，影响用户体验
        //否则就是用户可能在之前曾经勾选过《拒绝后不再询问》，那就可以用下面这个判断，强制弹出一个对话框
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            //但是这个api有个问题，他会显示一个对话框，但是这个对话框，点空白区域是可以取消的，如果用户点了空白区域，你就没办法进行后续操作了
            new AppSettingsDialog.Builder(this).build().show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //EasyPermissions会有一个默认的请求码，根据这个请求码，就可以判断是不是从APP的设置界面过来的
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // Do something after user returned from app settings screen, like showing a Toast.
            //在这儿，你可以再对权限进行检查，从而给出提示，或进行下一步操作
            Toast.makeText(this, "从设置中返回", Toast.LENGTH_SHORT).show();
            requireSomePermission();
        }
    }
}
