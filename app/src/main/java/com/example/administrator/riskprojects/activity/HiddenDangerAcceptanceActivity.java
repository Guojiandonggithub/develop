package com.example.administrator.riskprojects.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.administrator.riskprojects.Adpter.SpinnerAdapter;
import com.example.administrator.riskprojects.BaseActivity;
import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.util.DensityUtil;

public class HiddenDangerAcceptanceActivity extends BaseActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden_danger_acceptance);
        initView();
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
}
