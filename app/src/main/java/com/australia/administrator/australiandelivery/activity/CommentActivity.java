package com.australia.administrator.australiandelivery.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.australia.administrator.australiandelivery.MyApplication;
import com.australia.administrator.australiandelivery.R;
import com.australia.administrator.australiandelivery.comm.BaseActivity;
import com.australia.administrator.australiandelivery.utils.Contants;
import com.australia.administrator.australiandelivery.utils.GlideUtils;
import com.australia.administrator.australiandelivery.utils.HttpUtils;
import com.australia.administrator.australiandelivery.utils.ImageLoaderUtils;
import com.australia.administrator.australiandelivery.view.StarBar;
import com.australia.administrator.australiandelivery.view.TopBar;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

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

public class CommentActivity extends BaseActivity {

    @Bind(R.id.tp_activity_comment)
    TopBar tpActivityComment;
    @Bind(R.id.cimg_comment_shop_icon)
    CircleImageView cimgCommentShopIcon;
    @Bind(R.id.tv_comment_shop_name)
    TextView tvCommentShopName;
    @Bind(R.id.v_comment_list_line)
    View vCommentListLine;
    @Bind(R.id.iv_comment_shop_pic)
    ImageView ivCommentShopPic;
    @Bind(R.id.tv_comment_pre)
    TextView tvCommentPre;
    @Bind(R.id.sb_comment)
    StarBar sbComment;
    @Bind(R.id.et_comment_input)
    EditText etCommentInput;
    @Bind(R.id.rl_comment_input)
    RelativeLayout rlCommentInput;
    @Bind(R.id.cb_comment)
    CheckBox cbComment;
    @Bind(R.id.btn_comment_confirm)
    Button btnCommentConfirm;
    @Bind(R.id.iv_comment_file1)
    ImageView ivCommentFile1;
    @Bind(R.id.iv_comment_file2)
    ImageView ivCommentFile2;

    private int Checked = 2;
    private final int FILE1 = 1001;
    private final int FILE2 = 1002;
    private File file1, file2;
    private ImagePicker imagePicker;

    private ArrayList<ImageItem> images;
    private List<File> list;
    private AlertDialog.Builder builder;
    private ProgressBar progressBar;
    private AlertDialog alertDialog;
    private Button alertDialogButton;
    private Intent i;

