package com.australia.administrator.australiandelivery.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.australia.administrator.australiandelivery.MyApplication;
import com.australia.administrator.australiandelivery.R;
import com.australia.administrator.australiandelivery.activity.AddressActivity;
import com.australia.administrator.australiandelivery.activity.AddressReleaseActiviyty;
import com.australia.administrator.australiandelivery.activity.LoginActivity;
import com.australia.administrator.australiandelivery.bean.AddressBean1;
import com.australia.administrator.australiandelivery.comm.Login;
import com.australia.administrator.australiandelivery.utils.Contants;
import com.australia.administrator.australiandelivery.utils.HttpUtils;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/7/11.
 */

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.ViewHolder> {
    private Context context;
    private AddressBean1 bean;
    private String type;
    private Activity activity;

    public AddressListAdapter(Context context, AddressBean1 bean, String type, Activity activity) {
        this.context = context;
        this.bean = bean;
        this.type = type;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.address_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvSubmitPhone.setText(bean.getMsg().get(position).getMobile());
        if (MyApplication.isEnglish) {
            holder.tvSubmitSex.setText(bean.getMsg().get(position).getReal_name());
            if ("1".equals(bean.getMsg().get(position).getGender())) {
                holder.tvSubmitName.setText("Mr");
            }else {
                holder.tvSubmitName.setText("Miss");
            }
        }else {
            holder.tvSubmitName.setText(bean.getMsg().get(position).getReal_name());
            if ("1".equals(bean.getMsg().get(position).getGender())) {
                holder.tvSubmitSex.setText("先生");
            }else {
                holder.tvSubmitSex.setText("女士");
            }
        }

        holder.tvWeizhi.setText(bean.getMsg().get(position).getDetailed_address() +" " + bean.getMsg().get(position).getLocation_address());
        holder.llAddressList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("name", bean.getMsg().get(position).getReal_name());
                intent.putExtra("phone", bean.getMsg().get(position).getMobile());
                if ("1".equals(bean.getMsg().get(position).getGender())) {
                    if(MyApplication.isEnglish) intent.putExtra("sex","Mr");
                    else intent.putExtra("sex", "先生");
                }else {
                    if(MyApplication.isEnglish) intent.putExtra("sex","Miss");
                    else intent.putExtra("sex", "女士");
                }
                intent.putExtra("weizhi",bean.getMsg().get(position).getDetailed_address() + " " +bean.getMsg().get(position).getLocation_address());
                intent.putExtra("weizhiname", bean.getMsg().get(position).getLocation_address() );
                intent.putExtra("weizhixiangxi",bean.getMsg().get(position).getDetailed_address());
                intent.putExtra("id", bean.getMsg().get(position).getAddid());
                intent.putExtra("coordinate", bean.getMsg().get(position).getCoordinate());
                intent.putExtra("addid", bean.getMsg().get(position).getAddid());
                if ("1".equals(type)) {
                    intent.setClass(context, AddressReleaseActiviyty.class);
                    context.startActivity(intent);
                } else if ("2".equals(type)) {
                    activity.setResult(3, intent);
                    activity.finish();
                }else if ("3".equals(type)) {
                    String[] split = bean.getMsg().get(position).getCoordinate().split(",");
                    if (split.length < 2) {
                        Toast.makeText(context, R.string.qing_qiu_shi_bai, Toast.LENGTH_SHORT).show();
                    }else {
                        double lo = Double.parseDouble(split[0]);
                        double la = Double.parseDouble(split[1]);
                        MyApplication.mLocation = new LatLng(la, lo);
                        ((AddressActivity)context).finish();
                    }
                }
            }
        });
        holder.llAddressList.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                String[] strings = new String[]{context.getResources().getString(R.string.shan_chu_di_zhi)};
                builder.setItems(strings, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HttpUtils httpUtils = new HttpUtils(Contants.URL_DELADDRESS) {
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
                                    } else if(status == 9) {
                                        Toast.makeText(context, R.string.Please_Log_on_again, Toast.LENGTH_SHORT).show();
                                        MyApplication.saveLogin(null);
                                        context.startActivity(new Intent(context, LoginActivity.class));
                                        ((AddressActivity)context).finish();
                                    }else if (status == 1) {
                                        Toast.makeText(context, R.string.xiu_gai_cheng_gong, Toast.LENGTH_SHORT).show();
                                        bean.getMsg().remove(holder.getAdapterPosition());
                                        notifyDataSetChanged();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        Login login = MyApplication.getLogin();
                        if (login != null) {
                            httpUtils.addParam("userid", MyApplication.getLogin().getUserId());
                            httpUtils.addParam("token", MyApplication.getLogin().getToken());
                            httpUtils.addParam("str_addid", bean.getMsg().get(position).getAddid());
                            httpUtils.clicent();
                        }
                    }
                }).show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return bean.getMsg().size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_weizhi)
        TextView tvWeizhi;
        @Bind(R.id.tv_submit_name)
        TextView tvSubmitName;
        @Bind(R.id.tv_submit_sex)
        TextView tvSubmitSex;
        @Bind(R.id.tv_submit_phone)
        TextView tvSubmitPhone;
        @Bind(R.id.ll_weizhi)
        LinearLayout llWeizhi;
        @Bind(R.id.ll_address_list)
        LinearLayout llAddressList;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
