package com.example.administrator.riskprojects.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.example.administrator.riskprojects.R;
import com.khdz.patrol.bleSdk.BleScanner;
import com.khdz.patrol.bleSdk.IScanCardCallback;

import java.util.Locale;

/**
 * Administrator 在 2018/9/26 创建
 */

public class BleSdkActivity extends AppCompatActivity {
    private BleScanner mBleScanner;

    private StringBuilder mStringBuilder = new StringBuilder();
    private TextView mTvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ble_sdk);
        mTvResult = findViewById(R.id.tvResult);
        mTvResult.setMovementMethod(new ScrollingMovementMethod() {
        });
        //初始化BLE扫描器
        mBleScanner = new BleScanner(this);
        //在扫描过程中如果蓝牙关闭并重新打开后是否自动开始扫描, true:将在重新打开蓝牙后,自动开始扫描
        mBleScanner.setAutoContinue(true);
    }

    public void onBtnClick(View view) {
        switch (view.getId()) {
            case R.id.btnStartScan:
                try {
                    //开始扫描,设置监听
                    boolean startScanSuccessful = mBleScanner.StartScan(new IScanCardCallback() {
                        @Override
                        public void onScanCard(String card, boolean isLowPower) {
                            addResult("扫描到蓝牙卡,卡号[" + card + "],电量[:" + (isLowPower ? "不足]" : "充足]"));
                        }
                    }, BleScanner.ScanMode.SCAN_MODE_LOW_LATENCY);
                    if (startScanSuccessful) {
                        addResult("---开始扫描---");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    addResult(e.toString());
                }
                break;
            case R.id.btnStopScan:
                mBleScanner.StopScan();
                addResult("---停止扫描---");
                break;
            case R.id.btnRequestPermission:
                //当版本>=6.0时,需要弹窗请求用户授予权限
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions();
                } else {
                    addResult("当前手机版本低于6.0,无需手动请求权限");
                }
                break;
        }
    }

    //region 申请位置权限,安卓手机6.0以上读蓝牙广播需要该权限
    public final static int REQUEST_BLE_PATROL_PERMISSION = 1001;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestPermissions() {
        //判断是否已有权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //申请权限 位置权限
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_BLE_PATROL_PERMISSION);
        } else {
            addResult("APP已有权限,无需再次申请");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_BLE_PATROL_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    addResult("用户接受了[位置]权限");
                } else {
                    addResult("用户拒绝了[位置]权限");
                }
                break;
        }
    }
    //endregion

    private void addResult(final String result) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss.SSS    ", Locale.getDefault());
        String timeStr = format.format(Calendar.getInstance().getTime());
        mStringBuilder.append(timeStr).append(result).append("\n");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTvResult.setText(mStringBuilder.toString());
            }
        });
    }
}
