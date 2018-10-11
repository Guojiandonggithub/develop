package com.example.administrator.riskprojects.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.riskprojects.BaseActivity;
import com.example.administrator.riskprojects.R;
import com.github.chrisbanes.photoview.PhotoView;

public class ViewBIgPicActivity extends BaseActivity {

    private TextView txtLeft;
    private ImageView imgLeft;
    private TextView txtTitle;
    private ImageView imgRight;
    private TextView txtRight;
    private PhotoView photoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_big_pic);
        initView();
        setView();
    }

    private void setView() {
        txtTitle.setText("查看图片");
        Glide.with(this).load(getIntent().getStringExtra("url")).into(photoView);
    }

    private void initView() {
        txtLeft = findViewById(R.id.txt_left);
        imgLeft = findViewById(R.id.img_left);
        txtTitle = findViewById(R.id.txt_title);
        imgRight = findViewById(R.id.img_right);
        txtRight = findViewById(R.id.txt_right);
        photoView = findViewById(R.id.photo_view);
    }
}
