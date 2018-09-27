package com.example.administrator.riskprojects.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.administrator.riskprojects.Adpter.HiddenDangerStatisticsAllAdapter;
import com.example.administrator.riskprojects.Adpter.HiddenDangerStatisticsEachUnitAllAdapter;
import com.example.administrator.riskprojects.Adpter.HiddenDangerStatisticsRepeatAdapter;
import com.example.administrator.riskprojects.Adpter.HiddenRiskQueryStatisticsAdapter;
import com.example.administrator.riskprojects.Adpter.SpinnerAdapter;
import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.activity.Data;
import com.example.administrator.riskprojects.activity.HiddenRiskRecordAddEditActivity;
import com.example.administrator.riskprojects.activity.MainActivity;
import com.example.administrator.riskprojects.bean.HiddenDangerRecord;
import com.example.administrator.riskprojects.bean.HomeHiddenRecord;
import com.example.administrator.riskprojects.bean.SelectItem;
import com.example.administrator.riskprojects.net.BaseJsonRes;
import com.example.administrator.riskprojects.net.NetClient;
import com.example.administrator.riskprojects.tools.Constants;
import com.example.administrator.riskprojects.tools.UserUtils;
import com.example.administrator.riskprojects.util.DensityUtil;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.juns.health.net.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Fragment_Statistics extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    // 数据统计
    private static final String TAG = "Fragment_Statistics";
    private Activity ctx;
    private View layout;
    private BarChart mBarChart;
    private LineChart mLineChart;
    private SwipeRefreshLayout swipeRefreshLayout;
    protected NetClient netClient;
    private LinearLayoutCompat llOption;
    private LinearLayoutCompat llLineChart;
    private LinearLayoutCompat llBarChart;
    private TextView tvProfession;
    private TextView tvHiddenUnits;
    private TextView tvArea;
    private TextView titleTop;
    private BarChart barChart;
    private TextView titleBottom;
    private LineChart lineChart;
    private RecyclerView recyclerView;
    private String pid;
    private int flagint;
    private String teamGroupCode;
    private boolean onLoading = false;
    private int page = 1;
    private int pagesize = 1;
    int flag = 0;
    List<HomeHiddenRecord> dtatisticsList = new ArrayList<>();
    RecyclerView.Adapter adapter;
    private LinearLayoutCompat llMonth;
    private Spinner spMonth;
    private SpinnerAdapter monthAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        netClient = new NetClient(getActivity());
        if (layout == null) {
            ctx = this.getActivity();
            layout = ctx.getLayoutInflater().inflate(R.layout.fragment_dicover,
                    null);

        } else {
            ViewGroup parent = (ViewGroup) layout.getParent();
            if (parent != null) {
                parent.removeView(layout);
            }
        }
        initView(layout);
        return layout;
    }

    private void initView(View layout) {
        //直方图
        mBarChart = layout.findViewById(R.id.barChart);
        //曲线图
        mLineChart = layout.findViewById(R.id.lineChart);
        swipeRefreshLayout = layout.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        initBarChart(mBarChart);
        initLineChart(mLineChart);
        llOption = layout.findViewById(R.id.ll_option);
        tvProfession = layout.findViewById(R.id.tv_profession);
        tvHiddenUnits = layout.findViewById(R.id.tv_hidden_units);
        tvArea = layout.findViewById(R.id.tv_area);
        recyclerView = layout.findViewById(R.id.recyclerView);
        llBarChart = layout.findViewById(R.id.ll_barChart);

        llLineChart = layout.findViewById(R.id.ll_lineChart);
        titleTop = layout.findViewById(R.id.title_top);
        titleBottom = layout.findViewById(R.id.title_bottom);
        llBarChart.setVisibility(View.GONE);
        llLineChart.setVisibility(View.GONE);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(ctx));
        setUpFirstView();
        getHiddenStatisticsData("true");
        llMonth = layout.findViewById(R.id.ll_month);
        spMonth = layout.findViewById(R.id.sp_month);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

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
                                getDateByPage(page + 1);
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

    private void showLineChart(List<HomeHiddenRecord> dtatisticsList) {
        //设置数据
        List<Entry> entries = new ArrayList<Entry>();
        for (int i = 0; i < dtatisticsList.size(); i++) {
            float num = Float.parseFloat(dtatisticsList.get(i).getTotal());
            entries.add(new Entry(i, num));
        }
        //一个LineDataSet就是一条线
        LineDataSet lineDataSet = new LineDataSet(entries, "隐患条数");
        lineDataSet.setColor(getResources().getColor(R.color.blue1));
        lineDataSet.setDrawCircleHole(true);
        //设置曲线值的圆点是实心还是空心
        lineDataSet.setDrawCircleHole(true);
        lineDataSet.setCircleColor(getResources().getColor(R.color.halfBlack));
        lineDataSet.setCircleRadius(2f);
        lineDataSet.setCircleHoleRadius(1f);
        //设置显示值的字体大小
        lineDataSet.setValueTextColor(Color.TRANSPARENT);
        //线模式为圆滑曲线（默认折线）
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        lineDataSet.setDrawFilled(true);
        if (Utils.getSDKInt() >= 18) {
            // fill drawable only supported on api level 18 and above
            Drawable drawable = ContextCompat.getDrawable(ctx, R.drawable.fade_blue);
            lineDataSet.setFillDrawable(drawable);
        } else {
            lineDataSet.setFillColor(Color.BLACK);
        }
        LineData data = new LineData(lineDataSet);
        mLineChart.setData(data);
        llLineChart.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setVisibility(View.GONE);
    }

    private void initLineChart(LineChart lineChart) {
        //背景颜色
        lineChart.setBackgroundColor(Color.WHITE);
        //不显示图表网格
        lineChart.setDrawGridBackground(false);
        //显示边框
        lineChart.setDrawBorders(false);
        //是否双向指缩放
        lineChart.setPinchZoom(false);
        //是否显示右下角的描述
        lineChart.getDescription().setEnabled(false);

        lineChart.setScaleMinima(1.0f, 1.0f);

        lineChart.getViewPortHandler().refresh(new Matrix(), lineChart, true);

        //设置动画效果
        lineChart.animateY(1000, Easing.EasingOption.Linear);
        lineChart.animateX(1000, Easing.EasingOption.Linear);

        /***XY轴的设置***/
        //X轴设置显示位置在底部
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setAxisMinimum(0f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGridColor(Color.TRANSPARENT); //网格线颜色
        xAxis.setEnabled(false);
        xAxis.setGranularity(1f);

        YAxis leftAxis = lineChart.getAxisLeft();
        YAxis rightAxis = lineChart.getAxisRight();
        leftAxis.setAxisMinimum(0f);
        leftAxis.setGridColor(Color.parseColor("#eaeaea")); //网格线颜色
        leftAxis.setAxisLineColor(Color.TRANSPARENT); //网格线颜色
        rightAxis.setGridColor(Color.TRANSPARENT); //网格线颜色
        rightAxis.setEnabled(false);
        lineChart.getLegend().setEnabled(false);
    }


    private void initBarChart(BarChart barChart) {
        /***图表设置***/
        //背景颜色
        barChart.setBackgroundColor(Color.WHITE);
        //不显示图表网格
        barChart.setDrawGridBackground(false);
        //背景阴影
        barChart.setDrawBarShadow(false);
        barChart.setHighlightFullBarEnabled(false);
        //显示边框
        barChart.setDrawBorders(false);
        //是否双向指缩放
        barChart.setPinchZoom(false);
        //是否显示右下角的描述
        barChart.getDescription().setEnabled(false);

        barChart.setScaleMinima(1.0f, 1.0f);

        barChart.getViewPortHandler().refresh(new Matrix(), barChart, true);

        //设置动画效果
        barChart.animateY(1000, Easing.EasingOption.Linear);
        barChart.animateX(1000, Easing.EasingOption.Linear);

        /***XY轴的设置***/
        //X轴设置显示位置在底部
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGridColor(Color.TRANSPARENT); //网格线颜色
        xAxis.setEnabled(false);
        xAxis.setGranularity(1f);

        YAxis leftAxis = barChart.getAxisLeft();
        YAxis rightAxis = barChart.getAxisRight();
        leftAxis.setGridColor(Color.parseColor("#eaeaea")); //网格线颜色
        leftAxis.setAxisLineColor(Color.TRANSPARENT); //网格线颜色
        rightAxis.setGridColor(Color.TRANSPARENT); //网格线颜色
        rightAxis.setEnabled(false);
        //保证Y轴从0开始，不然会上移一点

        /***折线图例 标签 设置***/
        Legend legend = barChart.getLegend();
        legend.setForm(Legend.LegendForm.LINE);
        legend.setFormSize(8);
        legend.setTextSize(11f);
        //显示位置
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setWordWrapEnabled(true);
        //是否绘制在图表里面
        legend.setDrawInside(false);

    }


    /**
     * 柱状图始化设置 一个BarDataSet 代表一列柱状图
     *
     * @param barDataSet 柱状图
     * @param color      柱状图颜色
     */
    private void initBarDataSet(BarDataSet barDataSet, int color) {
        barDataSet.setColor(color);
        barDataSet.setFormLineWidth(1f);
        barDataSet.setFormSize(15.f);
        //不显示柱状图顶部值
        barDataSet.setDrawValues(false);
    }


    public void showBarChart(List<HomeHiddenRecord> dtatisticsList, String name) {
        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();

        for (int i = 0; i < dtatisticsList.size(); i++) {
            /**
             * 此处还可传入Drawable对象 BarEntry(float x, float y, Drawable icon)
             * 即可设置柱状图顶部的 icon展示
             */
            ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
            //i: 位置  (float) new Random().nextInt(100):值
            float num = Float.parseFloat(dtatisticsList.get(i).getTotal());
            BarEntry barEntry = new BarEntry(i, num);
            entries.add(barEntry);
            // 每一个BarDataSet代表一类柱状图
            if (name.equals("name")) {
                BarDataSet barDataSet = new BarDataSet(entries, dtatisticsList.get(i).getName());
                initBarDataSet(barDataSet, getRandColor());
                dataSets.add(barDataSet);
            } else {
                BarDataSet barDataSet = new BarDataSet(entries, dtatisticsList.get(i).getTeamGroupName());
                initBarDataSet(barDataSet, getRandColor());
                dataSets.add(barDataSet);
            }

        }
        // 添加多个BarDataSet时
        BarData data = new BarData(dataSets);
        //BarChart控件宽度 / 柱状图数量  * mBarWidth
        data.setBarWidth(0.5f);
        mBarChart.setData(data);
        llBarChart.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setVisibility(View.GONE);
    }

    //各单位隐患统计查询
    private void getHiddenStatisticsData(String currentdate) {
        RequestParams params = new RequestParams();
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int date = cal.get(Calendar.DATE);
        params.put("employeeId", UserUtils.getUserID(getActivity()));
        params.put("customParamsOne", year + "-" + month + "-01");//开始时间
        String customParamsTwo = year + "-" + month + "-" + date;
        if (currentdate.equals("false") && !TextUtils.isEmpty(tvHiddenUnits.getText())) {
            customParamsTwo = tvHiddenUnits.getText().toString();
        }
        params.put("customParamsTwo", customParamsTwo);//结束时间
        netClient.post(Data.getInstance().getIp() + Constants.TEAMHDSTAISTICSDATAGRID, params, new BaseJsonRes() {

            @Override
            public void onStart() {
                super.onStart();
                onLoading = true;
                swipeRefreshLayout.setRefreshing(true);
                dtatisticsList.clear();
                adapter = new HiddenDangerStatisticsEachUnitAllAdapter(dtatisticsList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "各单位隐患统计查询数据返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    page = 1;
                    pagesize = 1;
                    List<HomeHiddenRecord> tempList = JSONArray.parseArray(data, HomeHiddenRecord.class);
                    dtatisticsList.addAll(tempList);
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "各单位隐患统计查询数据返回错误信息：" + content);
                com.example.administrator.riskprojects.tools.Utils.showLongToast(getContext(), content);
            }


            @Override
            public void onFinish() {
                super.onFinish();
                onLoading = false;
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    //隐患汇总表查询
    private void getHiddenSumaryMobile() {
        RequestParams params = new RequestParams();
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int date = cal.get(Calendar.DATE);
        params.put("customParamsOne", year + "-" + month + "-01");//开始时间
        String customParamsTwo = year + "-" + month + "-" + date;
        if (!TextUtils.isEmpty(tvHiddenUnits.getText())) {
            customParamsTwo = tvHiddenUnits.getText().toString();
        }
        params.put("customParamsTwo", customParamsTwo);//结束时间
        netClient.post(Data.getInstance().getIp() + Constants.SUMARYMOBILE, params, new BaseJsonRes() {
            @Override
            public void onStart() {
                super.onStart();
                onLoading = true;
                swipeRefreshLayout.setRefreshing(true);
                dtatisticsList.clear();
                adapter = new HiddenDangerStatisticsAllAdapter(dtatisticsList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "隐患汇总表查询数据返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    page = 1;
                    pagesize = 1;
                    List<HomeHiddenRecord> tempList = JSONArray.parseArray(data, HomeHiddenRecord.class);
                    dtatisticsList.addAll(tempList);
                    adapter.notifyDataSetChanged();
//                    setAllHiddenTroubleList(dtatisticsList);
                    //showBarChart(dtatisticsList);
                    //showLineChart(dtatisticsList);
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "隐患汇总表查询数据返回错误信息：" + content);
                com.example.administrator.riskprojects.tools.Utils.showLongToast(getContext(), content);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                onLoading = false;
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    /*//已删除隐患记录查询
    private void getHiddenDeleteMobile() {
        RequestParams params = new RequestParams();
        //params.put("customParamsOne","");//开始时间
        //params.put("customParamsTwo", "");//结束时间
        netClient.post(Data.getInstance().getIp()+Constants.DELETEMOBILE, params, new BaseJsonRes() {
            @Override
            public void onStart() {
                super.onStart();
                swipeRefreshLayout.setRefreshing(true);
            }

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "已删除隐患记录查询数据返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    List<HomeHiddenRecord> dtatisticsList = JSONArray.parseArray(data, HomeHiddenRecord.class);
                    showBarChart(dtatisticsList);
                    showLineChart(dtatisticsList);
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "已删除隐患记录查询数据返回错误信息：" + content);
                com.example.administrator.riskprojects.tools.Utils.showLongToast(getContext(), content);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }*/

    //重复隐患记录查询
    private void getHiddenRepeatMobile(final int curPage) {
        RequestParams params = new RequestParams();
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("page", Integer.toString(curPage));
        paramsMap.put("rows", Constants.ROWS);
        paramsMap.put("employeeId", UserUtils.getUserID(getActivity()));
        String jsonString = JSON.toJSONString(paramsMap);
        params.put("hiddenDangerRecordJsonData", jsonString);
        netClient.post(Data.getInstance().getIp() + Constants.REPEATMOBILE, params, new BaseJsonRes() {

            @Override
            public void onStart() {
                super.onStart();
                onLoading = false;
                if (curPage == 1) {
                    swipeRefreshLayout.setRefreshing(true);
                    dtatisticsList.clear();
                    adapter = new HiddenDangerStatisticsRepeatAdapter(dtatisticsList);
                    recyclerView.setAdapter(adapter);
                } else if (((MainActivity) ctx).index == 3) {
                    Toast.makeText(ctx, "正在加载" + curPage + "/" + pagesize, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "重复隐患记录查询数据返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    JSONObject returndata = JSON.parseObject(data);
                    String rows = returndata.getString("rows");
                    page = Integer.parseInt(returndata.getString("page"));
                    pagesize = Integer.parseInt(returndata.getString("pagesize"));
                    List<HomeHiddenRecord> tempList = JSONArray.parseArray(rows, HomeHiddenRecord.class);
                    dtatisticsList.addAll(tempList);
                    adapter.notifyDataSetChanged();
//                    setUpRepeatList(dtatisticsList);
                    //showBarChart(dtatisticsList);
                    //showLineChart(dtatisticsList);
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "重复隐患记录查询数据返回错误信息：" + content);
                com.example.administrator.riskprojects.tools.Utils.showLongToast(getContext(), content);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                onLoading = false;
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    //隐患处理单位图表分析
    private void getHiddenDepartmentStatisticsMobile() {
        RequestParams params = new RequestParams();
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        params.put("employeeId", UserUtils.getUserID(getActivity()));
        params.put("customParamsOne", year + "-" + month + "-01");//开始时间
        params.put("customParamsTwo", tvHiddenUnits.getText().toString());//结束时间
        netClient.post(Data.getInstance().getIp() + Constants.DEPARTMENTSTATISTICSMOBILE, params, new BaseJsonRes() {

            @Override
            public void onStart() {
                super.onStart();
                llLineChart.setVisibility(View.GONE);
                llBarChart.setVisibility(View.GONE);
            }

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "隐患处理单位图表分析数据返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    List<HomeHiddenRecord> dtatisticsList = JSONArray.parseArray(data, HomeHiddenRecord.class);
                    llLineChart.setVisibility(View.VISIBLE);
                    llBarChart.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setVisibility(View.GONE);
                    initBarChart(mBarChart);
                    initLineChart(mLineChart);
                    showBarChart(dtatisticsList, "teamGroupName");
                    showLineChart(dtatisticsList);
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "隐患处理单位图表分析数据返回错误信息：" + content);
                com.example.administrator.riskprojects.tools.Utils.showLongToast(getContext(), content);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    //隐患处理图表分析接口
    private void getHiddenDangerSpecialStatistics() {
        RequestParams params = new RequestParams();
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        params.put("customParamsOne", year + "-" + month + "-01");//开始时间
        params.put("customParamsTwo", tvHiddenUnits.getText().toString());//结束时间
        if (!TextUtils.isEmpty(pid)) {
            params.put("customParamsFour", pid);//分析因素
        }
        netClient.post(Data.getInstance().getIp() + Constants.HIDDENDANGERSPECIALSTATISTICS, params, new BaseJsonRes() {
            @Override
            public void onStart() {
                super.onStart();
                llLineChart.setVisibility(View.GONE);
                llBarChart.setVisibility(View.GONE);
            }

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "隐患处理图表分析接口数据返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    List<HomeHiddenRecord> dtatisticsList = JSONArray.parseArray(data, HomeHiddenRecord.class);
                    llLineChart.setVisibility(View.VISIBLE);
                    llBarChart.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setVisibility(View.GONE);
                    initBarChart(mBarChart);
                    initLineChart(mLineChart);
                    showBarChart(dtatisticsList, "name");
                    showLineChart(dtatisticsList);
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "隐患处理图表分析接口数据返回错误信息：" + content);
                com.example.administrator.riskprojects.tools.Utils.showLongToast(getContext(), content);
            }
        });
    }

    //隐患年度图表分析接口
    private void getFindHiddenDangerYearChartStatistics(int poi) {
        final String num;
        if (poi < 10) {
            num = "0" + poi;
        } else {
            num = Integer.toString(poi);
        }
        RequestParams params = new RequestParams();
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        String yearStr = String.valueOf(year);
        if (!TextUtils.isEmpty(tvHiddenUnits.getText().toString())) {
            yearStr = tvHiddenUnits.getText().toString().split("-")[0];
        }
        params.put("customParamsOne", yearStr);
        params.put("employeeId", UserUtils.getUserID(getActivity()));
        netClient.post(Data.getInstance().getIp() + Constants.FINDHIDDENDANGERYEARCHARTSTATISTICS, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "隐患年度图表分析接口数据返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    JSONObject jsonObject = JSONObject.parseObject(data);
                    String monthData = jsonObject.getJSONArray(num).toString();
                    List<HomeHiddenRecord> dtatisticsList = JSONArray.parseArray(monthData, HomeHiddenRecord.class);
                    if (!dtatisticsList.isEmpty()) {
                        llLineChart.setVisibility(View.VISIBLE);
                        llBarChart.setVisibility(View.VISIBLE);
                        swipeRefreshLayout.setVisibility(View.GONE);
                        initBarChart(mBarChart);
                        initLineChart(mLineChart);
                        showBarChart(dtatisticsList, "");
                        showLineChart(dtatisticsList);
                    } else {
                        llLineChart.setVisibility(View.GONE);
                        llBarChart.setVisibility(View.GONE);
                        Toast.makeText(ctx, "该月份数据为空", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "隐患年度图表分析接口数据返回错误信息：" + content);
                com.example.administrator.riskprojects.tools.Utils.showLongToast(getContext(), content);
            }
        });
    }

    //隐患查询统计
    private void getHiddenQueryStaticMobile(final int curPage) {
        RequestParams params = new RequestParams();
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("page", Integer.toString(curPage));
        paramsMap.put("rows", Constants.ROWS);
        paramsMap.put("employeeId", UserUtils.getUserID(getActivity()));
        String jsonString = JSON.toJSONString(paramsMap);
        params.put("hiddenDangerRecordJsonData", jsonString);
        netClient.post(Data.getInstance().getIp() + Constants.QUERYSTATICMOBILE, params, new BaseJsonRes() {

            @Override
            public void onStart() {
                super.onStart();
                onLoading = true;
                if (curPage == 1) {
                    swipeRefreshLayout.setRefreshing(true);
                    dtatisticsList.clear();
                    adapter = new HiddenDangerStatisticsRepeatAdapter(dtatisticsList);
                    recyclerView.setAdapter(adapter);
                } else if (((MainActivity) ctx).index == 3) {
                    Toast.makeText(ctx, "正在加载" + curPage + "/" + pagesize, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "隐患查询统计数据返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    JSONObject returndata = JSON.parseObject(data);
                    String rows = returndata.getString("rows");
                    page = Integer.parseInt(returndata.getString("page"));
                    pagesize = Integer.parseInt(returndata.getString("pagesize"));
                    List<HomeHiddenRecord> tempList = JSONArray.parseArray(rows, HomeHiddenRecord.class);
                    dtatisticsList.addAll(tempList);
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "隐患查询统计数据返回错误信息：" + content);
                com.example.administrator.riskprojects.tools.Utils.showLongToast(getContext(), content);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                onLoading = false;
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private int getRandColor() {
        Random random = new Random();
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return Color.rgb(r, g, b);
    }


    public void onRightMenuClicked(View view) {
        if (!TextUtils.isEmpty(tvArea.getText().toString()) ||
                !TextUtils.isEmpty(tvProfession.getText().toString()) ||
                !TextUtils.isEmpty(tvHiddenUnits.getText().toString())) {
            llOption.setVisibility(View.VISIBLE);
        } else {
            llOption.setVisibility(View.GONE);
        }
        switch (view.getId()) {
            case R.id.ll_chart_01:
                flag = 0;
                setUpFirstView();
                llMonth.setVisibility(View.GONE);
                getHiddenStatisticsData("true");
                break;
            case R.id.ll_chart_02:
                flag = 1;
                titleTop.setText(R.string.summary_of_hazards);
                titleBottom.setText(R.string.summary_of_hazards);
                llMonth.setVisibility(View.GONE);
                getHiddenSumaryMobile();
                break;
            case R.id.ll_chart_03:
                flag = 2;
                titleTop.setText(R.string.hazard_query_statistics);
                titleBottom.setText(R.string.hazard_query_statistics);
                llMonth.setVisibility(View.GONE);

                break;
           /* case R.id.ll_chart_04:
                titleTop.setText(R.string.the_hidden_danger_record_has_been_deleted);
                titleBottom.setText(R.string.the_hidden_danger_record_has_been_deleted);
                getHiddenDeleteMobile();
                break;*/
            case R.id.ll_chart_05:
                flag = 4;
                titleTop.setText(R.string.duplicate_hazard_record);
                titleBottom.setText(R.string.duplicate_hazard_record);
                llMonth.setVisibility(View.GONE);
                break;
            case R.id.ll_chart_06:
                flag = 5;
                titleTop.setText(R.string.chart_analysis_of_hazard_handling_unit);
                titleBottom.setText(R.string.chart_analysis_of_hazard_handling_unit);
                llMonth.setVisibility(View.GONE);
                getHiddenDepartmentStatisticsMobile();
                break;
            case R.id.ll_chart_07:
                flag = 6;
                titleTop.setText(R.string.chart_analysis_of_hazard_handling);
                titleBottom.setText(R.string.chart_analysis_of_hazard_handling);
                llMonth.setVisibility(View.GONE);
                getHiddenDangerSpecialStatistics();
                break;
            case R.id.ll_chart_08:
                flag = 7;
                titleTop.setText(R.string.chart_analysis_of_hazard_year);
                titleBottom.setText(R.string.chart_analysis_of_hazard_year);
                llMonth.setVisibility(View.VISIBLE);
                setUpSpinner(spMonth);
                break;
            default:
                break;
        }

        if (flag < 5) {
            getDateByPage(1);
        }
    }

    private void getDateByPage(int curpage) {
        switch (flag) {
            case 0:
                getHiddenStatisticsData("true");
                break;
            case 1:
                getHiddenSumaryMobile();
                break;
            case 2:
                getHiddenQueryStaticMobile(curpage);
                break;
            case 4:
                getHiddenRepeatMobile(curpage);
                break;


        }
    }

    private void setUpSpinner(Spinner spinner) {
        String[] month = new String[]{"一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月",};
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        int collieryTeamsint = 0;
        for (int i = 0; i < 12; i++) {
            SelectItem selectItem = new SelectItem();
            selectItem.name = month[i];
            selectItem.id = Integer.toString(i + 1);
            selectItems.add(selectItem);
        }
        monthAdapter = SpinnerAdapter.createFromResource(ctx, selectItems, Gravity.CENTER_VERTICAL | Gravity.LEFT);
        spinner.setBackgroundColor(0x0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            spinner.setPopupBackgroundResource(R.drawable.shape_rectangle_rounded);
            spinner.setDropDownVerticalOffset(DensityUtil.dip2px(ctx, 3));
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                monthAdapter.setSelectedPostion(position);
                getFindHiddenDangerYearChartStatistics(position + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner.setAdapter(monthAdapter);
    }


    private void setUpFirstView() {
        titleTop.setText(R.string.hidden_danger_statistics_of_each_unit);
        titleBottom.setText(R.string.hidden_danger_statistics_of_each_unit);
        getHiddenStatisticsData("false");
        adapter = new HiddenDangerStatisticsEachUnitAllAdapter(dtatisticsList);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        llLineChart.setVisibility(View.GONE);
        llBarChart.setVisibility(View.GONE);
    }

    private void setUpRepeatList(List<HomeHiddenRecord> dtatisticsList) {
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new HiddenDangerStatisticsRepeatAdapter(dtatisticsList);
        recyclerView.setAdapter(adapter);
    }

    private void setHiddenRiskQueryStatisticsList(List<HiddenDangerRecord> recordList) {
        if (!TextUtils.isEmpty(tvArea.getText().toString()) ||
                !TextUtils.isEmpty(tvProfession.getText().toString()) ||
                !TextUtils.isEmpty(tvHiddenUnits.getText().toString())) {
            llOption.setVisibility(View.VISIBLE);
        } else {
            llOption.setVisibility(View.GONE);
        }
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(ctx));
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        HiddenRiskQueryStatisticsAdapter adapter = new HiddenRiskQueryStatisticsAdapter(recordList);
        recyclerView.setAdapter(adapter);
    }

    private void setAllHiddenTroubleList(List<HomeHiddenRecord> dtatisticsList) {
        if (!TextUtils.isEmpty(tvArea.getText().toString()) ||
                !TextUtils.isEmpty(tvProfession.getText().toString()) ||
                !TextUtils.isEmpty(tvHiddenUnits.getText().toString())) {
            llOption.setVisibility(View.VISIBLE);
        } else {
            llOption.setVisibility(View.GONE);
        }
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(ctx));
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        HiddenDangerStatisticsAllAdapter adapter = new HiddenDangerStatisticsAllAdapter(dtatisticsList);
        recyclerView.setAdapter(adapter);
    }

    private void setList(List<HomeHiddenRecord> dtatisticsList) {
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        HiddenDangerStatisticsEachUnitAllAdapter adapter = new HiddenDangerStatisticsEachUnitAllAdapter(dtatisticsList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        page = 1;
        pagesize = 1;
        getDateByPage(1);
    }

    public void onLeftMenuClicked(String aname, String aid, String pname, String pidstr, String hname, String hid) {
        llOption.setVisibility(View.VISIBLE);
        //tvArea.setText(aname);
        if (TextUtils.isEmpty(hname)) {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int date = cal.get(Calendar.DATE);
            hname = year + "-" + month + "-" + date;
        }
        tvProfession.setText(pname);
        tvHiddenUnits.setText(hname);
        pid = pidstr;
    }


    public void setIdFlag(String id, int flag) {
        //设置变量值，进行请求时使用
        flagint = flag;
        teamGroupCode = id;
    }
}
