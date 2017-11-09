package com.australia.administrator.australiandelivery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.australia.administrator.australiandelivery.R;
import com.australia.administrator.australiandelivery.bean.ShopDetailsBean;
import com.australia.administrator.australiandelivery.utils.GlideUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/8.
 */

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {
    private Context context;
    private List<ShopDetailsBean.MsgBean.ShopmessageBean.GoodsBean> goods;

    public OrdersAdapter(Context context, List<ShopDetailsBean.MsgBean.ShopmessageBean.GoodsBean> goods) {
        this.context = context;
        this.goods = goods;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.orders_item, parent, false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvOrdersName.setText(goods.get(position).getGoodsname());
        holder.tvOrdersNum.setText("×"+goods.get(position).getNumber());
        GlideUtils.load(context,goods.get(position).getGoodsphoto(),holder.ivOrdersItem, GlideUtils.Shape.ShopPic);
        holder.tvOrdersMoney.setText("$"+goods.get(position).getNumber()*Double.parseDouble(goods.get(position).getGoodsprice()));
    }

    @Override
    public int getItemCount() {
        if (goods==null||goods.size()==0){
            return 0;
        }
        return goods.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_orders_item)
        ImageView ivOrdersItem;
        @Bind(R.id.tv_orders_name)
        TextView tvOrdersName;
        @Bind(R.id.tv_orders_num)
        TextView tvOrdersNum;
        @Bind(R.id.tv_orders_money)
        TextView tvOrdersMoney;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
