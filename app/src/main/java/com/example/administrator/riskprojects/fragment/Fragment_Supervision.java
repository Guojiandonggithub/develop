package com.example.administrator.riskprojects.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.administrator.riskprojects.Adpter.HomeHiddenDangerAdapter;
import com.example.administrator.riskprojects.Adpter.ListingSupervisionAdapter;
import com.example.administrator.riskprojects.Adpter.SideBar;
import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.activity.MainActivity;
import com.example.administrator.riskprojects.bean.HiddenDangerRecord;
import com.example.administrator.riskprojects.bean.HomeHiddenRecord;
import com.example.administrator.riskprojects.net.BaseJsonRes;
import com.example.administrator.riskprojects.net.NetClient;
import com.example.administrator.riskprojects.tools.Constants;
import com.example.administrator.riskprojects.tools.Utils;
import com.example.administrator.riskprojects.util.DensityUtil;
import com.juns.health.net.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//挂牌督办

public class Fragment_Supervision extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "Fragment_Supervision";
    private Activity ctx;
    private View layout;
    private WindowManager mWindowManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    protected NetClient netClient;
    private boolean onLoading = false;
    private int page = 1;
    private int pagesize = 1;
    private ListingSupervisionAdapter adapter;
    private List<HiddenDangerRecord> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        netClient = new NetClient(getActivity());
        if (layout == null) {
            ctx = this.getActivity();
            layout = ctx.getLayoutInflater().inflate(R.layout.fragment_friends,
                    null);
            mWindowManager = (WindowManager) ctx
                    .getSystemService(Context.WINDOW_SERVICE);
        } else {
            ViewGroup parent = (ViewGroup) layout.getParent();
            if (parent != null) {
                parent.removeView(layout);
            }
        }
        initView(layout);
        initViews();
        return layout;
    }

    private void initView(View layout) {
        mSwipeRefreshLayout = layout.findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = layout.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ctx));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        adapter = new ListingSupervisionAdapter(list);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (recyclerView.getAdapter().getItemCount() != 0) {
                    //得到当前显示的最后一个item的view
                    View lastChildView = recyclerView.getLayoutManager().getChildAt(recyclerView.getLayoutManager().getChildCount() - 1);
                    if (lastChildView != null) {
                        //得到lastChildView的bottom坐标值
                        int lastChildBottom = lastChildView.getBottom();
                        //得到Recyclerview的底部坐标减去底部padding值，也就是显示内容最底部的坐标
                        int recyclerBottom = recyclerView.getBottom() - recyclerView.getPaddingBottom();
                        //通过这个lastChildView得到这个view当前的position值
                        int lastPosition = recyclerView.getLayoutManager().getPosition(lastChildView);
                        System.out.println("findFirstCompletelyVisibleItemPosition:" + (((LinearLayoutManager) recyclerView.getLayoutManager())
                                .findFirstCompletelyVisibleItemPosition()));
                        System.out.println("Math.abs(lastChildBottom - recyclerBottom):" + Math.abs(lastChildBottom - recyclerBottom));
                        System.out.println("lastPosition:" + lastPosition);
                        System.out.println("recyclerView.getLayoutManager().getItemCount() - 1:" + (recyclerView.getLayoutManager().getItemCount() - 1));
                        //判断lastChildView的bottom值跟recyclerBottom
                        //判断lastPosition是不是最后一个position
                        //如果两个条件都满足则说明是真正的滑动到了底部
                        if (!onLoading && 0 != (((LinearLayoutManager) recyclerView.getLayoutManager())
                                .findFirstCompletelyVisibleItemPosition())
                                && Math.abs(lastChildBottom - recyclerBottom) <= DensityUtil.dip2px(ctx, 8)
                                && lastPosition == recyclerView.getLayoutManager().getItemCount() - 1
                                && newState == RecyclerView.SCROLL_STATE_IDLE) {
                            //loading more data

                            if (page < pagesize) {
                                getHiddenRecord(page + 1);
                            } else {
                                Toast.makeText(ctx, "全部加载完毕", Toast.LENGTH_SHORT).show();
                            }


                        }
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void initViews() {
        getHiddenRecord(Integer.parseInt(Constants.PAGE));
    }

    private void getHiddenRecord(final int curpage) {//分页当前页
        RequestParams params = new RequestParams();
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("page", Integer.toString(curpage));
        paramsMap.put("rows", Constants.ROWS);
        String jsonString = JSON.toJSONString(paramsMap);
        params.put("hiddenDangerRecordJsonData", jsonString);
        netClient.post(Constants.GET_HIDDENRECORD, params, new BaseJsonRes() {
            @Override
            public void onStart() {
                super.onStart();
                onLoading = true;
                if (curpage == 1) {
                    if (!mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(true);
                    }
                } else if (((MainActivity) ctx).index == 1) {
                    Toast.makeText(ctx, "正在加载" + curpage + "/" + pagesize, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "挂牌督办隐患数据返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    JSONObject returndata = JSON.parseObject(data);
                    String rows = returndata.getString("rows");
                    page = Integer.parseInt(returndata.getString("page"));
                    pagesize = Integer.parseInt(returndata.getString("pagesize"));
                    if (page == 1) {
                        list.clear();
                    }
                    List<HiddenDangerRecord> recordList = JSONArray.parseArray(rows, HiddenDangerRecord.class);
                    list.addAll(recordList);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "挂牌督办隐患数据返回错误信息：" + content);
                Utils.showLongToast(getContext(), content);

            }

            @Override
            public void onFinish() {
                super.onFinish();
                mSwipeRefreshLayout.setRefreshing(false);
                onLoading = false;
            }
        });
    }

    //下拉刷新
    //加载结束后需要调用    mSwipeRefreshLayout.setRefreshing(false);
    @Override
    public void onRefresh() {
        getHiddenRecord(1);
    }
}
