package com.australia.administrator.australiandelivery.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.australia.administrator.australiandelivery.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.OnekeyShareTheme;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by Administrator on 2017/7/3.
 */

public class CustomDialog extends Dialog {
    @Bind(R.id.img_person_share_friend)
    ImageView imgPersonShareFriend;
    @Bind(R.id.img_person_share_moments)
    ImageView imgPersonShareMoments;
    @Bind(R.id.btn_dialog_cancel)
    Button btnDialogCancel;

    public CustomDialog(@NonNull Context context) {
        super(context);
    }

    public CustomDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_person_share);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.img_person_share_friend, R.id.img_person_share_moments, R.id.btn_dialog_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_person_share_friend:
                showShare(Wechat.NAME, false);
                break;
            case R.id.img_person_share_moments:
                showShare(WechatMoments.NAME, false);
                break;
            case R.id.btn_dialog_cancel:
                this.cancel();
                break;
        }
    }

    public void showShare(String platformToShare, boolean showContentEdit) {
        OnekeyShare oks = new OnekeyShare();
        oks.setSilent(!showContentEdit);
        if (platformToShare != null) {
            oks.setPlatform(platformToShare);
        }
        //ShareSDK快捷分享提供两个界面第一个是九宫格 CLASSIC  第二个是SKYBLUE
        oks.setTheme(OnekeyShareTheme.CLASSIC);
        // 令编辑页面显示为Dialog模式
//        oks.setDialogMode();
        // 在自动授权时可以禁用SSO方式
        oks.disableSSOWhenAuthorize();
        oks.setTitle("标题");
        oks.setTitleUrl("http://www.baidu.com");
        oks.setText("EatAway");
//        oks.setImagePath();

//        oks.setImageUrl(pic);
        oks.setImageUrl("https://www.google.co.jp/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png");
        oks.setUrl("http://www.baidu.com"); //微信不绕过审核分享链接
//        oks.setComment("lkdsjlfj"); //我对这条分享的评论，仅在人人网和QQ空间使用，否则可以不提供
//        oks.setSite("AustralianHeadNews");  //QZone分享完之后返回应用时提示框上显示的名称
//        oks.setSiteUrl(bean.getMsg().get(0).getGuid());//QZone分享参数
        oks.setVenueName("EatAway");
        oks.setVenueDescription("This is a beautiful place!");
        oks.setLatitude(23.169f);
        oks.setLongitude(112.908f);

        oks.show(getContext());
    }
}
