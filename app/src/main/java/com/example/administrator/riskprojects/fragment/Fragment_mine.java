package com.example.administrator.riskprojects.fragment;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.riskprojects.LoginActivity;
import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.activity.BleSdkActivity;
import com.example.administrator.riskprojects.activity.ChangePasswordActivity;
import com.example.administrator.riskprojects.activity.HiddenDangeTrackingDetailListActivity;
import com.example.administrator.riskprojects.activity.HiddenRiskRecordDetailActivity;
import com.example.administrator.riskprojects.activity.PersonInfoEditActivity;
import com.example.administrator.riskprojects.bean.UserInfo;
import com.example.administrator.riskprojects.tools.UserUtils;
import com.example.administrator.riskprojects.util.UpdateVersionUtil;
import com.example.administrator.riskprojects.view.MyAlertDialog;

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
    private TextView mTvBleSdk;

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
        mTvBleSdk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ctx, BleSdkActivity.class));
            }
        });
        mTvVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UpdateVersionUtil().versionUpdata(getActivity(),true);
            }
        });
        mTvLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyAlertDialog alertDialog = new MyAlertDialog(getActivity()
                        , new MyAlertDialog.DialogListener() {
                    @Override
                    public void affirm() {
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().onBackPressed();//销毁自己
                    }

                    @Override
                    public void cancel() {

                    }
                }, "你确定要退出登录吗？");
                alertDialog.show();
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
        mTvBleSdk = layout.findViewById(R.id.tv_ble_sdk);
    }

    private void initdata() {
        UserInfo userInfo = UserUtils.getUserModel(getActivity());
        mTvName.setText(userInfo.getUserName());
        mTvPhone.setText(userInfo.getPhone());
        mTvDepartment.setText(userInfo.getDepartmentName());
        mTvOffice.setText(userInfo.getPname());
    }
}
