package com.australia.administrator.australiandelivery.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.australia.administrator.australiandelivery.R;
import com.australia.administrator.australiandelivery.activity.MarkerAcitvity;
import com.australia.administrator.australiandelivery.bean.ShopDetailsBean;
import com.australia.administrator.australiandelivery.utils.ContactUtils;
import com.australia.administrator.australiandelivery.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShopDetailsFragment extends Fragment {

    public static final String TITLE = "title";
    @Bind(R.id.tv_details)
    TextView tvDetails;
    @Bind(R.id.iv_title)
    ImageView ivTitle;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.tv_location)
    TextView tvLocation;
    @Bind(R.id.tv_time)
    TextView tvTime;
    private String mTitle = "Defaut Value";
    private RecyclerView mRecyclerView;
    // private TextView mTextView;
    private List<String> mDatas = new ArrayList<String>();
    public static ShopDetailsBean bean;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        ButterKnife.bind(this, view);
        tvDetails.setText(bean.getMsg().getContent());
        tvTitle.setText(bean.getMsg().getShopname());
        tvPhone.setText(bean.getMsg().getMobile());
        tvTime.setText(bean.getMsg().getGotime());
        tvLocation.setText(bean.getMsg().getDetailed_address());
        GlideUtils.load(getContext(), bean.getMsg().getShophead(), ivTitle, GlideUtils.Shape.ShopPic);
        return view;
    }

    public static ShopDetailsFragment newInstance(String id, ShopDetailsBean bean) {
        ShopDetailsFragment tabFragment = new ShopDetailsFragment();
        ShopDetailsFragment.bean = bean;
        return tabFragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tv_phone, R.id.tv_location})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_phone:
                ContactUtils.ContactUS(getContext(), bean.getMsg().getMobile());
                break;
            case R.id.tv_location:
                String[] split = bean.getMsg().getCoordinate().split(",");
                if (split.length > 1) {
                    Intent i = new Intent(getContext(), MarkerAcitvity.class);
                    i.putExtra("la",split[0]);
                    i.putExtra("lo",split[1]);
                    startActivity(i);
                }

                break;
        }
    }
}
