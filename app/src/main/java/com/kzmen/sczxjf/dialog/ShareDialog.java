package com.kzmen.sczxjf.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import com.kzmen.sczxjf.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShareDialog {

	private AlertDialog dialog;
	private GridView gridView;
	private RelativeLayout cancelButton;
	private SimpleAdapter saImageItems;
	private int[] image={R.drawable.logo_sinaweibo,R.drawable.logo_wechat,R.drawable.logo_wechatmoments};
	private String[] name={"新浪微博","微信","微信朋友圈"};

	public ShareDialog(Context context) {
		initDialog(context);
	}
	private void initDialog(Context context){
		dialog=new AlertDialog.Builder(context).create();
		dialog.show();
		Window window = dialog.getWindow();
		window.setContentView(R.layout.kz_share_dialog);
		gridView=(GridView) window.findViewById(R.id.share_gridView);
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
        window.setBackgroundDrawableResource(R.color.transparent);
		WindowManager.LayoutParams lp = window.getAttributes();
		DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
		lp.width = (int) (d.widthPixels * 0.8); // 宽度设置为屏幕的0.8
		window.setAttributes(lp);
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