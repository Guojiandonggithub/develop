package com.example.administrator.riskprojects.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.dialog.WarnTipDialog;
import com.example.administrator.riskprojects.fragment.Fragment_Dicover;
import com.example.administrator.riskprojects.fragment.Fragment_Friends;
import com.example.administrator.riskprojects.fragment.Fragment_Msg;
import com.example.administrator.riskprojects.fragment.Fragment_Profile;
import com.example.administrator.riskprojects.tools.Constants;
import com.example.administrator.riskprojects.tools.Utils;


import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends FragmentActivity{
	private TextView txt_title;
	private WarnTipDialog Tipdialog;
	protected static final String TAG = "MainActivity";
	private Fragment[] fragments;
	public Fragment_Msg homefragment;
	private Fragment_Friends contactlistfragment;
	private Fragment_Profile profilefragment;
	private Fragment_Dicover findfragment;
	private ImageView[] imagebuttons;
	private TextView[] textviews;
	private String connectMsg = "aa";
	private int index;
	private int currentTabIndex;// 当前fragment的index

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById();
		initTabView();
		initPopWindow();
	}

	private void initTabView() {
		homefragment = new Fragment_Msg();
		contactlistfragment = new Fragment_Friends();
				findfragment = new Fragment_Dicover();
		profilefragment = new Fragment_Profile();
		fragments = new Fragment[] { homefragment, contactlistfragment,
				findfragment,profilefragment,homefragment };
		imagebuttons = new ImageView[5];
		imagebuttons[0] = (ImageView) findViewById(R.id.ib_contact_list);
		imagebuttons[1] = (ImageView) findViewById(R.id.ib_find);
		imagebuttons[2] = (ImageView) findViewById(R.id.iv_analysis);
		imagebuttons[3] = (ImageView) findViewById(R.id.ib_weixin);
		imagebuttons[4] = (ImageView) findViewById(R.id.ib_profile);

		imagebuttons[0].setSelected(true);
		textviews = new TextView[5]; 
		textviews[0] = (TextView) findViewById(R.id.tv_contact_list);
		textviews[1] = (TextView) findViewById(R.id.tv_find);
		textviews[2] = (TextView) findViewById(R.id.tv_analysis);
		textviews[3] = (TextView) findViewById(R.id.tv_weixin);
		textviews[4] = (TextView) findViewById(R.id.tv_profile);
		textviews[0].setTextColor(0xFF45C01A);
		// 添加显示第一个fragment
		getSupportFragmentManager().beginTransaction()
				.add(R.id.fragment_container, homefragment)
				.add(R.id.fragment_container, contactlistfragment)
				.add(R.id.fragment_container, profilefragment)
				.add(R.id.fragment_container, findfragment)
				.hide(contactlistfragment).hide(profilefragment)
				.hide(findfragment).show(homefragment).commit();
	}

	public void onTabClicked(View view) {
		//img_right.setVisibility(View.GONE);
		switch (view.getId()) {
		case R.id.re_weixin:
			//img_right.setVisibility(View.VISIBLE);
			index = 0;
			if (homefragment != null) {
				homefragment.refresh();
			}
			txt_title.setText(R.string.home);
			//img_right.setImageResource(R.drawable.icon_add);
			break;
		case R.id.re_contact_list:
			index = 1;
			txt_title.setText(R.string.guapai);
			//img_right.setVisibility(View.VISIBLE);
			//img_right.setImageResource(R.drawable.icon_titleaddfriend);
			break;
		case R.id.re_find:
			index = 2;
			txt_title.setText(R.string.record_tracking);
			break;
		case R.id.re_profile:
			index = 3;
			txt_title.setText(R.string.me);
			break;
		}
		if (currentTabIndex != index) {
			FragmentTransaction trx = getSupportFragmentManager()
					.beginTransaction();
			trx.hide(fragments[currentTabIndex]);
			if (!fragments[index].isAdded()) {
				trx.add(R.id.fragment_container, fragments[index]);
			}
			trx.show(fragments[index]).commit();
		}
		imagebuttons[currentTabIndex].setSelected(false);
		// 把当前tab设为选中状态
		imagebuttons[index].setSelected(true);
		textviews[currentTabIndex].setTextColor(0xFF999999);
		textviews[index].setTextColor(0xFF45C01A);
		currentTabIndex = index;
	}

	private void initPopWindow() {

	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private void findViewById() {
		txt_title = (TextView) findViewById(R.id.txt_title);
		//img_right = (ImageView) findViewById(R.id.img_right);
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

}