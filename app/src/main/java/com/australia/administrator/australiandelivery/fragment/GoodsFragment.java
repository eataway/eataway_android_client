package com.australia.administrator.australiandelivery.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;
import android.widget.Toast;


import com.australia.administrator.australiandelivery.R;
import com.australia.administrator.australiandelivery.adapter.BigramHeaderAdapter;
import com.australia.administrator.australiandelivery.adapter.PersonAdapter;
import com.australia.administrator.australiandelivery.adapter.RecycleGoodsCategoryListAdapter;
import com.australia.administrator.australiandelivery.bean.ShopDetailsBean;
import com.australia.administrator.australiandelivery.comm.GoodsListEvent;
import com.australia.library.OnHeaderClickListener;
import com.australia.library.StickyHeadersBuilder;
import com.australia.library.StickyHeadersItemDecoration;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


/**
 * 商品
 */
public class GoodsFragment extends BaseFragment1 implements PersonAdapter.OnShopCartGoodsChangeListener, OnHeaderClickListener {
    private RecyclerView mGoodsCateGoryList;
    private RecycleGoodsCategoryListAdapter mGoodsCategoryListAdapter;
    //商品类别列表
    private List<ShopDetailsBean.MsgBean.ShopmessageBean> goodscatrgoryEntities=new ArrayList<>();
    //商品列表
    private List<ShopDetailsBean.MsgBean.ShopmessageBean.GoodsBean> goodsitemEntities=new ArrayList<>();

    //存储含有标题的第一个含有商品类别名称的条目的下表
    private List<Integer> titlePois = new ArrayList<>();
    private List<Integer> num=new ArrayList<>();
    //上一个标题的小标
    private int lastTitlePoi;
    private RecyclerView recyclerView;
    private PersonAdapter personAdapter;
    private StickyHeadersItemDecoration top;
    private BigramHeaderAdapter headerAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    public static ShopDetailsBean shopDetailsBean;
    public static String id;
    public static Context context;
    private boolean isOnClick=false;
    private int isnum;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_goods, container, false);
        initView(view);
        initData();
        return view;
    }
    public static GoodsFragment newInstance( String id, ShopDetailsBean bean, Context context) {
        GoodsFragment tabFragment = new GoodsFragment();
        GoodsFragment.id = id;
        GoodsFragment.shopDetailsBean = bean;
        GoodsFragment.context = context;
        return tabFragment;
    }

    public void setJian(String id){
        personAdapter.setJian(id);

    }

    public void setJia(String id,String type){
        personAdapter.setJia(id,type);
    }

    public List<ShopDetailsBean.MsgBean.ShopmessageBean.GoodsBean> getCart(){
        return personAdapter.getCart();
    }
    private void initData() {
        int i = 0;
        int j = 0;
        boolean isFirst;
        for (ShopDetailsBean.MsgBean.ShopmessageBean dataItem : shopDetailsBean.getMsg().getShopmessage()) {
            goodscatrgoryEntities.add(dataItem);
            isFirst = true;
            for (ShopDetailsBean.MsgBean.ShopmessageBean.GoodsBean goodsitemEntity : dataItem.getGoods()) {
                if (isFirst) {
                    titlePois.add(j);
                    isFirst = false;
                }
                j++;
                goodsitemEntity.setId(i);
                goodsitemEntities.add(goodsitemEntity);
            }
            i++;
        }
        mGoodsCategoryListAdapter = new RecycleGoodsCategoryListAdapter(shopDetailsBean.getMsg().getShopmessage(), getActivity());
        mGoodsCateGoryList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mGoodsCateGoryList.setAdapter(mGoodsCategoryListAdapter);
        mGoodsCategoryListAdapter.setOnItemClickListener(new RecycleGoodsCategoryListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                isOnClick=true;
                recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView,null,titlePois.get(position)+position+2);
                mGoodsCategoryListAdapter.setCheckPosition(position);
            }
        });

        mLinearLayoutManager =new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        personAdapter = new PersonAdapter(getActivity(),goodsitemEntities,shopDetailsBean.getMsg().getShopmessage());
        personAdapter.setmActivity(getActivity());
        headerAdapter=new BigramHeaderAdapter(getActivity(),goodsitemEntities,shopDetailsBean.getMsg().getShopmessage());
        top = new StickyHeadersBuilder()
                .setAdapter(personAdapter)
                .setRecyclerView(recyclerView)
                .setStickyHeadersAdapter(headerAdapter)
                .setOnHeaderClickListener(this)
                .build();
        recyclerView.addItemDecoration(top);
        recyclerView.setAdapter(personAdapter);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                isnum=newState;
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                for (int i=0;i<titlePois.size();i++){
                    if(mLinearLayoutManager.findFirstVisibleItemPosition()>= titlePois.get(i)&&!isOnClick){
                        mGoodsCategoryListAdapter.setCheckPosition(i);
                    }
                }
                if (isnum!=2)
                isOnClick=false;
            }
        });

    }


    private void initView(View view) {
        mGoodsCateGoryList = (RecyclerView)view.findViewById(R.id.goods_category_list);
        recyclerView = (RecyclerView) view.findViewById(R.id.goods_recycleView);
    }


    @Override
    public void onNumChange() {

    }

    @Override
    public void onHeaderClick(View header, long headerId) {
        TextView text = (TextView)header.findViewById(R.id.tvGoodsItemTitle);
        Toast.makeText(getActivity(), "Click on " + text.getText(), Toast.LENGTH_SHORT).show();
    }

    /**
     * 处理滑动 是两个ListView联动
     */
    private class MyOnGoodsScrollListener implements AbsListView.OnScrollListener {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (!(lastTitlePoi == goodsitemEntities.get(firstVisibleItem).getId())) {
                lastTitlePoi =goodsitemEntities
                        .get(firstVisibleItem)
                        .getId();
                mGoodsCategoryListAdapter.setCheckPosition(goodsitemEntities
                        .get(firstVisibleItem)
                        .getId());
                mGoodsCategoryListAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * 添加 或者  删除  商品发送的消息处理
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(GoodsListEvent event) {
        if(event.buyNums.length>0){
            for (int i=0;i<event.buyNums.length;i++){
                goodscatrgoryEntities.get(i).setBugNum(event.buyNums[i]);
            }
            mGoodsCategoryListAdapter.changeData(goodscatrgoryEntities);
        }
    }
}