    private String shopId;
    private String orderId;
    private String shopName;
    private String shopIcon;
    private String shopPic;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_comment;
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        i = getIntent();
        if (i != null) {
            shopId = i.getStringExtra("shopid");
            orderId = i.getStringExtra("orderid");
            shopName = i.getStringExtra("shopname");
            shopIcon = i.getStringExtra("shopicon");
            shopPic = i.getStringExtra("shoppic");
            GlideUtils.load(this, shopIcon, cimgCommentShopIcon, GlideUtils.Shape.ShopIcon);
            GlideUtils.load(this, shopPic, ivCommentShopPic, GlideUtils.Shape.ShopPic);
            tvCommentShopName.setText(shopName);
        }
        initTopBar();
        initImagePicker();
        initDialog();
        initClick();
    }

    private void initTopBar() {
        tpActivityComment.setTbCenterTv(R.string.ping_jia, R.color.white);
        tpActivityComment.setTbLeftIv(R.drawable.img_icon_back_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
        imagePicker.setFocusWidth(MyApplication.width / 2);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(MyApplication.width / 2);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
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
        if (file1 != null || file2 != null) {
            list = new ArrayList<>();
            if (file1 != null) list.add(0, file1);
            if (file2 != null) list.add(1, file2);
            Luban.compress(this, list).launch(new OnMultiCompressListener() {
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
                    ReviewContent(file1, file2);
                }

                @Override
                public void onError(Throwable e) {
                    alertDialog.setMessage(getString(R.string.ping_jia_shi_bai));
                    progressBar.setVisibility(View.GONE);
                    alertDialogButton.setEnabled(true);
                }
            });
        }else {
            alertDialog.show();
            progressBar.setVisibility(View.VISIBLE);
            alertDialog.setMessage("正在上传，请稍后");
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialogButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
            alertDialogButton.setEnabled(false);
            ReviewContent(file1, file2);
        }

    }

    private void ReviewContent(final File file1, final File file2) {
        HttpUtils httpUtils = new HttpUtils(Contants.URL_ADDPINGJIA) {
            @Override
            public void onError(Call call, Exception e, int id) {
                alertDialog.setMessage("网络错误,请检查网络设置");
                progressBar.setVisibility(View.GONE);
                alertDialogButton.setEnabled(true);
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject o = new JSONObject(response);
                    int status = o.getInt("status");
                    if (status == 0) {
                        alertDialog.setMessage(getString(R.string.ping_jia_shi_bai));
                        progressBar.setVisibility(View.GONE);
                    } else if (status == 9) {
                        alertDialog.setMessage(getString(R.string.Please_Log_on_again));
                        progressBar.setVisibility(View.GONE);
                        MyApplication.saveLogin(null);
                        goToActivity(LoginActivity.class);
                        finish();
                    } else if (status == 1) {
                        alertDialog.setMessage(getString(R.string.ping_jia_cheng_gong));
                        progressBar.setVisibility(View.GONE);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                alertDialogButton.setEnabled(true);
            }
        };
        httpUtils.addParam("userid", MyApplication.getLogin().getUserId());
        httpUtils.addParam("shopid", shopId);
        httpUtils.addParam("state", Checked + "");
        httpUtils.addParam("orderid", orderId);
        if (file1 != null) {
            httpUtils.addFile("myFile1", file1.getName(), file1);
        }
        if (file2 != null) {
            httpUtils.addFile("myFile2", file2.getName(), file2);
        }
        httpUtils.addParam("pingjia", sbComment.getStarNums() + "");
        httpUtils.addParam("content", etCommentInput.getText().toString().trim());
        httpUtils.addParam("token", MyApplication.getLogin().getToken());
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null) {
                if (requestCode == FILE1) {
                    images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                    ivCommentFile1.setImageBitmap(BitmapFactory.decodeFile(images.get(0).path));
                    if ((images.get(0).path.toLowerCase().endsWith(".jpg")
                            || images.get(0).path.toLowerCase().endsWith(".jpeg")
                            || images.get(0).path.toLowerCase().endsWith(".png"))) {
                        file1 = new File(images.get(0).path);
                        ivCommentFile2.setVisibility(View.VISIBLE);
                        ivCommentFile2.setBackgroundResource(R.drawable.img_icon_add_gray);
                    } else {
                        Toast.makeText(this, "文件格式不支持", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                    ivCommentFile2.setImageBitmap(BitmapFactory.decodeFile(images.get(images.size() - 1).path));
                    if ((images.get(0).path.toLowerCase().endsWith(".jpg")
                            || images.get(0).path.toLowerCase().endsWith(".jpeg")
                            || images.get(0).path.toLowerCase().endsWith(".png"))) {
                        file2 = new File(images.get(0).path);
                    } else {
                        Toast.makeText(this, "文件格式不支持", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void initClick() {
        btnCommentConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("".equals(etCommentInput.getText().toString().trim())) {
                    Toast.makeText(CommentActivity.this, R.string.ping_jia_bu_neng_wei_kong, Toast.LENGTH_SHORT).show();
                }else{
                    selectHeadImage();
                }
            }
        });
        cbComment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Checked = 1;
                } else {
                    Checked = 2;
                }
            }
        });
    }

    @OnClick({R.id.iv_comment_file1, R.id.iv_comment_file2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_comment_file1:
                i.setClass(this, ImageGridActivity.class);
                startActivityForResult(i,FILE1);
                break;
            case R.id.iv_comment_file2:
                i.setClass(this, ImageGridActivity.class);
                startActivityForResult(i,FILE2);
                break;
        }
    }
}
