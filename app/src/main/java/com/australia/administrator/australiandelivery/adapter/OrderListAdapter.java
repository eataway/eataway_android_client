package com.australia.administrator.australiandelivery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.australia.administrator.australiandelivery.R;
import com.australia.administrator.australiandelivery.bean.OrderBean;
import com.australia.administrator.australiandelivery.utils.GlideUtils;

/**
 * Created by Administrator on 2017/7/10.
 */

public class OrderListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private OrderBean bean;

    public OrderListAdapter(Context context) {
        this.context = context;
    }

    public OrderListAdapter(Context context, OrderBean bean) {
        this.context = context;
        this.bean = bean;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_order_details_middle_order, null);
        OrderHolder holder = new OrderHolder(view);
        holder.itemOrderDetailsMiddleOrderFoodname = (TextView) view.findViewById(R.id.item_order_details_middle_order_foodname);
        holder.ivItemOrderDetailsMiddleOrderFoodimg = (ImageView) view.findViewById(R.id.iv_item_order_details_middle_order_foodimg);
        holder.itemOrderDetailsMiddleOrderFoodnum = (TextView) view.findViewById(R.id.item_order_details_middle_order_foodnum);
        holder.itemOrderDetailsMiddleOrderFoodfee = (TextView) view.findViewById(R.id.item_order_details_middle_order_foodfee);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        initOrderList((OrderHolder)holder, position);
    }

    @Override
    public int getItemCount() {
        if (bean.getMsg().getGoods() != null)
            return bean.getMsg().getGoods().size();
        return 0;
    }

    private void initOrderList(OrderHolder holder, int position) {
        holder.itemOrderDetailsMiddleOrderFoodnum.setText("× "+bean.getMsg().getGoods().get(position).getNum());
        holder.itemOrderDetailsMiddleOrderFoodname.setText(bean.getMsg().getGoods().get(position).getGoodsname());
        holder.itemOrderDetailsMiddleOrderFoodfee.setText("$"+bean.getMsg().getGoods().get(position).getGoodsprice());
        GlideUtils.load(context, bean.getMsg().getGoods().get(position).getGoodsphoto(), holder.ivItemOrderDetailsMiddleOrderFoodimg, GlideUtils.Shape.ShopPic);
    }

    /**
     * holders
     */
    class OrderHolder extends RecyclerView.ViewHolder {
        public OrderHolder(View itemView) {
            super(itemView);
        }
        ImageView ivItemOrderDetailsMiddleOrderFoodimg;
        TextView itemOrderDetailsMiddleOrderFoodname;
        TextView itemOrderDetailsMiddleOrderFoodfee;
        TextView itemOrderDetailsMiddleOrderFoodnum;
    }
}
