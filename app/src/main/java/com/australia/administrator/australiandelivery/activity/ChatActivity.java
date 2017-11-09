package com.australia.administrator.australiandelivery.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.FrameLayout;

import com.australia.administrator.australiandelivery.MyApplication;
import com.australia.administrator.australiandelivery.R;
import com.australia.administrator.australiandelivery.comm.BaseActivity;
import com.australia.administrator.australiandelivery.fragment.ChatFragment;
import com.australia.administrator.australiandelivery.model.PermissionsManager;
import com.australia.administrator.australiandelivery.ui.TextDialog;
import com.australia.administrator.australiandelivery.view.TopBar;

import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.util.EasyUtils;

import butterknife.Bind;

public class ChatActivity extends BaseActivity {
    @Bind(R.id.fl_chat_container)
    FrameLayout flChatContainer;

    public static ChatActivity activityInstance;
    @Bind(R.id.tp_activity_chat)
    TopBar tpActivityChat;
    private EaseChatFragment chatFragment;
    String toChatUsername = "eataway";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chat;
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityInstance = this;
        initTopBar();
        //use EaseChatFratFragment
        chatFragment = new ChatFragment();
        //pass parameters to chat fragment
        Bundle b = new Bundle();
        b.putString(EaseConstant.EXTRA_USER_ID, toChatUsername);
        b.putString("user", MyApplication.getLogin().getUserId());
        b.putString("token", MyApplication.getLogin().getToken());
        b.putString("name", MyApplication.getLogin().getUserName());
        chatFragment.setArguments(b);
        getSupportFragmentManager().beginTransaction().add(R.id.fl_chat_container, chatFragment).commit();
        TextDialog dialog = new TextDialog(this, R.style.ShareDialog, getString(R.string.wen_xin_ti_shi), getString(R.string.qing_zhuan_ren_gong_ke_fu));
        dialog.show();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // make sure only one chat activity is opened
        String username = intent.getStringExtra("userId");

        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }

    }

    private void initTopBar() {
        tpActivityChat.setTbCenterTv(R.string.left_custom_service, R.color.white);
        tpActivityChat.setTbLeftIv(R.drawable.img_back_icon_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tpActivityChat.setTbRightIv(R.drawable.img_icon_phone, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChatActivity.this, CustomerPhoneActivity.class));
            }
        });
    }


    public String getToChatUsername() {
        return toChatUsername;
    }

    @Override
    public void onBackPressedSupport() {
//        chatFragment.onBackPressed();
//        if (EasyUtils.isSingleActivity(this)) {
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
//        }
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }
}
