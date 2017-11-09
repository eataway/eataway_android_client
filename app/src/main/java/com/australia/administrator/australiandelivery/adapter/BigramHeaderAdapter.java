package com.australia.administrator.australiandelivery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.australia.administrator.australiandelivery.R;
import com.australia.administrator.australiandelivery.bean.ShopDetailsBean;
import com.australia.library.StickyHeadersAdapter;

import java.util.List;


public class BigramHeaderAdapter implements StickyHeadersAdapter<BigramHeaderAdapter.ViewHolder> {

    private  Context mContext;
    private List<ShopDetailsBean.MsgBean.ShopmessageBean.GoodsBean> dataList;
    private List<ShopDetailsBean.MsgBean.ShopmessageBean> goodscatrgoryEntities;
    public BigramHeaderAdapter(Context context, List<ShopDetailsBean.MsgBean.ShopmessageBean.GoodsBean> items, List<ShopDetailsBean.MsgBean.ShopmessageBean> goodscatrgoryEntities) {
        this.mContext = context;
        this.dataList = items;
        this.goodscatrgoryEntities = goodscatrgoryEntities;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_goods_list, parent, false);
        return new BigramHeaderAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BigramHeaderAdapter.ViewHolder headerViewHolder, int position) {
        headerViewHolder.tvGoodsItemTitle.setText(goodscatrgoryEntities.get(dataList.get(position).getId()).getCname());
    }

    @Override
    public long getHeaderId(int position) {
        return dataList.get(position).getId();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvGoodsItemTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            tvGoodsItemTitle = (TextView) itemView.findViewById(R.id.tvGoodsItemTitle);
        }
    }
}
