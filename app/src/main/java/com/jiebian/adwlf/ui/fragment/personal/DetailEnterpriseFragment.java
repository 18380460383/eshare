package com.jiebian.adwlf.ui.fragment.personal;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jiebian.adwlf.R;
import com.jiebian.adwlf.bean.PersonEntity;
import com.jiebian.adwlf.util.TextViewUtil;
import com.jiebian.adwlf.view.BackGroundView;
import com.jiebian.adwlf.view.LineTextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Describe:企业详情信息查看
 * Created by FuPei on 2016/2/25.
 */
public class DetailEnterpriseFragment extends Fragment {

    private final String[] source = {"昵\b\b\b\b\b\b称", "姓\b\b\b\b\b\b名", "企业名称", "报\b\b\b\b\b\b价", "所属行业", "人数规模"
            , "营业额度", "地\b\b\b\b\b\b址", "联系电话"};

    @InjectView(R.id.iv_logo)
    public CircleImageView iv_logo;
    @InjectView(R.id.layout_main)
    public LinearLayout ly_main;
    @InjectView(R.id.iv_bg)
    public BackGroundView iv_bg;

    private PersonEntity entity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_person, null);
        ButterKnife.inject(this, view);
        //entity = (PersonEntity) getArguments().getSerializable(DetailSourceActivity.EXTRA_PERSON);
        initView();
        return view;
    }

    private void initView() {
        for (int i = 0; i < source.length; i++) {
            LineTextView textView = new LineTextView(getActivity());
            setTextViewText(i, textView);
            textView.setPadding(20, 30, 20, 30);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
            textView.setGravity(Gravity.RIGHT);
            ly_main.addView(textView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
        }

        ImageLoader.getInstance().displayImage(entity.getImageurl(), iv_logo, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
                iv_bg.setImageResource(R.mipmap.image_def);
                iv_logo.setImageResource(R.mipmap.image_def);
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                if (bitmap != null) {
                    try {
                        iv_bg.setImageBitmap(BackGroundView.doBlur(bitmap, 23, false));
                    } catch (Exception e) {
                        iv_bg.setImageBitmap(bitmap);
                    }
                } else {
                    //设置没有头像的默认显示
                }
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });
    }

    private void setTextViewText(int index, LineTextView tv) {
        tv.setTypeText(source[index]);
        switch (index) {
            case 0:
                if (TextUtils.isEmpty(entity.getUsername())) {
                    tv.setVisibility(View.GONE);
                } else {
                    tv.setText(entity.getUsername());
                }
                break;

            case 1:
                if (TextUtils.isEmpty(entity.getNickname())) {
                    tv.setVisibility(View.GONE);
                } else {
                    tv.setText(entity.getNickname());
                }
                break;

            case 2:
                tv.setText(entity.getCompany());
                break;

            case 3:
                tv.append(TextViewUtil.getColorText(entity.getRoles_money() + "", "#ff8307"));
                break;

            case 4:
                tv.setText(entity.getIndustry());
                break;

            case 5:
                if ("1".equals(entity.getHeadcount_hidden())) {
                    tv.setVisibility(View.GONE);
                } else {
                    tv.setText(entity.getHeadcount());
                }
                break;

            case 6:
                if ("1".equals(entity.getTurnover_hidden())) {
                    tv.setVisibility(View.GONE);
                } else {
                    tv.setText(entity.getTurnover());
                }
                break;

            case 7:
                tv.setText(entity.getAddress());
                break;

            case 8:
                if ("1".equals(entity.getTel_hidden())) {
                    tv.setVisibility(View.GONE);
                } else {
                    tv.setText(entity.getTel());
                }
                break;

        }
    }

}
