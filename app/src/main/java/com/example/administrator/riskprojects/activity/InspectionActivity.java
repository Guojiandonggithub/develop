package com.example.administrator.riskprojects.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.riskprojects.Adpter.InspectionAdapter;
import com.example.administrator.riskprojects.BaseActivity;
import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.util.TimeCount;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InspectionActivity extends BaseActivity {

    private TextView txtLeft;
    private ImageView imgLeft;
    private TextView txtTitle;
    private ImageView imgRight;
    private TextView txtRight;
    private TextView tvName;
    private TextView tvTime;
    private RecyclerView recyclerView;
    private LinearLayoutCompat llBottom;
    private TextView tvOk;
    private TextView tvAccredit;
    private InspectionAdapter adapter;
    private TimeCount timeCount;
    private List<String> test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection);
        initView();
        setView();
        setUpData();
    }

    private void setUpData() {
        test = new ArrayList<>();
        adapter = new InspectionAdapter(test);
        recyclerView.setAdapter(adapter);
    }

    private void setView() {
        txtTitle.setText("巡检记录");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tvOk.isSelected()) {
                    //获取新记录
                    test.addAll(Arrays.asList("巡检时间 09:20", "巡检时间 09:25",
                            "巡检时间 09:30", "巡检时间 09:35"));
                    adapter.notifyDataSetChanged();
                    adapter.setWait(true);
                    adapter.notifyDataSetChanged();
                    //获取后倒计时
                    timeCount = new TimeCount(60000, 1000, tvOk);
                    timeCount.setListener(new TimeCount.OnTimeFinishListener() {
                        @Override
                        public void onTimeFinish() {
                            //倒计时结束，列表最后的巡检显示
                            adapter.setWait(false);
                            adapter.notifyDataSetChanged();
                        }
                    });
                    timeCount.start();


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
        tvName = findViewById(R.id.tv_name);
        tvTime = findViewById(R.id.tv_time);
        recyclerView = findViewById(R.id.recyclerView);
        llBottom = findViewById(R.id.ll_bottom);
        tvOk = findViewById(R.id.tv_ok);
        tvAccredit = findViewById(R.id.tv_accredit);
    }
}
