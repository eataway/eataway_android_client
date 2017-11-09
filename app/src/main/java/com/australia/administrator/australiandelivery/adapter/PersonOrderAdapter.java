package com.australia.administrator.australiandelivery.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.australia.administrator.australiandelivery.MyApplication;
import com.australia.administrator.australiandelivery.R;
import com.australia.administrator.australiandelivery.activity.CommentActivity;
import com.australia.administrator.australiandelivery.activity.GoodsListActivity1;
import com.australia.administrator.australiandelivery.activity.LoginActivity;
import com.australia.administrator.australiandelivery.activity.OrderDetailsActivity;
import com.australia.administrator.australiandelivery.bean.MyOrderBean;
import com.australia.administrator.australiandelivery.utils.ContactUtils;
import com.australia.administrator.australiandelivery.utils.Contants;
import com.australia.administrator.australiandelivery.utils.GlideUtils;
import com.australia.administrator.australiandelivery.utils.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/7/7.
 */

public class PersonOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private View view;
    private Context context;
    private MyOrderBean bean = new MyOrderBean();
    private int type;
    private HttpUtils httpUtils;
    private Intent i = new Intent();

    public PersonOrderAdapter(Context context, MyOrderBean bean) {
        this.context = context;
        this.bean = bean;
    }

    public void addBean(MyOrderBean addBean) {
        for (int i=0;i<addBean.getMsg().size();i++) {
            bean.getMsg().add(addBean.getMsg().get(i));
        }
    }

    public void setBean(MyOrderBean bean) {
        this.bean = bean;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.item_person_user_order, null);
        OrderHoder hoder = new OrderHoder(view);
        hoder.llPersonContainer = (RelativeLayout) view.findViewById(R.id.rl_person_order_container);
        hoder.tvPersonOrderResname = (TextView) view.findViewById(R.id.tv_person_order_resname);
        hoder.tvPersonOrderTotal = (TextView) view.findViewById(R.id.tv_person_order_total);
        hoder.tvPersonOrderOrdertime = (TextView) view.findViewById(R.id.tv_person_order_ordertime);
        hoder.tvPersonOrderContact = (TextView) view.findViewById(R.id.tv_person_order_contact);
        hoder.tvContactPre = (TextView) view.findViewById(R.id.tv_contact_pre);
        hoder.ivPersonOrderResicon = (ImageView) view.findViewById(R.id.iv_person_order_resicon);
        hoder.ivPersonMyorderType = (ImageView) view.findViewById(R.id.iv_person_myorder_type);
        hoder.btnPersonOrderReBuy = (Button) view.findViewById(R.id.btn_person_order_rebuy);
        hoder.tvSeller = (TextView)view.findViewById(R.id.tv_person_order_seller);
        return hoder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        initOrderItem((OrderHoder)holder, position);
    }

    @Override
    public int getItemCount() {
        if (bean ==null||bean.getMsg().size()==0) {
            return 0;
        }
        return bean.getMsg().size();
    }

    private void initOrderItem(OrderHoder hoder, final int position){
        hoder.tvPersonOrderResname.setText(bean.getMsg().get(position).getShopname());
        hoder.tvPersonOrderOrdertime.setText(bean.getMsg().get(position).getAddtime());
        hoder.tvPersonOrderTotal.setText("$"+bean.getMsg().get(position).getAllprice());
        GlideUtils.load(context, bean.getMsg().get(position).getShopphoto(), hoder.ivPersonOrderResicon, GlideUtils.Shape.UserIcon);
        hoder.ivPersonOrderResicon.setScaleType(ImageView.ScaleType.FIT_XY);
        if (bean.getMsg().get(position).getStatu().equals("1")) {
            hoder.ivPersonMyorderType.setVisibility(View.GONE);
            hoder.btnPersonOrderReBuy.setVisibility(View.GONE);
            hoder.tvSeller.setVisibility(View.VISIBLE);
            hoder.btnPersonOrderReBuy.setVisibility(View.GONE);
            hoder.tvSeller.setText(context.getResources().getString(R.string.tui_dan_zhong));
            hoder.tvSeller.setTextColor(context.getResources().getColor(R.color.order_red));
            hoder.tvContactPre.setVisibility(View.GONE);
            hoder.tvPersonOrderContact.setText(context.getResources().getString(R.string.order_contact_url));
            hoder.tvPersonOrderContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ContactUtils.ContactUS(context,bean.getMsg().get(position).getMobile());
                }
            });
        }else if (bean.getMsg().get(position).getStatu().equals("3")) {
            hoder.ivPersonMyorderType.setVisibility(View.GONE);
            hoder.btnPersonOrderReBuy.setVisibility(View.GONE);
            hoder.tvSeller.setVisibility(View.VISIBLE);
            hoder.btnPersonOrderReBuy.setVisibility(View.GONE);
            hoder.tvSeller.setText(context.getResources().getString(R.string.yi_tui_dan));
            hoder.tvSeller.setTextColor(context.getResources().getColor(R.color.order_red));
            hoder.tvContactPre.setVisibility(View.GONE);
            hoder.tvPersonOrderContact.setText(context.getResources().getString(R.string.order_contact_url));
            hoder.tvPersonOrderContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ContactUtils.ContactUS(context,bean.getMsg().get(position).getMobile());
                }
            });
        }else {
            if (MyApplication.isEnglish) {
                hoder.ivPersonMyorderType.setVisibility(View.GONE);
            }else {
                hoder.ivPersonMyorderType.setVisibility(View.VISIBLE);
            }
            type = Integer.parseInt(bean.getMsg().get(position).getState())-1;
            setResType(hoder, type);
            setClick(hoder, type, position);
        }
    }

    private void setResType(OrderHoder hoder, int type) {
        switch (type) {
            case 0: //未接单
                hoder.ivPersonMyorderType.setBackgroundResource(R.drawable.img_order_type_wait);
                hoder.btnPersonOrderReBuy.setVisibility(View.GONE);
                hoder.tvSeller.setVisibility(View.VISIBLE);
                hoder.btnPersonOrderReBuy.setVisibility(View.GONE);
                hoder.tvSeller.setText(context.getResources().getString(R.string.order_waiting_seller));
                hoder.tvSeller.setTextColor(context.getResources().getColor(R.color.order_red));
                hoder.tvContactPre.setVisibility(View.VISIBLE);
                hoder.tvPersonOrderContact.setText(context.getResources().getString(R.string.order_contact_url));
               break;
            case 1: //已接单
                hoder.ivPersonMyorderType.setBackgroundResource(R.drawable.img_order_type_receive);
                hoder.btnPersonOrderReBuy.setVisibility(View.GONE);
                hoder.tvSeller.setVisibility(View.VISIBLE);
                hoder.tvSeller.setText(context.getResources().getString(R.string.order_seller_order));
                hoder.tvSeller.setTextColor(context.getResources().getColor(R.color.order_orange));
                hoder.tvContactPre.setVisibility(View.VISIBLE);
                hoder.tvPersonOrderContact.setText(context.getResources().getString(R.string.order_contact_url));
                break;
            case 2: //待确认
                hoder.ivPersonMyorderType.setBackgroundResource(R.drawable.img_order_type_service);
                hoder.tvSeller.setVisibility(View.GONE);
                hoder.btnPersonOrderReBuy.setVisibility(View.VISIBLE);
                hoder.btnPersonOrderReBuy.setText(context.getResources().getString(R.string.order_receipt));
                hoder.btnPersonOrderReBuy.setBackgroundResource(R.drawable.shape_orange);
                hoder.btnPersonOrderReBuy.setTextColor(context.getResources().getColor(R.color.white));
                hoder.tvContactPre.setVisibility(View.GONE);
                hoder.tvPersonOrderContact.setText(context.getResources().getString(R.string.order_reviews));
                hoder.tvPersonOrderContact.setTextColor(context.getResources().getColor(R.color.btn_green_noraml));
                break;
            case 3: //已完成
                hoder.ivPersonMyorderType.setBackgroundResource(R.drawable.img_order_type_ok);
                hoder.tvSeller.setVisibility(View.GONE);
                hoder.btnPersonOrderReBuy.setVisibility(View.VISIBLE);
                hoder.btnPersonOrderReBuy.setText(context.getResources().getString(R.string.order_buyagain));
                hoder.btnPersonOrderReBuy.setTextColor(context.getResources().getColor(R.color.orange));
                hoder.btnPersonOrderReBuy.setBackgroundResource(R.drawable.shape_person_order_buyagain);
                hoder.btnPersonOrderReBuy.setTextColor(context.getResources().getColor(R.color.benefit_style));
                hoder.tvContactPre.setVisibility(View.GONE);
                hoder.tvPersonOrderContact.setText(context.getResources().getString(R.string.order_reviews));
                hoder.tvPersonOrderContact.setTextColor(context.getResources().getColor(R.color.btn_green_noraml));
                break;
            case 4: //已评价
                hoder.ivPersonMyorderType.setBackgroundResource(R.drawable.img_order_type_ok);
                hoder.tvSeller.setVisibility(View.GONE);
                hoder.btnPersonOrderReBuy.setVisibility(View.VISIBLE);
                hoder.btnPersonOrderReBuy.setText(context.getResources().getString(R.string.order_buyagain));
                hoder.btnPersonOrderReBuy.setBackgroundResource(R.drawable.shape_person_order_buyagain);
                hoder.btnPersonOrderReBuy.setTextColor(context.getResources().getColor(R.color.benefit_style));
                hoder.tvContactPre.setVisibility(View.GONE);
                hoder.tvPersonOrderContact.setText(context.getResources().getString(R.string.order_myreviews));
                hoder.tvPersonOrderContact.setClickable(false);
                hoder.tvPersonOrderContact.setTextColor(context.getResources().getColor(R.color.gray_pressed));
                break;
            default:
                break;
        }
    }

    private void setClick(OrderHoder hoder, int type, final int position) {
        hoder.llPersonContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"ll_person_order_container",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, OrderDetailsActivity.class);
                intent.putExtra("orderid",bean.getMsg().get(position).getOrderid());
                context.startActivity(intent);
            }
        });
        switch (type) {
            case 0: //未接单
            case 1: //已接单
                hoder.tvPersonOrderContact.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ContactUtils.ContactUS(context,bean.getMsg().get(position).getMobile());
                    }
                });
                break;
            case 2: //待确认
                hoder.btnPersonOrderReBuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        httpUtils = new HttpUtils(Contants.URL_QUEREN) {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                try {
                                    JSONObject o = new JSONObject(response);
                                    int status = o.getInt("status");
                                    if (status == 1) {
                                        bean.getMsg().get(position).setState("4");
                                        notifyDataSetChanged();
                                    }else if (status == 0) {
                                        Toast.makeText(context, R.string.qing_qiu_shi_bai, Toast.LENGTH_SHORT).show();
                                    }else if (status == 9) {
                                        Toast.makeText(context, R.string.Please_Log_on_again, Toast.LENGTH_SHORT).show();
                                        MyApplication.saveLogin(null);
                                        i.setClass(context, LoginActivity.class);
                                        context.startActivity(i);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        httpUtils.addParam("userid", MyApplication.getLogin().getUserId());
                        httpUtils.addParam("orderid", bean.getMsg().get(position).getOrderid());
                        httpUtils.addParam("token", MyApplication.getLogin().getToken());
                        httpUtils.clicent();
                    }
                });
                hoder.tvPersonOrderContact.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Toast.makeText(context,"去评价页面",Toast.LENGTH_SHORT).show();
                        i.setClass(context, CommentActivity.class);
                        i.putExtra("shopid", bean.getMsg().get(position).getShopid());
                        i.putExtra("orderid", bean.getMsg().get(position).getOrderid());
                        i.putExtra("shopname", bean.getMsg().get(position).getShopname());
                        i.putExtra("shopicon", bean.getMsg().get(position).getShophead());
                        i.putExtra("shoppic", bean.getMsg().get(position).getShopphoto());
                        context.startActivity(i);
                    }
                });
                break;
            case 3: //已完成
                hoder.btnPersonOrderReBuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Toast.makeText(context,"去商户页面",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, GoodsListActivity1.class);
                        intent.putExtra("id", bean.getMsg().get(position).getShopid());
                        context.startActivity(intent);
                    }
                });
                hoder.tvPersonOrderContact.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Toast.makeText(context,"去评价页面",Toast.LENGTH_SHORT).show();
                        i.setClass(context, CommentActivity.class);
                        i.putExtra("shopid", bean.getMsg().get(position).getShopid());
                        i.putExtra("orderid", bean.getMsg().get(position).getOrderid());
                        i.putExtra("shopname", bean.getMsg().get(position).getShopname());
                        i.putExtra("shopicon", bean.getMsg().get(position).getShophead());
                        i.putExtra("shoppic", bean.getMsg().get(position).getShopphoto());
                        context.startActivity(i);
                    }
                });
                break;
            case 4: //已评价
                hoder.btnPersonOrderReBuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Toast.makeText(context,"去商户页面",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, GoodsListActivity1.class);
                        intent.putExtra("id", bean.getMsg().get(position).getShopid());
                        context.startActivity(intent);
                    }
                });
                break;
            default:
                break;        }
    }
    /**
     * Holder
     */
    class OrderHoder extends RecyclerView.ViewHolder {
        public OrderHoder(View itemView) {
            super(itemView);
        }
        RelativeLayout llPersonContainer;
        ImageView ivPersonOrderResicon;
        TextView tvPersonOrderResname;
        TextView tvPersonOrderOrdertime;
        TextView tvPersonOrderTotal;
        TextView tvPersonOrderContact;
        TextView tvContactPre;
        TextView tvSeller;
        ImageView ivPersonMyorderType;
        Button btnPersonOrderReBuy;
    }

    /**
     * 辅助类
     */
}
