package com.example.administrator.riskprojects.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.dialog.FlippingLoadingDialog;
import com.example.administrator.riskprojects.dialog.WarnTipDialog;
import com.example.administrator.riskprojects.fragment.Fragment_Home;
import com.example.administrator.riskprojects.fragment.Fragment_Record_Manage;
import com.example.administrator.riskprojects.fragment.Fragment_Statistics;
import com.example.administrator.riskprojects.fragment.Fragment_Supervision;
import com.example.administrator.riskprojects.fragment.Fragment_mine;
import com.example.administrator.riskprojects.net.NetworkConnectChangedReceiver;
import com.example.administrator.riskprojects.tools.UserUtils;
import com.example.administrator.riskprojects.util.UpdateVersionUtil;
import com.example.administrator.riskprojects.view.MyAlertDialog;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

import static com.example.administrator.riskprojects.activity.TagAliasOperatorHelper.ACTION_SET;
import static com.example.administrator.riskprojects.activity.TagAliasOperatorHelper.sequence;

public class MainActivity extends FragmentActivity {
    private TextView txt_title;
    private TextView txt_title_right;
    private ImageView img_left;
    private ImageView img_right;
    private WarnTipDialog Tipdialog;
    protected static final String TAG = "MainActivity";
    private Fragment[] fragments;
    public Fragment_Home homefragment;
    private Fragment_Supervision supervisionfragment;
    private Fragment_Record_Manage manageFragment;
    private Fragment_Statistics statisticsfragment;
    private Fragment_mine minefragment;
    private ImageView[] imagebuttons;
    private TextView[] textviews;
    private String connectMsg = "aa";
    public int index;
    private int currentTabIndex;// 当前fragment的index


    private LinearLayoutCompat mLlDialog;
    private LinearLayoutCompat mLlManageDetail;
    private LinearLayoutCompat mLlManageRelease;
    private LinearLayoutCompat mLlManageRectification;
    private LinearLayoutCompat mLlManageTracking;
    private LinearLayoutCompat mLlManageOverdue;
    private LinearLayoutCompat mLlManageReview;
    private View.OnClickListener menuListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onMenuClicked(v);
        }
    };
    private LinearLayoutCompat mLlManage;
    private LinearLayoutCompat mLlChart;
    public static boolean isForeground = false;
    protected FlippingLoadingDialog mLoadingDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById();
        initTabView();
        initPopWindow();
        receiver();
        setUpAlias();
        registerMessageReceiver();
        new UpdateVersionUtil().versionUpdata(MainActivity.this, true);
