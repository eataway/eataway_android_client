package com.australia.administrator.australiandelivery.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.australia.administrator.australiandelivery.MyApplication;
import com.australia.administrator.australiandelivery.R;
import com.australia.administrator.australiandelivery.activity.GoodsListActivity1;
import com.australia.administrator.australiandelivery.activity.MainActivity;
import com.australia.administrator.australiandelivery.bean.ShopBean;
import com.australia.administrator.australiandelivery.utils.GlideUtils;
import com.australia.administrator.australiandelivery.utils.MyClassOnclik;
import com.australia.administrator.australiandelivery.utils.ViewHeightCalculator;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bingoogolapple.bgabanner.BGABanner;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/6/8.
 */

public class MainPageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<View> list = new ArrayList<>();
    private ShopBean mesBean;
    private ShopBean addBean;

    private SortBarClickListener listener = new SortBarClickListener();

    public int BannerHeight = 0;
    public int SortBarHeight = 0;
    public int ResItemHeight = 0;
    public BGABanner banner;
    private MyClassOnclik myClassOnclik;
    private SortBarHolder sortBarHolder;

    public MainPageAdapter(Context context, ShopBean mesBean, MyClassOnclik myClassOnclik) {
        this.context = context;
        this.mesBean = mesBean;
        this.myClassOnclik = myClassOnclik;
    }

    public void addBean(ShopBean addBean) {
        for (int i = 0; i < addBean.getMsg().size(); i++) {
            mesBean.getMsg().add(addBean.getMsg().get(i));
        }
    }

    public ShopBean getShopBean() {
        return mesBean;
    }

    public void setShopBean(ShopBean shopBean) {
        this.mesBean = shopBean;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        Log.i("tag", "position:" + "");
        if (viewType == 0) {
            view = LayoutInflater.from(context).inflate(R.layout.item_main_top_banner, parent, false);
            TopBannerHolder holder = new TopBannerHolder(view);
            BannerHeight = ViewHeightCalculator.ViewHeightCaluclate(view);
            banner = (BGABanner) view.findViewById(R.id.banner_main_top);
            ((MainActivity) context).menu.addIgnoredView(banner);
            holder.banner = banner;
            Log.i("banner", "height:" + BannerHeight);
            return holder;
        } else if (viewType == 1) {
            view = LayoutInflater.from(context).inflate(R.layout.item_main_sortbar, parent, false);
            sortBarHolder = new SortBarHolder(view);
            SortBarHeight = ViewHeightCalculator.ViewHeightCaluclate(view);
            sortBarHolder.sortDefault = (TextView) view.findViewById(R.id.sort_default);
            sortBarHolder.sortRecommend = (TextView) view.findViewById(R.id.sort_recommend);
            sortBarHolder.sortDistance = (TextView) view.findViewById(R.id.sort_distance);
            sortBarHolder.sortSale = (TextView) view.findViewById(R.id.sort_sale);
            return sortBarHolder;
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_main_restaurant, parent, false);
            ResItemHeight = ViewHeightCalculator.ViewHeightCaluclate(view);
            PageListHolder holder = new PageListHolder(view);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.i("tag", "position:" + position + "");
        if (position == 0) {
            if (mesBean != null && mesBean.getMsg1() != null && mesBean.getMsg1().size() != 0)
                initTopBanner((TopBannerHolder) holder);
        } else if (position == 1) {
            initSortBar((SortBarHolder) holder);
        } else {
            initResList((PageListHolder) holder, position);
        }
    }

    @Override
    public int getItemCount() {

        if (mesBean != null && mesBean.getMsg().size() != 0) {
            return mesBean.getMsg().size() + 2;
        } else {
            return 2;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    /**
     * Holders
     */
    class TopBannerHolder extends RecyclerView.ViewHolder {

        public TopBannerHolder(View itemView) {
            super(itemView);
        }

        public BGABanner banner;
    }

    class SortBarHolder extends RecyclerView.ViewHolder {
        public SortBarHolder(View itemView) {
            super(itemView);
        }

        TextView sortDefault, sortRecommend, sortDistance, sortSale;
    }

    class PageListHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_main_res_icon)
        CircleImageView ivMainResIcon;
        @Bind(R.id.line_vertical)
        View lineVertical;
        @Bind(R.id.tv_main_res_label_01)
        TextView tvMainResLabel01;
        @Bind(R.id.tv_main_res_label_02)
        TextView tvMainResLabel02;
        @Bind(R.id.tv_main_res_label_03)
        TextView tvMainResLabel03;
        @Bind(R.id.line_horizontal)
        View lineHorizontal;
        @Bind(R.id.img_main_res_img)
        ImageView imgMainResImg;
        @Bind(R.id.tv_res_main_name)
        TextView tvResMainName;
        @Bind(R.id.tv_res_main_distance)
        TextView tvResMainDistance;
        @Bind(R.id.ll_reslist_item)
        LinearLayout llReslistItem;
        @Bind(R.id.img_main_res_tag)
        ImageView imgMainResTag;
        @Bind(R.id.rl_shape)
        RelativeLayout rlShape;

        public PageListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    /**
     * Holders初始化
     *
     * @param holder
     */
    private void initTopBanner(TopBannerHolder holder) {
        list.clear();
        for (int i = 0; i < mesBean.getMsg1().size(); i++) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            GlideUtils.load(context, mesBean.getMsg1().get(i).getShopphoto(), imageView, GlideUtils.Shape.ShopPic);
            list.add(imageView);
        }
        holder.banner.setData(list);
        holder.banner.setDelegate(new BGABanner.Delegate() {
            @Override
            public void onBannerItemClick(BGABanner banner, View itemView, Object model, int position) {
                Intent intent = new Intent(context, GoodsListActivity1.class);
                intent.putExtra("id", mesBean.getMsg1().get(position).getShopid());
                context.startActivity(intent);
            }
        });
    }

    private void initSortBar(SortBarHolder holder) {
        holder.sortDefault.setOnClickListener(listener);
        holder.sortDistance.setOnClickListener(listener);
        holder.sortRecommend.setOnClickListener(listener);
        holder.sortSale.setOnClickListener(listener);
    }

    private void initResList(PageListHolder holder, final int position) {
        holder.tvResMainName.setText(mesBean.getMsg().get(position - 2).getShopname());
        holder.tvResMainDistance.setText(mesBean.getMsg().get(position - 2).getJuli() + "km");
        GlideUtils.load(context, mesBean.getMsg().get(position - 2).getShophead(), holder.ivMainResIcon, GlideUtils.Shape.UserIcon);
        GlideUtils.load(context, mesBean.getMsg().get(position - 2).getShopphoto(), holder.imgMainResImg, GlideUtils.Shape.UserIcon);
        if (mesBean.getMsg().get(position - 2).getState().equals("1")) {
            if (MyApplication.isEnglish) {
                holder.imgMainResTag.setImageResource(R.drawable.img_main_res_tag_on_en);
            }else {
                holder.imgMainResTag.setImageResource(R.drawable.img_main_res_tag_on);
            }
            holder.rlShape.setVisibility(View.GONE);
        } else {
            if (MyApplication.isEnglish) {
                holder.imgMainResTag.setImageResource(R.drawable.img_main_res_tag_close_en);
            }else {
                holder.imgMainResTag.setImageResource(R.drawable.img_main_res_tag_off);
            }
            holder.rlShape.setVisibility(View.VISIBLE);
        }
        if (mesBean.getMsg().get(position - 2).getCategory().size() > 0) {
            holder.tvMainResLabel01.setVisibility(View.VISIBLE);
            holder.tvMainResLabel01.setText(mesBean.getMsg().get(position - 2).getCategory().get(0).getCname());
        } else {
            holder.tvMainResLabel01.setVisibility(View.GONE);
        }
//        if (mesBean.getMsg().get(position - 2).getCategory().size() > 1) {
//            holder.tvMainResLabel02.setVisibility(View.VISIBLE);
//            holder.tvMainResLabel02.setText(mesBean.getMsg().get(position - 2).getCategory().get(1).getCname());
//        } else {
//            holder.tvMainResLabel02.setVisibility(View.GONE);
//
//        }
//        if (mesBean.getMsg().get(position - 2).getCategory().size() > 2) {
//            holder.tvMainResLabel03.setVisibility(View.VISIBLE);
//            holder.tvMainResLabel03.setText(mesBean.getMsg().get(position - 2).getCategory().get(2).getCname());
//        } else {
//            holder.tvMainResLabel03.setVisibility(View.GONE);
//        }
        holder.llReslistItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GoodsListActivity1.class);
                intent.putExtra("id", mesBean.getMsg().get(position - 2).getShopid());
                context.startActivity(intent);
            }
        });
    }

    /**
     * 辅助类
     */

    private class SortBarClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.sort_default:
                    sortBarHolder.sortDefault.setTextColor(context.getResources().getColor(R.color.text_black));
                    sortBarHolder.sortDistance.setTextColor(context.getResources().getColor(R.color.list_line));
                    sortBarHolder.sortRecommend.setTextColor(context.getResources().getColor(R.color.list_line));
                    sortBarHolder.sortSale.setTextColor(context.getResources().getColor(R.color.list_line));
                    ((MainActivity)context).sortDefault.setTextColor(context.getResources().getColor(R.color.text_black));
                    ((MainActivity)context).sortDistance.setTextColor(context.getResources().getColor(R.color.list_line));
                    ((MainActivity)context).sortRecommend.setTextColor(context.getResources().getColor(R.color.list_line));
                    ((MainActivity)context).sortSale.setTextColor(context.getResources().getColor(R.color.list_line));
                    myClassOnclik.setRefreshing(0, 1);
                    break;
                case R.id.sort_recommend:
                    sortBarHolder.sortRecommend.setTextColor(context.getResources().getColor(R.color.text_black));
                    sortBarHolder.sortDistance.setTextColor(context.getResources().getColor(R.color.list_line));
                    sortBarHolder.sortDefault.setTextColor(context.getResources().getColor(R.color.list_line));
                    sortBarHolder.sortSale.setTextColor(context.getResources().getColor(R.color.list_line));
                    ((MainActivity)context).sortDefault.setTextColor(context.getResources().getColor(R.color.list_line));
                    ((MainActivity)context).sortDistance.setTextColor(context.getResources().getColor(R.color.list_line));
                    ((MainActivity)context).sortRecommend.setTextColor(context.getResources().getColor(R.color.text_black));
                    ((MainActivity)context).sortSale.setTextColor(context.getResources().getColor(R.color.list_line));
                    myClassOnclik.setRefreshing(1, 1);
                    break;
                case R.id.sort_distance:
                    sortBarHolder.sortDistance.setTextColor(context.getResources().getColor(R.color.text_black));
                    sortBarHolder.sortDefault.setTextColor(context.getResources().getColor(R.color.list_line));
                    sortBarHolder.sortSale.setTextColor(context.getResources().getColor(R.color.list_line));
                    sortBarHolder.sortRecommend.setTextColor(context.getResources().getColor(R.color.list_line));
                    ((MainActivity)context).sortDefault.setTextColor(context.getResources().getColor(R.color.list_line));
                    ((MainActivity)context).sortDistance.setTextColor(context.getResources().getColor(R.color.text_black));
                    ((MainActivity)context).sortRecommend.setTextColor(context.getResources().getColor(R.color.list_line));
                    ((MainActivity)context).sortSale.setTextColor(context.getResources().getColor(R.color.list_line));
                    myClassOnclik.setRefreshing(2, 1);
                    break;
                case R.id.sort_sale:
                    sortBarHolder.sortSale.setTextColor(context.getResources().getColor(R.color.text_black));
                    sortBarHolder.sortDistance.setTextColor(context.getResources().getColor(R.color.list_line));
                    sortBarHolder.sortDefault.setTextColor(context.getResources().getColor(R.color.list_line));
                    sortBarHolder.sortRecommend.setTextColor(context.getResources().getColor(R.color.list_line));
                    ((MainActivity)context).sortDefault.setTextColor(context.getResources().getColor(R.color.list_line));
                    ((MainActivity)context).sortDistance.setTextColor(context.getResources().getColor(R.color.list_line));
                    ((MainActivity)context).sortRecommend.setTextColor(context.getResources().getColor(R.color.list_line));
                    ((MainActivity)context).sortSale.setTextColor(context.getResources().getColor(R.color.text_black));
                    myClassOnclik.setRefreshing(3, 1);
                    break;
            }
        }
    }

    public View.OnClickListener getSortBarListener() {
        return this.listener;
    }

}
