package com.example.administrator.riskprojects.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
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
import com.example.administrator.riskprojects.activity.AddSupervisorRecordActivity;
import com.example.administrator.riskprojects.activity.Data;
import com.example.administrator.riskprojects.activity.DatePickerActivity;
import com.example.administrator.riskprojects.activity.HiddenRiskRecordAddEditActivity;
import com.example.administrator.riskprojects.activity.MainActivity;
import com.example.administrator.riskprojects.bean.HiddenDangerRecord;
import com.example.administrator.riskprojects.bean.HomeHiddenRecord;
import com.example.administrator.riskprojects.bean.SelectItem;
import com.example.administrator.riskprojects.bean.Specialty;
import com.example.administrator.riskprojects.net.BaseJsonRes;
import com.example.administrator.riskprojects.net.NetClient;
import com.example.administrator.riskprojects.tools.Constants;
import com.example.administrator.riskprojects.tools.UserUtils;
import com.example.administrator.riskprojects.util.DensityUtil;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
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

import static android.app.Activity.RESULT_OK;

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
    private LinearLayoutCompat llYear;
    private Spinner spYear;
    private SpinnerAdapter monthAdapter;
    private SpinnerAdapter yearAdapter;
    private SpinnerAdapter isHandleAdapter;
    private SpinnerAdapter professionalAdapter;
    private SpinnerAdapter hiddenStatusAdapter;
    private SpinnerAdapter analyticalAdapter;
    private CardView cvSelectStartDate;
    private TextView tvStartDate;
    private CardView cvSelectEndDate;
    private TextView tvEndDate;
    private View llSelectTop;
    private LinearLayoutCompat llSelect;
    private TextView tvSpName;
    private Spinner spOther;
    private TextView tvdate;

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
        recyclerView = layout.findViewById(R.id.recyclerView);
        llBarChart = layout.findViewById(R.id.ll_barChart);

        llLineChart = layout.findViewById(R.id.ll_lineChart);
        titleTop = layout.findViewById(R.id.title_top);
        titleBottom = layout.findViewById(R.id.title_bottom);
        llBarChart.setVisibility(View.GONE);
        llLineChart.setVisibility(View.GONE);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(ctx));
        llMonth = layout.findViewById(R.id.ll_month);
        spMonth = layout.findViewById(R.id.sp_month);

        llYear = layout.findViewById(R.id.ll_year);
        spYear = layout.findViewById(R.id.sp_year);

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
        cvSelectStartDate = layout.findViewById(R.id.cv_select_start_date);
        tvStartDate = layout.findViewById(R.id.tv_start_date);
        cvSelectEndDate = layout.findViewById(R.id.cv_select_end_date);
        tvEndDate = layout.findViewById(R.id.tv_end_date);
        llSelectTop = layout.findViewById(R.id.ll_select_top);
        llSelect = layout.findViewById(R.id.ll_select);
        tvSpName = layout.findViewById(R.id.tv_sp_name);
        spOther = layout.findViewById(R.id.sp_other);
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        if(month<10){
            String monthstr = "0"+month;
            month = Integer.parseInt(monthstr);
        }
        int date = cal.get(Calendar.DATE);
        final String sname = year + "-" + (month + 1) + "-01";
        final String fname = year + "-" + (month + 1) + "-" + date;
        tvStartDate.setText(sname);
        tvEndDate.setText(fname);
        cvSelectStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvdate = tvStartDate;
