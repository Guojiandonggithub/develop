package com.example.administrator.riskprojects.Adpter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.riskprojects.OnItemClickListener;
import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.activity.Data;
import com.example.administrator.riskprojects.net.BaseJsonRes;
import com.example.administrator.riskprojects.net.NetClient;
import com.example.administrator.riskprojects.tools.Constants;
import com.example.administrator.riskprojects.tools.Utils;
import com.example.administrator.riskprojects.view.MyAlertDialog;
import com.juns.health.net.loopj.android.http.RequestParams;

import java.util.List;


public class AddPicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "AddPicAdapter";
    private List<String> paths;
    private List<String> picid;
    private OnItemClickListener onItemClickListener;
    protected NetClient netClient;

    public AddPicAdapter(List<String> paths,List<String> picid) {
        this.paths = paths;
        this.picid = picid;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_added_pic, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        ((ViewHolder) holder).delete.setVisibility(position == paths.size() ? View.GONE : View.VISIBLE);
        Glide.with(holder.itemView.getContext()).load(position == paths.size() ?
                R.mipmap.ic_add_pic : paths.get(position)).into(((ViewHolder) holder).pic);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, position, 0);
                }
            }
        });

        ((ViewHolder) holder).delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (paths.size() > position) {
                    MyAlertDialog myAlertDialog = new MyAlertDialog(holder.itemView.getContext(),
                            new MyAlertDialog.DialogListener() {
                                @Override
                                public void affirm() {
                                    paths.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, getItemCount()-position);
                                    if(!picid.get(position).equals("")){
                                        RequestParams params = new RequestParams();
                                        netClient = new NetClient(holder.itemView.getContext());
                                        params.put("imageId",picid.get(position));
                                        netClient.post(Data.getInstance().getIp()+Constants.DELETE_HIDDENPIC, params, new BaseJsonRes() {

                                            @Override
                                            public void onMySuccess(String data) {
                                                Log.e(TAG, "data===============: "+data);
                                            }

                                            @Override
                                            public void onMyFailure(String content) {
                                                Utils.showShortToast(holder.itemView.getContext(), content);
                                            }
                                        });
                                    }
                                }

                                @Override
                                public void cancel() {

                                }
                            }, "你确定要删除图片吗？");
                    myAlertDialog.show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return paths.size() < 3 ? paths.size() + 1 : 3;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView pic;
        private ImageView delete;

        ViewHolder(View view) {
            super(view);
            pic = view.findViewById(R.id.pic);
            delete = view.findViewById(R.id.delete);
        }
    }

}
