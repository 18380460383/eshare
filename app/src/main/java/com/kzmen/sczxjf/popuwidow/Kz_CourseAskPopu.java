package com.kzmen.sczxjf.popuwidow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.cusinterface.PlayPopuInterface;
import com.kzmen.sczxjf.util.EToastUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.kzmen.sczxjf.R.id.tv_cancle;

/**
 * Created by pjj18 on 2017/8/10.
 */

public class Kz_CourseAskPopu extends PopupWindow {
    @InjectView(tv_cancle)
    TextView tvCancle;
    @InjectView(R.id.tv_price)
    TextView tvPrice;
    @InjectView(R.id.ll_submit)
    LinearLayout llSubmit;
    @InjectView(R.id.et_content)
    EditText etContent;
    @InjectView(R.id.tv_content_count)
    TextView tvContentCount;
    private Context mContext;
    private View view;
    private PlayPopuInterface popuInterface;
    private int playPos = -1;


    public int getPlayPos() {
        return playPos;
    }

    public void setPlayPos(int playPos) {
        this.playPos = playPos;
    }

    public Kz_CourseAskPopu(Context mContext) {
        this.mContext=mContext;
        this.view = LayoutInflater.from(mContext).inflate(R.layout.kz_popu_course_ask, null);
        ButterKnife.inject(this, view);
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvContentCount.setText(s.length()+"/1000");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        // 设置外部可点击
        this.setOutsideTouchable(false);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
    /* 设置弹出窗口特征 */
        // 设置视图
        this.setContentView(this.view);
        // 设置弹出窗体的宽和高
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        // 设置弹出窗体可点击
        this.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);
        // 设置弹出窗体显示时的动画，从底部向上弹出
        this.setAnimationStyle(R.style.playlist);
    }

    @OnClick({tv_cancle, R.id.ll_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case tv_cancle:
                dismiss();
                break;
            case R.id.ll_submit:
                EToastUtil.show(mContext,"支付");
                dismiss();
                break;
        }
    }
}
