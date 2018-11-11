package com.example.administrator.riskprojects.activity;

import android.content.Intent;
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
import com.juns.health.net.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

/**
 * 隐患验收
 */
public class HiddenDangerReviewManagementActivity extends BaseActivity {
    public static final int REQUEST_CODE = 1024;
    private static final String TAG = "HiddenDangerReviewManag";
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
    private TextView mTvHeadquarters;
    private TextView mTvPrincipal;
    private TextView mTvMeasure;
    private TextView mTvCapital;
    private TextView mTvToCarryOutThePeople;
    private TextView mTvAcceptanceOfThePeople;
    private TextView mTvAcceptanceOfTheResults;
    private LinearLayoutCompat mLlBottom;
    private TextView mTvOk;
    private ThreeFix threeFix;
    private TextView mTvTrackPeople;
    private TextView mTvTrackUnit;
    private TextView mTvHandler;
    private RecyclerView recyclerView;
    protected NetClient netClient;
    private TextView mTvHiddenUnits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden_danger_review_management);
        netClient = new NetClient(HiddenDangerReviewManagementActivity.this);
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
        mTvPrincipal = findViewById(R.id.tv_principal);
        mTvMeasure = findViewById(R.id.tv_measure);
        mTvCapital = findViewById(R.id.tv_capital);
        mTvToCarryOutThePeople = findViewById(R.id.tv_to_carry_out_the_people);
        mTvAcceptanceOfThePeople = findViewById(R.id.tv_acceptance_of_the_people);
        mTvAcceptanceOfTheResults = findViewById(R.id.tv_acceptance_of_the_results);
        mLlBottom = findViewById(R.id.ll_bottom);
        mTvOk = findViewById(R.id.tv_ok);
        mTvTrackPeople = findViewById(R.id.tv_track_people);
        mTvTrackUnit = findViewById(R.id.tv_tracking_unit);
        mTvHandler = findViewById(R.id.tv_the_number_of_processing);
        recyclerView = findViewById(R.id.recyclerView);
        mTvHiddenUnits = findViewById(R.id.tv_hidden_units);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
    }

    private void setView() {
        mTxtTitle.setText(R.string.hidden_danger_review_management);
        mTvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HiddenDangerAcceptanceActivity.class);
                intent.putExtra("threeFixId",threeFix.getId());
                intent.putExtra("recheckresult",threeFix.getRecheckResult());
                intent.putExtra("description",threeFix.getDescription());
                intent.putExtra("recheckPersonId",threeFix.getRecheckPersonId());
                intent.putExtra("recheckPersonName",threeFix.getRecheckPersonName());
                startActivityForResult(intent, REQUEST_CODE);
                //startActivity(intent);
            }
        });

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
        mTvToCarryOutThePeople.setText(threeFix.getPracticablePerson());
        mTvAcceptanceOfThePeople.setText(threeFix.getRecheckPersonName());
        String result = threeFix.getRecheckResult();
        if(TextUtils.isEmpty(result)){
            result = "未验收";
        }else if(TextUtils.equals(result,"1")){
            result = "未通过";
        }else{
            result = "已通过";
        }
        mTvAcceptanceOfTheResults.setText(result);
        mTvTrackPeople.setText(threeFix.getFollingPersonName());
        mTvTrackUnit.setText(threeFix.getFollingTeamName());
        mTvHandler.setText(threeFix.getPersonNum());
        mTvHiddenUnits.setText(threeFix.getTeamGroupName().trim());
        getPicList(threeFix.getImageGroup());
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
                        Utils.showShortToast(HiddenDangerReviewManagementActivity.this, content);
                    }
                });
            }
        }catch (Exception e) {
            Utils.showShortToast(HiddenDangerReviewManagementActivity.this, e.toString());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            finish();
        }
    }

}
