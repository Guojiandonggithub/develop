package com.example.administrator.riskprojects.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSONArray;
import com.example.administrator.riskprojects.Adpter.ListingSupervisionAdapter;
import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.bean.HiddenDangerRecord;
import com.example.administrator.riskprojects.bean.HomeHiddenRecord;
import com.example.administrator.riskprojects.net.BaseJsonRes;
import com.example.administrator.riskprojects.net.NetClient;
import com.example.administrator.riskprojects.tools.Constants;
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
import java.util.List;
import java.util.Random;

public class Fragment_Statistics extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    // 数据统计
    private static final String TAG = "Fragment_Statistics";
    private Activity ctx;
    private View layout;
    private BarChart mBarChart;
    private LineChart mLineChart;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    protected NetClient netClient;

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
        mSwipeRefreshLayout = layout.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        initBarChart(mBarChart);
        initLineChart(mLineChart);
        getHiddenStatisticsData();
    }

    private void showLineChart(List<HomeHiddenRecord> dtatisticsList) {
        //设置数据
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < dtatisticsList.size(); i++) {
            float num = Float.parseFloat(dtatisticsList.get(i).getTotalNum());
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


    public void showBarChart(List<HomeHiddenRecord> dtatisticsList) {
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();

        for (int i = 0; i < dtatisticsList.size(); i++) {
            /**
             * 此处还可传入Drawable对象 BarEntry(float x, float y, Drawable icon)
             * 即可设置柱状图顶部的 icon展示
             */
            ArrayList<BarEntry> entries = new ArrayList<>();
            //i: 位置  (float) new Random().nextInt(100):值
            float num = Float.parseFloat(dtatisticsList.get(i).getTotalNum());
            BarEntry barEntry = new BarEntry(i, num);
            entries.add(barEntry);
            // 每一个BarDataSet代表一类柱状图
            BarDataSet barDataSet = new BarDataSet(entries, dtatisticsList.get(i).getTeamGroupName());
            initBarDataSet(barDataSet, getRandColor());
            dataSets.add(barDataSet);

        }
        // 添加多个BarDataSet时
        BarData data = new BarData(dataSets);
        //BarChart控件宽度 / 柱状图数量  * mBarWidth
        data.setBarWidth(0.5f);
        mBarChart.setData(data);
    }

    private void getHiddenStatisticsData() {
        RequestParams params = new RequestParams();
        //params.put("customParamsOne","");//开始时间
        //params.put("customParamsTwo", "");//结束时间
        netClient.post(Constants.TEAMHDSTAISTICSDATAGRID, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "挂牌督办隐患数据返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    List<HomeHiddenRecord> dtatisticsList = JSONArray.parseArray(data, HomeHiddenRecord.class);
                    showBarChart(dtatisticsList);
                    showLineChart(dtatisticsList);
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "挂牌督办隐患数据返回错误信息：" + content);
                com.example.administrator.riskprojects.tools.Utils.showLongToast(getContext(), content);
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
                break;
            case R.id.ll_chart_02:
                break;
            case R.id.ll_chart_03:
                break;
            case R.id.ll_chart_04:
                break;
            case R.id.ll_chart_05:
                break;
            case R.id.ll_chart_06:
                break;
            case R.id.ll_chart_07:
                break;
            case R.id.ll_chart_08:
                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
