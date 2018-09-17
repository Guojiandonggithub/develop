package com.example.administrator.riskprojects.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.administrator.riskprojects.Adpter.HomeHiddenDangerAdapter;
import com.example.administrator.riskprojects.LoginActivity;
import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.activity.MainActivity;
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

//消息
public class Fragment_Home extends Fragment {
    private static final String TAG = "Fragment_Home";
    private Activity ctx;
    private View layout, layout_head;
    private MainActivity parentActivity;
    private TextView mTvDeleteNum;
    private TextView mTvWithinTheTimeLimitNum;
    private TextView mTvUnchangeNum;
    private TextView mTvForAcceptanceNum;
    private RecyclerView mRecyclerView;
    protected NetClient netClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        netClient = new NetClient(getActivity());
        if (layout == null) {
            ctx = this.getActivity();
            parentActivity = (MainActivity) getActivity();
            layout = ctx.getLayoutInflater().inflate(R.layout.fragment_home,
                    null);
			/*lvContact = (ListView) layout.findViewById(R.id.listview);
			errorItem = (RelativeLayout) layout
					.findViewById(R.id.rl_error_item);*/
        } else {
            ViewGroup parent = (ViewGroup) layout.getParent();
            if (parent != null) {
                parent.removeView(layout);
            }
        }
        initView(layout);
        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();
        initViews();
    }

    /**
     * 刷新页面
     */
    public void refresh() {
        initViews();
    }

    private void initViews() {
        getHiddenStatisticsNum(UserUtils.getUserID(getActivity()));
        getHiddenRecord();
    }

    private void initView(View layout) {
        //已消号数量
        mTvDeleteNum = layout.findViewById(R.id.tv_delete_num);
        //逾期数量
        mTvWithinTheTimeLimitNum = layout.findViewById(R.id.tv_within_the_time_limit_num);
        //未整改
        mTvUnchangeNum = layout.findViewById(R.id.tv_unchange_num);
        //待验收
        mTvForAcceptanceNum = layout.findViewById(R.id.tv_for_acceptance_num);
        mRecyclerView = layout.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ctx));
    }


    private void getHiddenStatisticsNum(String employeeId) {
        if (!TextUtils.isEmpty(employeeId)) {
            RequestParams params = new RequestParams();
            params.put("employeeId", employeeId);
            netClient.post(Constants.HOME_GET_HIDDENUM, params, new BaseJsonRes() {

                @Override
                public void onMySuccess(String data) {
                    Log.i(TAG, "主页隐患数据统计返回数据：" + data);
                    if (!TextUtils.isEmpty(data)) {
                        JSONObject jsonObject = JSON.parseObject(data);
                        String notRectify = jsonObject.getString("notRectify");
                        String outTimeNumber = jsonObject.getString("outTimeNumber");
                        String recheck = jsonObject.getString("recheck");
                        String saleNumber = jsonObject.getString("saleNumber");
                        mTvUnchangeNum.setText(notRectify);
                        mTvWithinTheTimeLimitNum.setText(outTimeNumber);
                        mTvForAcceptanceNum.setText(recheck);
                        mTvDeleteNum.setText(saleNumber);
                    }

                }

                @Override
                public void onMyFailure(String content) {
                    Log.e(TAG, "主页获取隐患数据统计返回错误信息：" + content);
                    Utils.showLongToast(getContext(), content);
                }
            });
        } else {
            Utils.showLongToast(getActivity(), "请退出重新登录！");
        }
    }

    private void getHiddenRecord() {
        RequestParams params = new RequestParams();
        netClient.post(Constants.HOME_GET_HIDDENRECORD, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "主页隐患数据返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    List<HomeHiddenRecord> recordList = JSONArray.parseArray(data, HomeHiddenRecord.class);
                    mRecyclerView.addItemDecoration(new MyDecoration(ctx, MyDecoration.VERTICAL_LIST, R.color.white, DensityUtil.dip2px(ctx, 0.67f)));
                    mRecyclerView.setAdapter(new HomeHiddenDangerAdapter(recordList));
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "获取隐患数据返回错误信息：" + content);
                Utils.showLongToast(getContext(), content);
            }
        });
    }

}
