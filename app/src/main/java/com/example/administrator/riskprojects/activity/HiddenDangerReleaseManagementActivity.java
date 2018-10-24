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
 * 隐患下达
 */
public class HiddenDangerReleaseManagementActivity extends BaseActivity {
    public static final int REQUEST_CODE = 1024;
    private static final String TAG = "HiddenDangerReleaseMana";
    protected NetClient netClient;
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
    private TextView mTvPrincipal;
    private TextView mTvMeasure;
    private TextView mTvCapital;
    private TextView mTvTheNumberOfProcessing;
    //private TextView mTvToCarryOutThePeople;
    private TextView mTvDepartment;
    private TextView mTvHeadquarters;
    private LinearLayoutCompat mLlBottom;
    private TextView mTvOk;
    private ThreeFix threeFix;
    private TextView tvTrackingUnit;
    private TextView tvTrackPeople;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden_danger_release_management);
        netClient = new NetClient(HiddenDangerReleaseManagementActivity.this);
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
        mTvPrincipal = findViewById(R.id.tv_principal);
        mTvMeasure = findViewById(R.id.tv_measure);
        mTvCapital = findViewById(R.id.tv_capital);
        mTvTheNumberOfProcessing = findViewById(R.id.tv_the_number_of_processing);
        //mTvToCarryOutThePeople = findViewById(R.id.tv_to_carry_out_the_people);
        mTvDepartment = findViewById(R.id.tv_department);
        //mTvHeadquarters = findViewById(R.id.tv_headquarters);
        mLlBottom = findViewById(R.id.ll_bottom);
        mTvOk = findViewById(R.id.tv_ok);
        tvTrackingUnit = findViewById(R.id.tv_tracking_unit);
        tvTrackPeople = findViewById(R.id.tv_track_people);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mTvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        HiddenDangerReleaseManagementActivity.this,
                        FiveDecisionsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("threeFix", threeFix);
                intent.putExtra("threeBund", bundle);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    private void setView() {
        mTxtTitle.setText(R.string.hidden_danger_management);
        Bundle bundle = getIntent().getBundleExtra("threeBund");
        threeFix = (ThreeFix) bundle.getSerializable("threeFix");
        mTvHiddenContent.setText(threeFix.getContent());
        mTvArea.setText(threeFix.getAreaName());
        mTvSpecialty.setText(threeFix.getSname());
        String findTimeStr = threeFix.getFindTime();
        String findTime = findTimeStr.substring(0,10);
        mTvTimeOrOrder.setText(findTime + "/" + threeFix.getClassName());
        mTvCategory.setText(threeFix.getGname());
        String isuper = threeFix.getIsupervision();
        if (TextUtils.isEmpty(isuper) || TextUtils.equals(isuper, "0")) {
            isuper = "未挂牌";
        } else {
            isuper = "已挂牌";
        }
        mTvSupervise.setText(isuper);
        mTvFinishTime.setText(threeFix.getFixTime());
        mTvDepartment.setText(threeFix.getLsdeptName() + "/" + threeFix.getLsteamName());
        mTvMeasure.setText(threeFix.getMeasure());
        mTvCapital.setText(threeFix.getMoney());
        mTvPrincipal.setText(threeFix.getRealName());
        mTvTheNumberOfProcessing.setText(threeFix.getPersonNum());
        //mTvToCarryOutThePeople.setText(threeFix.getPracticablePerson());
        tvTrackingUnit.setText(threeFix.getFollingTeamName());
        tvTrackPeople.setText(threeFix.getFollingPersonName());
        getPicList(threeFix.getImageGroup());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            finish();
        }
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
                        Utils.showLongToast(HiddenDangerReleaseManagementActivity.this, content);
                    }
                });
            }
        }catch (Exception e) {
            Utils.showLongToast(HiddenDangerReleaseManagementActivity.this, e.toString());
        }
    }
}
