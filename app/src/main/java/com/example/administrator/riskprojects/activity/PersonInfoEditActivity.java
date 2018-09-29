package com.example.administrator.riskprojects.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.riskprojects.BaseActivity;
import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.bean.UserInfo;
import com.example.administrator.riskprojects.tools.UserUtils;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonInfoEditActivity extends BaseActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info_edit);
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
             //提交
                //成功后
                /**
                 *   mEtPhone.setEnabled(true);
                 *   mEtName.setEnabled(true);
                 *    mLlBottom.setVisibility(View.GONE);
                 */
            }
        });
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
