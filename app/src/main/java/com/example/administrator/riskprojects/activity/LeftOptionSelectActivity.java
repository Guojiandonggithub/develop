package com.example.administrator.riskprojects.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.example.administrator.riskprojects.Adpter.SpinnerAdapter;
import com.example.administrator.riskprojects.BaseActivity;
import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.bean.Area;
import com.example.administrator.riskprojects.bean.CollieryTeam;
import com.example.administrator.riskprojects.bean.SelectItem;
import com.example.administrator.riskprojects.bean.Specialty;
import com.example.administrator.riskprojects.net.BaseJsonRes;
import com.example.administrator.riskprojects.net.NetClient;
import com.example.administrator.riskprojects.tools.Constants;
import com.example.administrator.riskprojects.tools.UserUtils;
import com.example.administrator.riskprojects.tools.Utils;
import com.example.administrator.riskprojects.util.DensityUtil;
import com.juns.health.net.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

public class LeftOptionSelectActivity extends BaseActivity {
    public static final String TITLE = "title";

    public static final String A_ID = "a_id";
    public static final String A_NAME = "a_name";

    public static final String H_ID = "h_id";
    public static final String H_NAME = "h_name";

    public static final String P_ID = "p_id";
    public static final String P_NAME = "p_name";

    public static final int REQUEST_CODE = 9;
    public static final int FLAG_AREA = 0;
    public static final int FLAG_PROFESSION = 1;
    public static final int FLAG_HIDDENUNITS = 2;
    private static final String TAG = "LeftOptionSelect";
    private TextView txtLeft;
    private ImageView imgLeft;
    private TextView txtTitle;
    private ImageView imgRight;
    private TextView txtRight;
    private TextView tvProfession;
    private Spinner spinnerProfession;
    private TextView tvHiddenUnits;
    private Spinner spinnerHiddenUnits;
    private TextView tvArea;
    private Spinner spinnerArea;
    private SpinnerAdapter mSpHiddenUnitsAdapter;
    private SpinnerAdapter spProfessionalAdapter;
    private SpinnerAdapter spHiddenAreaAdapter;
    protected NetClient netClient;
    private LinearLayoutCompat llBottom;
    private TextView tvOk;
    private TextView tvProfessionResult;
    private TextView tvHiddenUnitsResult;
    private TextView tvAreaResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_left_option_select);
        netClient = new NetClient(this);
        initView();
        setView();
    }

    private void setView() {
        txtTitle.setText(getIntent().getStringExtra(TITLE));
        getArea();
        getCollieryTeam();
        getSpecialty();
        tvProfession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvProfession.isSelected()) {
                    tvProfession.setSelected(false);
                } else {
                    tvProfession.setSelected(true);
                    tvArea.setSelected(false);
                    tvHiddenUnits.setSelected(false);
                    spinnerProfession.performClick();
                }
            }
        });

        tvArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvArea.isSelected()) {
                    tvArea.setSelected(false);
                } else {
                    tvProfession.setSelected(false);
                    tvArea.setSelected(true);
                    tvHiddenUnits.setSelected(false);
                    spinnerArea.performClick();
                }
            }
        });

        tvHiddenUnits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvHiddenUnits.isSelected()) {
                    tvHiddenUnits.setSelected(false);
                } else {
                    tvProfession.setSelected(false);
                    tvArea.setSelected(false);
                    tvHiddenUnits.setSelected(true);
                    spinnerHiddenUnits.performClick();
                }
            }
        });
    }

    private void initView() {
        txtLeft = findViewById(R.id.txt_left);
        imgLeft = findViewById(R.id.img_left);
        txtTitle = findViewById(R.id.txt_title);
        imgRight = findViewById(R.id.img_right);
        txtRight = findViewById(R.id.txt_right);
        tvProfession = findViewById(R.id.tv_profession);
        spinnerProfession = findViewById(R.id.spinner_profession);
        tvHiddenUnits = findViewById(R.id.tv_hidden_units);
        spinnerHiddenUnits = findViewById(R.id.spinner_hidden_units);
        tvArea = findViewById(R.id.tv_area);
        spinnerArea = findViewById(R.id.spinner_area);

        llBottom = findViewById(R.id.ll_bottom);
        tvOk = findViewById(R.id.tv_ok);
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinnerArea.getAdapter() != null
                        && spinnerHiddenUnits.getAdapter() != null && spinnerProfession.getAdapter() != null) {
                    Intent intent = new Intent();
                    intent.putExtra(A_ID, ((SelectItem) spHiddenAreaAdapter.getItem(spinnerArea.getSelectedItemPosition())).id);
                    intent.putExtra(A_NAME, ((SelectItem) spHiddenAreaAdapter.getItem(spinnerArea.getSelectedItemPosition())).name);
                    intent.putExtra(H_ID, ((SelectItem) mSpHiddenUnitsAdapter.getItem(spinnerHiddenUnits.getSelectedItemPosition())).id);
                    intent.putExtra(H_NAME, ((SelectItem) mSpHiddenUnitsAdapter.getItem(spinnerHiddenUnits.getSelectedItemPosition())).name);
                    intent.putExtra(P_ID, ((SelectItem) spProfessionalAdapter.getItem(spinnerProfession.getSelectedItemPosition())).id);
                    intent.putExtra(P_NAME, ((SelectItem) spProfessionalAdapter.getItem(spinnerProfession.getSelectedItemPosition())).name);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
        tvProfessionResult = findViewById(R.id.tv_profession_result);
        tvHiddenUnitsResult = findViewById(R.id.tv_hidden_units_result);
        tvAreaResult = findViewById(R.id.tv_area_result);
    }


    private void setUpSpinner(Spinner spinner, final SpinnerAdapter adapter, final List<SelectItem> selectItems, final int flag) {
        spinner.setBackgroundColor(0x0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            spinner.setPopupBackgroundResource(R.drawable.shape_rectangle_rounded);
            spinner.setDropDownVerticalOffset(DensityUtil.dip2px(this, 3));
        }
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adapter.setSelectedPostion(position); switch (flag) {
                    case FLAG_AREA:
                        tvAreaResult.setText(((SelectItem) spHiddenAreaAdapter.getItem(spinnerArea.getSelectedItemPosition())).name);
                        break;
                    case FLAG_HIDDENUNITS:
                        tvHiddenUnitsResult.setText(((SelectItem) mSpHiddenUnitsAdapter.getItem(spinnerHiddenUnits.getSelectedItemPosition())).name);
                        break;
                    case FLAG_PROFESSION:
                        tvProfessionResult.setText(((SelectItem) spProfessionalAdapter.getItem(spinnerProfession.getSelectedItemPosition())).name);
                        break;

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                switch (flag) {
                    case FLAG_AREA:
                        tvArea.setSelected(false);
                        break;
                    case FLAG_HIDDENUNITS:
                        tvHiddenUnits.setSelected(false);
                        break;
                    case FLAG_PROFESSION:
                        tvProfession.setSelected(false);
                        break;

                }
            }
        });
    }


    //获取区域
    private void getArea() {
        RequestParams params = new RequestParams();
        params.put("employeeId", UserUtils.getUserID(LeftOptionSelectActivity.this));
        netClient.post(Data.getInstance().getIp()+Constants.GET_AREA, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "获取区域返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    List<Area> collieryTeams = JSONArray.parseArray(data, Area.class);
                    List<SelectItem> selectItems = new ArrayList<SelectItem>();
                    for (int i = 0; i < collieryTeams.size(); i++) {
                        SelectItem selectItem = new SelectItem();
                        selectItem.name = collieryTeams.get(i).getAreaName();
                        selectItem.id = collieryTeams.get(i).getId();
                        selectItems.add(selectItem);
                    }
                    spHiddenAreaAdapter = SpinnerAdapter.createFromResource(LeftOptionSelectActivity.this, selectItems);
                    setUpSpinner(spinnerArea, spHiddenAreaAdapter, selectItems, FLAG_AREA);
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "获取区域返回错误信息：" + content);
                Utils.showLongToast(LeftOptionSelectActivity.this, content);
            }
        });
    }


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
                        SelectItem selectItem = new SelectItem();
                        selectItem.name = collieryTeams.get(i).getSname();
                        selectItem.id = collieryTeams.get(i).getId();
                        selectItems.add(selectItem);
                    }
                    spProfessionalAdapter = SpinnerAdapter.createFromResource(LeftOptionSelectActivity.this, selectItems);
                    setUpSpinner(spinnerProfession, spProfessionalAdapter, selectItems, FLAG_PROFESSION);
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "获取所属专业返回错误信息：" + content);
                Utils.showShortToast(LeftOptionSelectActivity.this, content);
            }
        });
    }


    //获取部门/队组成员
    private void getCollieryTeam() {
        RequestParams params = new RequestParams();
        params.put("employeeId", UserUtils.getUserID(LeftOptionSelectActivity.this));
        netClient.post(Data.getInstance().getIp()+Constants.GET_COLLIERYTEAM, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "获取部门/队组成员返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    List<CollieryTeam> collieryTeams = JSONArray.parseArray(data, CollieryTeam.class);
                    List<SelectItem> selectItems = new ArrayList<SelectItem>();
                    for (int i = 0; i < collieryTeams.size(); i++) {
                        SelectItem selectItem = new SelectItem();
                        selectItem.name = collieryTeams.get(i).getTeamName().replaceAll("&nbsp;", "   ");
                        selectItem.id = collieryTeams.get(i).getId();
                        selectItems.add(selectItem);
                    }
                    mSpHiddenUnitsAdapter = SpinnerAdapter.createFromResource(LeftOptionSelectActivity.this, selectItems, Gravity.CENTER_VERTICAL|Gravity.LEFT);
                    setUpSpinner(spinnerHiddenUnits, mSpHiddenUnitsAdapter, selectItems, FLAG_HIDDENUNITS);
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "获取部门/队组成员返回错误信息：" + content);
                Utils.showShortToast(LeftOptionSelectActivity.this, content);
            }
        });
    }


}
