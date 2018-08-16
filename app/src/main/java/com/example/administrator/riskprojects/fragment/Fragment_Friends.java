package com.example.administrator.riskprojects.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.riskprojects.Adpter.SideBar;
import com.example.administrator.riskprojects.R;

//通讯录

public class Fragment_Friends extends Fragment{
	private Activity ctx;
	private View layout, layout_head;
	private ListView lvContact;
	private SideBar indexBar;
	private TextView mDialogText;
	private WindowManager mWindowManager;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		if (layout == null) {
			ctx = this.getActivity();
			layout = ctx.getLayoutInflater().inflate(R.layout.fragment_friends,
					null);
			mWindowManager = (WindowManager) ctx
					.getSystemService(Context.WINDOW_SERVICE);
		} else {
			ViewGroup parent = (ViewGroup) layout.getParent();
			if (parent != null) {
				parent.removeView(layout);
			}
		}
		return layout;
	}

}
