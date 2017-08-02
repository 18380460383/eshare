package com.cardholder.adwlf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cardholder.adwlf.R;
import com.cardholder.adwlf.bean.PersonEntity;
import com.cardholder.adwlf.connector.BackgroundImageLoadingListener;
import com.cardholder.adwlf.control.ImageLoaderOptionsControl;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Describe:
 * Created by FuPei on 2016/2/19.
 */
public class ChoosePersonAdapter extends BaseAdapter {

    private List<PersonEntity> mData;
    private Context mContext;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private PricBack pricback;

    public ChoosePersonAdapter(Context context, List<PersonEntity> data) {
        imageLoader = ImageLoader.getInstance();
        mContext = context;
        mData = data;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HoldView holdView;
        if (convertView == null) {
            holdView = new HoldView();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_choose_person, null);
            convertView.setTag(holdView);
            holdView.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holdView.tv_ename = (TextView) convertView.findViewById(R.id.tv_ename);
            holdView.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
            holdView.tv_type = (TextView) convertView.findViewById(R.id.tv_type);
            holdView.iv_image = (ImageView) convertView.findViewById(R.id.iv_image);
            holdView.cb_state = (CheckBox) convertView.findViewById(R.id.cb_state);
            holdView.tv_media_type = (TextView) convertView.findViewById(R.id.media_type);
            holdView.tv_media_hint = (TextView) convertView.findViewById(R.id.media_hint);
            holdView.tv_media_type_hint = (TextView) convertView.findViewById(R.id.media_hint_type);
            holdView.tv_fans_type_num = (TextView) convertView.findViewById(R.id.fans_num);
        } else {
            holdView = (HoldView) convertView.getTag();
        }
        holdView.cb_state.setOnCheckedChangeListener(null);
        final PersonEntity entity = mData.get(position);
        if (options == null) {

            options = ImageLoaderOptionsControl.getOptions();
        }
        if ("1".equals(entity.getRid())) {
            //企业家
            holdView.tv_media_hint.setVisibility(View.GONE);
            holdView.tv_media_type.setVisibility(View.GONE);
            holdView.tv_media_type_hint.setVisibility(View.GONE);
            holdView.tv_name.setText(entity.getUsername());
            holdView.tv_ename.setText(entity.getCompany());
            holdView.tv_money.setText(entity.getRoles_money() + "");
            holdView.tv_type.setText(entity.getIndustry());
            holdView.cb_state.setChecked(entity.getSelect());
            holdView.tv_fans_type_num.setText(entity.getFannum());

        } else if ("2".equals(entity.getRid())) {
            //媒体人
            holdView.tv_media_hint.setVisibility(View.VISIBLE);
            holdView.tv_media_hint.setText("板块：");
            holdView.tv_media_type.setVisibility(View.VISIBLE);
            holdView.tv_media_type_hint.setVisibility(View.VISIBLE);

            holdView.tv_name.setText(entity.getUsername());
            holdView.tv_ename.setText(entity.getMediaplate());

            holdView.tv_money.setText(entity.getRoles_money() + "");
            holdView.tv_type.setText(entity.getCompany());
            holdView.cb_state.setChecked(entity.getSelect());
            holdView.tv_media_type.setText(entity.getMediatype());
            holdView.tv_fans_type_num.setText(entity.getFannum());
        } else if ("3".equals(entity.getRid())) {
            //小V
            holdView.tv_media_hint.setVisibility(View.VISIBLE);
            holdView.tv_media_hint.setText("职业：");
            holdView.tv_media_type.setVisibility(View.GONE);
            holdView.tv_media_type_hint.setVisibility(View.GONE);
            holdView.tv_name.setText(entity.getUsername());
            holdView.tv_ename.setText(entity.getProfession());
            holdView.tv_money.setText(entity.getRoles_money() + "");
            holdView.tv_type.setText(entity.getIndustry());
            holdView.cb_state.setChecked(entity.getSelect());
            holdView.tv_fans_type_num.setText(entity.getFannum());
        }
        holdView.cb_state.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                entity.setSelect(isChecked);
                if (null != pricback) {
                    if (isChecked) {
                        pricback.pricChange(1, entity);
                    } else {
                        pricback.pricChange(-1, entity);
                    }
                }


            }
        });
        imageLoader.displayImage(entity.getImageurl(), holdView.iv_image, options,new BackgroundImageLoadingListener(holdView.iv_image));

        return convertView;
    }

    public class HoldView {
        /**
         * 角色名
         */
        public TextView tv_name;
        /**
         * 公司名或媒体领域
         */
        public TextView tv_ename;
        /**
         * 行业或媒体
         */
        public TextView tv_type;
        /**
         * 报价
         */
        public TextView tv_money;
        /**
         * 头像
         */
        public ImageView iv_image;
        /**
         * 选择控件
         */
        public CheckBox cb_state;
        /**
         * 媒体人类别
         */
        public TextView tv_media_type;
        /**
         * 媒体人板块提示语
         */
        public TextView tv_media_hint;
        /**
         * 媒体人类别提示语
         */
        public TextView tv_media_type_hint;
        /**
         * 企业规模或者粉丝数
         */
        public TextView tv_fans_type_num;
        public TextView media_hint;
    }

    public void setPricBack(PricBack pricback) {
        this.pricback = pricback;
    }

    public interface PricBack {
        void pricChange(int num, PersonEntity entity);
    }
}
