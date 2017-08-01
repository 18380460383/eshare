package com.jiebian.adwlf.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jiebian.adwlf.R;
import com.jiebian.adwlf.bean.InterestBean;
import com.jiebian.adwlf.bean.returned.ItemReturn;
import com.jiebian.adwlf.ui.activity.personal.UserDetailInfoActivity;

import java.util.ArrayList;
import java.util.List;

/**
 *兴趣列表
 */
public class GridAdapter extends BaseAdapter {

    /**选择兴趣的最大个数*/
    private final int MaxChooseCount = 5;
    private final String TAG = "in_adapter";
    private Context mContext;
    private LayoutInflater mLayoutInflater = null;
    /**数据类对象*/
    private ItemReturn itemReturn = null;
    /**列表数据*/
    private List<InterestBean> rows = new ArrayList<>();

    public GridAdapter(Context context, ItemReturn itemReturn) {
        this.mContext = context;
        this.itemReturn = itemReturn;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        if (itemReturn != null && itemReturn.msg != null) {
            this.rows = itemReturn.msg;
        }
    }

    /**
     * 获取当前选中的数量
     * @return 兴趣的选中数
     */
    public int getCurrentCount() {
        int num = 0;
        for(int i = 0; i < this.rows.size(); i++) {
            if(this.rows.get(i).checked) {
                num++;
            }
        }
        return num;
    }

    /**
     * 重新设置列表数据
     */
    public void setData(ItemReturn listBean) {
        Log.i(TAG, "重置兴趣数据");
        this.rows.clear();
        if(listBean != null && listBean.msg!= null) {
            this.rows.addAll(listBean.msg);
        }
        notifyDataSetChanged();
    }

    /**
     * 设置每个item是否被选中
     * @param interest
     */
    public void setChecked(List<InterestBean> interest) {
        Log.i(TAG, "设置了是否点击");
        if(interest == null)
            return;
        for(InterestBean bean : interest) {
            for(int i=0;i<rows.size();i++) {
                if(rows.get(i).iid == bean.iid) {
                    rows.get(i).checked = true;
                    break;
                }
            }
        }
        notifyDataSetChanged();
    }

    /**
     * 获取列表数据
     * @return 数据
     */
    public List<InterestBean> getRows() {
        Log.i(TAG, "调用了getRows");
        return rows;
    }

    @Override
    public Object getItem(int position) {
        if (rows != null && position < rows.size()){
            return rows.get(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        return rows==null?0:rows.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_industry,null);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.industry_name);
            viewHolder.cb = (CheckBox) convertView.findViewById(R.id.industry_ck);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //设置兴趣列表的状态
        final InterestBean bean = rows.get(position);
        //设置点击事件
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (getCurrentCount() >= MaxChooseCount && bean.checked == false&&isChecked==true) {
                    finalViewHolder.cb.setChecked(false);
                    Toast.makeText(mContext, "最多只能选择" + MaxChooseCount + "个哦！返回可保存", Toast.LENGTH_SHORT).show();
                } else {
                    bean.checked = isChecked;
                    int p = (int) finalViewHolder.name.getTag();
//                    setChecked(p);
                }
                ((UserDetailInfoActivity) mContext).setInterestTxt();
            }
        });
        viewHolder.name.setText(bean.item);
        viewHolder.name.setTag(position);
        viewHolder.cb.setChecked(bean.checked);

        return convertView;
    }

    public class ViewHolder{
        TextView name;
        CheckBox cb;
    }

    /**
     * 设置指定位置是否选中
     * @param position 当前位置
     */
    public void setChecked(int position) {
        boolean hascheck = rows.get(position).checked;
        rows.get(position).checked = !hascheck;
        notifyDataSetChanged();
    }

    /**
     * 设置单个兴趣的样式
     */
    private void setChecked(Button button, boolean checked) {
        if(checked) {
            button.setBackgroundResource(R.drawable.gridbtn_1);
            button.setTextColor(mContext.getResources().getColor(R.color.write));
        } else {
            button.setBackgroundResource(R.drawable.gridbtn_0);
            button.setTextColor(mContext.getResources().getColor(R.color.blue));
        }
    }
}
