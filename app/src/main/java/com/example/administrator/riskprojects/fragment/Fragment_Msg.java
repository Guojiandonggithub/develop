package com.example.administrator.riskprojects.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.riskprojects.Adpter.NewMsgAdpter;
import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.activity.MainActivity;
import com.example.administrator.riskprojects.bean.PublicMsgInfo;
import com.example.administrator.riskprojects.common.NetUtil;
import com.example.administrator.riskprojects.tools.Constants;
import com.example.administrator.riskprojects.tools.Utils;
import java.util.Collections;
import java.util.Hashtable;

//消息
public class Fragment_Msg extends Fragment{
	private Activity ctx;
	private View layout, layout_head;
	public RelativeLayout errorItem;
	public TextView errorText;
	private ListView lvContact;
	private NewMsgAdpter adpter;
	private MainActivity parentActivity;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		if (layout == null) {
			ctx = this.getActivity();
			parentActivity = (MainActivity) getActivity();
			layout = ctx.getLayoutInflater().inflate(R.layout.fragment_msg,
					null);
			/*lvContact = (ListView) layout.findViewById(R.id.listview);
			errorItem = (RelativeLayout) layout
					.findViewById(R.id.rl_error_item);*/
		} else {
			ViewGroup parent = (ViewGroup) layout.getParent();
			if (parent != null) {
				parent.removeView(layout);
			}
		}
		return layout;
	}

	@Override
	public void onResume() {
		super.onResume();
		initViews();
	}

	/**
	 * 刷新页面
	 */
	public void refresh() {
		initViews();
	}

	private void initViews() {
		/*conversationList.addAll(loadConversationsWithRecentChat());
		if (conversationList != null && conversationList.size() > 0) {
			layout.findViewById(R.id.txt_nochat).setVisibility(View.GONE);
			adpter = new NewMsgAdpter(getActivity(), conversationList);
			// TODO 加载订阅号信息 ，增加一个Item
			// if (GloableParams.isHasPulicMsg) {
			EMConversation nee = new EMConversation("100000");
			conversationList.add(0, nee);
			String time = Utils.getValue(getActivity(), "Time");
			String content = Utils.getValue(getActivity(), "Content");
			time = "下午 02:45";
			content = "[腾讯娱乐] 赵薇炒股日赚74亿";
			PublicMsgInfo msgInfo = new PublicMsgInfo();
			msgInfo.setContent(content);
			msgInfo.setMsg_ID("12");
			msgInfo.setTime(time);
			adpter.setPublicMsg(msgInfo);
			// }
			lvContact.setAdapter(adpter);
		} else {
			layout.findViewById(R.id.txt_nochat).setVisibility(View.VISIBLE);
		}*/
	}

	/**
	 * 获取所有会话
	 *
	 * @param context
	 * @return +
	 */
	/*private List<EMConversation> loadConversationsWithRecentChat() {
		// 获取所有会话，包括陌生人
		Hashtable<String, EMConversation> conversations = EMChatManager
				.getInstance().getAllConversations();
		List<EMConversation> list = new ArrayList<EMConversation>();
		// 过滤掉messages seize为0的conversation
		for (EMConversation conversation : conversations.values()) {
			if (conversation.getAllMessages().size() != 0)
				list.add(conversation);
		}
		return null;
	}*/


}
