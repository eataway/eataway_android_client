package com.australia.administrator.australiandelivery.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class TextDialog extends Dialog {

    @Bind(R.id.tv_dialog)
    TextView tvDialog;
    @Bind(R.id.btn_dialog_cancel)
    Button btnDialogCancel;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    private String title;
    private String content;

    public TextDialog(@NonNull Context context) {
        super(context);
    }

    public TextDialog(@NonNull Context context, @StyleRes int themeResId, String title, String content) {
        super(context, themeResId);
        this.title = title;
        this.content = content;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_text);
        ButterKnife.bind(this);
        tvTitle.setText(title);
        tvDialog.setText(content);
        btnDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
    }
}