//        upDateVersion();
    }

    @Override
    protected void onStart() {
        super.onStart();
        handlePushMessage(getIntent());
    }

    public FlippingLoadingDialog getLoadingDialog(String msg,Context context) {
        if (mLoadingDialog == null)
            mLoadingDialog = new FlippingLoadingDialog(context, msg);
        return mLoadingDialog;
    }

    private void setUpAlias() {
        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
        tagAliasBean.action = ACTION_SET;
        sequence++;
        tagAliasBean.alias = UserUtils.getUserID(this);
        tagAliasBean.isAliasAction = true;
        TagAliasOperatorHelper.getInstance().handleAction(getApplicationContext(), sequence, tagAliasBean);
    }


    private void upDateVersion() {
        new UpdateVersionUtil().versionUpdata(this, true);
    }


    private void initTabView() {
        homefragment = new Fragment_Home();
        supervisionfragment = new Fragment_Supervision();
        statisticsfragment = new Fragment_Statistics();
        manageFragment = new Fragment_Record_Manage();
        minefragment = new Fragment_mine();
        fragments = new Fragment[]{homefragment, supervisionfragment,
                manageFragment, statisticsfragment, minefragment};
        imagebuttons = new ImageView[5];
        txt_title_right = findViewById(R.id.txt_title_right);
        imagebuttons[0] = (ImageView) findViewById(R.id.ib_contact_list);
        imagebuttons[1] = (ImageView) findViewById(R.id.ib_find);
        imagebuttons[2] = (ImageView) findViewById(R.id.iv_analysis);
        imagebuttons[3] = (ImageView) findViewById(R.id.ib_weixin);
        imagebuttons[4] = (ImageView) findViewById(R.id.ib_profile);
        txt_title_right.setVisibility(View.VISIBLE);
        txt_title_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, InspectionActivity.class));
            }
        });
        imagebuttons[0].setSelected(true);
        textviews = new TextView[5];
        textviews[0] = (TextView) findViewById(R.id.tv_contact_list);
        textviews[1] = (TextView) findViewById(R.id.tv_find);
        textviews[2] = (TextView) findViewById(R.id.tv_analysis);
        textviews[3] = (TextView) findViewById(R.id.tv_weixin);
        textviews[4] = (TextView) findViewById(R.id.tv_profile);
        textviews[0].setTextColor(getResources().getColor(R.color.blue1));
        // 添加显示第一个fragment
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, homefragment)
                .add(R.id.fragment_container, supervisionfragment)
                .add(R.id.fragment_container, manageFragment)
                .add(R.id.fragment_container, statisticsfragment)
                .add(R.id.fragment_container, minefragment)
                .hide(supervisionfragment).hide(manageFragment)
                .hide(statisticsfragment).hide(minefragment).show(homefragment).commit();
    }

    public void onTabClicked(View view) {
        //img_right.setVisibility(View.GONE);
        switch (view.getId()) {
            case R.id.re_contact_list:
                //img_right.setVisibility(View.VISIBLE);
                index = 0;
                if (homefragment != null) {
                    homefragment.refresh();
                }
                txt_title.setText(R.string.home);
                txt_title_right.setVisibility(View.VISIBLE);
                img_left.setVisibility(View.GONE);
                img_right.setVisibility(View.GONE);
                break;
            case R.id.re_find:
                index = 1;
                txt_title.setText(R.string.guapai);
                txt_title_right.setVisibility(View.GONE);
                img_left.setVisibility(View.GONE);
                img_right.setVisibility(View.GONE);
                break;
            case R.id.analysis:
                index = 2;
                txt_title.setText(manageFragment.getTitle());
                txt_title_right.setVisibility(View.GONE);
                img_left.setVisibility(manageFragment.getAddVisible());
                img_right.setVisibility(View.VISIBLE);
                break;
            case R.id.re_weixin:
                index = 3;
                txt_title.setText(statisticsfragment.getTitle());
                txt_title_right.setVisibility(View.GONE);
                img_left.setVisibility(View.GONE);
                img_right.setVisibility(View.VISIBLE);
                break;
            case R.id.re_profile:
                index = 4;
                txt_title.setText(R.string.me);
                txt_title_right.setVisibility(View.GONE);
                img_left.setVisibility(View.GONE);
                img_right.setVisibility(View.GONE);
                break;
        }
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager()
                    .beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            System.out.println("fragments.isadd" + fragments[index].getClass().getSimpleName() + ":" + Boolean.toString(fragments[index].isAdded()));
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_container, fragments[index]);
            }
            System.out.println("index:" + Integer.toString(index));
            System.out.println("getSimpleName:" + fragments[index].getClass().getSimpleName());
            trx.show(fragments[index]).commit();
        }
        imagebuttons[currentTabIndex].setSelected(false);
        // 把当前tab设为选中状态
        imagebuttons[index].setSelected(true);
        textviews[currentTabIndex].setTextColor(getResources().getColor(R.color.colorGraylighter));
        textviews[index].setTextColor(getResources().getColor(R.color.blue1));
        currentTabIndex = index;
    }


    private void initPopWindow() {

    }

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }

    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void findViewById() {
        txt_title = findViewById(R.id.txt_title);
        txt_title.setText(R.string.home);
        img_left = findViewById(R.id.img_left);
        img_right = findViewById(R.id.img_right);
        img_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index == 2) {
                    startActivity(new Intent(MainActivity.this, HiddenRiskRecordAddEditActivity.class));
                }
            }
        });
        img_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLlDialog.getVisibility() == View.VISIBLE) {
                    mLlDialog.setVisibility(View.GONE);
                } else {
                    if (index == 2) {
                        mLlManage.setVisibility(View.VISIBLE);
                        mLlChart.setVisibility(View.GONE);
                    } else if (index == 3) {
                        mLlManage.setVisibility(View.GONE);
                        mLlChart.setVisibility(View.VISIBLE);
                    }
                    mLlDialog.setVisibility(View.VISIBLE);
                }

            }
        });

        mLlDialog = findViewById(R.id.ll_dialog);
        mLlDialog.setOnClickListener(menuListener);
        mLlManage = findViewById(R.id.ll_manage);
        mLlChart = findViewById(R.id.ll_chart);   //img_right = (ImageView) findViewById(R.id.img_right);
    }

	/*private void initVersion() {
		// TODO 检查版本更新
		String versionInfo = Utils.getValue(this, Constants.VersionInfo);
		if (!TextUtils.isEmpty(versionInfo)) {
			Tipdialog = new WarnTipDialog(this,
					"发现新版本：  1、更新啊阿三达到阿德阿   2、斯顿阿斯顿撒旦？");
			Tipdialog.setBtnOkLinstener(onclick);
			Tipdialog.show();
		}
	}*/


    public void onMenuClicked(View view) {
        //img_right.setVisibility(View.GONE);
        switch (view.getId()) {
            case R.id.ll_manage_detail:
            case R.id.ll_manage_release:
            case R.id.ll_manage_rectification:
            case R.id.ll_manage_tracking:
            case R.id.ll_manage_overdue:
            case R.id.ll_manage_review:
                manageFragment.onRightMenuClicked(view);
                img_left.setVisibility(manageFragment.getAddVisible());
                txt_title.setText(manageFragment.getTitle());
                break;
            case R.id.ll_chart_01:
            case R.id.ll_chart_02:
            case R.id.ll_chart_03:
                //case R.id.ll_chart_04:
            case R.id.ll_chart_05:
            case R.id.ll_chart_06:
            case R.id.ll_chart_07:
            case R.id.ll_chart_08:
                statisticsfragment.onRightMenuClicked(view);
                txt_title.setText(statisticsfragment.getTitle());
                break;
            default:
                break;
        }
        mLlDialog.setVisibility(View.GONE);
    }


    @Override
    public void onBackPressed() {
        if (mLlDialog.getVisibility() == View.VISIBLE) {
            mLlDialog.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LeftOptionSelectActivity.REQUEST_CODE && resultCode == RESULT_OK) {
            if (index == 2) {
                manageFragment.onLeftMenuClicked(
                        data.getStringExtra(LeftOptionSelectActivity.A_NAME)
                        , data.getStringExtra(LeftOptionSelectActivity.A_ID)
                        , data.getStringExtra(LeftOptionSelectActivity.P_NAME)
                        , data.getStringExtra(LeftOptionSelectActivity.P_ID)
                        , data.getStringExtra(LeftOptionSelectActivity.H_NAME)
                        , data.getStringExtra(LeftOptionSelectActivity.H_ID)
                );
            } else if (index == 3) {
                statisticsfragment.onLeftMenuClicked(
                        data.getStringExtra(LeftOptionSelectActivity.A_NAME)
                        , data.getStringExtra(LeftOptionSelectActivity.A_ID)
                        , data.getStringExtra(LeftOptionSelectActivity.P_NAME)
                        , data.getStringExtra(LeftOptionSelectActivity.P_ID)
                        , data.getStringExtra(LeftOptionSelectActivity.H_NAME)
                        , data.getStringExtra(LeftOptionSelectActivity.H_ID)
                );
            }
        }
    }

    public void onHomeListItemClicked(String id, int flag) {
        onTabClicked(findViewById(R.id.re_weixin));
        //设置属性，请求时使用
        statisticsfragment.setIdFlag(id, flag);
        onMenuClicked(findViewById(R.id.ll_chart_01));
    }

    private void receiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        filter.addAction("android.net.wifi.STATE_CHANGE");
        registerReceiver(new NetworkConnectChangedReceiver(), filter);
    }

    //重写onKeyDown方法,对按键(不一定是返回按键)监听
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {//当返回按键被按下
            MyAlertDialog alertDialog = new MyAlertDialog(MainActivity.this
                    , new MyAlertDialog.DialogListener() {
                @Override
                public void affirm() {
                    finish();//结束当前Activity
                }

                @Override
                public void cancel() {

                }
            }, "你确定要退出登录吗？");
            alertDialog.show();
        }
        return false;
    }


    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.mwh.enterprise.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    handleExtra(extras);
