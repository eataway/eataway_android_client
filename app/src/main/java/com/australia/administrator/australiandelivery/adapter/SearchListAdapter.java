package com.australia.administrator.australiandelivery.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.australia.administrator.australiandelivery.R;
import com.australia.administrator.australiandelivery.activity.GoodsListActivity1;
import com.australia.administrator.australiandelivery.activity.SearchActivity;
import com.australia.administrator.australiandelivery.bean.SearchBean;

/**
 * Created by Administrator on 2017/6/27.
 */

public class SearchListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private SearchBean bean;

    public SearchListAdapter(Context context, SearchBean bean) {
        this.context = context;
        this.bean = bean;
    }

    public SearchBean getBean() {
        return bean;
    }

    public void setBean(SearchBean bean) {
        this.bean = bean;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search_list, null);
        ResItemHolder holder = new ResItemHolder(view);
        holder.tvResItem = (TextView)view.findViewById(R.id.tv_search_list_item);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((ResItemHolder)holder).tvResItem.setText(bean.getMsg().get(position).getShopname());
        ((ResItemHolder)holder).tvResItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GoodsListActivity1.class);
                intent.putExtra("id", bean.getMsg().get(position).getShopid());
                intent.putExtra("shopname",bean.getMsg().get(position).getShopname());
                context.startActivity(intent);
                ((SearchActivity)context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (bean != null) {
            return bean.getMsg().size();
        }else {
            return 0;
        }
    }

    class ResItemHolder extends RecyclerView.ViewHolder {
        public ResItemHolder(View itemView) {
            super(itemView);
        }
        TextView tvResItem;
    }
}
