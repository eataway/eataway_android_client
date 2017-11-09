package com.australia.administrator.australiandelivery.view;

import android.view.LayoutInflater;
import android.view.View;

import com.ajguan.library.ILoadMoreView;
import com.australia.administrator.australiandelivery.MyApplication;
import com.australia.administrator.australiandelivery.R;

/**
 * Created by Administrator on 2017/6/27.
 */

public class MainLoadMoreView implements ILoadMoreView {
    View view = LayoutInflater.from(MyApplication.context).inflate(R.layout.layout_actionbar_mainpage,null);
    @Override
    public void reset() {

    }

    @Override
    public void loading() {

    }

    @Override
    public void loadComplete() {

    }

    @Override
    public void loadFail() {

    }

    @Override
    public void loadNothing() {

    }

    @Override
    public View getCanClickFailView() {
        return null;
    }
}
