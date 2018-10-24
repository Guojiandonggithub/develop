package com.example.administrator.riskprojects.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.administrator.riskprojects.Adpter.PicAdapter;
import com.example.administrator.riskprojects.BaseActivity;
import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.bean.ThreeFix;
import com.example.administrator.riskprojects.net.BaseJsonRes;
import com.example.administrator.riskprojects.net.NetClient;
import com.example.administrator.riskprojects.tools.Constants;
import com.example.administrator.riskprojects.tools.Utils;
import com.example.administrator.riskprojects.view.MyAlertDialog;
import com.juns.health.net.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

/**
 * 隐患整改
 */
public class HiddenDangerRectificationManagementActivity extends BaseActivity {
    private static final String TAG = "HiddenDangerRectificati";
    private TextView mTxtLeft;
    private ImageView mImgLeft;
    private TextView mTxtTitle;
    private ImageView mImgRight;
    private TextView mTxtRight;
    private TextView mTvHiddenContent;
    private TextView mTvArea;
    private TextView mTvSpecialty;
    private TextView mTvTimeOrOrder;
    private TextView mTvCategory;
    private TextView mTvSupervise;
    private TextView mTvFinishTime;
    private TextView mTvDepartment;
    private TextView mTvMeasure;
    private TextView mTvCapital;
    private TextView mTvPrincipal;
    private TextView mTvTheRectificationResults;
    private TextView mTvToCarryOutThePeople;
    private TextView mTvTrackPeople;
    private TextView mTvTrackUnit;
    private LinearLayoutCompat mLlBottom;
    private TextView mTvOk;
    private ThreeFix threeFix;
    protected NetClient netClient;
    private TextView mTvTheNumberOfProcessing;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden_danger_rectification_management);
        netClient = new NetClient(HiddenDangerRectificationManagementActivity.this);
        initView();
        setView();
    }

    private void initView() {
        mTxtLeft = findViewById(R.id.txt_left);
        mImgLeft = findViewById(R.id.img_left);
        mTxtTitle = findViewById(R.id.txt_title);
        mImgRight = findViewById(R.id.img_right);
        mTxtRight = findViewById(R.id.txt_right);
        mTvHiddenContent = findViewById(R.id.tv_hidden_content);
        mTvArea = findViewById(R.id.tv_area);
        mTvSpecialty = findViewById(R.id.tv_specialty);
        mTvTimeOrOrder = findViewById(R.id.tv_time_or_order);
        mTvCategory = findViewById(R.id.tv_category);
        mTvSupervise = findViewById(R.id.tv_supervise);
        mTvFinishTime = findViewById(R.id.tv_finish_time);
        mTvDepartment = findViewById(R.id.tv_department);
        mTvMeasure = findViewById(R.id.tv_measure);
        mTvCapital = findViewById(R.id.tv_capital);
        mTvPrincipal = findViewById(R.id.tv_principal);
        mTvTheNumberOfProcessing = findViewById(R.id.tv_the_number_of_processing);
        mTvTheRectificationResults = findViewById(R.id.tv_the_rectification_results);
        mTvToCarryOutThePeople = findViewById(R.id.tv_to_carry_out_the_people);
        mTvTrackPeople = findViewById(R.id.tv_track_people);
        mTvTrackUnit = findViewById(R.id.tv_tracking_unit);
        mLlBottom = findViewById(R.id.ll_bottom);
        mTvOk = findViewById(R.id.tv_ok);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mTvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyAlertDialog myAlertDialog = new MyAlertDialog(HiddenDangerRectificationManagementActivity.this,
                        new MyAlertDialog.DialogListener() {
                            @Override
                            public void affirm() {
                                //确定入口
                                getHiddenRecord();
                            }

                            @Override
                            public void cancel() {

                            }
                        },"您确定完成整改吗？" );
                myAlertDialog.show();
            }
        });
    }

    private void setView() {
        mTxtTitle.setText(R.string.hidden_danger_rectification_management);
        Bundle  bundle = getIntent().getBundleExtra("threeBund");
        threeFix = (ThreeFix) bundle.getSerializable("threeFix");
        mTvHiddenContent.setText(threeFix.getContent());
        mTvArea.setText(threeFix.getAreaName());
        mTvSpecialty.setText(threeFix.getSname());
        String findTimeStr = threeFix.getFindTime();
        String findTime = findTimeStr.substring(0,10);
        mTvTimeOrOrder.setText(findTime+"/"+threeFix.getClassName());
        mTvCategory.setText(threeFix.getJbName());
        String isuper = threeFix.getIsupervision();
        if(TextUtils.isEmpty(isuper)||TextUtils.equals(isuper,"0")){
            isuper = "未挂牌";
        }else{
            isuper = "已挂牌";
        }
        mTvSupervise.setText(isuper);
        mTvFinishTime.setText(threeFix.getFixTime());
        mTvDepartment.setText(threeFix.getTeamName());
        mTvMeasure.setText(threeFix.getMeasure());
        mTvCapital.setText(threeFix.getMoney());
        mTvPrincipal.setText(threeFix.getRealName());
        String rectifyResult = threeFix.getRectifyResult();
        if(TextUtils.isEmpty(rectifyResult)){
            rectifyResult = "";
        }else if(TextUtils.equals(rectifyResult,"0")){
            rectifyResult = "已整改";
        }else{
            rectifyResult = "未整改";
        }
        mTvTheRectificationResults.setText(rectifyResult);
        mTvToCarryOutThePeople.setText(threeFix.getPracticablePerson());
        mTvTheNumberOfProcessing.setText(threeFix.getPersonNum());
        mTvTrackPeople.setText(threeFix.getFollingPersonName());
        mTvTrackUnit.setText(threeFix.getFollingTeamName());
        getPicList(threeFix.getImageGroup());
    }

    //完成整改
    private void getHiddenRecord() {
        RequestParams params = new RequestParams();
        params.put("ids",threeFix.getId());
        netClient.post(Data.getInstance().getIp()+Constants.COMPLETERECTIFY, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "完成整改返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    Utils.showLongToast(HiddenDangerRectificationManagementActivity.this, "隐患整改成功！");
                    finish();
                }
            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "完成整改返回错误信息：" + content);
                Utils.showLongToast(HiddenDangerRectificationManagementActivity.this, content);
            }
        });
    }

    //查询图片列表
    private void getPicList(String imageGroup) {
        Log.i(TAG, "imageGroup: 图片imageGroup=========" + imageGroup);
        try {
            if(!TextUtils.isEmpty(imageGroup)){
                RequestParams params = new RequestParams();
                params.put("imageGroup",imageGroup);
                netClient.post(Data.getInstance().getIp() + Constants.GET_PICLIST, params, new BaseJsonRes() {

                    @Override
                    public void onMySuccess(String data) {
                        Log.i(TAG, "查询图片组返回数据：" + data);
                        if (!TextUtils.isEmpty(data)) {
                            JSONArray jsonArray = JSONArray.parseArray(data);
                            List<String> paths =new ArrayList<>();
                            for(int i=0;i<jsonArray.size();i++){
                                JSONObject job = jsonArray.getJSONObject(i); // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                                paths.add(Constants.MAIN_ENGINE+job.get("imagePath"));
                            }
                            Log.e(TAG, "paths================: "+paths);
                            recyclerView.setAdapter(new PicAdapter(paths));
                        }
                    }

                    @Override
                    public void onMyFailure(String content) {
                        Log.e(TAG, "查询图片组返回错误信息：" + content);
                        Utils.showLongToast(HiddenDangerRectificationManagementActivity.this, content);
                    }
                });
            }
        }catch (Exception e) {
            Utils.showLongToast(HiddenDangerRectificationManagementActivity.this, e.toString());
        }
    }
}
