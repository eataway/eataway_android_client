package com.australia.administrator.australiandelivery.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.australia.administrator.australiandelivery.MyApplication;
import com.australia.administrator.australiandelivery.R;
import com.australia.administrator.australiandelivery.activity.CurrentOrderActivity;
import com.australia.administrator.australiandelivery.activity.LoginActivity;
import com.australia.administrator.australiandelivery.bean.CurrentOrderBean;
import com.australia.administrator.australiandelivery.bean.CurrentOrderInterAdapter;
import com.australia.administrator.australiandelivery.bean.MyOrder;
import com.australia.administrator.australiandelivery.comm.Login;
import com.australia.administrator.australiandelivery.utils.ContactUtils;
import com.australia.administrator.australiandelivery.utils.Contants;
import com.australia.administrator.australiandelivery.utils.GlideUtils;
import com.australia.administrator.australiandelivery.utils.GoosInfoAdapterUtils;
import com.australia.administrator.australiandelivery.utils.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/7/28.
 */

public class CurrentOrderAdapter extends RecyclerView.Adapter<CurrentOrderAdapter.ViewHolder> {
    private Context context;
    private CurrentOrderBean bean;

    public CurrentOrderAdapter(Context context, CurrentOrderBean bean) {
        this.context = context;
        this.bean = bean;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recylerview_current_order, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        initOrderList(holder, position);
    }

    public void setBean(CurrentOrderBean bean) {
        this.bean = bean;
    }


    private void initOrderList(final ViewHolder holder, final int position) {
        LinearLayoutManager manager = new LinearLayoutManager(context);
        GoosInfoAdapterUtils.InfoCrop(bean.getMsg().get(position).getGoodsinfo(),position);
        List<MyOrder> list = GoosInfoAdapterUtils.getList();
        Log.i("mylist", "initOrderList: "+list.size());
        CurrentOrderInterAdapter adapter = new CurrentOrderInterAdapter(context, list);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        GlideUtils.load(context, bean.getMsg().get(position).getShophead(), holder.cimgCurrentOrderShopIcon, GlideUtils.Shape.ShopIcon);
        holder.tvCurrentOrderShopName.setText(bean.getMsg().get(position).getShopname());
        holder.rvItemCurrentOrderMiddleOrder.setLayoutManager(manager);
        holder.rvItemCurrentOrderMiddleOrder.setAdapter(adapter);
        holder.tvItemCurrentOrderMiddleDeliveryFee.setText("$" + bean.getMsg().get(position).getMoney());
        holder.tvItemCurrentOrderMiddleDistance.setText("(" + context.getString(R.string.order_distance) + bean.getMsg().get(position).getJuli() + "km）");
        holder.tvItemCurrentOrderMiddleTotal.setText("$" + bean.getMsg().get(position).getAllprice());
        holder.tvCurrentContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactUtils.ContactUS(context, bean.getMsg().get(position).getPhone());
            }
        });
        holder.btnCurrentOrderRebuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpUtils httpUtils = new HttpUtils(Contants.URL_QUEREN) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject o = new JSONObject(response);
                            int status = o.getInt("status");
                            if (status == 0) {
                                Toast.makeText(context, R.string.xiu_gai_shi_bai, Toast.LENGTH_SHORT).show();
                            } else if (status == 1) {
                                Toast.makeText(context, R.string.qing_qiu_cheng_gong, Toast.LENGTH_SHORT).show();
                                bean.getMsg().remove(position);
                                notifyDataSetChanged();
                            } else if (status == 9) {
                                Toast.makeText(context, R.string.Please_Log_on_again, Toast.LENGTH_SHORT).show();
                                MyApplication.saveLogin(null);
                                Intent i = new Intent(context, LoginActivity.class);
                                context.startActivity(i);
                                ((CurrentOrderActivity) context).finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                Login login = MyApplication.getLogin();
                if (login != null) {
                    httpUtils.addParam("orderid", bean.getMsg().get(position).getOrderid());
                    httpUtils.addParam("userid", login.getUserId());
                    httpUtils.addParam("token", login.getToken());
                    httpUtils.clicent();
                }
            }
        });
        if (MyApplication.isEnglish) {
            holder.ivCurrentOrderMiddleType.setVisibility(View.GONE);
        }else {
            holder.ivCurrentOrderMiddleType.setVisibility(View.VISIBLE);
        }
        if ("1".equals(bean.getMsg().get(position).getStatu())) {
            holder.tvCurrentOrderType.setText(context.getResources().getString(R.string.tui_dan_zhong));
        }else if ("3".equals(bean.getMsg().get(position).getStatu())) {
            holder.tvCurrentOrderType.setText(context.getResources().getString(R.string.yi_tui_dan));
        }else {
            if (bean.getMsg().get(position).getState().equals("1")) { //未接单
                holder.btnCurrentOrderRebuy.setVisibility(View.GONE);
                holder.tvCurrentOrderType.setText(context.getString(R.string.order_waiting_seller));
                holder.ivCurrentOrderMiddleType.setBackgroundResource(R.drawable.img_order_type_wait);
            } else if (bean.getMsg().get(position).getState().equals("2")) {   //已接单
                holder.btnCurrentOrderRebuy.setVisibility(View.GONE);
                holder.tvCurrentOrderType.setText(context.getString(R.string.order_seller_order));
                holder.ivCurrentOrderMiddleType.setBackgroundResource(R.drawable.img_order_type_receive);
            } else if (bean.getMsg().get(position).getState().equals("3")) {   //待确认
                holder.tvCurrentOrderType.setText(context.getString(R.string.order_seller_delivered));
                holder.ivCurrentOrderMiddleType.setBackgroundResource(R.drawable.img_order_type_service);
                holder.btnCurrentOrderRebuy.setVisibility(View.VISIBLE);
                holder.tvCurrentContactUs.setVisibility(View.GONE);
                holder.tvCurrentContactUsPre.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (bean==null || bean.getMsg().size()==0) {
            ((CurrentOrderActivity)context).tvCurrentOrderNone.setVisibility(View.VISIBLE);
            ((CurrentOrderActivity)context).rvCurrentOrder.setVisibility(View.GONE);
            return 0;
        } else {
            return bean.getMsg().size();
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.rv_item_current_order_middle_order)
        RecyclerView rvItemCurrentOrderMiddleOrder;
        @Bind(R.id.tv_item_current_order_middle_delivery_fee)
        TextView tvItemCurrentOrderMiddleDeliveryFee;
        @Bind(R.id.tv_item_current_order_middle_distance)
        TextView tvItemCurrentOrderMiddleDistance;
        @Bind(R.id.tv_item_current_order_middle_total)
        TextView tvItemCurrentOrderMiddleTotal;
        @Bind(R.id.tv_current_contact_us)
        TextView tvCurrentContactUs;
        @Bind(R.id.iv_current_order_middle_type)
        ImageView ivCurrentOrderMiddleType;
        @Bind(R.id.tv_current_order_type)
        TextView tvCurrentOrderType;
        @Bind(R.id.btn_current_order_rebuy)
        Button btnCurrentOrderRebuy;
        @Bind(R.id.tv_current_contact_us_pre)
        TextView tvCurrentContactUsPre;
        @Bind(R.id.cimg_current_order_shop_icon)
        CircleImageView cimgCurrentOrderShopIcon;
        @Bind(R.id.tv_current_order_shop_name)
        TextView tvCurrentOrderShopName;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
