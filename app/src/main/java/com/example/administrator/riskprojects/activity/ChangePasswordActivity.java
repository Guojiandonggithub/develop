package com.example.administrator.riskprojects.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.riskprojects.BaseActivity;
import com.example.administrator.riskprojects.R;

public class ChangePasswordActivity extends BaseActivity {

    private TextView mTxtLeft;
    private ImageView mImgLeft;
    private TextView mTxtTitle;
    private ImageView mImgRight;
    private TextView mTxtRight;
    private EditText mEtOldPassword;
    private EditText mEtNewPassword;
    private EditText mEtNewPasswordAgain;
    private Button mBtOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
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

        if (TextUtils.equals(mEtNewPassword.getText().toString(),mEtNewPasswordAgain.getText().toString())) {
            Toast.makeText(this,"两次密码输入不一致", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
