package com.australia.administrator.australiandelivery.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.australia.administrator.australiandelivery.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/7/28.
 */

public class StarBar extends LinearLayout {
    @Bind(R.id.iv_star_01)
    ImageView ivStar01;
    @Bind(R.id.iv_star_02)
    ImageView ivStar02;
    @Bind(R.id.iv_star_03)
    ImageView ivStar03;
    @Bind(R.id.iv_star_04)
    ImageView ivStar04;
    @Bind(R.id.iv_star_05)
    ImageView ivStar05;
    @Bind(R.id.ll_starbar)
    LinearLayout llStarbar;
    private Context context;
    private int starNums = 1;

    public StarBar(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public StarBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    private void initView() {
        LayoutInflater.from(context).inflate(R.layout.layout_star_bar, this, true);
        ButterKnife.bind(this);
    }

    public int getStarNums() {
        return starNums;
    }

    public void setStarNums(int num) {
        switch (num) {
            case 1:
                ivStar01.setBackgroundResource(R.drawable.img_icon_star_sel);
                ivStar02.setBackgroundResource(R.drawable.img_icon_star_nor);
                ivStar03.setBackgroundResource(R.drawable.img_icon_star_nor);
                ivStar04.setBackgroundResource(R.drawable.img_icon_star_nor);
                ivStar05.setBackgroundResource(R.drawable.img_icon_star_nor);
                break;
            case 2:
                ivStar01.setBackgroundResource(R.drawable.img_icon_star_sel);
                ivStar02.setBackgroundResource(R.drawable.img_icon_star_sel);
                ivStar03.setBackgroundResource(R.drawable.img_icon_star_nor);
                ivStar04.setBackgroundResource(R.drawable.img_icon_star_nor);
                ivStar05.setBackgroundResource(R.drawable.img_icon_star_nor);
                break;
            case 3:
                ivStar01.setBackgroundResource(R.drawable.img_icon_star_sel);
                ivStar02.setBackgroundResource(R.drawable.img_icon_star_sel);
                ivStar03.setBackgroundResource(R.drawable.img_icon_star_sel);
                ivStar04.setBackgroundResource(R.drawable.img_icon_star_nor);
                ivStar05.setBackgroundResource(R.drawable.img_icon_star_nor);
                break;
            case 4:
                ivStar01.setBackgroundResource(R.drawable.img_icon_star_sel);
                ivStar02.setBackgroundResource(R.drawable.img_icon_star_sel);
                ivStar03.setBackgroundResource(R.drawable.img_icon_star_sel);
                ivStar04.setBackgroundResource(R.drawable.img_icon_star_sel);
                ivStar05.setBackgroundResource(R.drawable.img_icon_star_nor);
                break;
            case 5:
                ivStar01.setBackgroundResource(R.drawable.img_icon_star_sel);
                ivStar02.setBackgroundResource(R.drawable.img_icon_star_sel);
                ivStar03.setBackgroundResource(R.drawable.img_icon_star_sel);
                ivStar04.setBackgroundResource(R.drawable.img_icon_star_sel);
                ivStar05.setBackgroundResource(R.drawable.img_icon_star_sel);
                break;
        }
    }

    @Nullable
    @OnClick({R.id.iv_star_01, R.id.iv_star_02, R.id.iv_star_03, R.id.iv_star_04, R.id.iv_star_05})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_star_01:
                setStarNums(1);
                starNums = 1;
                break;
            case R.id.iv_star_02:
                setStarNums(2);
                starNums = 2;
                break;
            case R.id.iv_star_03:
                setStarNums(3);
                starNums = 3;
                break;
            case R.id.iv_star_04:
                setStarNums(4);
                starNums = 4;
                break;
            case R.id.iv_star_05:
                setStarNums(5);
                starNums = 5;
                break;
        }
    }
}
