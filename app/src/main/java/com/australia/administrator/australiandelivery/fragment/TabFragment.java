package com.australia.administrator.australiandelivery.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.australia.administrator.australiandelivery.R;
import com.australia.administrator.australiandelivery.bean.ShopDetailsBean;
import com.australia.administrator.australiandelivery.utils.GlideUtils;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TabFragment extends Fragment {
    public static final String TITLE = "title";
    @Bind(R.id.id_stickynavlayout_innerscrollview)
    RecyclerView idStickynavlayoutInnerscrollview;
    private String mTitle = "Defaut Value";
    // private TextView mTextView;
    private List<ShopDetailsBean.MsgBean.UserspingjiaBean> mDatas = new ArrayList<>();
    public static ShopDetailsBean shopDetailsBean;
    public static String id;
    public static Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        if (shopDetailsBean!=null && shopDetailsBean.getMsg().getUserspingjia().size()>0) {
            view = inflater.inflate(R.layout.fragment_tab, container, false);
            ButterKnife.bind(this, view);
            idStickynavlayoutInnerscrollview.setLayoutManager(new LinearLayoutManager(getActivity()));

            for (int i = 0; i < shopDetailsBean.getMsg().getUserspingjia().size(); i++) {
                mDatas.add(shopDetailsBean.getMsg().getUserspingjia().get(i));
            }
            idStickynavlayoutInnerscrollview.setAdapter(new CommonAdapter<ShopDetailsBean.MsgBean.UserspingjiaBean>(getActivity(), R.layout.item, mDatas) {
                @Override
                public void convert(ViewHolder holder, ShopDetailsBean.MsgBean.UserspingjiaBean userspingjiaBean) {
                    holder.setText(R.id.tv_pingjia_name, userspingjiaBean.getUsername());
                    holder.setText(R.id.tv_pingjia_time, userspingjiaBean.getAddtime());
                    ImageView view1 = holder.getView(R.id.civ_pingjia);
                    GlideUtils.load(context, userspingjiaBean.getHead_photo(), view1, GlideUtils.Shape.UserIcon);
                    holder.setText(R.id.tv_pingjia_details, userspingjiaBean.getContent());
                    RatingBar ratingBar = holder.getView(R.id.rb_pingjia);
                    ratingBar.setRating(Float.parseFloat(userspingjiaBean.getPingjia()));

                    if (userspingjiaBean.getPhoto1()!=null&&!userspingjiaBean.getPhoto1().equals("")){
                        holder.getView(R.id.iv_pingjia1).setVisibility(View.VISIBLE);
                        GlideUtils.load(context, userspingjiaBean.getPhoto1(), (ImageView) holder.getView(R.id.iv_pingjia1), GlideUtils.Shape.UserIcon);
                    }else {
                        holder.getView(R.id.iv_pingjia1).setVisibility(View.GONE);
                    }
                    if (userspingjiaBean.getPhoto2()!=null&&!userspingjiaBean.getPhoto2().equals("")){
                        holder.getView(R.id.iv_pingjia2).setVisibility(View.VISIBLE);
                        GlideUtils.load(context, userspingjiaBean.getPhoto2(), (ImageView) holder.getView(R.id.iv_pingjia2), GlideUtils.Shape.UserIcon);
                    }else {
                        holder.getView(R.id.iv_pingjia2).setVisibility(View.GONE);
                    }
                    if (!userspingjiaBean.getBackpingjia().equals("")){
                        holder.getView(R.id.huifu).setVisibility(View.VISIBLE);
                        holder.setText(R.id.huifu,"回复："+userspingjiaBean.getBackpingjia());
                    }else {
                        holder.getView(R.id.huifu).setVisibility(View.GONE);
                    }
                }
            });
        }else {
            view = inflater.inflate(R.layout.fragment_none, container, false);
//            ButterKnife.bind(this, view);
        }
        return view;
    }

    public static TabFragment newInstance(String id, ShopDetailsBean bean, Context context) {
        TabFragment tabFragment = new TabFragment();
        TabFragment.id = id;
        TabFragment.shopDetailsBean = bean;
        TabFragment.context = context;
        return tabFragment;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
