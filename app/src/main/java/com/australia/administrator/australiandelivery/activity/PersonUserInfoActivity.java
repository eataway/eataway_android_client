package com.australia.administrator.australiandelivery.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.australia.administrator.australiandelivery.MyApplication;
import com.australia.administrator.australiandelivery.R;
import com.australia.administrator.australiandelivery.comm.BaseActivity;
import com.australia.administrator.australiandelivery.utils.Contants;
import com.australia.administrator.australiandelivery.utils.GlideUtils;
import com.australia.administrator.australiandelivery.utils.HttpUtils;
import com.australia.administrator.australiandelivery.utils.ImageLoaderUtils;
import com.australia.administrator.australiandelivery.view.TopBar;
import com.google.gson.Gson;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnMultiCompressListener;
import okhttp3.Call;

public class PersonUserInfoActivity extends BaseActivity {
    private static final int IMAGE_PICKER = 1000;
    private ImagePicker imagePicker;
    private ArrayList<ImageItem> images;
    private File picture;
    private AlertDialog.Builder builder;
    private ProgressBar progressBar;
    private AlertDialog alertDialog;
    private Button alertDialogButton;

    @Bind(R.id.tp_person_userinfo)
    TopBar tpPersonUserinfo;
    @Bind(R.id.img_userinfo_detail01)
    ImageView imgUserinfoDetail01;
    @Bind(R.id.cimg_person_userinfo_usericon)
    CircleImageView cimgPersonUserinfoUsericon;
    @Bind(R.id.rl_person_userinfo_changeicon)
    RelativeLayout rlPersonUserinfoChangeicon;
    @Bind(R.id.img_userinfo_detail02)
    ImageView imgUserinfoDetail02;
    @Bind(R.id.tv_person_userinfo_username)
    TextView tvPersonUserinfoUsername;
    @Bind(R.id.rl_person_userinfo_changeusername)
    RelativeLayout rlPersonUserinfoChangeusername;
    @Bind(R.id.img_userinfo_detail03)
    ImageView imgUserinfoDetail03;
    @Bind(R.id.tv_person_userinfo_userphone)
    TextView tvPersonUserinfoUserphone;
    @Bind(R.id.rl_person_userinfo_changephone)
    RelativeLayout rlPersonUserinfoChangephone;
    @Bind(R.id.btn_person_userinfo_exit)
    Button btnPersonUserinfoExit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_person_userinfo;
    }

    @Override
    protected void initDate() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        InitTopBar();
        initImagePicker();
        initDialog();
        btnPersonUserinfoExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpUtils httpUtils = new HttpUtils(Contants.URL_USER_LOGOUT) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }
                    @Override
                    public void onResponse(String response, int id) {
                    }
                };
                httpUtils.addParam("userid", MyApplication.getLogin().getUserId());
                MyApplication.saveLogin(null);
                goToActivity(LoginActivity.class);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initUserInfo();
    }

    /**
     * 辅助函数
     */
    private void InitTopBar() {
        tpPersonUserinfo.setBackGround(R.color.actionBar);
        tpPersonUserinfo.setTbCenterTv(R.string.person_userinfo, R.color.white);
        tpPersonUserinfo.setTbLeftIv(R.drawable.img_back_icon_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initUserInfo() {
        tvPersonUserinfoUsername.setText(MyApplication.getLogin().getUserName());
        tvPersonUserinfoUserphone.setText(MyApplication.getLogin().getPhone());
        if (MyApplication.getLogin().getHead()!=null && !"".equals(MyApplication.getLogin().getHead())) {
            GlideUtils.load(this, MyApplication.getLogin().getHead(), cimgPersonUserinfoUsericon, GlideUtils.Shape.UserIcon);
        } else {
            cimgPersonUserinfoUsericon.setBackgroundResource(R.drawable.img_user_icon_default);
        }
    }
    //关于ImagePicker
    private void initImagePicker() {
        imagePicker = ImagePicker.getInstance();
        imagePicker.setMultiMode(false);
        imagePicker.setImageLoader(new ImageLoaderUtils());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(1);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素

        File cropCacheFolder = imagePicker.getCropCacheFolder(this);
        File[] files = cropCacheFolder.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    files[i].delete();
                }
            }
        }
    }

    private void selectHeadImage() {
        List<File> pictures = new ArrayList<>();
        File cropCacheFolder = imagePicker.getCropCacheFolder(this);
        File[] ims = cropCacheFolder.listFiles();
        for (int i = 0; i < ims.length; i++) {
            if (ims[i].isFile() && (ims[i].getName().toLowerCase().endsWith(".jpg")
                    || ims[i].getName().toLowerCase().endsWith(".jpeg")
                    || ims[i].getName().toLowerCase().endsWith(".png"))) {
                pictures.add(ims[i]);
            }
        }
        if (pictures.size() == 0) {
            Toast.makeText(this, "文件格式不支持", Toast.LENGTH_SHORT).show();
            return;
        }
        Luban.compress(this, pictures).launch(new OnMultiCompressListener() {
            @Override
            public void onStart() {
                alertDialog.show();
                progressBar.setVisibility(View.VISIBLE);
                alertDialog.setMessage("正在上传，请稍后");
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialogButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                alertDialogButton.setEnabled(false);
            }

            @Override
            public void onSuccess(List<File> fileList) {
                upHeadImage(fileList.get(fileList.size() - 1));
            }

            @Override
            public void onError(Throwable e) {
                alertDialog.setMessage("上传失败");
                progressBar.setVisibility(View.GONE);
                alertDialogButton.setEnabled(true);
            }
        });

    }

    private void upHeadImage(final File pic) {
        HttpUtils httpUtils = new HttpUtils(Contants.URL_USER_EDITHEAD) {
            @Override
            public void onError(Call call, Exception e, int id) {
                alertDialog.setMessage("网络错误,请检查网络设置");
                progressBar.setVisibility(View.GONE);
                alertDialogButton.setEnabled(true);
            }

            @Override
            public void onResponse(String response, int id) {
                MyBean bean = new Gson().fromJson(response, MyBean.class);
                if (bean.getStatus() == 0) {
                    alertDialog.setMessage("修改失败，请重试");
                    progressBar.setVisibility(View.GONE);
                }
                if (bean.getStatus() == 1) {
                    alertDialog.setMessage("修改成功");
                    progressBar.setVisibility(View.GONE);
                    Glide.with(PersonUserInfoActivity.this).load(pic).crossFade().into(cimgPersonUserinfoUsericon);
                }
                if (bean.getStatus() == 2) {
                    alertDialog.setMessage("没有检测到图片");
                    progressBar.setVisibility(View.GONE);
                }
                if (bean.getStatus() == 9) {
                    alertDialog.setMessage("登录状态无效，请重新登录");
                    progressBar.setVisibility(View.GONE);
                    MyApplication.saveLogin(null);
                }
                alertDialogButton.setEnabled(true);
            }
        };
        httpUtils.addParam("userid", MyApplication.getLogin().getUserId());
        httpUtils.addParam("token", MyApplication.getLogin().getToken());
        httpUtils.addFile("photo", pic.getName(), pic);
        httpUtils.clicent();
    }

    private void initDialog() {
        builder = new AlertDialog.Builder(this);
        progressBar = new ProgressBar(this);
        builder.setTitle("上传到服务器");
        builder.setMessage("正在上传，请稍后");
        builder.setView(progressBar);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog = builder.create();
    }

    /**
     * 辅助类
     */
    @OnClick({R.id.rl_person_userinfo_changeicon, R.id.rl_person_userinfo_changeusername, R.id.rl_person_userinfo_changephone})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_person_userinfo_changeicon:
                Intent userHeadIntent = new Intent(this, ImageGridActivity.class);
                startActivityForResult(userHeadIntent, IMAGE_PICKER);
                break;
            case R.id.rl_person_userinfo_changeusername:
                Intent userNameIntent = new Intent(this, PersonUserinfoNameChangeActivity.class);
                startActivity(userNameIntent);
                break;
            case R.id.rl_person_userinfo_changephone:
               Intent i = new Intent(this, PersonUserinfoPhoneChangeActivity.class);
                i.putExtra("orignalNumber","123456789");
                startActivity(i);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                selectHeadImage();
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }

    class MyBean {


        /**
         * status : 9
         */

        private int status;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
