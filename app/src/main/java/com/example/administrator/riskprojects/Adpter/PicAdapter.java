package com.example.administrator.riskprojects.Adpter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.riskprojects.OnItemClickListener;
import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.activity.ViewBIgPicActivity;

import java.util.List;


public class PicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> paths;

    public PicAdapter(List<String> paths) {
        this.paths = paths;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pic, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        Glide.with(holder.itemView.getContext()).load(paths.get(position)).into(((ViewHolder) holder).pic);

        ((ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) holder.itemView.getContext()).startActivity(
                        new Intent((Activity) holder.itemView.getContext(),
                                ViewBIgPicActivity.class).putExtra("url"
                                ,paths.get(position))
                );
            }
        });
    }

    @Override
    public int getItemCount() {
        return paths.size();
    }



    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView pic;

        ViewHolder(View view) {
            super(view);
            pic = view.findViewById(R.id.pic);
        }
    }

}
