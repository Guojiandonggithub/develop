package com.example.administrator.riskprojects.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.administrator.riskprojects.Adpter.PicAdapter;
import com.example.administrator.riskprojects.BaseActivity;
import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.bean.HiddenDangerRecord;
import com.example.administrator.riskprojects.bean.ThreeFix;
import com.example.administrator.riskprojects.net.BaseJsonRes;
import com.example.administrator.riskprojects.net.NetClient;
import com.example.administrator.riskprojects.tools.Constants;
import com.example.administrator.riskprojects.tools.Utils;
import com.juns.health.net.loopj.android.http.RequestParams;
import java.util.ArrayList;
import java.util.List;

public class HiddenRiskRecordDetailActivity extends BaseActivity {
    private static final String TAG = "HiddenRiskRecordDetailA";
    private TextView txtLeft;
    private ImageView imgLeft;
    private TextView txtTitle;
    private ImageView imgRight;
    private TextView txtRight;
    private TextView tvHiddenUnits;
    private TextView tvProfessional;
    //private TextView tvHiddenClass;
    private TextView tvEnterMan;
    private TextView tvLevel;
    private TextView tvDate;
    private TextView tvOrder;
    private TextView tvHiddenSort;
    private TextView tvHiddenArea;
    private TextView tvDiscoverer;
    private TextView tvIsHandle;
    private TextView etContent;
    private RecyclerView recyclerView;
    protected NetClient netClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden_risk_record_detail);
        initView();
        setVeiw();
    }

    private void setVeiw() {
        Bundle bundle = getIntent().getBundleExtra("hiddenrecordBundle");
        HiddenDangerRecord hiddenDangerRecord = (HiddenDangerRecord) bundle.getSerializable("hiddenDangerRecord");
        txtTitle.setText("隐患详情");
        tvHiddenUnits.setText(hiddenDangerRecord.getTeamGroupName());
        tvProfessional.setText(hiddenDangerRecord.getSname());
        //tvHiddenClass.setText(hiddenDangerRecord.getGname());
        tvEnterMan.setText(hiddenDangerRecord.getRealName());
        tvLevel.setText(hiddenDangerRecord.getJbName());
        tvDate.setText(hiddenDangerRecord.getFindTime());
        tvOrder.setText(hiddenDangerRecord.getClassName());
        tvHiddenSort.setText(hiddenDangerRecord.getHiddenBelong());
        tvHiddenArea.setText(hiddenDangerRecord.getAreaName());
        tvDiscoverer.setText(hiddenDangerRecord.getRecheckPersonName());
        tvIsHandle.setText(hiddenDangerRecord.getIshandle().equals("0") ? "未处理" : "已处理");
        etContent.setText(hiddenDangerRecord.getContent());
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        getPicList(hiddenDangerRecord.getImageGroup());
    }

    private void initView() {
        txtLeft = findViewById(R.id.txt_left);
        imgLeft = findViewById(R.id.img_left);
        txtTitle = findViewById(R.id.txt_title);
        imgRight = findViewById(R.id.img_right);
        txtRight = findViewById(R.id.txt_right);
        tvHiddenUnits = findViewById(R.id.tv_hidden_units);
        tvProfessional = findViewById(R.id.tv_professional);
        //tvHiddenClass = findViewById(R.id.tv_hidden_class);
        tvEnterMan = findViewById(R.id.tv_enter_man);
        tvLevel = findViewById(R.id.tv_level);
        tvDate = findViewById(R.id.tv_date);
        tvOrder = findViewById(R.id.tv_order);
        tvHiddenSort = findViewById(R.id.tv_hidden_sort);
        tvHiddenArea = findViewById(R.id.tv_hidden_area);
        tvDiscoverer = findViewById(R.id.tv_discoverer);
        tvIsHandle = findViewById(R.id.tv_is_handle);
        etContent = findViewById(R.id.et_content);
        recyclerView = findViewById(R.id.recyclerView);
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
                        Utils.showLongToast(HiddenRiskRecordDetailActivity.this, content);
                    }
                });
            }
        }catch (Exception e) {
            Utils.showLongToast(HiddenRiskRecordDetailActivity.this, e.toString());
        }
    }
}
