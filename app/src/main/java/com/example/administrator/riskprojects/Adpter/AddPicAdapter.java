package com.example.administrator.riskprojects.Adpter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.riskprojects.OnItemClickListener;
import com.example.administrator.riskprojects.R;


import java.util.List;


public class AddPicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> paths;
    private OnItemClickListener onItemClickListener;

    public AddPicAdapter(List<String> paths) {
        this.paths = paths;
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
                    paths.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, getItemCount() - position);
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
