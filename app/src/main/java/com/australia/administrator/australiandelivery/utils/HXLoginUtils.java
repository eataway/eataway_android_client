package com.australia.administrator.australiandelivery.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.australia.administrator.australiandelivery.MyApplication;
import com.australia.administrator.australiandelivery.comm.Login;
import com.australia.administrator.australiandelivery.model.DbOpenHelper;
import com.australia.administrator.australiandelivery.model.DemoDBManager;
import com.australia.administrator.australiandelivery.model.DemoHelper;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import static com.australia.administrator.australiandelivery.utils.UIUtils.runOnUiThread;

/**
 * Created by Administrator on 2017/7/1.
 */

public class HXLoginUtils {
    private static final int sleepTime = 2000;
    public void loginHX(final Context context, String name) {
        Login login = MyApplication.getLogin();
        final String userName = name;
        final String password = name;
        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password)) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        // call method in SDK
                        EMClient.getInstance().createAccount(userName, password);
                    } catch (final HyphenateException e) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                int errorCode = e.getErrorCode();
                                if (errorCode == EMError.NETWORK_ERROR) {
                                    Toast.makeText(context, "网络不可用，请检查网络！", Toast.LENGTH_SHORT).show();
                                } else if (errorCode == EMError.USER_ALREADY_EXIST) {

                                } else if (errorCode == EMError.USER_AUTHENTICATION_FAILED) {
//                                    Toast.makeText(context, "注册失败，未经许可！", Toast.LENGTH_SHORT).show();
                                } else if (errorCode == EMError.USER_ILLEGAL_ARGUMENT) {
//                                    Toast.makeText(context, "非法用户的名字", Toast.LENGTH_SHORT).show();
                                } else {
//                                    Toast.makeText(context, "注册失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }finally {
                        if (DemoHelper.getInstance().isLoggedIn()) {
                            long start = System.currentTimeMillis();
                            EMClient.getInstance().chatManager().loadAllConversations();
                            EMClient.getInstance().groupManager().loadAllGroups();
                            long costTime = System.currentTimeMillis() - start;
                            //wait
                            if (sleepTime - costTime > 0) {
                                try {
                                    Thread.sleep(sleepTime - costTime);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }else {
                            // After logout，the DemoDB may still be accessed due to async callback, so the DemoDB will be re-opened again.
                            // close it before login to make sure DemoDB not overlap
                            DemoDBManager.getInstance().closeDB();
                            // reset current user name before login
                            DemoHelper.getInstance().setCurrentUserName(userName);
                            EMClient.getInstance().login(userName, password, new EMCallBack() {

                                @Override
                                public void onSuccess() {

                                    // ** manually load all local groups and conversation
                                    EMClient.getInstance().groupManager().loadAllGroups();
                                    EMClient.getInstance().chatManager().loadAllConversations();

                                    // update current user's display name for APNs
                                    boolean updatenick = EMClient.getInstance().pushManager().updatePushNickname(
                                            MyApplication.currentUserNick.trim());
                                    if (!updatenick) {
                                        Log.d("login", "update current user nick fail");
                                    }
                                    Log.d("login", "onSuccess: ");
                                    // get user's info (this should be get from App's server or 3rd party service)
                                    DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();
                                }

                                @Override
                                public void onProgress(int progress, String status) {
                                    Log.d("login", "login: onProgress");
                                }

                                @Override
                                public void onError(final int code, final String message) {
                                    Log.d("login", "login: onError");
//                    Toast.makeText(context, context.getResources().getText(R.string.Login_failed) + message,
//                            Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }
            }).start();
        }
    }
}
