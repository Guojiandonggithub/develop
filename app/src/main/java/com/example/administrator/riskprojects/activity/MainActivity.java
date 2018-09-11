package com.example.administrator.riskprojects.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.dialog.WarnTipDialog;
import com.example.administrator.riskprojects.fragment.Fragment_Dicover;
import com.example.administrator.riskprojects.fragment.Fragment_Supervision;
import com.example.administrator.riskprojects.fragment.Fragment_Home;
import com.example.administrator.riskprojects.fragment.Fragment_Record_Manage;
import com.example.administrator.riskprojects.fragment.Fragment_mine;

public class MainActivity extends FragmentActivity {
    private TextView txt_title;
    private ImageView img_left;
    private ImageView img_right;
    private WarnTipDialog Tipdialog;
    protected static final String TAG = "MainActivity";
    private Fragment[] fragments;
    public Fragment_Home homefragment;
    private Fragment_Supervision supervisionfragment;
    private Fragment_Record_Manage manageFragment;
    private Fragment_Dicover findfragment;
    private Fragment_mine minefragment;
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
        homefragment = new Fragment_Home();
        supervisionfragment = new Fragment_Supervision();
        findfragment = new Fragment_Dicover();
        manageFragment = new Fragment_Record_Manage();
        minefragment = new Fragment_mine();
        fragments = new Fragment[]{homefragment, supervisionfragment,
                manageFragment, findfragment,  minefragment};
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
        textviews[0].setTextColor(getResources().getColor(R.color.blue1));
        // 添加显示第一个fragment
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, homefragment)
                .add(R.id.fragment_container, supervisionfragment)
                .add(R.id.fragment_container, manageFragment)
                .add(R.id.fragment_container, findfragment)
                .add(R.id.fragment_container, minefragment)
                .hide(supervisionfragment).hide(manageFragment)
                .hide(findfragment).hide(minefragment).show(homefragment).commit();
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
                img_left.setVisibility(View.GONE);
                img_right.setVisibility(View.GONE);
                break;
            case R.id.re_find:
                index = 1;
                txt_title.setText(R.string.guapai);
                img_left.setVisibility(View.GONE);
                img_right.setVisibility(View.GONE);
                break;
            case R.id.analysis:
                index = 2;
                txt_title.setText(R.string.record_tracking);
                img_left.setVisibility(View.VISIBLE);
                img_right.setVisibility(View.VISIBLE);
                break;
            case R.id.re_weixin:
                index = 3;
                txt_title.setText(R.string.statistical_analysis);
                img_left.setVisibility(View.VISIBLE);
                img_right.setVisibility(View.VISIBLE);
                break;
            case R.id.re_profile:
                index = 4;
                txt_title.setText(R.string.me);
                img_left.setVisibility(View.GONE);
                img_right.setVisibility(View.GONE);
                break;
        }
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager()
                    .beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_container, fragments[index]);
            }
            System.out.println("index:"+Integer.toString(index));
            System.out.println("getSimpleName:"+fragments[index].getClass().getSimpleName());
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
        super.onResume();
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

                } else if (index == 3) {

                }
            }
        });
        img_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index == 2) {
                    manageFragment.ShowMenuDialog();
                } else if (index == 3) {

                }
            }
        });
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