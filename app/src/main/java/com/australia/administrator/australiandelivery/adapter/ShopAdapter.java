package com.australia.administrator.australiandelivery.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.australia.administrator.australiandelivery.R;
import com.australia.administrator.australiandelivery.bean.ShopDetailsBean;
import com.australia.administrator.australiandelivery.ui.ShopToDetailListener;

import java.util.List;

/**
 * Created by caobo on 2016/7/20.
 * 购物车适配器
 */
public class ShopAdapter extends BaseAdapter {

    private ShopToDetailListener shopToDetailListener;

    public void setShopToDetailListener(ShopToDetailListener callBackListener) {
        this.shopToDetailListener = callBackListener;
    }
    private List<ShopDetailsBean.MsgBean.ShopmessageBean.GoodsBean> shopProducts;
    private LayoutInflater mInflater;
    public ShopAdapter(Context context, List<ShopDetailsBean.MsgBean.ShopmessageBean.GoodsBean> shopProducts) {
        this.shopProducts = shopProducts;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return shopProducts.size();
    }

    @Override
    public Object getItem(int position) {
        return shopProducts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.trade_widget, null);
            viewHolder = new ViewHolder();
            viewHolder.commodityName = (TextView) convertView.findViewById(R.id.commodityName);
            viewHolder.commodityPrise = (TextView) convertView.findViewById(R.id.commodityPrise);
            viewHolder.increase = (ImageView)  convertView.findViewById(R.id.increase);
            viewHolder.reduce = (ImageView)  convertView.findViewById(R.id.reduce);
            viewHolder.shoppingNum = (TextView)  convertView.findViewById(R.id.shoppingNum);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.commodityName.setText(shopProducts.get(position).getGoodsname());
        viewHolder.commodityPrise.setText(shopProducts.get(position).getGoodsprice());
        viewHolder.shoppingNum.setText(shopProducts.get(position).getNumber()+"");
        viewHolder.increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = shopProducts.get(position).getNumber();
                num++;
                shopProducts.get(position).setNumber(num);
                viewHolder.shoppingNum.setText(shopProducts.get(position).getNumber()+"");
                if (shopToDetailListener != null) {
                    shopToDetailListener.onUpdateDetailList(shopProducts.get(position), "1");
                } else {
                }
            }
        });

        viewHolder.reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = shopProducts.get(position).getNumber();
                if (num > 0) {
                    num--;
                    if(num==0){
                        shopToDetailListener.onRemovePriduct(shopProducts.get(position));
                        notifyDataSetChanged();
                    }else {
                        shopProducts.get(position).setNumber(num);
                        viewHolder.shoppingNum.setText(shopProducts.get(position).getNumber()+"");
                        if (shopToDetailListener != null) {
                            shopToDetailListener.onRemovePriduct(shopProducts.get(position));
                        } else {
                        }
                    }

                }
            }
        });

        return convertView;
    }

    class ViewHolder {
        /**
         * 购物车商品名称
         */
        public TextView commodityName;
        /**
         * 购物车商品价格
         */
        public TextView commodityPrise;
        /**
         * 增加
         */
        public ImageView increase;
        /**
         * 减少
         */
        public ImageView reduce;
        /**
         * 商品数目
         */
        public TextView shoppingNum;
    }
}
