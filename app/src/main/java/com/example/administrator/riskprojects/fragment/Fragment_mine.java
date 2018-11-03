package com.example.administrator.riskprojects.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.riskprojects.LoginActivity;
import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.activity.ChangePasswordActivity;
import com.example.administrator.riskprojects.activity.Data;
import com.example.administrator.riskprojects.activity.PersonInfoEditActivity;
import com.example.administrator.riskprojects.bean.UserInfo;
import com.example.administrator.riskprojects.net.BaseJsonRes;
import com.example.administrator.riskprojects.net.NetClient;
import com.example.administrator.riskprojects.tools.Constants;
import com.example.administrator.riskprojects.tools.UserUtils;
import com.example.administrator.riskprojects.tools.Utils;
import com.example.administrator.riskprojects.util.UpdateVersionUtil;
import com.example.administrator.riskprojects.view.MyAlertDialog;
import com.juns.health.net.loopj.android.http.RequestParams;

import de.hdodenhof.circleimageview.CircleImageView;

public class Fragment_mine extends Fragment {
    private static final String TAG = "Fragment_mine";
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
    private TextView mTvDataUpdate;
    protected NetClient netClient;

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
        netClient = new NetClient(getActivity());
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
                startActivityForResult(new Intent(ctx, PersonInfoEditActivity.class), Integer.parseInt(Constants.PAGE));
            }
        });
        mTvDataUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.showShortToast(getActivity(), "请求数据开始");

                getCollieryTeam();
                getSpecialty();
                getRiskGrade();
                getClassNumber();
                getArea();
                getHiddenType();
                getHiddenGrade();
                getHiddenYHGSLX();
                getCheckContent();
                getEmployeeList();
                Utils.showShortToast(getActivity(), "请求数据完成");
            }
        });
        mTvVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UpdateVersionUtil().versionUpdata(getActivity(), true);
            }
        });
        mTvLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyAlertDialog alertDialog = new MyAlertDialog(getActivity()
                        , new MyAlertDialog.DialogListener() {
                    @Override
                    public void affirm() {
                        Utils.putValue(getActivity(), Constants.UserInfo, "");
                        Data.getInstance().deleteAlias();
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
        mTvDataUpdate = layout.findViewById(R.id.tv_date_update);
    }

    private void initdata() {
        UserInfo userInfo = UserUtils.getUserModel(getActivity());
        mTvName.setText(userInfo.getUserName());
        mTvPhone.setText(userInfo.getPhone());
        mTvDepartment.setText(userInfo.getDepartmentName());
        mTvOffice.setText(userInfo.getPname());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == -1) {
                    String phone = data.getStringExtra("phone");
                    mTvPhone.setText(phone);
                }
                break;
            default:
        }
    }

    //获取人员列表
    private void getEmployeeList() {
            RequestParams params = new RequestParams();
            netClient.post(Data.getInstance().getIp() + Constants.GET_EMPLOYEELIST, params, new BaseJsonRes() {

                @Override
                public void onMySuccess(String data) {
                    Log.i(TAG, "获取人员列表：" + data);
                        if (!TextUtils.isEmpty(data)) {
                            Utils.putValue(getActivity(), Constants.EmployeeList, data);
                        }else{
                            Utils.showShortToast(getActivity(), "没有获取到人员信息");
                        }
                }

                @Override
                public void onMyFailure(String content) {
                    Log.e(TAG, "获取部门/队组成员返回错误信息：" + content);
                    Utils.showShortToast(getActivity(), content);
                }
            });
        }

    //获取部门/队组成员
    private void getCollieryTeam() {
        RequestParams params = new RequestParams();
        params.put("employeeId", UserUtils.getUserID(getActivity()));
        netClient.post(Data.getInstance().getIp() + Constants.GET_COLLIERYTEAM, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "获取部门/队组成员返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    Utils.putValue(getActivity(), Constants.GET_COLLIERYTEAM, data);
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "获取部门/队组成员返回错误信息：" + content);
                Utils.showShortToast(getActivity(), content);
            }
        });
    }

    //获取所属专业
    private void getSpecialty() {
        RequestParams params = new RequestParams();
        netClient.post(Data.getInstance().getIp() + Constants.GET_SPECIALTY, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "获取所属专业返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    Utils.putValue(getActivity(), Constants.GET_SPECIALTY, data);
                }
            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "获取所属专业返回错误信息：" + content);
                Utils.showShortToast(getActivity(), content);
            }
        });
    }

    //获取隐患类别
    private void getRiskGrade() {
        RequestParams params = new RequestParams();
        netClient.post(Data.getInstance().getIp() + Constants.GET_RISKGRADE, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "获取隐患类别返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    Utils.putValue(getActivity(), Constants.GET_RISKGRADE, data);
                }
            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "获取隐患类别返回错误信息：" + content);
                Utils.showShortToast(getActivity(), content);
            }
        });
    }

    //获取班次
    private void getClassNumber() {
        RequestParams params = new RequestParams();
        netClient.post(Data.getInstance().getIp() + Constants.GET_CLASSNUMBER, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "获取班次返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    Utils.putValue(getActivity(), Constants.GET_CLASSNUMBER, data);
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "获取班次返回错误信息：" + content);
                Utils.showShortToast(getActivity(), content);
            }
        });
    }

    //获取区域
    private void getArea() {
        RequestParams params = new RequestParams();
        params.put("employeeId", UserUtils.getUserID(getActivity()));
        netClient.post(Data.getInstance().getIp() + Constants.GET_AREA, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "获取区域返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    Utils.putValue(getActivity(), Constants.GET_AREA, data);
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "获取区域返回错误信息：" + content);
                Utils.showShortToast(getActivity(), content);
            }
        });
    }

    //获取隐患类型
    private void getHiddenType() {
        RequestParams params = new RequestParams();
        params.put("dictTypeCode", "YHLX");
        netClient.post(Data.getInstance().getIp() + Constants.GET_DATADICT, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "获取隐患类型返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    Utils.putValue(getActivity(), Constants.GET_DATADICT, data);
                }
            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "获取隐患类型返回错误信息：" + content);
                Utils.showShortToast(getActivity(), content);
            }
        });
    }

    //获取隐患级别
    private void getHiddenGrade() {
        RequestParams params = new RequestParams();
        params.put("dictTypeCode", "YHJB");
        netClient.post(Data.getInstance().getIp() + Constants.GET_DATADICT, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "获取隐患级别返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    Utils.putValue(getActivity(), "hiddenGrade", data);
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "获取隐患级别返回错误信息：" + content);
                Utils.showShortToast(getActivity(), content);
            }
        });
    }

    //获取检查单位
    private void getHiddenYHGSLX() {
        RequestParams params = new RequestParams();
        params.put("dictTypeCode", "YHGSLX");
        netClient.post(Data.getInstance().getIp() + Constants.GET_DATADICT, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "获取检查单位返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    Utils.putValue(getActivity(), "hiddenYHGSLX", data);
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "获取检查单位返回错误信息：" + content);
                Utils.showShortToast(getActivity(), content);
            }
        });
    }

    //获取检查内容
    private void getCheckContent() {
        RequestParams params = new RequestParams();
        params.put("dictTypeCode", "JCNR");
        netClient.post(Data.getInstance().getIp() + Constants.GET_DATADICT, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "获取检查内容返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    Utils.putValue(getActivity(), "checkContent", data);
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "获取检查内容返回错误信息：" + content);
                Utils.showShortToast(getActivity(), content);
            }
        });
    }

}