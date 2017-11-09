package com.australia.administrator.australiandelivery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.australia.administrator.australiandelivery.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/7.
 */

public class LeftMenuAdapter extends BaseAdapter{
    private Context context;
    private List<String> data = new ArrayList<>();

    public LeftMenuAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){ convertView = LayoutInflater.from(context).inflate(R.layout.item_main_left,null); }
        TextView tv = (TextView) convertView.findViewById(R.id.tv);
        tv.setText(data.get(position));
        return convertView;
    }
}