//                DatePickerActivity.startPickDate(ctx, ctx);
                Fragment_Statistics.this.startActivityForResult(new Intent(ctx, DatePickerActivity.class), DatePickerActivity.REQUEST);
            }
        });

        cvSelectEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvdate = tvEndDate;
                Fragment_Statistics.this.startActivityForResult(new Intent(ctx, DatePickerActivity.class), DatePickerActivity.REQUEST);

            }
        });
        setUpTopView();
        setUpFirstView();
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
        params.put("employeeId", UserUtils.getUserID(getActivity()));
        params.put("customParamsOne",  tvStartDate.getText().toString());//开始时间
        params.put("customParamsTwo", tvEndDate.getText().toString());//结束时间
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
        params.put("customParamsOne",  tvStartDate.getText().toString());//开始时间
        params.put("customParamsTwo", tvEndDate.getText().toString());//结束时间
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
    private void getHiddenRepeatMobile(final int curPage,String sid) {
        RequestParams params = new RequestParams();
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("page", Integer.toString(curPage));
        paramsMap.put("rows", Constants.ROWS);
        paramsMap.put("employeeId", UserUtils.getUserID(getActivity()));
        paramsMap.put("customParamsFour",  tvStartDate.getText().toString());//开始时间
        paramsMap.put("customParamsFive", tvEndDate.getText().toString());//结束时间
        if(!TextUtils.isEmpty(sid)){
            paramsMap.put("customParamsTwo", sid);//专业id
        }
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
    private void getHiddenDepartmentStatisticsMobile(String ishandle) {
        Log.e(TAG, "隐患处理单位图表分析参数ishandle:==== "+ishandle);
        RequestParams params = new RequestParams();
        if(!TextUtils.isEmpty(ishandle)){
            params.put("customParamsThree",ishandle);//开始时间
        }
        params.put("employeeId", UserUtils.getUserID(getActivity()));
        params.put("customParamsOne",  tvStartDate.getText().toString());//开始时间
        params.put("customParamsTwo", tvEndDate.getText().toString());//结束时间
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
    private void getHiddenDangerSpecialStatistics(String pid) {
        RequestParams params = new RequestParams();
        params.put("customParamsOne",  tvStartDate.getText().toString());//开始时间
        params.put("customParamsTwo", tvEndDate.getText().toString());//结束时间
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
    private void getFindHiddenDangerYearChartStatistics(int yearPoi, int poi) {
        final String num;
        if (poi < 10) {
            num = "0" + poi;
        } else {
            num = Integer.toString(poi);
        }
        RequestParams params = new RequestParams();
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        String yearStr = String.valueOf(year - yearPoi);
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
    private void getHiddenQueryStaticMobile(final int curPage,int status) {
        RequestParams params = new RequestParams();
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("page", Integer.toString(curPage));
        paramsMap.put("rows", Constants.ROWS);
        paramsMap.put("employeeId", UserUtils.getUserID(getActivity()));
        paramsMap.put("customParamsNine", tvStartDate.getText().toString());//开始时间
        paramsMap.put("customParamsTen", tvEndDate.getText().toString());//结束时间
        if(status>0){
            paramsMap.put("customParamsSix", String.valueOf(status));//状态
        }
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
        switch (view.getId()) {
            case R.id.ll_chart_01:
                flag = 0;
                setUpFirstView();
                getHiddenStatisticsData("true");
                break;
            case R.id.ll_chart_02:
                flag = 1;
                titleTop.setText(R.string.summary_of_hazards);
                titleBottom.setText(R.string.summary_of_hazards);
                swipeRefreshLayout.setVisibility(View.VISIBLE);
                llLineChart.setVisibility(View.GONE);
                llBarChart.setVisibility(View.GONE);
                break;
            case R.id.ll_chart_03:
                flag = 2;
                titleTop.setText(R.string.hazard_query_statistics);
                titleBottom.setText(R.string.hazard_query_statistics);
                swipeRefreshLayout.setVisibility(View.VISIBLE);
                llLineChart.setVisibility(View.GONE);
                llBarChart.setVisibility(View.GONE);
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
                swipeRefreshLayout.setVisibility(View.VISIBLE);
                llLineChart.setVisibility(View.GONE);
                llBarChart.setVisibility(View.GONE);
                break;
            case R.id.ll_chart_06:
                flag = 5;
                titleTop.setText(R.string.chart_analysis_of_hazard_handling_unit);
                titleBottom.setText(R.string.chart_analysis_of_hazard_handling_unit);
                swipeRefreshLayout.setVisibility(View.GONE);
                getHiddenDepartmentStatisticsMobile("");
                break;
            case R.id.ll_chart_07:
                flag = 6;
                titleTop.setText(R.string.chart_analysis_of_hazard_handling);
                titleBottom.setText(R.string.chart_analysis_of_hazard_handling);
                swipeRefreshLayout.setVisibility(View.GONE);
                getHiddenDangerSpecialStatistics("0");
                break;
            case R.id.ll_chart_08:
                flag = 7;
                titleTop.setText(R.string.chart_analysis_of_hazard_year);
                titleBottom.setText(R.string.chart_analysis_of_hazard_year);
                llYear.setVisibility(View.VISIBLE);
                setUpYearSpinner(spYear);
                llMonth.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setVisibility(View.GONE);
                setUpSpinner(spMonth);
                break;
            default:
                break;
        }

        if (flag < 5) {
            getDateByPage(1);
        }
        setUpTopView();
    }

    private void setUpTopView() {
        if (flag != 7) {
            llOption.setVisibility(View.VISIBLE);
            llMonth.setVisibility(View.GONE);
            llYear.setVisibility(View.GONE);
            if (flag >= 2) {
                llSelect.setVisibility(View.VISIBLE);
                llSelectTop.setVisibility(View.VISIBLE);
                if (flag == 2) {
                    tvSpName.setText("隐患状态:");
                    setHiddenStatusSpinner(spOther);
                } else if (flag == 4) {
                    tvSpName.setText("专业:");
                    getSpecialty();
                } else if (flag == 5) {
                    tvSpName.setText("处理否:");
                    setIshandleSpinner(spOther);
                } else if (flag == 6) {
                    tvSpName.setText("分析因素:");
                    setAnalyticalSpinner(spOther);
                }

            } else {
                llSelect.setVisibility(View.GONE);
                llSelectTop.setVisibility(View.GONE);
            }

        } else {
            llOption.setVisibility(View.GONE);
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
                SelectItem selectItem = (SelectItem)spOther.getSelectedItem();
                int status = -1;
                if(null!=selectItem){
                    status = Integer.parseInt(selectItem.id);
                }
                getHiddenQueryStaticMobile(curpage,status);
                break;
            case 4:
                SelectItem selectItems = (SelectItem)spOther.getSelectedItem();
                String sid = "";
                if(null!=selectItems){
                    sid = selectItems.id;
                }
                getHiddenRepeatMobile(curpage,sid);
                break;


        }
    }

    //隐患状态
    private void setHiddenStatusSpinner(Spinner spinner){
        List<SelectItem> selectItems = new ArrayList<>();
        String[] hiddenStatus = {"请选择","筛选","五定中","整改中","复查中","销项"};
        for (int i = 0; i < hiddenStatus.length; i++) {
            SelectItem selectItem = new SelectItem();
            selectItem.name = hiddenStatus[i];
            selectItem.id = Integer.toString(i-1);
            selectItems.add(selectItem);
        }

        hiddenStatusAdapter = SpinnerAdapter.createFromResource(ctx, selectItems, Gravity.CENTER_VERTICAL | Gravity.LEFT);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hiddenStatusAdapter.setSelectedPostion(position);
                getHiddenQueryStaticMobile(Integer.parseInt(Constants.PAGE),Integer.parseInt(((SelectItem)spOther.getSelectedItem()).id));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spOther.setAdapter(hiddenStatusAdapter);
    }

    //专业列表
    private void setProfessionalSpinner(Spinner spinner){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                professionalAdapter.setSelectedPostion(position);
                getHiddenRepeatMobile(Integer.parseInt(Constants.PAGE),((SelectItem)spOther.getSelectedItem()).id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spOther.setAdapter(professionalAdapter);
    }

    //获取所属专业
    private void getSpecialty() {
        RequestParams params = new RequestParams();
        netClient.post(Data.getInstance().getIp()+Constants.GET_SPECIALTY, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "获取所属专业返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    List<Specialty> collieryTeams = JSONArray.parseArray(data, Specialty.class);
                    List<SelectItem> selectItems = new ArrayList<SelectItem>();
                    for (int i = 0; i < collieryTeams.size(); i++) {
                        if(i==0){
                            SelectItem selectItem = new SelectItem();
                            selectItem.name = "请选择";
                            selectItem.id = "";
                            selectItems.add(selectItem);
                        }
                        SelectItem selectItem = new SelectItem();
                        selectItem.name = collieryTeams.get(i).getSname();
                        selectItem.id = collieryTeams.get(i).getId();
                        selectItems.add(selectItem);
                    }
                    professionalAdapter = SpinnerAdapter.createFromResource(getActivity(), selectItems);
                    setProfessionalSpinner(spOther);
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "获取所属专业返回错误信息：" + content);
                com.example.administrator.riskprojects.tools.Utils.showLongToast(getActivity(), content);
            }
        });
    }

    //处理否设置
    private void setIshandleSpinner(Spinner spinner){
        List<SelectItem> selectItems = new ArrayList<>();
        SelectItem selectItem = new SelectItem();
        selectItem.name = "请选择";
        selectItem.id = "";
        SelectItem selectItemw = new SelectItem();
        selectItemw.name = "未处理";
        selectItemw.id = "0";
        SelectItem selectItemy = new SelectItem();
        selectItemy.name = "已处理";
        selectItemy.id = "1";
        selectItems.add(selectItem);
        selectItems.add(selectItemw);
        selectItems.add(selectItemy);
        isHandleAdapter = SpinnerAdapter.createFromResource(ctx, selectItems, Gravity.CENTER_VERTICAL | Gravity.LEFT);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                isHandleAdapter.setSelectedPostion(position);
                getHiddenDepartmentStatisticsMobile(((SelectItem)spOther.getSelectedItem()).id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spOther.setAdapter(isHandleAdapter);
    }

    //分析因素
    private void setAnalyticalSpinner(Spinner spinner){
        List<SelectItem> selectItems = new ArrayList<>();
        String[] analytical = {"隐患类型","隐患整改情况","专业类型","隐患处理状态","区域"};
        for (int i = 0; i < analytical.length; i++) {
            SelectItem selectItem = new SelectItem();
            selectItem.name = analytical[i];
            selectItem.id = Integer.toString(i);
            selectItems.add(selectItem);
        }
        analyticalAdapter = SpinnerAdapter.createFromResource(ctx, selectItems, Gravity.CENTER);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                analyticalAdapter.setSelectedPostion(position);
                getHiddenDangerSpecialStatistics(((SelectItem)spOther.getSelectedItem()).id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spOther.setAdapter(analyticalAdapter);
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
        monthAdapter = SpinnerAdapter.createFromResource(ctx, selectItems, Gravity.CENTER);
        spinner.setBackgroundColor(0x0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            spinner.setPopupBackgroundResource(R.drawable.shape_rectangle_rounded);
            spinner.setDropDownVerticalOffset(DensityUtil.dip2px(ctx, 3));
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                monthAdapter.setSelectedPostion(position);
                getFindHiddenDangerYearChartStatistics(spYear.getSelectedItemPosition(), spMonth.getSelectedItemPosition() + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner.setAdapter(monthAdapter);
    }

    private void setUpYearSpinner(Spinner spinner) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        int collieryTeamsint = 0;
        for (int i = 0; i < 10; i++) {
            SelectItem selectItem = new SelectItem();
            selectItem.name = Integer.toString(year--);
            selectItem.id = Integer.toString(i + 1);
            selectItems.add(selectItem);
        }
        yearAdapter = SpinnerAdapter.createFromResource(ctx, selectItems, Gravity.CENTER);
        spinner.setBackgroundColor(0x0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            spinner.setPopupBackgroundResource(R.drawable.shape_rectangle_rounded);
            spinner.setDropDownVerticalOffset(DensityUtil.dip2px(ctx, 3));
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                yearAdapter.setSelectedPostion(position);
                if (spMonth.getAdapter()!=null) {
                    getFindHiddenDangerYearChartStatistics(spYear.getSelectedItemPosition(), spMonth.getSelectedItemPosition() + 1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner.setAdapter(yearAdapter);
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

//    private void setHiddenRiskQueryStatisticsList(List<HiddenDangerRecord> recordList) {
//        if (!TextUtils.isEmpty(tvArea.getText().toString()) ||
//                !TextUtils.isEmpty(tvProfession.getText().toString()) ||
//                !TextUtils.isEmpty(tvHiddenUnits.getText().toString())) {
//            llOption.setVisibility(View.VISIBLE);
//        } else {
//            llOption.setVisibility(View.GONE);
//        }
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setLayoutManager(new LinearLayoutManager(ctx));
//        swipeRefreshLayout.setOnRefreshListener(this);
//        swipeRefreshLayout.setVisibility(View.VISIBLE);
//        HiddenRiskQueryStatisticsAdapter adapter = new HiddenRiskQueryStatisticsAdapter(recordList);
//        recyclerView.setAdapter(adapter);
//    }

//    private void setAllHiddenTroubleList(List<HomeHiddenRecord> dtatisticsList) {
//        if (!TextUtils.isEmpty(tvArea.getText().toString()) ||
//                !TextUtils.isEmpty(tvProfession.getText().toString()) ||
//                !TextUtils.isEmpty(tvHiddenUnits.getText().toString())) {
//            llOption.setVisibility(View.VISIBLE);
//        } else {
//            llOption.setVisibility(View.GONE);
//        }
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setLayoutManager(new LinearLayoutManager(ctx));
//        swipeRefreshLayout.setOnRefreshListener(this);
//        swipeRefreshLayout.setVisibility(View.VISIBLE);
//        HiddenDangerStatisticsAllAdapter adapter = new HiddenDangerStatisticsAllAdapter(dtatisticsList);
//        recyclerView.setAdapter(adapter);
//    }

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
//        if (TextUtils.isEmpty(hname)) {
//            Calendar cal = Calendar.getInstance();
//            int year = cal.get(Calendar.YEAR);
//            int month = cal.get(Calendar.MONTH);
//            int date = cal.get(Calendar.DATE);
//            hname = year + "-" + month + "-" + date;
//        }
//        tvProfession.setText(pname);
//        tvHiddenUnits.setText(hname);
//        pid = pidstr;
    }


    public void setIdFlag(String id, int flag) {
        //设置变量值，进行请求时使用
        flagint = flag;
        teamGroupCode = id;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DatePickerActivity.REQUEST && resultCode == RESULT_OK) {
            String date = data.getStringExtra(DatePickerActivity.DATE);
            tvdate.setText(date);
            page = 1;
            pagesize = 1;
            getDateByPage(1);
        }
    }
}
