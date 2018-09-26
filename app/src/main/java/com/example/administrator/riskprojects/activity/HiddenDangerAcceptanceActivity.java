package com.example.administrator.riskprojects.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.example.administrator.riskprojects.Adpter.SpinnerAdapter;
import com.example.administrator.riskprojects.BaseActivity;
import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.bean.DataDictionary;
import com.example.administrator.riskprojects.bean.SelectItem;
import com.example.administrator.riskprojects.net.BaseJsonRes;
import com.example.administrator.riskprojects.net.NetClient;
import com.example.administrator.riskprojects.tools.Constants;
import com.example.administrator.riskprojects.tools.UserUtils;
import com.example.administrator.riskprojects.tools.Utils;
import com.example.administrator.riskprojects.util.DensityUtil;
import com.juns.health.net.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

public class HiddenDangerAcceptanceActivity extends BaseActivity {
    private static final String TAG = "HiddenDangerAcceptanceA";
    private TextView txtLeft;
    private ImageView imgLeft;
    private TextView txtTitle;
    private ImageView imgRight;
    private TextView txtRight;
    private Spinner spAcceptanceResults;
    private EditText etAddLocation;
    private EditText etContent;
    private LinearLayoutCompat llBottom;
    private TextView tvOk;
    private SpinnerAdapter spAcceptanceResultsAdapter;
    protected NetClient netClient;
    private String recheckPersonId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden_danger_acceptance);
        netClient = new NetClient(HiddenDangerAcceptanceActivity.this);
        initView();
        setView();
    }

    private void setView() {
        txtTitle.setText("隐患验收");
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        SelectItem selectItemw = new SelectItem();
        SelectItem selectItemy = new SelectItem();
        selectItemy.name = "未验收";
        selectItemy.id = "1";
        selectItemw.name = "已验收";
        selectItemw.id = "0";
        selectItems.add(selectItemw);
        selectItems.add(selectItemy);
        spAcceptanceResultsAdapter = SpinnerAdapter.createFromResource(this, selectItems);
        setUpSpinner(spAcceptanceResults, spAcceptanceResultsAdapter);
        Intent intent = getIntent();
        String recheckresult = intent.getStringExtra("recheckresult");
        String description = intent.getStringExtra("description");
        recheckPersonId = intent.getStringExtra("recheckPersonId");
        String recheckPersonName = intent.getStringExtra("recheckPersonName");
        spAcceptanceResultsAdapter.notifyDataSetChanged();
        if(!TextUtils.isEmpty(recheckresult)){
            spAcceptanceResults.setSelection(Integer.parseInt(recheckresult));
        }
        etContent.setText(description);
        if(TextUtils.isEmpty(recheckPersonId)){
            recheckPersonId = UserUtils.getUserID(HiddenDangerAcceptanceActivity.this);
            recheckPersonName = UserUtils.getUserName(HiddenDangerAcceptanceActivity.this);
        }
        etAddLocation.setText(recheckPersonName);
    }

    private void initView() {
        txtLeft = findViewById(R.id.txt_left);
        imgLeft = findViewById(R.id.img_left);
        txtTitle = findViewById(R.id.txt_title);
        imgRight = findViewById(R.id.img_right);
        txtRight = findViewById(R.id.txt_right);
        spAcceptanceResults = findViewById(R.id.sp_acceptance_results);
        etAddLocation = findViewById(R.id.et_add_location);
        etContent = findViewById(R.id.et_content);
        llBottom = findViewById(R.id.ll_bottom);
        tvOk = findViewById(R.id.tv_ok);
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRecheck();
            }
        });
    }



    private void setUpSpinner(Spinner spinner, final SpinnerAdapter adapter) {
        spinner.setBackgroundColor(0x0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            spinner.setPopupBackgroundResource(R.drawable.shape_rectangle_rounded);
            spinner.setDropDownVerticalOffset(DensityUtil.dip2px(this, 3));
        }
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adapter.setSelectedPostion(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //验收
    private void addRecheck() {
        SelectItem selectedItem =(SelectItem)spAcceptanceResults.getSelectedItem();
        RequestParams params = new RequestParams();
        String threeFixId = getIntent().getStringExtra("threeFixId");
        String description = etContent.getText().toString();
        params.put("id",threeFixId);
        params.put("recheckResult",selectedItem.id);
        params.put("description",description);
        params.put("recheckPersonId",recheckPersonId);
        params.put("recheckPersonName",etAddLocation.getText().toString());
        Log.e(TAG, "addRecheck: 隐患验收参数==="+params);
        netClient.post(Data.getInstance().getIp()+Constants.ADD_RECHECK, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "隐患验收返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    if (!TextUtils.isEmpty(data)) {
                        Utils.showLongToast(HiddenDangerAcceptanceActivity.this, "删除成功");
                    }
                    finish();
                }
            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "隐患验收返回错误信息：" + content);
                Utils.showLongToast(HiddenDangerAcceptanceActivity.this, content);
            }
        });
    }
}
