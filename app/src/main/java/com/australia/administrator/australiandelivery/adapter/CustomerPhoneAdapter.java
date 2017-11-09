package com.australia.administrator.australiandelivery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.australia.administrator.australiandelivery.R;
import com.australia.administrator.australiandelivery.utils.ContactUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/15.
 */

public class CustomerPhoneAdapter extends RecyclerView.Adapter<CustomerPhoneAdapter.ViewHolder> {
    private Context context;
    private List<String> numbers;

    public CustomerPhoneAdapter(Context context) {
        this.context = context;
    }

    public CustomerPhoneAdapter(Context context, List<String> numbers) {
        this.context = context;
        this.numbers = numbers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_customer_phone, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (position != numbers.size()-1) {
            holder.tvCustomerPhone.setText(numbers.get(position)+"(中文)");
        }else {
            holder.tvCustomerPhone.setText(numbers.get(position)+"(English)");
        }
        holder.tvCustomerPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactUtils.ContactUS(context, numbers.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (numbers != null)
            return numbers.size();
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.tv_customer_phone)
        TextView tvCustomerPhone;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
