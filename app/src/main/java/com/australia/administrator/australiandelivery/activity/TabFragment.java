package com.australia.administrator.australiandelivery.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.ajguan.library.EasyRefreshLayout;
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
    @Bind(R.id.er_refresh)
    EasyRefreshLayout erRefresh;
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
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
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
            }
        });
        erRefresh.setEnablePullToRefresh(false);
        erRefresh.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        erRefresh.loadMoreComplete();
                    }
                });
            }

            @Override
            public void onRefreshing() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        erRefresh.refreshComplete();
                    }
                }, 200);
            }
        });
        return view;
    }

    public static TabFragment newInstance(String title, String id, ShopDetailsBean bean, Context context) {
        TabFragment tabFragment = new TabFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        tabFragment.setArguments(bundle);
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
