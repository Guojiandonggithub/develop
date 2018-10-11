package com.example.administrator.riskprojects.Adpter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.riskprojects.OnItemClickListener;
import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.activity.Data;
import com.example.administrator.riskprojects.activity.FiveDecisionsActivity;
import com.example.administrator.riskprojects.activity.HiddenDangerAcceptanceActivity;
import com.example.administrator.riskprojects.activity.HiddenDangerOverdueManagementActivity;
import com.example.administrator.riskprojects.activity.HiddenDangerRectificationManagementActivity;
import com.example.administrator.riskprojects.activity.HiddenDangerReviewManagementActivity;
import com.example.administrator.riskprojects.bean.ThreeFix;
import com.example.administrator.riskprojects.net.BaseJsonRes;
import com.example.administrator.riskprojects.net.NetClient;
import com.example.administrator.riskprojects.tools.Constants;
import com.example.administrator.riskprojects.tools.Utils;
import com.example.administrator.riskprojects.view.MyAlertDialog;
import com.juns.health.net.loopj.android.http.RequestParams;

import java.util.List;

public class HiddenDangeMuitipleAdapter extends RecyclerView.Adapter {
    private static final String TAG = "HiddenDangeMuitipleAdap";
    public static final int FLAG_OVERDUE = 0;
    public static final int FLAG_REALEASE = 1;
    public static final int FLAG_REVIEW = 2;
    public static final int FLAG_RECTIFICATION = 3;
    private int flag = FLAG_OVERDUE;

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    List<ThreeFix> threeFixList;
    private OnItemClickListener itemClickListener;
    protected NetClient netClient;
    Context context;

    public HiddenDangeMuitipleAdapter(int flag, List<ThreeFix> threeFixList, Context context) {
        this.flag = flag;
        this.threeFixList = threeFixList;
        this.context = context;
        netClient = new NetClient(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hidden_danger_management, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((ViewHolder) holder).tvHiddenContent.setText(threeFixList.get(position).getContent());
        ((ViewHolder) holder).tvArea.setText(threeFixList.get(position).getAreaName());
        ((ViewHolder) holder).tvSpecialty.setText(threeFixList.get(position).getSname());
        ((ViewHolder) holder).tvTimeOrOrder.setText(threeFixList.get(position).getClassName());
        ((ViewHolder) holder).tvCategory.setText(threeFixList.get(position).getJbName());
        String isuper = threeFixList.get(position).getIsupervision();
        if (TextUtils.isEmpty(isuper) || TextUtils.equals(isuper, "0")) {
            isuper = "未挂牌";
        } else {
            isuper = "已挂牌";
        }
        ((ViewHolder) holder).tvSupervise.setText(isuper);
        switch (flag) {
            case FLAG_OVERDUE:
                ((ViewHolder) holder).button.setText("重新下达");
                ((ViewHolder) holder).button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final ThreeFix threeFix = threeFixList.get(position);
                        /*Intent intent = new Intent(holder.itemView.getContext(),
                                HiddenDangerOverdueManagementActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("threeFix",threeFix);
                        intent.putExtra("threeBund",bundle);
                        holder.itemView.getContext().startActivity(intent);
                        */
                        MyAlertDialog myAlertDialog = new MyAlertDialog(context,
                                new MyAlertDialog.DialogListener() {
                                    @Override
                                    public void affirm() {
                                        handleOutTime(threeFix.getId(), position);
                                    }

                                    @Override
                                    public void cancel() {

                                    }
                                }, "你确定要重新下达么？");
                        myAlertDialog.show();
                    }
                });
                ((ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final ThreeFix threeFix = threeFixList.get(position);
                        Intent intent = new Intent(holder.itemView.getContext(),
                                HiddenDangerOverdueManagementActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("threeFix", threeFix);
                        intent.putExtra("threeBund", bundle);
                        holder.itemView.getContext().startActivity(intent);


                    }
                });
                break;
            case FLAG_REALEASE:
                ((ViewHolder) holder).button.setText("五定");
                ((ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ThreeFix threeFix = threeFixList.get(position);
                       /* Intent intent = new Intent(holder.itemView.getContext(),
                                HiddenDangerReleaseManagementActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("threeFix",threeFix);
                        intent.putExtra("threeBund",bundle);
                        holder.itemView.getContext().startActivity(intent);*/

                        Intent intent = new Intent(
                                holder.itemView.getContext(),
                                FiveDecisionsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("threeFix", threeFix);
                        intent.putExtra("threeBund", bundle);
                        holder.itemView.getContext().startActivity(intent);
                    }
                });
                break;

