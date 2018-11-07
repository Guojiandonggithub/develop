package com.example.administrator.riskprojects.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.riskprojects.BaseActivity;
import com.example.administrator.riskprojects.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DatePickerActivity extends BaseActivity {

    public static final String DATE = "date";
    public static final int REQUEST = 2018;
    private TextView mTxtLeft;
    private ImageView mImgLeft;
    private TextView mTxtTitle;
    private ImageView mImgRight;
    private TextView mTxtRight;
    private MaterialCalendarView mCalendarView;
    private LinearLayoutCompat mLlBottom;
    private TextView mTvOk;
    CalendarDay selectDate;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);
        initView();
        setView();
    }

    private void setView() {
        mTxtTitle.setText("选择日期");
        mCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                mLlBottom.setVisibility(View.VISIBLE);
                selectDate = date;
            }
        });
        mCalendarView.addDecorator(new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                return true;
            }

            @Override
            public void decorate(DayViewFacade view) {
                Drawable drawable = getResources().getDrawable(R.drawable.shape_rectangle_rounded_solid_blue);
                view.setSelectionDrawable(drawable);
            }
        });

        mTvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectDate != null) {
                    Intent intent = new Intent();
                    intent.putExtra(DATE, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(selectDate.getDate()));
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(DatePickerActivity.this, "请选择日期", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        mTxtLeft = findViewById(R.id.txt_left);
        mImgLeft = findViewById(R.id.img_left);
        mTxtTitle = findViewById(R.id.txt_title);
        mImgRight = findViewById(R.id.img_right);
        mTxtRight = findViewById(R.id.txt_right);
        mCalendarView = findViewById(R.id.calendarView);
        mLlBottom = findViewById(R.id.ll_bottom);
        mTvOk = findViewById(R.id.tv_ok);
    }

    public static void startPickDate(Activity activity, Context context) {
        activity.startActivityForResult(new Intent(context, DatePickerActivity.class), REQUEST);
    }
}
