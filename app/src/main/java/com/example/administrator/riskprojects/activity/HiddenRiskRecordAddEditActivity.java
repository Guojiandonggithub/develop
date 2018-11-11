package com.example.administrator.riskprojects.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.administrator.riskprojects.Adpter.AddPicAdapter;
import com.example.administrator.riskprojects.Adpter.SpinnerAdapter;
import com.example.administrator.riskprojects.BasePicActivity;
import com.example.administrator.riskprojects.OnItemClickListener;
import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.bean.Area;
import com.example.administrator.riskprojects.bean.ClassNumber;
import com.example.administrator.riskprojects.bean.CollieryTeam;
import com.example.administrator.riskprojects.bean.DataDictionary;
import com.example.administrator.riskprojects.bean.HiddenDangerRecord;
import com.example.administrator.riskprojects.bean.RiskGrade;
import com.example.administrator.riskprojects.bean.SelectItem;
import com.example.administrator.riskprojects.bean.Specialty;
import com.example.administrator.riskprojects.common.NetUtil;
import com.example.administrator.riskprojects.dialog.FlippingLoadingDialog;
import com.example.administrator.riskprojects.net.BaseJsonRes;
import com.example.administrator.riskprojects.net.NetClient;
import com.example.administrator.riskprojects.tools.Constants;
import com.example.administrator.riskprojects.tools.UserUtils;
import com.example.administrator.riskprojects.tools.Utils;
import com.example.administrator.riskprojects.util.DensityUtil;
import com.juns.health.net.loopj.android.http.RequestParams;

import org.devio.takephoto.model.TImage;
import org.devio.takephoto.model.TResult;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 隐患记录 添加修改
 */
