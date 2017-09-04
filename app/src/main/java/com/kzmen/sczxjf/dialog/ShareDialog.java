package com.kzmen.sczxjf.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.view.ExPandGridView;
import com.vondear.rxtools.RxDataUtils;
import com.vondear.rxtools.view.RxToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.wechat.friends.Wechat;

import static com.alibaba.sdk.android.feedback.xblink.config.GlobalConfig.context;

public class ShareDialog {

	private AlertDialog dialog;
	private ExPandGridView gridView;
	private RelativeLayout cancelButton;
	private SimpleAdapter saImageItems;
	private int[] image={R.drawable.icon_weibo,R.drawable.icon_wechat,R.drawable.icon_pyq};
	private String[] name={"新浪微博","微信","微信朋友圈"};

	public ShareDialog(Context context) {
		initDialog(context);
	}
	private void initDialog(Context context){
		dialog=new AlertDialog.Builder(context).create();
		dialog.show();
		Window window = dialog.getWindow();
		window.setContentView(R.layout.kz_share_dialog);
		gridView=(ExPandGridView) window.findViewById(R.id.share_gridView);
		cancelButton=(RelativeLayout) window.findViewById(R.id.share_cancel);
		List<HashMap<String, Object>> shareList=new ArrayList<HashMap<String,Object>>();
		for(int i=0;i<image.length;i++){
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ItemImage", image[i]);//添加图像资源的ID
			map.put("ItemText", name[i]);//按序号做ItemText
			shareList.add(map);
		}
		saImageItems =new SimpleAdapter(context, shareList, R.layout.kz_share_item, new String[] {"ItemImage","ItemText"}, new int[] {R.id.imageView1,R.id.textView1});
		gridView.setAdapter(saImageItems);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				switch (name[position]){
					case "新浪微博":
						RxToast.normal("新浪微博");
						break;
					case "微信":
						showShare();
						break;
					case "微信朋友圈":
						RxToast.normal("微信朋友圈");
						break;
				}
			}
		});
        window.setBackgroundDrawableResource(R.color.transparent);
		WindowManager.LayoutParams lp = window.getAttributes();
		DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
		lp.width = (int) (d.widthPixels * 0.8); // 宽度设置为屏幕的0.8
		window.setAttributes(lp);
	}
	public static void showShare() {
		OnekeyShare oks = new OnekeyShare();
		oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
		oks.setTitleUrl("http://www.baidu.com");
		oks.setText("text");
		oks.setTitle("标题");
		oks.setPlatform(Wechat.NAME);
		oks.show(context);
	}
	public void setCancelButtonOnClickListener(View.OnClickListener Listener){
		cancelButton.setOnClickListener(Listener);
	}

	public void setOnItemClickListener(OnItemClickListener listener){
		gridView.setOnItemClickListener(listener);
	}


	/**
	 * 关闭对话框
	 */
	public void dismiss() {
		dialog.dismiss();
	}
}