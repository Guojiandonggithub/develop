package com.example.administrator.riskprojects.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.riskprojects.BaseActivity;
import com.example.administrator.riskprojects.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
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
                Intent intent = new Intent();
                intent.putExtra(DATE, new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(date.getDate()));
                setResult(RESULT_OK, intent);
                finish();
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
    }

    private void initView() {
        mTxtLeft = findViewById(R.id.txt_left);
        mImgLeft = findViewById(R.id.img_left);
        mTxtTitle = findViewById(R.id.txt_title);
        mImgRight = findViewById(R.id.img_right);
        mTxtRight = findViewById(R.id.txt_right);
        mCalendarView = findViewById(R.id.calendarView);
    }

    public static void startPickDate(Activity activity,Context context) {
        activity.startActivityForResult(new Intent(context,DatePickerActivity.class),REQUEST);
    }
}
