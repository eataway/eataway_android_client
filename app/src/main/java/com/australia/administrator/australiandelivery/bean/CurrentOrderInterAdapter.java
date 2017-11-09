package com.australia.administrator.australiandelivery.bean;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.australia.administrator.australiandelivery.R;
import com.australia.administrator.australiandelivery.utils.GlideUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/29.
 */

public class CurrentOrderInterAdapter extends RecyclerView.Adapter<CurrentOrderInterAdapter.ViewHolder> {
    private Context context;
    private List<MyOrder> list;
    private int pos;

    public CurrentOrderInterAdapter(Context context, List<MyOrder> bean) {
        this.context = context;
        this.list = bean;
    }

    @Override
    public CurrentOrderInterAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_details_middle_order, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CurrentOrderInterAdapter.ViewHolder holder, int position) {
//        holder.itemOrderDetailsMiddleOrderFoodname.setText(GoosInfoAdapterUtils.getInfoName(position));
//        holder.itemOrderDetailsMiddleOrderFoodfee.setText("$" + GoosInfoAdapterUtils.getInfoPrice(position));
//        holder.itemOrderDetailsMiddleOrderFoodnum.setText("×"+ GoosInfoAdapterUtils.getInfoNun(position));
//        GlideUtils.load(context,GoosInfoAdapterUtils.getInfoPic(position), holder.ivItemOrderDetailsMiddleOrderFoodimg, GlideUtils.Shape.ShopPic);
        Log.i("mylist", "onBindViewHolder: ");
        holder.itemOrderDetailsMiddleOrderFoodname.setText(list.get(position).getGoodsName());
        holder.itemOrderDetailsMiddleOrderFoodnum.setText("×"+ list.get(position).getGoodsNum());
        holder.itemOrderDetailsMiddleOrderFoodfee.setText("$" + list.get(position).getGoodsPrice());
        GlideUtils.load(context, list.get(position).getGoodsPic(), holder.ivItemOrderDetailsMiddleOrderFoodimg, GlideUtils.Shape.ShopPic);
    }

    @Override
    public int getItemCount() {
        if (list != null && list.size() != 0){
            Log.i("mylist", "getItemCount: "+list.size());
            return list.size();
        }
        else
            return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.v_item_order_details_middle_order_line)
        View vItemOrderDetailsMiddleOrderLine;
        @Bind(R.id.iv_item_order_details_middle_order_foodimg)
        ImageView ivItemOrderDetailsMiddleOrderFoodimg;
        @Bind(R.id.item_order_details_middle_order_foodname)
        TextView itemOrderDetailsMiddleOrderFoodname;
        @Bind(R.id.item_order_details_middle_order_foodnum)
        TextView itemOrderDetailsMiddleOrderFoodnum;
        @Bind(R.id.item_order_details_middle_order_foodfee)
        TextView itemOrderDetailsMiddleOrderFoodfee;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
