package com.australia.administrator.australiandelivery.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.australia.administrator.australiandelivery.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/21.
 */

public class TopBar extends RelativeLayout {
    @Bind(R.id.tb_left_iv)
    ImageView tbLeftIv;
    @Bind(R.id.tb_center_tv)
    TextView tbCenterTv;
    @Bind(R.id.tb_right_iv)
    ImageView tbRightIv;
    @Bind(R.id.tb_right_tv)
    TextView tbRightTv;
    @Bind(R.id.rl_topbar)
    RelativeLayout rlTopbar;
    private Context context;

    public TopBar(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public TopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public TopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    private void initView() {
        LayoutInflater.from(context).inflate(R.layout.topbar, this, true);
        ButterKnife.bind(this);
    }

    public void setTbLeftIv(int image, OnClickListener clickListener) {
        tbLeftIv.setVisibility(VISIBLE);
        tbLeftIv.setImageDrawable(getResources().getDrawable(image));
        tbLeftIv.setOnClickListener(clickListener);
    }

    public void setTbLeftIvV(boolean isv) {
        if (isv) {
            tbLeftIv.setVisibility(GONE);
        } else {
            tbLeftIv.setVisibility(VISIBLE);
        }
    }

    public void setTbCenterTv(int tatle,int color) {
        tbCenterTv.setText(tatle);
        tbCenterTv.setTextColor(getResources().getColor(color));
    }

    public void setTbRightIv(int image, OnClickListener clickListener) {
        tbRightIv.setVisibility(VISIBLE);
        tbRightIv.setImageDrawable(getResources().getDrawable(image));
        tbRightIv.setOnClickListener(clickListener);
    }

    public void setTbRightIvV(boolean isv) {
        tbRightTv.setVisibility(GONE);
        if (isv) {
            tbRightIv.setVisibility(GONE);
        } else {
            tbRightIv.setVisibility(VISIBLE);
        }
    }
    public void setTbRightTv(int string,OnClickListener listener){
        tbRightTv.setText(string);
        tbRightIv.setVisibility(GONE);
        tbRightTv.setOnClickListener(listener);
    }
    public void  setBackGround(int drawable){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            rlTopbar.setBackground(getResources().getDrawable(drawable));
        }
    }
    public void setBackground(int background){
        rlTopbar.getBackground().setAlpha(background);
    }
}
