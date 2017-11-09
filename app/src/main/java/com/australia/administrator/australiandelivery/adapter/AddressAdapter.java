package com.australia.administrator.australiandelivery.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.australia.administrator.australiandelivery.R;
import com.australia.administrator.australiandelivery.activity.MapActivity;
import com.australia.administrator.australiandelivery.bean.AddressBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/22.
 */

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    private Context context;
    private AddressBean bean;
    private MapActivity activity;

    public AddressAdapter(Context context, AddressBean bean,MapActivity activity) {
        this.context = context;
        this.bean = bean;
        this.activity=activity;
    }

    public AddressBean getBean() {
        return bean;
    }

    public void setBean(AddressBean bean) {
        this.bean = bean;
    }

    @Override
    public AddressAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recylerview_address_list, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(AddressAdapter.ViewHolder holder, final int position) {
        holder.tvBottomAddress.setText(bean.getResults().get(position).getVicinity());
        holder.tvTopAddress.setText(bean.getResults().get(position).getName());
        holder.llMapListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("vicinity",bean.getResults().get(position).getName() + " "+bean.getResults().get(position).getVicinity());
                intent.putExtra("lat_lon",bean.getResults().get(position).getGeometry().getLocation().getLat()+","+bean.getResults().get(position).getGeometry().getLocation().getLng());
                activity.setResult(9,intent);
                activity.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return bean.getResults().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.tv_top_address)
        TextView tvTopAddress;
        @Bind(R.id.tv_bottom_address)
        TextView tvBottomAddress;
        @Bind(R.id.ll_map_list_item)
        LinearLayout llMapListItem;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
