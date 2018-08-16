package com.example.administrator.riskprojects.Adpter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaRouter;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.bean.PublicMsgInfo;
import com.example.administrator.riskprojects.bean.UserInfo;
import com.example.administrator.riskprojects.common.ViewHolder;
import com.example.administrator.riskprojects.dialog.WarnTipDialog;
import com.example.administrator.riskprojects.net.NetClient;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;

public class NewMsgAdpter extends BaseAdapter {
	protected Context context;
	private WarnTipDialog Tipdialog;
	private int deleteID;
	private String ChatID;
	private NetClient netClient;
	private String userid;
	private Hashtable<String, String> ChatRecord = new Hashtable<String, String>();

	public NewMsgAdpter(Context ctx, List<UserInfo> objects) {
		context = ctx;
		netClient = new NetClient(ctx);
	}


	@Override
	public int getCount() {
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		/*if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.layout_item_msg, parent, false);
		}
		ImageView img_avar = ViewHolder.get(convertView,
				R.id.contactitem_avatar_iv);
		TextView txt_name = ViewHolder.get(convertView, R.id.txt_name);
		TextView txt_state = ViewHolder.get(convertView, R.id.txt_state);
		TextView txt_del = ViewHolder.get(convertView, R.id.txt_del);
		TextView txt_content = ViewHolder.get(convertView, R.id.txt_content);
		TextView txt_time = ViewHolder.get(convertView, R.id.txt_time);
		TextView unreadLabel = ViewHolder.get(convertView,
				R.id.unread_msg_number);
		SwipeLayout swipe = ViewHolder.get(convertView, R.id.swipe);
		if (PublicMsg != null && position == 0) {
			txt_name.setText(R.string.official_accounts);
			img_avar.setImageResource(R.drawable.icon_public);
			txt_time.setText(PublicMsg.getTime());
			txt_content.setText(PublicMsg.getContent());
			unreadLabel.setText("3");
			unreadLabel.setVisibility(View.VISIBLE);
			swipe.setSwipeEnabled(false);
		} else {
			swipe.setSwipeEnabled(true);
			// 获取与此用户/群组的会话
			final EMConversation conversation = conversationList.get(position);
			// 获取用户username或者群组groupid
			ChatID = conversation.getUserName();
			txt_del.setTag(ChatID);
			if (conversation.isGroup()) {
				img_avar.setImageResource(R.drawable.defult_group);
				GroupInfo info = GloableParams.GroupInfos.get(ChatID);
				if (info != null) {
					txt_name.setText(info.getGroup_name());
				} else {
					// initGroupInfo(img_avar, txt_name);// 获取群组信息
				}
			} else {
				User user = GloableParams.Users.get(ChatID);
				if (user != null) {
					txt_name.setText(user.getUserName());
				} else {
					txt_name.setText("好友");
					UserUtils.initUserInfo(context, ChatID, img_avar, txt_name);// 获取用户信息
				}
			}
			if (conversation.getUnreadMsgCount() > 0) {
				// 显示与此用户的消息未读数
				unreadLabel.setText(String.valueOf(conversation
						.getUnreadMsgCount()));
				unreadLabel.setVisibility(View.VISIBLE);
			} else {
				unreadLabel.setVisibility(View.INVISIBLE);
			}
			if (conversation.getMsgCount() != 0) {
				// 把最后一条消息的内容作为item的message内容
				EMMessage lastMessage = conversation.getLastMessage();
				txt_content.setText(
						SmileUtils.getSmiledText(context,
								getMessageDigest(lastMessage, context)),
						BufferType.SPANNABLE);
				txt_time.setText(DateUtils.getTimestampString(new Date(
						lastMessage.getMsgTime())));
				if (lastMessage.status == EMMessage.Status.SUCCESS) {
					txt_state.setText("送达");
					// txt_state.setBackgroundResource(R.drawable.btn_bg_orgen);
				} else if (lastMessage.status == EMMessage.Status.FAIL) {
					txt_state.setText("失败");
					// txt_state.setBackgroundResource(R.drawable.btn_bg_red);
				} else if (lastMessage.direct == EMMessage.Direct.RECEIVE) {
					txt_state.setText("已读");
					txt_state.setBackgroundResource(R.drawable.btn_bg_blue);
				}
			}

			txt_del.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					deleteID = position;
					Tipdialog = new WarnTipDialog((Activity) context,
							"您确定要删除该聊天吗？");
					Tipdialog.setBtnOkLinstener(onclick);
					Tipdialog.show();
				}
			});
		}
		return convertView;*/
		return null;
	}



	String getStrng(Context context, int resId) {
		return context.getResources().getString(resId);
	}
}
