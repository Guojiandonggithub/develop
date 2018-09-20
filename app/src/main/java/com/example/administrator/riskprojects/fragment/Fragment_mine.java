package com.example.administrator.riskprojects.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.activity.ChangePasswordActivity;
import com.example.administrator.riskprojects.activity.HiddenRiskRecordDetailActivity;
import com.example.administrator.riskprojects.activity.PersonInfoEditActivity;
import com.example.administrator.riskprojects.bean.UserInfo;
import com.example.administrator.riskprojects.tools.UserUtils;

import de.hdodenhof.circleimageview.CircleImageView;

public class Fragment_mine extends Fragment {
    // 我的
    private Activity ctx;
    private View layout;
    private CircleImageView mIvHead;
    private TextView mTvName;
    private TextView mTvDepartment;
    private TextView mTvPhone;
    private TextView mTvOffice;
    private TextView mTvChangePassword;
    private TextView mTvVersion;
    private TextView mTvLogOut;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (layout == null) {
            ctx = this.getActivity();
            layout = ctx.getLayoutInflater().inflate(R.layout.fragment_mine,
                    null);

        } else {
            ViewGroup parent = (ViewGroup) layout.getParent();
            if (parent != null) {
                parent.removeView(layout);
            }
        }
        initView(layout);
        setView();
        initdata();
        return layout;
    }

    private void setView() {
        mTvChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ctx, ChangePasswordActivity.class));
            }
        });
        mIvHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ctx, PersonInfoEditActivity.class));
            }
        });
    }

    private void initView(View layout) {
        mIvHead = layout.findViewById(R.id.iv_head);
        mTvName = layout.findViewById(R.id.tv_name);
        mTvDepartment = layout.findViewById(R.id.tv_department);
        mTvPhone = layout.findViewById(R.id.tv_phone);
        mTvOffice = layout.findViewById(R.id.tv_office);
        mTvChangePassword = layout.findViewById(R.id.tv_change_password);
        mTvVersion = layout.findViewById(R.id.tv_version);
        mTvLogOut = layout.findViewById(R.id.tv_log_out);
    }

    private void initdata() {
        UserInfo userInfo = UserUtils.getUserModel(getActivity());
        mTvName.setText(userInfo.getUserName());
        mTvPhone.setText(userInfo.getPhone());
        mTvDepartment.setText(userInfo.getDepartmentName());
        mTvOffice.setText(userInfo.getPname());
    }
}