public class HiddenRiskRecordAddEditActivity extends BasePicActivity {
    private static final String TAG = "HiddenRiskRecordAddEdit";
    private TextView mTxtLeft;
    private ImageView mImgLeft;
    private TextView mTxtTitle;
    private ImageView mImgRight;
    private TextView mTxtRight;
    private TextView txtLeft;
    private ImageView imgLeft;
    private TextView txtTitle;
    private ImageView imgRight;
    private TextView txtRight;
    private Spinner spHiddenUnits;
    private SpinnerAdapter mSpHiddenUnitsAdapter;
    private Spinner spProfessional;
    private SpinnerAdapter spProfessionalAdapter;
    private Spinner spHiddenClass;
    private SpinnerAdapter spHiddenClassAdapter;
    private Spinner spHiddenTypes;
    private SpinnerAdapter spHiddenTypesAdapter;
    private Spinner spLevel;
    private SpinnerAdapter spLevelAdapter;
    private CardView cvSelectDate;
    private CardView cvSelectTime;
    private TextView tvDate;
    private TextView tvTime;
    private Spinner spOrder;
    private SpinnerAdapter spOrderAdapter;
    private Spinner spHiddenSort;
    private SpinnerAdapter spHiddenSortAdapter;
    private Spinner spHiddenArea;
    private SpinnerAdapter spHiddenAreaAdapter;
    private Spinner spCheckContent;
    private SpinnerAdapter spCheckContentAdapter;
    private Spinner spIsHandle;
    private SpinnerAdapter spIsHandleAdapter;
    private ImageView checkYes;
    private ImageView checkNos;
    private EditText etContent;
    private EditText etLocation;
    private EditText etCheckPerson;
    private LinearLayoutCompat llBottom;
    private TextView tvOk;
    private TextView tvHang;
    private RecyclerView recyclerView;
    protected NetClient netClient;
    private HiddenDangerRecord record = new HiddenDangerRecord();
    private String id;
    private String offlineStatus;
    private String imagegroup;
    private AddPicAdapter picAdapter;
    private TimePickerView mTimePicker;
    public static final DateFormat sSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //存放图片路径
    private List<String> paths = new ArrayList<>();
    private List<String> picid = new ArrayList<>();
    private List<String> updatepaths = new ArrayList<>();
    protected FlippingLoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden_risk_record_add_edit);
        netClient = new NetClient(HiddenRiskRecordAddEditActivity.this);
        initView();
        initdata();
    }

    private void initView() {
        mTxtLeft = findViewById(R.id.txt_left);
        mImgLeft = findViewById(R.id.img_left);
        mTxtTitle = findViewById(R.id.txt_title);
        mImgRight = findViewById(R.id.img_right);
        mTxtRight = findViewById(R.id.txt_right);

        txtLeft = findViewById(R.id.txt_left);
        imgLeft = findViewById(R.id.img_left);
        txtTitle = findViewById(R.id.txt_title);
        imgRight = findViewById(R.id.img_right);
        txtRight = findViewById(R.id.txt_right);
        //隐患单位
        spHiddenUnits = findViewById(R.id.sp_hidden_units);

        //所属专业
        spProfessional = findViewById(R.id.sp_professional);

        //隐患类别
        spHiddenClass = findViewById(R.id.sp_hidden_class);

        //隐患类型
        spHiddenTypes = findViewById(R.id.sp_hidden_types);

        //级别
        spLevel = findViewById(R.id.sp_level);

        //选择时间
        cvSelectDate = findViewById(R.id.cv_select_date);
        cvSelectTime = findViewById(R.id.cv_select_time);
        tvDate = findViewById(R.id.tv_date);
        tvTime = findViewById(R.id.tv_time);
        cvSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerView pvTime = new TimePickerBuilder(HiddenRiskRecordAddEditActivity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        final DateFormat FormatDate = new SimpleDateFormat("yyyy-MM-dd");
                        final DateFormat FormatTime = new SimpleDateFormat("HH:mm:ss");
                        tvDate.setText(FormatDate.format(date));
                        tvTime.setText(FormatTime.format(date));
                    }
                }).setType(new boolean[]{true, true, true, true, true, true})
                        .setLabel("年","月","日","时","分","秒")//默认设置为年月日时分秒
                 .build();
                pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                pvTime.show();
                //DatePickerActivity.startPickDate(HiddenRiskRecordAddEditActivity.this, HiddenRiskRecordAddEditActivity.this);
            }
        });
        //隐患班次
        spOrder = findViewById(R.id.sp_order);

        //检查单位
        spHiddenSort = findViewById(R.id.sp_hidden_sort);

        //隐患区域
        spHiddenArea = findViewById(R.id.sp_hidden_area);

        ////检查内容
        spCheckContent = findViewById(R.id.sp_check_content);

        //处理否
        spIsHandle = findViewById(R.id.sp_is_handle);
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        SelectItem selectItemw = new SelectItem();
        SelectItem selectItemy = new SelectItem();
        selectItemw.name = "未处理";
        selectItemw.id = "0";
        selectItemy.name = "已处理";
        selectItemy.id = "1";
        selectItems.add(selectItemw);
        selectItems.add(selectItemy);
        spIsHandleAdapter = SpinnerAdapter.createFromResource(this, selectItems);
        setUpSpinner(spIsHandle, spIsHandleAdapter);
        checkYes = findViewById(R.id.check_yes);
        checkNos = findViewById(R.id.check_nos);
        checkNos.setSelected(true);
        checkYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkYes.setSelected(true);
                checkNos.setSelected(false);
            }
        });
        checkNos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkYes.setSelected(false);
                checkNos.setSelected(true);
            }
        });
        etContent = findViewById(R.id.et_content);
        etLocation = findViewById(R.id.et_add_location);
        etCheckPerson = findViewById(R.id.et_add_checkPerson);
        llBottom = findViewById(R.id.ll_bottom);
        tvOk = findViewById(R.id.tv_ok);
        mTxtTitle.setText("隐患记录新增");
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HiddenDangerRecord records = getHiddenDangerRecord();
                String flag = "add";
                Log.e(TAG, "id==========================: "+id);
                if (!TextUtils.isEmpty(id)||null!=offlineStatus) {
                    flag = "update";
                    records.setId(id);
                    records.setOfflineDataStatus(record.getOfflineDataStatus());
                    String userid = UserUtils.getUserID(HiddenRiskRecordAddEditActivity.this);
                    String roleids = UserUtils.getUserRoleids(HiddenRiskRecordAddEditActivity.this);
                    if(record.getEmployeeId().equals(userid)||roleids.equals("1")){
                        if (checkInput(records)) {
                            upaction(paths,records, flag);
                        }
                    }else{
                        Utils.showShortToast(HiddenRiskRecordAddEditActivity.this, "没有权限进行修改！");
                    }
                }else{
                    Log.e(TAG, "upaction=================: "+paths);
                    if (checkInput(records)) {
                        Log.e(TAG, "flag=================: "+flag);
                        upaction(paths,records, flag);
                    }
                }

            }
        });
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
    }

    private void showSelectDialog() {
        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(this);
        View sheetView = getLayoutInflater().inflate(R.layout.dialog_bottomsheet_headpic, null);
        Button btcancel = (Button) sheetView.findViewById(R.id.bt_cancel);
        TextView tvphotoAlbum = (TextView) sheetView.findViewById(R.id.tv_photoAlbum);
        TextView tvcamera = (TextView) sheetView.findViewById(R.id.tv_camera);

        tvcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                Uri imageUri = Uri.fromFile(file);
                getTakePhoto().onPickFromCapture(imageUri);
                mBottomSheetDialog.dismiss();
            }
        });
        tvphotoAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (android.os.Build.VERSION.SDK_INT > M&&!TextUtils.isEmpty(getFilePath())){
//                    setFilePath("");
//                }
                getTakePhoto().onPickMultiple(3 - paths.size());
                mBottomSheetDialog.dismiss();
            }
        });

        btcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog.dismiss();
            }
        });

        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();
    }

    private void setUpSpinner(Spinner spinner, final SpinnerAdapter adapter) {
        spinner.setBackgroundColor(0x0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            spinner.setPopupBackgroundResource(R.drawable.shape_rectangle_rounded);
            spinner.setDropDownVerticalOffset(DensityUtil.dip2px(this, 3));
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adapter.setSelectedPostion(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner.setAdapter(adapter);
    }


    private void initdata() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        offlineStatus = intent.getStringExtra("offlineStatus");
        if (!TextUtils.isEmpty(id)||null!=offlineStatus) {
            Bundle bundle = intent.getBundleExtra("recordBund");
            record = (HiddenDangerRecord) bundle.getSerializable("hiddenDangerRecord");
            //String hiddenrecordjson = getIntent().getStringExtra("hiddenrecordjson");
            //Log.e(TAG, "initdata: hiddenrecordjson-------------" + hiddenrecordjson);
            //record = JSONArray.parseObject(hiddenrecordjson, HiddenDangerRecord.class);
            etContent.setText(record.getContent());
            tvDate.setText(record.getFindTime().split(" ")[0]);
            tvTime.setText(record.getFindTime().split(" ")[1]);
            etLocation.setText(record.getHiddenPlace());
            etCheckPerson.setText(record.getCheckPerson());
            String isuper = record.getIsupervision();
            imagegroup = record.getImageGroup();
            getPicList(imagegroup);
            if (TextUtils.equals(isuper, "0")) {
                checkYes.setSelected(false);
                checkNos.setSelected(true);
            } else {
                checkYes.setSelected(true);
                checkNos.setSelected(false);
            }
            if (!TextUtils.isEmpty(record.getId())) {
                mTxtTitle.setText("隐患记录修改");
            }
            String ishandle = record.getIshandle();
            spIsHandleAdapter.notifyDataSetChanged();
            spIsHandle.setSelection(Integer.parseInt(ishandle));
            if(record.getPicList()!=null){
                updatepaths.addAll(record.getPicList());
                paths = record.getPicList();
            }
            recyclerView.setAdapter(picAdapter = new AddPicAdapter(paths,picid));
            picAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position, int flag) {
                    if (position == paths.size()) {
                        /** * 图片多选 * @param limit 最多选择图片张数的限制 **/
                        //
                        showSelectDialog();
                    } else {
                        startActivity(
                                new Intent(HiddenRiskRecordAddEditActivity.this,
                                        ViewBIgPicActivity.class).putExtra("url"
                                        , paths.get(position)));

                    }
                }

                @Override
                public boolean onItemLongClick(View view, int position) {
                    return false;
                }
            });
        }else{
            Calendar cal = Calendar.getInstance();
            //当前时：HOUR_OF_DAY-24小时制；HOUR-12小时制
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            //当前分
            int minute = cal.get(Calendar.MINUTE);
            //当前秒
            int second = cal.get(Calendar.SECOND);
            tvTime.setText(hour + ":" + +minute + ":" + second);
            etCheckPerson.setText(UserUtils.getUserName(HiddenRiskRecordAddEditActivity.this));
        }
        getCollieryTeam();
        getSpecialty();
        getRiskGrade();
        getClassNumber();
        getArea();
        getHiddenType();
        getHiddenGrade();
        getHiddenYHGSLX();
        getCheckContent();
    }

    //获取部门/队组成员
    private void getCollieryTeam() {
        if (!NetUtil.checkNetWork(HiddenRiskRecordAddEditActivity.this)) {
            String jsondata = Utils.getValue(HiddenRiskRecordAddEditActivity.this, Constants.GET_COLLIERYTEAM);
            if("".equals(jsondata)){
                Utils.showShortToast(HiddenRiskRecordAddEditActivity.this, "没有联网，没有请求到数据");
            }else{
                resultColliery(jsondata);
            }
        }else{
            RequestParams params = new RequestParams();
            params.put("employeeId", UserUtils.getUserID(HiddenRiskRecordAddEditActivity.this));
            netClient.post(Data.getInstance().getIp() + Constants.GET_COLLIERYTEAM, params, new BaseJsonRes() {

                @Override
                public void onMySuccess(String data) {
                    Log.i(TAG, "获取部门/队组成员返回数据：" + data);
                    if (!TextUtils.isEmpty(data)) {
                        //Utils.putValue(HiddenRiskRecordAddEditActivity.this, Constants.GET_COLLIERYTEAM, data);
                        resultColliery(data);
                    }

                }

                @Override
                public void onMyFailure(String content) {
                    Log.e(TAG, "获取部门/队组成员返回错误信息：" + content);
                    Utils.showShortToast(HiddenRiskRecordAddEditActivity.this, content);
                }
            });
        }
    }

    //获取所属专业
    private void getSpecialty() {
        if (!NetUtil.checkNetWork(HiddenRiskRecordAddEditActivity.this)) {
            String jsondata = Utils.getValue(HiddenRiskRecordAddEditActivity.this, Constants.GET_SPECIALTY);
            if("".equals(jsondata)){
                Utils.showShortToast(HiddenRiskRecordAddEditActivity.this, "没有联网，没有请求到数据");
            }else{
                resultSpecialty(jsondata);
            }
        }else{
            RequestParams params = new RequestParams();
            netClient.post(Data.getInstance().getIp() + Constants.GET_SPECIALTY, params, new BaseJsonRes() {

                @Override
                public void onMySuccess(String data) {
                    Log.i(TAG, "获取所属专业返回数据：" + data);
                    if (!TextUtils.isEmpty(data)) {
                        //Utils.putValue(HiddenRiskRecordAddEditActivity.this, Constants.GET_SPECIALTY, data);
                        resultSpecialty(data);
                    }

                }

                @Override
                public void onMyFailure(String content) {
                    Log.e(TAG, "获取所属专业返回错误信息：" + content);
                    Utils.showShortToast(HiddenRiskRecordAddEditActivity.this, content);
                }
            });
        }
    }

    //获取隐患类别
    private void getRiskGrade() {
        if (!NetUtil.checkNetWork(HiddenRiskRecordAddEditActivity.this)) {
            String jsondata = Utils.getValue(HiddenRiskRecordAddEditActivity.this, Constants.GET_RISKGRADE);
            if("".equals(jsondata)){
                Utils.showShortToast(HiddenRiskRecordAddEditActivity.this, "没有联网，没有请求到数据");
            }else{
                resultGrade(jsondata);
            }
        }else{
            RequestParams params = new RequestParams();
            netClient.post(Data.getInstance().getIp() + Constants.GET_RISKGRADE, params, new BaseJsonRes() {

                @Override
                public void onMySuccess(String data) {
                    Log.i(TAG, "获取隐患类别返回数据：" + data);
                    if (!TextUtils.isEmpty(data)) {
                        //Utils.putValue(HiddenRiskRecordAddEditActivity.this, Constants.GET_RISKGRADE, data);
                        resultGrade(data);
                    }

                }

                @Override
                public void onMyFailure(String content) {
                    Log.e(TAG, "获取隐患类别返回错误信息：" + content);
                    Utils.showShortToast(HiddenRiskRecordAddEditActivity.this, content);
                }
            });
        }
    }

    //获取班次
    private void getClassNumber() {
        if (!NetUtil.checkNetWork(HiddenRiskRecordAddEditActivity.this)) {
            String jsondata = Utils.getValue(HiddenRiskRecordAddEditActivity.this, Constants.GET_CLASSNUMBER);
            if("".equals(jsondata)){
                Utils.showShortToast(HiddenRiskRecordAddEditActivity.this, "没有联网，没有请求到数据");
            }else{
                resultClassNumber(jsondata);
            }
        }else{
            RequestParams params = new RequestParams();
            netClient.post(Data.getInstance().getIp() + Constants.GET_CLASSNUMBER, params, new BaseJsonRes() {

                @Override
                public void onMySuccess(String data) {
                    Log.i(TAG, "获取班次返回数据：" + data);
                    if (!TextUtils.isEmpty(data)) {
                        //Utils.putValue(HiddenRiskRecordAddEditActivity.this, Constants.GET_CLASSNUMBER, data);
                        resultClassNumber(data);
                    }

                }

                @Override
                public void onMyFailure(String content) {
                    Log.e(TAG, "获取班次返回错误信息：" + content);
                    Utils.showShortToast(HiddenRiskRecordAddEditActivity.this, content);
                }
            });
        }
    }

    //获取区域
    private void getArea() {
        if (!NetUtil.checkNetWork(HiddenRiskRecordAddEditActivity.this)) {
            String jsondata = Utils.getValue(HiddenRiskRecordAddEditActivity.this, Constants.GET_AREA);
            if("".equals(jsondata)){
                Utils.showShortToast(HiddenRiskRecordAddEditActivity.this, "没有联网，没有请求到数据");
            }else{
                resultArea(jsondata);
            }
        }else{
            RequestParams params = new RequestParams();
            params.put("employeeId", UserUtils.getUserID(HiddenRiskRecordAddEditActivity.this));
            netClient.post(Data.getInstance().getIp() + Constants.GET_AREA, params, new BaseJsonRes() {

                @Override
                public void onMySuccess(String data) {
                    Log.i(TAG, "获取区域返回数据：" + data);
                    if (!TextUtils.isEmpty(data)) {
                        //Utils.putValue(HiddenRiskRecordAddEditActivity.this, Constants.GET_AREA, data);
                        resultArea(data);
                    }

                }

                @Override
                public void onMyFailure(String content) {
                    Log.e(TAG, "获取区域返回错误信息：" + content);
                    Utils.showShortToast(HiddenRiskRecordAddEditActivity.this, content);
                }
            });
        }
    }

    //获取隐患类型
    private void getHiddenType() {
        if (!NetUtil.checkNetWork(HiddenRiskRecordAddEditActivity.this)) {
            String jsondata = Utils.getValue(HiddenRiskRecordAddEditActivity.this, Constants.GET_DATADICT);
            if("".equals(jsondata)){
                Utils.showShortToast(HiddenRiskRecordAddEditActivity.this, "没有联网，没有请求到数据");
            }else{
                resultHiddenType(jsondata);
            }
        }else{
            RequestParams params = new RequestParams();
            params.put("dictTypeCode", "YHLX");
            netClient.post(Data.getInstance().getIp() + Constants.GET_DATADICT, params, new BaseJsonRes() {

                @Override
                public void onMySuccess(String data) {
                    Log.i(TAG, "获取隐患类型返回数据：" + data);
                    if (!TextUtils.isEmpty(data)) {
                        //Utils.putValue(HiddenRiskRecordAddEditActivity.this, Constants.GET_DATADICT, data);
                        resultHiddenType(data);
                    }

                }

                @Override
                public void onMyFailure(String content) {
                    Log.e(TAG, "获取隐患类型返回错误信息：" + content);
                    Utils.showShortToast(HiddenRiskRecordAddEditActivity.this, content);
                }
            });
        }
    }

    //获取隐患级别
    private void getHiddenGrade() {
        if (!NetUtil.checkNetWork(HiddenRiskRecordAddEditActivity.this)) {
            String jsondata = Utils.getValue(HiddenRiskRecordAddEditActivity.this, "hiddenGrade");
            if("".equals(jsondata)){
                Utils.showShortToast(HiddenRiskRecordAddEditActivity.this, "没有联网，没有请求到数据");
            }else{
                resultHiddenGrade(jsondata);
            }
        }else{
            RequestParams params = new RequestParams();
            params.put("dictTypeCode", "YHJB");
            netClient.post(Data.getInstance().getIp() + Constants.GET_DATADICT, params, new BaseJsonRes() {

                @Override
                public void onMySuccess(String data) {
                    Log.i(TAG, "获取隐患级别返回数据：" + data);
                    if (!TextUtils.isEmpty(data)) {
                        //Utils.putValue(HiddenRiskRecordAddEditActivity.this, "hiddenGrade", data);
                        resultHiddenGrade(data);
                    }

                }

                @Override
                public void onMyFailure(String content) {
                    Log.e(TAG, "获取隐患级别返回错误信息：" + content);
                    Utils.showShortToast(HiddenRiskRecordAddEditActivity.this, content);
                }
            });
        }
    }

    //获取检查单位
    private void getHiddenYHGSLX() {
        if (!NetUtil.checkNetWork(HiddenRiskRecordAddEditActivity.this)) {
            String jsondata = Utils.getValue(HiddenRiskRecordAddEditActivity.this, "hiddenYHGSLX");
            if("".equals(jsondata)){
                Utils.showShortToast(HiddenRiskRecordAddEditActivity.this, "没有联网，没有请求到数据");
            }else{
                resultHiddenYHGSLX(jsondata);
            }
        }else{
            RequestParams params = new RequestParams();
            params.put("dictTypeCode", "YHGSLX");
            netClient.post(Data.getInstance().getIp() + Constants.GET_DATADICT, params, new BaseJsonRes() {

                @Override
                public void onMySuccess(String data) {
                    Log.i(TAG, "获取检查单位返回数据：" + data);
                    if (!TextUtils.isEmpty(data)) {
                        //Utils.putValue(HiddenRiskRecordAddEditActivity.this, "hiddenYHGSLX", data);
                        resultHiddenYHGSLX(data);
                    }

                }

                @Override
                public void onMyFailure(String content) {
                    Log.e(TAG, "获取检查单位返回错误信息：" + content);
                    Utils.showShortToast(HiddenRiskRecordAddEditActivity.this, content);
                }
            });
        }
    }

    //获取检查内容
    private void getCheckContent() {
        if (!NetUtil.checkNetWork(HiddenRiskRecordAddEditActivity.this)) {
            String jsondata = Utils.getValue(HiddenRiskRecordAddEditActivity.this, "checkContent");
            if("".equals(jsondata)){
                Utils.showShortToast(HiddenRiskRecordAddEditActivity.this, "没有联网，没有请求到数据");
            }else{
                resultCheckContent(jsondata);
            }
        }else{
            RequestParams params = new RequestParams();
            params.put("dictTypeCode", "JCNR");
            netClient.post(Data.getInstance().getIp() + Constants.GET_DATADICT, params, new BaseJsonRes() {

                @Override
                public void onMySuccess(String data) {
                    Log.i(TAG, "获取检查内容返回数据：" + data);
                    if (!TextUtils.isEmpty(data)) {
                        //Utils.putValue(HiddenRiskRecordAddEditActivity.this, "checkContent", data);
                        resultCheckContent(data);
                    }

                }

                @Override
                public void onMyFailure(String content) {
                    Log.e(TAG, "获取检查内容返回错误信息：" + content);
                    Utils.showShortToast(HiddenRiskRecordAddEditActivity.this, content);
                }
            });
        }
    }

    //添加修改隐患记录
    private void addEditHiddenDanger(HiddenDangerRecord hiddenDangerRecord, String flag) {
        RequestParams params = new RequestParams();
        hiddenDangerRecord.setFlag(record.getFlag());
        String hiddenDangerRecordStr = JSON.toJSONString(hiddenDangerRecord);
        Log.i(TAG, "addHiddenDanger: 隐患添加修改=" + hiddenDangerRecordStr);
        params.put("hiddenDangerRecordJsonData", hiddenDangerRecordStr);
        if (flag.equals("add")) {
            flag = Constants.ADD_HIDDENDANGERRECORD;
        } else {
            flag = Constants.UPDATE_HIDDENDANGERRECORD;
        }
        Log.e(TAG, "addEditHiddenDanger: flag===============" + flag);
        netClient.post(Data.getInstance().getIp() + flag, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "添加修改记录返回数据：" + data);
                getLoadingDialog("正在连接服务器...  ").dismiss();
                if (!TextUtils.isEmpty(data)) {
                    if (TextUtils.isEmpty(record.getId())) {
                        Utils.showShortToast(HiddenRiskRecordAddEditActivity.this, "隐患添加成功！");
                    } else {
                        Utils.showShortToast(HiddenRiskRecordAddEditActivity.this, "隐患修改成功！");
                        setResult(RESULT_OK,new Intent());
                    }
                    finish();
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "添加修改隐患记录返回错误信息：" + content);
                getLoadingDialog("正在连接服务器...  ").dismiss();
                Utils.showShortToast(HiddenRiskRecordAddEditActivity.this, content);
                tvOk.setClickable(true);
            }
        });
    }

    //上传图片
    private void upaction(List<String> picList,final HiddenDangerRecord records,final  String flag) {
        Log.e(TAG, "id==flag========================: "+flag);
        tvOk.setClickable(false);
        List<File> fileList = new ArrayList();
        RequestParams params = new RequestParams();
        getLoadingDialog("正在连接服务器...  ").show();
        try {
            if(!TextUtils.isEmpty(record.getImageGroup())){
                params.put("imgGrop/"+record.getImageGroup(), "imgGrop/"+record.getImageGroup());
            }
            if(updatepaths.size()>0) {
                    for (int i=0;i<paths.size();i++){
                        if(!paths.get(i).contains("http://")){
                            String picurl = paths.get(i);
                            File file = new File(picurl);
                            fileList.add(file);
                            params.put(""+i, file);
                        }
                    }
                    Log.e(TAG, "picList==================="+picList);
                    Log.e(TAG, "updatepaths==================="+paths);
                    params.put("fileurl", JSONArray.toJSONString(paths));
                    String hiddenDangerRecordStr = JSON.toJSONString(records);
                    params.put("record", hiddenDangerRecordStr);
                    Log.e(TAG, "params==========================: "+params);
                    netClient.post(Data.getInstance().getIp() + Constants.UPLOAD_PIC, params, new BaseJsonRes() {

                        @Override
                        public void onMySuccess(String data) {
                            records.setImageGroup(data);
                            addEditHiddenDanger(records, flag);
                            Log.i(TAG, "添加修改记录返回数据：" + data);
                        }

                        @Override
                        public void onMyFailure(String content) {
                            Log.e(TAG, "添加修改隐患记录返回错误信息：" + content);
                            Utils.showShortToast(HiddenRiskRecordAddEditActivity.this, content);
                            tvOk.setClickable(true);
                        }
                    });
                }else{
                    if(paths.size()>0) {
                        if(!TextUtils.isEmpty(record.getImageGroup())){
                            records.setImageGroup(record.getImageGroup());
                        }
                    }else{
                        records.setImageGroup("");
                    }
                    addEditHiddenDanger(records, flag);
                }
            }catch (Exception e) {
                Utils.showShortToast(HiddenRiskRecordAddEditActivity.this, e.toString());
            }
        //}
    }

    private HiddenDangerRecord getHiddenDangerRecord() {
        HiddenDangerRecord record = new HiddenDangerRecord();
        SelectItem spHiddenUnitItem = (SelectItem) spHiddenUnits.getSelectedItem();
        //record.setTeamGroupCode(String.valueOf(spHiddenUnitItem.id));
        record.setTeamGroupId(String.valueOf(spHiddenUnitItem.id));
        record.setTeamGroupName(spHiddenUnitItem.toString());

        SelectItem spProfessionalItem = (SelectItem) spProfessional.getSelectedItem();
        record.setSid(String.valueOf(spProfessionalItem.id));
        record.setSname(spProfessionalItem.toString());


        SelectItem spHiddenClassItem = (SelectItem) spHiddenClass.getSelectedItem();
        record.setGname(spHiddenClassItem.toString());
        record.setGid(String.valueOf(spHiddenClassItem.id));

        SelectItem spHiddenTypesItem = (SelectItem) spHiddenTypes.getSelectedItem();
        record.setTname(spHiddenTypesItem.toString());
        record.setTid(String.valueOf(spHiddenTypesItem.id));


        SelectItem spOrderItem = (SelectItem) spOrder.getSelectedItem();
        record.setClassName(spOrderItem.toString());
        record.setClassId(String.valueOf(spOrderItem.id));

        SelectItem spHiddenSortItem = (SelectItem) spHiddenSort.getSelectedItem();
        record.setHiddenBelong(spHiddenSortItem.toString());
        record.setHiddenBelongId(String.valueOf(spHiddenSortItem.id));

        SelectItem spHiddenAreaItem = (SelectItem) spHiddenArea.getSelectedItem();
        String hiddenareaItem = spHiddenAreaItem.toString();
        hiddenareaItem = hiddenareaItem.replaceAll("#", "_");
        record.setAreaName(hiddenareaItem);
        record.setAreaId(String.valueOf(spHiddenAreaItem.id));

        SelectItem selectedItem = (SelectItem) spCheckContent.getSelectedItem();
        record.setHiddenCheckContent(selectedItem.getName());
        record.setHiddenCheckContentId(selectedItem.getId());

        SelectItem spIsHandleItem = (SelectItem) spIsHandle.getSelectedItem();
        record.setIshandle(String.valueOf(spIsHandleItem.id));

        if (checkYes.isSelected()) {
            record.setIsupervision("1");
        }
        if (checkNos.isSelected()) {
            record.setIsupervision("0");
        }

        record.setJbName(spLevel.getSelectedItem().toString());
        String dateStr = tvDate.getText().toString().replaceAll("\\.", "-");
        dateStr = dateStr + " " + tvTime.getText().toString();
        record.setFindTime(dateStr);
        record.setHiddenPlace(etLocation.getText().toString());
        record.setCheckPerson(etCheckPerson.getText().toString());
        record.setEmployeeId(UserUtils.getUserID(HiddenRiskRecordAddEditActivity.this));
        record.setRealName(UserUtils.getUserName(HiddenRiskRecordAddEditActivity.this));
        record.setFlag(record.getFlag());
        record.setContent(etContent.getText().toString());
        return record;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DatePickerActivity.REQUEST && resultCode == RESULT_OK) {
            tvDate.setText(data.getStringExtra(DatePickerActivity.DATE));
        }
    }

    //检查输入
    private boolean checkInput(HiddenDangerRecord hiddenDangerRecord) {
        if (TextUtils.isEmpty(etContent.getText().toString())) {
            Utils.showShortToast(HiddenRiskRecordAddEditActivity.this, "隐患内容不能为空!");
            return false;
        }
        if (TextUtils.isEmpty(etCheckPerson.getText().toString())) {
            Utils.showShortToast(HiddenRiskRecordAddEditActivity.this, "检查人不能为空!");
            return false;
        }

        if (TextUtils.isEmpty(tvDate.getText().toString())) {
            Utils.showShortToast(HiddenRiskRecordAddEditActivity.this, "隐患时间不能为空!");
            return false;
        }

        String role = UserUtils.getUserRoleids(HiddenRiskRecordAddEditActivity.this);
        String userid = UserUtils.getUserID(HiddenRiskRecordAddEditActivity.this);
        Log.e(TAG, "flag:=== " + hiddenDangerRecord.getFlag() + "userid==========" + userid);
        Log.e(TAG, "getEmployeeId:=== " + hiddenDangerRecord.getEmployeeId() + "userid==========" + userid);
        if (!"1".equals(role) && !userid.equals(hiddenDangerRecord.getEmployeeId())) {
            Utils.showShortToast(HiddenRiskRecordAddEditActivity.this, "您不是管理员或该隐患不是您上报的,不能进行修改!");
            return false;
        }
        if (null != hiddenDangerRecord.getFlag()) {
            if (Integer.parseInt(hiddenDangerRecord.getFlag()) >= 2) {
                Utils.showShortToast(HiddenRiskRecordAddEditActivity.this, "该隐患已经下达不能修改!");
                return false;
            }
        }
        return true;
    }


    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        for (TImage image : result.getImages()) {
            paths.add(image.getOriginalPath());
            updatepaths.add(image.getOriginalPath());
            picid.add("");
        }
        picAdapter.notifyDataSetChanged();

    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
        Toast.makeText(this, "msg", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void takeCancel() {
        super.takeCancel();
        Toast.makeText(this, "取消选择", Toast.LENGTH_SHORT).show();
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
                            //JSONObject returndata = JSON.parseObject(data);
                            //String rows = returndata.getString("rows");
                            JSONArray jsonArray = JSONArray.parseArray(data);
                            for(int i=0;i<jsonArray.size();i++){
                                JSONObject job = jsonArray.getJSONObject(i); // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                                paths.add(Constants.MAIN_ENGINE+job.get("imagePath"));
                                picid.add(job.get("id")+"");
                            }
                            Log.e(TAG, "paths================: "+paths);
                            picAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onMyFailure(String content) {
                        Log.e(TAG, "查询图片组返回错误信息：" + content);
                        Utils.showShortToast(HiddenRiskRecordAddEditActivity.this, content);
                    }
                });
            }
        }catch (Exception e) {
            Utils.showShortToast(HiddenRiskRecordAddEditActivity.this, e.toString());
        }
    }

    private void resultColliery(String data){
        List<CollieryTeam> collieryTeams = JSONArray.parseArray(data, CollieryTeam.class);
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        int collieryTeamsint = 0;
        for (int i = 0; i < collieryTeams.size(); i++) {
            SelectItem selectItem = new SelectItem();
            selectItem.name = collieryTeams.get(i).getTeamName().replaceAll("&nbsp;", " ");
            selectItem.id = collieryTeams.get(i).getId();
            if (collieryTeams.get(i).getId().equals(record.getTeamGroupCode())) {
                collieryTeamsint = i;
            }
            selectItems.add(selectItem);
        }
        mSpHiddenUnitsAdapter = SpinnerAdapter.createFromResource(HiddenRiskRecordAddEditActivity.this, selectItems, Gravity.CENTER_VERTICAL | Gravity.LEFT);
        setUpSpinner(spHiddenUnits, mSpHiddenUnitsAdapter);
        mSpHiddenUnitsAdapter.notifyDataSetChanged();
        spHiddenUnits.setSelection(collieryTeamsint);
    }

    private void resultSpecialty(String data){
        List<Specialty> collieryTeams = JSONArray.parseArray(data, Specialty.class);
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        int sint = 0;
        for (int i = 0; i < collieryTeams.size(); i++) {
            SelectItem selectItem = new SelectItem();
            selectItem.name = collieryTeams.get(i).getSname();
            selectItem.id = collieryTeams.get(i).getId();
            if (collieryTeams.get(i).getId().equals(record.getSid())) {
                sint = i;
            }
            selectItems.add(selectItem);
        }
        spProfessionalAdapter = SpinnerAdapter.createFromResource(HiddenRiskRecordAddEditActivity.this, selectItems);
        setUpSpinner(spProfessional, spProfessionalAdapter);
        spProfessionalAdapter.notifyDataSetChanged();
        spProfessional.setSelection(sint);
    }

    private void resultGrade(String data){
        List<RiskGrade> collieryTeams = JSONArray.parseArray(data, RiskGrade.class);
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        int riskGrade = 0;
        for (int i = 0; i < collieryTeams.size(); i++) {
            SelectItem selectItem = new SelectItem();
            selectItem.name = collieryTeams.get(i).getGname();
            selectItem.id = collieryTeams.get(i).getId();
            selectItems.add(selectItem);
            if (collieryTeams.get(i).getId().equals(record.getGid())) {
                riskGrade = i;
            }
        }
        spHiddenClassAdapter = SpinnerAdapter.createFromResource(HiddenRiskRecordAddEditActivity.this, selectItems);
        setUpSpinner(spHiddenClass, spHiddenClassAdapter);
        spHiddenClassAdapter.notifyDataSetChanged();
        spHiddenClass.setSelection(riskGrade);
    }

    private void resultClassNumber(String data){
        List<ClassNumber> collieryTeams = JSONArray.parseArray(data, ClassNumber.class);
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        int classNumberint = 0;
        for (int i = 0; i < collieryTeams.size(); i++) {
            SelectItem selectItem = new SelectItem();
            selectItem.name = collieryTeams.get(i).getClassName();
            selectItem.id = collieryTeams.get(i).getId();
            selectItems.add(selectItem);
            if (collieryTeams.get(i).getId().equals(record.getClassId())) {
                classNumberint = i;
            }
        }
        spOrderAdapter = SpinnerAdapter.createFromResource(HiddenRiskRecordAddEditActivity.this, selectItems);
        setUpSpinner(spOrder, spOrderAdapter);
        spOrderAdapter.notifyDataSetChanged();
        spOrder.setSelection(classNumberint);
    }

    private void resultArea(String data){
        List<Area> collieryTeams = JSONArray.parseArray(data, Area.class);
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        int areaint = 0;
        for (int i = 0; i < collieryTeams.size(); i++) {
            SelectItem selectItem = new SelectItem();
            selectItem.name = collieryTeams.get(i).getAreaName();
            selectItem.id = collieryTeams.get(i).getId();
            selectItems.add(selectItem);
            if (collieryTeams.get(i).getId().equals(record.getAreaId())) {
                areaint = i;
            }
        }
        spHiddenAreaAdapter = SpinnerAdapter.createFromResource(HiddenRiskRecordAddEditActivity.this, selectItems);
        setUpSpinner(spHiddenArea, spHiddenAreaAdapter);
        spHiddenAreaAdapter.notifyDataSetChanged();
        spHiddenArea.setSelection(areaint);
    }

    private void resultHiddenType(String data){
        List<DataDictionary> collieryTeams = JSONArray.parseArray(data, DataDictionary.class);
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        int dataDictionary = 0;
        for (int i = 0; i < collieryTeams.size(); i++) {
            SelectItem selectItem = new SelectItem();
            selectItem.name = collieryTeams.get(i).getName();
            selectItem.id = collieryTeams.get(i).getId();
            selectItems.add(selectItem);
            if (collieryTeams.get(i).getId().equals(record.getTid())) {
                dataDictionary = i;
            }
        }
        spHiddenTypesAdapter = SpinnerAdapter.createFromResource(HiddenRiskRecordAddEditActivity.this, selectItems);
        setUpSpinner(spHiddenTypes, spHiddenTypesAdapter);
        spHiddenTypesAdapter.notifyDataSetChanged();
        spHiddenTypes.setSelection(dataDictionary);
    }

    private void resultHiddenGrade(String data){
        List<DataDictionary> collieryTeams = JSONArray.parseArray(data, DataDictionary.class);
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        int dataDictionary = 0;
        for (int i = 0; i < collieryTeams.size(); i++) {
            SelectItem selectItem = new SelectItem();
            selectItem.name = collieryTeams.get(i).getName();
            selectItem.id = collieryTeams.get(i).getId();
            selectItems.add(selectItem);
            if (collieryTeams.get(i).getName().equals(record.getJbName())) {
                dataDictionary = i;
            }
        }
        spLevelAdapter = SpinnerAdapter.createFromResource(HiddenRiskRecordAddEditActivity.this, selectItems);
        setUpSpinner(spLevel, spLevelAdapter);
        spLevelAdapter.notifyDataSetChanged();
        spLevel.setSelection(dataDictionary);
    }
    private void resultHiddenYHGSLX(String data){
        List<DataDictionary> collieryTeams = JSONArray.parseArray(data, DataDictionary.class);
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        int dataDictionary = 0;
        for (int i = 0; i < collieryTeams.size(); i++) {
            SelectItem selectItem = new SelectItem();
            selectItem.name = collieryTeams.get(i).getName();
            selectItem.id = collieryTeams.get(i).getId();
            selectItems.add(selectItem);
            if (collieryTeams.get(i).getId().equals(record.getHiddenBelongId())) {
                dataDictionary = i;
            }
        }
        spHiddenSortAdapter = SpinnerAdapter.createFromResource(HiddenRiskRecordAddEditActivity.this, selectItems);
        setUpSpinner(spHiddenSort, spHiddenSortAdapter);
        spHiddenSortAdapter.notifyDataSetChanged();
        spHiddenSort.setSelection(dataDictionary);
    }

    private void resultCheckContent(String data){
        List<DataDictionary> collieryTeams = JSONArray.parseArray(data, DataDictionary.class);
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        int dataDictionary = 0;
        for (int i = 0; i < collieryTeams.size(); i++) {
            SelectItem selectItem = new SelectItem();
            selectItem.name = collieryTeams.get(i).getName();
            selectItem.id = collieryTeams.get(i).getId();
            selectItems.add(selectItem);
            if (collieryTeams.get(i).getId().equals(record.getHiddenCheckContentId())) {
                dataDictionary = i;
            }
        }
        spCheckContentAdapter = SpinnerAdapter.createFromResource(HiddenRiskRecordAddEditActivity.this, selectItems);
        setUpSpinner(spCheckContent, spCheckContentAdapter);
        spCheckContentAdapter.notifyDataSetChanged();
        Log.e(TAG, "onMySuccess: getHiddenCheckContentId====" + dataDictionary);
        spCheckContent.setSelection(dataDictionary);
    }

    public FlippingLoadingDialog getLoadingDialog(String msg) {
        if (mLoadingDialog == null)
            mLoadingDialog = new FlippingLoadingDialog(HiddenRiskRecordAddEditActivity.this, msg);
        return mLoadingDialog;
    }

    /**
     * 保存位图到本地
     * @param bitmap
     * @param path 本地路径
     * @return void
     */
    public void SavaImage(Bitmap bitmap, String path){
        File file=new File(path);
        FileOutputStream fileOutputStream=null;
        //文件夹不存在，则创建它
        if(!file.exists()){
            file.mkdir();
        }
        try {
            fileOutputStream=new FileOutputStream(path+"/"+System.currentTimeMillis()+".png");
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100,fileOutputStream);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
