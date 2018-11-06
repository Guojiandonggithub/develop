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
import com.example.administrator.riskprojects.bean.HiddenDangerRecord;
import com.example.administrator.riskprojects.net.BaseJsonRes;
import com.example.administrator.riskprojects.net.NetClient;
import com.example.administrator.riskprojects.tools.Constants;
import com.example.administrator.riskprojects.tools.Utils;
import com.juns.health.net.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

//督办详情
public class HiddenDangerDetailManagementAddOrDetailActivity extends BaseActivity {
    private static final String TAG = "HiddenDangerDetailManag";
    protected NetClient netClient;
    private TextView txtLeft;
    private ImageView imgLeft;
    private TextView txtTitle;
    private ImageView imgRight;
    private TextView txtRight;
    private TextView tvHiddenUnits;
    private TextView tvTimeOrOrder;
    private TextView tvHiddenContent;
    private TextView tvHiddenDangerBelongs;
    private LinearLayoutCompat expand;
    private TextView tvProfessional;
    private TextView tvArea;
    private TextView tvClasses;
    private TextView tvOversee;
    private ImageView ivStatusSecond;
    private LinearLayoutCompat clickMore;
    private ImageView ivStatus;
    private LinearLayoutCompat llBottom;
    private TextView tvDelete;
    private TextView tvChange;
    private TextView tvAdd;
    private HiddenDangerRecord record;
    private String hiddenrecordjson;
    private TextView tvHiddenBelong;
    private TextView tvIsHang;
    private TextView tvDiscoveryTime;
    private TextView tvCheckTheContent;
    private TextView tvStatus;
    private TextView tvIsHandle;
    private TextView tvHiddenDangerLogger;
    private TextView tvFinishTime;
    private TextView tvPrincipal;
    private TextView tvMeasure;
    private TextView tvCapital;
    private TextView tvTheNumberOfProcessing;
    private TextView tvToCarryOutThePeople;
    private TextView tvDepartment;
    private TextView tvTrackingUnit;
    private TextView tvTrackPeople;
    private TextView tvAcceptanceOfThePeople;
    private TextView tvAcceptanceOfTheResults;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden_danger_detail_management);
        netClient = new NetClient(HiddenDangerDetailManagementAddOrDetailActivity.this);
        initView();
        setView();
        initdata();
    }

    private void setView() {
        txtTitle.setText(R.string.guapai_details_management);
        tvDelete.setText(R.string.add_backspace);
        tvChange.setText(R.string.detail_backspace);
        tvAdd.setVisibility(View.GONE);
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intents = new Intent(HiddenDangerDetailManagementAddOrDetailActivity.this, AddSupervisorRecordActivity.class);
                Log.e(TAG, "onClick: hiddenrecordjson=============" + hiddenrecordjson);
                //intents.putExtra("hiddenrecordjson",hiddenrecordjson);
                intents.putExtra("id", record.getId());
                startActivity(intents);
            }
        });

        tvChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intents = new Intent(HiddenDangerDetailManagementAddOrDetailActivity.this, SuperviseRecordManagementActivity.class);
                Log.e(TAG, "onClick: hiddenrecordjson=============" + hiddenrecordjson);
                //intents.putExtra("hiddenrecordjson",hiddenrecordjson);
                intents.putExtra("hiddenDangerId", record.getId());
                startActivity(intents);
            }
        });

    }

    private void initView() {
        txtLeft = findViewById(R.id.txt_left);
        imgLeft = findViewById(R.id.img_left);
        txtTitle = findViewById(R.id.txt_title);
        imgRight = findViewById(R.id.img_right);
        txtRight = findViewById(R.id.txt_right);
        tvHiddenUnits = findViewById(R.id.tv_hidden_units);
        tvHiddenUnits.setSelected(true);
        tvTimeOrOrder = findViewById(R.id.tv_time_or_order);
        tvHiddenContent = findViewById(R.id.tv_hidden_content);
        tvHiddenDangerBelongs = findViewById(R.id.tv_hidden_danger_belongs);
        expand = findViewById(R.id.expand);
        tvProfessional = findViewById(R.id.tv_professional);
        tvArea = findViewById(R.id.tv_area);
        tvClasses = findViewById(R.id.tv_classes);
        tvOversee = findViewById(R.id.tv_oversee);
        ivStatusSecond = findViewById(R.id.iv_status_second);
        clickMore = findViewById(R.id.click_more);
        ivStatus = findViewById(R.id.iv_status);
        llBottom = findViewById(R.id.ll_bottom);
        tvDelete = findViewById(R.id.tv_delete);
        tvChange = findViewById(R.id.tv_change);
        tvAdd = findViewById(R.id.tv_add);
        tvHiddenBelong = findViewById(R.id.tv_hidden_belong);
        tvIsHang = findViewById(R.id.tv_is_hang);
        tvDiscoveryTime = findViewById(R.id.tv_discovery_time);
        tvCheckTheContent = findViewById(R.id.tv_check_the_content);
        tvStatus = findViewById(R.id.tv_status);
        tvIsHandle = findViewById(R.id.tv_is_handle);
        tvHiddenDangerLogger = findViewById(R.id.tv_hidden_danger_logger);
        tvFinishTime = findViewById(R.id.tv_finish_time);
        tvPrincipal = findViewById(R.id.tv_principal);
        tvMeasure = findViewById(R.id.tv_measure);
        tvCapital = findViewById(R.id.tv_capital);
        tvTheNumberOfProcessing = findViewById(R.id.tv_the_number_of_processing);
        tvToCarryOutThePeople = findViewById(R.id.tv_to_carry_out_the_people);
        tvDepartment = findViewById(R.id.tv_department);
        tvTrackingUnit = findViewById(R.id.tv_tracking_unit);
        tvTrackPeople = findViewById(R.id.tv_track_people);
        tvAcceptanceOfThePeople = findViewById(R.id.tv_acceptance_of_the_people);
        tvAcceptanceOfTheResults = findViewById(R.id.tv_acceptance_of_the_results);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
    }

    private void initdata() {
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        Bundle bundle = intent.getBundleExtra("recordBund");
        record = (HiddenDangerRecord) bundle.getSerializable("hiddenDangerRecord");
        getHiddenRecord(id);
    }

    private void getHiddenRecord(String id) {//隐患id
       /* RequestParams params = new RequestParams();
        params.put("hiddenDangerRecordId", id);
        netClient.post(Data.getInstance().getIp() + Constants.HIDDENDANGERRECORD, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "隐患数据返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    JSONObject returndata = JSON.parseObject(data);
                    hiddenrecordjson = data;
                    record = JSONArray.parseObject(data, HiddenDangerRecord.class);
                    */
                    tvHiddenUnits.setText(record.getTeamGroupName().trim());
                    String findTimeStr = record.getFindTime();
                    String findTime = findTimeStr.substring(0,10);
                    tvTimeOrOrder.setText(findTime + "/" + record.getClassName());
                    tvHiddenContent.setText(record.getContent());
                    tvHiddenDangerBelongs.setText(record.getHiddenBelong());
                    tvProfessional.setText(record.getSname());
                    tvArea.setText(record.getAreaName());
                    tvClasses.setText(record.getJbName());
                    ivStatus.setImageResource(getImageResourceByFlag(record.getFlag(), record.getOutTimeFlag()));
                    String isuper = record.getIsupervision();
                    String guapai = "未挂牌";
                    if (TextUtils.isEmpty(isuper) || TextUtils.equals(isuper, "0")) {
                        isuper = "未挂牌";
                    } else {
                        isuper = "已挂牌";
                        guapai = "已挂牌";
                    }
                    String ishandle = record.getIshandle();
                    if(TextUtils.isEmpty(ishandle)||"0".equals(ishandle)){
                        ishandle = "未处理";
                    }else{
                        ishandle = "已处理";
                    }
                    String recheckResult = record.getRecheckResult();
                    if(TextUtils.isEmpty(recheckResult)){
                        recheckResult = "";
                    }else if("0".equals(recheckResult)){
                        recheckResult = "未验收";
                    }else{
                        recheckResult = "已验收";
                    }
                    tvDiscoveryTime.setText(record.getFindTime());
                    tvOversee.setText(isuper);
                    tvIsHang.setText(guapai);
                    String status = getStatusByFlag(record.getFlag(),record.getOutTimeFlag());
                    tvStatus.setText(status);
                    tvIsHandle.setText(ishandle);
                    tvHiddenDangerLogger.setText(record.getRealName());
                    tvFinishTime.setText(record.getFixTime());
                    tvPrincipal.setText(record.getThreeFixRealName());
                    tvMeasure.setText(record.getMeasure());
                    tvCapital.setText(record.getMoney());
                    tvTheNumberOfProcessing.setText(record.getPersonNum());
                    //tvToCarryOutThePeople.setText(record.getPracticablePerson());
                    tvDepartment.setText(record.getTeamName());
                    tvCheckTheContent.setText(record.getHiddenCheckContent());
                    tvTrackingUnit.setText(record.getFollingTeamName());
                    tvTrackPeople.setText(record.getFollingPersonName());
                    tvAcceptanceOfThePeople.setText(record.getRecheckPersonName());
                    tvAcceptanceOfTheResults.setText(recheckResult);
                    getPicList(record.getImageGroup());
                /*}

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "隐患数据返回错误信息：" + content);
                Utils.showShortToast(HiddenDangerDetailManagementAddOrDetailActivity.this, content);
                return;
            }
        });*/
    }

    //删除隐患
    private void deleteHiddenRecord(String id) {//隐患id
        RequestParams params = new RequestParams();
        params.put("hiddenDangerRecordId", id);
        netClient.post(Data.getInstance().getIp() + Constants.DELETE_HIDDEN, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "删除隐患返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    Utils.showShortToast(HiddenDangerDetailManagementAddOrDetailActivity.this, "删除成功");
                }
                finish();
            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "删除隐患返回错误信息：" + content);
                Utils.showShortToast(HiddenDangerDetailManagementAddOrDetailActivity.this, content);
                return;
            }
        });
    }

    private int getImageResourceByFlag(String flag, String outTimeFlag) {
        if ("1".equals(outTimeFlag)) {
            return R.mipmap.ic_status_overdue;
        }
        switch (flag) {
            case "0":
                return R.mipmap.ic_status_shaixuan;
            case "1":
                return R.mipmap.ic_status_release;
            case "2":
                return R.mipmap.ic_status_rectificationg;
            case "3":
                return R.mipmap.ic_recheck;
            case "4":
                return R.mipmap.ic_status_dispelling;
            case "5":
                return R.mipmap.ic_status_release;
            default:
                return R.mipmap.ic_status_overdue;
        }
    }

    private String getStatusByFlag(String flag, String outTimeFlag) {
        if ("1".equals(outTimeFlag)) {
            return "逾期";
        }
        switch (flag) {
            case "0":
                return "筛选";
            case "1":
                return "五定中";
            case "2":
                return "整改中";
            case "3":
                return "验收中";
            case "4":
                return "销项";
            case "5":
                return "跟踪";
            default:
                return "未知";
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
                        Utils.showShortToast(HiddenDangerDetailManagementAddOrDetailActivity.this, content);
                    }
                });
            }
        }catch (Exception e) {
            Utils.showShortToast(HiddenDangerDetailManagementAddOrDetailActivity.this, e.toString());
        }
    }

}
