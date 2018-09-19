package com.example.administrator.riskprojects.Adpter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ArrayRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.bean.SelectItem;

import java.util.List;

public class SpinnerAdapter<T> extends ArrayAdapter<T> {

    private int selectedPostion;
    private int gravity = Gravity.CENTER;

    public void setSelectedPostion(int selectedPostion) {
        this.selectedPostion = selectedPostion;
    }

    public SpinnerAdapter(@NonNull Context context, int resource, @NonNull T[] objects) {
        super(context, resource, objects);
    }

    public SpinnerAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<T> objects) {
        super(context, resource, textViewResourceId, objects);
        this.setDropDownViewResource(R.layout.spinner_dropdown);
    }

    public SpinnerAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<T> objects,int gravity) {
        this(context, resource, textViewResourceId, objects);
        this.gravity=gravity;
    }


    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = super.getDropDownView(position, convertView, parent);
        TextView textView = (TextView) view.findViewById(R.id.textView);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);

        if (selectedPostion == position) {
            textView.setGravity(gravity);
            textView.setTextColor(Color.parseColor("#1197DB"));
            imageView.setVisibility(View.VISIBLE);

//            textView.getPaint().setFakeBoldText(true);
        } else {
            textView.setGravity(gravity);
            textView.setTextColor(Color.parseColor("#666666"));
//            textView.getPaint().setFakeBoldText(false);
            imageView.setVisibility(View.GONE);
        }
        textView.setSelected(true);
        return view;
    }

    public static @NonNull
    SpinnerAdapter<SelectItem> createFromResource(@NonNull Context context,
                                                  @NonNull List<SelectItem> objects) {
        return new SpinnerAdapter<>(context, R.layout.spinner_layout, R.id.textView, objects);
    }

    public static @NonNull
    SpinnerAdapter<SelectItem> createFromResource(@NonNull Context context,
                                                  @NonNull List<SelectItem> objects,int gravity) {
        return new SpinnerAdapter<>(context, R.layout.spinner_layout, R.id.textView, objects,gravity);
    }
}
