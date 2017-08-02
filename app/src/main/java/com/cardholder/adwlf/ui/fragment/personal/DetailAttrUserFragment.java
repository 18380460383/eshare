package com.cardholder.adwlf.ui.fragment.personal;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cardholder.adwlf.R;
import com.cardholder.adwlf.bean.PersonEntity;
import com.cardholder.adwlf.util.TextViewUtil;
import com.cardholder.adwlf.view.BackGroundView;
import com.cardholder.adwlf.view.LineTextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Describe:
 * Created by FuPei on 2016/3/11.
 */
public class DetailAttrUserFragment extends Fragment {
    private final String[] source = {"昵\b\b\b\b\b\b称", "报\b\b\b\b\b\b价", "粉丝数量", "行\b\b\b\b\b\b业"
            , "职\b\b\b\b\b\b业", "兴\b\b\b\b\b\b趣"};

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
       // entity = (PersonEntity) getArguments().getSerializable(DetailSourceActivity.EXTRA_PERSON);
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
                    iv_bg.setImageBitmap(BackGroundView.doBlur(bitmap, 23, false));
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
                tv.setText(entity.getUsername());
                break;

            case 1:
                tv.setText(TextViewUtil.getColorText(entity.getRoles_money() + "", "#ff8307"));
                break;

            case 2:
                tv.setText(entity.getFannum());
                break;

            case 3:
                tv.append(entity.getIndustry());
                break;

            case 4:
                tv.setText(entity.getProfession());
                break;

            case 5:
                tv.setText(entity.getInterest());
                break;
        }
    }
}