            case FLAG_REVIEW:
                ((ViewHolder) holder).button.setText("验收");
                ((ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ThreeFix threeFix = threeFixList.get(position);
                        /*Intent intent = new Intent(holder.itemView.getContext(),
                                HiddenDangerReviewManagementActivity.class);

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("threeFix",threeFix);
                        intent.putExtra("threeBund",bundle);
                        holder.itemView.getContext().startActivity(intent);*/
                        Intent intent = new Intent(holder.itemView.getContext(), HiddenDangerAcceptanceActivity.class);
                        intent.putExtra("threeFixId", threeFix.getId());
                        intent.putExtra("recheckresult", threeFix.getRecheckResult());
                        intent.putExtra("description", threeFix.getDescription());
                        intent.putExtra("recheckPersonId", threeFix.getRecheckPersonId());
                        intent.putExtra("recheckPersonName", threeFix.getRecheckPersonName());
                        holder.itemView.getContext().startActivity(intent);
                    }
                });
                break;

            case FLAG_RECTIFICATION:
                ((ViewHolder) holder).button.setText("整改");
                ((ViewHolder) holder).button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (itemClickListener != null) {
                            itemClickListener.onItemClick(view, position, flag);
                        }
                    }
                });
                ((ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(holder.itemView.getContext(), HiddenDangerRectificationManagementActivity.class);
                        final ThreeFix threeFix = threeFixList.get(position);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("threeFix", threeFix);
                        intent.putExtra("threeBund", bundle);
                        holder.itemView.getContext().startActivity(intent);

//                        MyAlertDialog myAlertDialog = new MyAlertDialog(context,
//                                new MyAlertDialog.DialogListener() {
//                                    @Override
//                                    public void affirm() {
//                                        //确定入口
//                                        getHiddenRecord(threeFix,position);
//                                    }
//
//                                    @Override
//                                    public void cancel() {
//
//                                    }
//                                },"你确定要整改此隐患吗？" );
//                        myAlertDialog.show();
//
                    }
                });
                break;
        }

    }

    //隐逾期患重新下达
    private void handleOutTime(String id, final int position) {//隐患id
        RequestParams params = new RequestParams();
        params.put("ids", id);
        netClient.post(Data.getInstance().getIp() + Constants.HANDLEOUT_OVERDUELIST, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "隐逾期患重新下达返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    Utils.showLongToast(context, "重新下达成功");
                    threeFixList.remove(position);
                    notifyItemRemoved(position);
                }
            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "隐逾期患重新下达返回错误信息：" + content);
                Utils.showLongToast(context, content);
                return;
            }
        });
    }

    @Override
    public int getItemCount() {
        return threeFixList.size();
    }

    private void initView(View view) {

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvHiddenContent;
        private TextView tvArea;
        private TextView tvSpecialty;
        private TextView tvTimeOrOrder;
        private TextView tvCategory;
        private TextView tvSupervise;
        private TextView button;
        private LinearLayoutCompat clickMore;

        ViewHolder(View view) {
            super(view);
            tvHiddenContent = view.findViewById(R.id.tv_hidden_content);
            tvArea = view.findViewById(R.id.tv_area);
            tvSpecialty = view.findViewById(R.id.tv_specialty);
            tvTimeOrOrder = view.findViewById(R.id.tv_time_or_order);
            tvCategory = view.findViewById(R.id.tv_category);
            tvSupervise = view.findViewById(R.id.tv_supervise);
            clickMore = view.findViewById(R.id.click_more);
            button = view.findViewById(R.id.button);
        }
    }

    //完成整改
    private void getHiddenRecord(final ThreeFix threeFix, final int position) {
        RequestParams params = new RequestParams();
        params.put("ids", threeFix.getId());
        netClient.post(Data.getInstance().getIp() + Constants.COMPLETERECTIFY, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "完成整改返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    Utils.showLongToast(context, "隐患整改成功！");
                    threeFixList.remove(position);
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "完成整改返回错误信息：" + content);
                Utils.showLongToast(context, content);
            }
        });
    }
}
