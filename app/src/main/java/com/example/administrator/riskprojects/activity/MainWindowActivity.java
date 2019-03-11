package com.example.administrator.riskprojects.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.administrator.riskprojects.Adpter.RiskAnalysisAdapter;
import com.example.administrator.riskprojects.Adpter.SpinnerAdapter;
import com.example.administrator.riskprojects.BaseActivity;
import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.bean.RiskCountJb;
import com.example.administrator.riskprojects.bean.SelectItem;
import com.example.administrator.riskprojects.common.NetUtil;
import com.example.administrator.riskprojects.net.BaseJsonRes;
import com.example.administrator.riskprojects.net.NetClient;
import com.example.administrator.riskprojects.tools.Constants;
import com.example.administrator.riskprojects.tools.UserUtils;
import com.example.administrator.riskprojects.tools.Utils;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.juns.health.net.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainWindowActivity extends BaseActivity {
    private static final String TAG = "MainWindowActivity";
    protected NetClient netClient;
    private TextView txtLeft;
    private ImageView imgLeft;
    private TextView txtTitle;
    private PieChart pieChartBig;
    private List<String> mineList = new ArrayList<>();
    private RecyclerView recyclerView;
    private String[] colorList = new String[]{"#3bbd0a", "#0068f7", "#21d7fb",
            "#f19ec2", "#80c269", "#facd89", "#c490bf", "#f39800", "#b28850", "#e5004f","#3bbd0a", "#0068f7", "#21d7fb",
            "#f19ec2", "#80c269", "#facd89", "#c490bf", "#f39800", "#b28850", "#e5004f"};
    RiskCountJb groupCountJb;
    private CardView cvSelectStartDate;
    private TextView tvStartDate;
    private CardView cvSelectEndDate;
    private TextView tvEndDate;
    private Spinner spOther;
    private SpinnerAdapter isHandleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_window);
        netClient = new NetClient(this);
        initView();
        setIshandleSpinner(spOther);
        initData();
    }

    private void initView() {
        txtLeft = findViewById(R.id.txt_left);
        imgLeft = findViewById(R.id.img_left);
        txtTitle = findViewById(R.id.txt_title);
        txtTitle.setText("统计分析");
        cvSelectStartDate = findViewById(R.id.cv_select_start_date);
        pieChartBig = findViewById(R.id.pieChart_big);
        tvStartDate = findViewById(R.id.tv_start_date);
        cvSelectEndDate = findViewById(R.id.cv_select_end_date);
        tvEndDate = findViewById(R.id.tv_end_date);
        recyclerView = findViewById(R.id.recyclerView);
        spOther = findViewById(R.id.sp_other);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cvSelectStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainWindowActivity.this.startActivityForResult(new Intent(MainWindowActivity.this, DatePickerActivity.class), DatePickerActivity.STARTREQUEST);
            }
        });
        cvSelectEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainWindowActivity.this.startActivityForResult(new Intent(MainWindowActivity.this, DatePickerActivity.class), DatePickerActivity.ENDREQUEST);
            }
        });
    }

    private void initData(){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        if (month < 10) {
            String monthstr = "0" + month;
            month = Integer.parseInt(monthstr);
        }
        int date = cal.get(Calendar.DATE);
        final String sname = year + "-" + (month + 1) + "-01";
        final String fname = year + "-" + (month + 1) + "-" + date;
        tvStartDate.setText(sname);
        tvEndDate.setText(fname);
        getGroupCountJb();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DatePickerActivity.ENDREQUEST && resultCode == RESULT_OK) {
            String date = data.getStringExtra(DatePickerActivity.DATE);
            tvEndDate.setText(date.substring(0,10));
            getGroupCountJb();
        }
        if (requestCode == DatePickerActivity.STARTREQUEST && resultCode == RESULT_OK) {
            String date = data.getStringExtra(DatePickerActivity.DATE);
            tvStartDate.setText(date.substring(0,10));
            getGroupCountJb();
        }
    }

    //获取总局隐患等级
    private void getGroupCountJb() {
        if (!NetUtil.checkNetWork(MainWindowActivity.this)) {
            String jsondata = Utils.getValue(MainWindowActivity.this, Constants.GET_RISKANALYSIS_LIST);
            if("".equals(jsondata)){
                Utils.showShortToast(MainWindowActivity.this, "没有联网，没有请求到数据");
            }else{
                resultGroupCountJb(jsondata);
            }
        }else{
            Map<String, String> paramsMap = new HashMap();
            RequestParams params = new RequestParams();
            paramsMap.put("customParamsOne",tvStartDate.getText().toString());
            paramsMap.put("customParamsTwo",tvEndDate.getText().toString());
            String three = "0";
            SelectItem selectItem = (SelectItem) spOther.getSelectedItem();
            if (null != selectItem&&!TextUtils.isEmpty(selectItem.id)) {
                if (Integer.parseInt(selectItem.id)>=0) {
                   three = selectItem.id;//状态
                }
            }
            paramsMap.put("customParamsThree",three);
            paramsMap.put("customParamsTen", UserUtils.getUserID(MainWindowActivity.this));//状态
            String jsonString = JSON.toJSONString(paramsMap);
            params.put("evaluationJson", jsonString);
            netClient.post(Constants.MAIN_ENGINE+Constants.GET_RISKANALYSIS_LIST, params, new BaseJsonRes() {

                @Override
                public void onMySuccess(String data) {
                    Log.i(TAG, "总局隐患等级返回数据：" + data);
                    if (!TextUtils.isEmpty(data)) {
                        resultGroupCountJb(data);
                    }

                }

                @Override
                public void onMyFailure(String content) {
                    Log.e(TAG, "总局隐患等级返回错误信息：" + content);
                    Utils.showShortToast(MainWindowActivity.this, content);
                }
            });
        }
    }


    private void resultGroupCountJb(String data){
        List<RiskCountJb> tempList = JSONArray.parseArray(data, RiskCountJb.class);
        initBigChartView(tempList);
    }

    private void initBigChartView( List<RiskCountJb> groupCountJb) {
        pieChartBig.clear();
        //设置标题
        ArrayList<String> titles = new ArrayList<>();
        for (int i = 0; i < groupCountJb.size(); i++) {
            titles.add(Integer.toString(i));
        }
        ArrayList<PieEntry> entrys = new ArrayList<>();
        int[] color = new int[groupCountJb.size()];
        List<RiskCountJb> riskCountJbList = new ArrayList<>();
        for (int i = 0; i < groupCountJb.size(); i++) {
            RiskCountJb riskCountJb = new RiskCountJb();
            entrys.add(new PieEntry(Integer.parseInt(groupCountJb.get(i).getTotal()),groupCountJb.get(i).getRiskGname()));
            color[i] = Color.parseColor(colorList[i]);
            riskCountJb.setRiskGname(groupCountJb.get(i).getRiskGname()+"：");
            riskCountJb.setColor(Color.parseColor(colorList[i]));
            riskCountJb.setTotal(groupCountJb.get(i).getTotal());
            riskCountJbList.add(riskCountJb);
        }
        recyclerView.setAdapter(new RiskAnalysisAdapter(riskCountJbList,MainWindowActivity.this));
        //饼图数据集
        PieDataSet dataset = new PieDataSet(entrys, "星级评定");
        //饼图Item被选中时变化的距离
        dataset.setSelectionShift(0f);
        //颜色填充
       /* dataset.setColors(new int[]{Color.parseColor("#4808da"),
                Color.parseColor("#ff7737"),
                Color.parseColor("#30bd08"),
                Color.parseColor("#da089c")});*/
        dataset.setColors(color);
        //数据填充
        PieData piedata = new PieData(dataset);
        //设置饼图上显示数据的字体大小
        piedata.setValueTextSize(15);
        piedata.setValueTextColor(Color.TRANSPARENT);

        pieChartBig.setData(piedata);
        Description description = new Description();
        description.setText("");
        pieChartBig.setDescription(description);
        pieChartBig.setUsePercentValues(false);
        pieChartBig.setRotationAngle(270);
        pieChartBig.setDrawHoleEnabled(true);       //Boolean类型   设置图饼中心是否是空心
        pieChartBig.setHoleColor(Color.WHITE);       //Boolean类型   设置图饼中心是否是空心
        pieChartBig.setDrawSliceText(false);
        pieChartBig.setTransparentCircleColor(Color.parseColor("#1bccfa"));//设置环形图与中间空心圆之间的环形的颜色
        pieChartBig.setEntryLabelColor(Color.TRANSPARENT);
        pieChartBig.setTransparentCircleAlpha(100);//设置环形图与中间空心圆之间圆环的的透明度
        pieChartBig.setHoleRadius(50);//设置圆孔半径
        pieChartBig.setTransparentCircleRadius(0);//设置半透明圈的宽度
        pieChartBig.setDrawCenterText(false);         //Boolean类型    设置中心圆是否可以显示文字
        Legend l = pieChartBig.getLegend();
        pieChartBig.getLegend().setForm(Legend.LegendForm.CIRCLE);//获取饼图图例
        pieChartBig.notifyDataSetChanged();
        l.setEnabled(false);
    }

    //处理否设置
    private void setIshandleSpinner(Spinner spinner) {
        List<SelectItem> selectItems = new ArrayList<>();
        SelectItem selectItem = new SelectItem();
        selectItem.name = "管控关闭";
        selectItem.id = "0";
        SelectItem selectItemw = new SelectItem();
        selectItemw.name = "管控开始";
        selectItemw.id = "1";
        SelectItem selectItemy = new SelectItem();
        selectItemy.name = "未管控";
        selectItemy.id = "2";
        selectItems.add(selectItem);
        selectItems.add(selectItemw);
        selectItems.add(selectItemy);
        isHandleAdapter = SpinnerAdapter.createFromResource(MainWindowActivity.this, selectItems, Gravity.CENTER_VERTICAL | Gravity.LEFT);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                isHandleAdapter.setSelectedPostion(position);
                getGroupCountJb();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spOther.setAdapter(isHandleAdapter);
    }
}
