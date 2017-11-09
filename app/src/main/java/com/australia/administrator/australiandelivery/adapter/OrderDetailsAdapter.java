package com.australia.administrator.australiandelivery.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.australia.administrator.australiandelivery.MyApplication;
import com.australia.administrator.australiandelivery.R;
import com.australia.administrator.australiandelivery.bean.OrderBean;


/**
 * Created by Administrator on 2017/7/10.
 */

public class OrderDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private OrderListAdapter adapter;
    private OrderBean bean;

    public OrderDetailsAdapter(Context context) {
        this.context = context;
    }

    public OrderDetailsAdapter(Context context, OrderBean bean) {
        this.context = context;
        this.bean = bean;
    }

    public OrderBean getBean() {
        return bean;
    }

    public void setBean(OrderBean bean) {
        this.bean = bean;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) {
            view = LayoutInflater.from(context).inflate(R.layout.item_order_details_top, null);
            TopHolder holder = new TopHolder(view);
            holder.tvOrderDetailsTopType = (TextView) view.findViewById(R.id.tv_order_details_top_type);
            holder.tvOrderDetailsTopTime = (TextView) view.findViewById(R.id.tv_order_details_top_time);
            return holder;
        } else if (viewType == 1) {
            view = LayoutInflater.from(context).inflate(R.layout.item_order_details_middle, null);
            MiddleHolder holder = new MiddleHolder(view);
            holder.rvItemOrderDetailsMiddleOrder = (RecyclerView) view.findViewById(R.id.rv_item_order_details_middle_order);
            holder.tvItemOrderDetailsMiddleDeliveryFee = (TextView) view.findViewById(R.id.tv_item_order_details_middle_delivery_fee);
            holder.tvItemOrderDetailsMiddleDistance = (TextView) view.findViewById(R.id.tv_item_order_details_middle_distance);
            holder.tvItemOrderDetailsMiddleTotal = (TextView) view.findViewById(R.id.tv_item_order_details_middle_total);
            holder.ivOrderDetailsMiddleType = (ImageView) view.findViewById(R.id.iv_order_details_middle_type);
            return holder;
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_order_details_bottom, null);
            BottomHolder holder = new BottomHolder(view);
            holder.tvItemOrderDetailsBottomDeliveryTime = (TextView) view.findViewById(R.id.tv_item_order_details_bottom_delivery_time);
            holder.tvItemOrderDetailsBottomOrderNumber = (TextView) view.findViewById(R.id.tv_item_order_details_bottom_order_number);
            holder.tvItemOrderDetailsBottomDeliveryAddress = (TextView) view.findViewById(R.id.tv_item_order_details_bottom_delivery_address);
            holder.tvItemOrderDetailsBottomRemarks = (TextView) view.findViewById(R.id.tv_item_order_details_bottom_remarks);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
            initTop((TopHolder) holder, position);
        }else if (position == 1) {
            initMiddle((MiddleHolder)holder, position);
        }else if (position ==2) {
            initBottom((BottomHolder)holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    /**
     * 辅助函数
     */
    private void initTop(TopHolder holder, int position) {
        holder.tvOrderDetailsTopTime.setText(bean.getMsg().getAddtime());
        if (bean.getMsg().getStatu().equals("1")) {
            holder.tvOrderDetailsTopType.setText(context.getResources().getString(R.string.tui_dan_zhong));
        }else if (bean.getMsg().getStatu().equals("3")) {
            holder.tvOrderDetailsTopType.setText(context.getResources().getString(R.string.yi_tui_dan));
        }else {
            if (bean.getMsg().getState().equals("1")) { //未接单
                holder.tvOrderDetailsTopType.setText(context.getResources().getString(R.string.order_waiting_seller));
            }else if (bean.getMsg().getState().equals("2")) {   //已接单
                holder.tvOrderDetailsTopType.setText(context.getResources().getString(R.string.order_seller_order));
            }else if (bean.getMsg().getState().equals("3")) {   //待确认
                holder.tvOrderDetailsTopType.setText(context.getResources().getString(R.string.order_seller_delivered));
            }else if (bean.getMsg().getState().equals("4")) {   //待评价
                holder.tvOrderDetailsTopType.setText(context.getResources().getString(R.string.order_finish));
            }else if (bean.getMsg().getState().equals("5")) {   //已评价
                holder.tvOrderDetailsTopType.setText(context.getResources().getString(R.string.order_finish));
            }
        }
    }

    private void initMiddle(MiddleHolder holder, int position) {
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        holder.rvItemOrderDetailsMiddleOrder.setLayoutManager(manager);
        adapter = new OrderListAdapter(context, bean);
        holder.rvItemOrderDetailsMiddleOrder.setAdapter(adapter);
        holder.tvItemOrderDetailsMiddleDeliveryFee.setText("$"+bean.getMsg().getMoney());
        holder.tvItemOrderDetailsMiddleTotal.setText("$" + bean.getMsg().getAllprice());
        holder.tvItemOrderDetailsMiddleDistance.setText("("+context.getResources().getString(R.string.order_distance)+bean.getMsg().getJuli()+"km)");
        if (bean.getMsg().getStatu().equals("1")) {
            holder.ivOrderDetailsMiddleType.setVisibility(View.GONE);
        }else if (bean.getMsg().getStatu().equals("3")) {
            holder.ivOrderDetailsMiddleType.setVisibility(View.GONE);
        }else {
            if (MyApplication.isEnglish) {
                holder.ivOrderDetailsMiddleType.setVisibility(View.GONE);
            }else {
                holder.ivOrderDetailsMiddleType.setVisibility(View.VISIBLE);
                if (bean.getMsg().getState().equals("1")) { //未接单
                    holder.ivOrderDetailsMiddleType.setBackgroundResource(R.drawable.img_order_type_wait);
                }else if (bean.getMsg().getState().equals("2")) {   //已接单
                    holder.ivOrderDetailsMiddleType.setBackgroundResource(R.drawable.img_order_type_receive);
                }else if (bean.getMsg().getState().equals("3")) {   //待确认
                    holder.ivOrderDetailsMiddleType.setBackgroundResource(R.drawable.img_order_type_service);
                }else if (bean.getMsg().getState().equals("4")) {   //待评价
                    holder.ivOrderDetailsMiddleType.setBackgroundResource(R.drawable.img_order_type_ok);
                }else if (bean.getMsg().getState().equals("5")) {   //已评价
                    holder.ivOrderDetailsMiddleType.setBackgroundResource(R.drawable.img_order_type_ok);
                }
            }
        }
    }

    private void initBottom(BottomHolder holder, int position) {
        holder.tvItemOrderDetailsBottomDeliveryTime.setText(bean.getMsg().getCometime());
        holder.tvItemOrderDetailsBottomOrderNumber.setText(bean.getMsg().getOrderid());
        holder.tvItemOrderDetailsBottomDeliveryAddress.setText(bean.getMsg().getAddress());
        holder.tvItemOrderDetailsBottomRemarks.setText(bean.getMsg().getBeizhu());
    }


    /**
     * holders
     */
    class TopHolder extends RecyclerView.ViewHolder {
        public TopHolder(View itemView) {
            super(itemView);
        }
        TextView tvOrderDetailsTopType;
        TextView tvOrderDetailsTopTime;
    }

    class MiddleHolder extends RecyclerView.ViewHolder {
        public MiddleHolder(View itemView) {
            super(itemView);
        }
        RecyclerView rvItemOrderDetailsMiddleOrder;
        TextView tvItemOrderDetailsMiddleDeliveryFee;
        TextView tvItemOrderDetailsMiddleTotal;
        ImageView ivOrderDetailsMiddleType;
        TextView tvItemOrderDetailsMiddleDistance;
    }

    class BottomHolder extends RecyclerView.ViewHolder {
        public BottomHolder(View itemView) {
            super(itemView);
        }
        TextView tvItemOrderDetailsBottomDeliveryTime;
        TextView tvItemOrderDetailsBottomOrderNumber;
        TextView tvItemOrderDetailsBottomDeliveryAddress;
        TextView tvItemOrderDetailsBottomRemarks;
    }
}
