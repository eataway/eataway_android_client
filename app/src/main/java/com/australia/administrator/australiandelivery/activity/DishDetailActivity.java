package com.australia.administrator.australiandelivery.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.australia.administrator.australiandelivery.R;
import com.australia.administrator.australiandelivery.comm.*;
import com.australia.administrator.australiandelivery.utils.GlideUtils;

import butterknife.Bind;

public class DishDetailActivity extends com.australia.administrator.australiandelivery.comm.BaseActivity {
    @Bind(R.id.iv_dish_detail)
    ImageView ivDishDetail;
    @Bind(R.id.iv_dish_detail_back)
    ImageView ivDishDetailBack;
    @Bind(R.id.tv_dish_detail_dish_name)
    TextView tvDishDetailDishName;
    @Bind(R.id.tv_dish_detail_dish_price)
    TextView tvDishDetailDishPrice;
    @Bind(R.id.tv_dish_detail_dish_content)
    TextView tvDishDetailDishContent;

    private Intent i;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dish_detail;
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        i = getIntent();
        if (i != null) {
            GlideUtils.load(this, i.getStringExtra("dishpic"), ivDishDetail, GlideUtils.Shape.ShopPic);
            tvDishDetailDishName.setText(i.getStringExtra("dishname"));
            tvDishDetailDishContent.setText(i.getStringExtra("dishcontent"));
            tvDishDetailDishPrice.setText("$"+i.getStringExtra("dishprice")+"/"+getString(R.string.fen));
        }
        ivDishDetailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