//                    StringBuilder showMsg = new StringBuilder();
//                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
//                    if (!TextUtils.isEmpty(extras)) {
//                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
//                    }
//                    setCostomMsg(showMsg.toString());

                }
            } catch (Exception e) {
            }
        }
    }

    private void setCostomMsg(String msg) {
        System.out.println("main-push:" + msg);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

//        if (null != tvLocaltion) {
//            tvLocaltion.setText(msg);
//            tvLocaltion.setVisibility(android.view.View.VISIBLE);
//        }
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handlePushMessage(intent);
    }

    private void handlePushMessage(Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            String message = bundle.getString(JPushInterface.EXTRA_ALERT);
            if (!TextUtils.isEmpty(message)) {
                String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
                handleExtra(extra);
                /*try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Log.e(TAG, "operation================== "+json);
                    String operation = json.optString("optionation");
                    Log.e(TAG, "operation================== "+operation);
                    switch (operation) {
                        case "add":
                            break;
                        case "chose":
                            manageFragment.setIdFlag(2);
                            onTabClicked(findViewById(R.id.analysis));
                            //设置属性，请求时使用
                            //onMenuClicked(findViewById(R.id.ll_manage_release));
                            break;
                        case "threeFix":
                            manageFragment.setIdFlag(3);
                            onTabClicked(findViewById(R.id.analysis));
                            //设置属性，请求时使用
                            //onMenuClicked(findViewById(R.id.ll_manage_rectification));
                            break;
                        case "recheck":
                            manageFragment.setIdFlag(6);
                            onTabClicked(findViewById(R.id.analysis));
                            //设置属性，请求时使用
                            //onMenuClicked(findViewById(R.id.ll_manage_review));
                            break;
                        case "finish":
                            *//*onTabClicked(findViewById(R.id.analysis));
                            //设置属性，请求时使用
                            //statisticsfragment.setIdFlag(id, flag);
                            onMenuClicked(findViewById(R.id.ll_manage_review));*//*
                            break;
                        case "outTime":
                            manageFragment.setIdFlag(5);
                            onTabClicked(findViewById(R.id.analysis));
                            //设置属性，请求时使用
                            onMenuClicked(findViewById(R.id.ll_manage_overdue));
                            break;
                        default:
                            manageFragment.setIdFlag(2);
                            onTabClicked(findViewById(R.id.analysis));
                            break;
                    }
                } catch (JSONException e) {
                }*/
            }
        }
    }

    private void handleExtra(String extra) {
        try {
            JSONObject json = new JSONObject(extra);
            Log.e(TAG, "operation================== " + json);
            String operation = json.optString("optionation");
            Log.e(TAG, "operation================== " + operation);
//            onTabClicked(findViewById(R.id.re_find));

            switch (operation) {
                case "add":
                    break;
                case "chose":
                    manageFragment.setIdFlag(2);
                    onTabClicked(findViewById(R.id.analysis));
                    //设置属性，请求时使用
                    onMenuClicked(findViewById(R.id.ll_manage_release));
                    break;
                case "threeFix":
                    manageFragment.setIdFlag(3);
                    onTabClicked(findViewById(R.id.analysis));
                    //设置属性，请求时使用
                    onMenuClicked(findViewById(R.id.ll_manage_rectification));
                    break;
                case "recheck":
                    manageFragment.setIdFlag(6);
                    onTabClicked(findViewById(R.id.analysis));
                    //设置属性，请求时使用
                    onMenuClicked(findViewById(R.id.ll_manage_review));
                    break;
                case "finish":
                    /*onTabClicked(findViewById(R.id.analysis));
                    //设置属性，请求时使用
                    //statisticsfragment.setIdFlag(id, flag);
                    onMenuClicked(findViewById(R.id.ll_manage_review));*/
                    break;
                case "outTime":
                    manageFragment.setIdFlag(5);
                    onTabClicked(findViewById(R.id.analysis));
                    //设置属性，请求时使用
                    onMenuClicked(findViewById(R.id.ll_manage_overdue));
                    break;
                default:
                    break;
        }
        } catch (
                JSONException e) {
        }
    }


}